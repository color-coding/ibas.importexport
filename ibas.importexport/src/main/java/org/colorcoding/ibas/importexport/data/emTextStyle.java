package org.colorcoding.ibas.importexport.data;

import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.common.Value;
import org.colorcoding.ibas.importexport.MyConfiguration;

/**
 * 文本样式
 * 
 * @author Niuren.Zhu
 *
 */
@XmlType(namespace = MyConfiguration.NAMESPACE_BO)
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
