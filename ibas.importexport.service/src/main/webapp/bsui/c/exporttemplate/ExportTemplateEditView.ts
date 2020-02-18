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
                /** 添加导出模板-项事件 */
                addAppendixEvent: Function;
                /** 删除导出模板-项事件 */
                removeAppendixEvent: Function;
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
                                    new sap.m.IconTabSeparator("", {
                                        icon: "sap-icon://vertical-grip"
                                    }),
                                    new sap.m.IconTabFilter("", {
                                        key: bo.emAreaType.APPENDIX,
                                        text: ibas.i18n.prop("bo_exporttemplateitem_appendixs"),
                                        content: [
                                            this.tabContainerAppendixes = new sap.m.TabContainer("", {
                                                showAddNewButton: true,
                                                backgroundDesign: sap.m.PageBackgroundDesign.Transparent,
                                                addNewButtonPress(event: sap.ui.base.Event): void {
                                                    that.fireViewEvents(that.addAppendixEvent);
                                                    let items: any[] = that.tabContainerAppendixes.getItems();
                                                    if (items.length > 0) {
                                                        that.tabContainerAppendixes.setSelectedItem(items[items.length - 1]);
                                                    }
                                                },
                                                itemClose(event: sap.ui.base.Event): void {
                                                    let item: any = event.getParameter("item");
                                                    if (item instanceof sap.m.TabContainerItem) {
                                                        let model: any = item.getModel();
                                                        if (model instanceof sap.extension.model.JSONModel) {
                                                            that.application.viewShower.messages({
                                                                type: ibas.emMessageType.QUESTION,
                                                                actions: [
                                                                    ibas.emMessageAction.YES,
                                                                    ibas.emMessageAction.NO,
                                                                ],
                                                                message: ibas.i18n.prop("importexport_remove_page_continue", item.getName()),
                                                                onCompleted: (action) => {
                                                                    if (action === ibas.emMessageAction.YES) {
                                                                        that.fireViewEvents(that.removeAppendixEvent, model.getData());
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                    event.preventDefault();
                                                },
                                                itemSelect(event: sap.ui.base.Event): void {
                                                    let item: any = event.getParameter("item");
                                                    if (item instanceof sap.m.TabContainerItem) {
                                                        let model: any = item.getModel();
                                                        if (model instanceof sap.extension.model.JSONModel) {
                                                            let data: any = model.getData();
                                                            if (data instanceof bo.ExportTemplateAppendix) {
                                                                that.tableAppendixes.setModel(new sap.extension.model.JSONModel({ rows: data.contents.filterDeleted() }));
                                                                return;
                                                            }
                                                        }
                                                    }
                                                    that.tableAppendixes.setModel(null);
                                                },
                                            }),
                                            this.tableAppendixes = <any>this.createTableTemplateItem(function (): void {
                                                let selectItem: any = sap.ui.getCore().byId(that.tabContainerAppendixes.getSelectedItem());
                                                if (selectItem instanceof sap.m.TabContainerItem) {
                                                    let model: any = selectItem.getModel();
                                                    if (model instanceof sap.extension.model.JSONModel) {
                                                        let data: any = model.getData();
                                                        if (data instanceof bo.ExportTemplateAppendix) {
                                                            data.contents.create();
                                                            that.tableAppendixes.setModel(new sap.extension.model.JSONModel({ rows: data.contents.filterDeleted() }));
                                                        }
                                                    }
                                                }
                                            }, function (items: bo.ExportTemplateItem[]): void {
                                                items = ibas.arrays.create(items);
                                                if (items.length === 0) {
                                                    return;
                                                }
                                                let selectItem: any = sap.ui.getCore().byId(that.tabContainerAppendixes.getSelectedItem());
                                                if (selectItem instanceof sap.m.TabContainerItem) {
                                                    let model: any = selectItem.getModel();
                                                    if (model instanceof sap.extension.model.JSONModel) {
                                                        let data: any = model.getData();
                                                        if (data instanceof bo.ExportTemplateAppendix) {
                                                            for (let item of items) {
                                                                if (data.contents.indexOf(item) >= 0) {
                                                                    if (item.isNew) {
                                                                        // 新建的移除集合
                                                                        data.contents.remove(item);
                                                                    } else {
                                                                        // 非新建标记删除
                                                                        item.delete();
                                                                    }
                                                                }
                                                            }
                                                            that.tableAppendixes.setModel(new sap.extension.model.JSONModel({ rows: data.contents.filterDeleted() }));
                                                        }
                                                    }
                                                }
                                            }).setVisible(false),
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
                                    icon: "sap-icon://add",
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
                                    showSuggestion: true,
                                    suggestionItems: [
                                        new sap.ui.core.ListItem("", {
                                            key: "TEXT",
                                            text: ibas.i18n.prop("template_item_type_text"),
                                        }),
                                        new sap.ui.core.ListItem("", {
                                            key: "IMG",
                                            text: ibas.i18n.prop("template_item_type_image"),
                                        })
                                    ]
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
                                    showValueHelp: true,
                                    valueHelpOnly: false,
                                    valueHelpRequest: function (event: sap.ui.base.Event): void {
                                        let source: any = event.getSource();
                                        if (!source) {
                                            return;
                                        }
                                        let data: bo.ExportTemplateItem = source.getBindingContext().getObject();
                                        if (ibas.objects.isNull(data)) {
                                            return;
                                        }
                                        jQuery.sap.require("sap.ui.codeeditor.CodeEditor");
                                        let dialog: sap.m.Dialog = new sap.extension.m.Dialog("", {
                                            title: ibas.i18n.prop("bo_exporttemplateitem_itemstring") + ibas.i18n.prop("shell_data_edit"),
                                            type: sap.m.DialogType.Standard,
                                            state: sap.ui.core.ValueState.None,
                                            content: [
                                                new sap.ui.codeeditor.CodeEditor("", {
                                                    height: ibas.strings.format("{0}px", window.innerHeight * 0.6),
                                                    width: ibas.strings.format("{0}px", window.innerWidth * 0.6),
                                                    type: data.sourceType === bo.emDataSourceType.QUERY ? "sql" : "text",
                                                    colorTheme: "eclipse",
                                                    value: {
                                                        path: "/itemString"
                                                    }
                                                })
                                            ],
                                            buttons: [
                                                new sap.m.Button("", {
                                                    text: ibas.i18n.prop("shell_exit"),
                                                    type: sap.m.ButtonType.Transparent,
                                                    icon: "sap-icon://inspect-down",
                                                    press: function (): void {
                                                        dialog.close();
                                                        dialog = null;
                                                    }
                                                }),
                                            ]
                                        });
                                        dialog.setModel(new sap.extension.model.JSONModel(data));
                                        dialog.open();
                                        dialog.focus(undefined);
                                    }
                                }).bindProperty("bindingValue", {
                                    path: "itemString",
                                    type: new sap.extension.data.Alphanumeric({
                                        maxLength: 800
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
                                label: ibas.i18n.prop("bo_exporttemplateitem_textsegment"),
                                template: new sap.extension.m.EnumSelect("", {
                                    enumType: bo.emTextSegment
                                }).bindProperty("bindingValue", {
                                    path: "textSegment",
                                    type: new sap.extension.data.Enum({
                                        enumType: bo.emTextSegment
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
                            new sap.extension.table.DataColumn("", {
                                label: ibas.i18n.prop("bo_exporttemplateitem_justificationvertical"),
                                template: new sap.extension.m.EnumSelect("", {
                                    enumType: bo.emJustificationVertical
                                }).bindProperty("bindingValue", {
                                    path: "justificationVertical",
                                    type: new sap.extension.data.Enum({
                                        enumType: bo.emJustificationVertical
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
                private tableAppendixes: sap.extension.table.Table;
                private tabContainerAppendixes: sap.m.TabContainer;

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
                /** 显示数据-附录 */
                showAppendixes(datas: bo.ExportTemplateAppendix[]): void {
                    this.tabContainerAppendixes.destroyItems();
                    for (let item of datas) {
                        let tabItem: sap.m.TabContainerItem = new sap.m.TabContainerItem("", {
                            name: {
                                path: "/pageOrder",
                                formatter(data: number): string {
                                    return ibas.i18n.prop("importexport_page_number", data ? data : 0);
                                },
                            }
                        });
                        tabItem.setModel(new sap.extension.model.JSONModel(item));
                        this.tabContainerAppendixes.addItem(tabItem);
                    }
                    if (this.tabContainerAppendixes.getItems().length > 0) {
                        this.tableAppendixes.setVisible(true);
                    }
                }
            }
        }
    }
}
