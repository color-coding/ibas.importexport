/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {
        const PROPERTY_STATUS: symbol = Symbol("status");
        const PROPERTY_FILE: symbol = Symbol("file");
        export class FileItem extends ibas.Bindable {
            constructor(file: File) {
                super();
                this.status = "pending";
                this.identified = 0;
                this.saved = 0;
                this[PROPERTY_FILE] = file;
            }
            get status(): "pending" | "processing" | "completed" | "failed" {
                return this[PROPERTY_STATUS];
            }
            set status(value: "pending" | "processing" | "completed" | "failed") {
                this[PROPERTY_STATUS] = value;
                this.firePropertyChanged("status");
            }
            get file(): File {
                return this[PROPERTY_FILE];
            }
            /** 顺序 */
            order: number;
            /** 识别的数据 */
            identified: number;
            /** 保存的数据 */
            saved: number;
            /** 解析错误 */
            error: Error;
        }
        export class FileItems extends ibas.ArrayList<FileItem> {
            add(item: FileItem): void;
            add(items: FileItem[]): void;
            add(): void {
                if (arguments[0] instanceof Array) {
                    for (let item of arguments[0]) {
                        this.add(item);
                    }
                } else {
                    super.add(arguments[0]);
                    this.afterAdd(arguments[0]);
                }
            }
            protected afterAdd(item: FileItem): void {
                let max: number = 0;
                for (let element of this) {
                    if (item === element) {
                        continue;
                    }
                    if (element.order > max) {
                        max = element.order;
                    }
                }
                item.order = Math.round(max + 1);
                if (item.order <= 0) {
                    item.order = 1;
                }
            }
        }
        /** 数据导入 */
        export class DataImportApp extends ibas.Application<IDataImportView>  {

            /** 应用标识 */
            static APPLICATION_ID: string = "232ec08a-cfca-426d-bba1-8d254d1548eb";
            /** 应用名称 */
            static APPLICATION_NAME: string = "importexport_app_dataimport";

            constructor() {
                super();
                this.id = DataImportApp.APPLICATION_ID;
                this.name = DataImportApp.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 注册视图 */
            protected registerView(): void {
                super.registerView();
                this.view.importEvent = this.import;
                this.view.addFilesEvent = this.addFiles;
                this.view.removeFilesEvent = this.removeFiles;
            }
            /** 视图显示后 */
            protected viewShowed(): void {
                // 视图加载完成
                this.files.clear();
                this.view.showFiles(this.files);
                this.view.showResults(undefined, []);
            }
            private files: ibas.IList<FileItem> = new FileItems();
            /** 导入 */
            protected import(method?: bo.emDataUpdateMethod): void {
                let datas: FileItem[] = this.files.filter(c => c.status === "pending");
                if (!(datas?.length > 0)) {
                    this.messages(ibas.emMessageType.WARNING, ibas.i18n.prop("importexport_please_choose_file"));
                    return;
                }
                this.messages({
                    type: ibas.emMessageType.QUESTION,
                    message: ibas.i18n.prop("importexport_import_files_continue", datas.length),
                    actions: [
                        ibas.emMessageAction.YES,
                        ibas.emMessageAction.NO
                    ],
                    onCompleted: (action) => {
                        if (action !== ibas.emMessageAction.YES) {
                            return;
                        }
                        this.busy(true);
                        let boRepository: bo.BORepositoryImportExport = new bo.BORepositoryImportExport();
                        ibas.queues.execute(datas.sort((a, b) => a.order - b.order),
                            (data, next) => {
                                if (data.file instanceof File) {
                                    let formData: FormData = new FormData();
                                    formData.append("updateMethod", ibas.enums.toString(
                                        bo.emDataUpdateMethod, method > 0 ? method : bo.emDataUpdateMethod.SKIP)
                                    );
                                    formData.append("file", data.file);
                                    data.status = "processing";
                                    boRepository.import({
                                        fileData: formData,
                                        onCompleted: (opRslt) => {
                                            if (opRslt.resultCode !== 0) {
                                                data.status = "failed";
                                                data.error = new Error(opRslt.message);
                                                next(data.error);
                                            } else {
                                                data.status = "completed";
                                                data.identified = ibas.numbers.valueOf(opRslt.informations.firstOrDefault(c => c.name === "IDENTIFY_DATA_COUNT")?.content);
                                                data.saved = ibas.numbers.valueOf(opRslt.informations.firstOrDefault(c => c.name === "SAVE_DATA_COUNT")?.content);
                                                this.view.showResults(data, opRslt.resultObjects);
                                                next();
                                            }
                                        }
                                    });
                                } else {
                                    data.status = "failed";
                                    next();
                                }
                            },
                            (error) => {
                                this.busy(false);
                                if (error instanceof Error) {
                                    this.messages(error);
                                } else {
                                    this.messages(ibas.emMessageType.SUCCESS, ibas.i18n.prop("importexport_import_done"));
                                }
                            }
                        );
                    }
                });
            }
            /** 选择文件 */
            protected addFiles(): void {
                ibas.files.open((files) => {
                    for (let item of files) {
                        this.files.add(new FileItem(item));
                    }
                    this.view.showFiles(this.files);
                }, {
                    accept: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/json,application/xml",
                    multiple: true
                });
            }
            /** 移除文件 */
            protected removeFiles(file?: FileItem | FileItem[]): void {
                if (ibas.objects.isNull(file)) {
                    this.files.clear();
                    this.view.showFiles(this.files);
                } else if (file instanceof Array) {
                    for (let item of file) {
                        this.files.remove(item);
                    }
                    this.view.showFiles(this.files);
                } else {
                    this.files.remove(file);
                    this.view.showFiles(this.files);
                }
            }
        }
        /** 数据导入-视图 */
        export interface IDataImportView extends ibas.IView {
            /** 导入 */
            importEvent: Function;
            /** 选择文件 */
            addFilesEvent: Function;
            /** 移除文件 */
            removeFilesEvent: Function;
            /** 显示文件 */
            showFiles(datas: FileItem[]): void;
            /** 显示结果 */
            showResults(file: FileItem, results: string[]): void;
        }
    }
}