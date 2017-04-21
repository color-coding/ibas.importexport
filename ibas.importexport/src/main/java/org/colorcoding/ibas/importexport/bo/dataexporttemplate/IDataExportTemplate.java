package org.colorcoding.ibas.importexport.bo.dataexporttemplate;

import org.colorcoding.ibas.bobas.bo.*;
import org.colorcoding.ibas.bobas.data.*;
import org.colorcoding.ibas.importexport.data.*;

/**
* 数据导出模板 接口
* 
*/
public interface IDataExportTemplate extends IBOSimple {

    /**
    * 获取-编号
    * 
    * @return 值
    */
    Integer getObjectKey();

    /**
    * 设置-编号
    * 
    * @param value 值
    */
    void setObjectKey(Integer value);


    /**
    * 获取-类型
    * 
    * @return 值
    */
    String getObjectCode();

    /**
    * 设置-类型
    * 
    * @param value 值
    */
    void setObjectCode(String value);


    /**
    * 获取-实例号（版本）
    * 
    * @return 值
    */
    Integer getLogInst();

    /**
    * 设置-实例号（版本）
    * 
    * @param value 值
    */
    void setLogInst(Integer value);


    /**
    * 获取-数据源
    * 
    * @return 值
    */
    String getDataSource();

    /**
    * 设置-数据源
    * 
    * @param value 值
    */
    void setDataSource(String value);


    /**
    * 获取-服务系列
    * 
    * @return 值
    */
    Integer getSeries();

    /**
    * 设置-服务系列
    * 
    * @param value 值
    */
    void setSeries(Integer value);


    /**
    * 获取-创建日期
    * 
    * @return 值
    */
    DateTime getCreateDate();

    /**
    * 设置-创建日期
    * 
    * @param value 值
    */
    void setCreateDate(DateTime value);


    /**
    * 获取-创建时间
    * 
    * @return 值
    */
    Short getCreateTime();

    /**
    * 设置-创建时间
    * 
    * @param value 值
    */
    void setCreateTime(Short value);


    /**
    * 获取-修改日期
    * 
    * @return 值
    */
    DateTime getUpdateDate();

    /**
    * 设置-修改日期
    * 
    * @param value 值
    */
    void setUpdateDate(DateTime value);


    /**
    * 获取-修改时间
    * 
    * @return 值
    */
    Short getUpdateTime();

    /**
    * 设置-修改时间
    * 
    * @param value 值
    */
    void setUpdateTime(Short value);


    /**
    * 获取-创建用户
    * 
    * @return 值
    */
    Integer getCreateUserSign();

    /**
    * 设置-创建用户
    * 
    * @param value 值
    */
    void setCreateUserSign(Integer value);


    /**
    * 获取-修改用户
    * 
    * @return 值
    */
    Integer getUpdateUserSign();

    /**
    * 设置-修改用户
    * 
    * @param value 值
    */
    void setUpdateUserSign(Integer value);


    /**
    * 获取-创建动作标识
    * 
    * @return 值
    */
    String getCreateActionId();

    /**
    * 设置-创建动作标识
    * 
    * @param value 值
    */
    void setCreateActionId(String value);


    /**
    * 获取-更新动作标识
    * 
    * @return 值
    */
    String getUpdateActionId();

    /**
    * 设置-更新动作标识
    * 
    * @param value 值
    */
    void setUpdateActionId(String value);


    /**
    * 获取-数据所有者
    * 
    * @return 值
    */
    Integer getDataOwner();

    /**
    * 设置-数据所有者
    * 
    * @param value 值
    */
    void setDataOwner(Integer value);


    /**
    * 获取-团队成员
    * 
    * @return 值
    */
    String getTeamMembers();

    /**
    * 设置-团队成员
    * 
    * @param value 值
    */
    void setTeamMembers(String value);


    /**
    * 获取-数据所属组织
    * 
    * @return 值
    */
    String getOrganization();

    /**
    * 设置-数据所属组织
    * 
    * @param value 值
    */
    void setOrganization(String value);


    /**
    * 获取-审批状态
    * 
    * @return 值
    */
    emApprovalStatus getApprovalStatus();

    /**
    * 设置-审批状态
    * 
    * @param value 值
    */
    void setApprovalStatus(emApprovalStatus value);


    /**
    * 获取-参考1
    * 
    * @return 值
    */
    String getReference1();

    /**
    * 设置-参考1
    * 
    * @param value 值
    */
    void setReference1(String value);


    /**
    * 获取-参考2
    * 
    * @return 值
    */
    String getReference2();

    /**
    * 设置-参考2
    * 
    * @param value 值
    */
    void setReference2(String value);


    /**
    * 获取-备注
    * 
    * @return 值
    */
    String getRemarks();

    /**
    * 设置-备注
    * 
    * @param value 值
    */
    void setRemarks(String value);


    /**
    * 获取-编码
    * 
    * @return 值
    */
    String getCode();

    /**
    * 设置-编码
    * 
    * @param value 值
    */
    void setCode(String value);


    /**
    * 获取-名称
    * 
    * @return 值
    */
    String getName();

    /**
    * 设置-名称
    * 
    * @param value 值
    */
    void setName(String value);


    /**
    * 获取-注释
    * 
    * @return 值
    */
    String getNotes();

    /**
    * 设置-注释
    * 
    * @param value 值
    */
    void setNotes(String value);


    /**
    * 获取-输出宽度
    * 
    * @return 值
    */
    Integer getWidth();

    /**
    * 设置-输出宽度
    * 
    * @param value 值
    */
    void setWidth(Integer value);


    /**
    * 获取-输出高度
    * 
    * @return 值
    */
    Integer getHeight();

    /**
    * 设置-输出高度
    * 
    * @param value 值
    */
    void setHeight(Integer value);


    /**
    * 获取-左边距
    * 
    * @return 值
    */
    Integer getLeftMargin();

    /**
    * 设置-左边距
    * 
    * @param value 值
    */
    void setLeftMargin(Integer value);


    /**
    * 获取-右边距
    * 
    * @return 值
    */
    Integer getRightMargin();

    /**
    * 设置-右边距
    * 
    * @param value 值
    */
    void setRightMargin(Integer value);


    /**
    * 获取-上边距
    * 
    * @return 值
    */
    Integer getTopMargin();

    /**
    * 设置-上边距
    * 
    * @param value 值
    */
    void setTopMargin(Integer value);


    /**
    * 获取-下边距
    * 
    * @return 值
    */
    Integer getBottomMargin();

    /**
    * 设置-下边距
    * 
    * @param value 值
    */
    void setBottomMargin(Integer value);


    /**
    * 获取-是否可以修改
    * 
    * @return 值
    */
    emYesNo getCanChange();

    /**
    * 设置-是否可以修改
    * 
    * @param value 值
    */
    void setCanChange(emYesNo value);


    /**
    * 获取-纸张大小
    * 
    * @return 值
    */
    emPaperSize getPaperSize();

    /**
    * 设置-纸张大小
    * 
    * @param value 值
    */
    void setPaperSize(emPaperSize value);


    /**
    * 获取-语言编码
    * 
    * @return 值
    */
    String getLanguageCode();

    /**
    * 设置-语言编码
    * 
    * @param value 值
    */
    void setLanguageCode(String value);


    /**
    * 获取-类型
    * 
    * @return 值
    */
    String getCategory();

    /**
    * 设置-类型
    * 
    * @param value 值
    */
    void setCategory(String value);


    /**
    * 获取-页眉-左坐标
    * 
    * @return 值
    */
    Integer getPageHeaderLeft();

    /**
    * 设置-页眉-左坐标
    * 
    * @param value 值
    */
    void setPageHeaderLeft(Integer value);


    /**
    * 获取-页眉-上坐标
    * 
    * @return 值
    */
    Integer getPageHeaderTop();

    /**
    * 设置-页眉-上坐标
    * 
    * @param value 值
    */
    void setPageHeaderTop(Integer value);


    /**
    * 获取-页眉-宽度
    * 
    * @return 值
    */
    Integer getPageHeaderWidth();

    /**
    * 设置-页眉-宽度
    * 
    * @param value 值
    */
    void setPageHeaderWidth(Integer value);


    /**
    * 获取-页眉-高度
    * 
    * @return 值
    */
    Integer getPageHeaderHeight();

    /**
    * 设置-页眉-高度
    * 
    * @param value 值
    */
    void setPageHeaderHeight(Integer value);


    /**
    * 获取-开始部分-左坐标
    * 
    * @return 值
    */
    Integer getStartOfSectionLeft();

    /**
    * 设置-开始部分-左坐标
    * 
    * @param value 值
    */
    void setStartOfSectionLeft(Integer value);


    /**
    * 获取-开始部分-上坐标
    * 
    * @return 值
    */
    Integer getStartOfSectionTop();

    /**
    * 设置-开始部分-上坐标
    * 
    * @param value 值
    */
    void setStartOfSectionTop(Integer value);


    /**
    * 获取-开始部分-宽度
    * 
    * @return 值
    */
    Integer getStartOfSectionWidth();

    /**
    * 设置-开始部分-宽度
    * 
    * @param value 值
    */
    void setStartOfSectionWidth(Integer value);


    /**
    * 获取-开始部分-高度
    * 
    * @return 值
    */
    Integer getStartOfSectionHeight();

    /**
    * 设置-开始部分-高度
    * 
    * @param value 值
    */
    void setStartOfSectionHeight(Integer value);


    /**
    * 获取-重复区域头-左坐标
    * 
    * @return 值
    */
    Integer getRepetitiveAreaHeaderLeft();

    /**
    * 设置-重复区域头-左坐标
    * 
    * @param value 值
    */
    void setRepetitiveAreaHeaderLeft(Integer value);


    /**
    * 获取-重复区域头-上坐标
    * 
    * @return 值
    */
    Integer getRepetitiveAreaHeaderTop();

    /**
    * 设置-重复区域头-上坐标
    * 
    * @param value 值
    */
    void setRepetitiveAreaHeaderTop(Integer value);


    /**
    * 获取-重复区域头-宽度
    * 
    * @return 值
    */
    Integer getRepetitiveAreaHeaderWidth();

    /**
    * 设置-重复区域头-宽度
    * 
    * @param value 值
    */
    void setRepetitiveAreaHeaderWidth(Integer value);


    /**
    * 获取-重复区域头-高度
    * 
    * @return 值
    */
    Integer getRepetitiveAreaHeaderHeight();

    /**
    * 设置-重复区域头-高度
    * 
    * @param value 值
    */
    void setRepetitiveAreaHeaderHeight(Integer value);


    /**
    * 获取-重复区域-左坐标
    * 
    * @return 值
    */
    Integer getRepetitiveAreaLeft();

    /**
    * 设置-重复区域-左坐标
    * 
    * @param value 值
    */
    void setRepetitiveAreaLeft(Integer value);


    /**
    * 获取-重复区域-上坐标
    * 
    * @return 值
    */
    Integer getRepetitiveAreaTop();

    /**
    * 设置-重复区域-上坐标
    * 
    * @param value 值
    */
    void setRepetitiveAreaTop(Integer value);


    /**
    * 获取-重复区域-宽度
    * 
    * @return 值
    */
    Integer getRepetitiveAreaWidth();

    /**
    * 设置-重复区域-宽度
    * 
    * @param value 值
    */
    void setRepetitiveAreaWidth(Integer value);


    /**
    * 获取-重复区域-高度
    * 
    * @return 值
    */
    Integer getRepetitiveAreaHeight();

    /**
    * 设置-重复区域-高度
    * 
    * @param value 值
    */
    void setRepetitiveAreaHeight(Integer value);


    /**
    * 获取-重复区域脚-左坐标
    * 
    * @return 值
    */
    Integer getRepetitiveAreaFooterLeft();

    /**
    * 设置-重复区域脚-左坐标
    * 
    * @param value 值
    */
    void setRepetitiveAreaFooterLeft(Integer value);


    /**
    * 获取-重复区域脚-上坐标
    * 
    * @return 值
    */
    Integer getRepetitiveAreaFooterTop();

    /**
    * 设置-重复区域脚-上坐标
    * 
    * @param value 值
    */
    void setRepetitiveAreaFooterTop(Integer value);


    /**
    * 获取-重复区域脚-宽度
    * 
    * @return 值
    */
    Integer getRepetitiveAreaFooterWidth();

    /**
    * 设置-重复区域脚-宽度
    * 
    * @param value 值
    */
    void setRepetitiveAreaFooterWidth(Integer value);


    /**
    * 获取-重复区域脚-高度
    * 
    * @return 值
    */
    Integer getRepetitiveAreaFooterHeight();

    /**
    * 设置-重复区域脚-高度
    * 
    * @param value 值
    */
    void setRepetitiveAreaFooterHeight(Integer value);


    /**
    * 获取-结束部分-左坐标
    * 
    * @return 值
    */
    Integer getEndOfSectionLeft();

    /**
    * 设置-结束部分-左坐标
    * 
    * @param value 值
    */
    void setEndOfSectionLeft(Integer value);


    /**
    * 获取-结束部分-上坐标
    * 
    * @return 值
    */
    Integer getEndOfSectionTop();

    /**
    * 设置-结束部分-上坐标
    * 
    * @param value 值
    */
    void setEndOfSectionTop(Integer value);


    /**
    * 获取-结束部分-宽度
    * 
    * @return 值
    */
    Integer getEndOfSectionWidth();

    /**
    * 设置-结束部分-宽度
    * 
    * @param value 值
    */
    void setEndOfSectionWidth(Integer value);


    /**
    * 获取-结束部分-高度
    * 
    * @return 值
    */
    Integer getEndOfSectionHeight();

    /**
    * 设置-结束部分-高度
    * 
    * @param value 值
    */
    void setEndOfSectionHeight(Integer value);


    /**
    * 获取-页脚-左坐标
    * 
    * @return 值
    */
    Integer getPageFooterLeft();

    /**
    * 设置-页脚-左坐标
    * 
    * @param value 值
    */
    void setPageFooterLeft(Integer value);


    /**
    * 获取-页脚-上坐标
    * 
    * @return 值
    */
    Integer getPageFooterTop();

    /**
    * 设置-页脚-上坐标
    * 
    * @param value 值
    */
    void setPageFooterTop(Integer value);


    /**
    * 获取-页脚-宽度
    * 
    * @return 值
    */
    Integer getPageFooterWidth();

    /**
    * 设置-页脚-宽度
    * 
    * @param value 值
    */
    void setPageFooterWidth(Integer value);


    /**
    * 获取-页脚-高度
    * 
    * @return 值
    */
    Integer getPageFooterHeight();

    /**
    * 设置-页脚-高度
    * 
    * @param value 值
    */
    void setPageFooterHeight(Integer value);


    /**
    * 获取-区域间隔
    * 
    * @return 值
    */
    Integer getAreaMargin();

    /**
    * 设置-区域间隔
    * 
    * @param value 值
    */
    void setAreaMargin(Integer value);


    /**
    * 获取-业务对象编码
    * 
    * @return 值
    */
    String getBOCode();

    /**
    * 设置-业务对象编码
    * 
    * @param value 值
    */
    void setBOCode(String value);


    /**
    * 获取-平台简码
    * 
    * @return 值
    */
    String getPlantform();

    /**
    * 设置-平台简码
    * 
    * @param value 值
    */
    void setPlantform(String value);


    /**
    * 获取-输出份数
    * 
    * @return 值
    */
    Integer getCopyNumber();

    /**
    * 设置-输出份数
    * 
    * @param value 值
    */
    void setCopyNumber(Integer value);



     /**
    * 获取-数据导出模板-项集合
    * 
    * @return 值
    */
    IDataExportTemplateItems getDataExportTemplateItems();

    /**
    * 设置-数据导出模板-项集合
    * 
    * @param value 值
    */
    void setDataExportTemplateItems(IDataExportTemplateItems value);


}
