package org.colorcoding.ibas.importexport.repository;

import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.core.RepositoryException;
import org.colorcoding.ibas.bobas.data.FileData;
import org.colorcoding.ibas.bobas.ownership.PermissionGroup;
import org.colorcoding.ibas.bobas.repository.BORepositoryServiceApplication;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.DataExportTemplate;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.IDataExportTemplate;

/**
 * ImportExport仓库
 */
@PermissionGroup("ImportExport")
public class BORepositoryImportExport extends BORepositoryServiceApplication
		implements IBORepositoryImportExportSvc, IBORepositoryImportExportApp {

	// --------------------------------------------------------------------------------------------//
	/**
	 * 开启事务
	 */
	public boolean beginTransaction() throws RepositoryException {
		return super.beginTransaction();
	}

	/**
	 * 提交事务
	 */
	public void commitTransaction() throws RepositoryException {
		super.commitTransaction();
	}

	/**
	 * 回滚事务
	 */
	public void rollbackTransaction() throws RepositoryException {
		super.rollbackTransaction();
	}

	/**
	 * 导入数据
	 * 
	 * @param data
	 *            数据
	 * @param token
	 *            口令
	 * @return 操作结果
	 */
	public OperationResult<String> importData(FileData data, String token) {
		OperationResult<String> opRslt = null;
		boolean myTrans = false;
		try {
			opRslt = new OperationResult<String>();
			myTrans = this.beginTransaction();
			opRslt.addResultObjects(new DataExportTemplate());
			opRslt.addResultObjects(new DataExportTemplate());
			opRslt.addResultObjects(new DataExportTemplate());
			if (myTrans) {
				this.commitTransaction();
			}
		} catch (Exception e) {
			opRslt = new OperationResult<String>(e);
			if (myTrans) {
				try {
					this.rollbackTransaction();
				} catch (RepositoryException e1) {
					opRslt.setMessage(opRslt.getMessage() + "&" + e1.getMessage());
				}
			}
		}
		return opRslt;
	}

	/**
	 * 导入数据
	 * 
	 * @param data
	 *            数据
	 * @return 操作结果
	 */
	public IOperationResult<String> importData(FileData data) {
		return this.importData(data, this.getUserToken());
	}

	// --------------------------------------------------------------------------------------------//
	/**
	 * 查询-数据导出模板
	 * 
	 * @param criteria
	 *            查询
	 * @param token
	 *            口令
	 * @return 操作结果
	 */
	public OperationResult<DataExportTemplate> fetchDataExportTemplate(ICriteria criteria, String token) {
		return super.fetch(criteria, token, DataExportTemplate.class);
	}

	/**
	 * 查询-数据导出模板（提前设置用户口令）
	 * 
	 * @param criteria
	 *            查询
	 * @return 操作结果
	 */
	public IOperationResult<IDataExportTemplate> fetchDataExportTemplate(ICriteria criteria) {
		return new OperationResult<IDataExportTemplate>(this.fetchDataExportTemplate(criteria, this.getUserToken()));
	}

	/**
	 * 保存-数据导出模板
	 * 
	 * @param bo
	 *            对象实例
	 * @param token
	 *            口令
	 * @return 操作结果
	 */
	public OperationResult<DataExportTemplate> saveDataExportTemplate(DataExportTemplate bo, String token) {
		return super.save(bo, token);
	}

	/**
	 * 保存-数据导出模板（提前设置用户口令）
	 * 
	 * @param bo
	 *            对象实例
	 * @return 操作结果
	 */
	public IOperationResult<IDataExportTemplate> saveDataExportTemplate(IDataExportTemplate bo) {
		return new OperationResult<IDataExportTemplate>(
				this.saveDataExportTemplate((DataExportTemplate) bo, this.getUserToken()));
	}

	// --------------------------------------------------------------------------------------------//

}
