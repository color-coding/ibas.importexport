package org.colorcoding.ibas.importexport.transformers.excel.template;

/**
 * 模板-头，整体信息
 * 
 * @author Niuren.Zhu
 *
 */
public class Head extends BindingArea {

	public static final int HEAD_STARTING_ROW = 0;
	public static final int HEAD_STARTING_COLUMN = 0;

	public Head() {
		this.setStartingRow(HEAD_STARTING_ROW);
		this.setEndingRow(this.getStartingRow());
		this.setStartingColumn(HEAD_STARTING_ROW);
		this.setEndingColumn(AREA_AUTO_REGION);
	}

	@Override
	public String toString() {
		return String.format("{head: %s}", super.toString());
	}

}
