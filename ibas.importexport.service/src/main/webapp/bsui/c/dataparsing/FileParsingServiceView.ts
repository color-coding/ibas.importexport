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
             * 文件解析服务视图
             */
            export class FileParsingServiceView extends ibas.View implements app.IFileParsingServiceView {
                parsingEvent: Function;
                /** 绘制视图 */
                draw(): any {
                    return null;
                }
                showFileDialog(extensions: string): void {
                    ibas.files.open((files) => {
                        this.fireViewEvents(this.parsingEvent, files[0]);
                    }, { accept: extensions, multiple: false });
                }
            }
        }
    }
}