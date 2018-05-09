/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {
        export class ExportTemplateFunc extends ibas.ModuleFunction {

            /** 功能标识 */
            static FUNCTION_ID = "8ad12057-607c-47ed-8285-eaac58ea4289";
            /** 功能名称 */
            static FUNCTION_NAME = "importexport_func_exporttemplate";
            /** 构造函数 */
            constructor() {
                super();
                this.id = ExportTemplateFunc.FUNCTION_ID;
                this.name = ExportTemplateFunc.FUNCTION_NAME;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 默认功能 */
            default(): ibas.IApplication<ibas.IView> {
                let app: ExportTemplateListApp = new ExportTemplateListApp();
                app.navigation = this.navigation;
                return app;
            }
        }
    }
}
