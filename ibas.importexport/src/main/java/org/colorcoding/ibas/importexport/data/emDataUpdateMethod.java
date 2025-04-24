package org.colorcoding.ibas.importexport.data;

import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.common.Value;
import org.colorcoding.ibas.importexport.MyConfiguration;

/**
 * 数据更新方式
 * 
 * @author Niuren.Zhu
 *
 */
@XmlType(namespace = MyConfiguration.NAMESPACE_BO)
public enum emDataUpdateMethod {
	/**
	 * 跳过
	 */
	@Value("S")
	SKIP,
	/**
	 * 替换（旧数据删除）
	 */
	@Value("R")
	REPLACE,
	/**
	 * 修改（修改旧数据）
	 */
	@Value("M")
	MODIFY,
}
