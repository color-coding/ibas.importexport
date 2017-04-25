package org.colorcoding.ibas.importexport.repository;

import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.data.FileData;
import org.colorcoding.ibas.bobas.repository.IBORepositoryApplication;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.IDataExportTemplate;

/**
 * ImportExport仓库应用
 */
public interface IBORepositoryImportExportApp extends IBORepositoryApplication {
	// --------------------------------------------------------------------------------------------//

	/**
	 * 导入数据
	 * 
	 * @param data
	 *            数据
	 * @return 操作结果
	 */
	IOperationResult<String> importData(FileData data);

	// --------------------------------------------------------------------------------------------//
	/**
	 * 查询-数据导出模板
	 * 
	 * @param criteria
	 *            查询
	 * @return 操作结果
	 */
	IOperationResult<IDataExportTemplate> fetchDataExportTemplate(ICriteria criteria);

	/**
	 * 保存-数据导出模板
	 * 
	 * @param bo
	 *            对象实例
	 * @return 操作结果
	 */
	IOperationResult<IDataExportTemplate> saveDataExportTemplate(IDataExportTemplate bo);

	// --------------------------------------------------------------------------------------------//

}
