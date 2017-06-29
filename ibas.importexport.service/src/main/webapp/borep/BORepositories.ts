/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import * as ibas from "ibas/index";
import * as bo from "./bo/index";
import { IBORepositoryImportExport, SchemaMethodsCaller, BO_REPOSITORY_IMPORTEXPORT } from "../api/index";
import { DataConverter4ie } from "./DataConverters";

/** 数据导入&导出 业务仓库 */
export class BORepositoryImportExport extends ibas.BORepositoryApplication implements IBORepositoryImportExport {

    /** 创建此模块的后端与前端数据的转换者 */
    protected createConverter(): ibas.IDataConverter {
        return new DataConverter4ie();
    }
    /** 获取导入方法地址 */
    getImportUrl(): string {
        let url: string = this.address;
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        if (url.endsWith("/data/")) {
            url = url.substring(0, url.lastIndexOf("/data/") + 1);
        }
        let token: string = this.token;
        if (ibas.objects.isNull(token)) {
            token = "";
        }
        return ibas.strings.format("{0}file/import?token={1}", url, token);
    }
    /** 获取导入方法地址 */
    parseImportResult(data: any): ibas.IOperationResult<string> {
        let jData: Object = data;
        if (typeof data === "string") {
            jData = JSON.parse(data);
        }
        return this.createConverter().parsing(jData, "import");
    }
    /** 创建远程仓库 */
    protected createRemoteRepository(): ibas.IRemoteRepository {
        let boRepository: ibas.BORepositoryAjax = new ibas.BORepositoryAjax();
        boRepository.address = this.address;
        boRepository.token = this.token;
        boRepository.converter = this.createConverter();
        return boRepository;
    }
    /**
     * 获取业务对象架构
     * @param caller 调用者
     */
    schema(caller: SchemaMethodsCaller<string>): void {
        let remoteRepository: ibas.IRemoteRepository = this.createRemoteRepository();
        if (ibas.objects.isNull(remoteRepository)) {
            throw new Error(ibas.i18n.prop("sys_invalid_parameter", "remoteRepository"));
        }
        let method: string =
            ibas.strings.format("schema?boCode={0}&type={1}&token={2}",
                caller.boCode, caller.type, this.token);
        remoteRepository.callRemoteMethod(method, undefined, caller);
    }
    /**
     * 查询 数据导出模板
     * @param fetcher 查询者
     */
    fetchDataExportTemplate(fetcher: ibas.FetchCaller<bo.DataExportTemplate>): void {
        super.fetch(bo.DataExportTemplate.name, fetcher);
    }
    /**
     * 保存 数据导出模板
     * @param saver 保存者
     */
    saveDataExportTemplate(saver: ibas.SaveCaller<bo.DataExportTemplate>): void {
        super.save(bo.DataExportTemplate.name, saver);
    }

}

// 注册业务对象仓库到工厂
ibas.boFactory.register(BO_REPOSITORY_IMPORTEXPORT, BORepositoryImportExport);
