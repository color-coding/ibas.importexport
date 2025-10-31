package org.colorcoding.ibas.importexport.bo.exportrecord;

import org.colorcoding.ibas.bobas.bo.IBOSimple;
import org.colorcoding.ibas.bobas.data.DateTime;

/**
* 导出日志 接口
* 
*/
public interface IExportRecord extends IBOSimple {

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
	* 获取-类型
	* 
	* @return 值
	*/
	String getBOCode();

	/**
	* 设置-类型
	* 
	* @param value 值
	*/
	void setBOCode(String value);

	/**
	* 获取-主键值
	* 
	* @return 值
	*/
	String getBOKeys();

	/**
	* 设置-主键值
	* 
	* @param value 值
	*/
	void setBOKeys(String value);

	/**
	* 获取-实例号
	* 
	* @return 值
	*/
	Integer getBOInst();

	/**
	* 设置-实例号
	* 
	* @param value 值
	*/
	void setBOInst(Integer value);

	/**
	* 获取-导出用户
	* 
	* @return 值
	*/
	Integer getExportUser();

	/**
	* 设置-导出用户
	* 
	* @param value 值
	*/
	void setExportUser(Integer value);

	/**
	* 获取-导出日期
	* 
	* @return 值
	*/
	DateTime getExportDate();

	/**
	* 设置-导出日期
	* 
	* @param value 值
	*/
	void setExportDate(DateTime value);

	/**
	* 获取-导出时间
	* 
	* @return 值
	*/
	Short getExportTime();

	/**
	* 设置-导出时间
	* 
	* @param value 值
	*/
	void setExportTime(Short value);

	/**
	* 获取-动机
	* 
	* @return 值
	*/
	String getCause();

	/**
	* 设置-动机
	* 
	* @param value 值
	*/
	void setCause(String value);

	/**
	* 获取-内容
	* 
	* @return 值
	*/
	String getContent();

	/**
	* 设置-内容
	* 
	* @param value 值
	*/
	void setContent(String value);

}
