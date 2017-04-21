/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import * as bo from "../../borep/bo/index";
import { BORepositoryImportExport } from "../../borep/BORepositories";
import { DataExportTemplateViewApp } from "./DataExportTemplateViewApp";
import { DataExportTemplateEditApp } from "./DataExportTemplateEditApp";

/** 列表应用-数据导出模板 */
export class DataExportTemplateListApp extends ibas.BOListApplication<IDataExportTemplateListView, bo.DataExportTemplate> {

    /** 应用标识 */
    static APPLICATION_ID: string = "73514fe6-d107-4bc4-8a55-9e9ca9c66cd8";
    /** 应用名称 */
    static APPLICATION_NAME: string = "importexport_app_dataexporttemplate_list";
    /** 业务对象编码 */
    static BUSINESS_OBJECT_CODE: string = bo.DataExportTemplate.BUSINESS_OBJECT_CODE;
    /** 构造函数 */
    constructor() {
        super();
        this.id = DataExportTemplateListApp.APPLICATION_ID;
        this.name = DataExportTemplateListApp.APPLICATION_NAME;
        this.boCode = DataExportTemplateListApp.BUSINESS_OBJECT_CODE;
        this.description = ibas.i18n.prop(this.name);
    }
    /** 注册视图 */
    protected registerView(): void {
        super.registerView();
        // 其他事件
        this.view.editDataEvent = this.editData;
        this.view.deleteDataEvent = this.deleteData;
    }
    /** 视图显示后 */
    protected viewShowed(): void {
        // 视图加载完成
    }
    /** 查询数据 */
    protected fetchData(criteria: ibas.ICriteria): void {
        try {
            this.busy(true);
            let that = this;
            let boRepository = new BORepositoryImportExport();
            boRepository.fetchDataExportTemplate({
                criteria: criteria,
                onCompleted(opRslt: ibas.IOperationResult<bo.DataExportTemplate>): void {
                    try {
                        if (opRslt.resultCode !== 0) {
                            throw new Error(opRslt.message);
                        }
                        that.view.showData(opRslt.resultObjects);
                        that.busy(false);
                    } catch (error) {
                        that.messages(error);
                    }
                }
            });
            this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("sys_shell_fetching_data"));
        } catch (error) {
            this.messages(error);
        }
    }
    /** 新建数据 */
    protected newData(): void {
        let app = new DataExportTemplateEditApp();
        app.navigation = this.navigation;
        app.viewShower = this.viewShower;
        app.run();
    }
    /** 查看数据，参数：目标数据 */
    protected viewData(data: bo.DataExportTemplate): void {
        // 检查目标数据
        if (ibas.objects.isNull(data)) {
            this.messages(ibas.emMessageType.WARNING, ibas.i18n.prop("sys_shell_ui_please_chooose_data",
                ibas.i18n.prop("sys_shell_ui_data_view")
            ));
            return;
        }
        let app = new DataExportTemplateViewApp();
        app.navigation = this.navigation;
        app.viewShower = this.viewShower;
        app.run(data);

    }
    /** 编辑数据，参数：目标数据 */
    protected editData(data: bo.DataExportTemplate): void {
        // 检查目标数据
        if (ibas.objects.isNull(data)) {
            this.messages(ibas.emMessageType.WARNING, ibas.i18n.prop("sys_shell_ui_please_chooose_data",
                ibas.i18n.prop("sys_shell_ui_data_edit")
            ));
            return;
        }
        let app = new DataExportTemplateEditApp();
        app.navigation = this.navigation;
        app.viewShower = this.viewShower;
        app.run(data);
    }
    /** 删除数据，参数：目标数据集合 */
    protected deleteData(data: bo.DataExportTemplate): void {
        // 检查目标数据
        if (ibas.objects.isNull(data)) {
            this.messages(ibas.emMessageType.WARNING, ibas.i18n.prop("sys_shell_ui_please_chooose_data",
                ibas.i18n.prop("sys_shell_ui_data_delete")
            ));
            return;
        }
        let beDeleteds:ibas.ArrayList<bo.DataExportTemplate> = new ibas.ArrayList<bo.DataExportTemplate>();
        if (data instanceof Array ) {
            for (let item of data) {
                if (ibas.objects.instanceOf(item, bo.DataExportTemplate)) {
                    item.delete();
                    beDeleteds.add(item);
                }
            }
        }
        // 没有选择删除的对象
        if (beDeleteds.length === 0) {
            return;
        }
        let that = this;
        this.messages({
            type: ibas.emMessageType.QUESTION,
            title: ibas.i18n.prop(this.name),
            message: ibas.i18n.prop("sys_shell_ui_whether_to_delete", beDeleteds.length),
            actions: [ibas.emMessageAction.YES, ibas.emMessageAction.NO],
            onCompleted(action: ibas.emMessageAction): void {
                if (action === ibas.emMessageAction.YES) {
                    try {
                        let boRepository: BORepositoryImportExport = new BORepositoryImportExport();
                        let saveMethod: Function = function(beSaved: bo.DataExportTemplate):void {
                            boRepository.saveDataExportTemplate({
                                beSaved: beSaved,
                                onCompleted(opRslt: ibas.IOperationResult<bo.DataExportTemplate>): void {
                                    try {
                                        if (opRslt.resultCode !== 0) {
                                            throw new Error(opRslt.message);
                                        }
                                        // 保存下一个数据
                                        let index: number = beDeleteds.indexOf(beSaved) + 1;
                                        if (index > 0 && index < beDeleteds.length) {
                                            saveMethod(beDeleteds[index]);
                                        } else {
                                            // 处理完成
                                            that.busy(false);
                                            that.messages(ibas.emMessageType.SUCCESS,
                                            ibas.i18n.prop("sys_shell_ui_data_delete") + ibas.i18n.prop("sys_shell_ui_sucessful"));
                                        }
                                    } catch (error) {
                                        that.messages(ibas.emMessageType.ERROR,
                                            ibas.i18n.prop("sys_shell_ui_data_delete_error", beSaved, error.message));
                                    }
                                }
                            });
                            this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("sys_shell_ui_data_deleting", beSaved));
                        };
                        that.busy(true);
                        // 开始保存
                        saveMethod(beDeleteds.firstOrDefault());
                    } catch (error) {
                        that.busy(false);
                        that.messages(error);
                    }
                }
            }
        });
    }
    /** 获取服务的契约 */
    protected getServiceProxies(): ibas.IServiceProxy<ibas.IServiceContract>[] {
        return [];
    }
}
/** 视图-数据导出模板 */
export interface IDataExportTemplateListView extends ibas.IBOListView {
    /** 编辑数据事件，参数：编辑对象 */
    editDataEvent: Function;
    /** 删除数据事件，参数：删除对象集合 */
    deleteDataEvent: Function;
    /** 显示数据 */
    showData(datas: bo.DataExportTemplate[]): void;
}
