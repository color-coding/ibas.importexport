package org.colorcoding.ibas.importexport.data;

import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.common.Value;
import org.colorcoding.ibas.importexport.MyConfiguration;

/**
 * 竖直对齐
 * 
 * @author Niuren.Zhu
 *
 */
@XmlType(namespace = MyConfiguration.NAMESPACE_BO)
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
