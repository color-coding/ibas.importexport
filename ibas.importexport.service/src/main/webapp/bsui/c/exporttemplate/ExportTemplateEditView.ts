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

                /** 绘制视图 */
                draw(): any {
                    let that: this = this;
                    let formTop: sap.ui.layout.form.SimpleForm = new sap.ui.layout.form.SimpleForm("", {
                        editable: true,
                        content: [
                        ]
                    });
                    let formExportTemplateItem: sap.ui.layout.form.SimpleForm = new sap.ui.layout.form.SimpleForm("", {
                        editable: true,
                        content: [
                            new sap.ui.core.Title("", { text: ibas.i18n.prop("bo_exporttemplateitem") }),
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

                private page: sap.m.Page;
                private layoutMain: sap.ui.layout.VerticalLayout;
                private tableExportTemplateItem: sap.ui.table.Table;

                /** 改变视图状态 */
                private changeViewStatus(data: bo.ExportTemplate): void {
                    if (ibas.objects.isNull(data)) {
                        return;
                    }
                    // 新建时：禁用删除，
                    if (data.isNew) {
                        if (this.page.getSubHeader() instanceof sap.m.Toolbar) {
                            openui5.utils.changeToolbarDeletable(<sap.m.Toolbar>this.page.getSubHeader(), false);
                        }
                    }
                }

                /** 显示数据 */
                showExportTemplate(data: bo.ExportTemplate): void {
                    this.layoutMain.setModel(new sap.ui.model.json.JSONModel(data));
                    this.layoutMain.bindObject("/");
                    // 监听属性改变，并更新控件
                    openui5.utils.refreshModelChanged(this.layoutMain, data);
                    // 改变视图状态
                    this.changeViewStatus(data);
                }
                /** 显示数据-页眉 */
                showPageHeaders(datas: bo.ExportTemplateItem[]): void {

                }
                /** 显示数据-开始区域 */
                showStartSections(datas: bo.ExportTemplateItem[]): void {

                }
                /** 显示数据-重复区头 */
                showRepetitionHeaders(datas: bo.ExportTemplateItem[]): void {

                }
                /** 显示数据-重复区 */
                showRepetitions(datas: bo.ExportTemplateItem[]): void {

                }
                /** 显示数据-重复区脚 */
                showRepetitionFooters(datas: bo.ExportTemplateItem[]): void {

                }
                /** 显示数据-结束区域 */
                showEndSections(datas: bo.ExportTemplateItem[]): void {

                }
                /** 显示数据-页脚 */
                showPageFooters(datas: bo.ExportTemplateItem[]): void {

                }
            }
        }
    }
}
