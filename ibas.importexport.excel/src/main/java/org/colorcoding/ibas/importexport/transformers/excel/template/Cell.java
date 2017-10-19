package org.colorcoding.ibas.importexport.transformers.excel.template;

/**
 * 模板-数据区，属性值
 * 
 * @author Niuren.Zhu
 *
 */
public class Cell extends Area<Property> {
	public static final String DEFAULT_NAME = "DATA_CELL";

	public Cell() {
		this.setName(DEFAULT_NAME);
	}

	@Override
	public int getIndex() {
		if (this.getParent() instanceof Property) {
			Property parent = (Property) this.getParent();
			if (parent != null) {
				return 0;
			}
		}
		return -1;
	}

	private java.lang.Object value;

	public final java.lang.Object getValue() {
		return value;
	}

	public final void setValue(java.lang.Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format("{cell: %s}", super.toString());
	}
}
