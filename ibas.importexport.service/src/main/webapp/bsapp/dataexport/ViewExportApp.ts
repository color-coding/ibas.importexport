/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {

        const CONFIG_ITEM_EXCEL_OUTPUT_LIBRARY: string = "xlsxWriteLibrary";

        export enum emExportMode {
            /** 当前全部 */
            CURRENT,
            /** 选择的 */
            SELECTED,
            /** 全部 */
            ALL,
        }
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
            exportMode: emExportMode = emExportMode.CURRENT;
            protected showFullView(mode?: emExportMode): void {
                super.showFullView();
                if (mode > 0) {
                    this.exportMode = mode;
                } else {
                    this.exportMode = emExportMode.CURRENT;
                }
                this.view.showTables(this.exportMode);
            }
            /** 视图显示后 */
            protected viewShowed(): void {
                // 视图加载完成
            }
            private export(tables: ibas.DataTable[] | ibas.DataTable): void {
                tables = ibas.arrays.create(tables);
                if (tables.length === 0) {
                    this.messages(ibas.emMessageType.WARNING, ibas.i18n.prop("shell_please_chooose_data",
                        ibas.i18n.prop("importexport_export")
                    )); return;
                }
                let writeType: string = config.get(CONFIG_ITEM_EXCEL_OUTPUT_LIBRARY);
                if (tables instanceof Array) {
                    if (tables.find(c => c.columns.find(d => ibas.strings.equalsIgnoreCase(d.dataType, "image")))) {
                        writeType = "ExcelJS";
                    }
                }
                let imageWidth: number = 15;
                let imageHeight: number = 55;
                if (ibas.strings.equalsIgnoreCase(writeType, "ExcelJS")) {
                    // 加载类库-ExcelJs
                    ibas.requires.create({
                        context: ibas.requires.naming(importexport.CONSOLE_NAME),
                        paths: {
                            exceljs: ["3rdparty/exceljs/exceljs-bundle"]
                        }
                    })(["exceljs"], () => {
                        let images: Map<string, number> = new Map<string, number>();
                        let workbook: ExcelJS.Workbook = new ExcelJS.Workbook();
                        for (let table of <any>tables) {
                            if (table instanceof ibas.DataTable) {
                                let worksheet: ExcelJS.Worksheet
                                    = workbook.addWorksheet(!ibas.strings.isEmpty(table.description) ? table.description : table.name);
                                let columns: any[] = [];
                                for (let item of table.columns) {
                                    // 跳过隐藏列
                                    if (ibas.strings.isWith(item.name, ".", undefined)) {
                                        continue;
                                    }
                                    columns.push({
                                        header: !ibas.strings.isEmpty(item.description) ? item.description : item.name,
                                        key: item.name,
                                    });
                                }
                                worksheet.columns = columns;
                                for (let rItem of table.rows) {
                                    let row: any = {};
                                    for (let cItem of table.columns) {
                                        // 跳过隐藏列
                                        if (ibas.strings.isWith(cItem.name, ".", undefined)) {
                                            continue;
                                        }
                                        let cellValue: string = rItem.cells[table.columns.indexOf(cItem)];
                                        if (cItem.definedDataType() === ibas.emTableDataType.DECIMAL
                                            || cItem.definedDataType() === ibas.emTableDataType.NUMERIC) {
                                            row[cItem.name] = ibas.numbers.valueOf(cellValue);
                                        } else if (ibas.strings.equalsIgnoreCase(cItem.dataType, "image")) {
                                            if (ibas.strings.isWith(cellValue, "http://", undefined)
                                                || ibas.strings.isWith(cellValue, "https://", undefined)) {
                                                images.set(cellValue, -1);
                                            }
                                            row[cItem.name] = cellValue;
                                        } else {
                                            row[cItem.name] = cellValue;
                                        }
                                    }
                                    let wsRow: ExcelJS.Row = worksheet.addRow(row);
                                    if (images.size > 0) {
                                        wsRow.height = imageHeight;
                                    }
                                }
                            }
                        }
                        // 下载所需的图片
                        ibas.queues.execute(Array.from(images.keys()), (imgUrl, next) => {
                            if (ibas.strings.isWith(imgUrl, "http://", undefined)
                                || ibas.strings.isWith(imgUrl, "https://", undefined)) {
                                fetch(imgUrl).then((response) => {
                                    if (!response.ok) {
                                        ibas.logger.log(response.statusText);
                                        next();
                                    } else {
                                        response.arrayBuffer().then((buffer) => {
                                            try {
                                                images.set(imgUrl, workbook.addImage({
                                                    extension: <any>imgUrl.match(/\.([a-zA-Z0-9]+)(?=[?#]|$)/)?.[1],
                                                    buffer: buffer
                                                }));
                                            } catch (error) {
                                                ibas.logger.log(error);
                                            }
                                            next();
                                        }).catch((reason) => {
                                            ibas.logger.log(reason.toString());
                                            next();
                                        });
                                    }
                                }).catch((reason) => {
                                    ibas.logger.log(reason.toString());
                                    next();
                                });
                            } else {
                                next();
                            }
                        }, (error) => {
                            // 替换地址为图片
                            for (let worksheet of workbook.worksheets) {
                                for (let col: number = 0; col < worksheet.columns.length; col++) {
                                    let workColumn: Partial<ExcelJS.Column> = worksheet.columns[col];
                                    if (tables instanceof Array
                                        && tables.find(c => c.columns.find(d =>
                                            d.name === workColumn.key && ibas.strings.equalsIgnoreCase(d.dataType, "image"))
                                        )) {
                                        workColumn.width = imageWidth;
                                        workColumn.eachCell((cell, row) => {
                                            if (images.has(String(cell.value)) && images.get(String(cell.value)) > -1) {
                                                worksheet.addImage(images.get(String(cell.value)), <any>{
                                                    tl: { col: col, row: row - 1, offset: 0 },
                                                    br: { col: col + 1, row: row, offset: 0 },
                                                    editAs: "oneCell",
                                                    /*
                                                    hyperlinks: {
                                                        hyperlink: cell.value,
                                                        tooltip: cell.value.toString().split("?")[0]
                                                    }
                                                    */
                                                });
                                                cell.value = undefined;
                                            }
                                        });
                                    }
                                }
                            }
                            workbook.xlsx.writeBuffer().then((buffer) => {
                                ibas.files.save(new Blob([buffer], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" }),
                                    ibas.strings.format("{0}_{1}.xlsx", (
                                        this.view.title?.indexOf(" - ") >= 0 ? this.view.title.substring(this.view.title?.lastIndexOf(" - ") + 3) : "table_"), ibas.dates.toString(ibas.dates.now(), "yyyyMMddHHss")
                                    )
                                );
                            }).catch((error) => {
                                this.messages(error);
                            });
                        });
                    }, (error: RequireError) => {
                        this.messages(error);
                    });
                } else {
                    // 加载类库-SheetJs
                    ibas.requires.create({
                        context: ibas.requires.naming(importexport.CONSOLE_NAME),
                        paths: {
                            xlsx: ["3rdparty/sheetjs/xlsx.full.min"]
                        }
                    })(["xlsx"], (xlsx: any) => {
                        try {
                            let workBook: XLSX.WorkBook = XLSX.utils.book_new();
                            for (let table of <any>tables) {
                                if (table instanceof ibas.DataTable) {
                                    let sheetDatas: Array<any> = new Array<any>();
                                    let row: Array<any> = new Array<any>();
                                    for (let item of table.columns) {
                                        // 跳过隐藏列
                                        if (ibas.strings.isWith(item.name, ".", undefined)) {
                                            continue;
                                        }
                                        row.push(!ibas.strings.isEmpty(item.description) ? item.description : item.name);
                                    }
                                    if (row.length > 0) {
                                        sheetDatas.push(row);
                                    }
                                    for (let rItem of table.rows) {
                                        row = new Array<any>();
                                        for (let cItem of table.columns) {
                                            // 跳过隐藏列
                                            if (ibas.strings.isWith(cItem.name, ".", undefined)) {
                                                continue;
                                            }
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
                            ibas.files.save(new Blob([outWorkBook]), ibas.strings.format("{0}_{1}.xlsx", (
                                this.view.title?.indexOf(" - ") >= 0 ? this.view.title.substring(this.view.title?.lastIndexOf(" - ") + 3) : "table_"
                            ), ibas.dates.toString(ibas.dates.now(), "yyyyMMddHHss")));
                        } catch (error) {
                            this.messages(error);
                        }
                    }, (error: RequireError) => {
                        this.messages(error);
                    });

                }
            }
        }
        /** 视图-审批流程 */
        export interface IViewExportView extends ibas.IResidentView {
            // 导出
            exportEvent: Function;
            /** 显示表格 */
            showTables(mode?: emExportMode): void;
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