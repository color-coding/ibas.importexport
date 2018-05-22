package org.colorcoding.ibas.importexport.transformer.template;

/**
 * 模板-属性区，类属性
 * 
 * @author Niuren.Zhu
 *
 */
public class Property extends BindingArea<Object> {

	public static final int OBJECT_STARTING_ROW = 2;
	public static final int OBJECT_STARTING_COLUMN = 0;

	public Property() {
		this.setStartingRow(OBJECT_STARTING_ROW);
		this.setEndingRow(this.getStartingRow());
		this.setStartingColumn(OBJECT_STARTING_COLUMN);
		this.setEndingColumn(AREA_AUTO_REGION);
	}

	@Override
	public int getIndex() {
		if (this.getParent() instanceof Object) {
			Object parent = (Object) this.getParent();
			for (int i = 0; i < parent.getProperties().length; i++) {
				if (parent.getProperties()[i] == this) {
					return i;
				}
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		return String.format("{property: %s}", super.toString());
	}
}
