/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {
        /** 数据导出结果 */
        export interface IExportResult {
            /** 导出方法 */
            mode: string;
            /** 文件地址 */
            address: string;
            /** 内容 */
            content?: any;
        }
        /** 数据导出调用者 */
        export interface IDataExportCaller {
            /** 导出的数据 */
            datas: any[];
            /** 导出完成 */
            onCompleted(result: IExportResult): void;
        }
        /** 数据导出方式 */
        export interface IDataExportMode {
            /** 名称 */
            name: string;
            /** 描述 */
            description: string;
            /** 导出 */
            export(caller: IDataExportCaller): void;
        }
        /** 数据导出方式 */
        export abstract class DataExportMode implements IDataExportMode {
            /** 名称 */
            name: string;
            /** 描述 */
            description: string;
            /** 导出 */
            abstract export(caller: IDataExportCaller): void;
        }
        /** 数据导出方式-json */
        export class DataExportModeJson extends DataExportMode {
            static MODE_SIGN = "export_json";
            constructor() {
                super();
                this.name = DataExportModeJson.MODE_SIGN;
                this.description = ibas.i18n.prop("importexport_export_json");
            }
            /** 导出 */
            export(caller: IDataExportCaller): void {
                if (ibas.objects.isNull(caller)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "caller"));
                }
                if (ibas.objects.isNull(caller.datas)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "caller.datas"));
                }
                let name: string = "unknown";
                let data: any = caller.datas[0];
                if (!ibas.objects.isNull(data)) {
                    name = ibas.objects.getName(ibas.objects.getType(data));
                }
                if (name === "Object" && !ibas.objects.isNull(data[ibas.REMOTE_OBJECT_TYPE_PROPERTY_NAME])) {
                    name = data[ibas.REMOTE_OBJECT_TYPE_PROPERTY_NAME];
                }
                name = ibas.strings.format("{0}_{1}.json", name, ibas.dates.toString(new Date(), "yyyyMMdd_HHmmss"));
                let result: IExportResult = {
                    mode: this.name,
                    address: name,
                    content: JSON.stringify(caller.datas)
                };
                caller.onCompleted(result);
            }
        }
    }
}