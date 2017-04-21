/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import * as bo from "./bo/index";
import { IBORepositoryImportExport } from "../api/index";
import { DataConverterOnline, DataConverterOffline } from "./DataConverters";

/** <%Domain.Name%> 业务仓库 */
export class BORepositoryImportExport extends ibas.BORepositoryApplication implements IBORepositoryImportExport {

    /** 创建此模块的后端与前端数据的转换者 */
    protected createConverter(): ibas.IDataConverter {
        if (this.offline) {
            // 离线状态
            return new DataConverterOffline();
        } else {
            return new DataConverterOnline();

        }
    }

    /**
     * 查询 数据导出模板
     * @param fetcher 查询者
     */
    fetchDataExportTemplate(fetcher: ibas.FetchCaller<bo.DataExportTemplate>):void {
        super.fetch(bo.DataExportTemplate.name, fetcher);
    }
    /**
     * 保存 数据导出模板
     * @param saver 保存者
     */
    saveDataExportTemplate(saver: ibas.SaveCaller<bo.DataExportTemplate>):void {
        super.save(bo.DataExportTemplate.name, saver);
    }

}
