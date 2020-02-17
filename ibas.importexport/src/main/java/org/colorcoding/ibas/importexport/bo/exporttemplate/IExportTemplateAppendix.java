package org.colorcoding.ibas.importexport.bo.exporttemplate;

import org.colorcoding.ibas.bobas.bo.IBOSimpleLine;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.emYesNo;

/**
 * 导出模板-附录 接口
 * 
 */
public interface IExportTemplateAppendix extends IBOSimpleLine {

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
	 * 获取-行号
	 * 
	 * @return 值
	 */
	Integer getLineId();

	/**
	 * 设置-行号
	 * 
	 * @param value 值
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
	 * @param value 值
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
	 * @param value 值
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
	 * 获取-页序号
	 * 
	 * @return 值
	 */
	Integer getPageOrder();

	/**
	 * 设置-页序号
	 * 
	 * @param value 值
	 */
	void setPageOrder(Integer value);

	/**
	 * 获取-使用页眉
	 * 
	 * @return 值
	 */
	emYesNo getPageHeader();

	/**
	 * 设置-使用页眉
	 * 
	 * @param value 值
	 */
	void setPageHeader(emYesNo value);

	/**
	 * 获取-使用页脚
	 * 
	 * @return 值
	 */
	emYesNo getPageFooter();

	/**
	 * 设置-使用页脚
	 * 
	 * @param value 值
	 */
	void setPageFooter(emYesNo value);

	/**
	 * 获取-内容-左坐标
	 * 
	 * @return 值
	 */
	Integer getContentLeft();

	/**
	 * 设置-内容-左坐标
	 * 
	 * @param value 值
	 */
	void setContentLeft(Integer value);

	/**
	 * 获取-内容-上坐标
	 * 
	 * @return 值
	 */
	Integer getContentTop();

	/**
	 * 设置-内容-上坐标
	 * 
	 * @param value 值
	 */
	void setContentTop(Integer value);

	/**
	 * 获取-内容-宽度
	 * 
	 * @return 值
	 */
	Integer getContentWidth();

	/**
	 * 设置-内容-宽度
	 * 
	 * @param value 值
	 */
	void setContentWidth(Integer value);

	/**
	 * 获取-内容-高度
	 * 
	 * @return 值
	 */
	Integer getContentHeight();

	/**
	 * 设置-内容-高度
	 * 
	 * @param value 值
	 */
	void setContentHeight(Integer value);

	/**
	 * 获取-背景色-红
	 * 
	 * @return 值
	 */
	Integer getBackgroundRed();

	/**
	 * 设置-背景色-红
	 * 
	 * @param value 值
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
	 * @param value 值
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
	 * @param value 值
	 */
	void setBackgroundBlue(Integer value);

	/**
	 * 获取-背景图
	 * 
	 * @return 值
	 */
	String getBackgroundImage();

	/**
	 * 设置-背景图
	 * 
	 * @param value 值
	 */
	void setBackgroundImage(String value);

	/**
	 * 获取-附录内容
	 * 
	 * @return 值
	 */
	IExportTemplateItems getContents();

	/**
	 * 设置-附录内容
	 * 
	 * @param value 值
	 */
	void setContents(IExportTemplateItems value);

}
