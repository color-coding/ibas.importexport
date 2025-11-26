/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {

        /** 数据导出 */
        export class DataExportApp extends ibas.BOQueryApplication<IDataExportView>  {

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
                this.view.schemaEvent = this.schema;
                this.view.exportEvent = this.export;
                this.view.addConditionEvent = this.addQueryCondition;
                this.view.removeConditionEvent = this.removeQueryCondition;
                this.view.selectedBusinessObjectEvent = this.selectedBusinessObject;
            }
            /** 视图显示后 */
            protected viewShowed(): void {
                // 视图加载完成
                if (ibas.objects.isNull(this.criteria)) {
                    this.criteria = new ibas.Criteria();
                }

                let that: this = this;
                let boRepository: bo.BORepositoryImportExport = new bo.BORepositoryImportExport();
                boRepository.fetchDataExporter({
                    criteria: [
                        new ibas.Condition("Transformer", ibas.emConditionOperation.START, "TO_FILE_")
                    ],
                    onCompleted(opRslt: ibas.IOperationResult<bo.IDataExporter>): void {
                        try {
                            if (opRslt.resultCode !== 0) {
                                throw new Error(opRslt.message);
                            }
                            that.view.showExporters(opRslt.resultObjects);
                        } catch (error) {
                            that.messages(error);
                        }
                    }
                });
            }
            /** 运行,覆盖原方法 */
            run(): void {
                super.run.apply(this, arguments);
            }
            private criteria: ibas.ICriteria;
            /** 获取Schema */
            schema(type: string): void {
                this.busy(true);
                let that: this = this;
                let boRepository: bo.BORepositoryImportExport = new bo.BORepositoryImportExport();
                boRepository.schema({
                    boCode: this.criteria.businessObject,
                    type: type,
                    onCompleted(opRslt: ibas.IOperationResult<string>): void {
                        try {
                            that.busy(false);
                            if (opRslt.resultCode !== 0) {
                                throw new Error(opRslt.message);
                            }
                            that.view.showSchemaContent(opRslt.resultObjects.firstOrDefault(), type);
                        } catch (error) {
                            that.messages(error);
                        }
                    }
                });
            }
            /** 导出 */
            export(exporter: bo.IDataExporter): void {
                if (ibas.strings.isEmpty(this.criteria.businessObject)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "BusinessObject"));
                }
                this.busy(true);
                let that: this = this;
                exporter.export({
                    criteria: this.criteria,
                    onCompleted(opRslt: ibas.IOperationResult<bo.IDataExportResult>): void {
                        try {
                            that.busy(false);
                            if (opRslt.resultCode !== 0) {
                                throw new Error(opRslt.message);
                            }
                            for (let item of opRslt.resultObjects) {
                                if (item instanceof bo.DataExportResultBlob) {
                                    item.fileName = ibas.strings.format("{0}_{1}", that.criteria.businessObject, item.fileName);
                                    item.fileName = item.fileName.toUpperCase();
                                }
                            }
                            that.view.showResluts(opRslt.resultObjects);
                        } catch (error) {
                            that.messages(error);
                        }
                    }

                });
                this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("importexport_exporting"));
            }
            /** 选择业务对象事件 */
            /** 查询数据 */
            protected fetchData(criteria: ibas.ICriteria): void {
                this.busy(true);
                if (criteria instanceof ibas.Criteria) {
                    criteria.noChilds = true;
                }
                let that: this = this;
                if (criteria.conditions.firstOrDefault(c =>
                    c.alias === initialfantasy.bo.BOInformation.PROPERTY_CODE_NAME
                    && c.operation === ibas.emConditionOperation.NOT_CONTAIN
                    && c.value === ".") === null) {
                    if (criteria.conditions.length > 1) {
                        criteria.conditions.firstOrDefault().bracketOpen += 1;
                        criteria.conditions.lastOrDefault().bracketClose += 1;
                    }
                    let condition: ibas.ICondition = criteria.conditions.create();
                    condition.alias = initialfantasy.bo.BOInformation.PROPERTY_CODE_NAME;
                    condition.value = ".";
                    condition.operation = ibas.emConditionOperation.NOT_CONTAIN;
                }
                let boRepository: initialfantasy.bo.BORepositoryInitialFantasy = new initialfantasy.bo.BORepositoryInitialFantasy();
                boRepository.fetchBOInformation({
                    criteria: criteria,
                    onCompleted(opRslt: ibas.IOperationResult<initialfantasy.bo.BOInformation>): void {
                        try {
                            that.busy(false);
                            if (opRslt.resultCode !== 0) {
                                throw new Error(opRslt.message);
                            }
                            if (!that.isViewShowed()) {
                                // 没显示视图，先显示
                                that.show();
                            }
                            if (opRslt.resultObjects.length === 0) {
                                that.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("shell_data_fetched_none"));
                            }
                            that.view.showBusinessObjects(opRslt.resultObjects);
                        } catch (error) {
                            that.messages(error);
                        }
                    }
                });
                this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("shell_fetching_data"));
            }
            private selectedBusinessObject(data: initialfantasy.bo.BOInformation): void {
                if (ibas.objects.isNull(data)) {
                    this.messages(ibas.emMessageType.WARNING, ibas.i18n.prop("shell_please_chooose_data"));
                    return;
                }
                this.criteria.businessObject = data.code;
                this.criteria.sorts.clear();
                this.criteria.conditions.clear();
                this.criteria.childCriterias.clear();
                this.view.showConditions(this.criteria.conditions);
                let criteria: ibas.Criteria = new ibas.Criteria();
                let condition: ibas.ICondition = criteria.conditions.create();
                condition.alias = initialfantasy.bo.BOPropertyInformation.PROPERTY_CODE_NAME;
                condition.value = this.criteria.businessObject;
                let sort: ibas.ISort = criteria.sorts.create();
                sort.alias = initialfantasy.bo.BOPropertyInformation.PROPERTY_DESCRIPTION_NAME;
                sort.sortType = ibas.emSortType.ASCENDING;
                sort = criteria.sorts.create();
                sort.alias = initialfantasy.bo.BOPropertyInformation.PROPERTY_PROPERTY_NAME;
                sort.sortType = ibas.emSortType.ASCENDING;

                let boRepository: initialfantasy.bo.BORepositoryInitialFantasy = new initialfantasy.bo.BORepositoryInitialFantasy();
                boRepository.fetchBOPropertyInformation({
                    criteria: criteria,
                    onCompleted: (opRslt) => {
                        this.view.showBusinessObjectProperties(opRslt.resultObjects);
                    }
                });
            }
            private addQueryCondition(): void {
                this.criteria.conditions.create();
                if (this.criteria.conditions.length > 0 && this.criteria.conditions.firstOrDefault()?.relationship !== ibas.emConditionRelationship.NONE) {
                    this.criteria.conditions.firstOrDefault().relationship = ibas.emConditionRelationship.NONE;
                }
                this.view.showConditions(this.criteria.conditions);
            }
            private removeQueryCondition(conditions: ibas.ICondition[]): void {
                for (let condition of ibas.arrays.create(conditions)) {
                    this.criteria.conditions.remove(condition);
                }
                if (this.criteria.conditions.length > 0 && this.criteria.conditions.firstOrDefault()?.relationship !== ibas.emConditionRelationship.NONE) {
                    this.criteria.conditions.firstOrDefault().relationship = ibas.emConditionRelationship.NONE;
                }
                this.view.showConditions(this.criteria.conditions);
            }
        }
        /** 数据导出-视图 */
        export interface IDataExportView extends ibas.IBOQueryView {
            /** 获取Schema，参数1，类型（xml,json） */
            schemaEvent: Function;
            /** 显示schema内容 */
            showSchemaContent(content: string, type: string): void;
            /** 显示数据导出者 */
            showExporters(exporters: bo.IDataExporter[]): void;
            /** 选择业务对象 */
            selectedBusinessObjectEvent: Function;
            /** 显示业务对象信息 */
            showBusinessObjects(datas: initialfantasy.bo.BOInformation[]): void;
            /** 显示业务对象属性信息 */
            showBusinessObjectProperties(datas: initialfantasy.bo.BOPropertyInformation[]): void;
            /** 添加条件 */
            addConditionEvent: Function;
            /** 移出条件 */
            removeConditionEvent: Function;
            /** 显示结果 */
            showConditions(conditions: ibas.ICondition[]): void;
            /** 导出 */
            exportEvent: Function;
            /** 显示结果 */
            showResluts(results: bo.IDataExportResult[]): void;
        }
    }
}