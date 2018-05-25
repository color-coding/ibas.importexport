/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace bo {
        /** 导出模板 */
        export class ExportTemplate extends ibas.BOSimple<ExportTemplate> implements IExportTemplate {
            /** 业务对象编码 */
            static BUSINESS_OBJECT_CODE: string = BO_CODE_EXPORTTEMPLATE;
            /** 构造函数 */
            constructor() {
                super();
            }
            /** 映射的属性名称-编号 */
            static PROPERTY_OBJECTKEY_NAME: string = "ObjectKey";
            /** 获取-编号 */
            get objectKey(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_OBJECTKEY_NAME);
            }
            /** 设置-编号 */
            set objectKey(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_OBJECTKEY_NAME, value);
            }

            /** 映射的属性名称-类型 */
            static PROPERTY_OBJECTCODE_NAME: string = "ObjectCode";
            /** 获取-类型 */
            get objectCode(): string {
                return this.getProperty<string>(ExportTemplate.PROPERTY_OBJECTCODE_NAME);
            }
            /** 设置-类型 */
            set objectCode(value: string) {
                this.setProperty(ExportTemplate.PROPERTY_OBJECTCODE_NAME, value);
            }

            /** 映射的属性名称-实例号（版本） */
            static PROPERTY_LOGINST_NAME: string = "LogInst";
            /** 获取-实例号（版本） */
            get logInst(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_LOGINST_NAME);
            }
            /** 设置-实例号（版本） */
            set logInst(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_LOGINST_NAME, value);
            }

            /** 映射的属性名称-数据源 */
            static PROPERTY_DATASOURCE_NAME: string = "DataSource";
            /** 获取-数据源 */
            get dataSource(): string {
                return this.getProperty<string>(ExportTemplate.PROPERTY_DATASOURCE_NAME);
            }
            /** 设置-数据源 */
            set dataSource(value: string) {
                this.setProperty(ExportTemplate.PROPERTY_DATASOURCE_NAME, value);
            }

            /** 映射的属性名称-服务系列 */
            static PROPERTY_SERIES_NAME: string = "Series";
            /** 获取-服务系列 */
            get series(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_SERIES_NAME);
            }
            /** 设置-服务系列 */
            set series(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_SERIES_NAME, value);
            }

            /** 映射的属性名称-创建日期 */
            static PROPERTY_CREATEDATE_NAME: string = "CreateDate";
            /** 获取-创建日期 */
            get createDate(): Date {
                return this.getProperty<Date>(ExportTemplate.PROPERTY_CREATEDATE_NAME);
            }
            /** 设置-创建日期 */
            set createDate(value: Date) {
                this.setProperty(ExportTemplate.PROPERTY_CREATEDATE_NAME, value);
            }

            /** 映射的属性名称-创建时间 */
            static PROPERTY_CREATETIME_NAME: string = "CreateTime";
            /** 获取-创建时间 */
            get createTime(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_CREATETIME_NAME);
            }
            /** 设置-创建时间 */
            set createTime(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_CREATETIME_NAME, value);
            }

            /** 映射的属性名称-修改日期 */
            static PROPERTY_UPDATEDATE_NAME: string = "UpdateDate";
            /** 获取-修改日期 */
            get updateDate(): Date {
                return this.getProperty<Date>(ExportTemplate.PROPERTY_UPDATEDATE_NAME);
            }
            /** 设置-修改日期 */
            set updateDate(value: Date) {
                this.setProperty(ExportTemplate.PROPERTY_UPDATEDATE_NAME, value);
            }

            /** 映射的属性名称-修改时间 */
            static PROPERTY_UPDATETIME_NAME: string = "UpdateTime";
            /** 获取-修改时间 */
            get updateTime(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_UPDATETIME_NAME);
            }
            /** 设置-修改时间 */
            set updateTime(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_UPDATETIME_NAME, value);
            }

            /** 映射的属性名称-创建用户 */
            static PROPERTY_CREATEUSERSIGN_NAME: string = "CreateUserSign";
            /** 获取-创建用户 */
            get createUserSign(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_CREATEUSERSIGN_NAME);
            }
            /** 设置-创建用户 */
            set createUserSign(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_CREATEUSERSIGN_NAME, value);
            }

            /** 映射的属性名称-修改用户 */
            static PROPERTY_UPDATEUSERSIGN_NAME: string = "UpdateUserSign";
            /** 获取-修改用户 */
            get updateUserSign(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_UPDATEUSERSIGN_NAME);
            }
            /** 设置-修改用户 */
            set updateUserSign(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_UPDATEUSERSIGN_NAME, value);
            }

            /** 映射的属性名称-创建动作标识 */
            static PROPERTY_CREATEACTIONID_NAME: string = "CreateActionId";
            /** 获取-创建动作标识 */
            get createActionId(): string {
                return this.getProperty<string>(ExportTemplate.PROPERTY_CREATEACTIONID_NAME);
            }
            /** 设置-创建动作标识 */
            set createActionId(value: string) {
                this.setProperty(ExportTemplate.PROPERTY_CREATEACTIONID_NAME, value);
            }

            /** 映射的属性名称-更新动作标识 */
            static PROPERTY_UPDATEACTIONID_NAME: string = "UpdateActionId";
            /** 获取-更新动作标识 */
            get updateActionId(): string {
                return this.getProperty<string>(ExportTemplate.PROPERTY_UPDATEACTIONID_NAME);
            }
            /** 设置-更新动作标识 */
            set updateActionId(value: string) {
                this.setProperty(ExportTemplate.PROPERTY_UPDATEACTIONID_NAME, value);
            }

            /** 映射的属性名称-数据所有者 */
            static PROPERTY_DATAOWNER_NAME: string = "DataOwner";
            /** 获取-数据所有者 */
            get dataOwner(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_DATAOWNER_NAME);
            }
            /** 设置-数据所有者 */
            set dataOwner(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_DATAOWNER_NAME, value);
            }

            /** 映射的属性名称-团队成员 */
            static PROPERTY_TEAMMEMBERS_NAME: string = "TeamMembers";
            /** 获取-团队成员 */
            get teamMembers(): string {
                return this.getProperty<string>(ExportTemplate.PROPERTY_TEAMMEMBERS_NAME);
            }
            /** 设置-团队成员 */
            set teamMembers(value: string) {
                this.setProperty(ExportTemplate.PROPERTY_TEAMMEMBERS_NAME, value);
            }

            /** 映射的属性名称-数据所属组织 */
            static PROPERTY_ORGANIZATION_NAME: string = "Organization";
            /** 获取-数据所属组织 */
            get organization(): string {
                return this.getProperty<string>(ExportTemplate.PROPERTY_ORGANIZATION_NAME);
            }
            /** 设置-数据所属组织 */
            set organization(value: string) {
                this.setProperty(ExportTemplate.PROPERTY_ORGANIZATION_NAME, value);
            }

            /** 映射的属性名称-名称 */
            static PROPERTY_NAME_NAME: string = "Name";
            /** 获取-名称 */
            get name(): string {
                return this.getProperty<string>(ExportTemplate.PROPERTY_NAME_NAME);
            }
            /** 设置-名称 */
            set name(value: string) {
                this.setProperty(ExportTemplate.PROPERTY_NAME_NAME, value);
            }

            /** 映射的属性名称-已激活的 */
            static PROPERTY_ACTIVATED_NAME: string = "Activated";
            /** 获取-已激活的 */
            get activated(): ibas.emYesNo {
                return this.getProperty<ibas.emYesNo>(ExportTemplate.PROPERTY_ACTIVATED_NAME);
            }
            /** 设置-已激活的 */
            set activated(value: ibas.emYesNo) {
                this.setProperty(ExportTemplate.PROPERTY_ACTIVATED_NAME, value);
            }

            /** 映射的属性名称-语言 */
            static PROPERTY_LANGUAGE_NAME: string = "Language";
            /** 获取-语言 */
            get language(): string {
                return this.getProperty<string>(ExportTemplate.PROPERTY_LANGUAGE_NAME);
            }
            /** 设置-语言 */
            set language(value: string) {
                this.setProperty(ExportTemplate.PROPERTY_LANGUAGE_NAME, value);
            }

            /** 映射的属性名称-类型 */
            static PROPERTY_CATEGORY_NAME: string = "Category";
            /** 获取-类型 */
            get category(): string {
                return this.getProperty<string>(ExportTemplate.PROPERTY_CATEGORY_NAME);
            }
            /** 设置-类型 */
            set category(value: string) {
                this.setProperty(ExportTemplate.PROPERTY_CATEGORY_NAME, value);
            }

            /** 映射的属性名称-关联的业务对象 */
            static PROPERTY_BOCODE_NAME: string = "BOCode";
            /** 获取-关联的业务对象 */
            get boCode(): string {
                return this.getProperty<string>(ExportTemplate.PROPERTY_BOCODE_NAME);
            }
            /** 设置-关联的业务对象 */
            set boCode(value: string) {
                this.setProperty(ExportTemplate.PROPERTY_BOCODE_NAME, value);
            }

            /** 映射的属性名称-关联的应用 */
            static PROPERTY_APPLICATIONID_NAME: string = "ApplicationId";
            /** 获取-关联的应用 */
            get applicationId(): string {
                return this.getProperty<string>(ExportTemplate.PROPERTY_APPLICATIONID_NAME);
            }
            /** 设置-关联的应用 */
            set applicationId(value: string) {
                this.setProperty(ExportTemplate.PROPERTY_APPLICATIONID_NAME, value);
            }

            /** 映射的属性名称-注释 */
            static PROPERTY_NOTES_NAME: string = "Notes";
            /** 获取-注释 */
            get notes(): string {
                return this.getProperty<string>(ExportTemplate.PROPERTY_NOTES_NAME);
            }
            /** 设置-注释 */
            set notes(value: string) {
                this.setProperty(ExportTemplate.PROPERTY_NOTES_NAME, value);
            }

            /** 映射的属性名称-输出宽度 */
            static PROPERTY_WIDTH_NAME: string = "Width";
            /** 获取-输出宽度 */
            get width(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_WIDTH_NAME);
            }
            /** 设置-输出宽度 */
            set width(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_WIDTH_NAME, value);
            }

            /** 映射的属性名称-输出高度 */
            static PROPERTY_HEIGHT_NAME: string = "Height";
            /** 获取-输出高度 */
            get height(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_HEIGHT_NAME);
            }
            /** 设置-输出高度 */
            set height(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_HEIGHT_NAME, value);
            }

            /** 映射的属性名称-左边距 */
            static PROPERTY_MARGINLEFT_NAME: string = "MarginLeft";
            /** 获取-左边距 */
            get marginLeft(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_MARGINLEFT_NAME);
            }
            /** 设置-左边距 */
            set marginLeft(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_MARGINLEFT_NAME, value);
            }

            /** 映射的属性名称-右边距 */
            static PROPERTY_MARGINRIGHT_NAME: string = "MarginRight";
            /** 获取-右边距 */
            get marginRight(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_MARGINRIGHT_NAME);
            }
            /** 设置-右边距 */
            set marginRight(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_MARGINRIGHT_NAME, value);
            }

            /** 映射的属性名称-上边距 */
            static PROPERTY_MARGINTOP_NAME: string = "MarginTop";
            /** 获取-上边距 */
            get marginTop(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_MARGINTOP_NAME);
            }
            /** 设置-上边距 */
            set marginTop(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_MARGINTOP_NAME, value);
            }

            /** 映射的属性名称-下边距 */
            static PROPERTY_MARGINBOTTOM_NAME: string = "MarginBottom";
            /** 获取-下边距 */
            get marginBottom(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_MARGINBOTTOM_NAME);
            }
            /** 设置-下边距 */
            set marginBottom(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_MARGINBOTTOM_NAME, value);
            }

            /** 映射的属性名称-区域间隔 */
            static PROPERTY_MARGINAREA_NAME: string = "MarginArea";
            /** 获取-区域间隔 */
            get marginArea(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_MARGINAREA_NAME);
            }
            /** 设置-区域间隔 */
            set marginArea(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_MARGINAREA_NAME, value);
            }

            /** 映射的属性名称-页眉-左坐标 */
            static PROPERTY_PAGEHEADERLEFT_NAME: string = "PageHeaderLeft";
            /** 获取-页眉-左坐标 */
            get pageHeaderLeft(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_PAGEHEADERLEFT_NAME);
            }
            /** 设置-页眉-左坐标 */
            set pageHeaderLeft(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_PAGEHEADERLEFT_NAME, value);
            }

            /** 映射的属性名称-页眉-上坐标 */
            static PROPERTY_PAGEHEADERTOP_NAME: string = "PageHeaderTop";
            /** 获取-页眉-上坐标 */
            get pageHeaderTop(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_PAGEHEADERTOP_NAME);
            }
            /** 设置-页眉-上坐标 */
            set pageHeaderTop(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_PAGEHEADERTOP_NAME, value);
            }

            /** 映射的属性名称-页眉-宽度 */
            static PROPERTY_PAGEHEADERWIDTH_NAME: string = "PageHeaderWidth";
            /** 获取-页眉-宽度 */
            get pageHeaderWidth(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_PAGEHEADERWIDTH_NAME);
            }
            /** 设置-页眉-宽度 */
            set pageHeaderWidth(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_PAGEHEADERWIDTH_NAME, value);
            }

            /** 映射的属性名称-页眉-高度 */
            static PROPERTY_PAGEHEADERHEIGHT_NAME: string = "PageHeaderHeight";
            /** 获取-页眉-高度 */
            get pageHeaderHeight(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_PAGEHEADERHEIGHT_NAME);
            }
            /** 设置-页眉-高度 */
            set pageHeaderHeight(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_PAGEHEADERHEIGHT_NAME, value);
            }

            /** 映射的属性名称-开始部分-左坐标 */
            static PROPERTY_STARTSECTIONLEFT_NAME: string = "StartSectionLeft";
            /** 获取-开始部分-左坐标 */
            get startSectionLeft(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_STARTSECTIONLEFT_NAME);
            }
            /** 设置-开始部分-左坐标 */
            set startSectionLeft(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_STARTSECTIONLEFT_NAME, value);
            }

            /** 映射的属性名称-开始部分-上坐标 */
            static PROPERTY_STARTSECTIONTOP_NAME: string = "StartSectionTop";
            /** 获取-开始部分-上坐标 */
            get startSectionTop(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_STARTSECTIONTOP_NAME);
            }
            /** 设置-开始部分-上坐标 */
            set startSectionTop(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_STARTSECTIONTOP_NAME, value);
            }

            /** 映射的属性名称-开始部分-宽度 */
            static PROPERTY_STARTSECTIONWIDTH_NAME: string = "StartSectionWidth";
            /** 获取-开始部分-宽度 */
            get startSectionWidth(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_STARTSECTIONWIDTH_NAME);
            }
            /** 设置-开始部分-宽度 */
            set startSectionWidth(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_STARTSECTIONWIDTH_NAME, value);
            }

            /** 映射的属性名称-开始部分-高度 */
            static PROPERTY_STARTSECTIONHEIGHT_NAME: string = "StartSectionHeight";
            /** 获取-开始部分-高度 */
            get startSectionHeight(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_STARTSECTIONHEIGHT_NAME);
            }
            /** 设置-开始部分-高度 */
            set startSectionHeight(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_STARTSECTIONHEIGHT_NAME, value);
            }

            /** 映射的属性名称-重复区域头-左坐标 */
            static PROPERTY_REPETITIONHEADERLEFT_NAME: string = "RepetitionHeaderLeft";
            /** 获取-重复区域头-左坐标 */
            get repetitionHeaderLeft(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_REPETITIONHEADERLEFT_NAME);
            }
            /** 设置-重复区域头-左坐标 */
            set repetitionHeaderLeft(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONHEADERLEFT_NAME, value);
            }

            /** 映射的属性名称-重复区域头-上坐标 */
            static PROPERTY_REPETITIONHEADERTOP_NAME: string = "RepetitionHeaderTop";
            /** 获取-重复区域头-上坐标 */
            get repetitionHeaderTop(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_REPETITIONHEADERTOP_NAME);
            }
            /** 设置-重复区域头-上坐标 */
            set repetitionHeaderTop(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONHEADERTOP_NAME, value);
            }

            /** 映射的属性名称-重复区域头-宽度 */
            static PROPERTY_REPETITIONHEADERWIDTH_NAME: string = "RepetitionHeaderWidth";
            /** 获取-重复区域头-宽度 */
            get repetitionHeaderWidth(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_REPETITIONHEADERWIDTH_NAME);
            }
            /** 设置-重复区域头-宽度 */
            set repetitionHeaderWidth(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONHEADERWIDTH_NAME, value);
            }

            /** 映射的属性名称-重复区域头-高度 */
            static PROPERTY_REPETITIONHEADERHEIGHT_NAME: string = "RepetitionHeaderHeight";
            /** 获取-重复区域头-高度 */
            get repetitionHeaderHeight(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_REPETITIONHEADERHEIGHT_NAME);
            }
            /** 设置-重复区域头-高度 */
            set repetitionHeaderHeight(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONHEADERHEIGHT_NAME, value);
            }

            /** 映射的属性名称-重复区域-左坐标 */
            static PROPERTY_REPETITIONLEFT_NAME: string = "RepetitionLeft";
            /** 获取-重复区域-左坐标 */
            get repetitionLeft(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_REPETITIONLEFT_NAME);
            }
            /** 设置-重复区域-左坐标 */
            set repetitionLeft(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONLEFT_NAME, value);
            }

            /** 映射的属性名称-重复区域-上坐标 */
            static PROPERTY_REPETITIONTOP_NAME: string = "RepetitionTop";
            /** 获取-重复区域-上坐标 */
            get repetitionTop(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_REPETITIONTOP_NAME);
            }
            /** 设置-重复区域-上坐标 */
            set repetitionTop(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONTOP_NAME, value);
            }

            /** 映射的属性名称-重复区域-宽度 */
            static PROPERTY_REPETITIONWIDTH_NAME: string = "RepetitionWidth";
            /** 获取-重复区域-宽度 */
            get repetitionWidth(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_REPETITIONWIDTH_NAME);
            }
            /** 设置-重复区域-宽度 */
            set repetitionWidth(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONWIDTH_NAME, value);
            }

            /** 映射的属性名称-重复区域-高度 */
            static PROPERTY_REPETITIONHEIGHT_NAME: string = "RepetitionHeight";
            /** 获取-重复区域-高度 */
            get repetitionHeight(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_REPETITIONHEIGHT_NAME);
            }
            /** 设置-重复区域-高度 */
            set repetitionHeight(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONHEIGHT_NAME, value);
            }

            /** 映射的属性名称-重复区域脚-左坐标 */
            static PROPERTY_REPETITIONFOOTERLEFT_NAME: string = "RepetitionFooterLeft";
            /** 获取-重复区域脚-左坐标 */
            get repetitionFooterLeft(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_REPETITIONFOOTERLEFT_NAME);
            }
            /** 设置-重复区域脚-左坐标 */
            set repetitionFooterLeft(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONFOOTERLEFT_NAME, value);
            }

            /** 映射的属性名称-重复区域脚-上坐标 */
            static PROPERTY_REPETITIONFOOTERTOP_NAME: string = "RepetitionFooterTop";
            /** 获取-重复区域脚-上坐标 */
            get repetitionFooterTop(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_REPETITIONFOOTERTOP_NAME);
            }
            /** 设置-重复区域脚-上坐标 */
            set repetitionFooterTop(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONFOOTERTOP_NAME, value);
            }

            /** 映射的属性名称-重复区域脚-宽度 */
            static PROPERTY_REPETITIONFOOTERWIDTH_NAME: string = "RepetitionFooterWidth";
            /** 获取-重复区域脚-宽度 */
            get repetitionFooterWidth(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_REPETITIONFOOTERWIDTH_NAME);
            }
            /** 设置-重复区域脚-宽度 */
            set repetitionFooterWidth(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONFOOTERWIDTH_NAME, value);
            }

            /** 映射的属性名称-重复区域脚-高度 */
            static PROPERTY_REPETITIONFOOTERHEIGHT_NAME: string = "RepetitionFooterHeight";
            /** 获取-重复区域脚-高度 */
            get repetitionFooterHeight(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_REPETITIONFOOTERHEIGHT_NAME);
            }
            /** 设置-重复区域脚-高度 */
            set repetitionFooterHeight(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONFOOTERHEIGHT_NAME, value);
            }

            /** 映射的属性名称-结束部分-左坐标 */
            static PROPERTY_ENDSECTIONLEFT_NAME: string = "EndSectionLeft";
            /** 获取-结束部分-左坐标 */
            get endSectionLeft(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_ENDSECTIONLEFT_NAME);
            }
            /** 设置-结束部分-左坐标 */
            set endSectionLeft(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_ENDSECTIONLEFT_NAME, value);
            }

            /** 映射的属性名称-结束部分-上坐标 */
            static PROPERTY_ENDSECTIONTOP_NAME: string = "EndSectionTop";
            /** 获取-结束部分-上坐标 */
            get endSectionTop(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_ENDSECTIONTOP_NAME);
            }
            /** 设置-结束部分-上坐标 */
            set endSectionTop(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_ENDSECTIONTOP_NAME, value);
            }

            /** 映射的属性名称-结束部分-宽度 */
            static PROPERTY_ENDSECTIONWIDTH_NAME: string = "EndSectionWidth";
            /** 获取-结束部分-宽度 */
            get endSectionWidth(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_ENDSECTIONWIDTH_NAME);
            }
            /** 设置-结束部分-宽度 */
            set endSectionWidth(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_ENDSECTIONWIDTH_NAME, value);
            }

            /** 映射的属性名称-结束部分-高度 */
            static PROPERTY_ENDSECTIONHEIGHT_NAME: string = "EndSectionHeight";
            /** 获取-结束部分-高度 */
            get endSectionHeight(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_ENDSECTIONHEIGHT_NAME);
            }
            /** 设置-结束部分-高度 */
            set endSectionHeight(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_ENDSECTIONHEIGHT_NAME, value);
            }

            /** 映射的属性名称-页脚-左坐标 */
            static PROPERTY_PAGEFOOTERLEFT_NAME: string = "PageFooterLeft";
            /** 获取-页脚-左坐标 */
            get pageFooterLeft(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_PAGEFOOTERLEFT_NAME);
            }
            /** 设置-页脚-左坐标 */
            set pageFooterLeft(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_PAGEFOOTERLEFT_NAME, value);
            }

            /** 映射的属性名称-页脚-上坐标 */
            static PROPERTY_PAGEFOOTERTOP_NAME: string = "PageFooterTop";
            /** 获取-页脚-上坐标 */
            get pageFooterTop(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_PAGEFOOTERTOP_NAME);
            }
            /** 设置-页脚-上坐标 */
            set pageFooterTop(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_PAGEFOOTERTOP_NAME, value);
            }

            /** 映射的属性名称-页脚-宽度 */
            static PROPERTY_PAGEFOOTERWIDTH_NAME: string = "PageFooterWidth";
            /** 获取-页脚-宽度 */
            get pageFooterWidth(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_PAGEFOOTERWIDTH_NAME);
            }
            /** 设置-页脚-宽度 */
            set pageFooterWidth(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_PAGEFOOTERWIDTH_NAME, value);
            }

            /** 映射的属性名称-页脚-高度 */
            static PROPERTY_PAGEFOOTERHEIGHT_NAME: string = "PageFooterHeight";
            /** 获取-页脚-高度 */
            get pageFooterHeight(): number {
                return this.getProperty<number>(ExportTemplate.PROPERTY_PAGEFOOTERHEIGHT_NAME);
            }
            /** 设置-页脚-高度 */
            set pageFooterHeight(value: number) {
                this.setProperty(ExportTemplate.PROPERTY_PAGEFOOTERHEIGHT_NAME, value);
            }

            /** 映射的属性名称-导出模板-页眉 */
            static PROPERTY_PAGEHEADERS_NAME: string = "PageHeaders";
            /** 获取-导出模板-页眉 */
            get pageHeaders(): ExportTemplateItems {
                return this.getProperty<ExportTemplateItems>(ExportTemplate.PROPERTY_PAGEHEADERS_NAME);
            }
            /** 设置-导出模板-页眉 */
            set pageHeaders(value: ExportTemplateItems) {
                this.setProperty(ExportTemplate.PROPERTY_PAGEHEADERS_NAME, value);
            }

            /** 映射的属性名称-导出模板-开始区 */
            static PROPERTY_STARTSECTIONS_NAME: string = "StartSections";
            /** 获取-导出模板-开始区 */
            get startSections(): ExportTemplateItems {
                return this.getProperty<ExportTemplateItems>(ExportTemplate.PROPERTY_STARTSECTIONS_NAME);
            }
            /** 设置-导出模板-开始区 */
            set startSections(value: ExportTemplateItems) {
                this.setProperty(ExportTemplate.PROPERTY_STARTSECTIONS_NAME, value);
            }

            /** 映射的属性名称-导出模板-重复区头 */
            static PROPERTY_REPETITIONHEADERS_NAME: string = "RepetitionHeaders";
            /** 获取-导出模板-重复区头 */
            get repetitionHeaders(): ExportTemplateItems {
                return this.getProperty<ExportTemplateItems>(ExportTemplate.PROPERTY_REPETITIONHEADERS_NAME);
            }
            /** 设置-导出模板-重复区头 */
            set repetitionHeaders(value: ExportTemplateItems) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONHEADERS_NAME, value);
            }

            /** 映射的属性名称-导出模板-重复区 */
            static PROPERTY_REPETITIONS_NAME: string = "Repetitions";
            /** 获取-导出模板-重复区 */
            get repetitions(): ExportTemplateItems {
                return this.getProperty<ExportTemplateItems>(ExportTemplate.PROPERTY_REPETITIONS_NAME);
            }
            /** 设置-导出模板-重复区 */
            set repetitions(value: ExportTemplateItems) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONS_NAME, value);
            }

            /** 映射的属性名称-导出模板-重复区脚 */
            static PROPERTY_REPETITIONFOOTERS_NAME: string = "RepetitionFooters";
            /** 获取-导出模板-重复区脚 */
            get repetitionFooters(): ExportTemplateItems {
                return this.getProperty<ExportTemplateItems>(ExportTemplate.PROPERTY_REPETITIONFOOTERS_NAME);
            }
            /** 设置-导出模板-重复区脚 */
            set repetitionFooters(value: ExportTemplateItems) {
                this.setProperty(ExportTemplate.PROPERTY_REPETITIONFOOTERS_NAME, value);
            }

            /** 映射的属性名称-导出模板-结束区 */
            static PROPERTY_ENDSECTIONS_NAME: string = "EndSections";
            /** 获取-导出模板-结束区 */
            get endSections(): ExportTemplateItems {
                return this.getProperty<ExportTemplateItems>(ExportTemplate.PROPERTY_ENDSECTIONS_NAME);
            }
            /** 设置-导出模板-结束区 */
            set endSections(value: ExportTemplateItems) {
                this.setProperty(ExportTemplate.PROPERTY_ENDSECTIONS_NAME, value);
            }

            /** 映射的属性名称-导出模板-页脚区 */
            static PROPERTY_PAGEFOOTERS_NAME: string = "PageFooters";
            /** 获取-导出模板-页脚区 */
            get pageFooters(): ExportTemplateItems {
                return this.getProperty<ExportTemplateItems>(ExportTemplate.PROPERTY_PAGEFOOTERS_NAME);
            }
            /** 设置-导出模板-页脚区 */
            set pageFooters(value: ExportTemplateItems) {
                this.setProperty(ExportTemplate.PROPERTY_PAGEFOOTERS_NAME, value);
            }

            /** 初始化数据 */
            protected init(): void {
                this.pageHeaders = new ExportTemplateItems(this, emAreaType.PAGE_HEADER);
                this.startSections = new ExportTemplateItems(this, emAreaType.START_SECTION);
                this.repetitionHeaders = new ExportTemplateItems(this, emAreaType.REPETITION_HEADER);
                this.repetitions = new ExportTemplateItems(this, emAreaType.REPETITION);
                this.repetitionFooters = new ExportTemplateItems(this, emAreaType.REPETITION_FOOTER);
                this.endSections = new ExportTemplateItems(this, emAreaType.END_SECTION);
                this.pageFooters = new ExportTemplateItems(this, emAreaType.PAGE_FOOTER);
                this.objectCode = ibas.config.applyVariables(ExportTemplate.BUSINESS_OBJECT_CODE);
                this.activated = ibas.emYesNo.YES;
            }
        }

        /** 导出模板-项 集合 */
        export class ExportTemplateItems extends ibas.BusinessObjects<ExportTemplateItem, ExportTemplate> implements IExportTemplateItems {
            constructor(parent: ExportTemplate, areaType: emAreaType) {
                super(parent);
                this.areaType = areaType;
            }
            private areaType: emAreaType;
            /** 创建并添加子项 */
            create(): ExportTemplateItem {
                let item: ExportTemplateItem = new ExportTemplateItem();
                this.add(item);
                return item;
            }

            protected afterAdd(item: ExportTemplateItem): void {
                super.afterAdd(item);
                item.area = this.areaType;
            }
        }

        /** 导出模板-项 */
        export class ExportTemplateItem extends ibas.BOSimpleLine<ExportTemplateItem> implements IExportTemplateItem {
            /** 构造函数 */
            constructor() {
                super();
            }
            /** 映射的属性名称-编号 */
            static PROPERTY_OBJECTKEY_NAME: string = "ObjectKey";
            /** 获取-编号 */
            get objectKey(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_OBJECTKEY_NAME);
            }
            /** 设置-编号 */
            set objectKey(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_OBJECTKEY_NAME, value);
            }

            /** 映射的属性名称-类型 */
            static PROPERTY_OBJECTCODE_NAME: string = "ObjectCode";
            /** 获取-类型 */
            get objectCode(): string {
                return this.getProperty<string>(ExportTemplateItem.PROPERTY_OBJECTCODE_NAME);
            }
            /** 设置-类型 */
            set objectCode(value: string) {
                this.setProperty(ExportTemplateItem.PROPERTY_OBJECTCODE_NAME, value);
            }

            /** 映射的属性名称-行号 */
            static PROPERTY_LINEID_NAME: string = "LineId";
            /** 获取-行号 */
            get lineId(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_LINEID_NAME);
            }
            /** 设置-行号 */
            set lineId(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_LINEID_NAME, value);
            }

            /** 映射的属性名称-数据源 */
            static PROPERTY_DATASOURCE_NAME: string = "DataSource";
            /** 获取-数据源 */
            get dataSource(): string {
                return this.getProperty<string>(ExportTemplateItem.PROPERTY_DATASOURCE_NAME);
            }
            /** 设置-数据源 */
            set dataSource(value: string) {
                this.setProperty(ExportTemplateItem.PROPERTY_DATASOURCE_NAME, value);
            }

            /** 映射的属性名称-实例号（版本） */
            static PROPERTY_LOGINST_NAME: string = "LogInst";
            /** 获取-实例号（版本） */
            get logInst(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_LOGINST_NAME);
            }
            /** 设置-实例号（版本） */
            set logInst(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_LOGINST_NAME, value);
            }

            /** 映射的属性名称-创建日期 */
            static PROPERTY_CREATEDATE_NAME: string = "CreateDate";
            /** 获取-创建日期 */
            get createDate(): Date {
                return this.getProperty<Date>(ExportTemplateItem.PROPERTY_CREATEDATE_NAME);
            }
            /** 设置-创建日期 */
            set createDate(value: Date) {
                this.setProperty(ExportTemplateItem.PROPERTY_CREATEDATE_NAME, value);
            }

            /** 映射的属性名称-创建时间 */
            static PROPERTY_CREATETIME_NAME: string = "CreateTime";
            /** 获取-创建时间 */
            get createTime(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_CREATETIME_NAME);
            }
            /** 设置-创建时间 */
            set createTime(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_CREATETIME_NAME, value);
            }

            /** 映射的属性名称-修改日期 */
            static PROPERTY_UPDATEDATE_NAME: string = "UpdateDate";
            /** 获取-修改日期 */
            get updateDate(): Date {
                return this.getProperty<Date>(ExportTemplateItem.PROPERTY_UPDATEDATE_NAME);
            }
            /** 设置-修改日期 */
            set updateDate(value: Date) {
                this.setProperty(ExportTemplateItem.PROPERTY_UPDATEDATE_NAME, value);
            }

            /** 映射的属性名称-修改时间 */
            static PROPERTY_UPDATETIME_NAME: string = "UpdateTime";
            /** 获取-修改时间 */
            get updateTime(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_UPDATETIME_NAME);
            }
            /** 设置-修改时间 */
            set updateTime(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_UPDATETIME_NAME, value);
            }

            /** 映射的属性名称-创建用户 */
            static PROPERTY_CREATEUSERSIGN_NAME: string = "CreateUserSign";
            /** 获取-创建用户 */
            get createUserSign(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_CREATEUSERSIGN_NAME);
            }
            /** 设置-创建用户 */
            set createUserSign(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_CREATEUSERSIGN_NAME, value);
            }

            /** 映射的属性名称-修改用户 */
            static PROPERTY_UPDATEUSERSIGN_NAME: string = "UpdateUserSign";
            /** 获取-修改用户 */
            get updateUserSign(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_UPDATEUSERSIGN_NAME);
            }
            /** 设置-修改用户 */
            set updateUserSign(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_UPDATEUSERSIGN_NAME, value);
            }

            /** 映射的属性名称-创建动作标识 */
            static PROPERTY_CREATEACTIONID_NAME: string = "CreateActionId";
            /** 获取-创建动作标识 */
            get createActionId(): string {
                return this.getProperty<string>(ExportTemplateItem.PROPERTY_CREATEACTIONID_NAME);
            }
            /** 设置-创建动作标识 */
            set createActionId(value: string) {
                this.setProperty(ExportTemplateItem.PROPERTY_CREATEACTIONID_NAME, value);
            }

            /** 映射的属性名称-更新动作标识 */
            static PROPERTY_UPDATEACTIONID_NAME: string = "UpdateActionId";
            /** 获取-更新动作标识 */
            get updateActionId(): string {
                return this.getProperty<string>(ExportTemplateItem.PROPERTY_UPDATEACTIONID_NAME);
            }
            /** 设置-更新动作标识 */
            set updateActionId(value: string) {
                this.setProperty(ExportTemplateItem.PROPERTY_UPDATEACTIONID_NAME, value);
            }

            /** 映射的属性名称-区域 */
            static PROPERTY_AREA_NAME: string = "Area";
            /** 获取-区域 */
            get area(): emAreaType {
                return this.getProperty<emAreaType>(ExportTemplateItem.PROPERTY_AREA_NAME);
            }
            /** 设置-区域 */
            set area(value: emAreaType) {
                this.setProperty(ExportTemplateItem.PROPERTY_AREA_NAME, value);
            }

            /** 映射的属性名称-项标识 */
            static PROPERTY_ITEMID_NAME: string = "ItemID";
            /** 获取-项标识 */
            get itemID(): string {
                return this.getProperty<string>(ExportTemplateItem.PROPERTY_ITEMID_NAME);
            }
            /** 设置-项标识 */
            set itemID(value: string) {
                this.setProperty(ExportTemplateItem.PROPERTY_ITEMID_NAME, value);
            }

            /** 映射的属性名称-项左坐标 */
            static PROPERTY_ITEMLEFT_NAME: string = "ItemLeft";
            /** 获取-项左坐标 */
            get itemLeft(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_ITEMLEFT_NAME);
            }
            /** 设置-项左坐标 */
            set itemLeft(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_ITEMLEFT_NAME, value);
            }

            /** 映射的属性名称-项上坐标 */
            static PROPERTY_ITEMTOP_NAME: string = "ItemTop";
            /** 获取-项上坐标 */
            get itemTop(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_ITEMTOP_NAME);
            }
            /** 设置-项上坐标 */
            set itemTop(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_ITEMTOP_NAME, value);
            }

            /** 映射的属性名称-数据源 */
            static PROPERTY_SOURCETYPE_NAME: string = "SourceType";
            /** 获取-数据源 */
            get sourceType(): emDataSourceType {
                return this.getProperty<emDataSourceType>(ExportTemplateItem.PROPERTY_SOURCETYPE_NAME);
            }
            /** 设置-数据源 */
            set sourceType(value: emDataSourceType) {
                this.setProperty(ExportTemplateItem.PROPERTY_SOURCETYPE_NAME, value);
            }

            /** 映射的属性名称-项字符串 */
            static PROPERTY_ITEMSTRING_NAME: string = "ItemString";
            /** 获取-项字符串 */
            get itemString(): string {
                return this.getProperty<string>(ExportTemplateItem.PROPERTY_ITEMSTRING_NAME);
            }
            /** 设置-项字符串 */
            set itemString(value: string) {
                this.setProperty(ExportTemplateItem.PROPERTY_ITEMSTRING_NAME, value);
            }

            /** 映射的属性名称-显示格式 */
            static PROPERTY_VALUEFORMAT_NAME: string = "ValueFormat";
            /** 获取-显示格式 */
            get valueFormat(): string {
                return this.getProperty<string>(ExportTemplateItem.PROPERTY_VALUEFORMAT_NAME);
            }
            /** 设置-显示格式 */
            set valueFormat(value: string) {
                this.setProperty(ExportTemplateItem.PROPERTY_VALUEFORMAT_NAME, value);
            }

            /** 映射的属性名称-项是否可见 */
            static PROPERTY_ITEMVISIBLE_NAME: string = "ItemVisible";
            /** 获取-项是否可见 */
            get itemVisible(): ibas.emYesNo {
                return this.getProperty<ibas.emYesNo>(ExportTemplateItem.PROPERTY_ITEMVISIBLE_NAME);
            }
            /** 设置-项是否可见 */
            set itemVisible(value: ibas.emYesNo) {
                this.setProperty(ExportTemplateItem.PROPERTY_ITEMVISIBLE_NAME, value);
            }

            /** 映射的属性名称-项宽度 */
            static PROPERTY_ITEMWIDTH_NAME: string = "ItemWidth";
            /** 获取-项宽度 */
            get itemWidth(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_ITEMWIDTH_NAME);
            }
            /** 设置-项宽度 */
            set itemWidth(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_ITEMWIDTH_NAME, value);
            }

            /** 映射的属性名称-项高度 */
            static PROPERTY_ITEMHEIGHT_NAME: string = "ItemHeight";
            /** 获取-项高度 */
            get itemHeight(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_ITEMHEIGHT_NAME);
            }
            /** 设置-项高度 */
            set itemHeight(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_ITEMHEIGHT_NAME, value);
            }

            /** 映射的属性名称-左边距 */
            static PROPERTY_MARGINLEFT_NAME: string = "MarginLeft";
            /** 获取-左边距 */
            get marginLeft(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_MARGINLEFT_NAME);
            }
            /** 设置-左边距 */
            set marginLeft(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_MARGINLEFT_NAME, value);
            }

            /** 映射的属性名称-右边距 */
            static PROPERTY_MARGINRIGHT_NAME: string = "MarginRight";
            /** 获取-右边距 */
            get marginRight(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_MARGINRIGHT_NAME);
            }
            /** 设置-右边距 */
            set marginRight(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_MARGINRIGHT_NAME, value);
            }

            /** 映射的属性名称-上边距 */
            static PROPERTY_MARGINTOP_NAME: string = "MarginTop";
            /** 获取-上边距 */
            get marginTop(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_MARGINTOP_NAME);
            }
            /** 设置-上边距 */
            set marginTop(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_MARGINTOP_NAME, value);
            }

            /** 映射的属性名称-下边距 */
            static PROPERTY_MARGINBOTTOM_NAME: string = "MarginBottom";
            /** 获取-下边距 */
            get marginBottom(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_MARGINBOTTOM_NAME);
            }
            /** 设置-下边距 */
            set marginBottom(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_MARGINBOTTOM_NAME, value);
            }

            /** 映射的属性名称-左线长度 */
            static PROPERTY_LINELEFT_NAME: string = "LineLeft";
            /** 获取-左线长度 */
            get lineLeft(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_LINELEFT_NAME);
            }
            /** 设置-左线长度 */
            set lineLeft(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_LINELEFT_NAME, value);
            }

            /** 映射的属性名称-右线长度 */
            static PROPERTY_LINERIGHT_NAME: string = "LineRight";
            /** 获取-右线长度 */
            get lineRight(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_LINERIGHT_NAME);
            }
            /** 设置-右线长度 */
            set lineRight(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_LINERIGHT_NAME, value);
            }

            /** 映射的属性名称-上线长度 */
            static PROPERTY_LINETOP_NAME: string = "LineTop";
            /** 获取-上线长度 */
            get lineTop(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_LINETOP_NAME);
            }
            /** 设置-上线长度 */
            set lineTop(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_LINETOP_NAME, value);
            }

            /** 映射的属性名称-下线长度 */
            static PROPERTY_LINEBOTTOM_NAME: string = "LineBottom";
            /** 获取-下线长度 */
            get lineBottom(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_LINEBOTTOM_NAME);
            }
            /** 设置-下线长度 */
            set lineBottom(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_LINEBOTTOM_NAME, value);
            }

            /** 映射的属性名称-字体名称 */
            static PROPERTY_FONTNAME_NAME: string = "FontName";
            /** 获取-字体名称 */
            get fontName(): string {
                return this.getProperty<string>(ExportTemplateItem.PROPERTY_FONTNAME_NAME);
            }
            /** 设置-字体名称 */
            set fontName(value: string) {
                this.setProperty(ExportTemplateItem.PROPERTY_FONTNAME_NAME, value);
            }

            /** 映射的属性名称-字体大小 */
            static PROPERTY_FONTSIZE_NAME: string = "FontSize";
            /** 获取-字体大小 */
            get fontSize(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_FONTSIZE_NAME);
            }
            /** 设置-字体大小 */
            set fontSize(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_FONTSIZE_NAME, value);
            }

            /** 映射的属性名称-文本样式 */
            static PROPERTY_TEXTSTYLE_NAME: string = "TextStyle";
            /** 获取-文本样式 */
            get textStyle(): emTextStyle {
                return this.getProperty<emTextStyle>(ExportTemplateItem.PROPERTY_TEXTSTYLE_NAME);
            }
            /** 设置-文本样式 */
            set textStyle(value: emTextStyle) {
                this.setProperty(ExportTemplateItem.PROPERTY_TEXTSTYLE_NAME, value);
            }

            /** 映射的属性名称-水平对齐方式 */
            static PROPERTY_JUSTIFICATIONHORIZONTAL_NAME: string = "JustificationHorizontal";
            /** 获取-水平对齐方式 */
            get justificationHorizontal(): emJustificationHorizontal {
                return this.getProperty<emJustificationHorizontal>(ExportTemplateItem.PROPERTY_JUSTIFICATIONHORIZONTAL_NAME);
            }
            /** 设置-水平对齐方式 */
            set justificationHorizontal(value: emJustificationHorizontal) {
                this.setProperty(ExportTemplateItem.PROPERTY_JUSTIFICATIONHORIZONTAL_NAME, value);
            }

            /** 映射的属性名称-竖直对齐方式 */
            static PROPERTY_JUSTIFICATIONVERTICAL_NAME: string = "JustificationVertical";
            /** 获取-竖直对齐方式 */
            get justificationVertical(): emJustificationVertical {
                return this.getProperty<emJustificationVertical>(ExportTemplateItem.PROPERTY_JUSTIFICATIONVERTICAL_NAME);
            }
            /** 设置-竖直对齐方式 */
            set justificationVertical(value: emJustificationVertical) {
                this.setProperty(ExportTemplateItem.PROPERTY_JUSTIFICATIONVERTICAL_NAME, value);
            }

            /** 映射的属性名称-背景色-红 */
            static PROPERTY_BACKGROUNDRED_NAME: string = "BackgroundRed";
            /** 获取-背景色-红 */
            get backgroundRed(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_BACKGROUNDRED_NAME);
            }
            /** 设置-背景色-红 */
            set backgroundRed(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_BACKGROUNDRED_NAME, value);
            }

            /** 映射的属性名称-背景色-绿 */
            static PROPERTY_BACKGROUNDGREEN_NAME: string = "BackgroundGreen";
            /** 获取-背景色-绿 */
            get backgroundGreen(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_BACKGROUNDGREEN_NAME);
            }
            /** 设置-背景色-绿 */
            set backgroundGreen(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_BACKGROUNDGREEN_NAME, value);
            }

            /** 映射的属性名称-背景色-蓝 */
            static PROPERTY_BACKGROUNDBLUE_NAME: string = "BackgroundBlue";
            /** 获取-背景色-蓝 */
            get backgroundBlue(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_BACKGROUNDBLUE_NAME);
            }
            /** 设置-背景色-蓝 */
            set backgroundBlue(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_BACKGROUNDBLUE_NAME, value);
            }

            /** 映射的属性名称-前景色-红 */
            static PROPERTY_FOREGROUNDRED_NAME: string = "ForegroundRed";
            /** 获取-前景色-红 */
            get foregroundRed(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_FOREGROUNDRED_NAME);
            }
            /** 设置-前景色-红 */
            set foregroundRed(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_FOREGROUNDRED_NAME, value);
            }

            /** 映射的属性名称-前景色-绿 */
            static PROPERTY_FOREGROUNDGREEN_NAME: string = "ForegroundGreen";
            /** 获取-前景色-绿 */
            get foregroundGreen(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_FOREGROUNDGREEN_NAME);
            }
            /** 设置-前景色-绿 */
            set foregroundGreen(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_FOREGROUNDGREEN_NAME, value);
            }

            /** 映射的属性名称-前景色-蓝 */
            static PROPERTY_FOREGROUNDBLUE_NAME: string = "ForegroundBlue";
            /** 获取-前景色-蓝 */
            get foregroundBlue(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_FOREGROUNDBLUE_NAME);
            }
            /** 设置-前景色-蓝 */
            set foregroundBlue(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_FOREGROUNDBLUE_NAME, value);
            }

            /** 映射的属性名称-高亮显示色-红 */
            static PROPERTY_MARKERRED_NAME: string = "MarkerRed";
            /** 获取-高亮显示色-红 */
            get markerRed(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_MARKERRED_NAME);
            }
            /** 设置-高亮显示色-红 */
            set markerRed(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_MARKERRED_NAME, value);
            }

            /** 映射的属性名称-高亮显示色-绿 */
            static PROPERTY_MARKERGREEN_NAME: string = "MarkerGreen";
            /** 获取-高亮显示色-绿 */
            get markerGreen(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_MARKERGREEN_NAME);
            }
            /** 设置-高亮显示色-绿 */
            set markerGreen(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_MARKERGREEN_NAME, value);
            }

            /** 映射的属性名称-高亮显示色-蓝 */
            static PROPERTY_MARKERBLUE_NAME: string = "MarkerBlue";
            /** 获取-高亮显示色-蓝 */
            get markerBlue(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_MARKERBLUE_NAME);
            }
            /** 设置-高亮显示色-蓝 */
            set markerBlue(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_MARKERBLUE_NAME, value);
            }

            /** 映射的属性名称-框架色-红 */
            static PROPERTY_BORDERRED_NAME: string = "BorderRed";
            /** 获取-框架色-红 */
            get borderRed(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_BORDERRED_NAME);
            }
            /** 设置-框架色-红 */
            set borderRed(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_BORDERRED_NAME, value);
            }

            /** 映射的属性名称-框架色-绿 */
            static PROPERTY_BORDERGREEN_NAME: string = "BorderGreen";
            /** 获取-框架色-绿 */
            get borderGreen(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_BORDERGREEN_NAME);
            }
            /** 设置-框架色-绿 */
            set borderGreen(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_BORDERGREEN_NAME, value);
            }

            /** 映射的属性名称-框架色-蓝 */
            static PROPERTY_BORDERBLUE_NAME: string = "BorderBlue";
            /** 获取-框架色-蓝 */
            get borderBlue(): number {
                return this.getProperty<number>(ExportTemplateItem.PROPERTY_BORDERBLUE_NAME);
            }
            /** 设置-框架色-蓝 */
            set borderBlue(value: number) {
                this.setProperty(ExportTemplateItem.PROPERTY_BORDERBLUE_NAME, value);
            }

            /** 初始化数据 */
            protected init(): void {
                this.itemVisible = ibas.emYesNo.YES;
                this.justificationHorizontal = emJustificationHorizontal.CENTER;
                this.justificationVertical = emJustificationVertical.CENTER;
                this.textStyle = emTextStyle.REGULAR;
            }
        }

    }
}
