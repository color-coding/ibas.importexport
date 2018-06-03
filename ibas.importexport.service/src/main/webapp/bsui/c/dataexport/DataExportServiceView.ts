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
            export class DataExportServiceView extends ibas.BODialogView implements app.IDataExportServiceView {
                /** 导出数据，参数1：使用的模板 */
                exportDataEvent: Function;
                /** 绘制视图 */
                draw(): any {
                    let that: this = this;
                    this.table = new sap.ui.table.Table("", {
                        enableSelectAll: false,
                        selectionBehavior: sap.ui.table.SelectionBehavior.Row,
                        selectionMode: sap.ui.table.SelectionMode.MultiToggle,
                        visibleRowCount: 5,
                        rows: "{/}",
                        columns: [
                            new sap.ui.table.Column("", {
                                label: ibas.i18n.prop("importexport_export_mode_name"),
                                template: new sap.m.Text("", {
                                    wrapping: false,
                                }).bindProperty("text", {
                                    path: "name"
                                })
                            }),
                            new sap.ui.table.Column("", {
                                label: ibas.i18n.prop("importexport_export_mode_description"),
                                template: new sap.m.Text("", {
                                    wrapping: false
                                }).bindProperty("text", {
                                    path: "description"
                                })
                            }),
                        ]
                    });
                    // 调整选择样式风格
                    openui5.utils.changeSelectionStyle(this.table, ibas.emChooseType.SINGLE);
                    return new sap.m.Dialog("", {
                        title: this.title,
                        type: sap.m.DialogType.Standard,
                        state: sap.ui.core.ValueState.None,
                        stretchOnPhone: true,
                        horizontalScrolling: true,
                        verticalScrolling: true,
                        content: [this.table],
                        buttons: [
                            new sap.m.Button("", {
                                text: ibas.i18n.prop("importexport_export"),
                                type: sap.m.ButtonType.Transparent,
                                press: function (): void {
                                    that.fireViewEvents(that.exportDataEvent,
                                        // 获取表格选中的对象
                                        openui5.utils.getSelecteds<bo.IDataExporter>(that.table).firstOrDefault()
                                    );
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
                    });
                }
                private table: sap.ui.table.Table;
                /** 显示数据导出者 */
                showExporters(exporters: bo.IDataExporter[]): void {
                    this.table.setModel(new sap.ui.model.json.JSONModel(exporters));
                }
                /** 显示结果 */
                showResluts(results: bo.IDataExportResult[]): void {
                    for (let result of results) {
                        if (result instanceof bo.DataExportResultString) {
                            jQuery.sap.require("sap.ui.core.util.File");
                            let content: any = result.content,
                                fileName: string = result.fileName.substring(0, result.fileName.lastIndexOf(".")),
                                extension: string = result.fileName.substring(result.fileName.lastIndexOf(".") + 1),
                                mimeType: string = extension;
                            sap.ui.core.util.File.save(content, fileName, extension, mimeType, result.fileCharset);
                        } else if (result instanceof bo.DataExportResultBlob) {
                            ibas.files.save(result.content, result.fileName);
                        }
                    }
                }
            }
        }
    }
}