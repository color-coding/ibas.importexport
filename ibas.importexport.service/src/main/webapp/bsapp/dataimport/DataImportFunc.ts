/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import { DataImportApp } from "./DataImportApp";

export class DataImportFunc extends ibas.ModuleFunction {

    /** 功能标识 */
    static FUNCTION_ID = "d48048a9-751a-4cf8-adc9-4fd0d3f9e74c";
    /** 功能名称 */
    static FUNCTION_NAME = "importexport_func_dataimport";
    /** 根文件名称 */
    static ROOT_FILE_NAME: string = "importexport/index";
    /** 构造函数 */
    constructor() {
        super();
        this.id = DataImportFunc.FUNCTION_ID;
        this.name = DataImportFunc.FUNCTION_NAME;
        this.description = ibas.i18n.prop(this.name);
    }
    /** 默认功能 */
    default(): ibas.IApplication<ibas.IView> {
        let app: DataImportApp = new DataImportApp();
        app.navigation = this.navigation;
        return app;
    }
}
