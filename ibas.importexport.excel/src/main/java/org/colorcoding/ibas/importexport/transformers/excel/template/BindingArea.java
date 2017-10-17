package org.colorcoding.ibas.importexport.transformers.excel.template;

public abstract class BindingArea extends Area {

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
		stringBuilder.append("binding: ");
		stringBuilder.append(this.getName());
		stringBuilder.append(System.getProperty("line.separator"));
		stringBuilder.append("data: ");
		stringBuilder.append(this.getBindingClass().getName());
		return stringBuilder.toString();
	}
}
