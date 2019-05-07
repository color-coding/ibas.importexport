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
            const PRINT_FRAME_TEMPLATE: string = "print_{0}";
            /**
             * 数据打印视图
             */
            export class DataPrintServiceView extends ibas.DialogView implements app.IDataPrintView {
                /** 打印 */
                printEvent: Function;
                /** 预览 */
                previewEvent: Function;
                /** 绘制视图 */
                draw(): any {
                    let that: this = this;
                    return this.dialog = new sap.m.Dialog("", {
                        title: this.title,
                        type: sap.m.DialogType.Standard,
                        state: sap.ui.core.ValueState.None,
                        horizontalScrolling: true,
                        verticalScrolling: true,
                        content: [
                        ],
                        buttons: [
                            new sap.m.Button("", {
                                text: ibas.i18n.prop("importexport_preview"),
                                type: sap.m.ButtonType.Transparent,
                                press: function (oControlEvent: sap.ui.base.Event): void {
                                    if (!ibas.objects.isNull(that.html)) {
                                        that.fireViewEvents(that.printEvent, ibas.strings.format(PRINT_FRAME_TEMPLATE, that.html.getId()));
                                    } else if (!ibas.objects.isNull(that.select)) {
                                        let item: sap.ui.core.Item = that.select.getSelectedItem();
                                        if (ibas.objects.isNull(item)) {
                                            return;
                                        }
                                        that.fireViewEvents(that.previewEvent, (<any>item.getModel()).getData());
                                        if (oControlEvent.getSource() instanceof sap.m.Button) {
                                            let button: sap.m.Button = <sap.m.Button>oControlEvent.getSource();
                                            button.setText(ibas.i18n.prop("importexport_print"));
                                        }
                                    }
                                }
                            }),
                            new sap.m.Button("", {
                                text: ibas.i18n.prop("shell_exit"),
                                type: sap.m.ButtonType.Transparent,
                                press: function (): void {
                                    that.fireViewEvents(that.closeEvent);
                                }
                            }),
                        ],
                    });
                }
                private dialog: sap.m.Dialog;
                private select: sap.m.Select;
                private html: sap.ui.core.HTML;

                /** 显示内容 */
                showContent(content: Blob, width: string, height: string): void {
                    this.select = null;
                    this.dialog.destroyContent();
                    this.html = new sap.ui.core.HTML("", {});
                    this.dialog.addContent(this.html);
                    let url: string = URL.createObjectURL(content);
                    let iframe: ibas.StringBuilder = new ibas.StringBuilder();
                    iframe.append("<iframe");
                    iframe.append(" id=\"");
                    iframe.append(ibas.strings.format(PRINT_FRAME_TEMPLATE, this.html.getId()));
                    iframe.append("\"");
                    iframe.append(" width=\"");
                    iframe.append(ibas.strings.isEmpty(width) ? window.innerWidth * 0.8 : width);
                    iframe.append("\"");
                    iframe.append(" height=\"");
                    iframe.append(window.innerHeight * 0.7);
                    iframe.append("\"");
                    iframe.append(" src=\"");
                    iframe.append(url);
                    iframe.append("\"");
                    iframe.append(" frameborder=\"no\"");
                    iframe.append(" border=\"0\"");
                    // iframe.append(" scrolling=\"no\"");
                    iframe.append(">");
                    iframe.append("</iframe>");
                    this.html.setContent(iframe.toString());
                }
                /** 显示数据导出者 */
                showExporters(exporters: bo.IDataExporter[]): void {
                    this.html = null;
                    this.select = new sap.m.Select("", {
                        width: "100%",
                    });
                    for (let item of exporters) {
                        let sItem: sap.ui.core.Item = new sap.ui.core.Item("", {
                            key: item.name,
                            text: ibas.strings.isEmpty(item.description) ? item.name : item.description.substring(
                                item.description.indexOf("[") + 1, item.description.lastIndexOf("]")
                            ),
                        });
                        sItem.setModel(new sap.ui.model.json.JSONModel(item));
                        this.select.addItem(sItem);
                    }
                    this.dialog.addContent(
                        new sap.m.VBox("", {
                            items: [
                                new sap.m.Title("", {
                                    width: "100%",
                                    text: ibas.i18n.prop("importexport_please_template"),
                                }),
                                this.select,
                            ]
                        })
                    );
                }
            }
        }
    }
}