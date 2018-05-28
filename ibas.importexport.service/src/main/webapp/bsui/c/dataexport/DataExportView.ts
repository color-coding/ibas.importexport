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
            export class DataExportView extends ibas.BOView implements app.IDataExportView {
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
                    this.sltTempalte = new sap.m.Select("", {});
                    this.form = new sap.ui.layout.form.SimpleForm("", {
                        editable: true,
                        content: [
                            new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_export_criteria") }),
                            new sap.m.Label("", { text: ibas.i18n.prop("importexport_export_template") }),
                            /*
                            new sap.m.Input("", {
                                showValueHelp: true,
                                valueHelpRequest: function (): void {
                                    that.fireViewEvents(that.chooseTemplateEvent);
                                }
                            }).bindProperty("value", {
                                path: "/remarks"
                            }),
                            */
                            this.sltTempalte,
                            new sap.m.Label("", { text: ibas.i18n.prop("importexport_export_bo") }),
                            new sap.m.Input("", {
                                showValueHelp: true,
                                valueHelpRequest: function (): void {
                                    that.fireViewEvents(that.chooseBusinessObjectEvent);
                                }
                            }).bindProperty("value", {
                                path: "/businessObject"
                            }),
                            new sap.ui.core.Title("", { text: ibas.i18n.prop("importexport_export_conditions") }),

                        ]
                    });
                    this.page = new sap.m.Page("", {
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
                        content: [this.form]
                    });
                    this.id = this.page.getId();
                    return this.page;
                }
                private page: sap.m.Page;
                private form: sap.ui.layout.form.SimpleForm;
                private uploader: sap.ui.unified.FileUploader;
                private table: sap.ui.table.Table;
                private sltTempalte: sap.m.Select;
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
                                that.table.setModel(new sap.ui.model.json.JSONModel({ rows: conditions }));
                            }
                        });
                    } else {
                        this.table.setModel(new sap.ui.model.json.JSONModel({ rows: conditions }));
                    }
                }
                private createTable(properies: initialfantasy.bo.IBOPropertyInformation[]): sap.ui.table.Table {
                    let that: this = this;
                    let table: sap.ui.table.Table = new sap.ui.table.Table("", {
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
                                        let selected: any = openui5.utils.getSelecteds(that.table).firstOrDefault();
                                        that.fireViewEvents(that.removeConditionEvent, selected);
                                    }
                                })
                            ]
                        }),
                        visibleRowCount: 5,
                        enableSelectAll: false,
                        selectionBehavior: sap.ui.table.SelectionBehavior.Row,
                        rows: "{/rows}",
                        columns: [
                            new sap.ui.table.Column("", {
                                label: ibas.i18n.prop("shell_query_condition_relationship"),
                                width: "100px",
                                template: new sap.m.Select("", {
                                    width: "100%",
                                    items: openui5.utils.createComboBoxItems(ibas.emConditionRelationship)
                                }).bindProperty("selectedKey", {
                                    path: "relationship",
                                    type: "sap.ui.model.type.Integer"
                                })
                            }),
                            new sap.ui.table.Column("", {
                                label: ibas.i18n.prop("shell_query_condition_bracketopen"),
                                width: "100px",
                                template: new sap.m.Select("", {
                                    width: "100%",
                                    items: this.getCharListItem("(")
                                }).bindProperty("selectedKey", {
                                    path: "bracketOpen",
                                    type: "sap.ui.model.type.Integer"
                                })
                            }),
                            new sap.ui.table.Column("", {
                                label: ibas.i18n.prop("shell_query_condition_alias"),
                                width: "200px",
                                template: new sap.m.Select("", {
                                    width: "100%",
                                    items: this.getPropertyListItem(properies)
                                }).bindProperty("selectedKey", {
                                    path: "alias",
                                })
                            }),
                            new sap.ui.table.Column("", {
                                label: ibas.i18n.prop("shell_query_condition_operation"),
                                width: "140px",
                                template: new sap.m.Select("", {
                                    width: "100%",
                                    items: openui5.utils.createComboBoxItems(ibas.emConditionOperation)
                                }).bindProperty("selectedKey", {
                                    path: "operation",
                                    type: "sap.ui.model.type.Integer"
                                })
                            }),
                            new sap.ui.table.Column("", {
                                label: ibas.i18n.prop("shell_query_condition_value"),
                                width: "120px",
                                template: new sap.m.Input("", {
                                }).bindProperty("value", {
                                    path: "value"
                                }),
                            }),
                            new sap.ui.table.Column("", {
                                label: ibas.i18n.prop("shell_query_condition_bracketclose"),
                                width: "100px",
                                template: new sap.m.Select("", {
                                    width: "100%",
                                    items: this.getCharListItem(")")
                                }).bindProperty("selectedKey", {
                                    path: "bracketClose",
                                    type: "sap.ui.model.type.Integer"
                                })
                            })
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
                private getCharListItem(char: string): sap.ui.core.ListItem[] {
                    // 获取重复的字符
                    let count: number = 4;
                    let items: Array<sap.ui.core.ListItem> = [];
                    items.push(new sap.ui.core.ListItem("", {
                        key: 0,
                        text: "",
                    }));
                    let vChar: string = char;
                    for (let i: number = 1; i < count; i++) {
                        items.push(new sap.ui.core.ListItem("", {
                            key: i,
                            text: vChar,
                        }));
                        vChar = vChar + char;
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