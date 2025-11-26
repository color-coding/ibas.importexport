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
            export class DataExportView extends ibas.BOQueryViewWithPanel implements app.IDataExportView {
                /** 返回查询的对象 */
                get queryTarget(): any {
                    return initialfantasy.bo.BOInformation;
                }
                /** 获取Schema，参数1，类型（xml,json） */
                schemaEvent: Function;
                /** 选择业务对象 */
                selectedBusinessObjectEvent: Function;
                /** 导出 */
                exportEvent: Function;
                /** 添加条件 */
                addConditionEvent: Function;
                /** 移出条件 */
                removeConditionEvent: Function;
                /** 绘制视图 */
                draw(): any {
                    let that: this = this;
                    return new sap.m.SplitContainer("", {
                        masterPages: [
                            new sap.m.Page("", {
                                showHeader: false,
                                content: [
                                    this.list = new sap.extension.m.List("", {
                                        chooseType: ibas.emChooseType.SINGLE,
                                        growingThreshold: sap.extension.table.visibleRowCount(15),
                                        mode: sap.m.ListMode.SingleSelectMaster,
                                        items: {
                                            path: "/rows",
                                            template: new sap.m.ObjectListItem("", {
                                                title: "{description}",
                                                firstStatus: new sap.m.ObjectStatus("", {
                                                    text: "{objectType}"
                                                }),
                                                attributes: [
                                                    new sap.extension.m.ObjectAttribute("", {
                                                        bindingValue: {
                                                            path: "name",
                                                            type: new sap.extension.data.Alphanumeric(),
                                                        },
                                                    }),
                                                    new sap.extension.m.ObjectAttribute("", {
                                                        bindingValue: {
                                                            path: "code",
                                                            type: new sap.extension.data.Alphanumeric(),
                                                        },
                                                    }),
                                                ]
                                            })
                                        },
                                        selectionChange(): void {
                                            that.fireViewEvents(that.selectedBusinessObjectEvent, that.list.getSelecteds().firstOrDefault());
                                        },
                                        nextDataSet(event: sap.ui.base.Event): void {
                                            // 查询下一个数据集
                                            let data: any = event.getParameter("data");
                                            if (ibas.objects.isNull(data)) {
                                                return;
                                            }
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
                                    })
                                ]
                            })
                        ],
                        detailPages: [
                            new sap.m.Page("", {
                                showHeader: true,
                                customHeader: new sap.m.Toolbar("", {
                                    content: [
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
                                        }),
                                        new sap.m.ToolbarSpacer(""),
                                        new sap.m.Label("", {
                                            showColon: true,
                                            text: ibas.i18n.prop("importexport_export_template"),
                                        }),
                                        this.tpltSelect = new sap.extension.m.Select("", {
                                            items: {
                                                path: "/items",
                                                template: new sap.extension.m.SelectItem("", {
                                                    key: {
                                                        path: "name",
                                                        type: new sap.extension.data.Alphanumeric(),
                                                    },
                                                    text: {
                                                        parts: [
                                                            {
                                                                path: "name",
                                                            },
                                                            {
                                                                path: "description",
                                                            },
                                                        ],
                                                        formatter(name: string, description: string): string {
                                                            return ibas.strings.isEmpty(description) ? name : description;
                                                        }
                                                    },
                                                })
                                            }
                                        }),
                                        new sap.m.ToolbarSeparator(""),
                                        new sap.m.Button("", {
                                            text: ibas.i18n.prop("importexport_export"),
                                            type: sap.m.ButtonType.Transparent,
                                            icon: "sap-icon://toaster-up",
                                            press: function (): void {
                                                let item: sap.ui.core.Item = that.tpltSelect.getSelectedItem();
                                                if (ibas.objects.isNull(item)) {
                                                    return;
                                                }
                                                that.fireViewEvents(that.exportEvent, item.getBindingContext().getObject());
                                            }
                                        }),
                                    ]
                                }),
                                content: [
                                    this.message = new sap.m.IllustratedMessage("", {
                                        illustrationType: sap.m.IllustratedMessageType.NoFilterResults
                                    }),
                                    this.table = new sap.extension.table.Table("", {
                                        visible: false,
                                        enableSelectAll: true,
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
                                                        that.fireViewEvents(that.removeConditionEvent, that.table.getSelecteds());
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
                                            this.aliasColumn = new sap.extension.table.Column("", {
                                                label: ibas.i18n.prop("shell_query_condition_alias"),
                                                template: new sap.extension.m.Select("", {
                                                }).bindProperty("bindingValue", {
                                                    path: "alias",
                                                    type: new sap.extension.data.Alphanumeric()
                                                }),
                                                width: "16rem",
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
                                                width: "14rem",
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
                                        ],
                                        noData: ibas.i18n.prop("importexport_export_conditions"),
                                    })
                                ]
                            })
                        ],
                    });
                }
                /** 嵌入查询面板 */
                embedded(view: any): void {
                    if (view instanceof sap.m.Toolbar) {
                        view.setDesign(sap.m.ToolbarDesign.Transparent);
                        view.setStyle(sap.m.ToolbarStyle.Clear);
                        view.setHeight("100%");
                    }
                    (<sap.m.Page>this.list.getParent()).addHeaderContent(view);
                    (<sap.m.Page>this.list.getParent()).setShowHeader(true);
                }
                /** 记录上次查询条件，表格滚动时自动触发 */
                query(criteria: ibas.ICriteria): void {
                    super.query(criteria);
                    // 清除历史数据
                    if (this.isDisplayed) {
                        this.list.setBusy(true);
                        this.list.setModel(null);
                    }
                }

                private list: sap.extension.m.List;
                private table: sap.extension.table.Table;
                private message: sap.m.IllustratedMessage;
                private tpltSelect: sap.extension.m.Select;
                private aliasColumn: sap.ui.table.Column;

                /** 显示导出者 */
                showExporters(exporters: bo.IDataExporter[]): void {
                    this.tpltSelect.setModel(new sap.ui.model.json.JSONModel({ items: exporters }));
                }
                /** 显示schema内容 */
                showSchemaContent(content: string, type: string): void {
                    jQuery.sap.require("sap.ui.codeeditor.CodeEditor");
                    let editor: sap.ui.codeeditor.CodeEditor = new sap.ui.codeeditor.CodeEditor("", {
                        height: ibas.strings.format("{0}px", window.innerHeight * 0.6),
                        width: ibas.strings.format("{0}px", window.innerWidth * 0.6),
                        type: type,
                        colorTheme: "eclipse",
                    });
                    new sap.m.Dialog("", {
                        title: "Schema",
                        type: sap.m.DialogType.Standard,
                        state: sap.ui.core.ValueState.None,
                        content: [
                            editor
                        ],
                        buttons: [
                            new sap.m.Button("", {
                                text: ibas.i18n.prop("reportanalysis_sql_code_pretty"),
                                type: sap.m.ButtonType.Transparent,
                                icon: "sap-icon://text-formatting",
                                press: function (): void {
                                    editor.prettyPrint();
                                }
                            }),
                            new sap.m.Button("", {
                                text: ibas.i18n.prop("shell_exit"),
                                type: sap.m.ButtonType.Transparent,
                                icon: "sap-icon://inspect-down",
                                press: function (): void {
                                    (<sap.m.Dialog>editor.getParent()).close();
                                    editor = null;
                                }
                            }),
                        ]
                    }).addStyleClass("sapUiNoContentPadding").open();
                    editor.setValue(content);
                }

                /** 显示业务对象信息 */
                showBusinessObjects(datas: initialfantasy.bo.BOInformation[]): void {
                    let model: sap.ui.model.Model = this.list.getModel();
                    if (model instanceof sap.extension.model.JSONModel) {
                        // 已绑定过数据
                        model.addData(datas);
                    } else {
                        // 未绑定过数据
                        this.list.setModel(new sap.extension.model.JSONModel({ rows: datas }));
                    }
                    this.list.setBusy(false);
                }
                /** 显示业务对象属性信息 */
                showBusinessObjectProperties(datas: initialfantasy.bo.BOPropertyInformation[]): void {
                    let template: any = this.aliasColumn.getTemplate();
                    if (template instanceof sap.m.Select) {
                        template.destroyItems();
                        for (let data of datas) {
                            if (data.editSize < 0) {
                                continue;
                            }
                            template.addItem(new sap.extension.m.SelectItem("", {
                                key: data.property,
                                text: ibas.strings.isEmpty(data.description) ? data.property : data.description,
                            }));
                        }
                        this.aliasColumn.setTemplate(template);
                    }
                }
                /** 显示结果 */
                showConditions(conditions: ibas.ICondition[]): void {
                    if (this.table.getVisible() === false) {
                        this.table.setVisible(true);
                        this.message.setVisible(false);
                    }
                    this.table.setModel(new sap.extension.model.JSONModel({ rows: conditions }));
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