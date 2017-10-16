package org.colorcoding.ibas.importexport.transformers.excel.template;

import java.util.ArrayList;
import java.util.List;

import org.colorcoding.ibas.bobas.bo.IBusinessObjects;
import org.colorcoding.ibas.bobas.core.IPropertyInfo;
import org.colorcoding.ibas.bobas.core.PropertyInfoList;
import org.colorcoding.ibas.bobas.core.PropertyInfoManager;

/**
 * 模板-对象区，类
 * 
 * @author Niuren.Zhu
 *
 */
public class Object extends Area {

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
	public final void resolving(Class<?> type) throws NotRecognizedException {
		this.setBindingClass(type);
		// 集合对象
		List<Property> properties = new ArrayList<>();
		// 分析属性的对象
		PropertyInfoList pInfoList = PropertyInfoManager.getPropertyInfoList(type);
		for (IPropertyInfo<?> pInfo : pInfoList) {
			if (pInfo.getValueType().isAssignableFrom(IBusinessObjects.class)) {
				continue;
			}
			Property property = new Property();
			property.setName(pInfo.getName());
			property.setBindingClass(pInfo.getValueType());
			properties.add(property);
		}
		this.setProperties(properties.toArray(new Property[] {}));
	}

}
