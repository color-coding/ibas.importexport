package org.colorcoding.ibas.importexport.service.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.cxf.WebServicePath;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.repository.BORepositoryImportExport;

/**
 * ImportExport 数据服务JSON
 */
@WebService
@WebServicePath("data")
public class DataService extends BORepositoryImportExport {
	// --------------------------------------------------------------------------------------------//
	/**
	 * 获取业务对象schema
	 * 
	 * @param boCode
	 *            业务对象编码
	 * @param type
	 *            schema类型
	 * @param token
	 *            口令
	 * @return 操作结果
	 */
	@WebMethod
	public OperationResult<String> schema(@WebParam(name = "boCode") String boCode,
			@WebParam(name = "type") String type, @WebParam(name = "token") String token) {
		return super.schema(boCode, type, token);
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
	@WebMethod
	public OperationResult<ExportTemplate> fetchExportTemplate(@WebParam(name = "criteria") Criteria criteria,
			@WebParam(name = "token") String token) {
		return super.fetchExportTemplate(criteria, token);
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
	@WebMethod
	public OperationResult<ExportTemplate> saveExportTemplate(@WebParam(name = "bo") ExportTemplate bo,
			@WebParam(name = "token") String token) {
		return super.saveExportTemplate(bo, token);
	}

	// --------------------------------------------------------------------------------------------//

}
