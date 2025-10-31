/**
 * @license
 * Copyright Color-Coding Studio. All Rights Reserved.
 *
 * Use of this source code is governed by an Apache License, Version 2.0
 * that can be found in the LICENSE file at http://www.apache.org/licenses/LICENSE-2.0
 */
namespace importexport {
    export namespace bo {
        /** 导出日志 */
        export class ExportRecord extends ibas.BOSimple<ExportRecord> implements IExportRecord {
            /** 业务对象编码 */
            static BUSINESS_OBJECT_CODE: string = BO_CODE_EXPORTRECORD;
            /** 构造函数 */
            constructor() {
                super();
            }
            /** 映射的属性名称-编号 */
            static PROPERTY_OBJECTKEY_NAME: string = "ObjectKey";
            /** 获取-编号 */
            get objectKey(): number {
                return this.getProperty<number>(ExportRecord.PROPERTY_OBJECTKEY_NAME);
            }
            /** 设置-编号 */
            set objectKey(value: number) {
                this.setProperty(ExportRecord.PROPERTY_OBJECTKEY_NAME, value);
            }

            /** 映射的属性名称-类型 */
            static PROPERTY_OBJECTCODE_NAME: string = "ObjectCode";
            /** 获取-类型 */
            get objectCode(): string {
                return this.getProperty<string>(ExportRecord.PROPERTY_OBJECTCODE_NAME);
            }
            /** 设置-类型 */
            set objectCode(value: string) {
                this.setProperty(ExportRecord.PROPERTY_OBJECTCODE_NAME, value);
            }

            /** 映射的属性名称-实例号（版本） */
            static PROPERTY_LOGINST_NAME: string = "LogInst";
            /** 获取-实例号（版本） */
            get logInst(): number {
                return this.getProperty<number>(ExportRecord.PROPERTY_LOGINST_NAME);
            }
            /** 设置-实例号（版本） */
            set logInst(value: number) {
                this.setProperty(ExportRecord.PROPERTY_LOGINST_NAME, value);
            }

            /** 映射的属性名称-数据源 */
            static PROPERTY_DATASOURCE_NAME: string = "DataSource";
            /** 获取-数据源 */
            get dataSource(): string {
                return this.getProperty<string>(ExportRecord.PROPERTY_DATASOURCE_NAME);
            }
            /** 设置-数据源 */
            set dataSource(value: string) {
                this.setProperty(ExportRecord.PROPERTY_DATASOURCE_NAME, value);
            }

            /** 映射的属性名称-服务系列 */
            static PROPERTY_SERIES_NAME: string = "Series";
            /** 获取-服务系列 */
            get series(): number {
                return this.getProperty<number>(ExportRecord.PROPERTY_SERIES_NAME);
            }
            /** 设置-服务系列 */
            set series(value: number) {
                this.setProperty(ExportRecord.PROPERTY_SERIES_NAME, value);
            }

            /** 映射的属性名称-创建日期 */
            static PROPERTY_CREATEDATE_NAME: string = "CreateDate";
            /** 获取-创建日期 */
            get createDate(): Date {
                return this.getProperty<Date>(ExportRecord.PROPERTY_CREATEDATE_NAME);
            }
            /** 设置-创建日期 */
            set createDate(value: Date) {
                this.setProperty(ExportRecord.PROPERTY_CREATEDATE_NAME, value);
            }

            /** 映射的属性名称-创建时间 */
            static PROPERTY_CREATETIME_NAME: string = "CreateTime";
            /** 获取-创建时间 */
            get createTime(): number {
                return this.getProperty<number>(ExportRecord.PROPERTY_CREATETIME_NAME);
            }
            /** 设置-创建时间 */
            set createTime(value: number) {
                this.setProperty(ExportRecord.PROPERTY_CREATETIME_NAME, value);
            }

            /** 映射的属性名称-修改日期 */
            static PROPERTY_UPDATEDATE_NAME: string = "UpdateDate";
            /** 获取-修改日期 */
            get updateDate(): Date {
                return this.getProperty<Date>(ExportRecord.PROPERTY_UPDATEDATE_NAME);
            }
            /** 设置-修改日期 */
            set updateDate(value: Date) {
                this.setProperty(ExportRecord.PROPERTY_UPDATEDATE_NAME, value);
            }

            /** 映射的属性名称-修改时间 */
            static PROPERTY_UPDATETIME_NAME: string = "UpdateTime";
            /** 获取-修改时间 */
            get updateTime(): number {
                return this.getProperty<number>(ExportRecord.PROPERTY_UPDATETIME_NAME);
            }
            /** 设置-修改时间 */
            set updateTime(value: number) {
                this.setProperty(ExportRecord.PROPERTY_UPDATETIME_NAME, value);
            }

            /** 映射的属性名称-创建用户 */
            static PROPERTY_CREATEUSERSIGN_NAME: string = "CreateUserSign";
            /** 获取-创建用户 */
            get createUserSign(): number {
                return this.getProperty<number>(ExportRecord.PROPERTY_CREATEUSERSIGN_NAME);
            }
            /** 设置-创建用户 */
            set createUserSign(value: number) {
                this.setProperty(ExportRecord.PROPERTY_CREATEUSERSIGN_NAME, value);
            }

            /** 映射的属性名称-修改用户 */
            static PROPERTY_UPDATEUSERSIGN_NAME: string = "UpdateUserSign";
            /** 获取-修改用户 */
            get updateUserSign(): number {
                return this.getProperty<number>(ExportRecord.PROPERTY_UPDATEUSERSIGN_NAME);
            }
            /** 设置-修改用户 */
            set updateUserSign(value: number) {
                this.setProperty(ExportRecord.PROPERTY_UPDATEUSERSIGN_NAME, value);
            }

            /** 映射的属性名称-创建动作标识 */
            static PROPERTY_CREATEACTIONID_NAME: string = "CreateActionId";
            /** 获取-创建动作标识 */
            get createActionId(): string {
                return this.getProperty<string>(ExportRecord.PROPERTY_CREATEACTIONID_NAME);
            }
            /** 设置-创建动作标识 */
            set createActionId(value: string) {
                this.setProperty(ExportRecord.PROPERTY_CREATEACTIONID_NAME, value);
            }

            /** 映射的属性名称-更新动作标识 */
            static PROPERTY_UPDATEACTIONID_NAME: string = "UpdateActionId";
            /** 获取-更新动作标识 */
            get updateActionId(): string {
                return this.getProperty<string>(ExportRecord.PROPERTY_UPDATEACTIONID_NAME);
            }
            /** 设置-更新动作标识 */
            set updateActionId(value: string) {
                this.setProperty(ExportRecord.PROPERTY_UPDATEACTIONID_NAME, value);
            }

            /** 映射的属性名称-类型 */
            static PROPERTY_BOCODE_NAME: string = "BOCode";
            /** 获取-类型 */
            get boCode(): string {
                return this.getProperty<string>(ExportRecord.PROPERTY_BOCODE_NAME);
            }
            /** 设置-类型 */
            set boCode(value: string) {
                this.setProperty(ExportRecord.PROPERTY_BOCODE_NAME, value);
            }

            /** 映射的属性名称-主键值 */
            static PROPERTY_BOKEYS_NAME: string = "BOKeys";
            /** 获取-主键值 */
            get boKeys(): string {
                return this.getProperty<string>(ExportRecord.PROPERTY_BOKEYS_NAME);
            }
            /** 设置-主键值 */
            set boKeys(value: string) {
                this.setProperty(ExportRecord.PROPERTY_BOKEYS_NAME, value);
            }

            /** 映射的属性名称-实例号 */
            static PROPERTY_BOINST_NAME: string = "BOInst";
            /** 获取-实例号 */
            get boInst(): number {
                return this.getProperty<number>(ExportRecord.PROPERTY_BOINST_NAME);
            }
            /** 设置-实例号 */
            set boInst(value: number) {
                this.setProperty(ExportRecord.PROPERTY_BOINST_NAME, value);
            }

            /** 映射的属性名称-导出用户 */
            static PROPERTY_EXPORTUSER_NAME: string = "ExportUser";
            /** 获取-导出用户 */
            get exportUser(): number {
                return this.getProperty<number>(ExportRecord.PROPERTY_EXPORTUSER_NAME);
            }
            /** 设置-导出用户 */
            set exportUser(value: number) {
                this.setProperty(ExportRecord.PROPERTY_EXPORTUSER_NAME, value);
            }

            /** 映射的属性名称-导出日期 */
            static PROPERTY_EXPORTDATE_NAME: string = "ExportDate";
            /** 获取-导出日期 */
            get exportDate(): Date {
                return this.getProperty<Date>(ExportRecord.PROPERTY_EXPORTDATE_NAME);
            }
            /** 设置-导出日期 */
            set exportDate(value: Date) {
                this.setProperty(ExportRecord.PROPERTY_EXPORTDATE_NAME, value);
            }

            /** 映射的属性名称-导出时间 */
            static PROPERTY_EXPORTTIME_NAME: string = "ExportTime";
            /** 获取-导出时间 */
            get exportTime(): number {
                return this.getProperty<number>(ExportRecord.PROPERTY_EXPORTTIME_NAME);
            }
            /** 设置-导出时间 */
            set exportTime(value: number) {
                this.setProperty(ExportRecord.PROPERTY_EXPORTTIME_NAME, value);
            }

            /** 映射的属性名称-动机 */
            static PROPERTY_CAUSE_NAME: string = "Cause";
            /** 获取-动机 */
            get cause(): string {
                return this.getProperty<string>(ExportRecord.PROPERTY_CAUSE_NAME);
            }
            /** 设置-动机 */
            set cause(value: string) {
                this.setProperty(ExportRecord.PROPERTY_CAUSE_NAME, value);
            }

            /** 映射的属性名称-内容 */
            static PROPERTY_CONTENT_NAME: string = "Content";
            /** 获取-内容 */
            get content(): string {
                return this.getProperty<string>(ExportRecord.PROPERTY_CONTENT_NAME);
            }
            /** 设置-内容 */
            set content(value: string) {
                this.setProperty(ExportRecord.PROPERTY_CONTENT_NAME, value);
            }



            /** 初始化数据 */
            protected init(): void {
                this.objectCode = ibas.config.applyVariables(ExportRecord.BUSINESS_OBJECT_CODE);
            }
        }


    }
}
