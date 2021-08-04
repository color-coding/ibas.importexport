/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace bo {

        /** 数据转换者 */
        export class DataConverter extends ibas.DataConverter4j {
            /**
             * 解析业务对象数据
             * @param data 目标类型
             * @param sign 特殊标记
             * @returns 本地类型
             */
            parsing(data: any, sign: string): any {
                if (data.type === "DataExportInfo") {
                    let remoteData: IDataExportInfo = data;
                    let exporter: DataExporterService = new DataExporterService();
                    exporter.name = remoteData.Transformer;
                    exporter.template = remoteData.Template;
                    exporter.description = remoteData.Description;
                    if (ibas.strings.isEmpty(exporter.description)) {
                        exporter.description = exporter.name;
                    }
                    return exporter;
                } else if (data.type === "DataWrapping") {
                    return JSON.parse(data.Content);
                }
                return super.parsing(data, sign);
            }
            /** 创建业务对象转换者 */
            protected createConverter(): ibas.BOConverter {
                return new BOConverter;
            }
        }
        /** 数据导出信息 */
        export interface IDataExportInfo {
            /** 转换者 */
            Transformer: string;
            /** 模板 */
            Template: string;
            /** 描述 */
            Description: string;
        }
        /** 模块业务对象工厂 */
        export const boFactory: ibas.BOFactory = new ibas.BOFactory();
        /** 业务对象转换者 */
        class BOConverter extends ibas.BOConverter {
            /** 模块对象工厂 */
            protected factory(): ibas.BOFactory {
                return boFactory;
            }

            /**
             * 自定义解析
             * @param data 远程数据
             * @returns 本地数据
             */
            protected customParsing(data: any): ibas.IBusinessObject {
                return data;
            }

            /**
             * 转换数据
             * @param boName 对象名称
             * @param property 属性名称
             * @param value 值
             * @returns 转换的值
             */
            protected convertData(boName: string, property: string, value: any): any {
                if (boName === bo.ExportTemplateItem.name) {
                    if (property === bo.ExportTemplateItem.PROPERTY_AREA_NAME) {
                        return ibas.enums.toString(emAreaType, value);
                    } else if (property === bo.ExportTemplateItem.PROPERTY_TEXTSTYLE_NAME) {
                        return ibas.enums.toString(emTextStyle, value);
                    } else if (property === bo.ExportTemplateItem.PROPERTY_TEXTSEGMENT_NAME) {
                        return ibas.enums.toString(emTextSegment, value);
                    } else if (property === bo.ExportTemplateItem.PROPERTY_SOURCETYPE_NAME) {
                        return ibas.enums.toString(emDataSourceType, value);
                    } else if (property === bo.ExportTemplateItem.PROPERTY_JUSTIFICATIONHORIZONTAL_NAME) {
                        return ibas.enums.toString(emJustificationHorizontal, value);
                    } else if (property === bo.ExportTemplateItem.PROPERTY_JUSTIFICATIONVERTICAL_NAME) {
                        return ibas.enums.toString(emJustificationVertical, value);
                    } else if (property === bo.ExportTemplateItem.PROPERTY_ITEMVISIBLE_NAME) {
                        return ibas.enums.toString(ibas.emYesNo, value);
                    }
                } else if (boName === bo.ExportTemplateAppendix.name) {
                    if (property === bo.ExportTemplateAppendix.PROPERTY_PAGEHEADER_NAME) {
                        return ibas.enums.toString(ibas.emYesNo, value);
                    } else if (property === bo.ExportTemplateAppendix.PROPERTY_PAGEFOOTER_NAME) {
                        return ibas.enums.toString(ibas.emYesNo, value);
                    }
                }
                return super.convertData(boName, property, value);
            }

            /**
             * 解析数据
             * @param boName 对象名称
             * @param property 属性名称
             * @param value 值
             * @returns 解析的值
             */
            protected parsingData(boName: string, property: string, value: any): any {
                if (boName === bo.ExportTemplateItem.name) {
                    if (property === bo.ExportTemplateItem.PROPERTY_AREA_NAME) {
                        return ibas.enums.valueOf(emAreaType, value);
                    } else if (property === bo.ExportTemplateItem.PROPERTY_TEXTSTYLE_NAME) {
                        return ibas.enums.valueOf(emTextStyle, value);
                    } else if (property === bo.ExportTemplateItem.PROPERTY_TEXTSEGMENT_NAME) {
                        return ibas.enums.valueOf(emTextSegment, value);
                    } else if (property === bo.ExportTemplateItem.PROPERTY_SOURCETYPE_NAME) {
                        return ibas.enums.valueOf(emDataSourceType, value);
                    } else if (property === bo.ExportTemplateItem.PROPERTY_JUSTIFICATIONHORIZONTAL_NAME) {
                        return ibas.enums.valueOf(emJustificationHorizontal, value);
                    } else if (property === bo.ExportTemplateItem.PROPERTY_JUSTIFICATIONVERTICAL_NAME) {
                        return ibas.enums.valueOf(emJustificationVertical, value);
                    } else if (property === bo.ExportTemplateItem.PROPERTY_ITEMVISIBLE_NAME) {
                        return ibas.enums.valueOf(ibas.emYesNo, value);
                    }
                } else if (boName === bo.ExportTemplateAppendix.name) {
                    if (property === bo.ExportTemplateAppendix.PROPERTY_PAGEHEADER_NAME) {
                        return ibas.enums.valueOf(ibas.emYesNo, value);
                    } else if (property === bo.ExportTemplateAppendix.PROPERTY_PAGEFOOTER_NAME) {
                        return ibas.enums.valueOf(ibas.emYesNo, value);
                    }
                }
                return super.parsingData(boName, property, value);
            }
        }
    }
}