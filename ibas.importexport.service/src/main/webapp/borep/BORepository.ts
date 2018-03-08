/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace bo {

        /** 数据导入&导出 业务仓库 */
        export class BORepositoryImportExport extends ibas.BORepositoryApplication implements IBORepositoryImportExport {

            /** 创建此模块的后端与前端数据的转换者 */
            protected createConverter(): ibas.IDataConverter {
                return new DataConverter();
            }
            /**
             * 导入
             * @param caller 调用者
             */
            import(caller: ImportFileCaller): void {
                if (!this.address.endsWith("/")) { this.address += "/"; }
                let fileRepository: ibas.FileRepositoryUploadAjax = new ibas.FileRepositoryUploadAjax();
                fileRepository.address = this.address.replace("/services/rest/data/", "/services/rest/file/");
                fileRepository.token = this.token;
                fileRepository.converter = this.createConverter();
                fileRepository.upload("import", caller);
            }
            /**
             * 导出
             * @param caller 调用者
             */
            export(caller: ibas.IDownloadFileCaller<Blob>): void {
                if (!this.address.endsWith("/")) { this.address += "/"; }
                let fileRepository: ibas.FileRepositoryDownloadAjax = new ibas.FileRepositoryDownloadAjax();
                fileRepository.address = this.address.replace("/services/rest/data/", "/services/rest/file/");
                fileRepository.token = this.token;
                fileRepository.converter = this.createConverter();
                fileRepository.download("export", caller);
            }
            /**
             * 获取业务对象架构
             * @param caller 调用者
             */
            schema(caller: SchemaMethodCaller<string>): void {
                let boRepository: ibas.BORepositoryAjax = new ibas.BORepositoryAjax();
                boRepository.address = this.address;
                boRepository.token = this.token;
                boRepository.converter = this.createConverter();
                let method: string =
                    ibas.strings.format("schema?boCode={0}&type={1}&token={2}",
                        caller.boCode, caller.type, this.token);
                boRepository.callRemoteMethod(method, undefined, caller);
            }
            /**
             * 查询 获取转换者名称
             * @param fetcher 查询者
             */
            fetchTransformer(fetcher: ibas.IFetchCaller<ibas.KeyText>): void {
                super.fetch("Transformer", fetcher);
            }
            /**
             * 查询 数据导出模板
             * @param fetcher 查询者
             */
            fetchDataExportTemplate(fetcher: ibas.IFetchCaller<bo.DataExportTemplate>): void {
                super.fetch(bo.DataExportTemplate.name, fetcher);
            }
            /**
             * 保存 数据导出模板
             * @param saver 保存者
             */
            saveDataExportTemplate(saver: ibas.ISaveCaller<bo.DataExportTemplate>): void {
                super.save(bo.DataExportTemplate.name, saver);
            }

        }

    }
}