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
             * 视图-数据导入
             */
            export class DataImportView extends ibas.View implements app.IDataImportView {
                /** 导入 */
                importEvent: Function;
                /** 选择文件 */
                addFilesEvent: Function;
                /** 移除文件 */
                removeFilesEvent: Function;
                /** 绘制视图 */
                draw(): any {
                    let that: this = this;
                    this.listMethod = new sap.extension.m.List("", {
                        headerToolbar: new sap.m.Toolbar("", {
                            content: [
                                new sap.m.Text("", {
                                    text: ibas.i18n.prop("importexport_update_exists_data"),
                                }),
                                new sap.m.ToolbarSpacer(),
                                new sap.m.Button("", {
                                    icon: "sap-icon://drill-down",
                                    press(): void {
                                        if (this.getIcon() === "sap-icon://drill-down") {
                                            that.listTransaction.setVisible(true);
                                            that.listApproval.setVisible(true);
                                            this.setIcon("sap-icon://drill-up");
                                        } else {
                                            that.listTransaction.setVisible(false);
                                            that.listApproval.setVisible(false);
                                            this.setIcon("sap-icon://drill-down");
                                        }
                                    }
                                })
                            ]
                        }),
                        mode: sap.m.ListMode.SingleSelectLeft,
                        items: [
                            new sap.m.StandardListItem("", {
                                selected: true,
                                counter: bo.emDataUpdateMethod.SKIP,
                                icon: "sap-icon://sys-next-page",
                                title: ibas.enums.describe(bo.emDataUpdateMethod, bo.emDataUpdateMethod.SKIP),
                            }),
                            new sap.m.StandardListItem("", {
                                counter: bo.emDataUpdateMethod.REPLACE,
                                icon: "sap-icon://documents",
                                title: ibas.enums.describe(bo.emDataUpdateMethod, bo.emDataUpdateMethod.REPLACE),

                            }),
                            new sap.m.StandardListItem("", {
                                counter: bo.emDataUpdateMethod.MODIFY,
                                icon: "sap-icon://request",
                                title: ibas.enums.describe(bo.emDataUpdateMethod, bo.emDataUpdateMethod.MODIFY),
                            }),
                        ],
                    });
                    this.listTransaction = new sap.extension.m.List("", {
                        visible: false,
                        headerToolbar: new sap.m.Toolbar("", {
                            content: [
                                new sap.m.Text("", {
                                    text: ibas.i18n.prop("importexport_update_single_transaction"),
                                }),
                                new sap.m.ToolbarSpacer(),
                            ]
                        }),
                        mode: sap.m.ListMode.SingleSelectLeft,
                        items: [
                            new sap.m.StandardListItem("", {
                                counter: ibas.emYesNo.NO,
                                icon: "sap-icon://message-error",
                                title: ibas.enums.describe(ibas.emYesNo, ibas.emYesNo.NO),
                            }),
                            new sap.m.StandardListItem("", {
                                selected: true,
                                counter: ibas.emYesNo.YES,
                                icon: "sap-icon://message-success",
                                title: ibas.enums.describe(ibas.emYesNo, ibas.emYesNo.YES),
                            }),
                        ],
                    });
                    this.listApproval = new sap.extension.m.List("", {
                        visible: false,
                        headerToolbar: new sap.m.Toolbar("", {
                            content: [
                                new sap.m.Text("", {
                                    text: ibas.i18n.prop("importexport_update_skip_approval"),
                                }),
                                new sap.m.ToolbarSpacer(),
                            ]
                        }),
                        mode: sap.m.ListMode.SingleSelectLeft,
                        items: [
                            new sap.m.StandardListItem("", {
                                selected: true,
                                counter: ibas.emYesNo.NO,
                                icon: "sap-icon://message-error",
                                title: ibas.enums.describe(ibas.emYesNo, ibas.emYesNo.NO),
                            }),
                            new sap.m.StandardListItem("", {
                                counter: ibas.emYesNo.YES,
                                icon: "sap-icon://message-success",
                                title: ibas.enums.describe(ibas.emYesNo, ibas.emYesNo.YES),
                            }),
                        ],
                    });
                    this.listFiles = new sap.extension.m.List("", {
                        headerToolbar: new sap.m.Toolbar("", {
                            content: [
                                new sap.m.Text("", {
                                    text: ibas.i18n.prop("importexport_import_files"),
                                }),
                                new sap.m.ToolbarSpacer(),
                                new sap.m.Button("", {
                                    icon: "sap-icon://sys-cancel",
                                    type: sap.m.ButtonType.Reject,
                                    press(): void {
                                        that.fireViewEvents(that.removeFilesEvent);
                                    }
                                }),
                                new sap.m.ToolbarSeparator(),
                                new sap.m.Button("", {
                                    icon: "sap-icon://add-document",
                                    type: sap.m.ButtonType.Accept,
                                    press(): void {
                                        that.fireViewEvents(that.addFilesEvent);
                                    }
                                }),
                            ]
                        }),
                        mode: sap.m.ListMode.None,
                        items: {
                            path: "/",
                            template: new sap.extension.m.CustomListItem("", {
                                type: sap.m.ListType.Detail,
                                detailIcon: "sap-icon://sys-cancel",
                                detailPress(this: sap.m.ListItemBase): void {
                                    let data: any = this.getBindingContext().getObject();
                                    if (data instanceof app.FileItem) {
                                        that.fireViewEvents(that.removeFilesEvent, data);
                                    }
                                },
                                content: [
                                    new sap.m.HBox("", {
                                        width: "100%",
                                        items: [
                                            new sap.m.Avatar("", {
                                                displayShape: sap.m.AvatarShape.Square,
                                                // backgroundColor: sap.m.AvatarColor.Random,
                                                displaySize: sap.m.AvatarSize.S,
                                                src: {
                                                    path: "file/type",
                                                    type: new sap.extension.data.Alphanumeric(),
                                                    formatter(data: string): string {
                                                        if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" === data) {
                                                            return "sap-icon://excel-attachment";
                                                        } else if ("application/json" === data) {
                                                            return "sap-icon://attachment-html";
                                                        }
                                                        return "sap-icon://document";
                                                    }
                                                },
                                            }),
                                            new sap.m.VBox("", {
                                                renderType: sap.m.FlexRendertype.Div,
                                                justifyContent: sap.m.FlexJustifyContent.Start,
                                                items: [
                                                    new sap.m.Title("", {
                                                        wrapping: true,
                                                        titleStyle: sap.ui.core.TitleLevel.H5,
                                                        text: {
                                                            path: "file/name",
                                                            type: new sap.extension.data.Alphanumeric(),
                                                        },
                                                    }),
                                                    new sap.m.Title("", {
                                                        wrapping: true,
                                                        titleStyle: sap.ui.core.TitleLevel.H6,
                                                        text: {
                                                            path: "file/lastModifiedDate",
                                                            type: new sap.extension.data.DateTime(),
                                                        },
                                                    }),
                                                ]
                                            }).addStyleClass("sapUiTinyMarginBegin"),
                                        ]
                                    }).addStyleClass("sapUiTinyMargin"),
                                ],
                                highlight: {
                                    path: "status",
                                    formatter(data: string): string {
                                        switch (data) {
                                            case "pending":
                                                return sap.ui.core.MessageType.Information;
                                            case "processing":
                                                return sap.ui.core.MessageType.Warning;
                                            case "completed":
                                                return sap.ui.core.MessageType.Success;
                                            case "failed":
                                                return sap.ui.core.MessageType.Error;
                                        }
                                        return sap.ui.core.MessageType.None;
                                    },
                                }
                            }),
                            sorter: [
                                new sap.ui.model.Sorter("order", false)
                            ]
                        },
                        dragDropConfig: [
                            new sap.ui.core.dnd.DragDropInfo("", {
                                sourceAggregation: "items",
                                targetAggregation: "items",
                                dropPosition: sap.ui.core.dnd.DropPosition.Between,
                                dropLayout: sap.ui.core.dnd.DropLayout.Vertical,
                                drop(event: sap.ui.base.Event): void {
                                    let dragged: any = event.getParameter("draggedControl");
                                    let dropped: any = event.getParameter("droppedControl");
                                    let dropPosition: string = event.getParameter("dropPosition");
                                    let table: any = (<any>event.getSource())?.getDropTarget();
                                    if (table instanceof sap.m.List) {
                                        let index: number = 1;
                                        let step: number = 1;
                                        let draggedData: app.FileItem = dragged.getBindingContext().getObject();
                                        let droppedData: app.FileItem = dropped.getBindingContext().getObject();
                                        let rowData: app.FileItem;
                                        for (let row of table.getItems()) {
                                            rowData = row.getBindingContext()?.getObject();
                                            if (ibas.objects.isNull(rowData)) {
                                                continue;
                                            }
                                            if (dragged === row) {
                                                continue;
                                            } else if (dropped === row) {
                                                if (dropPosition === "Before") {
                                                    draggedData.order = index * step;
                                                    index++;
                                                    droppedData.order = index * step;
                                                    index++;
                                                } else if (dropPosition === "After") {
                                                    droppedData.order = index * step;
                                                    index++;
                                                    draggedData.order = index * step;
                                                    index++;
                                                }
                                            } else {
                                                rowData.order = index * step;
                                                index++;
                                            }
                                        }
                                        if (index > 1) {
                                            // table.getModel().refresh(true);
                                            // 刷新不好使，需要重新绑定
                                            let model: any = table.getModel();
                                            table.setModel(undefined);
                                            table.setModel(model);
                                        }
                                    }
                                },
                            })
                        ]
                    });
                    return new sap.m.SplitContainer("", {
                        masterPages: [
                            new sap.m.Page("", {
                                showHeader: true,
                                customHeader: new sap.m.Toolbar("", {
                                    content: [
                                        new sap.m.Title("", {
                                            text: ibas.i18n.prop("importexport_import_content")
                                        }),
                                        new sap.m.ToolbarSpacer(),
                                        new sap.m.Button("", {
                                            icon: "sap-icon://toaster-up",
                                            text: ibas.i18n.prop("importexport_import_data"),
                                            type: sap.m.ButtonType.Emphasized,
                                            press(): void {
                                                let item: any = that.listMethod.getSelectedItem();
                                                if (item instanceof sap.m.StandardListItem) {
                                                    that.fireViewEvents(that.importEvent, item.getCounter(),
                                                        that.listTransaction.getSelectedItem().getCounter(),
                                                        that.listApproval.getSelectedItem().getCounter()
                                                    );
                                                }
                                            }
                                        }),
                                    ]
                                }),
                                content: [
                                    this.listMethod,
                                    this.listTransaction,
                                    this.listApproval,
                                    this.listFiles,
                                ]
                            })
                        ],
                        detailPages: [
                            this.pageResult = new sap.m.Page("", {
                                showHeader: true,
                                customHeader: new sap.m.Toolbar("", {
                                    content: [
                                        new sap.m.Button("", {
                                            type: sap.m.ButtonType.Transparent,
                                            icon: "sap-icon://navigation-right-arrow",
                                            press: function (this: sap.m.Button): void {
                                                let page: any = this.getParent().getParent();
                                                if (page instanceof sap.m.Page) {
                                                    if (this.getIcon() === "sap-icon://navigation-right-arrow") {
                                                        for (let vItem of page.getContent()) {
                                                            if (vItem instanceof sap.m.Panel) {
                                                                vItem.setExpanded(true);
                                                            }
                                                        }
                                                        this.setIcon("sap-icon://navigation-down-arrow");
                                                    } else {
                                                        for (let vItem of page.getContent()) {
                                                            if (vItem instanceof sap.m.Panel) {
                                                                vItem.setExpanded(false);
                                                            }
                                                        }
                                                        this.setIcon("sap-icon://navigation-right-arrow");
                                                    }
                                                }
                                            }
                                        }),
                                        new sap.m.ToolbarSeparator(),
                                        new sap.m.Title("", {
                                            text: ibas.i18n.prop("importexport_import_result")
                                        }),
                                        new sap.m.ToolbarSpacer(),
                                        new sap.m.Button("", {
                                            type: sap.m.ButtonType.Transparent,
                                            icon: "sap-icon://eraser",
                                            press(this: sap.m.Button): void {
                                                that.showResults(undefined, []);
                                            }
                                        }),
                                    ]
                                }),
                                enableScrolling: true,
                                content: [
                                ]
                            })
                        ],
                    });
                }

                private listMethod: sap.m.List;
                private listApproval: sap.m.List;
                private listTransaction: sap.m.List;
                private listFiles: sap.m.List;
                private pageResult: sap.m.Page;

                /** 显示文件 */
                showFiles(datas: app.FileItem[]): void {
                    this.listFiles.setModel(new sap.extension.model.JSONModel(datas));
                    if (datas.length === 0) {
                        this.pageResult.destroyContent();
                        this.pageResult.addContent(
                            new sap.m.IllustratedMessage("", {
                                illustrationType: sap.m.IllustratedMessageType.NoData
                            })
                        );
                    } else {
                        for (let pItem of ibas.arrays.create(this.pageResult.getContent())) {
                            let done: boolean = false;
                            let pData: any = (<sap.extension.model.JSONModel>pItem.getModel())?.getData();
                            for (let data of datas) {
                                if (pData === data) {
                                    done = true;
                                    break;
                                }
                            }
                            if (!done) {
                                this.pageResult.removeContent(pItem);
                            }
                        }
                        if (this.pageResult.getContent().length === 0) {
                            this.pageResult.addContent(
                                new sap.m.IllustratedMessage("", {
                                    illustrationType: sap.m.IllustratedMessageType.NoData
                                })
                            );
                        }
                    }
                }
                /** 显示结果 */
                showResults(file: app.FileItem, results: string[]): void {
                    if (ibas.objects.isNull(file)) {
                        this.pageResult.destroyContent();
                        this.pageResult.addContent(
                            new sap.m.IllustratedMessage("", {
                                illustrationType: sap.m.IllustratedMessageType.NoData
                            })
                        );
                    } else {
                        if (this.pageResult.getContent()[0] instanceof sap.m.IllustratedMessage) {
                            this.pageResult.removeContent(0);
                        }
                        let nResults: any[] = new ibas.ArrayList<any>();
                        for (let i: number = 0; i < results.length; i++) {
                            nResults.push({
                                index: ibas.strings.format("{0}/{1}", i + 1, results.length),
                                message: results[i],
                                error: !ibas.strings.isWith(results[i], "{", "}")
                            });
                        }
                        let that: this = this;
                        this.pageResult.addContent(
                            new sap.m.Panel("", {
                                expanded: false,
                                expandable: true,
                                headerToolbar: new sap.m.Toolbar("", {
                                    content: [
                                        new sap.m.Text("", {
                                            text: "{/order}.",
                                        }),
                                        new sap.m.Text("", {
                                            text: {
                                                path: "/file/name",
                                                type: new sap.extension.data.Alphanumeric(),
                                            },
                                        }),
                                        new sap.m.ToolbarSpacer(),
                                        new sap.m.GenericTag("", {
                                            design: sap.m.GenericTagDesign.StatusIconHidden,
                                            text: {
                                                parts: [
                                                    {
                                                        path: "/identified",
                                                        type: new sap.extension.data.Numeric(),
                                                    }, {
                                                        path: "/saved",
                                                        type: new sap.extension.data.Numeric(),
                                                    }
                                                ],
                                                formatter(identified: number, saved: number): string {
                                                    if (identified > saved) {
                                                        return ibas.i18n.prop("importexport_import_data_information_repetition", identified, saved, identified - saved);
                                                    }
                                                    return ibas.i18n.prop("importexport_import_data_information", identified, saved);
                                                }
                                            },
                                            status: {
                                                path: "/error",
                                                formatter(error: any): sap.ui.core.ValueState {
                                                    return error instanceof Error ? sap.ui.core.ValueState.Error : sap.ui.core.ValueState.Success;
                                                }
                                            },
                                        }),
                                    ],
                                }),
                                content: [
                                    new sap.extension.m.List("", {
                                        growing: true,
                                        growingScrollToLoad: true,
                                        mode: sap.m.ListMode.None,
                                        items: {
                                            path: "/",
                                            template: new sap.m.StandardListItem("", {
                                                type: {
                                                    path: "message",
                                                    formatter(data: any): sap.m.ListType {
                                                        if (ibas.strings.isWith(data, "{", "}")) {
                                                            return sap.m.ListType.Active;
                                                        }
                                                        return sap.m.ListType.Inactive;
                                                    }
                                                },
                                                title: {
                                                    path: "message",
                                                    formatter(data: any): string {
                                                        if (ibas.strings.isWith(data, "{", "}")) {
                                                            return ibas.businessobjects.describe(data);
                                                        }
                                                        return data;
                                                    }
                                                },
                                                highlight: {
                                                    path: "error",
                                                    formatter(data: any): sap.ui.core.ValueState {
                                                        if (data === true) {
                                                            return sap.ui.core.ValueState.Error;
                                                        }
                                                        return sap.ui.core.ValueState.Success;
                                                    }
                                                },
                                                info: {
                                                    path: "index",
                                                },
                                                press(this: sap.m.StandardListItem): void {
                                                    let data: any = this.getBindingContext().getObject();
                                                    if (!ibas.strings.isEmpty(data?.message)) {
                                                        let criteria: ibas.ICriteria = ibas.criterias.valueOf(data.message);
                                                        if (!ibas.strings.isEmpty(criteria?.businessObject)) {
                                                            let done: boolean = ibas.servicesManager.runLinkService({
                                                                boCode: criteria.businessObject,
                                                                linkValue: criteria
                                                            });
                                                            if (!done) {
                                                                that.application.viewShower.proceeding(
                                                                    that,
                                                                    ibas.emMessageType.WARNING,
                                                                    ibas.i18n.prop("importexport_not_found_link_service",
                                                                        ibas.businessobjects.describe(criteria.businessObject))
                                                                );
                                                            }
                                                        }
                                                    }
                                                }
                                            })
                                        },
                                    }).setModel(new sap.extension.model.JSONModel(nResults))
                                ]
                            }).setModel(new sap.extension.model.JSONModel(file))
                        );
                    }
                }
            }
        }
    }
}