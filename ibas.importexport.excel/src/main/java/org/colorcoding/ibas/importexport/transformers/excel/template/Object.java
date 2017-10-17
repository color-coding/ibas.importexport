package org.colorcoding.ibas.importexport.transformers.excel.template;

import java.util.ArrayList;
import java.util.List;

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
public class Object extends Area {

	public static final int OBJECT_STARTING_ROW = 1;
	public static final int OBJECT_STARTING_COLUMN = 0;

	public Object() {
		this.setStartingRow(OBJECT_STARTING_ROW);
		this.setEndingRow(this.getStartingRow());
		this.setStartingColumn(OBJECT_STARTING_COLUMN);
		this.setEndingColumn(AREA_AUTO_REGION);
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

	private Class<?> bindingClass;

	public final Class<?> getBindingClass() {
		return bindingClass;
	}

	private final void setBindingClass(Class<?> bindingClass) {
		this.bindingClass = bindingClass;
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
		this.setName(bo.getClass().getSimpleName());
		this.setBindingClass(bo.getClass());
		// 集合对象
		List<Property> properties = new ArrayList<>();
		IManageFields fields = (IManageFields) bo;
		for (IFieldData field : fields.getFields()) {
			if (IBusinessObjects.class.isInstance(field.getValue())) {
				continue;
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
