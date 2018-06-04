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
            /** 编辑视图-导出模板 */
            export class ExportTemplateEditView extends ibas.BOEditView implements app.IExportTemplateEditView {
                /** 删除数据事件 */
                deleteDataEvent: Function;
                /** 新建数据事件，参数1：是否克隆 */
                createDataEvent: Function;
                /** 添加导出模板-项事件 */
                addPageHeaderEvent: Function;
                /** 删除导出模板-项事件 */
                removePageHeaderEvent: Function;
                /** 添加导出模板-项事件 */
                addStartSectionEvent: Function;
                /** 删除导出模板-项事件 */
                removeStartSectionEvent: Function;
                /** 添加导出模板-项事件 */
                addRepetitionHeaderEvent: Function;
                /** 删除导出模板-项事件 */
                removeRepetitionHeaderEvent: Function;
                /** 添加导出模板-项事件 */
                addRepetitionEvent: Function;
                /** 删除导出模板-项事件 */
                removeRepetitionEvent: Function;
                /** 添加导出模板-项事件 */
                addRepetitionFooterEvent: Function;
                /** 删除导出模板-项事件 */
                removeRepetitionFooterEvent: Function;
                /** 添加导出模板-项事件 */
                addEndSectionEvent: Function;
                /** 删除导出模板-项事件 */
                removeEndSectionEvent: Function;
                /** 添加导出模板-项事件 */
                addPageFooterEvent: Function;
                /** 删除导出模板-项事件 */
                removePageFooterEvent: Function;
                /** 选择业务对象 */
                chooseBusinessObjectEvent: Function;

                /** 绘制视图 */
                draw(): any {
                    let that: this = this;
                    let formTop: sap.ui.layout.form.SimpleForm = new sap.ui.layout.form.SimpleForm("", {
                        editable: true,
                        content: [
                            new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_title_general") }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_name") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Text
                            }).bindProperty("value", {
                                path: "name"
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_activated") }),
                            new sap.m.Select("", {
                                items: openui5.utils.createComboBoxItems(ibas.emYesNo),
                            }).bindProperty("selectedKey", {
                                path: "activated",
                                type: "sap.ui.model.type.Integer",
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_language") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Text
                            }).bindProperty("value", {
                                path: "language"
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_category") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Text
                            }).bindProperty("value", {
                                path: "category"
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_notes") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Text
                            }).bindProperty("value", {
                                path: "notes"
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_bocode") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Text,
                                showValueHelp: true,
                                valueHelpRequest: function (): void {
                                    that.fireViewEvents(that.chooseBusinessObjectEvent);
                                }
                            }).bindProperty("value", {
                                path: "boCode"
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_applicationid") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Text
                            }).bindProperty("value", {
                                path: "applicationId"
                            }),
                            new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_title_size") }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_width") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("value", {
                                path: "width"
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_height") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("value", {
                                path: "height"
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_marginleft") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("value", {
                                path: "marginLeft"
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_marginright") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("value", {
                                path: "marginRight"
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_margintop") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("value", {
                                path: "marginTop"
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_marginbottom") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("value", {
                                path: "marginBottom"
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_marginarea") }),
                            new sap.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("value", {
                                path: "marginArea"
                            }),
                        ]
                    });
                    this.tablePageHeaders = this.createTableTemplateItem(this.addPageHeaderEvent, this.removePageHeaderEvent);
                    this.tableStartSections = this.createTableTemplateItem(this.addStartSectionEvent, this.removeStartSectionEvent);
                    this.tableRepetitionHeaders = this.createTableTemplateItem(this.addRepetitionHeaderEvent, this.removeRepetitionHeaderEvent);
                    this.tableRepetitions = this.createTableTemplateItem(this.addRepetitionEvent, this.removeRepetitionEvent);
                    this.tableRepetitionFooters = this.createTableTemplateItem(this.addRepetitionFooterEvent, this.removeRepetitionFooterEvent);
                    this.tableEndSections = this.createTableTemplateItem(this.addEndSectionEvent, this.removeEndSectionEvent);
                    this.tablePageFooters = this.createTableTemplateItem(this.addPageFooterEvent, this.removePageFooterEvent);

                    let formExportTemplateItem: sap.ui.layout.form.SimpleForm = new sap.ui.layout.form.SimpleForm("", {
                        editable: true,
                        content: [
                            new sap.m.IconTabBar("", {
                                headerBackgroundDesign: sap.m.BackgroundDesign.Transparent,
                                items: [
                                    new sap.m.IconTabFilter("", {
                                        key: bo.emAreaType.PAGE_HEADER,
                                        text: ibas.i18n.prop("bo_exporttemplateitem_pageheaders"),
                                        content: [
                                            new sap.m.OverflowToolbar("", {
                                                content: [
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_left")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "pageHeaderLeft"
                                                    }),
                                                    new sap.m.ToolbarSeparator("", { width: "10px", }),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "pageHeaderTop"
                                                    }),
                                                    new sap.m.ToolbarSeparator("", { width: "10px", }),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "pageHeaderWidth"
                                                    }),
                                                    new sap.m.ToolbarSeparator("", { width: "10px", }),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "pageHeaderHeight"
                                                    }),
                                                ]
                                            }),
                                            this.tablePageHeaders,
                                        ]
                                    }),
                                    new sap.m.IconTabFilter("", {
                                        key: bo.emAreaType.START_SECTION,
                                        text: ibas.i18n.prop("bo_exporttemplateitem_startsections"),
                                        content: [
                                            new sap.m.OverflowToolbar("", {
                                                content: [
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_left")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "startSectionLeft"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "startSectionTop"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "startSectionWidth"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "startSectionHeight"
                                                    }),
                                                ]
                                            }),
                                            this.tableStartSections,
                                        ]
                                    }),
                                    new sap.m.IconTabFilter("", {
                                        key: bo.emAreaType.REPETITION_HEADER,
                                        text: ibas.i18n.prop("bo_exporttemplateitem_repetitionheaders"),
                                        content: [
                                            new sap.m.OverflowToolbar("", {
                                                content: [
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_left")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "repetitionHeaderLeft"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "repetitionHeaderTop"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "repetitionHeaderWidth"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "repetitionHeaderHeight"
                                                    }),
                                                ]
                                            }),
                                            this.tableRepetitionHeaders,
                                        ]
                                    }),
                                    new sap.m.IconTabFilter("", {
                                        key: bo.emAreaType.REPETITION,
                                        text: ibas.i18n.prop("bo_exporttemplateitem_repetitions"),
                                        content: [
                                            new sap.m.OverflowToolbar("", {
                                                content: [
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_left")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "repetitionLeft"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "repetitionTop"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "repetitionWidth"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "repetitionHeight"
                                                    }),
                                                ]
                                            }),
                                            this.tableRepetitions,
                                        ]
                                    }),
                                    new sap.m.IconTabFilter("", {
                                        key: bo.emAreaType.REPETITION_FOOTER,
                                        text: ibas.i18n.prop("bo_exporttemplateitem_repetitionfooters"),
                                        content: [
                                            new sap.m.OverflowToolbar("", {
                                                content: [
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_left")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "repetitionFooterLeft"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "repetitionFooterTop"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "repetitionFooterWidth"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "repetitionFooterHeight"
                                                    }),
                                                ]
                                            }),
                                            this.tableRepetitionFooters,
                                        ]
                                    }),
                                    new sap.m.IconTabFilter("", {
                                        key: bo.emAreaType.END_SECTION,
                                        text: ibas.i18n.prop("bo_exporttemplateitem_endsections"),
                                        content: [
                                            new sap.m.OverflowToolbar("", {
                                                content: [
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_left")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "endSectionLeft"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "endSectionTop"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "endSectionWidth"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "endSectionHeight"
                                                    }),
                                                ]
                                            }),
                                            this.tableEndSections,
                                        ]
                                    }),
                                    new sap.m.IconTabFilter("", {
                                        key: bo.emAreaType.PAGE_FOOTER,
                                        text: ibas.i18n.prop("bo_exporttemplateitem_pagefooters"),
                                        content: [
                                            new sap.m.OverflowToolbar("", {
                                                content: [
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_left")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "pageFooterLeft"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "pageFooterTop"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "pageFooterWidth"
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("value", {
                                                        path: "pageFooterHeight"
                                                    }),
                                                ]
                                            }),
                                            this.tablePageFooters,
                                        ]
                                    }),
                                ]
                            })
                        ]
                    });
                    this.layoutMain = new sap.ui.layout.VerticalLayout("", {
                        width: "100%",
                        height: "100%",
                        content: [
                            formTop,
                            formExportTemplateItem,
                        ]
                    });
                    this.page = new sap.m.Page("", {
                        showHeader: false,
                        subHeader: new sap.m.Toolbar("", {
                            content: [
                                new sap.m.Button("", {
                                    text: ibas.i18n.prop("shell_data_save"),
                                    type: sap.m.ButtonType.Transparent,
                                    icon: "sap-icon://save",
                                    press: function (): void {
                                        that.fireViewEvents(that.saveDataEvent);
                                    }
                                }),
                                new sap.m.Button("", {
                                    text: ibas.i18n.prop("shell_data_delete"),
                                    type: sap.m.ButtonType.Transparent,
                                    icon: "sap-icon://delete",
                                    press: function (): void {
                                        that.fireViewEvents(that.deleteDataEvent);
                                    }
                                }),
                                new sap.m.ToolbarSeparator(""),
                                new sap.m.MenuButton("", {
                                    text: ibas.strings.format("{0}/{1}",
                                        ibas.i18n.prop("shell_data_new"), ibas.i18n.prop("shell_data_clone")),
                                    icon: "sap-icon://create",
                                    type: sap.m.ButtonType.Transparent,
                                    menu: new sap.m.Menu("", {
                                        items: [
                                            new sap.m.MenuItem("", {
                                                text: ibas.i18n.prop("shell_data_new"),
                                                icon: "sap-icon://create",
                                                press: function (): void {
                                                    // 创建新的对象
                                                    that.fireViewEvents(that.createDataEvent, false);
                                                }
                                            }),
                                            new sap.m.MenuItem("", {
                                                text: ibas.i18n.prop("shell_data_clone"),
                                                icon: "sap-icon://copy",
                                                press: function (): void {
                                                    // 复制当前对象
                                                    that.fireViewEvents(that.createDataEvent, true);
                                                }
                                            }),
                                        ],
                                    })
                                }),
                            ]
                        }),
                        content: [this.layoutMain]
                    });
                    return this.page;
                }

                private createTableTemplateItem(eventAdd: Function, eventRemove: Function): sap.ui.table.Table {
                    let that: this = this;
                    let table: sap.ui.table.Table = new sap.ui.table.Table("", {
                        toolbar: new sap.m.Toolbar("", {
                            content: [
                                new sap.m.Button("", {
                                    text: ibas.i18n.prop("shell_data_add"),
                                    type: sap.m.ButtonType.Transparent,
                                    icon: "sap-icon://less",
                                    press: function (): void {
                                        that.fireViewEvents(eventAdd);
                                    }
                                }),
                                new sap.m.Button("", {
                                    text: ibas.i18n.prop("shell_data_remove"),
                                    type: sap.m.ButtonType.Transparent,
                                    icon: "sap-icon://less",
                                    press: function (): void {
                                        that.fireViewEvents(eventRemove,
                                            // 获取表格选中的对象
                                            openui5.utils.getSelecteds<bo.ExportTemplateItem>(table)
                                        );
                                    }
                                }),
                            ]
                        }),
                        enableSelectAll: false,
                        selectionBehavior: sap.ui.table.SelectionBehavior.Row,
                        visibleRowCount: ibas.config.get(openui5.utils.CONFIG_ITEM_LIST_TABLE_VISIBLE_ROW_COUNT, 8),
                        rows: "{/rows}",
                        columns: [
                            new sap.ui.table.Column("", {
                                width: "80px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemid"),
                                template: new sap.m.Text("", {
                                    wrapping: false
                                }).bindProperty("text", {
                                    path: "itemID",
                                }),
                            }),
                            new sap.ui.table.Column("", {
                                width: "100px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemtype"),
                                template: new sap.m.Input("", {
                                    width: "100%",
                                }).bindProperty("value", {
                                    path: "itemType",
                                }),
                            }),
                            new sap.ui.table.Column("", {
                                width: "100px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemleft"),
                                template: new sap.m.Input("", {
                                    width: "100%",
                                    type: sap.m.InputType.Number
                                }).bindProperty("value", {
                                    path: "itemLeft",
                                }),
                            }),
                            new sap.ui.table.Column("", {
                                width: "100px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemtop"),
                                template: new sap.m.Input("", {
                                    width: "100%",
                                    type: sap.m.InputType.Number
                                }).bindProperty("value", {
                                    path: "itemTop",
                                }),
                            }),
                            new sap.ui.table.Column("", {
                                width: "100px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemwidth"),
                                template: new sap.m.Input("", {
                                    width: "100%",
                                    type: sap.m.InputType.Number
                                }).bindProperty("value", {
                                    path: "itemWidth",
                                }),
                            }),
                            new sap.ui.table.Column("", {
                                width: "100px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemheight"),
                                template: new sap.m.Input("", {
                                    width: "100%",
                                    type: sap.m.InputType.Number
                                }).bindProperty("value", {
                                    path: "itemHeight",
                                }),
                            }),
                            new sap.ui.table.Column("", {
                                width: "100px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_sourcetype"),
                                template: new sap.m.Select("", {
                                    width: "100%",
                                    items: openui5.utils.createComboBoxItems(bo.emDataSourceType),
                                }).bindProperty("selectedKey", {
                                    path: "sourceType",
                                    type: "sap.ui.model.type.Integer",
                                })
                            }),
                            new sap.ui.table.Column("", {
                                width: "160px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemstring"),
                                template: new sap.m.Input("", {
                                    width: "100%",
                                }).bindProperty("value", {
                                    path: "itemString",
                                }),
                            }),
                            new sap.ui.table.Column("", {
                                width: "100px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_valueformat"),
                                template: new sap.m.Input("", {
                                    width: "100%",
                                }).bindProperty("value", {
                                    path: "valueFormat",
                                }),
                            }),
                            new sap.ui.table.Column("", {
                                width: "100px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemvisible"),
                                template: new sap.m.Select("", {
                                    width: "100%",
                                    items: openui5.utils.createComboBoxItems(ibas.emYesNo),
                                }).bindProperty("selectedKey", {
                                    path: "itemVisible",
                                    type: "sap.ui.model.type.Integer",
                                })
                            }),
                            new sap.ui.table.Column("", {
                                width: "100px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_fontname"),
                                template: new sap.m.Input("", {
                                    width: "100%",
                                }).bindProperty("value", {
                                    path: "fontName",
                                }),
                            }),
                            new sap.ui.table.Column("", {
                                width: "100px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_fontsize"),
                                template: new sap.m.Input("", {
                                    width: "100%",
                                    type: sap.m.InputType.Number,
                                }).bindProperty("value", {
                                    path: "fontSize",
                                }),
                            }),
                            new sap.ui.table.Column("", {
                                width: "100px",
                                label: ibas.i18n.prop("bo_exporttemplateitem_justificationhorizontal"),
                                template: new sap.m.Select("", {
                                    width: "100%",
                                    items: openui5.utils.createComboBoxItems(bo.emJustificationHorizontal),
                                }).bindProperty("selectedKey", {
                                    path: "justificationHorizontal",
                                    type: "sap.ui.model.type.Integer",
                                })
                            }),
                        ]
                    });
                    return table;
                }

                private page: sap.m.Page;
                private layoutMain: sap.ui.layout.VerticalLayout;
                private tablePageHeaders: sap.ui.table.Table;
                private tableStartSections: sap.ui.table.Table;
                private tableRepetitionHeaders: sap.ui.table.Table;
                private tableRepetitions: sap.ui.table.Table;
                private tableRepetitionFooters: sap.ui.table.Table;
                private tableEndSections: sap.ui.table.Table;
                private tablePageFooters: sap.ui.table.Table;

                /** 显示数据 */
                showExportTemplate(data: bo.ExportTemplate): void {
                    this.layoutMain.setModel(new sap.ui.model.json.JSONModel(data));
                    this.layoutMain.bindObject("/");
                    // 监听属性改变，并更新控件
                    openui5.utils.refreshModelChanged(this.layoutMain, data);
                }
                /** 显示数据-页眉 */
                showPageHeaders(datas: bo.ExportTemplateItem[]): void {
                    this.tablePageHeaders.setModel(new sap.ui.model.json.JSONModel({ rows: datas }));
                    // 监听属性改变，并更新控件
                    openui5.utils.refreshModelChanged(this.tablePageHeaders, datas);
                }
                /** 显示数据-开始区域 */
                showStartSections(datas: bo.ExportTemplateItem[]): void {
                    this.tableStartSections.setModel(new sap.ui.model.json.JSONModel({ rows: datas }));
                    // 监听属性改变，并更新控件
                    openui5.utils.refreshModelChanged(this.tableStartSections, datas);
                }
                /** 显示数据-重复区头 */
                showRepetitionHeaders(datas: bo.ExportTemplateItem[]): void {
                    this.tableRepetitionHeaders.setModel(new sap.ui.model.json.JSONModel({ rows: datas }));
                    // 监听属性改变，并更新控件
                    openui5.utils.refreshModelChanged(this.tableRepetitionHeaders, datas);
                }
                /** 显示数据-重复区 */
                showRepetitions(datas: bo.ExportTemplateItem[]): void {
                    this.tableRepetitions.setModel(new sap.ui.model.json.JSONModel({ rows: datas }));
                    // 监听属性改变，并更新控件
                    openui5.utils.refreshModelChanged(this.tableRepetitions, datas);
                }
                /** 显示数据-重复区脚 */
                showRepetitionFooters(datas: bo.ExportTemplateItem[]): void {
                    this.tableRepetitionFooters.setModel(new sap.ui.model.json.JSONModel({ rows: datas }));
                    // 监听属性改变，并更新控件
                    openui5.utils.refreshModelChanged(this.tableRepetitionFooters, datas);
                }
                /** 显示数据-结束区域 */
                showEndSections(datas: bo.ExportTemplateItem[]): void {
                    this.tableEndSections.setModel(new sap.ui.model.json.JSONModel({ rows: datas }));
                    // 监听属性改变，并更新控件
                    openui5.utils.refreshModelChanged(this.tableEndSections, datas);
                }
                /** 显示数据-页脚 */
                showPageFooters(datas: bo.ExportTemplateItem[]): void {
                    this.tablePageFooters.setModel(new sap.ui.model.json.JSONModel({ rows: datas }));
                    // 监听属性改变，并更新控件
                    openui5.utils.refreshModelChanged(this.tablePageFooters, datas);
                }
            }
        }
    }
}
