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
            import(caller: IImportFileCaller): void {
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
            export(caller: IExportFileCaller): void {
                if (!this.address.endsWith("/")) { this.address += "/"; }
                let fileRepository: FileRepositoryDownloadAjax = new FileRepositoryDownloadAjax();
                fileRepository.address = this.address.replace("/services/rest/data/", "/services/rest/file/");
                fileRepository.token = this.token;
                fileRepository.converter = this.createConverter();
                let formData: FormData = new FormData();
                formData.append("transformer", caller.transformer);
                if (!ibas.objects.isNull(caller.template)) {
                    formData.append("template", caller.template);
                }
                if (!ibas.strings.isEmpty(caller.contentType)) {
                    formData.append("contentType", caller.contentType);
                }
                if (caller.criteria instanceof ibas.Criteria) {
                    let data: any = fileRepository.converter.convert(caller.criteria, "");
                    formData.append("criteria", JSON.stringify(data));
                }
                if (caller.content instanceof Blob) {
                    formData.append("content", caller.content);
                } else if (typeof caller.content === "string") {
                    formData.append("content", caller.content);
                } else if (caller.content instanceof Object) {
                    let data: any = fileRepository.converter.convert(caller.content, "");
                    formData.append("content", JSON.stringify(data));
                }
                fileRepository.download("export", {
                    criteria: formData,
                    onCompleted: caller.onCompleted,
                });
            }
            /**
             * 解析
             * @param caller 调用者
             */
            parse<T>(caller: IParseFileCaller<T>): void {
                if (!this.address.endsWith("/")) { this.address += "/"; }
                let fileRepository: ibas.FileRepositoryUploadAjax = new ibas.FileRepositoryUploadAjax();
                fileRepository.address = this.address.replace("/services/rest/data/", "/services/rest/file/");
                fileRepository.token = this.token;
                fileRepository.converter = this.createConverter();
                fileRepository.upload("parse", {
                    fileData: caller.fileData,
                    onCompleted: (opRslt) => {
                        if (opRslt.resultObjects instanceof Array) {
                            for (let i: number = 0; i < opRslt.resultObjects.length; i++) {
                                let data: any = caller.converter.parsing(opRslt.resultObjects[i], "PARSE");
                                if (data instanceof ibas.BusinessObject) {
                                    data.reset();
                                }
                                opRslt.resultObjects[i] = data;
                            }
                        }
                        caller.onCompleted.call(ibas.objects.isNull(caller.caller) ? caller : caller.caller, opRslt);
                    }
                });
            }
            /**
             * 获取业务对象架构
             * @param caller 调用者
             */
            schema(caller: ISchemaMethodCaller<string>): void {
                let boRepository: ibas.BORepositoryAjax = new ibas.BORepositoryAjax();
                boRepository.address = this.address;
                boRepository.token = this.token;
                boRepository.converter = this.createConverter();
                let method: string =
                    ibas.strings.format("schema?boCode={0}&type={1}&token={2}",
                        caller.boCode, caller.type, ibas.tokens.content(this.token));
                boRepository.callRemoteMethod(method, undefined, (opRslt) => {
                    caller.onCompleted.call(ibas.objects.isNull(caller.caller) ? caller : caller.caller, opRslt);
                });
            }
            /**
             * 查询 获取数据导出者
             * @param fetcher 查询者
             */
            fetchDataExporter(fetcher: ibas.IFetchCaller<bo.IDataExporter>): void {
                super.fetch("DataExporter", fetcher);
            }
            /**
             * 查询 数据导出模板
             * @param fetcher 查询者
             */
            fetchExportTemplate(fetcher: ibas.IFetchCaller<bo.ExportTemplate>): void {
                super.fetch(bo.ExportTemplate.name, fetcher);
            }
            /**
             * 保存 数据导出模板
             * @param saver 保存者
             */
            saveExportTemplate(saver: ibas.ISaveCaller<bo.ExportTemplate>): void {
                super.save(bo.ExportTemplate.name, saver);
            }
        }
        class FileRepositoryDownloadAjax extends ibas.FileRepositoryDownloadAjax {
            protected createHttpRequest(method: string): XMLHttpRequest {
                let methodUrl: string = this.methodUrl(method);
                let xhr: XMLHttpRequest = new XMLHttpRequest();
                xhr.open("POST", methodUrl, true);
                xhr.responseType = "blob";
                // token为头认证形式
                if (ibas.strings.isWith(this.token, ibas.HTTP_HEADER_TOKEN_AUTHORIZATION, undefined)) {
                    // xhr.withCredentials = true;
                    xhr.setRequestHeader("Authorization", this.token.substring(ibas.HTTP_HEADER_TOKEN_AUTHORIZATION.length));
                }
                return xhr;
            }
        }
    }
}