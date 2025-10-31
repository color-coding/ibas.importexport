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
            const DPI: { x: number, y: number } = function (): { x: number, y: number } {
                let dpi: { x: number, y: number } = { x: NaN, y: NaN };
                if ((<any>window.screen).deviceXDPI) {
                    dpi.x = (<any>window.screen).deviceXDPI;
                    dpi.y = (<any>window.screen).deviceYDPI;
                } else {
                    let tmpNode: HTMLElement = document.createElement("DIV");
                    tmpNode.style.cssText = "width:1in;height:1in;position:absolute;left:0px;top:0px;z-index:99;visibility:hidden";
                    document.body.appendChild(tmpNode);
                    dpi.x = tmpNode.offsetWidth;
                    dpi.y = tmpNode.offsetHeight;
                    tmpNode.parentNode.removeChild(tmpNode);
                }
                return dpi;
            }();
            function revisePx(value: number | string, dpi: number = 72): string {
                if (typeof value === "string" && value.endsWith("px")) {
                    let nValue: number = ibas.numbers.valueOf(value.substring(0, value.length - 2));
                    return ibas.strings.format("{0}px", Math.ceil(nValue * DPI.x / dpi));
                } else if (typeof value === "string" || typeof value === "number") {
                    return ibas.strings.format("{0}px", Math.ceil(ibas.numbers.valueOf(value) * DPI.x / dpi));
                }
                return value;
            }
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
                        horizontalScrolling: false,
                        verticalScrolling: false,
                        content: [
                        ],
                        buttons: [
                            new sap.m.Button("", {
                                text: ibas.i18n.prop("importexport_preview"),
                                type: sap.m.ButtonType.Transparent,
                                press: function (oControlEvent: sap.ui.base.Event): void {
                                    if (that.printing instanceof Function) {
                                        that.printing();
                                    } else if (!ibas.objects.isNull(that.select)) {
                                        let item: sap.ui.core.Item = that.select.getSelectedItem();
                                        if (ibas.objects.isNull(item)) {
                                            return;
                                        }
                                        that.fireViewEvents(that.previewEvent, item.getBindingContext().getObject());
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
                    }).addStyleClass("sapUiNoContentPadding");
                }
                private dialog: sap.m.Dialog;
                private select: sap.m.Select;
                private printing: Function;

                /** 显示内容 */
                showContent(content: Blob, contentType: string, width: string, height: string): void {
                    this.select = null;
                    this.dialog.destroyContent();
                    if (!ibas.strings.isEmpty(width)) {
                        this.dialog.setContentWidth(revisePx(width));
                    }
                    if (!ibas.strings.isEmpty(height)) {
                        this.dialog.setContentHeight(revisePx(height));
                    }
                    if (ibas.strings.equalsIgnoreCase("text/html", contentType)) {
                        let printArea: any = this.createHTML(content);
                        this.dialog.addContent(printArea);
                        let that: this = this;
                        this.printing = function (): void {
                            that.fireViewEvents(that.printEvent, printArea.getFrameId());
                        };
                    } else if (ibas.strings.equalsIgnoreCase("application/pdf", contentType)) {
                        this.dialog.setContentHeight("80%");
                        this.dialog.setContentWidth("80%");
                        let printArea: any = this.createPDF(content);
                        this.dialog.addContent(printArea);
                        let that: this = this;
                        this.printing = function (): void {
                            that.fireViewEvents(that.printEvent, printArea.getFrameId());
                        };
                    } else {
                        this.dialog.addContent(new sap.m.IllustratedMessage("", {
                            illustrationType: sap.m.IllustratedMessageType.UnableToLoad,
                        }));
                    }
                }

                private createHTML(content: Blob): sap.ui.core.Control {
                    return new sap.extension.core.FrameHTML("", {
                        frameWidth: "100%",
                        frameHeight: "100%",
                        frameSrc: content,
                        afterRendering(this: sap.extension.core.FrameHTML): void {
                            let frameHTML: HTMLElement = document.getElementById(this.getFrameId());
                            if (frameHTML instanceof HTMLIFrameElement) {
                                frameHTML.onload = function (this: HTMLIFrameElement): void {
                                    // 调整大小，以适应当前终端
                                    let document: HTMLDocument = this.contentDocument;
                                    let dpi: number;
                                    let element: Element = document.querySelector("meta[name=page_dpi]");
                                    if (ibas.objects.instanceOf(element, HTMLMetaElement)) {
                                        dpi = ibas.numbers.valueOf((<HTMLMetaElement>element).content);
                                    }
                                    let elements: HTMLCollectionOf<Element> = document.getElementsByTagName("*");
                                    for (let item of elements) {
                                        if (ibas.objects.instanceOf(item, HTMLElement)) {
                                            let element: HTMLElement = <HTMLElement>item;
                                            if (!element.style) {
                                                continue;
                                            }
                                            for (let item of element.style) {
                                                if (ibas.strings.isEmpty(item)) {
                                                    continue;
                                                }
                                                if (ibas.strings.isWith(item, "border-", undefined)) {
                                                    continue;
                                                }
                                                let value: any = element.style[item];
                                                if (typeof value === "string" && value.endsWith("px")) {
                                                    element.style[item] = revisePx(value, dpi);
                                                }
                                            }
                                        }
                                    }
                                    let value: any = this.width;
                                    if (typeof value === "string" && value.endsWith("px")) {
                                        this.width = revisePx(value, dpi);
                                    }
                                };
                            }
                        }
                    });
                }
                private createPDF(content: Blob): sap.ui.core.Control {
                    let that: this = this;
                    return new sap.extension.core.FrameHTML("", {
                        frameWidth: "100%",
                        frameHeight: "100%",
                        frameSrc: content,
                        afterRendering(this: sap.extension.core.FrameHTML): void {
                            /* 跨域问题
                            let frameHTML: any = document.getElementById(this.getFrameId());
                            if (frameHTML?.contentWindow) {
                                frameHTML?.contentWindow.addEventListener("afterprint", () => {
                                    that.fireViewEvents(that.printEvent, frameHTML.id);
                                });
                            }
                            */
                        }
                    });
                }
                /** 显示数据导出者 */
                showExporters(exporters: bo.IDataExporter[]): void {
                    delete (this.printing);
                    this.dialog.destroyContent();
                    this.select = new sap.m.Select("", {
                        items: {
                            path: "/",
                            template: new sap.ui.core.Item("", {
                                key: {
                                    path: "template",
                                    type: new sap.extension.data.Alphanumeric(),
                                },
                                text: {
                                    parts: [
                                        {
                                            path: "name",
                                            type: new sap.extension.data.Alphanumeric(),
                                        },
                                        {
                                            path: "description",
                                            type: new sap.extension.data.Alphanumeric(),
                                        }
                                    ],
                                    formatter(name: string, description: string): string {
                                        return ibas.strings.isEmpty(description) ? name : description.substring(
                                            description.indexOf("[") + 1, description.lastIndexOf("]"));
                                    }
                                },
                            })
                        }
                    });
                    this.select.setModel(new sap.ui.model.json.JSONModel(exporters));
                    this.dialog.addContent(
                        new sap.ui.layout.form.SimpleForm("", {
                            editable: false,
                            content: [
                                new sap.m.Label("", {
                                    width: "100%",
                                    text: ibas.i18n.prop("importexport_please_template"),
                                }),
                                this.select,
                            ]
                        })
                    );
                }
                /** 显示导出日志 */
                showPrintRecords(records: bo.ExportRecord[]): void {
                    let form: any = this.dialog.getContent()[0];
                    if (form instanceof sap.ui.layout.form.SimpleForm) {
                        form.addContent(new sap.m.Label("", {}));
                        form.addContent(new sap.m.Text("", {
                            textAlign: sap.ui.core.TextAlign.End,
                            text: ibas.i18n.prop("importexport_printed_count", records.length),
                        }));
                    }
                }
            }
        }
    }
}