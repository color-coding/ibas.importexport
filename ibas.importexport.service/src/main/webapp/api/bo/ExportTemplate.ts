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
        export interface IExportTemplate extends ibas.IBOSimple {
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
            /** 名称 */
            name: string;
            /** 已激活的 */
            activated: ibas.emYesNo;
            /** 语言 */
            language: string;
            /** 类型 */
            category: string;
            /** 关联的业务对象 */
            boCode: string;
            /** 关联的应用 */
            applicationId: string;
            /** 注释 */
            notes: string;
            /** 输出宽度 */
            width: number;
            /** 输出高度 */
            height: number;
            /** 输出像素 */
            dpi: number;
            /** 左边距 */
            marginLeft: number;
            /** 右边距 */
            marginRight: number;
            /** 上边距 */
            marginTop: number;
            /** 下边距 */
            marginBottom: number;
            /** 区域间隔 */
            marginArea: number;
            /** 页眉-左坐标 */
            pageHeaderLeft: number;
            /** 页眉-上坐标 */
            pageHeaderTop: number;
            /** 页眉-宽度 */
            pageHeaderWidth: number;
            /** 页眉-高度 */
            pageHeaderHeight: number;
            /** 开始部分-左坐标 */
            startSectionLeft: number;
            /** 开始部分-上坐标 */
            startSectionTop: number;
            /** 开始部分-宽度 */
            startSectionWidth: number;
            /** 开始部分-高度 */
            startSectionHeight: number;
            /** 重复区域头-左坐标 */
            repetitionHeaderLeft: number;
            /** 重复区域头-上坐标 */
            repetitionHeaderTop: number;
            /** 重复区域头-宽度 */
            repetitionHeaderWidth: number;
            /** 重复区域头-高度 */
            repetitionHeaderHeight: number;
            /** 重复区域-左坐标 */
            repetitionLeft: number;
            /** 重复区域-上坐标 */
            repetitionTop: number;
            /** 重复区域-宽度 */
            repetitionWidth: number;
            /** 重复区域-高度 */
            repetitionHeight: number;
            /** 重复区域脚-左坐标 */
            repetitionFooterLeft: number;
            /** 重复区域脚-上坐标 */
            repetitionFooterTop: number;
            /** 重复区域脚-宽度 */
            repetitionFooterWidth: number;
            /** 重复区域脚-高度 */
            repetitionFooterHeight: number;
            /** 结束部分-左坐标 */
            endSectionLeft: number;
            /** 结束部分-上坐标 */
            endSectionTop: number;
            /** 结束部分-宽度 */
            endSectionWidth: number;
            /** 结束部分-高度 */
            endSectionHeight: number;
            /** 页脚-左坐标 */
            pageFooterLeft: number;
            /** 页脚-上坐标 */
            pageFooterTop: number;
            /** 页脚-宽度 */
            pageFooterWidth: number;
            /** 页脚-高度 */
            pageFooterHeight: number;

            /** 导出模板-页眉 */
            pageHeaders: IExportTemplateItems;
            /** 导出模板-开始区 */
            startSections: IExportTemplateItems;
            /** 导出模板-重复区头 */
            repetitionHeaders: IExportTemplateItems;
            /** 导出模板-重复区 */
            repetitions: IExportTemplateItems;
            /** 导出模板-重复区脚 */
            repetitionFooters: IExportTemplateItems;
            /** 导出模板-结束区 */
            endSections: IExportTemplateItems;
            /** 导出模板-页脚区 */
            pageFooters: IExportTemplateItems;

        }

        /** 导出模板-项 集合 */
        export interface IExportTemplateItems extends ibas.IBusinessObjects<IExportTemplateItem> {
            /** 创建并添加子项 */
            create(): IExportTemplateItem;
        }

        /** 导出模板-项 */
        export interface IExportTemplateItem extends ibas.IBOSimpleLine {
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
            /** 区域 */
            area: emAreaType;
            /** 项标识 */
            itemID: string;
            /** 项类型 */
            itemType: string;
            /** 项左坐标 */
            itemLeft: number;
            /** 项上坐标 */
            itemTop: number;
            /** 数据源 */
            sourceType: emDataSourceType;
            /** 项字符串 */
            itemString: string;
            /** 显示格式 */
            valueFormat: string;
            /** 项是否可见 */
            itemVisible: ibas.emYesNo;
            /** 项宽度 */
            itemWidth: number;
            /** 项高度 */
            itemHeight: number;
            /** 左边距 */
            marginLeft: number;
            /** 右边距 */
            marginRight: number;
            /** 上边距 */
            marginTop: number;
            /** 下边距 */
            marginBottom: number;
            /** 左线长度 */
            lineLeft: number;
            /** 右线长度 */
            lineRight: number;
            /** 上线长度 */
            lineTop: number;
            /** 下线长度 */
            lineBottom: number;
            /** 字体名称 */
            fontName: string;
            /** 字体大小 */
            fontSize: number;
            /** 文本样式 */
            textStyle: emTextStyle;
            /** 文本段落 */
            textSegment: emTextSegment;
            /** 水平对齐方式 */
            justificationHorizontal: emJustificationHorizontal;
            /** 竖直对齐方式 */
            justificationVertical: emJustificationVertical;
            /** 背景色-红 */
            backgroundRed: number;
            /** 背景色-绿 */
            backgroundGreen: number;
            /** 背景色-蓝 */
            backgroundBlue: number;
            /** 前景色-红 */
            foregroundRed: number;
            /** 前景色-绿 */
            foregroundGreen: number;
            /** 前景色-蓝 */
            foregroundBlue: number;
            /** 高亮显示色-红 */
            markerRed: number;
            /** 高亮显示色-绿 */
            markerGreen: number;
            /** 高亮显示色-蓝 */
            markerBlue: number;
            /** 框架色-红 */
            borderRed: number;
            /** 框架色-绿 */
            borderGreen: number;
            /** 框架色-蓝 */
            borderBlue: number;

        }


    }
}
