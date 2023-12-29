/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {
        /** 应用-审批流程 */
        export class ViewExportApp extends ibas.ResidentApplication<IViewExportView> {
            /** 应用标识 */
            static APPLICATION_ID: string = "83027f70-f457-45b6-bcec-ad4b8bc65a5d";
            /** 应用名称 */
            static APPLICATION_NAME: string = "importexport_app_viewexport";
            /** 构造函数 */
            constructor() {
                super();
                this.id = ViewExportApp.APPLICATION_ID;
                this.name = ViewExportApp.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 注册视图 */
            protected registerView(): void {
                super.registerView();
                // 其他事件
                this.view.exportEvent = this.export;
            }
            /** 运行,覆盖原方法 */
            run(): void {
                super.run.apply(this, arguments);
            }
            /** 视图显示后 */
            protected viewShowed(): void {
                // 视图加载完成
                this.view.showTables();
            }
            private export(tables: ibas.DataTable[] | ibas.DataTable): void {
                tables = ibas.arrays.create(tables);
                if (tables.length === 0) {
                    this.messages(ibas.emMessageType.WARNING, ibas.i18n.prop("shell_please_chooose_data",
                        ibas.i18n.prop("importexport_export")
                    )); return;
                }
                // 加载类库
                ibas.requires.create({
                    context: ibas.requires.naming(importexport.CONSOLE_NAME),
                    paths: {
                        xlsx: ["3rdparty/sheetjs/xlsx.full.min"]
                    }
                })(['xlsx'], (xlsx: any) => {
                    try {
                        let workBook: XLSX.WorkBook = XLSX.utils.book_new();
                        for (let table of <any>tables) {
                            if (table instanceof ibas.DataTable) {
                                let sheetDatas: Array<any> = new Array<any>();
                                let row: Array<any> = new Array<any>();
                                for (let item of table.columns) {
                                    row.push(!ibas.strings.isEmpty(item.description) ? item.description : item.name);
                                }
                                if (row.length > 0) {
                                    sheetDatas.push(row);
                                }
                                for (let rItem of table.rows) {
                                    row = new Array<any>();
                                    for (let cItem of table.columns) {
                                        let cellValue: string = rItem.cells[table.columns.indexOf(cItem)];
                                        if (cItem.definedDataType() === ibas.emTableDataType.DECIMAL
                                            || cItem.definedDataType() === ibas.emTableDataType.NUMERIC) {
                                            row.push(ibas.numbers.valueOf(cellValue));
                                        } else {
                                            row.push(cellValue);
                                        }
                                    }
                                    sheetDatas.push(row);
                                }
                                let sheet: XLSX.Sheet = XLSX.utils.aoa_to_sheet(sheetDatas);
                                // 树形结构缩进
                                if (table.columns[0]?.name === "$LEVEL" && table.columns.length > 1) {
                                    for (let i: number = 2; i <= table.rows.length + 1; i++) {
                                        if (!sheet["B" + i].s) {
                                            sheet["B" + i].s = {};
                                        }
                                        sheet["B" + i].s.alignment = { indent: table.rows[i - 2].cells[0] };
                                    }
                                }
                                XLSX.utils.book_append_sheet(workBook, sheet, !ibas.strings.isEmpty(table.description) ? table.description : table.name);
                            }
                        }
                        let outWorkBook: any = XLSX.write(workBook, {
                            bookType: "xlsx",
                            bookSST: false,
                            type: "array",
                            compression: true,
                        });
                        ibas.files.save(new Blob([outWorkBook]), ibas.strings.format("table_{0}.xlsx", ibas.dates.toString(ibas.dates.now(), "yyyyMMddHHss")));
                    } catch (error) {
                        this.messages(error);
                    }
                }, (error: RequireError) => {
                    this.messages(error);
                });
            }
        }
        /** 视图-审批流程 */
        export interface IViewExportView extends ibas.IResidentView {
            // 导出
            exportEvent: Function;
            /** 显示表格 */
            showTables(): void;
        }
        export class ViewExportApplicationMapping extends ibas.ResidentApplicationMapping {
            /** 构造函数 */
            constructor() {
                super();
                this.id = ViewExportApp.APPLICATION_ID;
                this.name = ViewExportApp.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
            }
            create(): ibas.ResidentApplication<ibas.IResidentView> {
                return new ViewExportApp();
            }
        }
    }
}