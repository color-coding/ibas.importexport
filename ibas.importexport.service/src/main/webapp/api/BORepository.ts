/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace bo {

        /** ImportExport 业务仓库 */
        export interface IBORepositoryImportExport extends ibas.IBORepositoryApplication {

            /**
             * 获取业务对象架构
             * @param caller 调用者
             */
            schema(caller: ISchemaMethodCaller<string>): void;
            /**
             * 导入
             * @param caller 调用者
             */
            import(caller: ImportFileCaller): void;
            /**
             * 数据导出调用者
             * @param caller 调用者
             */
            export(caller: ibas.IDownloadFileCaller<Blob>): void;
            /**
             * 查询 获取转换者名称
             * @param fetcher 查询者
             */
            fetchTransformer(fetcher: ibas.IFetchCaller<ibas.KeyText>): void;
            /**
             * 查询 数据导出模板
             * @param fetcher 查询者
             */
            fetchDataExportTemplate(fetcher: ibas.IFetchCaller<bo.IDataExportTemplate>): void;
            /**
             * 保存 数据导出模板
             * @param saver 保存者
             */
            saveDataExportTemplate(saver: ibas.ISaveCaller<bo.IDataExportTemplate>): void;
        }
        /**
         * 业务对象架构相关调用者
         */
        export interface ISchemaMethodCaller<P> extends ibas.IMethodCaller<P> {
            /** 业务对象编码 */
            boCode: string;
            /** 结构类型 */
            type: string;
        }
        /**
         * 文件导入调用者
         */
        export interface ImportFileCaller extends ibas.IUploadFileCaller<string> {
        }
    }
}