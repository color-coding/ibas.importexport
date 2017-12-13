/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import * as openui5 from "openui5/index";
import * as bo from "../../../borep/bo/index";
import { IDataImportView } from "../../../bsapp/dataimport/index";

/**
 * 视图-数据导入
 */
export class DataImportView extends ibas.BOView implements IDataImportView {
    /** 导入 */
    importEvent: Function;
    /** 绘制视图 */
    darw(): any {
        let that: this = this;
        this.form = new sap.ui.layout.form.SimpleForm("", {
            content: [
            ]
        });
        this.form.addContent(new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_import_data") }));
        this.uploader = new sap.ui.unified.FileUploader("", {
            name: "file",
            width: "100%",
            placeholder: ibas.i18n.prop("importexport_please_choose_file"),
        });
        this.form.addContent(this.uploader);
        this.check = new sap.m.CheckBox("", {
            width: "100%",
            selected: false,
            text: ibas.i18n.prop("importexport_update_exists_data"),
            textAlign: sap.ui.core.TextAlign.Right,
        });
        this.form.addContent(this.check);
        this.form.addContent(new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_import_result") }));
        this.table = new sap.ui.table.Table("", {
            enableSelectAll: false,
            selectionBehavior: sap.ui.table.SelectionBehavior.Row,
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
        });
        this.form.addContent(this.table);
        this.page = new sap.m.Page("", {
            showHeader: false,
            subHeader: new sap.m.Bar("", {
                contentLeft: [
                    new sap.m.Button("", {
                        text: ibas.i18n.prop("importexport_import"),
                        type: sap.m.ButtonType.Transparent,
                        icon: "sap-icon://toaster-up",
                        press: function (): void {
                            let elements: NodeListOf<HTMLElement> = document.getElementsByName(that.uploader.getName());
                            if (ibas.objects.isNull(elements) || elements.length === 0) {
                                return;
                            }
                            let element: any = elements[0];
                            if (ibas.objects.isNull(element.files) || element.files.length === 0) {
                                return;
                            }
                            let fileData: FormData = new FormData();
                            fileData.append("file", element.files[0]);
                            fileData.append("update", that.check.getSelected().toString());
                            that.fireViewEvents(that.importEvent, fileData);
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
    private check: sap.m.CheckBox;
    /** 显示结果 */
    showResults(results: any[]): void {
        this.table.setModel(new sap.ui.model.json.JSONModel(results));
    }
}
