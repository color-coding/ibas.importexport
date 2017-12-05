/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import {
    FetchCaller,
    SaveCaller,
    UploadFileCaller,
    DownloadFileCaller,
    FileData,
    IBORepositoryApplication
} from "ibas/index";
import * as bo from "./bo/index"

/** InitialFantasy 业务仓库 */
export interface IBORepositoryInitialFantasy extends IBORepositoryApplication {

    /**
     * 上传文件
     * @param caller 调用者
     */
    upload(caller: UploadFileCaller<FileData>);
    /**
     * 下载文件
     * @param caller 调用者
     */
    download(caller: DownloadFileCaller<Blob>);
    /**
     * 查询 应用程序功能
     * @param fetcher 查询者
     */
    fetchApplicationFunction(fetcher: FetchCaller<bo.IApplicationFunction>);
    /**
     * 保存 应用程序功能
     * @param saver 保存者
     */
    saveApplicationFunction(saver: SaveCaller<bo.IApplicationFunction>);

    /**
     * 查询 应用程序模块
     * @param fetcher 查询者
     */
    fetchApplicationModule(fetcher: FetchCaller<bo.IApplicationModule>);
    /**
     * 保存 应用程序模块
     * @param saver 保存者
     */
    saveApplicationModule(saver: SaveCaller<bo.IApplicationModule>);

    /**
     * 查询 应用程序平台
     * @param fetcher 查询者
     */
    fetchApplicationPlatform(fetcher: FetchCaller<bo.IApplicationPlatform>);
    /**
     * 保存 应用程序平台
     * @param saver 保存者
     */
    saveApplicationPlatform(saver: SaveCaller<bo.IApplicationPlatform>);

    /**
     * 查询 业务对象检索条件
     * @param fetcher 查询者
     */
    fetchBOCriteria(fetcher: FetchCaller<bo.IBOCriteria>);
    /**
     * 保存 业务对象检索条件
     * @param saver 保存者
     */
    saveBOCriteria(saver: SaveCaller<bo.IBOCriteria>);

    /**
     * 查询 业务对象筛选
     * @param fetcher 查询者
     */
    fetchBOFiltering(fetcher: FetchCaller<bo.IBOFiltering>);
    /**
     * 保存 业务对象筛选
     * @param saver 保存者
     */
    saveBOFiltering(saver: SaveCaller<bo.IBOFiltering>);

    /**
     * 查询 组织
     * @param fetcher 查询者
     */
    fetchOrganization(fetcher: FetchCaller<bo.IOrganization>);
    /**
     * 保存 组织
     * @param saver 保存者
     */
    saveOrganization(saver: SaveCaller<bo.IOrganization>);

    /**
     * 查询 系统权限
     * @param fetcher 查询者
     */
    fetchPrivilege(fetcher: FetchCaller<bo.IPrivilege>);
    /**
     * 保存 系统权限
     * @param saver 保存者
     */
    savePrivilege(saver: SaveCaller<bo.IPrivilege>);

    /**
     * 查询 用户
     * @param fetcher 查询者
     */
    fetchUser(fetcher: FetchCaller<bo.IUser>);
    /**
     * 保存 用户
     * @param saver 保存者
     */
    saveUser(saver: SaveCaller<bo.IUser>);

    /**
     * 查询 业务对象信息
     * @param fetcher 查询者
     */
    fetchBOInformation(fetcher: FetchCaller<bo.IBOInformation>);
    /**
     * 保存 业务对象信息
     * @param saver 保存者
     */
    saveBOInformation(saver: SaveCaller<bo.IBOInformation>);

    /**
     * 查询 项目
     * @param fetcher 查询者
     */
    fetchProject(fetcher: FetchCaller<bo.IProject>);
    /**
     * 保存 项目
     * @param saver 保存者
     */
    saveProject(saver: SaveCaller<bo.IProject>);

}
