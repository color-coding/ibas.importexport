package org.colorcoding.ibas.importexport.data;

import org.colorcoding.ibas.bobas.mapping.Value;

/**
 * 文本段落
 * 
 * @author Niuren.Zhu
 *
 */
public enum emTextSegment {

	/**
	 * 字段落
	 */
	@Value("WD")
	WORD,
	/**
	 * 单元格
	 */
	@Value("CL")
	CELL,
}
