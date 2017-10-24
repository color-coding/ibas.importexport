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
    emConditionOperation,
    emConditionRelationship,
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

/** 业务对象筛选 */
export interface IBOFiltering extends IBOSimple {

    /** 角色标识 */
    roleCode: string;

    /** 对象类型 */
    boCode: string;

    /** 激活的 */
    activated: emYesNo;

    /** 名称 */
    name: string;

    /** 编号 */
    objectKey: number;

    /** 类型 */
    objectCode: string;

    /** 实例号（版本） */
    logInst: number;

    /** 服务系列 */
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

    /** 创建动作标识 */
    createActionId: string;

    /** 更新动作标识 */
    updateActionId: string;

    /** 创建用户 */
    createUserSign: number;

    /** 修改用户 */
    updateUserSign: number;

    /** 数据所有者 */
    dataOwner: number;

    /** 数据所属组织 */
    organization: string;


    /** 业务对象筛选-条件集合 */
    boFilteringConditions: IBOFilteringConditions;


}

/** 业务对象筛选-条件 */
export interface IBOFilteringCondition extends IBOSimpleLine {

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
    /** 取值属性 */
    propertyName: string;
    /** 比较的值 */
    conditionValue: string;
    /** 比较的方法 */
    operation: emConditionOperation;
    /** 与上一个条件的关系 */
    relationship: emConditionRelationship;

    /** 开括号数 */
    bracketOpen: number;

    /** 闭括号数 */
    bracketClose: number;
}

/** 业务对象筛选-条件 集合 */
export interface IBOFilteringConditions extends IBusinessObjects<IBOFilteringCondition, IBOFiltering> {

    /** 创建并添加子项 */
    create(): IBOFilteringCondition;
}

