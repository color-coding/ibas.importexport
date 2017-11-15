/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import {
    FetchCaller, IOperationResult, ICriteria, KeyText,
    SaveCaller, MethodCaller, DownloadFileCaller, UploadFileCaller
} from "ibas/index";
import * as bo from "./bo/index"

/** ImportExport 业务仓库 */
export interface IBORepositoryImportExport {

    /**
     * 获取业务对象架构
     * @param caller 调用者
     */
    schema(caller: SchemaMethodCaller<string>);
    /**
     * 导入
     * @param caller 调用者
     */
    import(caller: ImportFileCaller);
    /**
     * 数据导出调用者
     * @param caller 调用者
     */
    export(caller: DownloadFileCaller);
    /**
     * 查询 获取转换者名称
     * @param fetcher 查询者
     */
    fetchTransformer(fetcher: FetchCaller<KeyText>);
    /**
     * 查询 数据导出模板
     * @param fetcher 查询者
     */
    fetchDataExportTemplate(fetcher: FetchCaller<bo.IDataExportTemplate>);
    /**
     * 保存 数据导出模板
     * @param saver 保存者
     */
    saveDataExportTemplate(saver: SaveCaller<bo.IDataExportTemplate>);
}
/**
 * 业务对象架构相关调用者
 */
export interface SchemaMethodCaller<P> extends MethodCaller {
    /** 业务对象编码 */
    boCode: string;
    /** 结构类型 */
    type: string;
    /**
     * 调用完成
     * @param opRslt 结果
     */
    onCompleted(opRslt: IOperationResult<P>): void;
}
/**
 * 文件导入调用者
 */
export interface ImportFileCaller extends UploadFileCaller<string> {
}