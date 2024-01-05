package org.colorcoding.ibas.importexport.data;

import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.mapping.Value;
import org.colorcoding.ibas.importexport.MyConfiguration;

/**
 * 数据源类型
 * 
 * @author Niuren.Zhu
 *
 */
@XmlType(namespace = MyConfiguration.NAMESPACE_BO)
public enum emDataSourceType {
	/**
	 * 文本
	 */
	@Value("T")
	TEXT,
	/**
	 * 路径
	 */
	@Value("P")
	PATH,
	/**
	 * 查询
	 */
	@Value("Q")
	QUERY,
}
