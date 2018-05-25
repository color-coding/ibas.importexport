package org.colorcoding.ibas.importexport.bo.exporttemplate;

import org.colorcoding.ibas.bobas.bo.IBOSimpleLine;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.importexport.data.emAreaType;
import org.colorcoding.ibas.importexport.data.emDataSourceType;
import org.colorcoding.ibas.importexport.data.emJustificationHorizontal;
import org.colorcoding.ibas.importexport.data.emJustificationVertical;
import org.colorcoding.ibas.importexport.data.emTextStyle;

/**
 * 导出模板-项 接口
 * 
 */
public interface IExportTemplateItem extends IBOSimpleLine {

	/**
	 * 获取-编号
	 * 
	 * @return 值
	 */
	Integer getObjectKey();

	/**
	 * 设置-编号
	 * 
	 * @param value
	 *            值
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
	 * @param value
	 *            值
	 */
	void setObjectCode(String value);

	/**
	 * 获取-行号
	 * 
	 * @return 值
	 */
	Integer getLineId();

	/**
	 * 设置-行号
	 * 
	 * @param value
	 *            值
	 */
	void setLineId(Integer value);

	/**
	 * 获取-数据源
	 * 
	 * @return 值
	 */
	String getDataSource();

	/**
	 * 设置-数据源
	 * 
	 * @param value
	 *            值
	 */
	void setDataSource(String value);

	/**
	 * 获取-实例号（版本）
	 * 
	 * @return 值
	 */
	Integer getLogInst();

	/**
	 * 设置-实例号（版本）
	 * 
	 * @param value
	 *            值
	 */
	void setLogInst(Integer value);

	/**
	 * 获取-创建日期
	 * 
	 * @return 值
	 */
	DateTime getCreateDate();

	/**
	 * 设置-创建日期
	 * 
	 * @param value
	 *            值
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
	 * @param value
	 *            值
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
	 * @param value
	 *            值
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
	 * @param value
	 *            值
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
	 * @param value
	 *            值
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
	 * @param value
	 *            值
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
	 * @param value
	 *            值
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
	 * @param value
	 *            值
	 */
	void setUpdateActionId(String value);

	/**
	 * 获取-区域
	 * 
	 * @return 值
	 */
	emAreaType getArea();

	/**
	 * 设置-区域
	 * 
	 * @param value
	 *            值
	 */
	void setArea(emAreaType value);

	/**
	 * 获取-项标识
	 * 
	 * @return 值
	 */
	String getItemID();

	/**
	 * 设置-项标识
	 * 
	 * @param value
	 *            值
	 */
	void setItemID(String value);

	/**
	 * 获取-项类型
	 * 
	 * @return 值
	 */
	String getItemType();

	/**
	 * 设置-项类型
	 * 
	 * @param value
	 *            值
	 */
	void setItemType(String value);

	/**
	 * 获取-项左坐标
	 * 
	 * @return 值
	 */
	Integer getItemLeft();

	/**
	 * 设置-项左坐标
	 * 
	 * @param value
	 *            值
	 */
	void setItemLeft(Integer value);

	/**
	 * 获取-项上坐标
	 * 
	 * @return 值
	 */
	Integer getItemTop();

	/**
	 * 设置-项上坐标
	 * 
	 * @param value
	 *            值
	 */
	void setItemTop(Integer value);

	/**
	 * 获取-数据源
	 * 
	 * @return 值
	 */
	emDataSourceType getSourceType();

	/**
	 * 设置-数据源
	 * 
	 * @param value
	 *            值
	 */
	void setSourceType(emDataSourceType value);

	/**
	 * 获取-项字符串
	 * 
	 * @return 值
	 */
	String getItemString();

	/**
	 * 设置-项字符串
	 * 
	 * @param value
	 *            值
	 */
	void setItemString(String value);

	/**
	 * 获取-显示格式
	 * 
	 * @return 值
	 */
	String getValueFormat();

	/**
	 * 设置-显示格式
	 * 
	 * @param value
	 *            值
	 */
	void setValueFormat(String value);

	/**
	 * 获取-项是否可见
	 * 
	 * @return 值
	 */
	emYesNo getItemVisible();

	/**
	 * 设置-项是否可见
	 * 
	 * @param value
	 *            值
	 */
	void setItemVisible(emYesNo value);

	/**
	 * 获取-项宽度
	 * 
	 * @return 值
	 */
	Integer getItemWidth();

	/**
	 * 设置-项宽度
	 * 
	 * @param value
	 *            值
	 */
	void setItemWidth(Integer value);

	/**
	 * 获取-项高度
	 * 
	 * @return 值
	 */
	Integer getItemHeight();

	/**
	 * 设置-项高度
	 * 
	 * @param value
	 *            值
	 */
	void setItemHeight(Integer value);

	/**
	 * 获取-左边距
	 * 
	 * @return 值
	 */
	Integer getMarginLeft();

	/**
	 * 设置-左边距
	 * 
	 * @param value
	 *            值
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
	 * @param value
	 *            值
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
	 * @param value
	 *            值
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
	 * @param value
	 *            值
	 */
	void setMarginBottom(Integer value);

	/**
	 * 获取-左线长度
	 * 
	 * @return 值
	 */
	Integer getLineLeft();

	/**
	 * 设置-左线长度
	 * 
	 * @param value
	 *            值
	 */
	void setLineLeft(Integer value);

	/**
	 * 获取-右线长度
	 * 
	 * @return 值
	 */
	Integer getLineRight();

	/**
	 * 设置-右线长度
	 * 
	 * @param value
	 *            值
	 */
	void setLineRight(Integer value);

	/**
	 * 获取-上线长度
	 * 
	 * @return 值
	 */
	Integer getLineTop();

	/**
	 * 设置-上线长度
	 * 
	 * @param value
	 *            值
	 */
	void setLineTop(Integer value);

	/**
	 * 获取-下线长度
	 * 
	 * @return 值
	 */
	Integer getLineBottom();

	/**
	 * 设置-下线长度
	 * 
	 * @param value
	 *            值
	 */
	void setLineBottom(Integer value);

	/**
	 * 获取-字体名称
	 * 
	 * @return 值
	 */
	String getFontName();

	/**
	 * 设置-字体名称
	 * 
	 * @param value
	 *            值
	 */
	void setFontName(String value);

	/**
	 * 获取-字体大小
	 * 
	 * @return 值
	 */
	Integer getFontSize();

	/**
	 * 设置-字体大小
	 * 
	 * @param value
	 *            值
	 */
	void setFontSize(Integer value);

	/**
	 * 获取-文本样式
	 * 
	 * @return 值
	 */
	emTextStyle getTextStyle();

	/**
	 * 设置-文本样式
	 * 
	 * @param value
	 *            值
	 */
	void setTextStyle(emTextStyle value);

	/**
	 * 获取-水平对齐方式
	 * 
	 * @return 值
	 */
	emJustificationHorizontal getJustificationHorizontal();

	/**
	 * 设置-水平对齐方式
	 * 
	 * @param value
	 *            值
	 */
	void setJustificationHorizontal(emJustificationHorizontal value);

	/**
	 * 获取-竖直对齐方式
	 * 
	 * @return 值
	 */
	emJustificationVertical getJustificationVertical();

	/**
	 * 设置-竖直对齐方式
	 * 
	 * @param value
	 *            值
	 */
	void setJustificationVertical(emJustificationVertical value);

	/**
	 * 获取-背景色-红
	 * 
	 * @return 值
	 */
	Integer getBackgroundRed();

	/**
	 * 设置-背景色-红
	 * 
	 * @param value
	 *            值
	 */
	void setBackgroundRed(Integer value);

	/**
	 * 获取-背景色-绿
	 * 
	 * @return 值
	 */
	Integer getBackgroundGreen();

	/**
	 * 设置-背景色-绿
	 * 
	 * @param value
	 *            值
	 */
	void setBackgroundGreen(Integer value);

	/**
	 * 获取-背景色-蓝
	 * 
	 * @return 值
	 */
	Integer getBackgroundBlue();

	/**
	 * 设置-背景色-蓝
	 * 
	 * @param value
	 *            值
	 */
	void setBackgroundBlue(Integer value);

	/**
	 * 获取-前景色-红
	 * 
	 * @return 值
	 */
	Integer getForegroundRed();

	/**
	 * 设置-前景色-红
	 * 
	 * @param value
	 *            值
	 */
	void setForegroundRed(Integer value);

	/**
	 * 获取-前景色-绿
	 * 
	 * @return 值
	 */
	Integer getForegroundGreen();

	/**
	 * 设置-前景色-绿
	 * 
	 * @param value
	 *            值
	 */
	void setForegroundGreen(Integer value);

	/**
	 * 获取-前景色-蓝
	 * 
	 * @return 值
	 */
	Integer getForegroundBlue();

	/**
	 * 设置-前景色-蓝
	 * 
	 * @param value
	 *            值
	 */
	void setForegroundBlue(Integer value);

	/**
	 * 获取-高亮显示色-红
	 * 
	 * @return 值
	 */
	Integer getMarkerRed();

	/**
	 * 设置-高亮显示色-红
	 * 
	 * @param value
	 *            值
	 */
	void setMarkerRed(Integer value);

	/**
	 * 获取-高亮显示色-绿
	 * 
	 * @return 值
	 */
	Integer getMarkerGreen();

	/**
	 * 设置-高亮显示色-绿
	 * 
	 * @param value
	 *            值
	 */
	void setMarkerGreen(Integer value);

	/**
	 * 获取-高亮显示色-蓝
	 * 
	 * @return 值
	 */
	Integer getMarkerBlue();

	/**
	 * 设置-高亮显示色-蓝
	 * 
	 * @param value
	 *            值
	 */
	void setMarkerBlue(Integer value);

	/**
	 * 获取-框架色-红
	 * 
	 * @return 值
	 */
	Integer getBorderRed();

	/**
	 * 设置-框架色-红
	 * 
	 * @param value
	 *            值
	 */
	void setBorderRed(Integer value);

	/**
	 * 获取-框架色-绿
	 * 
	 * @return 值
	 */
	Integer getBorderGreen();

	/**
	 * 设置-框架色-绿
	 * 
	 * @param value
	 *            值
	 */
	void setBorderGreen(Integer value);

	/**
	 * 获取-框架色-蓝
	 * 
	 * @return 值
	 */
	Integer getBorderBlue();

	/**
	 * 设置-框架色-蓝
	 * 
	 * @param value
	 *            值
	 */
	void setBorderBlue(Integer value);

}
