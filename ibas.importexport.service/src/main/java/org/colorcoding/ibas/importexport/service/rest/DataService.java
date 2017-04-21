package org.colorcoding.ibas.importexport.service.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.colorcoding.ibas.bobas.common.*;
import org.colorcoding.ibas.importexport.repository.*;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.*;

/**
* ImportExport 数据服务JSON
*/
@Path("data")
public class DataService extends BORepositoryImportExport {

    //--------------------------------------------------------------------------------------------//
    /**
     * 查询-数据导出模板
     * @param criteria 查询
     * @param token 口令
     * @return 操作结果
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("fetchDataExportTemplate")
    public OperationResult<DataExportTemplate> fetchDataExportTemplate(Criteria criteria, @QueryParam("token") String token) {
        return super.fetchDataExportTemplate(criteria, token);
    }

    /**
     * 保存-数据导出模板
     * @param bo 对象实例
     * @param token 口令
     * @return 操作结果
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("saveDataExportTemplate")
    public OperationResult<DataExportTemplate> saveDataExportTemplate(DataExportTemplate bo, @QueryParam("token") String token) {
        return super.saveDataExportTemplate(bo, token);
    }

    //--------------------------------------------------------------------------------------------//

}
