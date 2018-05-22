package org.colorcoding.ibas.importexport.transformer.template;

/**
 * 模板区域
 * 
 * @author Niuren.Zhu
 *
 * @param <P>
 *            父项类型
 */
public abstract class Area<P extends Area<?>> {

	public static final int AREA_AUTO_REGION = -1;

	public Area() {
		this.setEndingColumn(AREA_AUTO_REGION);
		this.setEndingRow(AREA_AUTO_REGION);
		this.setStartingColumn(AREA_AUTO_REGION);
		this.setStartingRow(AREA_AUTO_REGION);
	}

	private String name;

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	private String description;

	public final String getDescription() {
		if (this.description == null || this.description.isEmpty()) {
			return this.getName();
		}
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	private int startingRow;

	public final int getStartingRow() {
		return startingRow;
	}

	public final void setStartingRow(int startingRow) {
		this.startingRow = startingRow;
	}

	private int startingColumn;

	public final int getStartingColumn() {
		return startingColumn;
	}

	public final void setStartingColumn(int startingColumn) {
		this.startingColumn = startingColumn;
	}

	private int endingRow;

	public final int getEndingRow() {
		return endingRow;
	}

	public final void setEndingRow(int endingRow) {
		this.endingRow = endingRow;
	}

	private int endingColumn;

	public final int getEndingColumn() {
		return endingColumn;
	}

	public final void setEndingColumn(int endingColumn) {
		this.endingColumn = endingColumn;
	}

	private P parent;

	public final P getParent() {
		return parent;
	}

	final void setParent(P parent) {
		this.parent = parent;
	}

	/**
	 * 父项的索引
	 * 
	 * @return
	 */
	public abstract int getIndex();

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		if (this.getName() != null && !this.getName().isEmpty()) {
			stringBuilder.append(this.getName());
		} else {
			stringBuilder.append("UNKNOWN");
		}
		stringBuilder.append(" ");
		stringBuilder.append("(");
		stringBuilder.append("row=");
		stringBuilder.append("[");
		stringBuilder.append(this.getStartingRow());
		stringBuilder.append(",");
		stringBuilder.append(this.getEndingRow());
		stringBuilder.append("]");
		stringBuilder.append(" ");
		stringBuilder.append("col=");
		stringBuilder.append("[");
		stringBuilder.append(this.getStartingColumn());
		stringBuilder.append(",");
		stringBuilder.append(this.getEndingColumn());
		stringBuilder.append("]");
		stringBuilder.append(")");
		return stringBuilder.toString();
	}
}
