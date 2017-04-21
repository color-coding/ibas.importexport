package org.colorcoding.ibas.importexport.repository;

import org.colorcoding.ibas.bobas.common.*;
import org.colorcoding.ibas.bobas.repository.*;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.*;

/**
* ImportExport仓库应用
*/
public interface IBORepositoryImportExportApp extends IBORepositoryApplication {

    //--------------------------------------------------------------------------------------------//
    /**
     * 查询-数据导出模板
     * @param criteria 查询
     * @return 操作结果
     */
    IOperationResult<IDataExportTemplate> fetchDataExportTemplate(ICriteria criteria);

    /**
     * 保存-数据导出模板
     * @param bo 对象实例
     * @return 操作结果
     */
    IOperationResult<IDataExportTemplate> saveDataExportTemplate(IDataExportTemplate bo);

    //--------------------------------------------------------------------------------------------//

}
