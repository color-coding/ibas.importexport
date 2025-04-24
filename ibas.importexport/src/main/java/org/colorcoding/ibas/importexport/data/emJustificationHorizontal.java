package org.colorcoding.ibas.importexport.data;

import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.common.Value;
import org.colorcoding.ibas.importexport.MyConfiguration;

/**
 * 水平对齐
 * 
 * @author Niuren.Zhu
 *
 */
@XmlType(namespace = MyConfiguration.NAMESPACE_BO)
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
