/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {
        /** 数据打印 */
        export abstract class AbstractDataPrintService<T extends ibas.IServiceContract> extends ibas.ServiceApplication<IDataPrintView, T>  {
            /** 注册视图 */
            protected registerView(): void {
                super.registerView();
                this.view.previewEvent = this.preview;
                this.view.printEvent = this.print;
            }
            /** 视图显示后 */
            protected viewShowed(): void {
                // 视图加载完成
            }
            protected printData: any[];
            /** 预览 */
            private preview(exporter: bo.IDataExporter): void {
                if (ibas.objects.isNull(exporter)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "exporter"));
                }
                if (ibas.objects.isNull(this.printData)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "printData"));
                }
                this.busy(true);
                let that: this = this;
                exporter.export({
                    data: this.printData,
                    contentType: "text/html",
                    onCompleted(opRslt: ibas.IOperationResult<bo.IDataExportResult>): void {
                        try {
                            that.busy(false);
                            if (opRslt.resultCode !== 0) {
                                throw new Error(opRslt.message);
                            }
                            // 获取大小
                            let width: string = "";
                            let height: string = "";
                            try {
                                let size: string = exporter.description.substring(
                                    exporter.description.lastIndexOf("(") + 1, exporter.description.lastIndexOf(")")
                                );
                                let tmps: string[] = size.split("\*");
                                if (tmps.length >= 2) {
                                    width = (parseInt(tmps[0], 0) + 30).toString();
                                    height = tmps[1];
                                }
                            } catch (error) {
                            }
                            for (let item of opRslt.resultObjects) {
                                that.view.showContent(item.content, width, height);
                            }
                        } catch (error) {
                            that.messages(error);
                        }
                    }
                });
                this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("importexport_export_is_running",
                    ibas.strings.isEmpty(exporter.description) ? exporter.name : exporter.description));
            }
            /** 打印 */
            private print(printId: string): void {
                if (ibas.strings.isEmpty(printId)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "printId"));
                }
                let pWindow: Window = (<any>document.getElementById(printId)).contentWindow;
                pWindow.print();
            }
        }
        /** 数据打印-视图 */
        export interface IDataPrintView extends ibas.IView {
            /** 打印 */
            printEvent: Function;
            /** 预览 */
            previewEvent: Function;
            /** 显示数据导出者 */
            showExporters(exporters: bo.IDataExporter[]): void;
            /** 显示内容 */
            showContent(content: Blob, width: string, height: string): void;
        }
        /** 数据打印 */
        export class DataPrintService extends AbstractDataPrintService<IDataPrintServiceContract>  {
            /** 应用标识 */
            static APPLICATION_ID: string = "c6d50b88-7418-4ee9-9b6b-c8002ab1a611";
            /** 应用名称 */
            static APPLICATION_NAME: string = "importexport_app_dataprint";
            constructor() {
                super();
                this.id = DataPrintService.APPLICATION_ID;
                this.name = DataPrintService.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 运行服务 */
            runService(contract: IDataPrintServiceContract): void {
                this.printData = new ibas.ArrayList();
                // 处理数据
                let boCode: string = null;
                if (contract.data instanceof Array) {
                    for (let item of contract.data) {
                        boCode = (<ibas.IBOStorageTag><any>item).objectCode;
                        if (!ibas.objects.isNull(contract.converter)) {
                            this.printData.push(contract.converter.convert(item, this.name));
                        } else {
                            this.printData.push(item);
                        }
                    }
                } else if (!ibas.objects.isNull(contract.data)) {
                    boCode = (<ibas.IBOStorageTag><any>contract.data).objectCode;
                    if (!ibas.objects.isNull(contract.converter)) {
                        this.printData.push(contract.converter.convert(contract.data, this.name));
                    } else {
                        this.printData.push(contract.data);
                    }
                }
                if (ibas.strings.isEmpty(contract.businessObject) && !ibas.strings.isEmpty(boCode)) {
                    contract.businessObject = boCode;
                }
                // 处理模板
                if (!ibas.strings.isEmpty(contract.content)) {
                    // 直接显示打印内容
                    this.show();
                    var blob: Blob = new Blob([contract.content], { type: "text/html; charset=utf-8" });
                    this.view.showContent(blob, null, null);
                } else if (!ibas.objects.isNull(contract.template) || !ibas.objects.isNull(contract.businessObject)) {
                    let criteria: ibas.ICriteria = new ibas.Criteria();
                    if (!ibas.strings.isEmpty(contract.businessObject)) {
                        criteria.businessObject = contract.businessObject;
                    }
                    let condition: ibas.ICondition = criteria.conditions.create();
                    condition.alias = bo.ExportTemplate.PROPERTY_ACTIVATED_NAME;
                    condition.value = ibas.emYesNo.YES.toString();
                    if (contract.template instanceof Array) {
                        for (let item of contract.template) {
                            if (item instanceof ibas.Condition) {
                                criteria.conditions.add(item);
                            }
                        }
                    } else if (typeof contract.template === "number") {
                        condition = criteria.conditions.create();
                        condition.alias = bo.ExportTemplate.PROPERTY_OBJECTKEY_NAME;
                        condition.value = contract.template.toString();
                    }
                    condition = criteria.conditions.create();
                    condition.alias = "Transformer";
                    condition.value = "TO_FILE_HTML";
                    condition.operation = ibas.emConditionOperation.START;
                    let that: this = this;
                    let boRepository: bo.BORepositoryImportExport = new bo.BORepositoryImportExport();
                    boRepository.fetchDataExporter({
                        criteria: criteria,
                        onCompleted(opRslt: ibas.IOperationResult<bo.IDataExporter>): void {
                            try {
                                if (opRslt.resultCode !== 0) {
                                    throw new Error(opRslt.message);
                                }
                                if (!that.isViewShowed()) {
                                    that.show();
                                }
                                that.view.showExporters(opRslt.resultObjects);
                            } catch (error) {
                                that.messages(error);
                            }
                        }
                    });
                }
            }
        }
        /** 数据打印映射 */
        export class DataPrintServiceMapping extends ibas.ServiceMapping {
            constructor() {
                super();
                this.id = DataPrintService.APPLICATION_ID;
                this.name = DataPrintService.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
                this.proxy = ibas.BOServiceProxy;
                this.icon = ibas.i18n.prop("importexport_print_icon");
                this.category = SERVICE_CATEGORY_BO_PRINT;
            }
            /** 创建服务实例 */
            create(): ibas.IService<ibas.IServiceContract> {
                return new DataPrintService();
            }
        }

        /** 数据表格打印 */
        export class DataTablePrintService extends AbstractDataPrintService<ibas.IDataTableServiceContract>  {
            /** 应用标识 */
            static APPLICATION_ID: string = "a338c536-437c-441b-8131-ce4c0945a4e4";
            /** 应用名称 */
            static APPLICATION_NAME: string = "importexport_app_datatableprint";
            constructor() {
                super();
                this.id = DataTablePrintService.APPLICATION_ID;
                this.name = DataTablePrintService.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 运行服务 */
            runService(contract: ibas.IDataTableServiceContract): void {
                this.printData = new ibas.ArrayList();
                if (contract.data instanceof ibas.DataTable) {
                    for (let item of contract.data.convert()) {
                        this.printData.push(item);
                    }
                }
                let criteria: ibas.ICriteria = new ibas.Criteria();
                criteria.businessObject = ibas.DataTable.name;
                let condition: ibas.ICondition = criteria.conditions.create();
                condition.alias = bo.ExportTemplate.PROPERTY_ACTIVATED_NAME;
                condition.value = ibas.emYesNo.YES.toString();
                condition = criteria.conditions.create();
                condition.alias = "Transformer";
                condition.value = "TO_FILE_HTML";
                condition.operation = ibas.emConditionOperation.START;
                let that: this = this;
                let boRepository: bo.BORepositoryImportExport = new bo.BORepositoryImportExport();
                boRepository.fetchDataExporter({
                    criteria: criteria,
                    onCompleted(opRslt: ibas.IOperationResult<bo.IDataExporter>): void {
                        try {
                            if (opRslt.resultCode !== 0) {
                                throw new Error(opRslt.message);
                            }
                            if (!that.isViewShowed()) {
                                that.show();
                            }
                            that.view.showExporters(opRslt.resultObjects);
                        } catch (error) {
                            that.messages(error);
                        }
                    }
                });
            }
        }
        /** 数据表格打印映射 */
        export class DataTablePrintServiceMapping extends ibas.ServiceMapping {
            constructor() {
                super();
                this.id = DataTablePrintService.APPLICATION_ID;
                this.name = DataTablePrintService.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
                this.proxy = ibas.DataTableServiceProxy;
                this.icon = ibas.i18n.prop("importexport_print_icon");
                this.category = SERVICE_CATEGORY_DATATABLE_PRINT;
            }
            /** 创建服务实例 */
            create(): ibas.IService<ibas.IServiceContract> {
                return new DataTablePrintService();
            }
        }
    }
}