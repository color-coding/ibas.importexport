/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import { BO_CODE_BOINFORMATION, IBOInformation } from "../../3rdparty/initialfantasy/index";
import { BORepositoryImportExport } from "../../borep/BORepositories";

/** 数据导出 */
export class DataExportApp extends ibas.Application<IDataExportView>  {

    /** 应用标识 */
    static APPLICATION_ID: string = "a6c6252c-ebc0-4feb-8c39-5a97862a68de";
    /** 应用名称 */
    static APPLICATION_NAME: string = "importexport_app_dataexport";

    constructor() {
        super();
        this.id = DataExportApp.APPLICATION_ID;
        this.name = DataExportApp.APPLICATION_NAME;
        this.description = ibas.i18n.prop(this.name);
    }
    /** 注册视图 */
    protected registerView(): void {
        super.registerView();
        this.view.exportEvent = this.export;
        this.view.chooseBusinessObjectEvent = this.chooseBusinessObject;
        this.view.addConditionEvent = this.addQueryCondition;
        this.view.removeConditionEvent = this.removeQueryCondition;
        this.view.schemaEvent = this.schema;
    }
    /** 视图显示后 */
    protected viewShowed(): void {
        // 视图加载完成
        if (ibas.objects.isNull(this.criteria)) {
            this.criteria = new ibas.Criteria();
        }
        this.view.showCriteria(this.criteria);
        let that: this = this;
        let boRepository: BORepositoryImportExport = new BORepositoryImportExport();
        boRepository.fetchTransformer({
            criteria: [
                new ibas.Condition("NAME", ibas.emConditionOperation.START, "TO_FILE_")
            ],
            onCompleted(opRslt: ibas.IOperationResult<ibas.KeyText>): void {
                that.view.showTemplates(opRslt.resultObjects);
                that.busy(false);
            }
        });
        this.busy(true);
    }
    /** 运行,覆盖原方法 */
    run(...args: any[]): void {
        super.run();
    }
    private criteria: ibas.ICriteria;
    /** 获取Schema */
    schema(type: string): void {
        let that: this = this;
        let boRepository: BORepositoryImportExport = new BORepositoryImportExport();
        boRepository.schema({
            boCode: this.criteria.businessObject,
            type: type,
            onCompleted(opRslt: ibas.IOperationResult<string>): void {
                try {
                    that.busy(false);
                    if (opRslt.resultCode !== 0) {
                        throw new Error(opRslt.message);
                    }
                    that.messages(ibas.emMessageType.SUCCESS, opRslt.resultObjects.firstOrDefault());
                } catch (error) {
                    that.messages(error);
                }
            }
        });
        this.busy(true);
    }
    /** 导出 */
    export(): void {
        if (ibas.strings.isEmpty(this.criteria.businessObject)) {
            throw new Error(ibas.i18n.prop("sys_invalid_parameter", "BusinessObject"));
        }
        if (ibas.strings.isEmpty(this.criteria.remarks)) {
            throw new Error(ibas.i18n.prop("sys_invalid_parameter", "Template"));
        }
        this.busy(true);
        let that: this = this;
        let boRepository: BORepositoryImportExport = new BORepositoryImportExport();
        boRepository.export({
            criteria: this.criteria,
            onCompleted(opRslt: ibas.IOperationResult<Blob>): void {
                try {
                    that.busy(false);
                    if (opRslt.resultCode !== 0) {
                        throw new Error(opRslt.message);
                    }
                    let data: Blob = opRslt.resultObjects.firstOrDefault();
                    if (!ibas.objects.isNull(data)) {
                        let extName: string = that.criteria.remarks;
                        extName = extName.substring(extName.lastIndexOf("_") + 1);
                        ibas.files.save(data, ibas.strings.format("{0}_{1}.{2}",
                            that.criteria.businessObject, ibas.dates.toString(ibas.dates.now(), "yyyyMMddhhss"), extName));
                    }
                } catch (error) {
                    that.messages(error);
                }
            }
        });
        this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("importexport_exporting"));
    }
    /** 选择业务对象事件 */
    chooseBusinessObject(): void {
        let that: this = this;
        let criteria: ibas.ICriteria = new ibas.Criteria();
        criteria.noChilds = true;
        let condition: ibas.ICondition = criteria.conditions.create();
        condition.alias = "code";
        condition.value = ".";
        condition.operation = ibas.emConditionOperation.NOT_CONTAIN;
        ibas.servicesManager.runChooseService<IBOInformation>({
            boCode: BO_CODE_BOINFORMATION,
            chooseType: ibas.emChooseType.SINGLE,
            criteria: criteria,
            onCompleted(selecteds: ibas.List<IBOInformation>): void {
                that.criteria.businessObject = selecteds.firstOrDefault().code;
                that.view.showConditions(null);
                that.criteria.conditions.clear();
                that.view.showCriteria(that.criteria);
                that.view.showConditions(that.criteria.conditions);
            }
        });
    }
    private addQueryCondition(): void {
        this.criteria.conditions.create();
        this.view.showConditions(this.criteria.conditions);
    }
    private removeQueryCondition(condition: ibas.ICondition): void {
        this.criteria.conditions.remove(condition);
        this.view.showConditions(this.criteria.conditions);
    }
}
/** 数据导出-视图 */
export interface IDataExportView extends ibas.IView {
    /** 获取Schema，参数1，类型（xml,json） */
    schemaEvent: Function;
    /** 显示查询 */
    showCriteria(criteria: ibas.ICriteria): void;
    /** 显示可用模板 */
    showTemplates(templates: ibas.KeyText[]): void;
    /** 选择业务对象 */
    chooseBusinessObjectEvent: Function;
    /** 导出 */
    exportEvent: Function;
    /** 添加条件 */
    addConditionEvent: Function;
    /** 移出条件 */
    removeConditionEvent: Function;
    /** 显示结果 */
    showConditions(conditions: ibas.ICondition[]): void;
}