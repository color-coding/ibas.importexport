/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import { DataExportTemplateListApp } from "./DataExportTemplateListApp";

export class DataExportTemplateFunc extends ibas.ModuleFunction {

    /** 功能标识 */
    static FUNCTION_ID = "c1ab26cc-bd69-4b0f-b766-755c8dcfce5b";
    /** 功能名称 */
    static FUNCTION_NAME = "importexport_func_dataexporttemplate";
    /** 根文件名称 */
    static ROOT_FILE_NAME: string = "importexport/index";
    /** 构造函数 */
    constructor() {
        super();
        this.id = DataExportTemplateFunc.FUNCTION_ID;
        this.name = DataExportTemplateFunc.FUNCTION_NAME;
        this.description = ibas.i18n.prop(this.name);
    }
    /** 默认功能 */
    default(): ibas.IApplication<ibas.IView> {
        let app: DataExportTemplateListApp = new DataExportTemplateListApp();
        app.navigation = this.navigation;
        return app;
    }
}
