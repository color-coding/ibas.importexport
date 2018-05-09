package org.colorcoding.ibas.importexport.data;

import org.colorcoding.ibas.bobas.mapping.Value;

/**
 * 文本样式
 * 
 * @author Niuren.Zhu
 *
 */
public enum emTextStyle {
	/**
	 * 常规
	 */
	@Value("RG")
	REGULAR,
	/**
	 * 粗体
	 */
	@Value("BD")
	BOLD,
	/**
	 * 斜体
	 */
	@Value("IT")
	ITALIC,
	/**
	 * 粗斜体
	 */
	@Value("BI")
	BOLD_ITALIC
}
