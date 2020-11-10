/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {
        /** 数据导出服务 */
        export class DataExportService extends ibas.ServiceApplication<IDataExportServiceView, ibas.IBOServiceContract>  {
            /** 应用标识 */
            static APPLICATION_ID: string = "34a8ebc2-b105-42ea-ad06-b813fb782f9a";
            /** 应用名称 */
            static APPLICATION_NAME: string = "importexport_service_dataexport";
            constructor() {
                super();
                this.id = DataExportService.APPLICATION_ID;
                this.name = DataExportService.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 运行服务 */
            runService(contract: ibas.IBOServiceContract): void {
                if (!ibas.objects.isNull(contract) && !ibas.objects.isNull(contract.data)) {
                    let boCode: string = null;
                    this.exportDatas = new ibas.ArrayList<any>();
                    if (!ibas.objects.isNull(contract.converter)) {
                        // 存在数据转换者，转换数据
                        if (Array.isArray(contract.data)) {
                            for (let item of contract.data) {
                                if (ibas.strings.isEmpty(boCode)) {
                                    boCode = (<ibas.IBOStorageTag><any>item).objectCode;
                                }
                                this.exportDatas.add(contract.converter.convert(item, this.name));
                            }
                        } else {
                            boCode = (<ibas.IBOStorageTag><any>contract.data).objectCode;
                            this.exportDatas.add(contract.converter.convert(contract.data, this.name));
                        }
                    } else {
                        this.exportDatas.add(contract.data);
                    }
                    let criteria: ibas.ICriteria = new ibas.Criteria();
                    if (!ibas.strings.isEmpty(boCode)) {
                        criteria.businessObject = boCode;
                    }
                    let condition: ibas.ICondition = criteria.conditions.create();
                    condition.alias = "Transformer";
                    condition.value = "TO_FILE_";
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
                                let exporters: ibas.IList<bo.IDataExporter> = new ibas.ArrayList();
                                exporters.add(new bo.DataExporterJson());
                                for (let item of opRslt.resultObjects) {
                                    if (item instanceof bo.DataExporterService) {
                                        let sItem: bo.DataExporterService = item;
                                        if (ibas.strings.isEmpty(sItem.template)) {
                                            continue;
                                        }
                                    }
                                    exporters.add(item);
                                }
                                that.view.showExporters(exporters);
                            } catch (error) {
                                that.messages(error);
                            }
                        }
                    });
                } else {
                    // 输入数据无效，服务不运行
                    this.proceeding(ibas.emMessageType.WARNING,
                        ibas.i18n.prop("importexport_service_dataexport") + ibas.i18n.prop("sys_invalid_parameter", "data"));
                }
            }
            /** 注册视图 */
            protected registerView(): void {
                super.registerView();
                this.view.exportDataEvent = this.exportData;
            }
            /** 视图显示后 */
            protected viewShowed(): void {
                // 视图加载完成
            }
            /** 导出的数据 */
            private exportDatas: ibas.IList<any>;
            /** 导出数据，参数1：使用的方式 */
            private exportData(exporter: bo.IDataExporter): void {
                if (ibas.objects.isNull(exporter)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "exporter"));
                }
                let that: this = this;
                exporter.export({
                    data: this.exportDatas,
                    onCompleted(opRslt: ibas.IOperationResult<any>): void {
                        try {
                            if (opRslt.resultCode !== 0) {
                                throw new Error(opRslt.message);
                            }
                            that.view.showResluts(opRslt.resultObjects);
                        } catch (error) {
                            that.messages(error);
                        }
                    }
                });
                this.close();
                this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("importexport_export_is_running",
                    ibas.strings.isEmpty(exporter.description) ? exporter.name : exporter.description));
            }
        }
        /** 数据导出服务-视图 */
        export interface IDataExportServiceView extends ibas.IView {
            /** 显示数据导出者 */
            showExporters(exporters: bo.IDataExporter[]): void;
            /** 导出数据，参数1：使用的方式 */
            exportDataEvent: Function;
            /** 显示结果 */
            showResluts(results: bo.IDataExportResult[]): void;
        }
        /** 数据导出服务映射 */
        export class DataExportServiceMapping extends ibas.ServiceMapping {
            constructor() {
                super();
                this.id = DataExportService.APPLICATION_ID;
                this.name = DataExportService.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
                this.proxy = ibas.BOServiceProxy;
                this.icon = ibas.i18n.prop("importexport_export_icon");
            }
            /** 创建服务实例 */
            create(): ibas.IService<ibas.IServiceContract> {
                return new DataExportService();
            }
        }
        /** 数据表导出服务 */
        export class DataTableExportService extends ibas.ServiceApplication<IDataExportServiceView, ibas.IDataTableServiceContract>  {
            /** 应用标识 */
            static APPLICATION_ID: string = "9d54fade-2393-4840-a3f9-7f8e2c1c0605";
            /** 应用名称 */
            static APPLICATION_NAME: string = "importexport_service_datatableexport";
            constructor() {
                super();
                this.id = DataTableExportService.APPLICATION_ID;
                this.name = DataTableExportService.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 注册视图 */
            protected registerView(): void {
                super.registerView();
                this.view.exportDataEvent = this.exportData;
            }
            /** 视图显示后 */
            protected viewShowed(): void {
                // 视图加载完成
            }
            /** 运行服务 */
            runService(contract: ibas.IDataTableServiceContract): void {
                if (contract.data instanceof ibas.DataTable) {
                    this.exportDataTable = contract.data;
                    let exporters: ibas.IList<bo.IDataExporter> = new ibas.ArrayList();
                    exporters.add(new bo.DataTableExporterJson());
                    exporters.add(new bo.DataTableExporterCSV());
                    exporters.add(new bo.DataTableExporterXLSX());
                    this.show();
                    this.view.showExporters(exporters);
                } else {
                    // 输入数据无效，服务不运行
                    this.proceeding(ibas.emMessageType.WARNING,
                        ibas.i18n.prop("importexport_service_dataexport") + ibas.i18n.prop("sys_invalid_parameter", "data"));
                }
            }
            /** 导出的数据 */
            private exportDataTable: ibas.DataTable;
            /** 导出数据，参数1：使用的方式 */
            private exportData(exporter: bo.IDataExporter): void {
                if (ibas.objects.isNull(exporter)) {
                    throw new Error(ibas.i18n.prop("sys_invalid_parameter", "exporter"));
                }
                let that: this = this;
                exporter.export({
                    data: this.exportDataTable,
                    onCompleted(opRslt: ibas.IOperationResult<any>): void {
                        try {
                            if (opRslt.resultCode !== 0) {
                                throw new Error(opRslt.message);
                            }
                            that.view.showResluts(opRslt.resultObjects);
                        } catch (error) {
                            that.messages(error);
                        }
                    }
                });
                this.close();
                this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("importexport_export_is_running",
                    ibas.strings.isEmpty(exporter.description) ? exporter.name : exporter.description));
            }
        }
        /** 数据表导出服务映射 */
        export class DataTableExportServiceMapping extends ibas.ServiceMapping {
            constructor() {
                super();
                this.id = DataTableExportService.APPLICATION_ID;
                this.name = DataTableExportService.APPLICATION_NAME;
                this.description = ibas.i18n.prop(this.name);
                this.proxy = ibas.DataTableServiceProxy;
                this.icon = ibas.i18n.prop("importexport_export_icon");
            }
            /** 创建服务实例 */
            create(): ibas.IService<ibas.IServiceContract> {
                return new DataTableExportService();
            }
        }
    }
}