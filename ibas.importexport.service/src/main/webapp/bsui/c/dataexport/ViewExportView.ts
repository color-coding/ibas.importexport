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
             * 视图-审批流程
             */
            export class ViewExportView extends ibas.ResidentView implements app.IViewExportView {
                // 导出
                exportEvent: Function;

                /** 绘制工具条视图 */
                drawBar(): any {
                    let that: this = this;
                    return new sap.m.Button("", {
                        tooltip: this.title,
                        icon: "sap-icon://activate",
                        type: sap.m.ButtonType.Transparent,
                        press: function (): void {
                            if (!that.isDisplayed) {
                                that.fireViewEvents(that.showFullViewEvent);
                            }
                        }
                    });
                }
                /** 绘制视图 */
                draw(): any {
                    let that: this = this;
                    return this.dialog = new sap.m.Dialog("", {
                        title: this.title,
                        type: sap.m.DialogType.Standard,
                        state: sap.ui.core.ValueState.None,
                        horizontalScrolling: true,
                        verticalScrolling: true,
                        content: [
                        ],
                        buttons: [
                            new sap.m.Button("", {
                                text: ibas.i18n.prop("importexport_export"),
                                type: sap.m.ButtonType.Transparent,
                                press: function (): void {
                                    let tables: ibas.IList<ibas.DataTable> = new ibas.ArrayList<ibas.DataTable>();
                                    for (let item of that.dialog.getContent()) {
                                        if (item instanceof sap.extension.table.Table) {
                                            tables.add(item.toDataTable());
                                        } else if (item instanceof sap.extension.table.TreeTable) {
                                            tables.add(item.toDataTable());
                                        }
                                    }
                                    if (tables.length > 0) {
                                        that.fireViewEvents(that.exportEvent, tables);
                                    }
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
                private dialog: sap.m.Dialog;
                /** 显示表格 */
                showTables(): void {
                    let app: any = sap.ui.getCore().byId("__UI_APP");
                    if (app instanceof sap.m.App) {
                        let mainPage: any = app.getCurrentPage();
                        if (mainPage instanceof sap.tnt.ToolPage) {
                            let navContainer: any = mainPage.getMainContents()[0];
                            if (navContainer instanceof sap.m.NavContainer) {
                                this.pasingPage(navContainer.getCurrentPage());
                            }
                        }
                    }
                }

                private pasingPage(page: sap.ui.core.Control): void {
                    if (page instanceof sap.m.Page) {
                        for (let item of page.getContent()) {
                            this.pasingPage(item);
                        }
                    } else if (page instanceof sap.m.NavContainer) {
                        for (let item of page.getPages()) {
                            this.pasingPage(item);
                        }
                    } else if (page instanceof sap.ui.layout.DynamicSideContent) {
                        for (let item of page.getMainContent()) {
                            this.pasingPage(item);
                        }
                    } else if (page instanceof sap.ui.layout.form.SimpleForm) {
                        for (let item of page.getContent()) {
                            if (item instanceof sap.ui.core.Control) {
                                this.pasingPage(item);
                            }
                        }
                    } else if (page instanceof sap.m.IconTabBar) {
                        for (let item of page.getItems()) {
                            if (item instanceof sap.ui.core.Control) {
                                this.pasingPage(item);
                            } else if (item instanceof sap.m.IconTabFilter) {
                                for (let sItem of item.getContent()) {
                                    this.pasingPage(sItem);
                                }
                            }
                        }
                    } else if (page instanceof sap.ui.table.Table) {
                        let nTable: sap.ui.table.Table = this.cloneTable(page);
                        if (!ibas.objects.isNull(nTable)) {
                            this.dialog.addContent(nTable);
                        }
                    }
                }

                private cloneTable(table: sap.ui.table.Table): sap.ui.table.Table {
                    let count: number = (<any>table)._getTotalRowCount();
                    if (count > 0) {
                        let nTable: sap.ui.table.Table = table.clone("_s");
                        let model: any = table.getModel();
                        if (model instanceof sap.ui.model.json.JSONModel) {
                            nTable.setModel(new sap.extension.model.JSONModel(model.getData()));
                        }
                        nTable.setVisibleRowCount(count);
                        nTable.destroyExtension();
                        nTable.destroyFooter();
                        (<any>nTable).getToolbar()?.setVisible(false);
                        return nTable;
                    }
                    return null;
                }
            }
        }
    }
}