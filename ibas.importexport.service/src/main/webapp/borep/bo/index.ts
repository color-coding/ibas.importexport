/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

// 模块索引文件，此文件集中导出类
export * from "../../api/Datas";
export * from "./DataExportTemplate";

// 注册业务对象到工厂
import * as ibas from "ibas/index";
import { DataExportTemplate } from "./DataExportTemplate";
ibas.boFactory.register(DataExportTemplate.BUSINESS_OBJECT_CODE, DataExportTemplate);
// 注册其他
ibas.boFactory.register(ibas.FileData);
