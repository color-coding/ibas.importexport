package org.colorcoding.ibas.importexport.transformers.excel.template;

/**
 * 模板-属性区，类属性
 * 
 * @author Niuren.Zhu
 *
 */
public class Property extends Area {

	private Class<?> bindingClass;

	public final Class<?> getBindingClass() {
		return bindingClass;
	}

	public final void setBindingClass(Class<?> bindingClass) {
		this.bindingClass = bindingClass;
	}
}
