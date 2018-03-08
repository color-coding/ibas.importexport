/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {

        export class DataExportFunc extends ibas.ModuleFunction {

            /** 功能标识 */
            static FUNCTION_ID = "2fc3126c-938d-42d0-baaa-4cd5b8293745";
            /** 功能名称 */
            static FUNCTION_NAME = "importexport_func_dataexport";
            /** 构造函数 */
            constructor() {
                super();
                this.id = DataExportFunc.FUNCTION_ID;
                this.name = DataExportFunc.FUNCTION_NAME;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 默认功能 */
            default(): ibas.IApplication<ibas.IView> {
                let app: DataExportApp = new DataExportApp();
                app.navigation = this.navigation;
                return app;
            }
        }
    }
}