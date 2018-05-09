package org.colorcoding.ibas.importexport.data;

import org.colorcoding.ibas.bobas.mapping.Value;

/**
 * 水平对齐
 * 
 * @author Niuren.Zhu
 *
 */
public enum emJustificationHorizontal {
	/**
	 * 靠右
	 */
	@Value("HR")
	RIGHT,
	/**
	 * 靠左
	 */
	@Value("HL")
	LEFT,
	/**
	 * 中间
	 */
	@Value("HC")
	CENTER,
}
