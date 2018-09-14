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

        export enum emAreaType {
            /**
             * 页眉
             */
            PAGE_HEADER,
            /**
             * 开始区
             */
            START_SECTION,
            /**
             * 重复区头
             */
            REPETITION_HEADER,
            /**
             * 重复区
             */
            REPETITION,
            /**
             * 重复区脚
             */
            REPETITION_FOOTER,
            /**
             * 结束区
             */
            END_SECTION,
            /**
             * 页脚区
             */
            PAGE_FOOTER,
        }
        export enum emDataSourceType {
            /**
             * 文本
             */
            TEXT,
            /**
             * 路径
             */
            PATH,
            /**
             * 查询
             */
            QUERY,
        }
        export enum emJustificationHorizontal {
            /**
             * 靠右
             */
            RIGHT,
            /**
             * 靠左
             */
            LEFT,
            /**
             * 中间
             */
            CENTER,
        }
        export enum emJustificationVertical {
            /**
             * 靠上
             */
            TOP,
            /**
             * 靠下
             */
            BOTTOM,
            /**
             * 中间
             */
            CENTER,
        }
        export enum emTextStyle {
            /**
             * 常规
             */
            REGULAR,
            /**
             * 粗体
             */
            BOLD,
            /**
             * 斜体
             */
            ITALIC,
            /**
             * 粗斜体
             */
            BOLD_ITALIC
        }
        /** 数据导出调用者 */
        export interface IDataExportCaller<T> extends ibas.IMethodCaller<T> {
            /** 导出的数据 */
            data?: object | object[];
            /** 数据查询 */
            criteria?: ibas.ICriteria;
            /** 内容类型 */
            contentType?: string;
        }
        /** 数据导出者 */
        export interface IDataExporter {
            /** 名称 */
            name: string;
            /** 描述 */
            description: string;
            /** 导出 */
            export(caller: IDataExportCaller<any>): void;
        }
    }
    export namespace app {
        /** 服务码-业务对象打印 */
        export const SERVICE_CATEGORY_BO_PRINT: string = "SERVICE_CATEGORY_BO_PRINT";
        /** 服务码-数据表打印 */
        export const SERVICE_CATEGORY_DATATABLE_PRINT: string = "SERVICE_CATEGORY_DATATABLE_PRINT";
        /** 数据打印服务契约 */
        export interface IDataPrintServiceContract extends ibas.IBOServiceContract {
            /** 模板 */
            template: number | ibas.ICondition[];
            /** 业务对象 */
            businessObject?: string;
            /** 内容 */
            content?: string;
        }
    }
}