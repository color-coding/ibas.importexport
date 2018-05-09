package org.colorcoding.ibas.importexport.data;

import org.colorcoding.ibas.bobas.mapping.Value;

/**
 * 数据源类型
 * 
 * @author Niuren.Zhu
 *
 */
public enum emDataSourceType {
	/**
	 * 文本
	 */
	@Value("T")
	TEXT,
	/**
	 * 路径
	 */
	@Value("P")
	PATH,
}
