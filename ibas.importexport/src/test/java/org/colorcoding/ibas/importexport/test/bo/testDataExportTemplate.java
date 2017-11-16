package org.colorcoding.ibas.importexport.test.bo;

import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.DataExportTemplate;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.IDataExportTemplateItem;
import org.colorcoding.ibas.importexport.repository.BORepositoryImportExport;
import org.colorcoding.ibas.importexport.repository.IBORepositoryImportExportApp;

import junit.framework.TestCase;

/**
 * 数据导出模板 测试
 * 
 */
public class testDataExportTemplate extends TestCase {
	/**
	 * 获取连接口令
	 */
	String getToken() {
		return "";
	}

	/**
	 * 基本项目测试
	 * 
	 * @throws Exception
	 */
	public void testBasicItems() throws Exception {
		DataExportTemplate bo = new DataExportTemplate();
		// 测试属性赋值

		// 测试数据导出模板-项
		IDataExportTemplateItem dataexporttemplateitem = bo.getDataExportTemplateItems().create();
		System.out.println(String.format("new item: %s", dataexporttemplateitem.toString()));
		// 测试属性赋值

		// 测试对象的保存和查询
		IOperationResult<?> operationResult = null;
		ICriteria criteria = null;
		IBORepositoryImportExportApp boRepository = new BORepositoryImportExport();
		// 设置用户口令
		boRepository.setUserToken(this.getToken());

		// 测试保存
		operationResult = boRepository.saveDataExportTemplate(bo);
		assertEquals(operationResult.getMessage(), operationResult.getResultCode(), 0);
		DataExportTemplate boSaved = (DataExportTemplate) operationResult.getResultObjects().firstOrDefault();

		// 测试查询
		criteria = boSaved.getCriteria();
		criteria.setResultCount(10);
		operationResult = boRepository.fetchDataExportTemplate(criteria);
		assertEquals(operationResult.getMessage(), operationResult.getResultCode(), 0);

	}

}
