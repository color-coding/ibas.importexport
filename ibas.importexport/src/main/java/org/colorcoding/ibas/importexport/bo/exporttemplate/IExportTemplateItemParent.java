package org.colorcoding.ibas.importexport.bo.exporttemplate;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;

/**
 * 输出模板项目-父项
 * 
 * @author Niuren.Zhu
 *
 */
public interface IExportTemplateItemParent extends IBusinessObject {
	/**
	 * 获取-编号
	 * 
	 * @return 值
	 */
	Integer getObjectKey();

	/**
	 * 获取-行号
	 * 
	 * @return 值
	 */
	Integer getLineId();
}
