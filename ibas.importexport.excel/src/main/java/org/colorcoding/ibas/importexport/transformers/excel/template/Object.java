package org.colorcoding.ibas.importexport.transformers.excel.template;

import java.util.ArrayList;
import java.util.List;

import org.colorcoding.ibas.bobas.bo.IBOStorageTag;
import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.bo.IBusinessObjects;
import org.colorcoding.ibas.bobas.core.fields.IFieldData;
import org.colorcoding.ibas.bobas.core.fields.IManageFields;

/**
 * 模板-对象区，类
 * 
 * @author Niuren.Zhu
 *
 */
public class Object extends BindingArea {

	public static final int OBJECT_STARTING_ROW = 1;
	public static final int OBJECT_STARTING_COLUMN = 0;

	public Object() {
		this.setStartingRow(OBJECT_STARTING_ROW);
		this.setEndingRow(this.getStartingRow());
		this.setStartingColumn(OBJECT_STARTING_COLUMN);
		this.setEndingColumn(AREA_AUTO_REGION);
	}

	@Override
	public int getIndex() {
		if (this.getParent() instanceof Template) {
			Template parent = (Template) this.getParent();
			for (int i = 0; i < parent.getObjects().length; i++) {
				if (parent.getObjects()[i] == this) {
					return i;
				}
			}
		}
		return -1;
	}

	private Property[] properties;

	public final Property[] getProperties() {
		if (this.properties == null) {
			this.properties = new Property[] {};
		}
		return properties;
	}

	private final void setProperties(Property[] properties) {
		this.properties = properties;
	}

	/**
	 * 解析对象，形成模板
	 * 
	 * @param bo
	 *            待解析对象
	 * @throws ParsingException
	 *             无法识别异常
	 */
	public final void resolving(IBusinessObject bo) throws ParsingException {
		this.setBindingClass(bo.getClass());
		List<Property> properties = new ArrayList<>();
		IManageFields fields = (IManageFields) bo;
		for (IFieldData field : fields.getFields()) {
			// 对象不再处理
			if (IBusinessObjects.class.isInstance(field.getValue())) {
				continue;
			}
			// 对象不再处理
			if (IBusinessObject.class.isInstance(field.getValue())) {
				continue;
			}
			// 过滤属性
			if (bo.isNew() && bo instanceof IBOStorageTag) {
				// 新建对象，不处理存储标签
				try {
					if (IBOStorageTag.class.getDeclaredMethod("get" + field.getName()) != null) {
						continue;
					}
				} catch (SecurityException | NoSuchMethodException e) {
				}
			}
			Property property = new Property();
			property.setParent(this);
			property.setName(field.getName());
			property.setBindingClass(field.getValueType());
			property.setStartingRow(property.getParent().getEndingRow() + 1);
			property.setEndingRow(property.getStartingRow());
			property.setStartingColumn(property.getParent().getStartingColumn() + properties.size());
			property.setEndingColumn(property.getStartingColumn());
			properties.add(property);
		}
		this.setProperties(properties.toArray(new Property[] {}));
	}

	@Override
	public String toString() {
		return String.format("{object: %s}", super.toString());
	}
}
