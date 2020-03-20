/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace ui {
        export namespace c {
            /**
             * 视图-数据导入
             */
            export class DataImportView extends ibas.View implements app.IDataImportView {
                /** 导入 */
                importEvent: Function;
                /** 绘制视图 */
                draw(): any {
                    let that: this = this;
                    let check: sap.m.CheckBox, uploader: sap.ui.unified.FileUploader;
                    let form: sap.ui.layout.form.SimpleForm = new sap.ui.layout.form.SimpleForm("", {
                        editable: true,
                        content: [
                            new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_import_data") }),
                            uploader = new sap.ui.unified.FileUploader("", {
                                name: ibas.strings.format("FILE_{0}", ibas.uuids.random().toUpperCase()),
                                width: "100%",
                                placeholder: ibas.i18n.prop("importexport_please_choose_file"),
                            }),
                            check = new sap.m.CheckBox("", {
                                width: "100%",
                                selected: false,
                                text: ibas.i18n.prop("importexport_update_exists_data"),
                                textAlign: sap.ui.core.TextAlign.Right,
                            }),
                            new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_import_result") }),
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
                            })
                        ]
                    });
                    return new sap.m.Page("", {
                        showHeader: false,
                        subHeader: new sap.m.Bar("", {
                            contentLeft: [
                                new sap.m.Button("", {
                                    text: ibas.i18n.prop("importexport_import"),
                                    type: sap.m.ButtonType.Transparent,
                                    icon: "sap-icon://toaster-up",
                                    press: function (): void {
                                        let elements: NodeListOf<HTMLElement> = document.getElementsByName(uploader.getName());
                                        if (ibas.objects.isNull(elements) || elements.length === 0) {
                                            return;
                                        }
                                        let element: HTMLInputElement = <HTMLInputElement>elements[0];
                                        if (ibas.objects.isNull(element.files) || element.files.length === 0) {
                                            return;
                                        }
                                        let fileData: FormData = new FormData();
                                        fileData.append("file", element.files[0]);
                                        fileData.append("update", check.getSelected().toString());
                                        that.fireViewEvents(that.importEvent, fileData);
                                    }
                                })
                            ]
                        }),
                        content: [
                            form
                        ]
                    });
                }
                private table: sap.ui.table.Table;
                /** 显示结果 */
                showResults(results: any[]): void {
                    this.table.setModel(new sap.ui.model.json.JSONModel(results));
                }
            }
        }
    }
}