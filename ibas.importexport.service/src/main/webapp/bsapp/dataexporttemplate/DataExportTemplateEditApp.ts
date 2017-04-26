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

/** 应用-数据导出模板 */
export class DataExportTemplateEditApp extends ibas.BOEditApplication<IDataExportTemplateEditView, bo.DataExportTemplate> {

    /** 应用标识 */
    static APPLICATION_ID: string = "649eff99-c949-4580-8c4d-127c9209654f";
    /** 应用名称 */
    static APPLICATION_NAME: string = "importexport_app_dataexporttemplate_edit";
    /** 业务对象编码 */
    static BUSINESS_OBJECT_CODE: string = bo.DataExportTemplate.BUSINESS_OBJECT_CODE;
    /** 构造函数 */
    constructor() {
        super();
        this.id = DataExportTemplateEditApp.APPLICATION_ID;
        this.name = DataExportTemplateEditApp.APPLICATION_NAME;
        this.boCode = DataExportTemplateEditApp.BUSINESS_OBJECT_CODE;
        this.description = ibas.i18n.prop(this.name);
    }
    /** 注册视图 */
    protected registerView(): void {
        super.registerView();
        // 其他事件
        this.view.deleteDataEvent = this.deleteData;
        this.view.addDataExportTemplateItemEvent = this.addDataExportTemplateItem;
        this.view.removeDataExportTemplateItemEvent = this.removeDataExportTemplateItem;
    }
    /** 视图显示后 */
    protected viewShowed(): void {
        // 视图加载完成
        this.view.showDataExportTemplate(this.editData);
        this.view.showDataExportTemplateItems(this.editData.dataExportTemplateItems.filterDeleted());
    }
    /** 运行,覆盖原方法 */
    run(...args: any[]): void {
        // 尝试设置编辑对象
        if (!ibas.objects.isNull(args) && args.length === 1 && ibas.objects.instanceOf(args[0], bo.DataExportTemplate)) {
            this.editData = args[0];
        }
        // 创建编辑对象实例
        if (ibas.objects.isNull(this.editData)) {
            this.editData = new bo.DataExportTemplate();
            this.proceeding(ibas.emMessageType.WARNING, ibas.i18n.prop("sys_shell_ui_data_created_new"));

        }
        super.run();
    }
    /** 待编辑的数据 */
    protected editData: bo.DataExportTemplate;
    /** 保存数据 */
    protected saveData(): void {
        try {
            let that = this;
            let boRepository: BORepositoryImportExport = new BORepositoryImportExport();
            boRepository.saveDataExportTemplate({
                beSaved: this.editData,
                onCompleted(opRslt: ibas.IOperationResult<bo.DataExportTemplate>): void {
                    try {
                        that.busy(false);
                        if (opRslt.resultCode !== 0) {
                            throw new Error(opRslt.message);
                        }
                        if (opRslt.resultObjects.length === 0) {
                            that.messages(ibas.emMessageType.SUCCESS,
                                ibas.i18n.prop("sys_shell_ui_data_delete") + ibas.i18n.prop("sys_shell_ui_sucessful"));
                            // 创建新的对象
                            that.editData = new bo.DataExportTemplate();
                        } else {
                            // 替换编辑对象
                            that.editData = opRslt.resultObjects.firstOrDefault();
                            that.messages(ibas.emMessageType.SUCCESS,
                                ibas.i18n.prop("sys_shell_ui_data_save") + ibas.i18n.prop("sys_shell_ui_sucessful"));
                        }
                        // 刷新当前视图
                        that.viewShowed();
                    } catch (error) {
                        that.messages(error);
                    }
                }
            });
            this.busy(true);
            this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("sys_shell_saving_data"));
        } catch (error) {
            this.messages(error);
        }
    }
    /** 删除数据 */
    protected deleteData(): void {
        let that = this;
        this.messages({
            type: ibas.emMessageType.QUESTION,
            title: ibas.i18n.prop(this.name),
            message: ibas.i18n.prop("msg_whether_to_delete"),
            actions: [ibas.emMessageAction.YES, ibas.emMessageAction.NO],
            onCompleted(action: ibas.emMessageAction): void {
                if (action === ibas.emMessageAction.YES) {
                    that.editData.delete();
                    that.saveData();
                }
            }
        });
    }
    /** 添加数据导出模板-项事件 */
    addDataExportTemplateItem(): void {
        this.editData.dataExportTemplateItems.create();
        this.view.showDataExportTemplateItems(this.editData.dataExportTemplateItems.filterDeleted());
    }
    /** 删除数据导出模板-项事件 */
    removeDataExportTemplateItem(item: bo.DataExportTemplateItem): void {
        if (this.editData.dataExportTemplateItems.indexOf(item) >= 0) {
            this.editData.dataExportTemplateItems.remove(item);
            this.view.showDataExportTemplateItems(this.editData.dataExportTemplateItems.filterDeleted());
        }
    }

}
/** 视图-数据导出模板 */
export interface IDataExportTemplateEditView extends ibas.IBOEditView {
    /** 显示数据 */
    showDataExportTemplate(data: bo.DataExportTemplate): void;
    /** 删除数据事件 */
    deleteDataEvent: Function;
    /** 添加数据导出模板-项事件 */
    addDataExportTemplateItemEvent: Function;
    /** 删除数据导出模板-项事件 */
    removeDataExportTemplateItemEvent: Function;
    /** 显示数据 */
    showDataExportTemplateItems(datas: bo.DataExportTemplateItem[]): void;
}
