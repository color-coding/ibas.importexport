package org.colorcoding.ibas.importexport.transformers.excel.template;

/**
 * 模板-属性区，类属性
 * 
 * @author Niuren.Zhu
 *
 */
public class Property extends Area {

	public Property() {
		this.setStartingRow(3);
		this.setEndingRow(3);
		this.setStartingColumn(1);
		this.setEndingColumn(-1);
	}

	private Class<?> bindingClass;

	public final Class<?> getBindingClass() {
		return bindingClass;
	}

	public final void setBindingClass(Class<?> bindingClass) {
		this.bindingClass = bindingClass;
	}
}
