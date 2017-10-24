/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

import {
    emYesNo,
    emDocumentStatus,
    emBOStatus,
    emApprovalStatus,
    IBusinessObject,
    IBusinessObjects,
    IBOMasterData,
    IBOMasterDataLine,
    IBODocument,
    IBODocumentLine,
    IBOSimple,
    IBOSimpleLine
} from "ibas/index";
import {

} from "../Datas";

/** 组织-结构 */
export interface IOrganizationalStructure extends IBOSimple {

    /** 组织 */
    organization: string;

    /** 归属结构 */
    belonging: number;

    /** 经理 */
    manager: string;

    /** 生效日期 */
    validDate: Date;

    /** 失效日期 */
    invalidDate: Date;

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

    /** 数据源 */
    dataSource: string;

    /** 实例号（版本） */
    logInst: number;

    /** 服务系列 */
    series: number;

    /** 创建用户 */
    createUserSign: number;

    /** 修改用户 */
    updateUserSign: number;

    /** 创建动作标识 */
    createActionId: string;

    /** 更新动作标识 */
    updateActionId: string;


    /** 组织-角色集合 */
    organizationalRoles: IOrganizationalRoles;


}

/** 组织-角色 */
export interface IOrganizationalRole extends IBOSimpleLine {

    /** 编号 */
    objectKey: number;
    /** 类型 */
    objectCode: string;
    /** 行号 */
    lineId: number;
    /** 实例号（版本） */
    logInst: number;
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
    /** 参考1 */
    reference1: string;
    /** 参考2 */
    reference2: string;
    /** 角色 */
    role: string;
    /** 组织-角色-成员集合 */
    roleMembers: IRoleMembers;
}

/** 组织-角色 集合 */
export interface IOrganizationalRoles extends IBusinessObjects<IOrganizationalRole, IOrganizationalStructure> {

    /** 创建并添加子项 */
    create(): IOrganizationalRole;
}


/** 组织-角色-成员 */
export interface IRoleMember extends IBOSimpleLine {

    /** 编号 */
    objectKey: number;
    /** 类型 */
    objectCode: string;
    /** 行号 */
    lineId: number;
    /** 实例号（版本） */
    logInst: number;
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
    /** 参考1 */
    reference1: string;
    /** 参考2 */
    reference2: string;
    /** 角色-行号 */
    roleLineId: number;
    /** 成员 */
    member: string;
}

/** 组织-角色-成员 集合 */
export interface IRoleMembers extends IBusinessObjects<IRoleMember, IOrganizationalStructure> {

    /** 创建并添加子项 */
    create(): IRoleMember;
}
