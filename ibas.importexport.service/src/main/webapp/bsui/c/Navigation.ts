/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
/// <reference path="../../index.d.ts" />
/// <reference path="./exporttemplate/index.ts" />
/// <reference path="./dataexport/index.ts" />
/// <reference path="./dataimport/index.ts" />
/// <reference path="./dataprint/index.ts" />
/// <reference path="./dataparsing/index.ts" />
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
                    case app.ExportTemplateListApp.APPLICATION_ID:
                        view = new c.ExportTemplateListView();
                        break;
                    case app.ExportTemplateChooseApp.APPLICATION_ID:
                        view = new c.ExportTemplateChooseView();
                        break;
                    case app.ExportTemplateEditApp.APPLICATION_ID:
                        view = new c.ExportTemplateEditView();
                        break;
                    case app.DataExportService.APPLICATION_ID:
                        view = new c.DataExportServiceView();
                        break;
                    case app.DataTableExportService.APPLICATION_ID:
                        view = new c.DataExportServiceView();
                        break;
                    case app.DataExportApp.APPLICATION_ID:
                        view = new c.DataExportView();
                        break;
                    case app.DataImportApp.APPLICATION_ID:
                        view = new c.DataImportView();
                        break;
                    case app.DataPrintService.APPLICATION_ID:
                        view = new c.DataPrintServiceView();
                        break;
                    case app.DataTablePrintService.APPLICATION_ID:
                        view = new c.DataPrintServiceView();
                        break;
                    case app.FileParsingService.APPLICATION_ID:
                        view = new c.FileParsingServiceView();
                        break;
                    case app.ViewExportApp.APPLICATION_ID:
                        view = new c.ViewExportView();
                        break;
                    case app.ExportRecordService.APPLICATION_ID:
                        view = new c.ExportRecordServiceView();
                        break;
                    default:
                        break;
                }
                return view;
            }
        }
    }
}