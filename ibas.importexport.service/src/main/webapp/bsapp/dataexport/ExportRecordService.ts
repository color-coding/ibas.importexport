/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {
        /** 导出记录服务 */
        export class ExportRecordService extends ibas.ServiceApplication<IExportRecordServiceView, ibas.IBOServiceContract> {
            /** 应用标识 */
            static APPLICATION_ID: string = "bd2c1373-c7ab-462b-8379-e90f248d285d";
            /** 应用名称 */
            static APPLICATION_NAME: string = "importexport_service_exportrecord";

            constructor() {
                super();
                this.id = ExportRecordService.APPLICATION_ID;
                this.name = ExportRecordService.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 注册视图 */
            protected registerView(): void {
                super.registerView();
            }
            /** 视图显示后 */
            protected viewShowed(): void {
                // 视图加载完成
                this.view.showBusinessObject(this.bo);
                let boKeys: string = this.bo.toString();
                let condition: ibas.ICondition = null;
                let criteria: ibas.ICriteria = new ibas.Criteria();
                if (boKeys.startsWith("{[") && boKeys.endsWith("]}")) {
                    let tmp: string = boKeys.substring(1, boKeys.length - 1);
                    tmp = ibas.strings.remove(tmp, "[", "]");
                    let values: string[] = tmp.split(".");
                    if (values.length === 2) {
                        condition = criteria.conditions.create();
                        condition.alias = bo.ExportRecord.PROPERTY_BOCODE_NAME;
                        condition.value = values[0];
                    }
                }
                condition = criteria.conditions.create();
                condition.alias = bo.ExportRecord.PROPERTY_BOKEYS_NAME;
                condition.value = boKeys;
                if (criteria.conditions.length === 2) {
                    // 解析到主键相关
                    let sort: ibas.ISort = criteria.sorts.create();
                    sort.alias = bo.ExportRecord.PROPERTY_EXPORTTIME_NAME;
                    sort.sortType = ibas.emSortType.DESCENDING;
                    sort = criteria.sorts.create();
                    sort.alias = bo.ExportRecord.PROPERTY_EXPORTDATE_NAME;
                    sort.sortType = ibas.emSortType.DESCENDING;
                    sort = criteria.sorts.create();
                    sort.alias = bo.ExportRecord.PROPERTY_LOGINST_NAME;
                    sort.sortType = ibas.emSortType.DESCENDING;
                    let that: this = this;
                    let boRepository: bo.BORepositoryImportExport = new bo.BORepositoryImportExport();
                    boRepository.fetchExportRecord({
                        criteria: criteria,
                        onCompleted(opRslt: ibas.IOperationResult<bo.ExportRecord>): void {
                            try {
                                if (opRslt.resultCode !== 0) {
                                    throw new Error(opRslt.message);
                                }
                                that.view.showRecords(opRslt.resultObjects);
                            } catch (error) {
                                that.messages(error);
                            }
                        }
                    });
                }
            }
            /** 运行服务 */
            runService(contract: ibas.IBOServiceContract): void {
                if (!ibas.objects.isNull(contract)) {
                    // 传入的数据可能是数组
                    if (contract.data instanceof Array) {
                        // 数组只处理第一个
                        this.bo = contract.data[0];
                    } else {
                        this.bo = contract.data;
                    }
                }
                if (ibas.objects.isNull(this.bo)) {
                    // 输入数据无效，服务不运行
                    this.proceeding(ibas.emMessageType.WARNING,
                        ibas.i18n.prop("documents_bo_document_service") + ibas.i18n.prop("sys_invalid_parameter", "data"));
                } else if (this.bo instanceof ibas.BusinessObject && this.bo.isNew) {
                    // 单据未保存，服务不运行
                    this.messages(ibas.emMessageType.WARNING, ibas.i18n.prop("shell_data_saved_first"));
                } else {
                    super.show();
                }
            }
            /** 关联的数据 */
            private bo: ibas.IBusinessObject;

        }
        /** 导出记录服务-视图 */
        export interface IExportRecordServiceView extends ibas.IView {
            /** 显示关联对象 */
            showBusinessObject(bo: ibas.IBusinessObject): void;
            /** 显示已存在日志 */
            showRecords(datas: bo.ExportRecord[]): void;
        }
        /** 导出记录服务映射 */
        export class ExportRecordServiceMapping extends ibas.ServiceMapping {

            constructor() {
                super();
                this.id = ExportRecordService.APPLICATION_ID;
                this.name = ExportRecordService.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
                this.proxy = ibas.BOServiceProxy;
                this.icon = ibas.i18n.prop("importexport_exportrecord_icon");
            }
            /** 创建服务实例 */
            create(): ibas.IService<ibas.IServiceContract> {
                return new ExportRecordService();
            }
        }
    }
}