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
                            new sap.extension.m.Input("", {
                            }).bindProperty("bindingValue", {
                                path: "name",
                                type: new sap.extension.data.Alphanumeric({
                                    maxLength: 30
                                })
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_activated") }),
                            new sap.extension.m.EnumSelect("", {
                                enumType: ibas.emYesNo
                            }).bindProperty("bindingValue", {
                                path: "activated",
                                type: new sap.extension.data.YesNo()
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_language") }),
                            new sap.extension.m.Input("", {
                            }).bindProperty("bindingValue", {
                                path: "language",
                                type: new sap.extension.data.Alphanumeric({
                                    maxLength: 20
                                })
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_category") }),
                            new sap.extension.m.Input("", {
                            }).bindProperty("bindingValue", {
                                path: "category",
                                type: new sap.extension.data.Alphanumeric({
                                    maxLength: 16
                                })
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_notes") }),
                            new sap.extension.m.Input("", {
                            }).bindProperty("bindingValue", {
                                path: "notes",
                                type: new sap.extension.data.Alphanumeric({
                                    maxLength: 100
                                })
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_bocode") }),
                            new sap.extension.m.RepositoryInput("", {
                                showValueHelp: true,
                                repository: initialfantasy.bo.BORepositoryInitialFantasy,
                                dataInfo: {
                                    type: initialfantasy.bo.BOInformation,
                                    key: initialfantasy.bo.BOInformation.PROPERTY_CODE_NAME,
                                    text: initialfantasy.bo.BOInformation.PROPERTY_DESCRIPTION_NAME
                                },
                                valueHelpRequest: function (): void {
                                    that.fireViewEvents(that.chooseBusinessObjectEvent);
                                }
                            }).bindProperty("bindingValue", {
                                path: "boCode",
                                type: new sap.extension.data.Alphanumeric({
                                    maxLength: 30
                                })
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_applicationid") }),
                            new sap.extension.m.Input("", {
                            }).bindProperty("bindingValue", {
                                path: "applicationId",
                                type: new sap.extension.data.Alphanumeric({
                                    maxLength: 36
                                })
                            }),
                            new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_title_size") }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_width") }),
                            new sap.extension.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("bindingValue", {
                                path: "width",
                                type: new sap.extension.data.Numeric()
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_height") }),
                            new sap.extension.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("bindingValue", {
                                path: "height",
                                type: new sap.extension.data.Numeric()
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_dpi") }),
                            new sap.extension.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("bindingValue", {
                                path: "dpi",
                                type: new sap.extension.data.Numeric(),
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_marginleft") }),
                            new sap.extension.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("bindingValue", {
                                path: "marginLeft",
                                type: new sap.extension.data.Numeric()
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_marginright") }),
                            new sap.extension.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("bindingValue", {
                                path: "marginRight",
                                type: new sap.extension.data.Numeric()
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_margintop") }),
                            new sap.extension.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("bindingValue", {
                                path: "marginTop",
                                type: new sap.extension.data.Numeric()
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_marginbottom") }),
                            new sap.extension.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("bindingValue", {
                                path: "marginBottom",
                                type: new sap.extension.data.Numeric()
                            }),
                            new sap.m.Label("", { text: ibas.i18n.prop("bo_exporttemplate_marginarea") }),
                            new sap.extension.m.Input("", {
                                type: sap.m.InputType.Number
                            }).bindProperty("bindingValue", {
                                path: "marginArea",
                                type: new sap.extension.data.Numeric()
                            }),
                        ]
                    });

                    let formExportTemplateItem: sap.ui.layout.form.SimpleForm = new sap.ui.layout.form.SimpleForm("", {
                        editable: true,
                        content: [
                            new sap.m.IconTabBar("", {
                                headerBackgroundDesign: sap.m.BackgroundDesign.Transparent,
                                expandable: false,
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
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "pageHeaderLeft",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator("", {}),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                    }).bindProperty("bindingValue", {
                                                        path: "pageHeaderTop",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator("", {}),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "pageHeaderWidth",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator("", {}),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "pageHeaderHeight",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                ]
                                            }),
                                            this.tablePageHeaders = this.createTableTemplateItem(this.addPageHeaderEvent, this.removePageHeaderEvent)
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
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "startSectionLeft",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "startSectionTop",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "startSectionWidth",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "startSectionHeight",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                ]
                                            }),
                                            this.tableStartSections = this.createTableTemplateItem(this.addStartSectionEvent, this.removeStartSectionEvent),
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
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "repetitionHeaderLeft",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "repetitionHeaderTop",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "repetitionHeaderWidth",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "repetitionHeaderHeight",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                ]
                                            }),
                                            this.tableRepetitionHeaders = this.createTableTemplateItem(this.addRepetitionHeaderEvent, this.removeRepetitionHeaderEvent),
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
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "repetitionLeft",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "repetitionTop",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "repetitionWidth",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "repetitionHeight",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                ]
                                            }),
                                            this.tableRepetitions = this.createTableTemplateItem(this.addRepetitionEvent, this.removeRepetitionEvent),
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
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "repetitionFooterLeft",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "repetitionFooterTop",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "repetitionFooterWidth",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "repetitionFooterHeight",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                ]
                                            }),
                                            this.tableRepetitionFooters = this.createTableTemplateItem(this.addRepetitionFooterEvent, this.removeRepetitionFooterEvent),
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
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "endSectionLeft",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "endSectionTop",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "endSectionWidth",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "endSectionHeight",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                ]
                                            }),
                                            this.tableEndSections = this.createTableTemplateItem(this.addEndSectionEvent, this.removeEndSectionEvent),
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
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "pageFooterLeft",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_top")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "pageFooterTop",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_width")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "pageFooterWidth",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                    new sap.m.ToolbarSeparator(""),
                                                    new sap.m.Label("", {
                                                        width: "100px",
                                                        text: ibas.i18n.prop("bo_exporttemplate_area_height")
                                                    }),
                                                    new sap.extension.m.Input("", {
                                                        width: "160px",
                                                        type: sap.m.InputType.Number
                                                    }).bindProperty("bindingValue", {
                                                        path: "pageFooterHeight",
                                                        type: new sap.extension.data.Numeric()
                                                    }),
                                                ]
                                            }),
                                            this.tablePageFooters = this.createTableTemplateItem(this.addPageFooterEvent, this.removePageFooterEvent),
                                        ]
                                    }),
                                ]
                            })
                        ]
                    });
                    return this.page = new sap.extension.m.DataPage("", {
                        showHeader: false,
                        dataInfo: {
                            code: bo.ExportTemplate.BUSINESS_OBJECT_CODE,
                        },
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
                        content: [
                            formTop,
                            formExportTemplateItem,
                        ]
                    });
                }

                private createTableTemplateItem(eventAdd: Function, eventRemove: Function): sap.extension.table.Table {
                    let that: this = this;
                    let table: sap.extension.table.DataTable = new sap.extension.table.DataTable("", {
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
                                        that.fireViewEvents(eventRemove, table.getSelecteds());
                                    }
                                }),
                            ]
                        }),
                        rows: "{/rows}",
                        columns: [
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemid"),
                                template: new sap.extension.m.Text("", {
                                }).bindProperty("bindingValue", {
                                    path: "itemID",
                                    type: new sap.extension.data.Alphanumeric()
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemtype"),
                                template: new sap.extension.m.Input("", {
                                }).bindProperty("bindingValue", {
                                    path: "itemType",
                                    type: new sap.extension.data.Alphanumeric({
                                        maxLength: 8
                                    })
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemleft"),
                                template: new sap.extension.m.Input("", {
                                    type: sap.m.InputType.Number
                                }).bindProperty("bindingValue", {
                                    path: "itemLeft",
                                    type: new sap.extension.data.Numeric()
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemtop"),
                                template: new sap.extension.m.Input("", {
                                    type: sap.m.InputType.Number
                                }).bindProperty("bindingValue", {
                                    path: "itemTop",
                                    type: new sap.extension.data.Numeric()
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemwidth"),
                                template: new sap.extension.m.Input("", {
                                    type: sap.m.InputType.Number
                                }).bindProperty("bindingValue", {
                                    path: "itemWidth",
                                    type: new sap.extension.data.Numeric()
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemheight"),
                                template: new sap.extension.m.Input("", {
                                    type: sap.m.InputType.Number
                                }).bindProperty("bindingValue", {
                                    path: "itemHeight",
                                    type: new sap.extension.data.Numeric()
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_sourcetype"),
                                template: new sap.extension.m.EnumSelect("", {
                                    enumType: bo.emDataSourceType
                                }).bindProperty("bindingValue", {
                                    path: "sourceType",
                                    type: new sap.extension.data.Enum({
                                        enumType: bo.emDataSourceType
                                    })
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemstring"),
                                template: new sap.extension.m.Input("", {
                                }).bindProperty("bindingValue", {
                                    path: "itemString",
                                    type: new sap.extension.data.Alphanumeric({
                                        maxLength: 200
                                    })
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_valueformat"),
                                template: new sap.extension.m.Input("", {
                                }).bindProperty("bindingValue", {
                                    path: "valueFormat",
                                    type: new sap.extension.data.Alphanumeric({
                                        maxLength: 200
                                    })
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_itemvisible"),
                                template: new sap.extension.m.EnumSelect("", {
                                    enumType: ibas.emYesNo
                                }).bindProperty("bindingValue", {
                                    path: "itemVisible",
                                    type: new sap.extension.data.YesNo()
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_fontname"),
                                template: new sap.extension.m.Input("", {
                                }).bindProperty("bindingValue", {
                                    path: "fontName",
                                    type: new sap.extension.data.Alphanumeric({
                                        maxLength: 50
                                    })
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_fontsize"),
                                template: new sap.extension.m.Input("", {
                                    type: sap.m.InputType.Number
                                }).bindProperty("bindingValue", {
                                    path: "fontSize",
                                    type: new sap.extension.data.Numeric()
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_textstyle"),
                                template: new sap.extension.m.EnumSelect("", {
                                    enumType: bo.emTextStyle
                                }).bindProperty("bindingValue", {
                                    path: "textStyle",
                                    type: new sap.extension.data.Enum({
                                        enumType: bo.emTextStyle
                                    }),
                                }),
                            }),
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_justificationhorizontal"),
                                template: new sap.extension.m.EnumSelect("", {
                                    enumType: bo.emJustificationHorizontal
                                }).bindProperty("bindingValue", {
                                    path: "justificationHorizontal",
                                    type: new sap.extension.data.Enum({
                                        enumType: bo.emJustificationHorizontal
                                    })
                                }),
                            }),
                        ]
                    });
                    return table;
                }

                private page: sap.extension.m.Page;
                private tablePageHeaders: sap.extension.table.Table;
                private tableStartSections: sap.extension.table.Table;
                private tableRepetitionHeaders: sap.extension.table.Table;
                private tableRepetitions: sap.extension.table.Table;
                private tableRepetitionFooters: sap.extension.table.Table;
                private tableEndSections: sap.extension.table.Table;
                private tablePageFooters: sap.extension.table.Table;

                /** 显示数据 */
                showExportTemplate(data: bo.ExportTemplate): void {
                    this.page.setModel(new sap.extension.model.JSONModel(data));
                    // 改变页面状态
                    sap.extension.pages.changeStatus(this.page);
                }
                /** 显示数据-页眉 */
                showPageHeaders(datas: bo.ExportTemplateItem[]): void {
                    this.tablePageHeaders.setModel(new sap.extension.model.JSONModel({ rows: datas }));
                }
                /** 显示数据-开始区域 */
                showStartSections(datas: bo.ExportTemplateItem[]): void {
                    this.tableStartSections.setModel(new sap.extension.model.JSONModel({ rows: datas }));
                }
                /** 显示数据-重复区头 */
                showRepetitionHeaders(datas: bo.ExportTemplateItem[]): void {
                    this.tableRepetitionHeaders.setModel(new sap.extension.model.JSONModel({ rows: datas }));
                }
                /** 显示数据-重复区 */
                showRepetitions(datas: bo.ExportTemplateItem[]): void {
                    this.tableRepetitions.setModel(new sap.extension.model.JSONModel({ rows: datas }));
                }
                /** 显示数据-重复区脚 */
                showRepetitionFooters(datas: bo.ExportTemplateItem[]): void {
                    this.tableRepetitionFooters.setModel(new sap.extension.model.JSONModel({ rows: datas }));
                }
                /** 显示数据-结束区域 */
                showEndSections(datas: bo.ExportTemplateItem[]): void {
                    this.tableEndSections.setModel(new sap.extension.model.JSONModel({ rows: datas }));
                }
                /** 显示数据-页脚 */
                showPageFooters(datas: bo.ExportTemplateItem[]): void {
                    this.tablePageFooters.setModel(new sap.extension.model.JSONModel({ rows: datas }));
                }
            }
        }
    }
}
