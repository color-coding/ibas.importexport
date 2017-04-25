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
import { IDataImportView } from "../../../bsapp/dataimport/index";

/**
 * 视图-数据导入
 */
export class DataImportView extends ibas.BOView implements IDataImportView {
    /** 导入数据完成，参数为返回的消息 */
    importCompletedEvent: Function;
    /** 绘制视图 */
    darw(): any {
        let that = this;
        this.form = new sap.ui.layout.form.SimpleForm("", {
            content: [
            ]
        });
        this.form.addContent(new sap.ui.core.Title("", { text: ibas.i18n.prop("importexpor_import_data") }));
        this.uploader = new sap.ui.unified.FileUploader("", {
            name: "file",
            uploadUrl: "upload/",// 上传的路径
            mimeType: [],// 打开文件指定类型
            sendXHR: true,// false时没有返回值
            sameFilenameAllowed: true,
            placeholder: ibas.i18n.prop("importexpor_please_choose_file"),
            change(): void {
                // 选择文件发生变化
                that.showResults([]);
            },
            uploadComplete(event: any): void {
                // 上传文件完成
                if (event.getParameters().status === 200) {
                    // 上传成功，把返回的消息提交
                    that.fireViewEvents(that.importCompletedEvent, event.getParameters().responseRaw);
                } else {
                    // 返回值不正确，都归集为网络问题
                    that.application.viewShower.messages({
                        type: ibas.emMessageType.ERROR,
                        message: ibas.i18n.prop("importexpor_network_error")
                    });
                }
            },
            /*
            headerParameters: [
                new sap.ui.unified.FileUploaderParameter("", {
                    name: "Access-Control-Allow-Headers",
                    value: "Content-Type, Accept, Authorization"
                }),
                new sap.ui.unified.FileUploaderParameter("", {
                    name: "Access-Control-Allow-Methods",
                    value: "GET, POST, PUT, DELETE, OPTIONS"
                }),
                new sap.ui.unified.FileUploaderParameter("", {
                    name: "Access-Control-Allow-Origin",
                    value: "*"
                }),
            ]
            */
        });
        this.form.addContent(this.uploader);
        this.form.addContent(new sap.ui.core.Title("", { text: ibas.i18n.prop("importexpor_import_result") }));
        this.table = new sap.ui.table.Table("", {
            enableSelectAll: false,
            visibleRowCount: 10,
            visibleRowCountMode: sap.ui.table.VisibleRowCountMode.Interactive,
            //  rows: "{/}",
            columns: [
                new sap.ui.table.Column("", {
                    label: ibas.i18n.prop("importexpor_import_result"),
                    template: new sap.m.Text("", {
                        wrapping: false
                    }).bindProperty("text", {
                        path: "/"
                    })
                }),
            ]
        });
        this.form.addContent(this.table);
        this.page = new sap.m.Page("", {
            showHeader: false,
            subHeader: new sap.m.Bar("", {
                contentLeft: [
                    new sap.m.Button("", {
                        text: ibas.i18n.prop("importexpor_import"),
                        type: sap.m.ButtonType.Transparent,
                        icon: "sap-icon://inbox",
                        press: function (): void {
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
    private table: sap.ui.table.Table;
    get uploadUrl(): string {
        return this.uploader.getUploadUrl();
    }
    set uploadUrl(value: string) {
        this.uploader.setUploadUrl(value);
    }
    /** 显示结果 */
    showResults(results: any[]): void {
        this.table.setModel(new sap.ui.model.json.JSONModel(results));
    }
}
