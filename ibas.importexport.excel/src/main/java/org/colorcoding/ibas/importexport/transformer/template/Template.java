package org.colorcoding.ibas.importexport.transformer.template;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.bo.IBusinessObjects;
import org.colorcoding.ibas.bobas.core.BOFactory;
import org.colorcoding.ibas.bobas.core.fields.IFieldData;
import org.colorcoding.ibas.bobas.core.fields.IManagedFields;

/**
 * 模板（sheet）
 * 
 * @author Niuren.Zhu
 *
 */
public class Template extends Area<Area<?>> {

	public static String TEMPLATE_NAME = String.format("%s@ibas", Template.class.getSimpleName());
	public static final String PROPERTY_PATH_SEPARATOR = ".";
	public static final String PROPERTY_PATH_LIST_SIGN = "[]";
	public static final String PROPERTY_PATH_FORMAT = "%s" + PROPERTY_PATH_SEPARATOR + "%s";
	public static final String LIST_PROPERTY_PATH_FORMAT = "%s" + PROPERTY_PATH_SEPARATOR + "%s"
			+ PROPERTY_PATH_LIST_SIGN;

	public Template() {
		this.setStartingRow(AREA_AUTO_REGION);
		this.setEndingRow(AREA_AUTO_REGION);
		this.setStartingColumn(AREA_AUTO_REGION);
		this.setEndingColumn(AREA_AUTO_REGION);
	}

	@Override
	public int getIndex() {
		return -1;
	}

	private Head head;

	/**
	 * 获取-模板头
	 * 
	 * @return
	 */
	public final Head getHead() {
		return head;
	}

	/**
	 * 设置-模板头
	 * 
	 * @param head
	 */
	final void setHead(Head head) {
		head.setParent(this);
		this.head = head;
	}

	private List<Object> objects;

	/**
	 * 获取-模板拥有对象
	 * 
	 * @return
	 */
	public final Object[] getObjects() {
		if (this.objects == null) {
			this.objects = new ArrayList<>();
		}
		return this.objects.toArray(new Object[] {});
	}

	/**
	 * 添加-模板拥有对象
	 * 
	 * @param object
	 */
	final void addObject(Object object) {
		if (this.objects == null) {
			this.objects = new ArrayList<>();
		}
		object.setParent(this);
		this.objects.add(object);
	}

	private Data datas;

	public final Data getDatas() {
		return datas;
	}

	final void setDatas(Data datas) {
		datas.setParent(this);
		this.datas = datas;
	}

	@Override
	public String toString() {
		return String.format("{template: %s}", super.toString());
	}

	/**
	 * 解析对象，形成模板
	 * 
	 * 解析的对象isNew时，不处理IBOStorageTag相关属性，生成模板时应如此
	 * 
	 * @param bo
	 *            待解析对象
	 * @throws ResolvingException
	 *             无法识别异常
	 */
	public final void resolving(IBusinessObject bo) throws ResolvingException {
		if (bo == null) {
			// 无效数据
			return;
		}
		if (this.head == null) {
			// 未解析头
			this.resolvingHead(bo);
			this.resolvingObject(bo, this.getHead().getName());
			// 填充模板信息
			this.setStartingColumn(this.getHead().getStartingColumn());
			this.setStartingRow(this.getHead().getStartingRow());
			if (this.getObjects().length > 0) {
				Object lastObject = this.getObjects()[this.getObjects().length - 1];
				if (lastObject.getProperties().length > 0) {
					Property lastProperty = lastObject.getProperties()[lastObject.getProperties().length - 1];
					this.setEndingColumn(lastProperty.getEndingColumn());
					this.setEndingRow(lastProperty.getEndingRow());
					this.getHead().setEndingColumn(this.getEndingColumn());
				}
			}
			this.setName(bo.getClass().getSimpleName());
			// 初始化数据区
			this.setDatas(new Data());
			this.getDatas().setColumnCount(new Function<Template, Integer>() {

				@Override
				public Integer apply(Template t) {
					int count = 0;
					for (Object object : t.getObjects()) {
						count += object.getProperties().length;
					}
					return count;
				}

			}.apply(this));
			this.getDatas().setStartingColumn(this.getStartingColumn());
			this.getDatas().setEndingColumn(this.getEndingColumn());
			this.getDatas().setStartingRow(this.getEndingRow() + 1);
		}
		if (this.getHead().getBindingClass() != bo.getClass()) {
			throw new ResolvingException("data class not match template binding.");
		}
		// 解析数据
		this.resolvingDatas(bo);
		this.setEndingRow(this.getDatas().getEndingRow());
	}

	/**
	 * 解析头区域
	 * 
	 * @param bo
	 * @throws ResolvingException
	 */
	private void resolvingHead(IBusinessObject bo) throws ResolvingException {
		Head head = new Head();
		head.setBindingClass(bo.getClass());
		head.setName(bo.getClass().getSimpleName());
		String boCode = BOFactory.create().getCode(bo.getClass());
		if (boCode != null && !boCode.isEmpty()) {
			head.setCode(boCode);
		}
		this.setHead(head);
	}

	/**
	 * 解析对象区域
	 * 
	 * @param bo
	 * @return
	 * @throws ResolvingException
	 */
	private void resolvingObject(IBusinessObject bo, String name) throws ResolvingException {
		// 根对象
		Object object = new Object();
		object.setName(name);
		object.setStartingRow(Object.OBJECT_STARTING_ROW);
		object.setEndingRow(object.getStartingRow());
		object.setStartingColumn(
				this.getObjects().length > 0 ? this.getObjects()[this.getObjects().length - 1].getEndingColumn() + 1
						: Object.OBJECT_STARTING_COLUMN);
		object.resolving(bo);
		object.setEndingColumn(object.getStartingColumn() + object.getProperties().length - 1);
		this.addObject(object);
		// 集合对象
		IManagedFields fields = (IManagedFields) bo;
		for (IFieldData field : fields.getFields()) {
			if (IBusinessObjects.class.isInstance(field.getValue())) {
				// 解析集合属性
				IBusinessObjects<?, ?> list = (IBusinessObjects<?, ?>) field.getValue();
				IBusinessObject subItem = list.firstOrDefault();
				if (subItem == null) {
					// 集合中没有对象，创建新的，并删除
					subItem = ((IBusinessObjects<?, ?>) field.getValue()).create();
					if (!bo.isNew()) {
						list.remove(subItem);// 非新建，移出自动添加对象
					}
				}
				if (subItem != null && !bo.getClass().isInstance(subItem)) {
					// 非本身类型，不做处理，防止嵌套
					this.resolvingObject(subItem, String.format(LIST_PROPERTY_PATH_FORMAT, name, field.getName()));
				}
			} else if (IBusinessObject.class.isInstance(field.getValue())
					&& !bo.getClass().isInstance(field.getValue())) {
				// 解析对象属性
				this.resolvingObject((IBusinessObject) field.getValue(),
						String.format(PROPERTY_PATH_FORMAT, name, field.getName()));
			}
		}
	}

	/**
	 * 解析数据区域
	 * 
	 * @param bo
	 * @throws ResolvingException
	 */
	private void resolvingDatas(IBusinessObject bo) throws ResolvingException {
		if (bo == null || this.head == null || this.objects == null) {
			// 未初始化，退出
			return;
		}
		this.resolvingDatas((IManagedFields) bo, this.getHead().getName());
	}

	private void resolvingDatas(IManagedFields boFields, String level) throws ResolvingException {
		if (boFields == null) {
			// 未初始化，退出
			return;
		}
		for (Object object : this.getObjects()) {
			if (!object.getName().startsWith(level)) {
				// 非此类，不做处理
				continue;
			}
			if (object.getName().equals(level)) {
				// 当前级别，同对象。如： TP - TP
				Cell[] row = this.getDatas().createRow();
				for (Property property : object.getProperties()) {
					Cell cell = new Cell();
					cell.setParent(property);
					cell.setStartingColumn(property.getStartingColumn());
					cell.setEndingColumn(cell.getStartingColumn());
					cell.setStartingRow(this.getDatas().getEndingRow());
					cell.setEndingRow(cell.getStartingRow());
					// 单元格赋值
					IFieldData field = boFields.getField(property.getName());
					if (field != null && field.getValue() != null) {
						cell.setValue(field.getValue());
					}
					row[property.getStartingColumn()] = cell;
				}
			} else if (object.getName().indexOf(PROPERTY_PATH_SEPARATOR, level.length() + 1) < 0) {
				// 当前基本，不同对象。如：TP.BB - TP or TP.AA[] - TP
				if (object.getName().endsWith(PROPERTY_PATH_LIST_SIGN)) {
					// TP.AA[] - TP
					String property = object.getName().substring(level.length() + 1, object.getName().length() - 2);
					IFieldData field = boFields.getField(property);
					if (field != null && IBusinessObjects.class.isInstance(field.getValue())) {
						IBusinessObjects<?, ?> list = (IBusinessObjects<?, ?>) field.getValue();
						for (IBusinessObject item : list) {
							if (item instanceof IManagedFields) {
								// 处理此数据
								this.resolvingDatas((IManagedFields) item, object.getName());
							}
						}
					}
				} else {
					// TP.BB - TP
					String property = object.getName().substring(level.length() + 1);
					IFieldData field = boFields.getField(property);
					if (field != null && field.getValue() instanceof IManagedFields) {
						this.resolvingDatas((IManagedFields) field.getValue(), object.getName());
					}
				}
			}
		}
	}

	/**
	 * 当前数据解析为对象
	 * 
	 * @return
	 * @throws ResolvingException
	 */
	public final IBusinessObject[] resolving() throws ResolvingException {
		if (this.getHead() != null && this.getObjects() != null && this.getDatas() != null) {
			try {
				ArrayList<IBusinessObject> businessObjects = new ArrayList<>();
				Iterator<Cell[]> rows = this.getDatas().getRowIterator();
				while (rows.hasNext()) {
					IBusinessObject bo = (IBusinessObject) this.getHead().getBindingClass().newInstance();
					if (!(bo instanceof IManagedFields)) {
						throw new ResolvingException(String.format("not supported %s", bo.getClass().getName()));
					}
					if (this.resolving((IManagedFields) bo, this.getHead().getName(), rows)) {
						// 成功获取数据
						businessObjects.add(bo);
					} else {
						// 此行无解析，继续
						rows.next();
					}
				}
				return businessObjects.toArray(new IBusinessObject[] {});
			} catch (ResolvingException e) {
				throw e;
			} catch (Exception e) {
				throw new ResolvingException(e);
			}
		}
		return new IBusinessObject[] {};
	}

	private boolean resolving(IManagedFields boFields, String level, Iterator<Cell[]> rows) {
		boolean done = false;
		for (Object object : this.getObjects()) {
			if (!object.getName().startsWith(level)) {
				// 非此类，不做处理
				continue;
			}
			if (object.getName().equals(level)) {
				// 当前级别，同对象。如： TP - TP
				if (rows.hasNext()) {
					boolean matched = false;
					Cell[] row = rows.next();
					for (Property property : object.getProperties()) {
						IFieldData field = boFields.getField(property.getName());
						if (field != null) {
							Cell cell = row[property.getStartingColumn()];
							if (cell != null && cell.getValue() != null) {
								field.setValue(cell.getValue());
								if (!matched) {
									matched = true;
								}
							}
						}
					}
					if (matched) {
						if (!done) {
							done = true;
						}
					} else {
						// 此行数据没有被识别，游标回滚
						rows.back();
					}
				}
			} else if (object.getName().indexOf(PROPERTY_PATH_SEPARATOR, level.length() + 1) < 0) {
				// 当前基本，不同对象。如：TP.BB - TP or TP.AA[] - TP
				if (object.getName().endsWith(PROPERTY_PATH_LIST_SIGN)) {
					// TP.AA[] - TP
					String property = object.getName().substring(level.length() + 1, object.getName().length() - 2);
					IFieldData field = boFields.getField(property);
					if (field != null && IBusinessObjects.class.isInstance(field.getValue())) {
						while (rows.hasNext()) {
							IBusinessObjects<?, ?> list = (IBusinessObjects<?, ?>) field.getValue();
							boolean doneItem = false;
							IBusinessObject boItem = list.create();
							if (boItem instanceof IManagedFields) {
								doneItem = this.resolving((IManagedFields) boItem, object.getName(), rows);
							}
							if (!doneItem) {
								// 未处理，移出
								list.remove(boItem);
								break;
							} else {
								if (!done) {
									done = true;
								}
							}
						}
					}
				} else {
					// TP.BB - TP
					String property = object.getName().substring(level.length() + 1);
					IFieldData field = boFields.getField(property);
					if (field != null && field.getValue() instanceof IManagedFields) {
						boolean doneItem = false;
						doneItem = this.resolving((IManagedFields) field.getValue(), object.getName(), rows);
						if (doneItem) {
							if (!done) {
								done = true;
							}
						}
					}
				}
			}
		}
		return done;
	}

	private FileWriter writer;

	public final FileWriter getWriter() {
		if (this.writer == null) {
			this.writer = new ExcelWriter();
		}
		return writer;
	}

	public final void setWriter(FileWriter writer) {
		this.writer = writer;
	}

	/**
	 * 模板内容输出文件
	 * 
	 * @param file
	 * @throws WriteFileException
	 * @throws IOException
	 */
	public final void write(File file) throws WriteFileException, IOException {
		this.getWriter().setTemplate(this);
		this.getWriter().write(file);
	}

	private FileReader reader;

	protected final FileReader getReader() {
		if (this.reader == null) {
			this.reader = new ExcelReader();
		}
		return reader;
	}

	protected final void setReader(FileReader reader) {
		this.reader = reader;
	}

	/**
	 * 解析文件，形成模板
	 * 
	 * @param file
	 *            待分析文件
	 * @throws ResolvingException
	 *             无法识别异常
	 */
	public final void resolving(File file) throws ResolvingException {
		if (this.head != null) {
			throw new ResolvingException("the template has been used.");
		}
		try {
			this.getReader().setTemplate(this);
			this.getReader().read(file);
		} catch (Exception e) {
			throw new ResolvingException(e);
		}
	}

}
