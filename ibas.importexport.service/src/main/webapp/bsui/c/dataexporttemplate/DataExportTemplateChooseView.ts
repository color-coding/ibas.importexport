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
import { IDataExportTemplateChooseView } from "../../../bsapp/DataExportTemplate/index";

/**
 * 视图-DataExportTemplate
 */
export class DataExportTemplateChooseView extends ibas.BOChooseView implements IDataExportTemplateChooseView {
    /** 返回查询的对象 */
    get queryTarget(): any {
        return bo.DataExportTemplate;
    }
    /** 绘制工具条 */
    darwBars(): any {
        let that = this;
        return [
            new sap.m.Button("", {
                text: ibas.i18n.prop("sys_shell_ui_data_new"),
                type: sap.m.ButtonType.Transparent,
                // icon: "sap-icon://create",
                press: function (): void {
                    that.fireViewEvents(that.newDataEvent);
                }
            }),
            new sap.m.Button("", {
                text: ibas.i18n.prop("sys_shell_ui_data_choose"),
                type: sap.m.ButtonType.Transparent,
                // icon: "sap-icon://accept",
                press: function (): void {
                    that.fireViewEvents(that.chooseDataEvent,
                        // 获取表格选中的对象
                        utils.getTableSelecteds<bo.DataExportTemplate>(that.table)
                    );
                }
            }),
            new sap.m.Button("", {
                text: ibas.i18n.prop("sys_shell_ui_exit"),
                type: sap.m.ButtonType.Transparent,
                // icon: "sap-icon://inspect-down",
                press: function (): void {
                    that.fireViewEvents(that.closeEvent);
                }
            }),
        ]
    }
    /** 绘制视图 */
    darw(): any {
        let that = this;
        this.table = new sap.ui.table.Table("", {
            enableSelectAll: false,
            visibleRowCount: 15,
            rows: "{/}",
            columns: [
            ]
        });
        this.id = this.table.getId();
        // 添加列表自动查询事件
        utils.triggerNextResults({
            listener: this.table,
            next(data: any): void {
                if (ibas.objects.isNull(that.lastCriteria)) {
                    return;
                }
                let criteria: ibas.ICriteria = that.lastCriteria.next(data);
                if (ibas.objects.isNull(criteria)) {
                    return;
                }
                ibas.logger.log(ibas.emMessageLevel.DEBUG, "result: {0}", criteria.toString());
                that.fireViewEvents(that.fetchDataEvent, criteria);
            }
        });
        return this.table;
    }
    private table: sap.ui.table.Table;
    /** 显示数据 */
    showData(datas: bo.DataExportTemplate[]): void {
        let done: boolean = false;
        let model: sap.ui.model.Model = this.table.getModel(undefined);
        if (!ibas.objects.isNull(model)) {
            // 已存在绑定数据，添加新的
            let hDatas: bo.DataExportTemplate[] = (<any>model).getData();
            if (!ibas.objects.isNull(hDatas)) {
                for (let item of datas) {
                    hDatas.push(item);
                }
                model.refresh(false);
                done = true;
            }
        }
        if (!done) {
            // 没有显示数据
            this.table.setModel(new sap.ui.model.json.JSONModel(datas));
        }
        this.table.setBusy(false);
    }
    private lastCriteria: ibas.ICriteria;
    /** 记录上次查询条件，表格滚动时自动触发 */
    query(criteria: ibas.ICriteria): void {
        super.query(criteria);
        this.lastCriteria = criteria;
        // 清除历史数据
        this.table.setBusy(true);
        this.table.setFirstVisibleRow(0);
        this.table.setModel(null);
    }
}
