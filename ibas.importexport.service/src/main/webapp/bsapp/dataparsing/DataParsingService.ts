/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {
        /** 文件解析服务 */
        export class FileParsingService extends ibas.ServiceWithResultApplication<IFileParsingServiceView, IFileParsingServiceContract, any>  {
            /** 应用标识 */
            static APPLICATION_ID: string = "6e238060-b352-4699-af04-b1f1bbe051a1";
            /** 应用名称 */
            static APPLICATION_NAME: string = "importexport_service_fileparsing";
            constructor() {
                super();
                this.id = FileParsingService.APPLICATION_ID;
                this.name = FileParsingService.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 运行服务 */
            runService(contract: IFileParsingServiceContract): void {
                if (!ibas.objects.isNull(contract)) {
                    if (ibas.strings.isEmpty(contract.outType)) {
                        contract.outType = "json";
                    }
                    this.outType = contract.outType;
                    this.sheets = contract.sheets;
                    if (contract.file instanceof Blob) {
                        this.parsing(contract.file);
                    } else {
                        if (ibas.strings.equalsIgnoreCase(this.outType, "json")) {
                            this.view.showFileDialog("application/json");
                        } else if (ibas.strings.equalsIgnoreCase(this.outType, "table")
                            || ibas.strings.equalsIgnoreCase(this.outType, "array")) {
                            // 文件过大，可能导致浏览器崩溃
                            this.view.showFileDialog("text/csv,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel.sheet.macroenabled.12");
                        } else {
                            this.view.showFileDialog("");
                        }
                    }
                } else {
                    // 输入数据无效，服务不运行
                    this.proceeding(ibas.emMessageType.WARNING,
                        ibas.i18n.prop("importexport_service_fileparsing") + ibas.i18n.prop("sys_invalid_parameter", "contract"));
                }
            }
            /** 注册视图 */
            protected registerView(): void {
                super.registerView();
                this.view.parsingEvent = this.parsing;
            }
            private outType: string;
            private sheets: string[];
            /** 视图显示后 */
            protected viewShowed(): void {
                // 视图加载完成
            }
            protected parsing(file: Blob): void {
                if (ibas.strings.equalsIgnoreCase(this.outType, "json")) {
                    let reader: FileReader = new FileReader();
                    reader.onload = (event) => {
                        try {
                            if (typeof (reader.result) === "string") {
                                let data: any = JSON.parse(reader.result);
                                this.fireCompleted(data);
                            }
                        } catch (error) {
                            this.messages(new Error(event.target?.error?.message));
                        }
                    };
                    reader.onerror = (event) => {
                        this.messages(new Error(event.target?.error?.message));
                    };
                    reader.readAsText(file);
                } else if (ibas.strings.equalsIgnoreCase(this.outType, "table")
                    || ibas.strings.equalsIgnoreCase(this.outType, "array")) {
                    ibas.requires.create({
                        context: ibas.requires.naming(importexport.CONSOLE_NAME),
                        paths: {
                            xlsx: ["3rdparty/sheetjs/xlsx.full.min"]
                        }
                    })(["xlsx"], (xlsx: any) => {
                        try {
                            let reader: FileReader = new FileReader();
                            reader.onload = (event) => {
                                let workbook: XLSX.WorkBook;
                                let data: any = event.target.result;
                                if (file.type === "text/csv") {
                                    let sData: string = cptable.utils.decode(936, data);
                                    workbook = XLSX.read(sData, { type: "string" });
                                } else {
                                    workbook = XLSX.read(data, { type: "array" });
                                }
                                if (workbook.SheetNames.length > 0) {
                                    let sheetNames: string[] = this.sheets;
                                    if (ibas.objects.isNull(sheetNames)) {
                                        sheetNames = [
                                            workbook.SheetNames[0]
                                        ];
                                    }
                                    if (sheetNames instanceof Array && sheetNames.length === 0) {
                                        for (let item of workbook.SheetNames) {
                                            sheetNames.push(item);
                                        }
                                    }
                                    let results: Map<string, any> = new Map<string, any>();
                                    for (let sheetName of sheetNames) {
                                        let sheet: XLSX.WorkSheet = workbook.Sheets[sheetName];
                                        if (ibas.strings.equalsIgnoreCase(this.outType, "array")) {
                                            // 直接表格输出
                                            results.set(sheetName, XLSX.utils.sheet_to_json(sheet, {
                                                header: 1,
                                                blankrows: true,
                                                defval: null,
                                            }));
                                        } else {
                                            // 转为table
                                            let datas: any[] = XLSX.utils.sheet_to_json(sheet);
                                            let table: ibas.DataTable = new ibas.DataTable();
                                            for (let data of datas) {
                                                if (table.columns.length === 0) {
                                                    for (let item in data) {
                                                        if (item) {
                                                            let column: ibas.DataTableColumn = new ibas.DataTableColumn();
                                                            column.name = item;
                                                            table.columns.add(column);
                                                        }
                                                    }
                                                }
                                                let row: ibas.DataTableRow = new ibas.DataTableRow();
                                                for (let item of table.columns) {
                                                    row.cells.add(data[item.name]);
                                                }
                                                table.rows.add(row);
                                            }
                                            results.set(sheetName, table);
                                        }
                                    }
                                    if (ibas.objects.isNull(this.sheets)) {
                                        // 没有指定页签时，仅返回第一个数据
                                        if (results.size > 0) {
                                            this.fireCompleted(results.values().next().value);
                                        } else {
                                            this.fireCompleted(null);
                                        }
                                    } else {
                                        this.fireCompleted(results);
                                    }
                                }
                            };
                            reader.onerror = (event) => {
                                this.messages(new Error(event.target?.error?.message));
                            };
                            if (file.type === "text/csv") {
                                reader.readAsBinaryString(file);
                            } else {
                                reader.readAsArrayBuffer(file);
                            }
                        } catch (error) {
                            this.messages(error);
                        }
                    }, (error: RequireError) => {
                        this.messages(error);
                    });
                } else {
                    // 不支持
                    this.messages(new Error(ibas.i18n.prop("sys_unsupported_operation")));
                }
            }
        }
        /** 文件解析服务-视图 */
        export interface IFileParsingServiceView extends ibas.IView {
            /** 显示文件对话框 */
            showFileDialog(extensions: string): void;
            /** 解析 */
            parsingEvent: Function;
        }
        /** 文件解析服务映射 */
        export class FileParsingServiceMapping extends ibas.ServiceMapping {
            constructor() {
                super();
                this.id = FileParsingService.APPLICATION_ID;
                this.name = FileParsingService.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
                this.proxy = FileParsingServiceProxy;
            }
            /** 创建服务实例 */
            create(): ibas.IService<ibas.IServiceContract> {
                return new FileParsingService();
            }
        }
    }
}