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
        export abstract class DataExporter<T> implements bo.IDataExporter {
            /** 名称 */
            name: string;
            /** 描述 */
            description: string;
            /** 导出 */
            abstract export(caller: bo.IDataExportCaller<T>): void;
        }
        /** 数据导出结果 */
        export class DataExportResult<T> {
            constructor(name: string, contect: T);
            constructor() {
                this.name = arguments[0];
                this.content = arguments[1];
            }
            /** 名称 */
            name: string;
            /** 内容 */
            content?: T;
        }
        /** 数据导出者-json */
        export class DataExporterJson extends DataExporter<DataExportResult<string>> {
            static MODE_SIGN: string = "TO_FILE_JSON";
            constructor() {
                super();
                this.name = DataExporterJson.MODE_SIGN;
                this.description = ibas.i18n.prop("importexport_export_json");
            }
            /** 导出 */
            export(caller: bo.IDataExportCaller<DataExportResult<string>>): void {
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
                name = ibas.strings.format("{0}_{1}.json", name, ibas.dates.toString(new Date(), "yyyyMMdd_HHmmss"));
                if (caller.onCompleted instanceof Function) {
                    let opRslt: ibas.OperationResult<DataExportResult<string>> = new ibas.OperationResult<DataExportResult<string>>();
                    opRslt.addResults(new DataExportResult<string>(name, JSON.stringify(caller.data)));
                    caller.onCompleted(opRslt);
                }
            }
        }
        /** 数据导出者-服务 */
        export class DataExporterService extends DataExporter<Blob> {
            constructor() {
                super();
            }
            /** 模板 */
            template: string;
            /** 导出 */
            export(caller: bo.IDataExportCaller<Blob>): void {
                let boRepository: bo.BORepositoryImportExport = new bo.BORepositoryImportExport();
                boRepository.export({
                    transformer: this.name,
                    template: this.template,
                    content: caller.data,
                    criteria: caller.criteria,
                    onCompleted: caller.onCompleted,
                });
            }
        }
    }
}