package org.colorcoding.ibas.importexport.bo.exporttemplate;

import org.colorcoding.ibas.bobas.bo.IBOSimple;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.emYesNo;

/**
 * 导出模板 接口
 * 
 */
public interface IExportTemplate extends IBOSimple {

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
	 * 获取-已激活的
	 * 
	 * @return 值
	 */
	emYesNo getActivated();

	/**
	 * 设置-已激活的
	 * 
	 * @param value 值
	 */
	void setActivated(emYesNo value);

	/**
	 * 获取-语言
	 * 
	 * @return 值
	 */
	String getLanguage();

	/**
	 * 设置-语言
	 * 
	 * @param value 值
	 */
	void setLanguage(String value);

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
	 * 获取-关联的业务对象
	 * 
	 * @return 值
	 */
	String getBOCode();

	/**
	 * 设置-关联的业务对象
	 * 
	 * @param value 值
	 */
	void setBOCode(String value);

	/**
	 * 获取-关联的应用
	 * 
	 * @return 值
	 */
	String getApplicationId();

	/**
	 * 设置-关联的应用
	 * 
	 * @param value 值
	 */
	void setApplicationId(String value);

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
	 * 获取-输出像素
	 * 
	 * @return 值
	 */
	Integer getDpi();

	/**
	 * 设置-输出像素
	 * 
	 * @param value 值
	 */
	void setDpi(Integer value);

	/**
	 * 获取-左边距
	 * 
	 * @return 值
	 */
	Integer getMarginLeft();

	/**
	 * 设置-左边距
	 * 
	 * @param value 值
	 */
	void setMarginLeft(Integer value);

	/**
	 * 获取-右边距
	 * 
	 * @return 值
	 */
	Integer getMarginRight();

	/**
	 * 设置-右边距
	 * 
	 * @param value 值
	 */
	void setMarginRight(Integer value);

	/**
	 * 获取-上边距
	 * 
	 * @return 值
	 */
	Integer getMarginTop();

	/**
	 * 设置-上边距
	 * 
	 * @param value 值
	 */
	void setMarginTop(Integer value);

	/**
	 * 获取-下边距
	 * 
	 * @return 值
	 */
	Integer getMarginBottom();

	/**
	 * 设置-下边距
	 * 
	 * @param value 值
	 */
	void setMarginBottom(Integer value);

	/**
	 * 获取-区域间隔
	 * 
	 * @return 值
	 */
	Integer getMarginArea();

	/**
	 * 设置-区域间隔
	 * 
	 * @param value 值
	 */
	void setMarginArea(Integer value);

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
	Integer getStartSectionLeft();

	/**
	 * 设置-开始部分-左坐标
	 * 
	 * @param value 值
	 */
	void setStartSectionLeft(Integer value);

	/**
	 * 获取-开始部分-上坐标
	 * 
	 * @return 值
	 */
	Integer getStartSectionTop();

	/**
	 * 设置-开始部分-上坐标
	 * 
	 * @param value 值
	 */
	void setStartSectionTop(Integer value);

	/**
	 * 获取-开始部分-宽度
	 * 
	 * @return 值
	 */
	Integer getStartSectionWidth();

	/**
	 * 设置-开始部分-宽度
	 * 
	 * @param value 值
	 */
	void setStartSectionWidth(Integer value);

	/**
	 * 获取-开始部分-高度
	 * 
	 * @return 值
	 */
	Integer getStartSectionHeight();

	/**
	 * 设置-开始部分-高度
	 * 
	 * @param value 值
	 */
	void setStartSectionHeight(Integer value);

	/**
	 * 获取-重复区域头-左坐标
	 * 
	 * @return 值
	 */
	Integer getRepetitionHeaderLeft();

	/**
	 * 设置-重复区域头-左坐标
	 * 
	 * @param value 值
	 */
	void setRepetitionHeaderLeft(Integer value);

	/**
	 * 获取-重复区域头-上坐标
	 * 
	 * @return 值
	 */
	Integer getRepetitionHeaderTop();

	/**
	 * 设置-重复区域头-上坐标
	 * 
	 * @param value 值
	 */
	void setRepetitionHeaderTop(Integer value);

	/**
	 * 获取-重复区域头-宽度
	 * 
	 * @return 值
	 */
	Integer getRepetitionHeaderWidth();

	/**
	 * 设置-重复区域头-宽度
	 * 
	 * @param value 值
	 */
	void setRepetitionHeaderWidth(Integer value);

	/**
	 * 获取-重复区域头-高度
	 * 
	 * @return 值
	 */
	Integer getRepetitionHeaderHeight();

	/**
	 * 设置-重复区域头-高度
	 * 
	 * @param value 值
	 */
	void setRepetitionHeaderHeight(Integer value);

	/**
	 * 获取-重复区域-左坐标
	 * 
	 * @return 值
	 */
	Integer getRepetitionLeft();

	/**
	 * 设置-重复区域-左坐标
	 * 
	 * @param value 值
	 */
	void setRepetitionLeft(Integer value);

	/**
	 * 获取-重复区域-上坐标
	 * 
	 * @return 值
	 */
	Integer getRepetitionTop();

	/**
	 * 设置-重复区域-上坐标
	 * 
	 * @param value 值
	 */
	void setRepetitionTop(Integer value);

	/**
	 * 获取-重复区域-宽度
	 * 
	 * @return 值
	 */
	Integer getRepetitionWidth();

	/**
	 * 设置-重复区域-宽度
	 * 
	 * @param value 值
	 */
	void setRepetitionWidth(Integer value);

	/**
	 * 获取-重复区域-高度
	 * 
	 * @return 值
	 */
	Integer getRepetitionHeight();

	/**
	 * 设置-重复区域-高度
	 * 
	 * @param value 值
	 */
	void setRepetitionHeight(Integer value);

	/**
	 * 获取-重复区域脚-左坐标
	 * 
	 * @return 值
	 */
	Integer getRepetitionFooterLeft();

	/**
	 * 设置-重复区域脚-左坐标
	 * 
	 * @param value 值
	 */
	void setRepetitionFooterLeft(Integer value);

	/**
	 * 获取-重复区域脚-上坐标
	 * 
	 * @return 值
	 */
	Integer getRepetitionFooterTop();

	/**
	 * 设置-重复区域脚-上坐标
	 * 
	 * @param value 值
	 */
	void setRepetitionFooterTop(Integer value);

	/**
	 * 获取-重复区域脚-宽度
	 * 
	 * @return 值
	 */
	Integer getRepetitionFooterWidth();

	/**
	 * 设置-重复区域脚-宽度
	 * 
	 * @param value 值
	 */
	void setRepetitionFooterWidth(Integer value);

	/**
	 * 获取-重复区域脚-高度
	 * 
	 * @return 值
	 */
	Integer getRepetitionFooterHeight();

	/**
	 * 设置-重复区域脚-高度
	 * 
	 * @param value 值
	 */
	void setRepetitionFooterHeight(Integer value);

	/**
	 * 获取-结束部分-左坐标
	 * 
	 * @return 值
	 */
	Integer getEndSectionLeft();

	/**
	 * 设置-结束部分-左坐标
	 * 
	 * @param value 值
	 */
	void setEndSectionLeft(Integer value);

	/**
	 * 获取-结束部分-上坐标
	 * 
	 * @return 值
	 */
	Integer getEndSectionTop();

	/**
	 * 设置-结束部分-上坐标
	 * 
	 * @param value 值
	 */
	void setEndSectionTop(Integer value);

	/**
	 * 获取-结束部分-宽度
	 * 
	 * @return 值
	 */
	Integer getEndSectionWidth();

	/**
	 * 设置-结束部分-宽度
	 * 
	 * @param value 值
	 */
	void setEndSectionWidth(Integer value);

	/**
	 * 获取-结束部分-高度
	 * 
	 * @return 值
	 */
	Integer getEndSectionHeight();

	/**
	 * 设置-结束部分-高度
	 * 
	 * @param value 值
	 */
	void setEndSectionHeight(Integer value);

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
	 * 获取-导出模板-页眉
	 * 
	 * @return 值
	 */
	IExportTemplateItems getPageHeaders();

	/**
	 * 设置-导出模板-页眉
	 * 
	 * @param value 值
	 */
	void setPageHeaders(IExportTemplateItems value);

	/**
	 * 获取-导出模板-开始区
	 * 
	 * @return 值
	 */
	IExportTemplateItems getStartSections();

	/**
	 * 设置-导出模板-开始区
	 * 
	 * @param value 值
	 */
	void setStartSections(IExportTemplateItems value);

	/**
	 * 获取-导出模板-重复区头
	 * 
	 * @return 值
	 */
	IExportTemplateItems getRepetitionHeaders();

	/**
	 * 设置-导出模板-重复区头
	 * 
	 * @param value 值
	 */
	void setRepetitionHeaders(IExportTemplateItems value);

	/**
	 * 获取-导出模板-重复区
	 * 
	 * @return 值
	 */
	IExportTemplateItems getRepetitions();

	/**
	 * 设置-导出模板-重复区
	 * 
	 * @param value 值
	 */
	void setRepetitions(IExportTemplateItems value);

	/**
	 * 获取-导出模板-重复区脚
	 * 
	 * @return 值
	 */
	IExportTemplateItems getRepetitionFooters();

	/**
	 * 设置-导出模板-重复区脚
	 * 
	 * @param value 值
	 */
	void setRepetitionFooters(IExportTemplateItems value);

	/**
	 * 获取-导出模板-结束区
	 * 
	 * @return 值
	 */
	IExportTemplateItems getEndSections();

	/**
	 * 设置-导出模板-结束区
	 * 
	 * @param value 值
	 */
	void setEndSections(IExportTemplateItems value);

	/**
	 * 获取-导出模板-页脚区
	 * 
	 * @return 值
	 */
	IExportTemplateItems getPageFooters();

	/**
	 * 设置-导出模板-页脚区
	 * 
	 * @param value 值
	 */
	void setPageFooters(IExportTemplateItems value);

	/**
	 * 获取-导出模板-附录集合
	 * 
	 * @return 值
	 */
	IExportTemplateAppendixs getAppendixs();

	/**
	 * 设置-导出模板-附录集合
	 * 
	 * @param value 值
	 */
	void setAppendixs(IExportTemplateAppendixs value);
}
