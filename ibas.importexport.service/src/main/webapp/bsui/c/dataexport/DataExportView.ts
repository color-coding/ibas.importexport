/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import { utils } from "openui5/typings/ibas.utils";
import * as bo from "../../../borep/bo/index";
import { IDataExportView } from "../../../bsapp/dataexport/index";

/**
 * 视图-数据导出
 */
export class DataExportView extends ibas.BOView implements IDataExportView {
    /** 选择业务对象 */
    chooseBusinessObjectEvent: Function;
    /** 选择导出模板 */
    chooseTemplateEvent: Function;
    /** 导出 */
    exportEvent: Function;
    /** 绘制视图 */
    darw(): any {
        let that: this = this;
        this.form = new sap.ui.layout.form.SimpleForm("", {
            content: [
                new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_export_criteria") }),
                new sap.m.Label("", { text: ibas.i18n.prop("importexport_export_bo") }),
                new sap.m.Input("", {
                    showValueHelp: true,
                    valueHelpRequest: function (): void {
                        that.fireViewEvents(that.chooseBusinessObjectEvent);
                    }
                }).bindProperty("value", {
                    path: "/boCode"
                }),
                new sap.m.Label("", { text: ibas.i18n.prop("importexport_export_template") }),
                new sap.m.Input("", {
                    showValueHelp: true,
                    valueHelpRequest: function (): void {
                        that.fireViewEvents(that.chooseBusinessObjectEvent);
                    }
                }).bindProperty("value", {
                    path: "/remarks"
                }),
                new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_export_result") }),
                new sap.ui.table.Table("", {
                    enableSelectAll: false,
                    visibleRowCount: 10,
                    visibleRowCountMode: sap.ui.table.VisibleRowCountMode.Interactive,
                    rows: "{/}",
                    columns: [
                        new sap.ui.table.Column("", {
                            label: ibas.i18n.prop("importexport_businessobject_key"),
                            template: new sap.m.Text("", {
                                wrapping: false
                            }).bindProperty("text", {
                                path: ""
                            })
                        }),
                    ]
                })
            ]
        });
        this.page = new sap.m.Page("", {
            showHeader: false,
            subHeader: new sap.m.Bar("", {
                contentLeft: [
                    new sap.m.Button("", {
                        text: ibas.i18n.prop("importexport_export"),
                        type: sap.m.ButtonType.Transparent,
                        icon: "sap-icon://toaster-up",
                        press: function (): void {
                            let fileData: FormData = new FormData();
                            fileData.append("file", (<any>document.getElementsByName(that.uploader.getName()).item(0)).files[0]);
                            fileData.append("name", that.uploader.getName());
                            that.fireViewEvents(that.exportEvent, fileData);
                        }
                    })
                ]
            }),
            content: [this.form]
        });
        this.id = this.page.getId();
        return this.page;
    }
    private page: sap.m.Page;
    private form: sap.ui.layout.form.SimpleForm;
    private uploader: sap.ui.unified.FileUploader;
    private table: sap.ui.table.Table;
    /** 显示结果 */
    showResults(results: any[]): void {
        // 显示结果
    }
    /** 显示查询 */
    showCriteria(criteria: ibas.ICriteria): void {
        // 显示查询
    }
}
