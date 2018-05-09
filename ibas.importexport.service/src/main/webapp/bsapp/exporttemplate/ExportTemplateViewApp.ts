/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {
        /** 查看应用-导出模板 */
        export class ExportTemplateViewApp extends ibas.BOViewService<IExportTemplateViewView> {

            /** 应用标识 */
            static APPLICATION_ID: string = "d443660a-2cbc-445e-a556-7e2d718f46e3";
            /** 应用名称 */
            static APPLICATION_NAME: string = "importexport_app_exporttemplate_view";
            /** 业务对象编码 */
            static BUSINESS_OBJECT_CODE: string = bo.ExportTemplate.BUSINESS_OBJECT_CODE;
            /** 构造函数 */
            constructor() {
                super();
                this.id = ExportTemplateViewApp.APPLICATION_ID;
                this.name = ExportTemplateViewApp.APPLICATION_NAME;
                this.boCode = ExportTemplateViewApp.BUSINESS_OBJECT_CODE;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 注册视图 */
            protected registerView(): void {
                super.registerView();
                // 其他事件
                this.view.editDataEvent = this.editData;
            }
            /** 视图显示后 */
            protected viewShowed(): void {
                // 视图加载完成
            }
            /** 编辑数据，参数：目标数据 */
            protected editData(): void {
                let app: ExportTemplateEditApp = new ExportTemplateEditApp();
                app.navigation = this.navigation;
                app.viewShower = this.viewShower;
                app.run(this.viewData);
            }
            run(): void;
            run(data: bo.ExportTemplate): void;
            /** 运行 */
            run(): void {
                if (ibas.objects.instanceOf(arguments[0], bo.ExportTemplate)) {
                    this.viewData = arguments[0];
                    this.show();
                } else {
                    super.run.apply(this, arguments);
                }
            }
            protected viewData: bo.ExportTemplate;
            /** 查询数据 */
            protected fetchData(criteria: ibas.ICriteria | string): void {
                this.busy(true);
                let that: this = this;
                if (typeof criteria === "string") {
                    criteria = new ibas.Criteria();
                    // 添加查询条件

                }
                let boRepository: bo.BORepositoryImportExport = new bo.BORepositoryImportExport();
                boRepository.fetchExportTemplate({
                    criteria: criteria,
                    onCompleted(opRslt: ibas.IOperationResult<bo.ExportTemplate>): void {
                        try {
                            if (opRslt.resultCode !== 0) {
                                throw new Error(opRslt.message);
                            }
                            that.viewData = opRslt.resultObjects.firstOrDefault();
                            that.viewShowed();
                        } catch (error) {
                            that.messages(error);
                        }
                    }
                });
                this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("shell_fetching_data"));
            }
        }
        /** 视图-导出模板 */
        export interface IExportTemplateViewView extends ibas.IBOViewView {

        }
        /** 导出模板连接服务映射 */
        export class ExportTemplateLinkServiceMapping extends ibas.BOLinkServiceMapping {
            /** 构造函数 */
            constructor() {
                super();
                this.id = ExportTemplateViewApp.APPLICATION_ID;
                this.name = ExportTemplateViewApp.APPLICATION_NAME;
                this.boCode = ExportTemplateViewApp.BUSINESS_OBJECT_CODE;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 创建服务实例 */
            create(): ibas.IBOLinkService {
                return new ExportTemplateViewApp();
            }
        }
    }
}
