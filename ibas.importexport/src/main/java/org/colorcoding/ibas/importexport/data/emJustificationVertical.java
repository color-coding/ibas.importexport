package org.colorcoding.ibas.importexport.data;

import org.colorcoding.ibas.bobas.mapping.Value;

/**
 * 竖直对齐
 * 
 * @author Niuren.Zhu
 *
 */
public enum emJustificationVertical {
	/**
	 * 靠上
	 */
	@Value("VT")
	TOP,
	/**
	 * 靠下
	 */
	@Value("VB")
	BOTTOM,
	/**
	 * 中间
	 */
	@Value("VC")
	CENTER,
}
