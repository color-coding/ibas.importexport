/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
/// <reference path="../../3rdparty/ibas/index.d.ts" />
/// <reference path="../../3rdparty/openui5/index.d.ts" />
/// <reference path="../../index.d.ts" />
/// <reference path="./dataexport/index.ts" />
/// <reference path="./dataexporttemplate/index.ts" />
/// <reference path="./dataimport/index.ts" />
namespace importexport {
    export namespace ui {
        /**
         * 视图导航
         */
        export class Navigation extends ibas.ViewNavigation {

            /**
             * 创建实例
             * @param id 应用id
             */
            protected newView(id: string): ibas.IView {
                let view: ibas.IView = null;
                switch (id) {
                    case app.DataExportTemplateListApp.APPLICATION_ID:
                        view = new c.DataExportTemplateListView();
                        break;
                    case app.DataExportTemplateChooseApp.APPLICATION_ID:
                        view = new c.DataExportTemplateChooseView();
                        break;
                    case app.DataExportTemplateEditApp.APPLICATION_ID:
                        view = new c.DataExportTemplateEditView();
                        break;
                    case app.DataExportService.APPLICATION_ID:
                        view = new c.DataExportServiceView();
                        break;
                    case app.DataExportApp.APPLICATION_ID:
                        view = new c.DataExportView();
                        break;
                    case app.DataImportApp.APPLICATION_ID:
                        view = new c.DataImportView();
                        break;
                    default:
                        break;
                }
                return view;
            }
        }
    }
}