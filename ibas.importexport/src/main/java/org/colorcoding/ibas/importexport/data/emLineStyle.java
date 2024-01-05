package org.colorcoding.ibas.importexport.data;

import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.mapping.Value;
import org.colorcoding.ibas.importexport.MyConfiguration;

/**
 * 框线样式
 * 
 * @author Niuren.Zhu
 *
 */
@XmlType(namespace = MyConfiguration.NAMESPACE_BO)
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
