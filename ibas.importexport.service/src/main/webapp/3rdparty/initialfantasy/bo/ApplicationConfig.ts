/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace initialfantasy {
    export namespace bo {
        /** 应用程序配置 */
        export interface IApplicationConfig extends ibas.IBOSimple {
            /** 配置组 */
            configGroup: string;
            /** 配置项 */
            configKey: string;
            /** 配置说明 */
            configDescription: string;
            /** 配置值 */
            configValue: string;
            /** 对象键值 */
            objectKey: number;
            /** 对象类型 */
            objectCode: string;
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
            /** 创建动作标识 */
            createActionId: string;
            /** 更新动作标识 */
            updateActionId: string;
            /** 实例号（版本） */
            logInst: number;
            /** 创建用户 */
            createUserSign: number;
            /** 修改用户 */
            updateUserSign: number;

        }


    }
}
