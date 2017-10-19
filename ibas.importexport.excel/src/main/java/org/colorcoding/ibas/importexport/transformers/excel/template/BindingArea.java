package org.colorcoding.ibas.importexport.transformers.excel.template;

public abstract class BindingArea<P extends Area<?>> extends Area<P> {

	private Class<?> bindingClass;

	public final Class<?> getBindingClass() {
		return bindingClass;
	}

	public final void setBindingClass(Class<?> bindingClass) {
		this.bindingClass = bindingClass;
	}

	public String bindingNotes() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("type: ");
		stringBuilder.append(this.getClass().getSimpleName());
		stringBuilder.append(System.getProperty("line.separator"));
		stringBuilder.append("path: ");
		stringBuilder.append(this.getName());
		stringBuilder.append(System.getProperty("line.separator"));
		stringBuilder.append("class: ");
		stringBuilder.append(this.getBindingClass().getName());
		return stringBuilder.toString();
	}
}
