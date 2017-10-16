package org.colorcoding.ibas.importexport.transformers.excel.template;

/**
 * 模板区域
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class Area implements IArea {

	public Area() {
		this.setEndingColumn(-1);
		this.setEndingRow(-1);
		this.setStartingColumn(-1);
		this.setStartingRow(-1);
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

	private Area parent;

	public final Area getParent() {
		return parent;
	}

	public final void setParent(Area parent) {
		this.parent = parent;
	}
}
