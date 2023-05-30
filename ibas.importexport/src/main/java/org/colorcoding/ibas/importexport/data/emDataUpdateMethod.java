package org.colorcoding.ibas.importexport.data;

import org.colorcoding.ibas.bobas.mapping.Value;

/**
 * 数据更新方式
 * 
 * @author Niuren.Zhu
 *
 */
public enum emDataUpdateMethod {
	/**
	 * 跳过
	 */
	@Value("S")
	SKIP,
	/**
	 * 替换（旧数据删除）
	 */
	@Value("R")
	REPLACE,
	/**
	 * 修改（修改旧数据）
	 */
	@Value("M")
	MODIFY,
}
