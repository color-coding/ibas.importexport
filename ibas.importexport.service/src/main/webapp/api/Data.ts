/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    /** 模块-标识 */
    export const CONSOLE_ID: string = "cbd4ef0f-ee88-4ea7-96c7-13f974026b58";
    /** 模块-名称 */
    export const CONSOLE_NAME: string = "ImportExport";
    /** 模块-版本 */
    export const CONSOLE_VERSION: string = "0.1.0";

    export namespace bo {
        /** 业务仓库名称 */
        export const BO_REPOSITORY_IMPORTEXPORT: string = ibas.strings.format(ibas.MODULE_REPOSITORY_NAME_TEMPLATE, CONSOLE_NAME);
        /** 业务对象编码-数据导出模板 */
        export const BO_CODE_EXPORTTEMPLATE: string = "${Company}_IE_EXPORTTEMPLATE";

        export enum emPaperSize {
            A4,
        }
        export enum emAreaType {

        }
        export enum emDataSourceType {

        }
        export enum emHorizontalJustification {

        }
        export enum emVerticalJustification {

        }
    }
}