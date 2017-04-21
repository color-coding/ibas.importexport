package org.colorcoding.ibas.importexport.service.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.colorcoding.ibas.bobas.common.*;
import org.colorcoding.ibas.bobas.cxf.WebServicePath;
import org.colorcoding.ibas.importexport.repository.*;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.*;

/**
* ImportExport 数据服务JSON
*/
@WebService
@WebServicePath("data")
public class DataService extends BORepositoryImportExport {

    //--------------------------------------------------------------------------------------------//
    /**
     * 查询-数据导出模板
     * @param criteria 查询
     * @param token 口令
     * @return 操作结果
     */
    @WebMethod
    public OperationResult<DataExportTemplate> fetchDataExportTemplate(@WebParam(name = "criteria") Criteria criteria, @WebParam(name = "token") String token) {
        return super.fetchDataExportTemplate(criteria, token);
    }

    /**
     * 保存-数据导出模板
     * @param bo 对象实例
     * @param token 口令
     * @return 操作结果
     */
    @WebMethod
    public OperationResult<DataExportTemplate> saveDataExportTemplate(@WebParam(name = "bo") DataExportTemplate bo, @WebParam(name = "token") String token) {
        return super.saveDataExportTemplate(bo, token);
    }

    //--------------------------------------------------------------------------------------------//

}
