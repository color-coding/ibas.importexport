package org.colorcoding.ibas.importexport.transformers.excel.template;

import org.colorcoding.ibas.bobas.core.BOFactory;

public abstract class BindingArea<P extends Area<?>> extends Area<P> {

	public final static String NOTE_LINE_TYPE = "type: ";
	public final static String NOTE_LINE_PATH = "path: ";
	public final static String NOTE_LINE_CLASS = "class: ";

	private Class<?> bindingClass;

	public final Class<?> getBindingClass() {
		return bindingClass;
	}

	public final void setBindingClass(Class<?> bindingClass) {
		this.bindingClass = bindingClass;
	}

	public String bindingNotes() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(NOTE_LINE_TYPE);
		stringBuilder.append(this.getClass().getSimpleName());
		stringBuilder.append(System.getProperty("line.separator"));
		stringBuilder.append(NOTE_LINE_PATH);
		stringBuilder.append(this.getName());
		stringBuilder.append(System.getProperty("line.separator"));
		stringBuilder.append(NOTE_LINE_CLASS);
		stringBuilder.append(this.getBindingClass().getName());
		return stringBuilder.toString();
	}

	public boolean resolvingNotes(String note) throws ClassNotFoundException {
		String[] lines = note.split(System.getProperty("line.separator"));
		if (lines == null || lines.length != 3) {
			return false;
		}
		String type = lines[0].substring(NOTE_LINE_TYPE.length());
		if (!this.getClass().getSimpleName().equals(type)) {
			return false;
		}
		String name = lines[1].substring(NOTE_LINE_PATH.length());
		if (name == null || name.isEmpty()) {
			return false;
		}
		String bind = lines[2].substring(NOTE_LINE_CLASS.length());
		if (bind == null || bind.isEmpty()) {
			return false;
		}
		Class<?> bindType = BOFactory.create().getClass(bind);
		if (type == null) {
			return false;
		}
		this.setName(name);
		this.setBindingClass(bindType);
		return true;
	}
}
