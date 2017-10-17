package org.colorcoding.ibas.importexport.transformers.excel.template;

/**
 * 模板-头，整体信息
 * 
 * @author Niuren.Zhu
 *
 */
public class Head extends Area {

	public Head() {
		this.setStartingRow(1);
		this.setEndingRow(1);
		this.setStartingColumn(1);
		this.setEndingColumn(-1);
	}

	@Override
	public String toString() {
		return String.format("{head: %s}", super.toString());
	}
}
