package org.colorcoding.ibas.importexport.repository;

import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.data.FileData;
import org.colorcoding.ibas.bobas.data.KeyText;
import org.colorcoding.ibas.bobas.repository.IBORepositoryApplication;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;

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

	/**
	 * 导入数据
	 * 
	 * @param data
	 *            数据
	 * @param update
	 *            是否更新
	 * @return
	 */
	IOperationResult<String> importData(FileData data, boolean update);

	/**
	 * 导出数据
	 * 
	 * @param criteria
	 *            查询
	 * @return 操作结果
	 */
	IOperationResult<FileData> exportData(ICriteria criteria);

	/**
	 * 获取业务对象schema
	 * 
	 * @param boCode
	 *            业务对象编码
	 * @param type
	 *            schema类型
	 * @return 操作结果
	 */
	IOperationResult<String> schema(String boCode, String type);

	/**
	 * 获取转换者名称
	 * 
	 * @param criteria
	 *            查询
	 * @return 操作结果
	 */
	IOperationResult<KeyText> fetchTransformer(ICriteria criteria);

	// --------------------------------------------------------------------------------------------//
	/**
	 * 查询-数据导出模板
	 * 
	 * @param criteria
	 *            查询
	 * @return 操作结果
	 */
	IOperationResult<IExportTemplate> fetchExportTemplate(ICriteria criteria);

	/**
	 * 保存-数据导出模板
	 * 
	 * @param bo
	 *            对象实例
	 * @return 操作结果
	 */
	IOperationResult<IExportTemplate> saveExportTemplate(IExportTemplate bo);

	// --------------------------------------------------------------------------------------------//

}
