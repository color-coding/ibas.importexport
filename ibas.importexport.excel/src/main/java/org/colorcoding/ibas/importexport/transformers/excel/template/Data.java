package org.colorcoding.ibas.importexport.transformers.excel.template;

/**
 * 模板-数据区，属性值
 * 
 * @author Niuren.Zhu
 *
 */
public class Data extends Area {

	/**
	 * 空值
	 */
	public final static Data DATA_NULL = new Data("NULL");

	public Data() {
	}

	public Data(String name) {
		this.setName(name);
	}

}
