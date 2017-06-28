/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import { IDataExportMode, DataExportModeJson, IExportResult } from "./modes/DataExportModes";

/** 数据导出服务 */
export class DataExportService extends ibas.Application<IDataExportView> implements ibas.IService<ibas.IBOServiceContract> {

    /** 应用标识 */
    static APPLICATION_ID: string = "34a8ebc2-b105-42ea-ad06-b813fb782f9" + "a";
    /** 应用名称 */
    static APPLICATION_NAME: string = "importexport_service_dataexport";

    constructor() {
        super();
        this.id = DataExportService.APPLICATION_ID;
        this.name = DataExportService.APPLICATION_NAME;
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
        let modes: ibas.ArrayList<IDataExportMode> = new ibas.ArrayList<IDataExportMode>();
        modes.add(new DataExportModeJson());
        this.view.showModes(modes);
    }
    /** 运行,覆盖原方法 */
    run(...args: any[]): void {
        if (!ibas.objects.isNull(args) && args.length === 1) {
            let contract: ibas.IBOServiceContract = <ibas.IBOServiceContract>args[0];
            if (!ibas.objects.isNull(contract.data)) {
                this.exportDatas = new ibas.ArrayList<any>();
                if (!ibas.objects.isNull(contract.converter)) {
                    // 存在数据转换者，转换数据
                    if (Array.isArray(contract.data)) {
                        for (let item of contract.data) {
                            this.exportDatas.add(contract.converter.convert(item, this.name));
                        }
                    } else {
                        this.exportDatas.add(contract.converter.convert(contract.data, this.name));
                    }
                } else {
                    this.exportDatas.add(contract.data);
                }
                super.run();
                return;
            }
        }
        // 输入数据无效，服务不运行
        this.proceeding(ibas.emMessageType.WARNING,
            ibas.i18n.prop("importexport_service_dataexport") + ibas.i18n.prop("sys_invalid_parameter", "data"));

    }
    /** 导出的数据 */
    private exportDatas: ibas.List<any>;
    /** 导出数据，参数1：使用的方式 */
    exportData(mode: IDataExportMode): void {
        let that: this = this;
        if (!ibas.objects.isNull(mode)) {
            mode.export({
                datas: this.exportDatas,
                onCompleted(result: IExportResult): void {
                    try {
                        if (ibas.objects.isNull(result.address)) {
                            // 没有地址表示失败
                            throw new Error(result.content);
                        }
                        that.view.showReslut(result);
                    } catch (error) {
                        that.messages(error);
                    }
                }
            });
            this.close();
            this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("importexport_export_is_running", mode.description));
        } else {
            this.proceeding(ibas.emMessageType.WARNING, ibas.i18n.prop("sys_invalid_parameter", "mode"));
        }
    }
}
/** 数据导出服务-视图 */
export interface IDataExportView extends ibas.IView {
    /** 显示可用的模板 */
    showModes(modes: IDataExportMode[]): void;
    /** 导出数据，参数1：使用的方式 */
    exportDataEvent: Function;
    /** 显示结果 */
    showReslut(result: IExportResult): void;
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
    /** 创建服务并运行 */
    create(): ibas.IService<ibas.IServiceContract> {
        return new DataExportService();
    }
}
/** 数据导出服务映射 */
export class DataListExportServiceMapping extends ibas.ServiceMapping {

    constructor() {
        super();
        this.id = DataExportService.APPLICATION_ID + "b";
        this.name = DataExportService.APPLICATION_NAME;
        this.description = ibas.i18n.prop(this.name);
        this.proxy = ibas.BOListServiceProxy;
        this.icon = ibas.i18n.prop("importexport_export_icon");
    }
    /** 创建服务并运行 */
    create(): ibas.IService<ibas.IServiceContract> {
        return new DataExportService();
    }
}