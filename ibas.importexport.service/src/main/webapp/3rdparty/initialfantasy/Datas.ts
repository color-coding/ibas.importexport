/**
 * @license
 * Copyright color-coding studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */

// 共享的数据
import {
    strings,
    MODULE_REPOSITORY_NAME_TEMPLATE,
    emYesNo,
} from "ibas/index";

/** 模块-标识 */
export const CONSOLE_ID: string = "c2b31c06-20d8-44a2-bb34-17f47ed01859";
/** 模块-名称 */
export const CONSOLE_NAME: string = "InitialFantasy";
/** 模块-版本 */
export const CONSOLE_VERSION: string = "0.1.0";
/** 配置值-组织方式 */
export const CONFIG_VALUE_ORGANIZATION_WAY: string = "initial";
/** 配置值-数据权限方式 */
export const CONFIG_VALUE_OWNERSHIP_WAY: string = "initial";
/** 业务仓库名称 */
export const BO_REPOSITORY_INITIALFANTASY: string = strings.format(MODULE_REPOSITORY_NAME_TEMPLATE, CONSOLE_NAME);
/** 业务对象编码-应用程序功能 */
export const BO_CODE_APPLICATIONFUNCTION: string = "${Company}_SYS_FUNCTION";
/** 业务对象编码-应用程序模块 */
export const BO_CODE_APPLICATIONMODULE: string = "${Company}_SYS_MODULE";
/** 业务对象编码-应用程序平台 */
export const BO_CODE_APPLICATIONPLATFORM: string = "${Company}_SYS_PLATFORM";
/** 业务对象编码-业务对象检索条件 */
export const BO_CODE_BOCRITERIA: string = "${Company}_SYS_BOCRITERIA";
/** 业务对象编码-业务对象筛选 */
export const BO_CODE_BOFILTERING: string = "${Company}_SYS_BOFILTERING";
/** 业务对象编码-组织 */
export const BO_CODE_ORGANIZATION: string = "${Company}_SYS_ORGANIZATION";
/** 业务对象编码-系统权限 */
export const BO_CODE_PRIVILEGE: string = "${Company}_SYS_PRIVILEGE";
/** 业务对象编码-用户 */
export const BO_CODE_USER: string = "${Company}_SYS_USER";
/** 业务对象编码-业务对象信息 */
export const BO_CODE_BOINFORMATION: string = "${Company}_SYS_BOINFO";
/** 业务对象编码-系统变量 */
export const BO_CODE_SYSTEM_VARIABLE: string = "${Company}_SYS_VARIABLE";
/** 业务对象编码-系统配置 */
export const BO_CODE_SYSTEM_CONFIG: string = "${Company}_SYS_CONFIG";
/** 业务对象编码-用户角色（默认与组织相同） */
export const BO_CODE_ROLE: string = "${Company}_SYS_ROLE";
/** 业务对象编码-项目 */
export const BO_CODE_PROJECT: string = "${Company}_SYS_PROJECT";

/**
 * 分配类型
 */
export enum emAssignedType {
    /** 用户 */
    USER,
    /** 角色 */
    ROLE,
}
/**
 * 筛选类型
 */
export enum emFilteringType {
    /** 不可用的 */
    UNAVAILABLE,
    /** 可用的 */
    AVAILABLE
}
/** 分配-角色 */
export interface IRole {
    /** 编码 */
    code: string;
    /** 名称 */
    name: string;
    /** 激活 */
    activated: emYesNo;
}