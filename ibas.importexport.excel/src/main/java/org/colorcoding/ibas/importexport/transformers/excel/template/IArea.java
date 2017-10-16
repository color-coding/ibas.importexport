package org.colorcoding.ibas.importexport.transformers.excel.template;

/**
 * 模板区域
 * 
 * @author Niuren.Zhu
 *
 */
public interface IArea {

	/**
	 * 获取-名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 设置-名称
	 * 
	 * @param value
	 */
	void setName(String value);

	/**
	 * 获取-描述
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * 设置-描述
	 * 
	 * @param value
	 */
	void setDescription(String value);

	/**
	 * 获取-起始行
	 * 
	 * @return
	 */
	int getStartingRow();

	/**
	 * 设置-起始行
	 * 
	 * @param value
	 */
	void setStartingRow(int value);

	/**
	 * 获取-起始列
	 * 
	 * @return
	 */
	int getStartingColumn();

	/**
	 * 设置-起始列
	 * 
	 * @param value
	 */
	void setStartingColumn(int value);

	/**
	 * 获取-结束行
	 * 
	 * @return
	 */
	int getEndingRow();

	/**
	 * 设置-结束列
	 * 
	 * @return
	 */
	void setEndingRow(int value);

	/**
	 * 获取-结束列
	 * 
	 * @return
	 */
	int getEndingColumn();

	/**
	 * 设置-结束列
	 * 
	 * @param value
	 */
	void setEndingColumn(int value);
}
