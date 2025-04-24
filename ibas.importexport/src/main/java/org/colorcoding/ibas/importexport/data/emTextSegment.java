package org.colorcoding.ibas.importexport.data;

import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.common.Value;
import org.colorcoding.ibas.importexport.MyConfiguration;

/**
 * 文本段落
 * 
 * @author Niuren.Zhu
 *
 */
@XmlType(namespace = MyConfiguration.NAMESPACE_BO)
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
