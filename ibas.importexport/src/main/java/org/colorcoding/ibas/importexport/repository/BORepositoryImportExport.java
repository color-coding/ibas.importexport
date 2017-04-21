package org.colorcoding.ibas.importexport.repository;

import org.colorcoding.ibas.bobas.common.*;
import org.colorcoding.ibas.bobas.ownership.*;
import org.colorcoding.ibas.bobas.repository.*;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.*;

/**
* ImportExport仓库
*/
@PermissionGroup("ImportExport")
public class BORepositoryImportExport extends BORepositoryServiceApplication implements IBORepositoryImportExportSvc, IBORepositoryImportExportApp {

    //--------------------------------------------------------------------------------------------//
    /**
     * 查询-数据导出模板
     * @param criteria 查询
     * @param token 口令
     * @return 操作结果
     */
    public OperationResult<DataExportTemplate> fetchDataExportTemplate(ICriteria criteria, String token) {
        return super.fetch(criteria, token, DataExportTemplate.class);
    }

    /**
     * 查询-数据导出模板（提前设置用户口令）
     * @param criteria 查询
     * @return 操作结果
     */
    public IOperationResult<IDataExportTemplate> fetchDataExportTemplate(ICriteria criteria) {
        return new OperationResult<IDataExportTemplate>(this.fetchDataExportTemplate(criteria, this.getUserToken()));
    }

    /**
     * 保存-数据导出模板
     * @param bo 对象实例
     * @param token 口令
     * @return 操作结果
     */
    public OperationResult<DataExportTemplate> saveDataExportTemplate(DataExportTemplate bo, String token) {
        return super.save(bo, token);
    }

    /**
     * 保存-数据导出模板（提前设置用户口令）
     * @param bo 对象实例
     * @return 操作结果
     */
    public IOperationResult<IDataExportTemplate> saveDataExportTemplate(IDataExportTemplate bo) {
        return new OperationResult<IDataExportTemplate>(this.saveDataExportTemplate((DataExportTemplate) bo, this.getUserToken()));
    }

    //--------------------------------------------------------------------------------------------//

}
