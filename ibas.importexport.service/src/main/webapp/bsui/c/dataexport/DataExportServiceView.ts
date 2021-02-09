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
             * 数据服务视图
             */
            export class DataExportServiceView extends ibas.DialogView implements app.IDataExportServiceView {
                /** 导出数据，参数1：使用的模板 */
                exportDataEvent: Function;
                /** 绘制视图 */
                draw(): any {
                    let that: this = this;
                    this.table = new sap.extension.table.Table("", {
                        enableSelectAll: false,
                        visibleRowCount: 5,
                        chooseType: ibas.emChooseType.SINGLE,
                        rows: "{/rows}",
                        columns: [
                            new sap.extension.table.Column("", {
                                label: ibas.i18n.prop("importexport_export_mode_name"),
                                template: new sap.extension.m.Text("", {
                                }).bindProperty("bindingValue", {
                                    path: "name",
                                    type: new sap.extension.data.Alphanumeric()
                                })
                            }),
                            new sap.extension.table.Column("", {
                                label: ibas.i18n.prop("importexport_export_mode_description"),
                                width: "20rem",
                                template: new sap.extension.m.Text("", {
                                }).bindProperty("bindingValue", {
                                    path: "description",
                                    type: new sap.extension.data.Alphanumeric()
                                })
                            }),
                        ]
                    });
                    return new sap.m.Dialog("", {
                        title: this.title,
                        type: sap.m.DialogType.Standard,
                        state: sap.ui.core.ValueState.None,
                        horizontalScrolling: true,
                        verticalScrolling: true,
                        content: [
                            this.table
                        ],
                        buttons: [
                            new sap.m.Button("", {
                                text: ibas.i18n.prop("importexport_export"),
                                type: sap.m.ButtonType.Transparent,
                                press: function (): void {
                                    that.fireViewEvents(that.exportDataEvent, that.table.getSelecteds().firstOrDefault());
                                }
                            }),
                            new sap.m.Button("", {
                                text: ibas.i18n.prop("shell_exit"),
                                type: sap.m.ButtonType.Transparent,
                                press: function (): void {
                                    that.fireViewEvents(that.closeEvent);
                                }
                            }),
                        ],
                    }).addStyleClass("sapUiNoContentPadding");
                }
                private table: sap.extension.table.Table;
                /** 显示数据导出者 */
                showExporters(exporters: bo.IDataExporter[]): void {
                    this.table.setModel(new sap.extension.model.JSONModel({ rows: exporters }));
                }
                /** 显示结果 */
                showResluts(results: bo.IDataExportResult[]): void {
                    for (let result of results) {
                        if (result instanceof bo.DataExportResultString) {
                            jQuery.sap.require("sap.ui.core.util.File");
                            let content: any = result.content,
                                fileName: string = result.fileName.substring(0, result.fileName.lastIndexOf(".")),
                                extension: string = result.fileName.substring(result.fileName.lastIndexOf(".") + 1);
                            sap.ui.core.util.File.save(content, fileName, extension, "text/plain", "utf-8");
                        } else if (result instanceof bo.DataExportResultBlob) {
                            ibas.files.save(result.content, result.fileName);
                        }
                    }
                }
            }
        }
    }
}