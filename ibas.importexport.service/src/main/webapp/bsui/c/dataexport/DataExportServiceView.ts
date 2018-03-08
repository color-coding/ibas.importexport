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
                /** 导出原始数据 */
                exportRawDataEvent: Function;
                /** 导出数据，参数1：使用的模板 */
                exportDataEvent: Function;
                /** 绘制工具条 */
                drawBars(): any {
                    let that: this = this;
                    return [
                        new sap.m.Button("", {
                            text: ibas.i18n.prop("importexport_export"),
                            type: sap.m.ButtonType.Transparent,
                            press: function (): void {
                                that.fireViewEvents(that.exportDataEvent,
                                    // 获取表格选中的对象
                                    openui5.utils.getSelecteds<app.IDataExportMode>(that.table).firstOrDefault()
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
                    ];
                }
                /** 绘制视图 */
                draw(): any {
                    let that: this = this;
                    this.table = new sap.ui.table.Table("", {
                        enableSelectAll: false,
                        selectionBehavior: sap.ui.table.SelectionBehavior.Row,
                        selectionMode: sap.ui.table.SelectionMode.Single,
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
                    return new sap.m.Dialog("", {
                        title: this.title,
                        type: sap.m.DialogType.Standard,
                        state: sap.ui.core.ValueState.None,
                        stretchOnPhone: true,
                        horizontalScrolling: true,
                        verticalScrolling: true,
                        content: [this.table],
                        buttons: [this.drawBars()],
                    });
                }
                private table: sap.ui.table.Table;
                /** 显示可用的模板 */
                showModes(modes: app.IDataExportMode[]): void {
                    this.table.setModel(new sap.ui.model.json.JSONModel(modes));
                }
                /** 显示结果 */
                showReslut(result: app.IExportResult): void {
                    if (result.mode === app.DataExportModeJson.MODE_SIGN) {
                        jQuery.sap.require("sap.ui.core.util.File");
                        let content: any = result.content,
                            fileName: string = result.address.substring(0, result.address.lastIndexOf(".")),
                            extension: string = result.address.substring(result.address.lastIndexOf(".") + 1),
                            mimeType: string = extension;
                        sap.ui.core.util.File.save(
                            content,
                            fileName,
                            extension,
                            mimeType,
                            "");
                    } else {
                        throw new Error(ibas.i18n.prop("importexport_export_result_can_not_showed", result.mode));
                    }
                }
            }
        }
    }
}