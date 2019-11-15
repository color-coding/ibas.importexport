/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace ui {
        export namespace c {
            /**
             * 视图-数据导出
             */
            export class DataExportView extends ibas.View implements app.IDataExportView {
                /** 获取Schema，参数1，类型（xml,json） */
                schemaEvent: Function;
                /** 选择业务对象 */
                chooseBusinessObjectEvent: Function;
                /** 选择导出模板 */
                chooseTemplateEvent: Function;
                /** 导出 */
                exportEvent: Function;
                /** 添加条件 */
                addConditionEvent: Function;
                /** 移出条件 */
                removeConditionEvent: Function;
                /** 绘制视图 */
                draw(): any {
                    let that: this = this;
                    this.form = new sap.ui.layout.form.SimpleForm("", {
                        editable: true,
                        content: [
                            new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_export_criteria") }),
                            new sap.m.Label("", { text: ibas.i18n.prop("importexport_export_template") }),
                            this.sltTempalte = new sap.extension.m.Select("", {}),
                            new sap.m.Label("", { text: ibas.i18n.prop("importexport_export_bo") }),
                            new sap.extension.m.RepositoryInput("", {
                                showValueHelp: true,
                                repository: initialfantasy.bo.BORepositoryInitialFantasy,
                                dataInfo: {
                                    type: initialfantasy.bo.BOInformation,
                                    key: initialfantasy.bo.BOInformation.PROPERTY_CODE_NAME,
                                    text: initialfantasy.bo.BOInformation.PROPERTY_DESCRIPTION_NAME
                                },
                                valueHelpRequest: function (): void {
                                    that.fireViewEvents(that.chooseBusinessObjectEvent);
                                }
                            }).bindProperty("bindingValue", {
                                path: "/businessObject",
                                type: new sap.extension.data.Alphanumeric(),
                            }),
                            new sap.ui.core.Title("", {}),
                            new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_export_conditions") }),
                        ]
                    });
                    return new sap.m.Page("", {
                        showHeader: false,
                        subHeader: new sap.m.Toolbar("", {
                            content: [
                                new sap.m.Button("", {
                                    text: ibas.i18n.prop("importexport_export"),
                                    type: sap.m.ButtonType.Transparent,
                                    icon: "sap-icon://toaster-up",
                                    press: function (): void {
                                        let item: sap.ui.core.Item = that.sltTempalte.getSelectedItem();
                                        if (ibas.objects.isNull(item)) {
                                            return;
                                        }
                                        that.fireViewEvents(that.exportEvent, (<any>item.getModel()).getData());
                                    }
                                }),
                                new sap.m.ToolbarSeparator(""),
                                new sap.m.MenuButton("", {
                                    text: "schema",
                                    type: sap.m.ButtonType.Transparent,
                                    icon: "sap-icon://document-text",
                                    menu: new sap.m.Menu("", {
                                        items: [
                                            new sap.m.MenuItem("", {
                                                text: "json",
                                                icon: "sap-icon://attachment-e-pub",
                                                press: function (event: any): void {
                                                    that.fireViewEvents(that.schemaEvent, "json");
                                                }
                                            }),
                                            new sap.m.MenuItem("", {
                                                text: "xml",
                                                icon: "sap-icon://attachment-html",
                                                press: function (event: any): void {
                                                    that.fireViewEvents(that.schemaEvent, "xml");
                                                }
                                            }),
                                        ],
                                    })
                                })
                            ]
                        }),
                        content: [
                            this.form
                        ]
                    });
                }
                private form: sap.ui.layout.form.SimpleForm;
                private table: sap.extension.table.Table;
                private sltTempalte: sap.extension.m.Select;
                private criteria: ibas.ICriteria;
                /** 显示结果 */
                showConditions(conditions: ibas.ICondition[]): void {
                    if (ibas.objects.isNull(this.criteria) || ibas.strings.isEmpty(this.criteria.businessObject)) {
                        return;
                    }
                    if (ibas.objects.isNull(conditions)) {
                        if (!ibas.objects.isNull(this.table)) {
                            this.table.destroy(false);
                            this.table = null;
                        }
                        return;
                    }
                    if (ibas.objects.isNull(this.table)) {
                        // 初始化表格
                        let that: this = this;
                        let boRepository: initialfantasy.bo.IBORepositoryInitialFantasy = ibas.boFactory.create(initialfantasy.bo.BO_REPOSITORY_INITIALFANTASY);
                        boRepository.fetchBOInformation({
                            criteria: [
                                new ibas.Condition("Code", ibas.emConditionOperation.EQUAL, this.criteria.businessObject)
                            ],
                            onCompleted(opRslt: ibas.IOperationResult<initialfantasy.bo.IBOInformation>): void {
                                let boInfo: initialfantasy.bo.IBOInformation = opRslt.resultObjects.firstOrDefault();
                                if (ibas.objects.isNull(boInfo)) {
                                    that.table = that.createTable([]);
                                    that.form.addContent(that.table);
                                } else {
                                    that.table = that.createTable(boInfo.boPropertyInformations);
                                    that.form.addContent(that.table);
                                }
                                that.table.setModel(new sap.extension.model.JSONModel({ rows: conditions }));
                            }
                        });
                    } else {
                        this.table.setModel(new sap.extension.model.JSONModel({ rows: conditions }));
                    }
                }
                private createTable(properies: initialfantasy.bo.IBOPropertyInformation[]): sap.extension.table.Table {
                    let that: this = this;
                    let table: sap.extension.table.Table = new sap.extension.table.Table("", {
                        enableSelectAll: false,
                        visibleRowCount: 5,
                        toolbar: new sap.m.Toolbar("", {
                            content: [
                                new sap.m.Button("", {
                                    text: ibas.i18n.prop("shell_data_add"),
                                    type: sap.m.ButtonType.Transparent,
                                    icon: "sap-icon://add",
                                    press: function (): void {
                                        that.fireViewEvents(that.addConditionEvent);
                                    }
                                }),
                                new sap.m.Button("", {
                                    text: ibas.i18n.prop("shell_data_remove"),
                                    type: sap.m.ButtonType.Transparent,
                                    icon: "sap-icon://less",
                                    press: function (): void {
                                        that.fireViewEvents(that.removeConditionEvent, that.table.getSelecteds().firstOrDefault());
                                    }
                                })
                            ]
                        }),
                        rows: "{/rows}",
                        columns: [
                            new sap.extension.table.Column("", {
                                label: ibas.i18n.prop("shell_query_condition_relationship"),
                                template: new sap.extension.m.EnumSelect("", {
                                    enumType: ibas.emConditionRelationship
                                }).bindProperty("bindingValue", {
                                    path: "relationship",
                                    type: new sap.extension.data.ConditionRelationship()
                                })
                            }),
                            new sap.extension.table.Column("", {
                                label: ibas.i18n.prop("shell_query_condition_bracketopen"),
                                template: new sap.extension.m.RepeatCharSelect("", {
                                    repeatText: "(",
                                    maxCount: 5,
                                }).bindProperty("bindingValue", {
                                    path: "bracketOpen",
                                    type: new sap.extension.data.Numeric()
                                })
                            }),
                            new sap.extension.table.Column("", {
                                label: ibas.i18n.prop("shell_query_condition_alias"),
                                template: new sap.extension.m.Select("", {
                                    items: this.getPropertyListItem(properies)
                                }).bindProperty("bindingValue", {
                                    path: "alias",
                                    type: new sap.extension.data.Alphanumeric()
                                })
                            }),
                            new sap.extension.table.Column("", {
                                label: ibas.i18n.prop("shell_query_condition_operation"),
                                template: new sap.extension.m.EnumSelect("", {
                                    enumType: ibas.emConditionOperation
                                }).bindProperty("bindingValue", {
                                    path: "operation",
                                    type: new sap.extension.data.ConditionOperation()
                                })
                            }),
                            new sap.extension.table.Column("", {
                                label: ibas.i18n.prop("shell_query_condition_value"),
                                template: new sap.extension.m.Input("", {
                                }).bindProperty("bindingValue", {
                                    path: "value",
                                    type: new sap.extension.data.Alphanumeric()
                                }),
                            }),
                            new sap.extension.table.Column("", {
                                label: ibas.i18n.prop("shell_query_condition_bracketclose"),
                                template: new sap.extension.m.RepeatCharSelect("", {
                                    repeatText: ")",
                                    maxCount: 5,
                                }).bindProperty("bindingValue", {
                                    path: "bracketClose",
                                    type: new sap.extension.data.Numeric()
                                })
                            }),
                        ]
                    });
                    return table;
                }
                private getPropertyListItem(properies: initialfantasy.bo.IBOPropertyInformation[]): sap.ui.core.ListItem[] {
                    let items: Array<sap.ui.core.ListItem> = [];
                    items.push(new sap.ui.core.ListItem("", {
                        key: "",
                        text: ibas.i18n.prop("shell_please_chooose_data", ""),
                    }));
                    if (!ibas.objects.isNull(properies)) {
                        for (let property of properies) {
                            items.push(new sap.ui.core.ListItem("", {
                                key: property.property,
                                text: property.description,
                            }));
                        }
                    }
                    return items;
                }
                /** 显示查询 */
                showCriteria(criteria: ibas.ICriteria): void {
                    // 显示查询
                    this.form.setModel(new sap.ui.model.json.JSONModel(criteria));
                    this.criteria = criteria;
                }
                /** 显示导出者 */
                showExporters(exporters: bo.IDataExporter[]): void {
                    for (let item of exporters) {
                        let sItem: sap.ui.core.Item = new sap.ui.core.Item("", {
                            key: item.name,
                            text: ibas.strings.isEmpty(item.description) ? item.name : item.description,
                        });
                        sItem.setModel(new sap.ui.model.json.JSONModel(item));
                        this.sltTempalte.addItem(sItem);
                    }
                }
                /** 显示结果 */
                showResluts(results: bo.IDataExportResult[]): void {
                    for (let result of results) {
                        if (result instanceof bo.DataExportResultBlob) {
                            ibas.files.save(result.content, result.fileName);
                        }
                    }
                }
            }
        }
    }
}