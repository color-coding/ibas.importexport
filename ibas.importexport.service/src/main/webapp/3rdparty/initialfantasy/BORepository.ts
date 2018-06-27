/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace initialfantasy {
    export namespace bo {

        /** InitialFantasy 业务仓库 */
        export interface IBORepositoryInitialFantasy extends ibas.IBORepositoryApplication {

            /**
             * 上传文件
             * @param caller 调用者
             */
            upload(caller: ibas.IUploadFileCaller<ibas.FileData>): void;
            /**
             * 下载文件
             * @param caller 调用者
             */
            download(caller: ibas.IDownloadFileCaller<Blob>): void;
            /**
             * 查询 应用程序配置
             * @param fetcher 查询者
             */
            fetchApplicationConfig(fetcher: ibas.IFetchCaller<bo.IApplicationConfig>): void;
            /**
             * 保存 应用程序配置
             * @param saver 保存者
             */
            saveApplicationConfig(saver: ibas.ISaveCaller<bo.IApplicationConfig>): void;
            /**
             * 查询 应用程序元素
             * @param fetcher 查询者
             */
            fetchApplicationElement(fetcher: ibas.IFetchCaller<bo.IApplicationElement>): void;
            /**
             * 保存 应用程序元素
             * @param saver 保存者
             */
            saveApplicationElement(saver: ibas.ISaveCaller<bo.IApplicationElement>): void;

            /**
             * 查询 应用程序模块
             * @param fetcher 查询者
             */
            fetchApplicationModule(fetcher: ibas.IFetchCaller<bo.IApplicationModule>): void;
            /**
             * 保存 应用程序模块
             * @param saver 保存者
             */
            saveApplicationModule(saver: ibas.ISaveCaller<bo.IApplicationModule>): void;

            /**
             * 查询 应用程序平台
             * @param fetcher 查询者
             */
            fetchApplicationPlatform(fetcher: ibas.IFetchCaller<bo.IApplicationPlatform>): void;
            /**
             * 保存 应用程序平台
             * @param saver 保存者
             */
            saveApplicationPlatform(saver: ibas.ISaveCaller<bo.IApplicationPlatform>): void;

            /**
             * 查询 业务对象检索条件
             * @param fetcher 查询者
             */
            fetchBOCriteria(fetcher: ibas.IFetchCaller<bo.IBOCriteria>): void;
            /**
             * 保存 业务对象检索条件
             * @param saver 保存者
             */
            saveBOCriteria(saver: ibas.ISaveCaller<bo.IBOCriteria>): void;

            /**
             * 查询 业务对象筛选
             * @param fetcher 查询者
             */
            fetchBOFiltering(fetcher: ibas.IFetchCaller<bo.IBOFiltering>): void;
            /**
             * 保存 业务对象筛选
             * @param saver 保存者
             */
            saveBOFiltering(saver: ibas.ISaveCaller<bo.IBOFiltering>): void;

            /**
             * 查询 组织
             * @param fetcher 查询者
             */
            fetchOrganization(fetcher: ibas.IFetchCaller<bo.IOrganization>): void;
            /**
             * 保存 组织
             * @param saver 保存者
             */
            saveOrganization(saver: ibas.ISaveCaller<bo.IOrganization>): void;

            /**
             * 查询 系统权限
             * @param fetcher 查询者
             */
            fetchPrivilege(fetcher: ibas.IFetchCaller<bo.IPrivilege>): void;
            /**
             * 保存 系统权限
             * @param saver 保存者
             */
            savePrivilege(saver: ibas.ISaveCaller<bo.IPrivilege>): void;

            /**
             * 查询 用户
             * @param fetcher 查询者
             */
            fetchUser(fetcher: ibas.IFetchCaller<bo.IUser>): void;
            /**
             * 保存 用户
             * @param saver 保存者
             */
            saveUser(saver: ibas.ISaveCaller<bo.IUser>): void;

            /**
             * 查询 业务对象信息
             * @param fetcher 查询者
             */
            fetchBOInformation(fetcher: ibas.IFetchCaller<bo.IBOInformation>): void;
            /**
             * 保存 业务对象信息
             * @param saver 保存者
             */
            saveBOInformation(saver: ibas.ISaveCaller<bo.IBOInformation>): void;

            /**
             * 查询 项目
             * @param fetcher 查询者
             */
            fetchProject(fetcher: ibas.IFetchCaller<bo.IProject>): void;
            /**
             * 保存 项目
             * @param saver 保存者
             */
            saveProject(saver: ibas.ISaveCaller<bo.IProject>): void;
        }
    }
}
