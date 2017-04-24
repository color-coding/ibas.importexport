/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import { utils } from "openui5/typings/ibas.utils";
import * as bo from "../../../borep/bo/index";
import { IDataImportView, IImportResult } from "../../../bsapp/dataimport/index";

/**
 * 视图-数据导入
 */
export class DataImportView extends ibas.BOView implements IDataImportView {
    /** 导入数据 */
    importDataEvent: Function;
    /** 绘制视图 */
    darw(): any {
        let that = this;
        this.form = new sap.ui.layout.form.SimpleForm("", {
            content: [
            ]
        });
        this.form.addContent(new sap.ui.core.Title("", { text: ibas.i18n.prop("importexpor_import_data") }));
        this.uploader = new sap.ui.unified.FileUploader("", {
            value: "",
            uploadUrl: "upload/",
            // sendXHR: true,
            sameFilenameAllowed: true,
            uploadComplete(event: any): void {
                // 上传文件完成
            }
        });
        this.form.addContent(this.uploader);
        this.form.addContent(new sap.ui.core.Title("", { text: ibas.i18n.prop("importexpor_import_result") }));
        this.page = new sap.m.Page("", {
            showHeader: false,
            subHeader: new sap.m.Bar("", {
                contentLeft: [
                    new sap.m.Button("", {
                        text: ibas.i18n.prop("importexpor_import"),
                        type: sap.m.ButtonType.Transparent,
                        icon: "sap-icon://inbox",
                        press: function (): void {
                            // that.fireViewEvents(that.importDataEvent);
                            that.uploader.upload();
                        }
                    })
                ]
            }),
            content: [this.form]
        });
        this.id = this.page.getId();
        return this.page;
    }
    private page: sap.m.Page;
    private form: sap.ui.layout.form.SimpleForm;
    private uploader: sap.ui.unified.FileUploader;
    get uploadUrl(): string {
        return this.uploader.getUploadUrl();
    }
    set uploadUrl(value: string) {
        this.uploader.setUploadUrl(value);
    }
    /** 显示结果 */
    showResults(result: IImportResult): void {

    }
}
