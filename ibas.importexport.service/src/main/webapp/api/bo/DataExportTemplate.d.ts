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
    emPaperSize,
    emAreaType,
    emDataSourceType,
    emHorizontalJustification,
    emVerticalJustification
} from "../Datas";

/** 数据导出模板 */
export interface IDataExportTemplate extends IBOSimple {

    /** 编号 */
    objectKey: number;

    /** 类型 */
    objectCode: string;

    /** 实例号（版本） */
    logInst: number;

    /** 数据源 */
    dataSource: string;

    /** 服务系列 */
    series: number;

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

    /** 数据所有者 */
    dataOwner: number;

    /** 团队成员 */
    teamMembers: string;

    /** 数据所属组织 */
    organization: string;

    /** 审批状态 */
    approvalStatus: emApprovalStatus;

    /** 参考1 */
    reference1: string;

    /** 参考2 */
    reference2: string;

    /** 备注 */
    remarks: string;

    /** 编码 */
    code: string;

    /** 名称 */
    name: string;

    /** 注释 */
    notes: string;

    /** 输出宽度 */
    width: number;

    /** 输出高度 */
    height: number;

    /** 左边距 */
    leftMargin: number;

    /** 右边距 */
    rightMargin: number;

    /** 上边距 */
    topMargin: number;

    /** 下边距 */
    bottomMargin: number;

    /** 是否可以修改 */
    canChange: emYesNo;

    /** 纸张大小 */
    paperSize: emPaperSize;

    /** 语言编码 */
    languageCode: string;

    /** 类型 */
    category: string;

    /** 页眉-左坐标 */
    pageHeaderLeft: number;

    /** 页眉-上坐标 */
    pageHeaderTop: number;

    /** 页眉-宽度 */
    pageHeaderWidth: number;

    /** 页眉-高度 */
    pageHeaderHeight: number;

    /** 开始部分-左坐标 */
    startOfSectionLeft: number;

    /** 开始部分-上坐标 */
    startOfSectionTop: number;

    /** 开始部分-宽度 */
    startOfSectionWidth: number;

    /** 开始部分-高度 */
    startOfSectionHeight: number;

    /** 重复区域头-左坐标 */
    repetitiveAreaHeaderLeft: number;

    /** 重复区域头-上坐标 */
    repetitiveAreaHeaderTop: number;

    /** 重复区域头-宽度 */
    repetitiveAreaHeaderWidth: number;

    /** 重复区域头-高度 */
    repetitiveAreaHeaderHeight: number;

    /** 重复区域-左坐标 */
    repetitiveAreaLeft: number;

    /** 重复区域-上坐标 */
    repetitiveAreaTop: number;

    /** 重复区域-宽度 */
    repetitiveAreaWidth: number;

    /** 重复区域-高度 */
    repetitiveAreaHeight: number;

    /** 重复区域脚-左坐标 */
    repetitiveAreaFooterLeft: number;

    /** 重复区域脚-上坐标 */
    repetitiveAreaFooterTop: number;

    /** 重复区域脚-宽度 */
    repetitiveAreaFooterWidth: number;

    /** 重复区域脚-高度 */
    repetitiveAreaFooterHeight: number;

    /** 结束部分-左坐标 */
    endOfSectionLeft: number;

    /** 结束部分-上坐标 */
    endOfSectionTop: number;

    /** 结束部分-宽度 */
    endOfSectionWidth: number;

    /** 结束部分-高度 */
    endOfSectionHeight: number;

    /** 页脚-左坐标 */
    pageFooterLeft: number;

    /** 页脚-上坐标 */
    pageFooterTop: number;

    /** 页脚-宽度 */
    pageFooterWidth: number;

    /** 页脚-高度 */
    pageFooterHeight: number;

    /** 区域间隔 */
    areaMargin: number;

    /** 业务对象编码 */
    bOCode: string;

    /** 平台简码 */
    plantform: string;

    /** 输出份数 */
    copyNumber: number;


    /** 数据导出模板-项集合 */
    dataExportTemplateItems: IDataExportTemplateItems;


}

/** 数据导出模板-项 */
export interface IDataExportTemplateItem extends IBOSimpleLine {

    /** 编号 */
    objectKey: number;
    /** 类型 */
    objectCode: string;
    /** 行号 */
    lineId: number;
    /** 数据源 */
    dataSource: string;
    /** 实例号（版本） */
    logInst: number;
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
    /** 区域类型 */
    areaType: emAreaType;
    /** 项标识 */
    itemUID: string;
    /** 项左坐标 */
    itemLeft: number;
    /** 项上坐标 */
    itemTop: number;
    /** 数据源 */
    sourceType: emDataSourceType;
    /** 项字符串 */
    itemString: string;
    /** 项是否可见 */
    itemVisible: emYesNo;
    /** 项宽度 */
    width: number;
    /** 项高度 */
    height: number;
    /** 左边距 */
    leftMargin: number;
    /** 右边距 */
    rightMargin: number;
    /** 上边距 */
    topMargin: number;
    /** 下边距 */
    bottomMargin: number;
    /** 左线长度 */
    leftLine: number;
    /** 右线长度 */
    rightLine: number;
    /** 上线长度 */
    topLine: number;
    /** 下线长度 */
    bottomLine: number;
    /** 字体名称 */
    fontName: string;
    /** 字体大小 */
    fontSize: number;
    /** 文本样式 */
    textStyle: number;
    /** 水平对齐方式 */
    horizontalJustification: emHorizontalJustification;
    /** 竖直对齐方式 */
    verticalJustification: emVerticalJustification;
    /** 背景色-红 */
    background_Red: number;
    /** 背景色-绿 */
    background_Green: number;
    /** 背景色-蓝 */
    background_Blue: number;
    /** 前景色-红 */
    foreground_Red: number;
    /** 前景色-绿 */
    foreground_Green: number;
    /** 前景色-蓝 */
    foreground_Blue: number;
    /** 高亮显示色-红 */
    marker_Red: number;
    /** 高亮显示色-绿 */
    marker_Green: number;
    /** 高亮显示色-蓝 */
    marker_Blue: number;
    /** 框架色-红 */
    border_Red: number;
    /** 框架色-绿 */
    border_Green: number;
    /** 框架色-蓝 */
    border_Blue: number;
    /** 显示格式 */
    valFormat: string;
}

/** 数据导出模板-项 集合 */
export interface IDataExportTemplateItems extends IBusinessObjects<IDataExportTemplateItem, IDataExportTemplate> {

    /** 创建并添加子项 */
    create(): IDataExportTemplateItem;
}

