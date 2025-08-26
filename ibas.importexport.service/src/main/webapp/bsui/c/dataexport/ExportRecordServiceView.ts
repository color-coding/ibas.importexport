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
             * 导出日志服务视图
             */
            export class ExportRecordServiceView extends ibas.DialogView implements app.IExportRecordServiceView {
                /** 绘制视图 */
                draw(): any {
                    let that: this = this;
                    this.list = new sap.extension.m.Table("", {
                        chooseType: ibas.emChooseType.MULTIPLE,
                        columns: [
                            new sap.extension.m.Column("", {
                                header: ibas.i18n.prop("bo_exportrecord_cause"),
                                width: "10rem",
                                hAlign: sap.ui.core.TextAlign.Center,
                            }),
                            new sap.extension.m.Column("", {
                                header: ibas.i18n.prop("bo_exportrecord_boinst"),
                                width: "5rem",
                                hAlign: sap.ui.core.TextAlign.Center,
                            }),
                            new sap.extension.m.Column("", {
                                header: ibas.i18n.prop("bo_exportrecord_exportuser"),
                                width: "10rem",
                                hAlign: sap.ui.core.TextAlign.Center,
                            }),
                            new sap.extension.m.Column("", {
                                header: ibas.i18n.prop("bo_exportrecord_exportdate"),
                                width: "8rem",
                                hAlign: sap.ui.core.TextAlign.Center,
                            }),
                            new sap.extension.m.Column("", {
                                header: ibas.i18n.prop("bo_exportrecord_exporttime"),
                                width: "6rem",
                                hAlign: sap.ui.core.TextAlign.Center,
                            }),
                            new sap.extension.m.Column("", {
                                header: ibas.i18n.prop("bo_exportrecord_content"),
                                width: "100%",
                                hAlign: sap.ui.core.TextAlign.Center,
                            }),
                        ],
                        items: {
                            path: "/rows",
                            template: new sap.extension.m.ColumnListItem("", {
                                type: sap.m.ListType.Inactive,
                                cells: [
                                    new sap.extension.m.PropertyObjectAttribute("", {
                                        dataInfo: {
                                            code: bo.ExportRecord.BUSINESS_OBJECT_CODE,
                                        },
                                        propertyName: "cause",
                                        bindingValue: {
                                            path: "cause",
                                            type: new sap.extension.data.Alphanumeric(),
                                        }
                                    }),
                                    new sap.extension.m.ObjectAttribute("", {
                                        bindingValue: {
                                            path: "boInst",
                                            type: new sap.extension.data.Numeric(),
                                        }
                                    }),
                                    new sap.extension.m.UserObjectAttribute("", {
                                        bindingValue: {
                                            path: "exportUser",
                                            type: new sap.extension.data.Numeric(),
                                        },
                                    }),
                                    new sap.extension.m.ObjectAttribute("", {
                                        bindingValue: {
                                            path: "exportDate",
                                            type: new sap.extension.data.Date(),
                                        }
                                    }),
                                    new sap.extension.m.ObjectAttribute("", {
                                        bindingValue: {
                                            path: "exportTime",
                                            type: new sap.extension.data.Time(),
                                        }
                                    }),
                                    new sap.extension.m.ObjectAttribute("", {
                                        bindingValue: {
                                            path: "content",
                                            type: new sap.extension.data.Alphanumeric(),
                                        }
                                    }),
                                ]
                            }),
                        }
                    });
                    return new sap.m.Dialog("", {
                        title: this.title,
                        type: sap.m.DialogType.Standard,
                        state: sap.ui.core.ValueState.None,
                        stretch: ibas.config.get(ibas.CONFIG_ITEM_PLANTFORM) === ibas.emPlantform.PHONE ? true : false,
                        horizontalScrolling: false,
                        verticalScrolling: true,
                        contentWidth: "60%",
                        contentHeight: "40%",
                        subHeader: this.headerBar = new sap.m.Bar("", {
                            contentLeft: [
                                new sap.m.Title("", {
                                    titleStyle: sap.ui.core.TitleLevel.H4
                                }),
                            ],
                            contentRight: [
                            ]
                        }),
                        content: [
                            this.list
                        ],
                        buttons: [
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
                private list: sap.extension.m.Table;
                private headerBar: sap.m.Bar;
                /** 显示日志 */
                showRecords(datas: bo.ExportRecord[]): void {
                    this.list.setModel(new sap.extension.model.JSONModel({ rows: datas }));
                }
                /** 显示关联对象 */
                showBusinessObject(bo: ibas.IBusinessObject): void {
                    let title: any = this.headerBar.getContentLeft()[0];
                    if (title instanceof sap.m.Title) {
                        title.setText(ibas.businessobjects.describe(bo.toString()));
                    }
                }

            }
        }
    }
}