/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace ui {
        export namespace m {
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
                    return this.dialog = new sap.extension.m.Dialog("", {
                        title: this.title,
                        type: sap.m.DialogType.Standard,
                        state: sap.ui.core.ValueState.None,
                        stretch: ibas.config.get(ibas.CONFIG_ITEM_PLANTFORM) === ibas.emPlantform.PHONE ? true : false,
                        horizontalScrolling: false,
                        verticalScrolling: false,
                        content: [
                        ],
                        buttons: [
                            new sap.m.Button("", {
                                text: ibas.i18n.prop("importexport_print"),
                                type: sap.m.ButtonType.Transparent,
                                visible: false,
                                press: function (oControlEvent: sap.ui.base.Event): void {
                                    if (!ibas.objects.isNull(that.html)) {
                                        that.fireViewEvents(that.printEvent, that.html.getFrameId());
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
                private html: sap.extension.core.FrameHTML;

                /** 显示内容 */
                showContent(content: Blob, width: string, height: string): void {
                    this.dialog.destroyContent();
                    if (!ibas.strings.isEmpty(width)) {
                        this.dialog.setContentWidth(revisePx(width));
                    }
                    if (!ibas.strings.isEmpty(height)) {
                        this.dialog.setContentHeight(revisePx(height));
                    }
                    this.dialog.addContent(this.html = new sap.extension.core.FrameHTML("", {
                        frameWidth: "100%",
                        frameHeight: "100%",
                        frameSrc: content,
                        afterRendering: () => {
                            let frameHTML: HTMLElement = document.getElementById(this.html.getFrameId());
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
                    }));
                    this.dialog.getButtons()[0].setVisible(true);
                }
                /** 显示数据导出者 */
                showExporters(exporters: bo.IDataExporter[]): void {
                    let that: this = this;
                    let list: any = new sap.extension.m.List("", {
                        inset: false,
                        growing: false,
                        mode: sap.m.ListMode.None,
                        backgroundDesign: sap.m.BackgroundDesign.Transparent,
                        headerToolbar: new sap.m.Toolbar("", {
                            content: [
                                new sap.m.Label("", {
                                    text: ibas.i18n.prop("importexport_please_template")
                                }),
                            ]
                        }),
                        showNoData: true,
                        items: {
                            path: "/rows",
                            template: new sap.m.ObjectListItem("", {
                                title: {
                                    path: "description",
                                    formatter(data: string): string {
                                        return data.substring(data.indexOf("[") + 1, data.lastIndexOf("]"));
                                    }
                                },
                                type: sap.m.ListType.Active,
                                press: function (oEvent: sap.ui.base.Event): void {
                                    that.fireViewEvents(that.previewEvent, this.getBindingContext().getObject());
                                },
                            })
                        }
                    });
                    list.setModel(new sap.extension.model.JSONModel({ rows: exporters }));
                    this.dialog.addContent(list);
                }
            }
        }
    }
}