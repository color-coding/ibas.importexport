/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import { BORepositoryImportExport } from "../../borep/BORepositories";

/** 数据导入服务 */
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
        this.view.importCompletedEvent = this.importCompleted;
    }
    /** 视图显示后 */
    protected viewShowed(): void {
        // 视图加载完成
        // 设置导入方法地址
        let url: string = (new BORepositoryImportExport()).getImportUrl();
       // url = "http://localhost:8080/demo/services/jersey/files/upload";
        this.view.uploadUrl = url;
    }
    /** 运行,覆盖原方法 */
    run(...args: any[]): void {
        super.run();
    }
    /** 导入完成 */
    importCompleted(data: any): void {
        try {
            let opRslt: ibas.IOperationResult<string> = (new BORepositoryImportExport()).parseImportResult(data);
            if (opRslt.resultCode !== 0) {
                throw new Error(opRslt.message);
            }
            this.view.showResults(opRslt.resultObjects);
        } catch (error) {
            this.messages(error);
        }
    }
}
/** 数据导出服务-视图 */
export interface IDataImportView extends ibas.IView {
    /** 上传数据地址 */
    uploadUrl: string;
    /** 导入数据完成，参数为返回的消息 */
    importCompletedEvent: Function;
    /** 显示结果 */
    showResults(results: any[]): void;
}