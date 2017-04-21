/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import {
    FetchCaller,
    SaveCaller
} from "ibas/index";
import * as bo from "./bo/index"

/** ImportExport 业务仓库 */
export interface IBORepositoryImportExport {

    /**
     * 查询 数据导出模板
     * @param fetcher 查询者
     */
    fetchDataExportTemplate(fetcher: FetchCaller<bo.IDataExportTemplate>);
    /**
     * 保存 数据导出模板
     * @param saver 保存者
     */
    saveDataExportTemplate(saver: SaveCaller<bo.IDataExportTemplate>);


}
