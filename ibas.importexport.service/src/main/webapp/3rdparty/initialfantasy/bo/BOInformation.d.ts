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

/** 业务对象信息 */
export interface IBOInformation extends IBusinessObject {

    /** 编码 */
    code: string;

    /** 名称 */
    name: string;

    /** 描述 */
    description: string;

    /** 映射（表） */
    mapped: string;

    /** 对象类型 */
    objectType: string;

    /** 业务对象属性信息集合 */
    boPropertyInformations: IBOPropertyInformations;

}

/** 业务对象属性信息 */
export interface IBOPropertyInformation extends IBusinessObject {

    /** 编码 */
    code: string;
    /** 属性名称 */
    property: string;
    /** 映射（字段） */
    mapped: string;
    /** 描述 */
    description: string;
    /** 数据类型 */
    dataType: string;
    /** 编辑类型 */
    editType: string;
    /** 编辑大小 */
    editSize: number;
    /** 检索的 */
    searched: emYesNo;
    /** 系统的 */
    systemed: emYesNo;
    /** 可编辑 */
    editable: emYesNo;
}

/** 业务对象属性信息 集合 */
export interface IBOPropertyInformations extends IBusinessObjects<IBOPropertyInformation, IBOInformation> {

    /** 创建并添加子项 */
    create(): IBOPropertyInformation;
}

