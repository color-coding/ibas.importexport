/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace initialfantasy {
    export namespace bo {

        /** 业务对象检索条件 */
        export interface IBOCriteria extends ibas.IBOSimple {

            /** 应用标识 */
            applicationId: string;

            /** 检索名称 */
            name: string;

            /** 指派类型 */
            assignedType: emAssignedType;

            /** 指派目标 */
            assigned: string;

            /** 激活的 */
            activated: ibas.emYesNo;

            /** 查询数据 */
            data: string;

            /** 顺序 */
            order: number;

            /** 编号 */
            objectKey: number;

            /** 类型 */
            objectCode: string;

            /** 实例号（版本） */
            logInst: number;

            /** 编号系列 */
            series: number;

            /** 数据源 */
            dataSource: string;

            /** 创建日期 */
            createDate: Date;

            /** 创建时间 */
            createTime: number;

            /** 修改日期 */
            updateDate: Date;

            /** 修改时间 */
            updateTime: number;

            /** 创建用户 */
            createUserSign: number;

            /** 修改用户 */
            updateUserSign: number;

            /** 创建动作标识 */
            createActionId: string;

            /** 更新动作标识 */
            updateActionId: string;

            /** 数据所属组织 */
            organization: string;
        }
    }

}

