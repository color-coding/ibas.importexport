/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import { BORepositoryImportExport } from "../../borep/BORepositories";

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
    }
    /** 视图显示后 */
    protected viewShowed(): void {
        // 视图加载完成
    }
    /** 运行,覆盖原方法 */
    run(): void {
        super.run.apply(this, arguments);
    }
    /** 导入 */
    import(data: FormData): void {
        this.busy(true);
        this.view.showResults([]);
        let that: this = this;
        let boRepository: BORepositoryImportExport = new BORepositoryImportExport();
        boRepository.import({
            fileData: data,
            onCompleted(opRslt: ibas.IOperationResult<string>): void {
                try {
                    that.busy(false);
                    if (opRslt.resultCode !== 0) {
                        throw new Error(opRslt.message);
                    }
                    that.messages(ibas.emMessageType.SUCCESS,
                        ibas.i18n.prop("shell_upload") + ibas.i18n.prop("shell_sucessful"));
                    that.view.showResults(opRslt.resultObjects);
                } catch (error) {
                    that.messages(error);
                }
            }
        });
        this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("shell_uploading_file"));
    }
}
/** 数据导入-视图 */
export interface IDataImportView extends ibas.IView {
    /** 导入 */
    importEvent: Function;
    /** 显示结果 */
    showResults(results: any[]): void;
}