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
import { IDataExportTemplateEditView } from "../../../bsapp/dataexporttemplate/index";

/**
 * 视图-DataExportTemplate
 */
export class DataExportTemplateEditView extends ibas.BOEditView implements IDataExportTemplateEditView {

    /** 删除数据事件 */
    deleteDataEvent: Function;
    /** 添加数据导出模板-项事件 */
    addDataExportTemplateItemEvent: Function;
    /** 删除数据导出模板-项事件 */
    removeDataExportTemplateItemEvent: Function;

    /** 绘制视图 */
    darw(): any {
        let that = this;
        this.form = new sap.ui.layout.form.SimpleForm("", {
            content: [
            ]
        });
        this.form.addContent(new sap.ui.core.Title("", { text: ibas.i18n.prop("bo_dataexporttemplateitem") }));
        this.tableDataExportTemplateItem = new sap.ui.table.Table("", {
            extension: new sap.m.Toolbar("", {
                content: [
                    new sap.m.Button("", {
                        text: ibas.i18n.prop("sys_shell_data_add"),
                        type: sap.m.ButtonType.Transparent,
                        icon: "sap-icon://add",
                        press: function (): void {
                            that.fireViewEvents(that.addDataExportTemplateItemEvent);
                        }
                    }),
                    new sap.m.Button("", {
                        text: ibas.i18n.prop("sys_shell_data_remove"),
                        type: sap.m.ButtonType.Transparent,
                        icon: "sap-icon://less",
                        press: function (): void {
                            that.fireViewEvents(that.removeDataExportTemplateItemEvent);
                        }
                    })
                ]
            }),
            enableSelectAll: false,
            visibleRowCount: 6,
            rows: "{/}",
            columns: [
            ]
        });
        this.form.addContent(this.tableDataExportTemplateItem);
        this.page = new sap.m.Page("", {
            showHeader: false,
            subHeader: new sap.m.Toolbar("", {
                content: [
                    new sap.m.Button("", {
                        text: ibas.i18n.prop("sys_shell_data_save"),
                        type: sap.m.ButtonType.Transparent,
                        icon: "sap-icon://save",
                        press: function (): void {
                            that.fireViewEvents(that.saveDataEvent);
                        }
                    }),
                    new sap.m.ToolbarSeparator(""),
                    new sap.m.Button("", {
                        text: ibas.i18n.prop("sys_shell_data_delete"),
                        type: sap.m.ButtonType.Transparent,
                        icon: "sap-icon://delete",
                        press: function (): void {
                            that.fireViewEvents(that.deleteDataEvent);
                        }
                    }),
                ]
            }),
            content: [this.form]
        });
        this.id = this.page.getId();
        return this.page;
    }
    private page: sap.m.Page;
    private form: sap.ui.layout.form.SimpleForm;
    private tableDataExportTemplateItem: sap.ui.table.Table;

    /** 显示数据 */
    showDataExportTemplate(data: bo.DataExportTemplate): void {
        this.form.setModel(new sap.ui.model.json.JSONModel(data));
        // 监听属性改变，并更新控件
        utils.refreshModelChanged(this.form, data);
    }
    /** 显示数据 */
    showDataExportTemplateItems(datas: bo.DataExportTemplateItem[]): void {
        this.tableDataExportTemplateItem.setModel(new sap.ui.model.json.JSONModel(datas));
        // 监听属性改变，并更新控件
        utils.refreshModelChanged(this.tableDataExportTemplateItem, datas);
    }
}
