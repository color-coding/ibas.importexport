/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";

/** 数据导入服务 */
export class DataImportApp extends ibas.Application<IDataImportView>  {

    /** 应用标识 */
    static APPLICATION_ID: string = "232ec08a-cfca-426d-bba1-8d254d1548eb";
    /** 应用名称 */
    static APPLICATION_NAME: string = "importexport_app_dataimport";
    /** 配置项目-导入文件的上传地址 */
    static CONFIG_ITEM_IMPORT_FILE_UPLOAD_URL: string = "ImportUploadUrl";

    constructor() {
        super();
        this.id = DataImportApp.APPLICATION_ID;
        this.name = DataImportApp.APPLICATION_NAME;
        this.description = ibas.i18n.prop(this.name);
    }
    /** 注册视图 */
    protected registerView(): void {
        super.registerView();
    }
    /** 视图显示后 */
    protected viewShowed(): void {
        // 视图加载完成
        let url: string = ibas.config.get(DataImportApp.CONFIG_ITEM_IMPORT_FILE_UPLOAD_URL, "./upload/");
        this.view.uploadUrl = url;
    }
    /** 运行,覆盖原方法 */
    run(...args: any[]): void {
        super.run();
    }
}
/** 数据导出服务-视图 */
export interface IDataImportView extends ibas.IView {
    /** 上传数据地址 */
    uploadUrl: string;
    /** 导入数据 */
    importDataEvent: Function;
    /** 显示结果 */
    showResults(result: IImportResult): void;
}
/** 导出结果 */
export interface IImportResult {
    /** 消息 */
    message: string;
    /** 导入的key值 */
    keys: string[];
}