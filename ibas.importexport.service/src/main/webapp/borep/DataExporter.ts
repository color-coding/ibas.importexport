/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace bo {
        /** 数据导出者 */
        export abstract class DataExporter<T extends IDataExportResult> implements bo.IDataExporter {
            /** 名称 */
            name: string;
            /** 描述 */
            description: string;
            /** 导出 */
            abstract export(caller: bo.IDataExportCaller<T>): void;
        }
        /** 数据导出结果 */
        export interface IDataExportResult {
            /** 内容 */
            content: any;
        }
        /** 数据导出结果 */
        export abstract class DataExportResult<T> implements IDataExportResult {
            /** 内容 */
            content: T;
        }
        /** 数据导出结果-文件 */
        export class DataExportResultBlob extends DataExportResult<Blob> {
            constructor(contect: Blob) {
                super();
                this.content = contect;
            }
            /** 名称 */
            fileName: string;
        }
        /** 数据导出结果-文件 */
        export class DataExportResultString extends DataExportResult<string> {
            constructor();
            constructor(name: string, contect: string);
            constructor() {
                super();
                this.fileName = arguments[0];
                this.content = arguments[1];
            }
            /** 文件名称 */
            fileName: string;
        }
        /** 数据导出者-json */
        export class DataExporterJson extends DataExporter<DataExportResultString> {
            static MODE_SIGN: string = "TO_FILE_JSON";
            constructor() {
                super();
                this.name = DataExporterJson.MODE_SIGN;
                this.description = ibas.i18n.prop("importexport_export_json");
            }
            /** 导出 */
            export(caller: bo.IDataExportCaller<DataExportResultString>): void {
                if (ibas.objects.isNull(caller)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "caller"));
                }
                if (ibas.objects.isNull(caller.data)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "caller.data"));
                }
                let name: string = "unknown";
                let data: any = caller.data[0];
                if (!ibas.objects.isNull(data)) {
                    name = ibas.objects.nameOf(data);
                }
                if (name === "Object" && !ibas.objects.isNull(data[ibas.REMOTE_OBJECT_TYPE_PROPERTY_NAME])) {
                    name = data[ibas.REMOTE_OBJECT_TYPE_PROPERTY_NAME];
                }
                name = ibas.strings.format("{0}_{1}.json", name, ibas.uuids.random());
                if (caller.onCompleted instanceof Function) {
                    let opRslt: ibas.OperationResult<DataExportResultString> = new ibas.OperationResult<DataExportResultString>();
                    opRslt.addResults(new DataExportResultString(name, JSON.stringify(caller.data)));
                    caller.onCompleted(opRslt);
                }
            }
        }
        /** 数据导出者-服务 */
        export class DataExporterService extends DataExporter<IDataExportResult> {
            constructor() {
                super();
            }
            /** 模板 */
            template: string;
            /** 导出 */
            export(caller: bo.IDataExportCaller<IDataExportResult>): void {
                let that: this = this;
                let boRepository: bo.BORepositoryImportExport = new bo.BORepositoryImportExport();
                boRepository.export({
                    transformer: this.name,
                    template: this.template,
                    content: caller.data,
                    criteria: caller.criteria,
                    contentType: caller.contentType,
                    onCompleted(opRslt: ibas.IOperationResult<Blob>): void {
                        if (!(caller.onCompleted instanceof Function)) {
                            return;
                        }
                        let opRsltRS: ibas.OperationResult<IDataExportResult> = new ibas.OperationResult<IDataExportResult>();
                        if (opRslt.resultCode === 0) {
                            for (let item of opRslt.resultObjects) {
                                let result: DataExportResultBlob = new DataExportResultBlob(item);
                                for (let item of opRslt.informations) {
                                    if (ibas.strings.isEmpty(item.content)) {
                                        continue;
                                    }
                                    let index: number = item.content.indexOf("filename");
                                    if (index >= 0) {
                                        let value: string = item.content.substring(index + 8);
                                        if (value.startsWith("=")) {
                                            value = value.substring(1);
                                        }
                                        result.fileName = value;
                                    }
                                }
                                if (ibas.strings.isEmpty(result.fileName)) {
                                    result.fileName = ibas.strings.format("{0}.{1}", ibas.uuids.random(), that.name.substring(that.name.lastIndexOf("_") + 1).toLowerCase());
                                }
                                opRsltRS.resultObjects.add(result);
                            }
                        } else {
                            opRsltRS.resultCode = opRslt.resultCode;
                            opRsltRS.message = opRslt.message;
                        }
                        caller.onCompleted(opRsltRS);
                    }
                });
            }
        }
        /** 数据表导出者-json */
        export class DataTableExporterJson extends DataExporter<DataExportResultString> {
            static MODE_SIGN: string = "TO_FILE_JSON";
            constructor() {
                super();
                this.name = DataTableExporterJson.MODE_SIGN;
                this.description = ibas.i18n.prop("importexport_export_json");
            }
            /** 导出 */
            export(caller: bo.IDataExportCaller<DataExportResultString>): void {
                if (ibas.objects.isNull(caller)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "caller"));
                }
                if (!(caller.data instanceof ibas.DataTable)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "caller.data"));
                }
                let data: any[] = caller.data.convert();
                let name: string = caller.data.description;
                if (ibas.strings.isEmpty(name)) {
                    name = "datatable";
                }
                name = ibas.strings.format("{0}_{1}.json", name, ibas.dates.toString(ibas.dates.now(), "yyyyMMddHHss"));
                if (caller.onCompleted instanceof Function) {
                    let opRslt: ibas.OperationResult<DataExportResultString> = new ibas.OperationResult<DataExportResultString>();
                    opRslt.addResults(new DataExportResultString(name, JSON.stringify(data)));
                    caller.onCompleted(opRslt);
                }
            }
        }
        /** 数据表导出者-Excel */
        export abstract class DataTableExporterSheetJS extends DataExporter<DataExportResultBlob> {
            static MODE_SIGN: string = "TO_FILE_XLSX";
            constructor() {
                super();
                this.name = DataTableExporterXLSX.MODE_SIGN;
                this.description = ibas.i18n.prop("importexport_export_xlsx");
            }
            export(caller: IDataExportCaller<DataExportResultBlob>): void {
                if (ibas.objects.isNull(caller)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "caller"));
                }
                if (!(caller.data instanceof ibas.DataTable)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "caller.data"));
                }
                let table: ibas.DataTable = caller.data;
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
                if (caller.onCompleted instanceof Function) {
                    ibas.requires.create({
                        context: ibas.requires.naming(importexport.CONSOLE_NAME),
                        paths: {
                            xlsx: ["3rdparty/sheetjs/xlsx.full.min"]
                        }
                    })(["xlsx"], (xlsx: any) => {
                        let workBook: XLSX.WorkBook = XLSX.utils.book_new();
                        let sheet: XLSX.Sheet = XLSX.utils.aoa_to_sheet(sheetDatas);
                        XLSX.utils.book_append_sheet(workBook, sheet, !ibas.strings.isEmpty(table.description) ? table.description : table.name);
                        let outWorkBook: any = XLSX.write(workBook, this.writingOptions());
                        let result: DataExportResultBlob = new DataExportResultBlob(new Blob([outWorkBook], { type: "application/octet-stream" }));
                        result.fileName = table.description;
                        if (ibas.strings.isEmpty(result.fileName)) {
                            result.fileName = "datatable";
                        }
                        result.fileName = ibas.strings.format("{0}_{1}.{2}",
                            result.fileName, ibas.dates.toString(ibas.dates.now(), "yyyyMMddHHss"), this.writingOptions().bookType);
                        caller.onCompleted(new ibas.OperationResult<DataExportResultBlob>().addResults(result));
                    }, (error: RequireError) => {
                        caller.onCompleted(new ibas.OperationResult<DataExportResultBlob>(error));
                    });
                }
            }
            protected abstract writingOptions(): any;
        }
        /** 数据表导出者-csv */
        export class DataTableExporterCSV extends DataTableExporterSheetJS {
            static MODE_SIGN: string = "TO_FILE_CSV";
            constructor() {
                super();
                this.name = DataTableExporterCSV.MODE_SIGN;
                this.description = ibas.i18n.prop("importexport_export_csv");
            }
            protected writingOptions(): any {
                return {
                    bookType: "csv",
                    bookSST: false,
                    type: "array",
                    compression: false,
                };
            }

        }
        /** 数据表导出者-Excel */
        export class DataTableExporterXLSX extends DataTableExporterSheetJS {
            static MODE_SIGN: string = "TO_FILE_XLSX";
            constructor() {
                super();
                this.name = DataTableExporterXLSX.MODE_SIGN;
                this.description = ibas.i18n.prop("importexport_export_xlsx");
            }
            protected writingOptions(): any {
                return {
                    bookType: "xlsx",
                    bookSST: false,
                    type: "array",
                    compression: true,
                };
            }
        }
    }
}