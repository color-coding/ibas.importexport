/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import * as bo from "./bo/index";
import {
    emPaperSize
} from "../api/index";

/** ImportExport 模块的数据转换者 */
export class DataConverterOnline extends ibas.DataConverter4j {

    /** 创建业务对象转换者 */
    protected createConverter(): ibas.BOConverter {
        return new ImportExportBOConverter();
    }
}

/** ImportExport 模块的业务对象转换者 */
class ImportExportBOConverter extends ibas.BOConverter {

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
        if (boName === bo.DataExportTemplate.name) {
            if (property === bo.DataExportTemplate.PROPERTY_PAPERSIZE_NAME) {
                return ibas.enums.valueOf(emPaperSize, value);
            }
        }
        return super.parsingData(boName, property, value);
    }
}
/** ImportExport 模块的离线数据转换者 */
export class DataConverterOffline implements ibas.IDataConverter {

    /**
     * 转换业务对象数据
     * @param data 本地类型
     * @param sign 特殊标记
     * @returns 目标类型
     */
    convert(data: any, sign: string): any {
        return data;
    }
    /**
     * 解析业务对象数据
     * @param data 目标类型
     * @param sign 特殊标记
     * @returns 本地类型
     */
    parsing(data: any, sign: string): any {
        return data;
    }
}
