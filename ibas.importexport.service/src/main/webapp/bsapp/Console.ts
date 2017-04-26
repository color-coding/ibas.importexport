/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import {
    DataExportTemplateFunc,
    DataExportTemplateChooseServiceMapping,
    DataExportTemplateLinkServiceMapping
} from "./dataexporttemplate/index";
import {
    DataExportServiceMapping,
    DataListExportServiceMapping,
} from "./dataexport/index";
import {
    DataImportFunc
} from "./dataimport/index";

/** 模块控制台 */
export class Console extends ibas.ModuleConsole {
    /** 模块-标识 */
    static CONSOLE_ID: string = "cbd4ef0f-ee88-4ea7-96c7-13f974026b58";
    /** 模块-名称 */
    static CONSOLE_NAME: string = "ImportExport";
    /** 根文件名称 */
    static ROOT_FILE_NAME: string = "importexport/index";
    /** 构造函数 */
    constructor() {
        super();
        this.id = Console.CONSOLE_ID;
        this.name = Console.CONSOLE_NAME;
    }
    private _navigation: ibas.IViewNavigation;
    /** 创建视图导航 */
    navigation(): ibas.IViewNavigation {
        return this._navigation;
    }
    /** 初始化 */
    protected registers(): void {
        // 注册功能
        this.register(new DataExportTemplateFunc());
        this.register(new DataImportFunc());
        // 注册服务应用
        this.register(new DataExportTemplateChooseServiceMapping());
        this.register(new DataExportTemplateLinkServiceMapping());
        this.register(new DataExportServiceMapping());
        this.register(new DataListExportServiceMapping());
        // 注册常驻应用


    }
    /** 运行 */
    run(): void {
        // 获取根地址
        let rootUrl: string = ibas.url.rootUrl(Console.ROOT_FILE_NAME);
        // 加载语言-框架默认
        ibas.i18n.load(ibas.strings.format("{0}/resources/languages/importexport.json", rootUrl));
        ibas.i18n.load(ibas.strings.format("{0}/resources/languages/bo/dataexporttemplate.json", rootUrl));
        // 设置资源属性
        this.description = ibas.i18n.prop(this.name.toLowerCase());
        this.icon = ibas.i18n.prop(this.name.toLowerCase() + "_icon");
        // 先加载ui导航
        let uiModules: string[] = [];
        if (!ibas.config.get(ibas.ModuleConsole.CONFIG_ITEM_DISABLE_PLATFORM_VIEW, false)
            && this.plantform === ibas.emPlantform.PHONE) {
            // 使用m类型视图
            uiModules.push("../bsui/m/Navigation");
        } else {
            // 使用c类型视图
            uiModules.push("../bsui/c/Navigation");
        }
        let that: Console = this;
        require(uiModules, function (ui: any): void {
            // 设置导航
            that._navigation = new ui.default();
            // 调用初始化
            that.initialize();
        });
        // 保留基类方法
        super.run();
    }
}
