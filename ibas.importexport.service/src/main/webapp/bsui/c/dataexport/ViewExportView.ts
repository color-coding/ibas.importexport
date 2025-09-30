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
                    return new sap.m.MenuButton("", {
                        tooltip: this.title,
                        icon: "sap-icon://activate",
                        type: sap.m.ButtonType.Transparent,
                        useDefaultActionOnly: false,
                        buttonMode: sap.m.MenuButtonMode.Regular,
                        menuPosition: sap.ui.core.Popup.Dock.EndBottom,
                        menu: new sap.m.Menu("", {
                            items: [
                                new sap.m.MenuItem("", {
                                    icon: "sap-icon://action",
                                    text: ibas.i18n.prop("importexport_export_content_current"),
                                    press: function (): void {
                                        if (!that.isDisplayed) {
                                            that.fireViewEvents(that.showFullViewEvent, app.emExportMode.CURRENT);
                                        }
                                    }
                                }),
                                new sap.m.MenuItem("", {
                                    icon: "sap-icon://action",
                                    text: ibas.i18n.prop("importexport_export_content_selected"),
                                    press: function (): void {
                                        if (!that.isDisplayed) {
                                            that.fireViewEvents(that.showFullViewEvent, app.emExportMode.SELECTED);
                                        }
                                    }
                                }),
                                new sap.m.MenuItem("", {
                                    icon: "sap-icon://action",
                                    text: ibas.i18n.prop("importexport_export_content_all"),
                                    press: function (): void {
                                        if (!that.isDisplayed) {
                                            that.fireViewEvents(that.showFullViewEvent, app.emExportMode.ALL);
                                        }
                                    }
                                }),
                                new sap.m.MenuItem("", {
                                    icon: "sap-icon://resize-horizontal",
                                    text: ibas.i18n.prop("importexport_resize_column"),
                                    press: function (): void {
                                        delete (that.exportMode);
                                        let app: any = sap.ui.getCore().byId("__UI_APP");
                                        if (app instanceof sap.m.App) {
                                            let mainPage: any = app.getCurrentPage();
                                            if (mainPage instanceof sap.tnt.ToolPage) {
                                                let navContainer: any = mainPage.getMainContents()[0];
                                                if (navContainer instanceof sap.m.NavContainer) {
                                                    that.pasingPage(navContainer.getCurrentPage(), that.resizeColumn);
                                                } else if (navContainer instanceof sap.m.TabContainer) {
                                                    let page: any = sap.ui.getCore().byId(navContainer.getSelectedItem());
                                                    if (page instanceof sap.m.TabContainerItem) {
                                                        page = sap.ui.getCore().byId(page.getKey());
                                                        if (page instanceof sap.ui.core.Control) {
                                                            that.pasingPage(page, that.resizeColumn);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }),
                            ]
                        }),
                        defaultAction(): void {
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
                                text: ibas.i18n.prop("importexport_export_selected"),
                                type: sap.m.ButtonType.Attention,
                                press: function (): void {
                                    let table: ibas.DataTable;
                                    let tables: ibas.IList<ibas.DataTable> = new ibas.ArrayList<ibas.DataTable>();
                                    for (let item of that.dialog.getContent()) {
                                        if (item instanceof sap.extension.table.Table ||
                                            item instanceof sap.extension.table.TreeTable) {
                                            table = item.toDataTable(true);
                                            table.description = (<any>item).getToolbar()?.getContent()[0]?.getText();
                                            tables.add(table);
                                        }
                                    }
                                    if (tables.length > 0) {
                                        that.fireViewEvents(that.exportEvent, tables);
                                    }
                                }
                            }),
                            new sap.m.Button("", {
                                text: ibas.i18n.prop("importexport_export_all"),
                                type: sap.m.ButtonType.Accept,
                                press: function (): void {
                                    let table: ibas.DataTable;
                                    let tables: ibas.IList<ibas.DataTable> = new ibas.ArrayList<ibas.DataTable>();
                                    for (let item of that.dialog.getContent()) {
                                        if (item instanceof sap.extension.table.Table ||
                                            item instanceof sap.extension.table.TreeTable) {
                                            table = item.toDataTable();
                                            table.description = (<any>item).getToolbar()?.getContent()[0]?.getText();
                                            tables.add(table);
                                        }
                                    }
                                    if (tables.length > 0) {
                                        that.fireViewEvents(that.exportEvent, tables);
                                    }
                                }
                            }),
                            new sap.m.Button("", {
                                text: ibas.i18n.prop("shell_exit"),
                                type: sap.m.ButtonType.Reject,
                                press: function (): void {
                                    for (let item of that.dialog.getContent()) {
                                        if (item instanceof sap.ui.table.Table) {
                                            for (let col of item.getColumns()) {
                                                let menu: any = sap.ui.getCore().byId(col.getId() + "-menu");
                                                if (menu instanceof sap.ui.core.Element) {
                                                    menu.destroy();
                                                }
                                            }
                                        }
                                        item.destroy();
                                    }
                                    that.fireViewEvents(that.closeEvent);
                                    that.dialog.destroy();
                                }
                            }),
                        ],
                        afterClose(this: sap.m.Dialog): void {
                            delete (that.exportMode);
                            for (let item of this.getContent()) {
                                if (item instanceof sap.ui.core.Element) {
                                    item.destroy();
                                }
                            }
                        }
                    }).addStyleClass("sapUiNoContentPadding");
                }
                private dialog: sap.m.Dialog;
                /** 显示表格 */
                showTables(mode: app.emExportMode): void {
                    this.title = this.application.description;
                    this.exportMode = mode;
                    let app: any = sap.ui.getCore().byId("__UI_APP");
                    if (app instanceof sap.m.App) {
                        let mainPage: any = app.getCurrentPage();
                        if (mainPage instanceof sap.tnt.ToolPage) {
                            let navContainer: any = mainPage.getMainContents()[0];
                            if (navContainer instanceof sap.m.NavContainer) {
                                this.pasingPage(navContainer.getCurrentPage(), this.cloneTable);
                            } else if (navContainer instanceof sap.m.TabContainer) {
                                let page: any = sap.ui.getCore().byId(navContainer.getSelectedItem());
                                if (page instanceof sap.m.TabContainerItem) {
                                    page = sap.ui.getCore().byId(page.getKey());
                                    if (page instanceof sap.ui.core.Control) {
                                        this.pasingPage(page, this.cloneTable);
                                    }
                                }
                            }
                        }
                    }
                }
                protected exportMode: app.emExportMode;

                private pasingPage(page: sap.ui.core.Control, funcTask: (table: sap.ui.table.Table, exportMode?: app.emExportMode) => any): void {
                    if (page instanceof sap.m.Page) {
                        for (let item of page.getContent()) {
                            this.pasingPage(item, funcTask);
                        }
                        if (this.isDisplayed) {
                            if (!ibas.strings.isEmpty(page.getTitle()) && this.title.indexOf(" - ") < 0) {
                                this.title = ibas.strings.format("{0} - {1}", this.title, page.getTitle());
                                this.dialog.setTitle(this.title);
                            }
                        }
                    } else if (page instanceof sap.m.NavContainer) {
                        let curPage: any = page.getCurrentPage();
                        if (ibas.objects.isNull(curPage)) {
                            for (let item of page.getPages()) {
                                this.pasingPage(item, funcTask);
                            }
                        } else {
                            this.pasingPage(curPage, funcTask);
                        }
                    } else if (page instanceof sap.ui.layout.cssgrid.CSSGrid) {
                        for (let item of page.getItems()) {
                            this.pasingPage(item, funcTask);
                        }
                    } else if (page instanceof sap.ui.layout.Splitter) {
                        for (let item of page.getContentAreas()) {
                            this.pasingPage(item, funcTask);
                        }
                    } else if (page instanceof sap.m.ScrollContainer) {
                        for (let item of page.getContent()) {
                            this.pasingPage(item, funcTask);
                        }
                    } else if (page instanceof sap.m.SplitContainer) {
                        for (let item of page.getDetailPages()) {
                            this.pasingPage(item, funcTask);
                        }
                    } else if (page instanceof sap.m.Panel) {
                        for (let item of page.getContent()) {
                            this.pasingPage(item, funcTask);
                        }
                    } else if (page instanceof sap.m.FlexBox) {
                        for (let item of page.getItems()) {
                            this.pasingPage(item, funcTask);
                        }
                    } else if (page instanceof sap.ui.layout.DynamicSideContent) {
                        for (let item of page.getMainContent()) {
                            this.pasingPage(item, funcTask);
                        }
                    } else if (page instanceof sap.ui.layout.form.SimpleForm) {
                        for (let item of page.getContent()) {
                            if (item instanceof sap.ui.core.Control) {
                                this.pasingPage(item, funcTask);
                            }
                        }
                    } else if (page instanceof sap.m.TabContainer) {
                        let curPage: any = page.getSelectedItem();
                        for (let item of page.getItems()) {
                            if (!ibas.strings.isEmpty(curPage) && item.getId() !== curPage) {
                                continue;
                            }
                            if (this.isDisplayed) {
                                if (!ibas.strings.isEmpty(item.getName()) && this.title.indexOf(" - ") < 0) {
                                    this.title = ibas.strings.format("{0} - {1}", this.title, item.getName());
                                    this.dialog.setTitle(this.title);
                                }
                            }
                            for (let cItem of item.getContent()) {
                                this.pasingPage(cItem, funcTask);
                            }
                        }
                    } else if (page instanceof sap.m.IconTabBar) {
                        let selected: string = page.getSelectedKey();
                        for (let item of page.getItems()) {
                            if (item instanceof sap.ui.core.Control) {
                                this.pasingPage(item, funcTask);
                            } else if (item instanceof sap.m.IconTabFilter) {
                                if (!ibas.strings.isEmpty(selected) && item.getKey() !== selected) {
                                    continue;
                                }
                                for (let sItem of item.getContent()) {
                                    this.pasingPage(sItem, funcTask);
                                }
                            }
                        }
                    } else if (page instanceof sap.ui.table.Table) {
                        if (funcTask === this.cloneTable) {
                            let count: number = (<any>page)._getTotalRowCount();
                            if (!(count > 0) && this.dialog.getContent().length === 0) {
                                this.dialog.addContent(new sap.m.IllustratedMessage("", {
                                    illustrationType: sap.m.IllustratedMessageType.NoData,
                                }));
                            } else {
                                if (this.exportMode === app.emExportMode.ALL
                                    // 存在数据分页加载事件
                                    && (<any>page).mEventRegistry?.nextDataSet?.length > 0) {
                                    if (this.dialog.getContent().length === 0) {
                                        this.dialog.addContent(new sap.m.IllustratedMessage("", {
                                            illustrationType: sap.m.IllustratedMessageType.BeforeSearch,
                                            description: ibas.i18n.prop("shell_fetching_data"),
                                            busy: true,
                                        }));
                                    }
                                    this.dialog.getButtons()[0].setEnabled(false);
                                    this.dialog.getButtons()[1].setEnabled(false);
                                    this.completeTable(page, app.emExportMode.ALL);
                                } else {
                                    let nTable: sap.ui.table.Table = funcTask.call(this, page, this.exportMode);
                                    if (!ibas.objects.isNull(nTable)) {
                                        this.dialog.addContent(nTable);
                                        if (nTable instanceof sap.ui.table.TreeTable) {
                                            nTable.expandToLevel(99);
                                            setTimeout(() => {
                                                // 半秒后重置显示行数
                                                let count: number = (<any>nTable)._getTotalRowCount();
                                                if (count > 0) {
                                                    nTable.setVisibleRowCount(count);
                                                }
                                            }, 500);
                                        }
                                    }
                                }
                            }
                        } else {
                            funcTask.call(this, page);
                        }
                    }
                }

                private resizeColumn(table: sap.ui.table.Table): void {
                    for (let i: number = table.getColumns().length - 1; i >= 0; i--) {
                        let column: sap.ui.table.Column = table.getColumns()[i];
                        if (column.getVisible() === false) {
                            continue;
                        }
                        if (ibas.objects.isNull(column.getLabel())) {
                            continue;
                        }
                        table.autoResizeColumn(i);
                    }
                }

                private completeTable(table: sap.ui.table.Table, preLastData?: any): void {
                    // 对话框关闭不再加载数据
                    if (this.dialog.isOpen() !== true || this.dialog.isDestroyed()) {
                        return;
                    }
                    // 表格忙，则稍后再试
                    if (table.getBusy() === true) {
                        setTimeout(() => {
                            this.completeTable(table, preLastData);
                        }, 300);
                        return;
                    }
                    let lastData: any;
                    let model: any = table.getModel();
                    let path: string = table.getBinding("rows")?.getPath();
                    if (!ibas.strings.isEmpty(path)) {
                        if (ibas.strings.isWith(path, "/", undefined)) {
                            path = path.substring(1);
                        }
                        lastData = model.getData(path);
                        if (lastData instanceof Array && lastData.length > 0) {
                            lastData = lastData[lastData.length - 1];
                        } else {
                            lastData = undefined;
                        }
                    } else {
                        lastData = model.getData();
                    }
                    if (!ibas.objects.isNull(lastData)) {
                        if (preLastData === undefined
                            || (preLastData !== undefined && preLastData !== lastData)) {
                            (<any>table).fireNextDataSet({ data: lastData });
                            table.setBusy(true);
                            this.completeTable(table, lastData);
                            return;
                        }
                    }
                    let nTable: any = this.cloneTable(table, app.emExportMode.CURRENT);
                    if (!ibas.objects.isNull(nTable)) {
                        for (let item of this.dialog.getContent()) {
                            if (item instanceof sap.m.IllustratedMessage) {
                                this.dialog.removeContent(item);
                            }
                        }
                        this.dialog.addContent(nTable);
                        for (let item of this.dialog.getButtons()) {
                            item.setEnabled(true);
                        }
                        if (nTable instanceof sap.ui.table.TreeTable) {
                            nTable.expandToLevel(99);
                            setTimeout(() => {
                                // 半秒后重置显示行数
                                let count: number = (<any>nTable)._getTotalRowCount();
                                if (count > 0) {
                                    nTable.setVisibleRowCount(count);
                                }
                            }, 500);
                        }
                    }
                }
                private cloneTable(table: sap.ui.table.Table, exportMode: app.emExportMode): sap.ui.table.Table {
                    let count: number = (<any>table)._getTotalRowCount();
                    if (!(count > 0)) {
                        return null;
                    }
                    let model: any = table.getModel();
                    let nTable: sap.ui.table.Table = table.clone("_s");
                    if (nTable.getVisibleRowCountMode() === sap.ui.table.VisibleRowCountMode.Auto) {
                        nTable.setVisibleRowCountMode(sap.ui.table.VisibleRowCountMode.Fixed);
                    }
                    nTable.destroyExtension();
                    nTable.destroyFooter();
                    if ((<any>nTable).destroyToolbar instanceof Function) {
                        (<any>nTable).destroyToolbar();
                    }
                    if (model instanceof sap.ui.model.json.JSONModel) {
                        if (exportMode === app.emExportMode.SELECTED) {
                            let selecteds: any[] = [];
                            for (let i of table.getSelectedIndices()) {
                                selecteds.push(table.getContextByIndex(i)?.getObject());
                            }
                            let nData: any = {};
                            let path: string = table.getBinding("rows")?.getPath();
                            if (!ibas.strings.isEmpty(path)) {
                                let paths: string[] = [];
                                for (let item of path.split("/")) {
                                    if (ibas.strings.isEmpty(item)) {
                                        continue;
                                    }
                                    paths.push(item);
                                }
                                if (paths.length >= 1) {
                                    for (let item of paths) {
                                        if (paths.indexOf(item) === paths.length - 1) {
                                            nData[item] = selecteds;
                                        } else {
                                            nData[item] = {};
                                        }
                                    }
                                } else {
                                    nData = selecteds;
                                }
                            } else {
                                nData = selecteds;
                            }
                            nTable.setModel(new sap.extension.model.JSONModel(nData));
                            if (selecteds.length > 0) {
                                nTable.setVisibleRowCount(selecteds.length);
                            }
                        } else {
                            nTable.setModel(new sap.extension.model.JSONModel(model.getData()));
                            nTable.setVisibleRowCount(count);
                        }
                    }
                    for (let i: number = 0; i < table.getColumns().length; i++) {
                        let column: sap.ui.table.Column = table.getColumns()[i];
                        if (column.getFiltered()) {
                            let nColumn: sap.ui.table.Column = nTable.getColumns()[i];
                            nTable.filter(nColumn, column.getFilterValue());
                        }
                    }
                    return nTable;
                }
            }
        }
    }
}