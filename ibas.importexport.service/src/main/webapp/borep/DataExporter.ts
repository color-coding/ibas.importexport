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
            constructor(name: string, contect: string) {
                super();
                this.fileName = name;
                this.content = contect;
            }
            /** 名称 */
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
                    name = ibas.objects.getName(ibas.objects.getType(data));
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
                        caller.onCompleted(opRsltRS);
                    }
                });
            }
        }
    }
}