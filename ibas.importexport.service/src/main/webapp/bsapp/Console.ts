/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
/// <reference path="../3rdparty/ibas/index.d.ts" />
/// <reference path="../borep/index.ts" />
/// <reference path="./dataexport/index.ts" />
/// <reference path="./exporttemplate/index.ts" />
/// <reference path="./dataimport/index.ts" />
/// <reference path="./dataprint/index.ts" />

namespace importexport {
    export namespace app {
        /** 模块控制台 */
        export class Console extends ibas.ModuleConsole {
            /** 构造函数 */
            constructor() {
                super();
                this.id = CONSOLE_ID;
                this.name = CONSOLE_NAME;
                this.version = CONSOLE_VERSION;
                this.copyright = ibas.i18n.prop("shell_license");
            }
            private _navigation: ibas.IViewNavigation;
            /** 创建视图导航 */
            navigation(): ibas.IViewNavigation {
                return this._navigation;
            }
            /** 初始化 */
            protected registers(): void {
                // 注册功能
                this.register(new DataImportFunc());
                this.register(new DataExportFunc());
                this.register(new ExportTemplateFunc());
                // 注册服务应用
                this.register(new DataExportServiceMapping());
                this.register(new ExportTemplateChooseServiceMapping());
                this.register(new DataPrintServiceMapping());
                // 注册常驻应用


            }
            /** 运行 */
            run(): void {
                // 加载语言-框架默认
                ibas.i18n.load(this.rootUrl + "resources/languages/importexport.json");
                ibas.i18n.load(this.rootUrl + "resources/languages/bos.json");
                // 设置资源属性
                this.description = ibas.i18n.prop(this.name.toLowerCase());
                this.icon = ibas.i18n.prop(this.name.toLowerCase() + "_icon");
                // 先加载ui导航
                let uiModules: string[] = [];
                if (!ibas.config.get(ibas.CONFIG_ITEM_DISABLE_PLATFORM_VIEW, false)) {
                    if (this.plantform === ibas.emPlantform.PHONE) {
                        // 使用m类型视图
                        uiModules.push("index.ui.m");
                    }
                }
                // 默认使用视图
                if (uiModules.length === 0) {
                    // 使用c类型视图
                    uiModules.push("index.ui.c");
                }
                let that: this = this;
                this.loadUI(uiModules, function (ui: any): void {
                    // 设置导航
                    that._navigation = new ui.Navigation();
                    // 调用初始化
                    that.initialize();
                });
                // 保留基类方法
                super.run();
            }
        }
    }
}