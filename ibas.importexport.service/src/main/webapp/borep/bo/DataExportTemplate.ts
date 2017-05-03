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
    BusinessObject,
    BusinessObjects,
    BOMasterData,
    BOMasterDataLine,
    BODocument,
    BODocumentLine,
    BOSimple,
    BOSimpleLine,
    config,
} from "ibas/index";
import {
    IDataExportTemplate,
    IDataExportTemplateItem,
    IDataExportTemplateItems,
    emPaperSize,
    emAreaType,
    emDataSourceType,
    emHorizontalJustification,
    emVerticalJustification
} from "../../api/index";

/** 数据导出模板 */
export class DataExportTemplate extends BOSimple<DataExportTemplate> implements IDataExportTemplate {

    /** 业务对象编码 */
    static BUSINESS_OBJECT_CODE: string = "${Company}_IE_EXPORTTEMPLATE";
    /** 构造函数 */
    constructor() {
        super();
    }
    /** 映射的属性名称-编号 */
    static PROPERTY_OBJECTKEY_NAME: string = "ObjectKey";
    /** 获取-编号 */
    get objectKey(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_OBJECTKEY_NAME);
    }
    /** 设置-编号 */
    set objectKey(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_OBJECTKEY_NAME, value);
    }

    /** 映射的属性名称-类型 */
    static PROPERTY_OBJECTCODE_NAME: string = "ObjectCode";
    /** 获取-类型 */
    get objectCode(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_OBJECTCODE_NAME);
    }
    /** 设置-类型 */
    set objectCode(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_OBJECTCODE_NAME, value);
    }

    /** 映射的属性名称-实例号（版本） */
    static PROPERTY_LOGINST_NAME: string = "LogInst";
    /** 获取-实例号（版本） */
    get logInst(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_LOGINST_NAME);
    }
    /** 设置-实例号（版本） */
    set logInst(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_LOGINST_NAME, value);
    }

    /** 映射的属性名称-数据源 */
    static PROPERTY_DATASOURCE_NAME: string = "DataSource";
    /** 获取-数据源 */
    get dataSource(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_DATASOURCE_NAME);
    }
    /** 设置-数据源 */
    set dataSource(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_DATASOURCE_NAME, value);
    }

    /** 映射的属性名称-服务系列 */
    static PROPERTY_SERIES_NAME: string = "Series";
    /** 获取-服务系列 */
    get series(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_SERIES_NAME);
    }
    /** 设置-服务系列 */
    set series(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_SERIES_NAME, value);
    }

    /** 映射的属性名称-创建日期 */
    static PROPERTY_CREATEDATE_NAME: string = "CreateDate";
    /** 获取-创建日期 */
    get createDate(): Date {
        return this.getProperty<Date>(DataExportTemplate.PROPERTY_CREATEDATE_NAME);
    }
    /** 设置-创建日期 */
    set createDate(value: Date) {
        this.setProperty(DataExportTemplate.PROPERTY_CREATEDATE_NAME, value);
    }

    /** 映射的属性名称-创建时间 */
    static PROPERTY_CREATETIME_NAME: string = "CreateTime";
    /** 获取-创建时间 */
    get createTime(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_CREATETIME_NAME);
    }
    /** 设置-创建时间 */
    set createTime(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_CREATETIME_NAME, value);
    }

    /** 映射的属性名称-修改日期 */
    static PROPERTY_UPDATEDATE_NAME: string = "UpdateDate";
    /** 获取-修改日期 */
    get updateDate(): Date {
        return this.getProperty<Date>(DataExportTemplate.PROPERTY_UPDATEDATE_NAME);
    }
    /** 设置-修改日期 */
    set updateDate(value: Date) {
        this.setProperty(DataExportTemplate.PROPERTY_UPDATEDATE_NAME, value);
    }

    /** 映射的属性名称-修改时间 */
    static PROPERTY_UPDATETIME_NAME: string = "UpdateTime";
    /** 获取-修改时间 */
    get updateTime(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_UPDATETIME_NAME);
    }
    /** 设置-修改时间 */
    set updateTime(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_UPDATETIME_NAME, value);
    }

    /** 映射的属性名称-创建用户 */
    static PROPERTY_CREATEUSERSIGN_NAME: string = "CreateUserSign";
    /** 获取-创建用户 */
    get createUserSign(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_CREATEUSERSIGN_NAME);
    }
    /** 设置-创建用户 */
    set createUserSign(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_CREATEUSERSIGN_NAME, value);
    }

    /** 映射的属性名称-修改用户 */
    static PROPERTY_UPDATEUSERSIGN_NAME: string = "UpdateUserSign";
    /** 获取-修改用户 */
    get updateUserSign(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_UPDATEUSERSIGN_NAME);
    }
    /** 设置-修改用户 */
    set updateUserSign(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_UPDATEUSERSIGN_NAME, value);
    }

    /** 映射的属性名称-创建动作标识 */
    static PROPERTY_CREATEACTIONID_NAME: string = "CreateActionId";
    /** 获取-创建动作标识 */
    get createActionId(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_CREATEACTIONID_NAME);
    }
    /** 设置-创建动作标识 */
    set createActionId(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_CREATEACTIONID_NAME, value);
    }

    /** 映射的属性名称-更新动作标识 */
    static PROPERTY_UPDATEACTIONID_NAME: string = "UpdateActionId";
    /** 获取-更新动作标识 */
    get updateActionId(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_UPDATEACTIONID_NAME);
    }
    /** 设置-更新动作标识 */
    set updateActionId(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_UPDATEACTIONID_NAME, value);
    }

    /** 映射的属性名称-数据所有者 */
    static PROPERTY_DATAOWNER_NAME: string = "DataOwner";
    /** 获取-数据所有者 */
    get dataOwner(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_DATAOWNER_NAME);
    }
    /** 设置-数据所有者 */
    set dataOwner(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_DATAOWNER_NAME, value);
    }

    /** 映射的属性名称-团队成员 */
    static PROPERTY_TEAMMEMBERS_NAME: string = "TeamMembers";
    /** 获取-团队成员 */
    get teamMembers(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_TEAMMEMBERS_NAME);
    }
    /** 设置-团队成员 */
    set teamMembers(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_TEAMMEMBERS_NAME, value);
    }

    /** 映射的属性名称-数据所属组织 */
    static PROPERTY_ORGANIZATION_NAME: string = "Organization";
    /** 获取-数据所属组织 */
    get organization(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_ORGANIZATION_NAME);
    }
    /** 设置-数据所属组织 */
    set organization(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_ORGANIZATION_NAME, value);
    }

    /** 映射的属性名称-审批状态 */
    static PROPERTY_APPROVALSTATUS_NAME: string = "ApprovalStatus";
    /** 获取-审批状态 */
    get approvalStatus(): emApprovalStatus {
        return this.getProperty<emApprovalStatus>(DataExportTemplate.PROPERTY_APPROVALSTATUS_NAME);
    }
    /** 设置-审批状态 */
    set approvalStatus(value: emApprovalStatus) {
        this.setProperty(DataExportTemplate.PROPERTY_APPROVALSTATUS_NAME, value);
    }

    /** 映射的属性名称-参考1 */
    static PROPERTY_REFERENCE1_NAME: string = "Reference1";
    /** 获取-参考1 */
    get reference1(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_REFERENCE1_NAME);
    }
    /** 设置-参考1 */
    set reference1(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_REFERENCE1_NAME, value);
    }

    /** 映射的属性名称-参考2 */
    static PROPERTY_REFERENCE2_NAME: string = "Reference2";
    /** 获取-参考2 */
    get reference2(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_REFERENCE2_NAME);
    }
    /** 设置-参考2 */
    set reference2(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_REFERENCE2_NAME, value);
    }

    /** 映射的属性名称-备注 */
    static PROPERTY_REMARKS_NAME: string = "Remarks";
    /** 获取-备注 */
    get remarks(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_REMARKS_NAME);
    }
    /** 设置-备注 */
    set remarks(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_REMARKS_NAME, value);
    }

    /** 映射的属性名称-编码 */
    static PROPERTY_CODE_NAME: string = "Code";
    /** 获取-编码 */
    get code(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_CODE_NAME);
    }
    /** 设置-编码 */
    set code(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_CODE_NAME, value);
    }

    /** 映射的属性名称-名称 */
    static PROPERTY_NAME_NAME: string = "Name";
    /** 获取-名称 */
    get name(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_NAME_NAME);
    }
    /** 设置-名称 */
    set name(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_NAME_NAME, value);
    }

    /** 映射的属性名称-注释 */
    static PROPERTY_NOTES_NAME: string = "Notes";
    /** 获取-注释 */
    get notes(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_NOTES_NAME);
    }
    /** 设置-注释 */
    set notes(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_NOTES_NAME, value);
    }

    /** 映射的属性名称-输出宽度 */
    static PROPERTY_WIDTH_NAME: string = "Width";
    /** 获取-输出宽度 */
    get width(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_WIDTH_NAME);
    }
    /** 设置-输出宽度 */
    set width(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_WIDTH_NAME, value);
    }

    /** 映射的属性名称-输出高度 */
    static PROPERTY_HEIGHT_NAME: string = "Height";
    /** 获取-输出高度 */
    get height(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_HEIGHT_NAME);
    }
    /** 设置-输出高度 */
    set height(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_HEIGHT_NAME, value);
    }

    /** 映射的属性名称-左边距 */
    static PROPERTY_LEFTMARGIN_NAME: string = "LeftMargin";
    /** 获取-左边距 */
    get leftMargin(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_LEFTMARGIN_NAME);
    }
    /** 设置-左边距 */
    set leftMargin(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_LEFTMARGIN_NAME, value);
    }

    /** 映射的属性名称-右边距 */
    static PROPERTY_RIGHTMARGIN_NAME: string = "RightMargin";
    /** 获取-右边距 */
    get rightMargin(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_RIGHTMARGIN_NAME);
    }
    /** 设置-右边距 */
    set rightMargin(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_RIGHTMARGIN_NAME, value);
    }

    /** 映射的属性名称-上边距 */
    static PROPERTY_TOPMARGIN_NAME: string = "TopMargin";
    /** 获取-上边距 */
    get topMargin(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_TOPMARGIN_NAME);
    }
    /** 设置-上边距 */
    set topMargin(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_TOPMARGIN_NAME, value);
    }

    /** 映射的属性名称-下边距 */
    static PROPERTY_BOTTOMMARGIN_NAME: string = "BottomMargin";
    /** 获取-下边距 */
    get bottomMargin(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_BOTTOMMARGIN_NAME);
    }
    /** 设置-下边距 */
    set bottomMargin(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_BOTTOMMARGIN_NAME, value);
    }

    /** 映射的属性名称-是否可以修改 */
    static PROPERTY_CANCHANGE_NAME: string = "CanChange";
    /** 获取-是否可以修改 */
    get canChange(): emYesNo {
        return this.getProperty<emYesNo>(DataExportTemplate.PROPERTY_CANCHANGE_NAME);
    }
    /** 设置-是否可以修改 */
    set canChange(value: emYesNo) {
        this.setProperty(DataExportTemplate.PROPERTY_CANCHANGE_NAME, value);
    }

    /** 映射的属性名称-纸张大小 */
    static PROPERTY_PAPERSIZE_NAME: string = "PaperSize";
    /** 获取-纸张大小 */
    get paperSize(): emPaperSize {
        return this.getProperty<emPaperSize>(DataExportTemplate.PROPERTY_PAPERSIZE_NAME);
    }
    /** 设置-纸张大小 */
    set paperSize(value: emPaperSize) {
        this.setProperty(DataExportTemplate.PROPERTY_PAPERSIZE_NAME, value);
    }

    /** 映射的属性名称-语言编码 */
    static PROPERTY_LANGUAGECODE_NAME: string = "LanguageCode";
    /** 获取-语言编码 */
    get languageCode(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_LANGUAGECODE_NAME);
    }
    /** 设置-语言编码 */
    set languageCode(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_LANGUAGECODE_NAME, value);
    }

    /** 映射的属性名称-类型 */
    static PROPERTY_CATEGORY_NAME: string = "Category";
    /** 获取-类型 */
    get category(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_CATEGORY_NAME);
    }
    /** 设置-类型 */
    set category(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_CATEGORY_NAME, value);
    }

    /** 映射的属性名称-页眉-左坐标 */
    static PROPERTY_PAGEHEADERLEFT_NAME: string = "PageHeaderLeft";
    /** 获取-页眉-左坐标 */
    get pageHeaderLeft(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_PAGEHEADERLEFT_NAME);
    }
    /** 设置-页眉-左坐标 */
    set pageHeaderLeft(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_PAGEHEADERLEFT_NAME, value);
    }

    /** 映射的属性名称-页眉-上坐标 */
    static PROPERTY_PAGEHEADERTOP_NAME: string = "PageHeaderTop";
    /** 获取-页眉-上坐标 */
    get pageHeaderTop(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_PAGEHEADERTOP_NAME);
    }
    /** 设置-页眉-上坐标 */
    set pageHeaderTop(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_PAGEHEADERTOP_NAME, value);
    }

    /** 映射的属性名称-页眉-宽度 */
    static PROPERTY_PAGEHEADERWIDTH_NAME: string = "PageHeaderWidth";
    /** 获取-页眉-宽度 */
    get pageHeaderWidth(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_PAGEHEADERWIDTH_NAME);
    }
    /** 设置-页眉-宽度 */
    set pageHeaderWidth(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_PAGEHEADERWIDTH_NAME, value);
    }

    /** 映射的属性名称-页眉-高度 */
    static PROPERTY_PAGEHEADERHEIGHT_NAME: string = "PageHeaderHeight";
    /** 获取-页眉-高度 */
    get pageHeaderHeight(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_PAGEHEADERHEIGHT_NAME);
    }
    /** 设置-页眉-高度 */
    set pageHeaderHeight(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_PAGEHEADERHEIGHT_NAME, value);
    }

    /** 映射的属性名称-开始部分-左坐标 */
    static PROPERTY_STARTOFSECTIONLEFT_NAME: string = "StartOfSectionLeft";
    /** 获取-开始部分-左坐标 */
    get startOfSectionLeft(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_STARTOFSECTIONLEFT_NAME);
    }
    /** 设置-开始部分-左坐标 */
    set startOfSectionLeft(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_STARTOFSECTIONLEFT_NAME, value);
    }

    /** 映射的属性名称-开始部分-上坐标 */
    static PROPERTY_STARTOFSECTIONTOP_NAME: string = "StartOfSectionTop";
    /** 获取-开始部分-上坐标 */
    get startOfSectionTop(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_STARTOFSECTIONTOP_NAME);
    }
    /** 设置-开始部分-上坐标 */
    set startOfSectionTop(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_STARTOFSECTIONTOP_NAME, value);
    }

    /** 映射的属性名称-开始部分-宽度 */
    static PROPERTY_STARTOFSECTIONWIDTH_NAME: string = "StartOfSectionWidth";
    /** 获取-开始部分-宽度 */
    get startOfSectionWidth(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_STARTOFSECTIONWIDTH_NAME);
    }
    /** 设置-开始部分-宽度 */
    set startOfSectionWidth(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_STARTOFSECTIONWIDTH_NAME, value);
    }

    /** 映射的属性名称-开始部分-高度 */
    static PROPERTY_STARTOFSECTIONHEIGHT_NAME: string = "StartOfSectionHeight";
    /** 获取-开始部分-高度 */
    get startOfSectionHeight(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_STARTOFSECTIONHEIGHT_NAME);
    }
    /** 设置-开始部分-高度 */
    set startOfSectionHeight(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_STARTOFSECTIONHEIGHT_NAME, value);
    }

    /** 映射的属性名称-重复区域头-左坐标 */
    static PROPERTY_REPETITIVEAREAHEADERLEFT_NAME: string = "RepetitiveAreaHeaderLeft";
    /** 获取-重复区域头-左坐标 */
    get repetitiveAreaHeaderLeft(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_REPETITIVEAREAHEADERLEFT_NAME);
    }
    /** 设置-重复区域头-左坐标 */
    set repetitiveAreaHeaderLeft(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_REPETITIVEAREAHEADERLEFT_NAME, value);
    }

    /** 映射的属性名称-重复区域头-上坐标 */
    static PROPERTY_REPETITIVEAREAHEADERTOP_NAME: string = "RepetitiveAreaHeaderTop";
    /** 获取-重复区域头-上坐标 */
    get repetitiveAreaHeaderTop(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_REPETITIVEAREAHEADERTOP_NAME);
    }
    /** 设置-重复区域头-上坐标 */
    set repetitiveAreaHeaderTop(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_REPETITIVEAREAHEADERTOP_NAME, value);
    }

    /** 映射的属性名称-重复区域头-宽度 */
    static PROPERTY_REPETITIVEAREAHEADERWIDTH_NAME: string = "RepetitiveAreaHeaderWidth";
    /** 获取-重复区域头-宽度 */
    get repetitiveAreaHeaderWidth(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_REPETITIVEAREAHEADERWIDTH_NAME);
    }
    /** 设置-重复区域头-宽度 */
    set repetitiveAreaHeaderWidth(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_REPETITIVEAREAHEADERWIDTH_NAME, value);
    }

    /** 映射的属性名称-重复区域头-高度 */
    static PROPERTY_REPETITIVEAREAHEADERHEIGHT_NAME: string = "RepetitiveAreaHeaderHeight";
    /** 获取-重复区域头-高度 */
    get repetitiveAreaHeaderHeight(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_REPETITIVEAREAHEADERHEIGHT_NAME);
    }
    /** 设置-重复区域头-高度 */
    set repetitiveAreaHeaderHeight(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_REPETITIVEAREAHEADERHEIGHT_NAME, value);
    }

    /** 映射的属性名称-重复区域-左坐标 */
    static PROPERTY_REPETITIVEAREALEFT_NAME: string = "RepetitiveAreaLeft";
    /** 获取-重复区域-左坐标 */
    get repetitiveAreaLeft(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_REPETITIVEAREALEFT_NAME);
    }
    /** 设置-重复区域-左坐标 */
    set repetitiveAreaLeft(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_REPETITIVEAREALEFT_NAME, value);
    }

    /** 映射的属性名称-重复区域-上坐标 */
    static PROPERTY_REPETITIVEAREATOP_NAME: string = "RepetitiveAreaTop";
    /** 获取-重复区域-上坐标 */
    get repetitiveAreaTop(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_REPETITIVEAREATOP_NAME);
    }
    /** 设置-重复区域-上坐标 */
    set repetitiveAreaTop(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_REPETITIVEAREATOP_NAME, value);
    }

    /** 映射的属性名称-重复区域-宽度 */
    static PROPERTY_REPETITIVEAREAWIDTH_NAME: string = "RepetitiveAreaWidth";
    /** 获取-重复区域-宽度 */
    get repetitiveAreaWidth(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_REPETITIVEAREAWIDTH_NAME);
    }
    /** 设置-重复区域-宽度 */
    set repetitiveAreaWidth(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_REPETITIVEAREAWIDTH_NAME, value);
    }

    /** 映射的属性名称-重复区域-高度 */
    static PROPERTY_REPETITIVEAREAHEIGHT_NAME: string = "RepetitiveAreaHeight";
    /** 获取-重复区域-高度 */
    get repetitiveAreaHeight(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_REPETITIVEAREAHEIGHT_NAME);
    }
    /** 设置-重复区域-高度 */
    set repetitiveAreaHeight(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_REPETITIVEAREAHEIGHT_NAME, value);
    }

    /** 映射的属性名称-重复区域脚-左坐标 */
    static PROPERTY_REPETITIVEAREAFOOTERLEFT_NAME: string = "RepetitiveAreaFooterLeft";
    /** 获取-重复区域脚-左坐标 */
    get repetitiveAreaFooterLeft(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_REPETITIVEAREAFOOTERLEFT_NAME);
    }
    /** 设置-重复区域脚-左坐标 */
    set repetitiveAreaFooterLeft(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_REPETITIVEAREAFOOTERLEFT_NAME, value);
    }

    /** 映射的属性名称-重复区域脚-上坐标 */
    static PROPERTY_REPETITIVEAREAFOOTERTOP_NAME: string = "RepetitiveAreaFooterTop";
    /** 获取-重复区域脚-上坐标 */
    get repetitiveAreaFooterTop(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_REPETITIVEAREAFOOTERTOP_NAME);
    }
    /** 设置-重复区域脚-上坐标 */
    set repetitiveAreaFooterTop(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_REPETITIVEAREAFOOTERTOP_NAME, value);
    }

    /** 映射的属性名称-重复区域脚-宽度 */
    static PROPERTY_REPETITIVEAREAFOOTERWIDTH_NAME: string = "RepetitiveAreaFooterWidth";
    /** 获取-重复区域脚-宽度 */
    get repetitiveAreaFooterWidth(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_REPETITIVEAREAFOOTERWIDTH_NAME);
    }
    /** 设置-重复区域脚-宽度 */
    set repetitiveAreaFooterWidth(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_REPETITIVEAREAFOOTERWIDTH_NAME, value);
    }

    /** 映射的属性名称-重复区域脚-高度 */
    static PROPERTY_REPETITIVEAREAFOOTERHEIGHT_NAME: string = "RepetitiveAreaFooterHeight";
    /** 获取-重复区域脚-高度 */
    get repetitiveAreaFooterHeight(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_REPETITIVEAREAFOOTERHEIGHT_NAME);
    }
    /** 设置-重复区域脚-高度 */
    set repetitiveAreaFooterHeight(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_REPETITIVEAREAFOOTERHEIGHT_NAME, value);
    }

    /** 映射的属性名称-结束部分-左坐标 */
    static PROPERTY_ENDOFSECTIONLEFT_NAME: string = "EndOfSectionLeft";
    /** 获取-结束部分-左坐标 */
    get endOfSectionLeft(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_ENDOFSECTIONLEFT_NAME);
    }
    /** 设置-结束部分-左坐标 */
    set endOfSectionLeft(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_ENDOFSECTIONLEFT_NAME, value);
    }

    /** 映射的属性名称-结束部分-上坐标 */
    static PROPERTY_ENDOFSECTIONTOP_NAME: string = "EndOfSectionTop";
    /** 获取-结束部分-上坐标 */
    get endOfSectionTop(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_ENDOFSECTIONTOP_NAME);
    }
    /** 设置-结束部分-上坐标 */
    set endOfSectionTop(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_ENDOFSECTIONTOP_NAME, value);
    }

    /** 映射的属性名称-结束部分-宽度 */
    static PROPERTY_ENDOFSECTIONWIDTH_NAME: string = "EndOfSectionWidth";
    /** 获取-结束部分-宽度 */
    get endOfSectionWidth(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_ENDOFSECTIONWIDTH_NAME);
    }
    /** 设置-结束部分-宽度 */
    set endOfSectionWidth(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_ENDOFSECTIONWIDTH_NAME, value);
    }

    /** 映射的属性名称-结束部分-高度 */
    static PROPERTY_ENDOFSECTIONHEIGHT_NAME: string = "EndOfSectionHeight";
    /** 获取-结束部分-高度 */
    get endOfSectionHeight(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_ENDOFSECTIONHEIGHT_NAME);
    }
    /** 设置-结束部分-高度 */
    set endOfSectionHeight(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_ENDOFSECTIONHEIGHT_NAME, value);
    }

    /** 映射的属性名称-页脚-左坐标 */
    static PROPERTY_PAGEFOOTERLEFT_NAME: string = "PageFooterLeft";
    /** 获取-页脚-左坐标 */
    get pageFooterLeft(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_PAGEFOOTERLEFT_NAME);
    }
    /** 设置-页脚-左坐标 */
    set pageFooterLeft(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_PAGEFOOTERLEFT_NAME, value);
    }

    /** 映射的属性名称-页脚-上坐标 */
    static PROPERTY_PAGEFOOTERTOP_NAME: string = "PageFooterTop";
    /** 获取-页脚-上坐标 */
    get pageFooterTop(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_PAGEFOOTERTOP_NAME);
    }
    /** 设置-页脚-上坐标 */
    set pageFooterTop(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_PAGEFOOTERTOP_NAME, value);
    }

    /** 映射的属性名称-页脚-宽度 */
    static PROPERTY_PAGEFOOTERWIDTH_NAME: string = "PageFooterWidth";
    /** 获取-页脚-宽度 */
    get pageFooterWidth(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_PAGEFOOTERWIDTH_NAME);
    }
    /** 设置-页脚-宽度 */
    set pageFooterWidth(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_PAGEFOOTERWIDTH_NAME, value);
    }

    /** 映射的属性名称-页脚-高度 */
    static PROPERTY_PAGEFOOTERHEIGHT_NAME: string = "PageFooterHeight";
    /** 获取-页脚-高度 */
    get pageFooterHeight(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_PAGEFOOTERHEIGHT_NAME);
    }
    /** 设置-页脚-高度 */
    set pageFooterHeight(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_PAGEFOOTERHEIGHT_NAME, value);
    }

    /** 映射的属性名称-区域间隔 */
    static PROPERTY_AREAMARGIN_NAME: string = "AreaMargin";
    /** 获取-区域间隔 */
    get areaMargin(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_AREAMARGIN_NAME);
    }
    /** 设置-区域间隔 */
    set areaMargin(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_AREAMARGIN_NAME, value);
    }

    /** 映射的属性名称-业务对象编码 */
    static PROPERTY_BOCODE_NAME: string = "BOCode";
    /** 获取-业务对象编码 */
    get bOCode(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_BOCODE_NAME);
    }
    /** 设置-业务对象编码 */
    set bOCode(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_BOCODE_NAME, value);
    }

    /** 映射的属性名称-平台简码 */
    static PROPERTY_PLANTFORM_NAME: string = "Plantform";
    /** 获取-平台简码 */
    get plantform(): string {
        return this.getProperty<string>(DataExportTemplate.PROPERTY_PLANTFORM_NAME);
    }
    /** 设置-平台简码 */
    set plantform(value: string) {
        this.setProperty(DataExportTemplate.PROPERTY_PLANTFORM_NAME, value);
    }

    /** 映射的属性名称-输出份数 */
    static PROPERTY_COPYNUMBER_NAME: string = "CopyNumber";
    /** 获取-输出份数 */
    get copyNumber(): number {
        return this.getProperty<number>(DataExportTemplate.PROPERTY_COPYNUMBER_NAME);
    }
    /** 设置-输出份数 */
    set copyNumber(value: number) {
        this.setProperty(DataExportTemplate.PROPERTY_COPYNUMBER_NAME, value);
    }


    /** 映射的属性名称-数据导出模板-项集合 */
    static PROPERTY_DATAEXPORTTEMPLATEITEMS_NAME: string = "DataExportTemplateItems";
    /** 获取-数据导出模板-项集合 */
    get dataExportTemplateItems(): DataExportTemplateItems {
        return this.getProperty<DataExportTemplateItems>(DataExportTemplate.PROPERTY_DATAEXPORTTEMPLATEITEMS_NAME);
    }
    /** 设置-数据导出模板-项集合 */
    set dataExportTemplateItems(value: DataExportTemplateItems) {
        this.setProperty(DataExportTemplate.PROPERTY_DATAEXPORTTEMPLATEITEMS_NAME, value);
    }


    /** 初始化数据 */
    protected init(): void {
        this.dataExportTemplateItems = new DataExportTemplateItems(this);
        this.objectCode = config.applyVariables(DataExportTemplate.BUSINESS_OBJECT_CODE);
    }
}

/** 数据导出模板-项 */
export class DataExportTemplateItem extends BOSimpleLine<DataExportTemplateItem> implements IDataExportTemplateItem {

    /** 构造函数 */
    constructor() {
        super();
    }
    /** 映射的属性名称-编号 */
    static PROPERTY_OBJECTKEY_NAME: string = "ObjectKey";
    /** 获取-编号 */
    get objectKey(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_OBJECTKEY_NAME);
    }
    /** 设置-编号 */
    set objectKey(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_OBJECTKEY_NAME, value);
    }

    /** 映射的属性名称-类型 */
    static PROPERTY_OBJECTCODE_NAME: string = "ObjectCode";
    /** 获取-类型 */
    get objectCode(): string {
        return this.getProperty<string>(DataExportTemplateItem.PROPERTY_OBJECTCODE_NAME);
    }
    /** 设置-类型 */
    set objectCode(value: string) {
        this.setProperty(DataExportTemplateItem.PROPERTY_OBJECTCODE_NAME, value);
    }

    /** 映射的属性名称-行号 */
    static PROPERTY_LINEID_NAME: string = "LineId";
    /** 获取-行号 */
    get lineId(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_LINEID_NAME);
    }
    /** 设置-行号 */
    set lineId(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_LINEID_NAME, value);
    }

    /** 映射的属性名称-数据源 */
    static PROPERTY_DATASOURCE_NAME: string = "DataSource";
    /** 获取-数据源 */
    get dataSource(): string {
        return this.getProperty<string>(DataExportTemplateItem.PROPERTY_DATASOURCE_NAME);
    }
    /** 设置-数据源 */
    set dataSource(value: string) {
        this.setProperty(DataExportTemplateItem.PROPERTY_DATASOURCE_NAME, value);
    }

    /** 映射的属性名称-实例号（版本） */
    static PROPERTY_LOGINST_NAME: string = "LogInst";
    /** 获取-实例号（版本） */
    get logInst(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_LOGINST_NAME);
    }
    /** 设置-实例号（版本） */
    set logInst(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_LOGINST_NAME, value);
    }

    /** 映射的属性名称-创建日期 */
    static PROPERTY_CREATEDATE_NAME: string = "CreateDate";
    /** 获取-创建日期 */
    get createDate(): Date {
        return this.getProperty<Date>(DataExportTemplateItem.PROPERTY_CREATEDATE_NAME);
    }
    /** 设置-创建日期 */
    set createDate(value: Date) {
        this.setProperty(DataExportTemplateItem.PROPERTY_CREATEDATE_NAME, value);
    }

    /** 映射的属性名称-创建时间 */
    static PROPERTY_CREATETIME_NAME: string = "CreateTime";
    /** 获取-创建时间 */
    get createTime(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_CREATETIME_NAME);
    }
    /** 设置-创建时间 */
    set createTime(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_CREATETIME_NAME, value);
    }

    /** 映射的属性名称-修改日期 */
    static PROPERTY_UPDATEDATE_NAME: string = "UpdateDate";
    /** 获取-修改日期 */
    get updateDate(): Date {
        return this.getProperty<Date>(DataExportTemplateItem.PROPERTY_UPDATEDATE_NAME);
    }
    /** 设置-修改日期 */
    set updateDate(value: Date) {
        this.setProperty(DataExportTemplateItem.PROPERTY_UPDATEDATE_NAME, value);
    }

    /** 映射的属性名称-修改时间 */
    static PROPERTY_UPDATETIME_NAME: string = "UpdateTime";
    /** 获取-修改时间 */
    get updateTime(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_UPDATETIME_NAME);
    }
    /** 设置-修改时间 */
    set updateTime(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_UPDATETIME_NAME, value);
    }

    /** 映射的属性名称-创建用户 */
    static PROPERTY_CREATEUSERSIGN_NAME: string = "CreateUserSign";
    /** 获取-创建用户 */
    get createUserSign(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_CREATEUSERSIGN_NAME);
    }
    /** 设置-创建用户 */
    set createUserSign(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_CREATEUSERSIGN_NAME, value);
    }

    /** 映射的属性名称-修改用户 */
    static PROPERTY_UPDATEUSERSIGN_NAME: string = "UpdateUserSign";
    /** 获取-修改用户 */
    get updateUserSign(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_UPDATEUSERSIGN_NAME);
    }
    /** 设置-修改用户 */
    set updateUserSign(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_UPDATEUSERSIGN_NAME, value);
    }

    /** 映射的属性名称-创建动作标识 */
    static PROPERTY_CREATEACTIONID_NAME: string = "CreateActionId";
    /** 获取-创建动作标识 */
    get createActionId(): string {
        return this.getProperty<string>(DataExportTemplateItem.PROPERTY_CREATEACTIONID_NAME);
    }
    /** 设置-创建动作标识 */
    set createActionId(value: string) {
        this.setProperty(DataExportTemplateItem.PROPERTY_CREATEACTIONID_NAME, value);
    }

    /** 映射的属性名称-更新动作标识 */
    static PROPERTY_UPDATEACTIONID_NAME: string = "UpdateActionId";
    /** 获取-更新动作标识 */
    get updateActionId(): string {
        return this.getProperty<string>(DataExportTemplateItem.PROPERTY_UPDATEACTIONID_NAME);
    }
    /** 设置-更新动作标识 */
    set updateActionId(value: string) {
        this.setProperty(DataExportTemplateItem.PROPERTY_UPDATEACTIONID_NAME, value);
    }

    /** 映射的属性名称-参考1 */
    static PROPERTY_REFERENCE1_NAME: string = "Reference1";
    /** 获取-参考1 */
    get reference1(): string {
        return this.getProperty<string>(DataExportTemplateItem.PROPERTY_REFERENCE1_NAME);
    }
    /** 设置-参考1 */
    set reference1(value: string) {
        this.setProperty(DataExportTemplateItem.PROPERTY_REFERENCE1_NAME, value);
    }

    /** 映射的属性名称-参考2 */
    static PROPERTY_REFERENCE2_NAME: string = "Reference2";
    /** 获取-参考2 */
    get reference2(): string {
        return this.getProperty<string>(DataExportTemplateItem.PROPERTY_REFERENCE2_NAME);
    }
    /** 设置-参考2 */
    set reference2(value: string) {
        this.setProperty(DataExportTemplateItem.PROPERTY_REFERENCE2_NAME, value);
    }

    /** 映射的属性名称-区域类型 */
    static PROPERTY_AREATYPE_NAME: string = "AreaType";
    /** 获取-区域类型 */
    get areaType(): emAreaType {
        return this.getProperty<emAreaType>(DataExportTemplateItem.PROPERTY_AREATYPE_NAME);
    }
    /** 设置-区域类型 */
    set areaType(value: emAreaType) {
        this.setProperty(DataExportTemplateItem.PROPERTY_AREATYPE_NAME, value);
    }

    /** 映射的属性名称-项标识 */
    static PROPERTY_ITEMUID_NAME: string = "ItemUID";
    /** 获取-项标识 */
    get itemUID(): string {
        return this.getProperty<string>(DataExportTemplateItem.PROPERTY_ITEMUID_NAME);
    }
    /** 设置-项标识 */
    set itemUID(value: string) {
        this.setProperty(DataExportTemplateItem.PROPERTY_ITEMUID_NAME, value);
    }

    /** 映射的属性名称-项左坐标 */
    static PROPERTY_ITEMLEFT_NAME: string = "ItemLeft";
    /** 获取-项左坐标 */
    get itemLeft(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_ITEMLEFT_NAME);
    }
    /** 设置-项左坐标 */
    set itemLeft(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_ITEMLEFT_NAME, value);
    }

    /** 映射的属性名称-项上坐标 */
    static PROPERTY_ITEMTOP_NAME: string = "ItemTop";
    /** 获取-项上坐标 */
    get itemTop(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_ITEMTOP_NAME);
    }
    /** 设置-项上坐标 */
    set itemTop(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_ITEMTOP_NAME, value);
    }

    /** 映射的属性名称-数据源 */
    static PROPERTY_SOURCETYPE_NAME: string = "SourceType";
    /** 获取-数据源 */
    get sourceType(): emDataSourceType {
        return this.getProperty<emDataSourceType>(DataExportTemplateItem.PROPERTY_SOURCETYPE_NAME);
    }
    /** 设置-数据源 */
    set sourceType(value: emDataSourceType) {
        this.setProperty(DataExportTemplateItem.PROPERTY_SOURCETYPE_NAME, value);
    }

    /** 映射的属性名称-项字符串 */
    static PROPERTY_ITEMSTRING_NAME: string = "ItemString";
    /** 获取-项字符串 */
    get itemString(): string {
        return this.getProperty<string>(DataExportTemplateItem.PROPERTY_ITEMSTRING_NAME);
    }
    /** 设置-项字符串 */
    set itemString(value: string) {
        this.setProperty(DataExportTemplateItem.PROPERTY_ITEMSTRING_NAME, value);
    }

    /** 映射的属性名称-项是否可见 */
    static PROPERTY_ITEMVISIBLE_NAME: string = "ItemVisible";
    /** 获取-项是否可见 */
    get itemVisible(): emYesNo {
        return this.getProperty<emYesNo>(DataExportTemplateItem.PROPERTY_ITEMVISIBLE_NAME);
    }
    /** 设置-项是否可见 */
    set itemVisible(value: emYesNo) {
        this.setProperty(DataExportTemplateItem.PROPERTY_ITEMVISIBLE_NAME, value);
    }

    /** 映射的属性名称-项宽度 */
    static PROPERTY_WIDTH_NAME: string = "Width";
    /** 获取-项宽度 */
    get width(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_WIDTH_NAME);
    }
    /** 设置-项宽度 */
    set width(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_WIDTH_NAME, value);
    }

    /** 映射的属性名称-项高度 */
    static PROPERTY_HEIGHT_NAME: string = "Height";
    /** 获取-项高度 */
    get height(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_HEIGHT_NAME);
    }
    /** 设置-项高度 */
    set height(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_HEIGHT_NAME, value);
    }

    /** 映射的属性名称-左边距 */
    static PROPERTY_LEFTMARGIN_NAME: string = "LeftMargin";
    /** 获取-左边距 */
    get leftMargin(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_LEFTMARGIN_NAME);
    }
    /** 设置-左边距 */
    set leftMargin(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_LEFTMARGIN_NAME, value);
    }

    /** 映射的属性名称-右边距 */
    static PROPERTY_RIGHTMARGIN_NAME: string = "RightMargin";
    /** 获取-右边距 */
    get rightMargin(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_RIGHTMARGIN_NAME);
    }
    /** 设置-右边距 */
    set rightMargin(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_RIGHTMARGIN_NAME, value);
    }

    /** 映射的属性名称-上边距 */
    static PROPERTY_TOPMARGIN_NAME: string = "TopMargin";
    /** 获取-上边距 */
    get topMargin(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_TOPMARGIN_NAME);
    }
    /** 设置-上边距 */
    set topMargin(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_TOPMARGIN_NAME, value);
    }

    /** 映射的属性名称-下边距 */
    static PROPERTY_BOTTOMMARGIN_NAME: string = "BottomMargin";
    /** 获取-下边距 */
    get bottomMargin(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_BOTTOMMARGIN_NAME);
    }
    /** 设置-下边距 */
    set bottomMargin(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_BOTTOMMARGIN_NAME, value);
    }

    /** 映射的属性名称-左线长度 */
    static PROPERTY_LEFTLINE_NAME: string = "LeftLine";
    /** 获取-左线长度 */
    get leftLine(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_LEFTLINE_NAME);
    }
    /** 设置-左线长度 */
    set leftLine(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_LEFTLINE_NAME, value);
    }

    /** 映射的属性名称-右线长度 */
    static PROPERTY_RIGHTLINE_NAME: string = "RightLine";
    /** 获取-右线长度 */
    get rightLine(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_RIGHTLINE_NAME);
    }
    /** 设置-右线长度 */
    set rightLine(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_RIGHTLINE_NAME, value);
    }

    /** 映射的属性名称-上线长度 */
    static PROPERTY_TOPLINE_NAME: string = "TopLine";
    /** 获取-上线长度 */
    get topLine(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_TOPLINE_NAME);
    }
    /** 设置-上线长度 */
    set topLine(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_TOPLINE_NAME, value);
    }

    /** 映射的属性名称-下线长度 */
    static PROPERTY_BOTTOMLINE_NAME: string = "BottomLine";
    /** 获取-下线长度 */
    get bottomLine(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_BOTTOMLINE_NAME);
    }
    /** 设置-下线长度 */
    set bottomLine(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_BOTTOMLINE_NAME, value);
    }

    /** 映射的属性名称-字体名称 */
    static PROPERTY_FONTNAME_NAME: string = "FontName";
    /** 获取-字体名称 */
    get fontName(): string {
        return this.getProperty<string>(DataExportTemplateItem.PROPERTY_FONTNAME_NAME);
    }
    /** 设置-字体名称 */
    set fontName(value: string) {
        this.setProperty(DataExportTemplateItem.PROPERTY_FONTNAME_NAME, value);
    }

    /** 映射的属性名称-字体大小 */
    static PROPERTY_FONTSIZE_NAME: string = "FontSize";
    /** 获取-字体大小 */
    get fontSize(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_FONTSIZE_NAME);
    }
    /** 设置-字体大小 */
    set fontSize(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_FONTSIZE_NAME, value);
    }

    /** 映射的属性名称-文本样式 */
    static PROPERTY_TEXTSTYLE_NAME: string = "TextStyle";
    /** 获取-文本样式 */
    get textStyle(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_TEXTSTYLE_NAME);
    }
    /** 设置-文本样式 */
    set textStyle(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_TEXTSTYLE_NAME, value);
    }

    /** 映射的属性名称-水平对齐方式 */
    static PROPERTY_HORIZONTALJUSTIFICATION_NAME: string = "HorizontalJustification";
    /** 获取-水平对齐方式 */
    get horizontalJustification(): emHorizontalJustification {
        return this.getProperty<emHorizontalJustification>(DataExportTemplateItem.PROPERTY_HORIZONTALJUSTIFICATION_NAME);
    }
    /** 设置-水平对齐方式 */
    set horizontalJustification(value: emHorizontalJustification) {
        this.setProperty(DataExportTemplateItem.PROPERTY_HORIZONTALJUSTIFICATION_NAME, value);
    }

    /** 映射的属性名称-竖直对齐方式 */
    static PROPERTY_VERTICALJUSTIFICATION_NAME: string = "VerticalJustification";
    /** 获取-竖直对齐方式 */
    get verticalJustification(): emVerticalJustification {
        return this.getProperty<emVerticalJustification>(DataExportTemplateItem.PROPERTY_VERTICALJUSTIFICATION_NAME);
    }
    /** 设置-竖直对齐方式 */
    set verticalJustification(value: emVerticalJustification) {
        this.setProperty(DataExportTemplateItem.PROPERTY_VERTICALJUSTIFICATION_NAME, value);
    }

    /** 映射的属性名称-背景色-红 */
    static PROPERTY_BACKGROUND_RED_NAME: string = "Background_Red";
    /** 获取-背景色-红 */
    get background_Red(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_BACKGROUND_RED_NAME);
    }
    /** 设置-背景色-红 */
    set background_Red(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_BACKGROUND_RED_NAME, value);
    }

    /** 映射的属性名称-背景色-绿 */
    static PROPERTY_BACKGROUND_GREEN_NAME: string = "Background_Green";
    /** 获取-背景色-绿 */
    get background_Green(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_BACKGROUND_GREEN_NAME);
    }
    /** 设置-背景色-绿 */
    set background_Green(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_BACKGROUND_GREEN_NAME, value);
    }

    /** 映射的属性名称-背景色-蓝 */
    static PROPERTY_BACKGROUND_BLUE_NAME: string = "Background_Blue";
    /** 获取-背景色-蓝 */
    get background_Blue(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_BACKGROUND_BLUE_NAME);
    }
    /** 设置-背景色-蓝 */
    set background_Blue(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_BACKGROUND_BLUE_NAME, value);
    }

    /** 映射的属性名称-前景色-红 */
    static PROPERTY_FOREGROUND_RED_NAME: string = "Foreground_Red";
    /** 获取-前景色-红 */
    get foreground_Red(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_FOREGROUND_RED_NAME);
    }
    /** 设置-前景色-红 */
    set foreground_Red(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_FOREGROUND_RED_NAME, value);
    }

    /** 映射的属性名称-前景色-绿 */
    static PROPERTY_FOREGROUND_GREEN_NAME: string = "Foreground_Green";
    /** 获取-前景色-绿 */
    get foreground_Green(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_FOREGROUND_GREEN_NAME);
    }
    /** 设置-前景色-绿 */
    set foreground_Green(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_FOREGROUND_GREEN_NAME, value);
    }

    /** 映射的属性名称-前景色-蓝 */
    static PROPERTY_FOREGROUND_BLUE_NAME: string = "Foreground_Blue";
    /** 获取-前景色-蓝 */
    get foreground_Blue(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_FOREGROUND_BLUE_NAME);
    }
    /** 设置-前景色-蓝 */
    set foreground_Blue(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_FOREGROUND_BLUE_NAME, value);
    }

    /** 映射的属性名称-高亮显示色-红 */
    static PROPERTY_MARKER_RED_NAME: string = "Marker_Red";
    /** 获取-高亮显示色-红 */
    get marker_Red(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_MARKER_RED_NAME);
    }
    /** 设置-高亮显示色-红 */
    set marker_Red(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_MARKER_RED_NAME, value);
    }

    /** 映射的属性名称-高亮显示色-绿 */
    static PROPERTY_MARKER_GREEN_NAME: string = "Marker_Green";
    /** 获取-高亮显示色-绿 */
    get marker_Green(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_MARKER_GREEN_NAME);
    }
    /** 设置-高亮显示色-绿 */
    set marker_Green(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_MARKER_GREEN_NAME, value);
    }

    /** 映射的属性名称-高亮显示色-蓝 */
    static PROPERTY_MARKER_BLUE_NAME: string = "Marker_Blue";
    /** 获取-高亮显示色-蓝 */
    get marker_Blue(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_MARKER_BLUE_NAME);
    }
    /** 设置-高亮显示色-蓝 */
    set marker_Blue(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_MARKER_BLUE_NAME, value);
    }

    /** 映射的属性名称-框架色-红 */
    static PROPERTY_BORDER_RED_NAME: string = "Border_Red";
    /** 获取-框架色-红 */
    get border_Red(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_BORDER_RED_NAME);
    }
    /** 设置-框架色-红 */
    set border_Red(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_BORDER_RED_NAME, value);
    }

    /** 映射的属性名称-框架色-绿 */
    static PROPERTY_BORDER_GREEN_NAME: string = "Border_Green";
    /** 获取-框架色-绿 */
    get border_Green(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_BORDER_GREEN_NAME);
    }
    /** 设置-框架色-绿 */
    set border_Green(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_BORDER_GREEN_NAME, value);
    }

    /** 映射的属性名称-框架色-蓝 */
    static PROPERTY_BORDER_BLUE_NAME: string = "Border_Blue";
    /** 获取-框架色-蓝 */
    get border_Blue(): number {
        return this.getProperty<number>(DataExportTemplateItem.PROPERTY_BORDER_BLUE_NAME);
    }
    /** 设置-框架色-蓝 */
    set border_Blue(value: number) {
        this.setProperty(DataExportTemplateItem.PROPERTY_BORDER_BLUE_NAME, value);
    }

    /** 映射的属性名称-显示格式 */
    static PROPERTY_VALFORMAT_NAME: string = "ValFormat";
    /** 获取-显示格式 */
    get valFormat(): string {
        return this.getProperty<string>(DataExportTemplateItem.PROPERTY_VALFORMAT_NAME);
    }
    /** 设置-显示格式 */
    set valFormat(value: string) {
        this.setProperty(DataExportTemplateItem.PROPERTY_VALFORMAT_NAME, value);
    }


    /** 初始化数据 */
    protected init(): void {
    }
}

/** 数据导出模板-项 集合 */
export class DataExportTemplateItems extends BusinessObjects<DataExportTemplateItem, DataExportTemplate> implements IDataExportTemplateItems {

    /** 创建并添加子项 */
    create(): DataExportTemplateItem {
        let item: DataExportTemplateItem = new DataExportTemplateItem();
        this.add(item);
        return item;
    }
}
