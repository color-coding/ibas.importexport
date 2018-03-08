/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace initialfantasy {
    export namespace bo {

        /** 系统权限 */
        export interface IPrivilege extends ibas.IBOSimple {

            /** 角色标识 */
            roleCode: string;

            /** 平台标识 */
            platformId: string;

            /** 模块标识 */
            moduleId: string;

            /** 目标标识 */
            target: string;

            /** 是否可用 */
            activated: ibas.emYesNo;

            /** 权限类型 */
            authoriseValue: ibas.emAuthoriseType;

            /** 对象编号 */
            objectKey: number;

            /** 对象类型 */
            objectCode: string;

            /** 创建日期 */
            createDate: Date;

            /** 创建时间 */
            createTime: number;

            /** 修改日期 */
            updateDate: Date;

            /** 修改时间 */
            updateTime: number;

            /** 实例号（版本） */
            logInst: number;

            /** 服务系列 */
            series: number;

            /** 数据源 */
            dataSource: string;

            /** 创建用户 */
            createUserSign: number;

            /** 修改用户 */
            updateUserSign: number;

            /** 创建动作标识 */
            createActionId: string;

            /** 更新动作标识 */
            updateActionId: string;
        }
    }

}

