package org.colorcoding.ibas.importexport.transformers.excel.template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.bo.IBusinessObjects;
import org.colorcoding.ibas.bobas.core.fields.IFieldData;
import org.colorcoding.ibas.bobas.core.fields.IManageFields;

/**
 * 模板（sheet）
 * 
 * @author Niuren.Zhu
 *
 */
public class Template extends Area {

	public Template() {
		this.setStartingRow(-1);
		this.setEndingRow(-1);
		this.setStartingColumn(-1);
		this.setEndingColumn(-1);
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
	public final void setHead(Head head) {
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
	protected final void addObject(Object object) {
		if (this.objects == null) {
			this.objects = new ArrayList<>();
		}
		this.objects.add(object);
	}

	/**
	 * 解析对象，形成模板
	 * 
	 * @param bo
	 *            待解析对象
	 * @throws NotRecognizedException
	 *             无法识别异常
	 */
	public final void resolving(IBusinessObject bo) throws NotRecognizedException {
		try {
			this.setName(bo.getClass().getSimpleName());
			this.resolvingObject(bo);
		} catch (Exception e) {
			throw new NotRecognizedException(e);
		}
	}

	/**
	 * 解析对象区域
	 * 
	 * @param bo
	 * @return
	 * @throws NotRecognizedException
	 */
	protected void resolvingObject(IBusinessObject bo) throws NotRecognizedException {
		// 根对象
		Object object = new Object();
		object.resolving(bo);
		object.setStartingRow(1);
		object.setEndingRow(object.getStartingRow());
		object.setStartingColumn(
				this.getObjects().length > 0 ? this.getObjects()[this.getObjects().length - 1].getEndingColumn() + 1
						: 1);
		object.setEndingColumn(object.getStartingColumn() + object.getProperties().length);
		this.addObject(object);
		// 集合对象
		IManageFields fields = (IManageFields) bo;
		for (IFieldData field : fields.getFields()) {
			if (IBusinessObjects.class.isInstance(field.getValue())) {
				IBusinessObject item = ((IBusinessObjects<?, ?>) field.getValue()).create();
				if (item instanceof IBusinessObject) {
					this.resolvingObject((IBusinessObject) item);
				}
			}
		}
	}

	/**
	 * 解析文件，形成模板
	 * 
	 * @param file
	 *            待分析文件
	 * @throws NotRecognizedException
	 *             无法识别异常
	 */
	public final void resolving(File file) throws NotRecognizedException {

	}

	public Data read(int row, int column) {

		return Data.DATA_NULL;
	}
}
