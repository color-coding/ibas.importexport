/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace app {
        /** 编辑应用-导出模板 */
        export class ExportTemplateEditApp extends ibas.BOEditApplication<IExportTemplateEditView, bo.ExportTemplate> {

            /** 应用标识 */
            static APPLICATION_ID: string = "72869ec1-90de-4354-8a2c-1c06f2184a53";
            /** 应用名称 */
            static APPLICATION_NAME: string = "importexport_app_exporttemplate_edit";
            /** 业务对象编码 */
            static BUSINESS_OBJECT_CODE: string = bo.ExportTemplate.BUSINESS_OBJECT_CODE;
            /** 构造函数 */
            constructor() {
                super();
                this.id = ExportTemplateEditApp.APPLICATION_ID;
                this.name = ExportTemplateEditApp.APPLICATION_NAME;
                this.boCode = ExportTemplateEditApp.BUSINESS_OBJECT_CODE;
                this.description = ibas.i18n.prop(this.name);
            }
            /** 注册视图 */
            protected registerView(): void {
                super.registerView();
                // 其他事件
                this.view.deleteDataEvent = this.deleteData;
                this.view.createDataEvent = this.createData;
                this.view.addPageHeaderEvent = this.addPageHeader;
                this.view.removePageHeaderEvent = this.removePageHeader;
                this.view.addStartSectionEvent = this.addStartSection;
                this.view.removeStartSectionEvent = this.removeStartSection;
                this.view.addRepetitionHeaderEvent = this.addRepetitionHeader;
                this.view.removeRepetitionHeaderEvent = this.removeRepetitionHeader;
                this.view.addRepetitionEvent = this.addRepetition;
                this.view.removeRepetitionEvent = this.removeRepetition;
                this.view.addRepetitionFooterEvent = this.addRepetitionFooter;
                this.view.removeRepetitionFooterEvent = this.removeRepetitionFooter;
                this.view.addEndSectionEvent = this.addEndSection;
                this.view.removeEndSectionEvent = this.removeEndSection;
                this.view.addPageFooterEvent = this.addPageFooter;
                this.view.removePageFooterEvent = this.removePageFooter;
            }
            /** 视图显示后 */
            protected viewShowed(): void {
                // 视图加载完成
                if (ibas.objects.isNull(this.editData)) {
                    // 创建编辑对象实例
                    this.editData = new bo.ExportTemplate();
                    this.proceeding(ibas.emMessageType.WARNING, ibas.i18n.prop("shell_data_created_new"));
                }
                this.view.showExportTemplate(this.editData);
                this.view.showPageHeaders(this.editData.pageHeaders.filterDeleted());
                this.view.showStartSections(this.editData.startSections.filterDeleted());
                this.view.showRepetitionHeaders(this.editData.repetitionHeaders.filterDeleted());
                this.view.showRepetitions(this.editData.repetitions.filterDeleted());
                this.view.showRepetitionFooters(this.editData.repetitionFooters.filterDeleted());
                this.view.showEndSections(this.editData.endSections.filterDeleted());
                this.view.showPageFooters(this.editData.pageFooters.filterDeleted());
            }
            run(): void;
            run(data: bo.ExportTemplate): void;
            run(): void {
                let that: this = this;
                if (ibas.objects.instanceOf(arguments[0], bo.ExportTemplate)) {
                    let data: bo.ExportTemplate = arguments[0];
                    // 新对象直接编辑
                    if (data.isNew) {
                        that.editData = data;
                        that.show();
                        return;
                    }
                    // 尝试重新查询编辑对象
                    let criteria: ibas.ICriteria = data.criteria();
                    if (!ibas.objects.isNull(criteria) && criteria.conditions.length > 0) {
                        // 有效的查询对象查询
                        let boRepository: bo.BORepositoryImportExport = new bo.BORepositoryImportExport();
                        boRepository.fetchExportTemplate({
                            criteria: criteria,
                            onCompleted(opRslt: ibas.IOperationResult<bo.ExportTemplate>): void {
                                let data: bo.ExportTemplate;
                                if (opRslt.resultCode === 0) {
                                    data = opRslt.resultObjects.firstOrDefault();
                                }
                                if (ibas.objects.instanceOf(data, bo.ExportTemplate)) {
                                    // 查询到了有效数据
                                    that.editData = data;
                                    that.show();
                                } else {
                                    // 数据重新检索无效
                                    that.messages({
                                        type: ibas.emMessageType.WARNING,
                                        message: ibas.i18n.prop("shell_data_deleted_and_created"),
                                        onCompleted(): void {
                                            that.show();
                                        }
                                    });
                                }
                            }
                        });
                        return; // 退出
                    }
                }
                super.run.apply(this, arguments);
            }
            /** 待编辑的数据 */
            protected editData: bo.ExportTemplate;
            /** 保存数据 */
            protected saveData(): void {
                let that: this = this;
                let boRepository: bo.BORepositoryImportExport = new bo.BORepositoryImportExport();
                boRepository.saveExportTemplate({
                    beSaved: this.editData,
                    onCompleted(opRslt: ibas.IOperationResult<bo.ExportTemplate>): void {
                        try {
                            that.busy(false);
                            if (opRslt.resultCode !== 0) {
                                throw new Error(opRslt.message);
                            }
                            if (opRslt.resultObjects.length === 0) {
                                // 删除成功，释放当前对象
                                that.messages(ibas.emMessageType.SUCCESS,
                                    ibas.i18n.prop("shell_data_delete") + ibas.i18n.prop("shell_sucessful"));
                                that.editData = undefined;
                            } else {
                                // 替换编辑对象
                                that.editData = opRslt.resultObjects.firstOrDefault();
                                that.messages(ibas.emMessageType.SUCCESS,
                                    ibas.i18n.prop("shell_data_save") + ibas.i18n.prop("shell_sucessful"));
                            }
                            // 刷新当前视图
                            that.viewShowed();
                        } catch (error) {
                            that.messages(error);
                        }
                    }
                });
                this.busy(true);
                this.proceeding(ibas.emMessageType.INFORMATION, ibas.i18n.prop("shell_saving_data"));
            }
            /** 删除数据 */
            protected deleteData(): void {
                let that: this = this;
                this.messages({
                    type: ibas.emMessageType.QUESTION,
                    title: ibas.i18n.prop(this.name),
                    message: ibas.i18n.prop("shell_delete_continue"),
                    actions: [ibas.emMessageAction.YES, ibas.emMessageAction.NO],
                    onCompleted(action: ibas.emMessageAction): void {
                        if (action === ibas.emMessageAction.YES) {
                            that.editData.delete();
                            that.saveData();
                        }
                    }
                });
            }
            /** 新建数据，参数1：是否克隆 */
            protected createData(clone: boolean): void {
                let that: this = this;
                let createData: Function = function (): void {
                    if (clone) {
                        // 克隆对象
                        that.editData = that.editData.clone();
                        that.proceeding(ibas.emMessageType.WARNING, ibas.i18n.prop("shell_data_cloned_new"));
                        that.viewShowed();
                    } else {
                        // 新建对象
                        that.editData = new bo.ExportTemplate();
                        that.proceeding(ibas.emMessageType.WARNING, ibas.i18n.prop("shell_data_created_new"));
                        that.viewShowed();
                    }
                };
                if (that.editData.isDirty) {
                    this.messages({
                        type: ibas.emMessageType.QUESTION,
                        title: ibas.i18n.prop(this.name),
                        message: ibas.i18n.prop("shell_data_not_saved_continue"),
                        actions: [ibas.emMessageAction.YES, ibas.emMessageAction.NO],
                        onCompleted(action: ibas.emMessageAction): void {
                            if (action === ibas.emMessageAction.YES) {
                                createData();
                            }
                        }
                    });
                } else {
                    createData();
                }
            }
            /** 添加导出模板-项事件 */
            protected addPageHeader(): void {
                this.editData.pageHeaders.create();
                // 仅显示没有标记删除的
                this.view.showPageHeaders(this.editData.pageHeaders.filterDeleted());
            }
            /** 删除导出模板-项事件 */
            protected removePageHeader(items: bo.ExportTemplateItem[]): void {
                // 非数组，转为数组
                if (!(items instanceof Array)) {
                    items = [items];
                }
                if (items.length === 0) {
                    return;
                }
                // 移除项目
                for (let item of items) {
                    if (this.editData.pageHeaders.indexOf(item) >= 0) {
                        if (item.isNew) {
                            // 新建的移除集合
                            this.editData.pageHeaders.remove(item);
                        } else {
                            // 非新建标记删除
                            item.delete();
                        }
                    }
                }
                // 仅显示没有标记删除的
                this.view.showPageHeaders(this.editData.pageHeaders.filterDeleted());
            }
            /** 添加导出模板-项事件 */
            protected addStartSection(): void {
                this.editData.startSections.create();
                // 仅显示没有标记删除的
                this.view.showStartSections(this.editData.startSections.filterDeleted());
            }
            /** 删除导出模板-项事件 */
            protected removeStartSection(items: bo.ExportTemplateItem[]): void {
                // 非数组，转为数组
                if (!(items instanceof Array)) {
                    items = [items];
                }
                if (items.length === 0) {
                    return;
                }
                // 移除项目
                for (let item of items) {
                    if (this.editData.startSections.indexOf(item) >= 0) {
                        if (item.isNew) {
                            // 新建的移除集合
                            this.editData.startSections.remove(item);
                        } else {
                            // 非新建标记删除
                            item.delete();
                        }
                    }
                }
                // 仅显示没有标记删除的
                this.view.showStartSections(this.editData.startSections.filterDeleted());
            }

            /** 添加导出模板-项事件 */
            protected addRepetitionHeader(): void {
                this.editData.repetitionHeaders.create();
                // 仅显示没有标记删除的
                this.view.showRepetitionHeaders(this.editData.repetitionHeaders.filterDeleted());
            }
            /** 删除导出模板-项事件 */
            protected removeRepetitionHeader(items: bo.ExportTemplateItem[]): void {
                // 非数组，转为数组
                if (!(items instanceof Array)) {
                    items = [items];
                }
                if (items.length === 0) {
                    return;
                }
                // 移除项目
                for (let item of items) {
                    if (this.editData.repetitionHeaders.indexOf(item) >= 0) {
                        if (item.isNew) {
                            // 新建的移除集合
                            this.editData.repetitionHeaders.remove(item);
                        } else {
                            // 非新建标记删除
                            item.delete();
                        }
                    }
                }
                // 仅显示没有标记删除的
                this.view.showRepetitionHeaders(this.editData.repetitionHeaders.filterDeleted());
            }
            /** 添加导出模板-项事件 */
            protected addRepetition(): void {
                this.editData.repetitions.create();
                // 仅显示没有标记删除的
                this.view.showRepetitions(this.editData.repetitions.filterDeleted());
            }
            /** 删除导出模板-项事件 */
            protected removeRepetition(items: bo.ExportTemplateItem[]): void {
                // 非数组，转为数组
                if (!(items instanceof Array)) {
                    items = [items];
                }
                if (items.length === 0) {
                    return;
                }
                // 移除项目
                for (let item of items) {
                    if (this.editData.repetitions.indexOf(item) >= 0) {
                        if (item.isNew) {
                            // 新建的移除集合
                            this.editData.repetitions.remove(item);
                        } else {
                            // 非新建标记删除
                            item.delete();
                        }
                    }
                }
                // 仅显示没有标记删除的
                this.view.showRepetitions(this.editData.repetitions.filterDeleted());
            }
            /** 添加导出模板-项事件 */
            protected addRepetitionFooter(): void {
                this.editData.repetitionFooters.create();
                // 仅显示没有标记删除的
                this.view.showRepetitionFooters(this.editData.repetitionFooters.filterDeleted());
            }
            /** 删除导出模板-项事件 */
            protected removeRepetitionFooter(items: bo.ExportTemplateItem[]): void {
                // 非数组，转为数组
                if (!(items instanceof Array)) {
                    items = [items];
                }
                if (items.length === 0) {
                    return;
                }
                // 移除项目
                for (let item of items) {
                    if (this.editData.repetitionFooters.indexOf(item) >= 0) {
                        if (item.isNew) {
                            // 新建的移除集合
                            this.editData.repetitionFooters.remove(item);
                        } else {
                            // 非新建标记删除
                            item.delete();
                        }
                    }
                }
                // 仅显示没有标记删除的
                this.view.showRepetitionFooters(this.editData.repetitionFooters.filterDeleted());
            }
            /** 添加导出模板-项事件 */
            protected addEndSection(): void {
                this.editData.endSections.create();
                // 仅显示没有标记删除的
                this.view.showEndSections(this.editData.endSections.filterDeleted());
            }
            /** 删除导出模板-项事件 */
            protected removeEndSection(items: bo.ExportTemplateItem[]): void {
                // 非数组，转为数组
                if (!(items instanceof Array)) {
                    items = [items];
                }
                if (items.length === 0) {
                    return;
                }
                // 移除项目
                for (let item of items) {
                    if (this.editData.endSections.indexOf(item) >= 0) {
                        if (item.isNew) {
                            // 新建的移除集合
                            this.editData.endSections.remove(item);
                        } else {
                            // 非新建标记删除
                            item.delete();
                        }
                    }
                }
                // 仅显示没有标记删除的
                this.view.showEndSections(this.editData.endSections.filterDeleted());
            }
            /** 添加导出模板-项事件 */
            protected addPageFooter(): void {
                this.editData.pageFooters.create();
                // 仅显示没有标记删除的
                this.view.showPageFooters(this.editData.pageFooters.filterDeleted());
            }
            /** 删除导出模板-项事件 */
            protected removePageFooter(items: bo.ExportTemplateItem[]): void {
                // 非数组，转为数组
                if (!(items instanceof Array)) {
                    items = [items];
                }
                if (items.length === 0) {
                    return;
                }
                // 移除项目
                for (let item of items) {
                    if (this.editData.pageFooters.indexOf(item) >= 0) {
                        if (item.isNew) {
                            // 新建的移除集合
                            this.editData.pageFooters.remove(item);
                        } else {
                            // 非新建标记删除
                            item.delete();
                        }
                    }
                }
                // 仅显示没有标记删除的
                this.view.showPageFooters(this.editData.pageFooters.filterDeleted());
            }
        }
        /** 视图-导出模板 */
        export interface IExportTemplateEditView extends ibas.IBOEditView {
            /** 显示数据 */
            showExportTemplate(data: bo.ExportTemplate): void;
            /** 删除数据事件 */
            deleteDataEvent: Function;
            /** 新建数据事件，参数1：是否克隆 */
            createDataEvent: Function;
            /** 添加导出模板-项事件 */
            addPageHeaderEvent: Function;
            /** 删除导出模板-项事件 */
            removePageHeaderEvent: Function;
            /** 显示数据-页眉 */
            showPageHeaders(datas: bo.ExportTemplateItem[]): void;
            /** 添加导出模板-项事件 */
            addStartSectionEvent: Function;
            /** 删除导出模板-项事件 */
            removeStartSectionEvent: Function;
            /** 显示数据-开始区域 */
            showStartSections(datas: bo.ExportTemplateItem[]): void;
            /** 添加导出模板-项事件 */
            addRepetitionHeaderEvent: Function;
            /** 删除导出模板-项事件 */
            removeRepetitionHeaderEvent: Function;
            /** 显示数据-重复区头 */
            showRepetitionHeaders(datas: bo.ExportTemplateItem[]): void;
            /** 添加导出模板-项事件 */
            addRepetitionEvent: Function;
            /** 删除导出模板-项事件 */
            removeRepetitionEvent: Function;
            /** 显示数据-重复区 */
            showRepetitions(datas: bo.ExportTemplateItem[]): void;
            /** 添加导出模板-项事件 */
            addRepetitionFooterEvent: Function;
            /** 删除导出模板-项事件 */
            removeRepetitionFooterEvent: Function;
            /** 显示数据-重复区脚 */
            showRepetitionFooters(datas: bo.ExportTemplateItem[]): void;
            /** 添加导出模板-项事件 */
            addEndSectionEvent: Function;
            /** 删除导出模板-项事件 */
            removeEndSectionEvent: Function;
            /** 显示数据-结束区域 */
            showEndSections(datas: bo.ExportTemplateItem[]): void;
            /** 添加导出模板-项事件 */
            addPageFooterEvent: Function;
            /** 删除导出模板-项事件 */
            removePageFooterEvent: Function;
            /** 显示数据-页脚 */
            showPageFooters(datas: bo.ExportTemplateItem[]): void;
        }
    }
}
