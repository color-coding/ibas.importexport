package org.colorcoding.ibas.importexport.data;

import org.colorcoding.ibas.bobas.mapping.Value;

/**
 * 框线样式
 * 
 * @author Niuren.Zhu
 *
 */
public enum emLineStyle {
	/**
	 * 实线
	 */
	@Value("SO")
	SOLID,
	/**
	 * 虚线
	 */
	@Value("DS")
	DASHED,
	/**
	 * 点状
	 */
	@Value("DO")
	DOTTED,
	/**
	 * 双线
	 */
	@Value("DU")
	DOUBLE
}
