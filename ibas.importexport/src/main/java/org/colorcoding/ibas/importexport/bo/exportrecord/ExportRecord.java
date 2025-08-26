package org.colorcoding.ibas.importexport.bo.exportrecord;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.core.IPropertyInfo;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.mapping.BusinessObjectUnit;
import org.colorcoding.ibas.bobas.mapping.DbField;
import org.colorcoding.ibas.bobas.mapping.DbFieldType;
import org.colorcoding.ibas.importexport.MyConfiguration;

/**
* 导出日志
* 
*/
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = ExportRecord.BUSINESS_OBJECT_NAME, namespace = MyConfiguration.NAMESPACE_BO)
@XmlRootElement(name = ExportRecord.BUSINESS_OBJECT_NAME, namespace = MyConfiguration.NAMESPACE_BO)
@BusinessObjectUnit(code = ExportRecord.BUSINESS_OBJECT_CODE)
public class ExportRecord extends BusinessObject<ExportRecord> implements IExportRecord {

	/**
	 * 序列化版本标记
	 */
	private static final long serialVersionUID = -7406261236946172316L;

	/**
	* 当前类型
	*/
	private static final Class<?> MY_CLASS = ExportRecord.class;

	/**
	* 数据库表
	*/
	public static final String DB_TABLE_NAME = "${Company}_IE_OERD";

	/**
	* 业务对象编码
	*/
	public static final String BUSINESS_OBJECT_CODE = "${Company}_IE_EXPORTRECORD";

	/**
	* 业务对象名称
	*/
	public static final String BUSINESS_OBJECT_NAME = "ExportRecord";

	/**
	* 属性名称-编号
	*/
	private static final String PROPERTY_OBJECTKEY_NAME = "ObjectKey";

	/**
	* 编号 属性
	*/
	@DbField(name = "ObjectKey", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = true)
	public static final IPropertyInfo<Integer> PROPERTY_OBJECTKEY = registerProperty(PROPERTY_OBJECTKEY_NAME,
			Integer.class, MY_CLASS);

	/**
	* 获取-编号
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_OBJECTKEY_NAME)
	public final Integer getObjectKey() {
		return this.getProperty(PROPERTY_OBJECTKEY);
	}

	/**
	* 设置-编号
	* 
	* @param value 值
	*/
	public final void setObjectKey(Integer value) {
		this.setProperty(PROPERTY_OBJECTKEY, value);
	}

	/**
	* 属性名称-类型
	*/
	private static final String PROPERTY_OBJECTCODE_NAME = "ObjectCode";

	/**
	* 类型 属性
	*/
	@DbField(name = "Object", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<String> PROPERTY_OBJECTCODE = registerProperty(PROPERTY_OBJECTCODE_NAME,
			String.class, MY_CLASS);

	/**
	* 获取-类型
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_OBJECTCODE_NAME)
	public final String getObjectCode() {
		return this.getProperty(PROPERTY_OBJECTCODE);
	}

	/**
	* 设置-类型
	* 
	* @param value 值
	*/
	public final void setObjectCode(String value) {
		this.setProperty(PROPERTY_OBJECTCODE, value);
	}

	/**
	* 属性名称-实例号（版本）
	*/
	private static final String PROPERTY_LOGINST_NAME = "LogInst";

	/**
	* 实例号（版本） 属性
	*/
	@DbField(name = "LogInst", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<Integer> PROPERTY_LOGINST = registerProperty(PROPERTY_LOGINST_NAME, Integer.class,
			MY_CLASS);

	/**
	* 获取-实例号（版本）
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_LOGINST_NAME)
	public final Integer getLogInst() {
		return this.getProperty(PROPERTY_LOGINST);
	}

	/**
	* 设置-实例号（版本）
	* 
	* @param value 值
	*/
	public final void setLogInst(Integer value) {
		this.setProperty(PROPERTY_LOGINST, value);
	}

	/**
	* 属性名称-数据源
	*/
	private static final String PROPERTY_DATASOURCE_NAME = "DataSource";

	/**
	* 数据源 属性
	*/
	@DbField(name = "DataSource", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<String> PROPERTY_DATASOURCE = registerProperty(PROPERTY_DATASOURCE_NAME,
			String.class, MY_CLASS);

	/**
	* 获取-数据源
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_DATASOURCE_NAME)
	public final String getDataSource() {
		return this.getProperty(PROPERTY_DATASOURCE);
	}

	/**
	* 设置-数据源
	* 
	* @param value 值
	*/
	public final void setDataSource(String value) {
		this.setProperty(PROPERTY_DATASOURCE, value);
	}

	/**
	* 属性名称-服务系列
	*/
	private static final String PROPERTY_SERIES_NAME = "Series";

	/**
	* 服务系列 属性
	*/
	@DbField(name = "Series", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<Integer> PROPERTY_SERIES = registerProperty(PROPERTY_SERIES_NAME, Integer.class,
			MY_CLASS);

	/**
	* 获取-服务系列
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_SERIES_NAME)
	public final Integer getSeries() {
		return this.getProperty(PROPERTY_SERIES);
	}

	/**
	* 设置-服务系列
	* 
	* @param value 值
	*/
	public final void setSeries(Integer value) {
		this.setProperty(PROPERTY_SERIES, value);
	}

	/**
	* 属性名称-创建日期
	*/
	private static final String PROPERTY_CREATEDATE_NAME = "CreateDate";

	/**
	* 创建日期 属性
	*/
	@DbField(name = "CreateDate", type = DbFieldType.DATE, table = DB_TABLE_NAME)
	public static final IPropertyInfo<DateTime> PROPERTY_CREATEDATE = registerProperty(PROPERTY_CREATEDATE_NAME,
			DateTime.class, MY_CLASS);

	/**
	* 获取-创建日期
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_CREATEDATE_NAME)
	public final DateTime getCreateDate() {
		return this.getProperty(PROPERTY_CREATEDATE);
	}

	/**
	* 设置-创建日期
	* 
	* @param value 值
	*/
	public final void setCreateDate(DateTime value) {
		this.setProperty(PROPERTY_CREATEDATE, value);
	}

	/**
	* 属性名称-创建时间
	*/
	private static final String PROPERTY_CREATETIME_NAME = "CreateTime";

	/**
	* 创建时间 属性
	*/
	@DbField(name = "CreateTime", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<Short> PROPERTY_CREATETIME = registerProperty(PROPERTY_CREATETIME_NAME,
			Short.class, MY_CLASS);

	/**
	* 获取-创建时间
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_CREATETIME_NAME)
	public final Short getCreateTime() {
		return this.getProperty(PROPERTY_CREATETIME);
	}

	/**
	* 设置-创建时间
	* 
	* @param value 值
	*/
	public final void setCreateTime(Short value) {
		this.setProperty(PROPERTY_CREATETIME, value);
	}

	/**
	* 属性名称-修改日期
	*/
	private static final String PROPERTY_UPDATEDATE_NAME = "UpdateDate";

	/**
	* 修改日期 属性
	*/
	@DbField(name = "UpdateDate", type = DbFieldType.DATE, table = DB_TABLE_NAME)
	public static final IPropertyInfo<DateTime> PROPERTY_UPDATEDATE = registerProperty(PROPERTY_UPDATEDATE_NAME,
			DateTime.class, MY_CLASS);

	/**
	* 获取-修改日期
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_UPDATEDATE_NAME)
	public final DateTime getUpdateDate() {
		return this.getProperty(PROPERTY_UPDATEDATE);
	}

	/**
	* 设置-修改日期
	* 
	* @param value 值
	*/
	public final void setUpdateDate(DateTime value) {
		this.setProperty(PROPERTY_UPDATEDATE, value);
	}

	/**
	* 属性名称-修改时间
	*/
	private static final String PROPERTY_UPDATETIME_NAME = "UpdateTime";

	/**
	* 修改时间 属性
	*/
	@DbField(name = "UpdateTime", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<Short> PROPERTY_UPDATETIME = registerProperty(PROPERTY_UPDATETIME_NAME,
			Short.class, MY_CLASS);

	/**
	* 获取-修改时间
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_UPDATETIME_NAME)
	public final Short getUpdateTime() {
		return this.getProperty(PROPERTY_UPDATETIME);
	}

	/**
	* 设置-修改时间
	* 
	* @param value 值
	*/
	public final void setUpdateTime(Short value) {
		this.setProperty(PROPERTY_UPDATETIME, value);
	}

	/**
	* 属性名称-创建用户
	*/
	private static final String PROPERTY_CREATEUSERSIGN_NAME = "CreateUserSign";

	/**
	* 创建用户 属性
	*/
	@DbField(name = "Creator", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<Integer> PROPERTY_CREATEUSERSIGN = registerProperty(PROPERTY_CREATEUSERSIGN_NAME,
			Integer.class, MY_CLASS);

	/**
	* 获取-创建用户
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_CREATEUSERSIGN_NAME)
	public final Integer getCreateUserSign() {
		return this.getProperty(PROPERTY_CREATEUSERSIGN);
	}

	/**
	* 设置-创建用户
	* 
	* @param value 值
	*/
	public final void setCreateUserSign(Integer value) {
		this.setProperty(PROPERTY_CREATEUSERSIGN, value);
	}

	/**
	* 属性名称-修改用户
	*/
	private static final String PROPERTY_UPDATEUSERSIGN_NAME = "UpdateUserSign";

	/**
	* 修改用户 属性
	*/
	@DbField(name = "Updator", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<Integer> PROPERTY_UPDATEUSERSIGN = registerProperty(PROPERTY_UPDATEUSERSIGN_NAME,
			Integer.class, MY_CLASS);

	/**
	* 获取-修改用户
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_UPDATEUSERSIGN_NAME)
	public final Integer getUpdateUserSign() {
		return this.getProperty(PROPERTY_UPDATEUSERSIGN);
	}

	/**
	* 设置-修改用户
	* 
	* @param value 值
	*/
	public final void setUpdateUserSign(Integer value) {
		this.setProperty(PROPERTY_UPDATEUSERSIGN, value);
	}

	/**
	* 属性名称-创建动作标识
	*/
	private static final String PROPERTY_CREATEACTIONID_NAME = "CreateActionId";

	/**
	* 创建动作标识 属性
	*/
	@DbField(name = "CreateActId", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<String> PROPERTY_CREATEACTIONID = registerProperty(PROPERTY_CREATEACTIONID_NAME,
			String.class, MY_CLASS);

	/**
	* 获取-创建动作标识
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_CREATEACTIONID_NAME)
	public final String getCreateActionId() {
		return this.getProperty(PROPERTY_CREATEACTIONID);
	}

	/**
	* 设置-创建动作标识
	* 
	* @param value 值
	*/
	public final void setCreateActionId(String value) {
		this.setProperty(PROPERTY_CREATEACTIONID, value);
	}

	/**
	* 属性名称-更新动作标识
	*/
	private static final String PROPERTY_UPDATEACTIONID_NAME = "UpdateActionId";

	/**
	* 更新动作标识 属性
	*/
	@DbField(name = "UpdateActId", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<String> PROPERTY_UPDATEACTIONID = registerProperty(PROPERTY_UPDATEACTIONID_NAME,
			String.class, MY_CLASS);

	/**
	* 获取-更新动作标识
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_UPDATEACTIONID_NAME)
	public final String getUpdateActionId() {
		return this.getProperty(PROPERTY_UPDATEACTIONID);
	}

	/**
	* 设置-更新动作标识
	* 
	* @param value 值
	*/
	public final void setUpdateActionId(String value) {
		this.setProperty(PROPERTY_UPDATEACTIONID, value);
	}

	/**
	* 属性名称-类型
	*/
	private static final String PROPERTY_BOCODE_NAME = "BOCode";

	/**
	* 类型 属性
	*/
	@DbField(name = "BOCode", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<String> PROPERTY_BOCODE = registerProperty(PROPERTY_BOCODE_NAME, String.class,
			MY_CLASS);

	/**
	* 获取-类型
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_BOCODE_NAME)
	public final String getBOCode() {
		return this.getProperty(PROPERTY_BOCODE);
	}

	/**
	* 设置-类型
	* 
	* @param value 值
	*/
	public final void setBOCode(String value) {
		this.setProperty(PROPERTY_BOCODE, value);
	}

	/**
	* 属性名称-主键值
	*/
	private static final String PROPERTY_BOKEYS_NAME = "BOKeys";

	/**
	* 主键值 属性
	*/
	@DbField(name = "BOKeys", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<String> PROPERTY_BOKEYS = registerProperty(PROPERTY_BOKEYS_NAME, String.class,
			MY_CLASS);

	/**
	* 获取-主键值
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_BOKEYS_NAME)
	public final String getBOKeys() {
		return this.getProperty(PROPERTY_BOKEYS);
	}

	/**
	* 设置-主键值
	* 
	* @param value 值
	*/
	public final void setBOKeys(String value) {
		this.setProperty(PROPERTY_BOKEYS, value);
	}

	/**
	* 属性名称-实例号
	*/
	private static final String PROPERTY_BOINST_NAME = "BOInst";

	/**
	* 实例号 属性
	*/
	@DbField(name = "BOInst", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<Integer> PROPERTY_BOINST = registerProperty(PROPERTY_BOINST_NAME, Integer.class,
			MY_CLASS);

	/**
	* 获取-实例号
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_BOINST_NAME)
	public final Integer getBOInst() {
		return this.getProperty(PROPERTY_BOINST);
	}

	/**
	* 设置-实例号
	* 
	* @param value 值
	*/
	public final void setBOInst(Integer value) {
		this.setProperty(PROPERTY_BOINST, value);
	}

	/**
	* 属性名称-导出用户
	*/
	private static final String PROPERTY_EXPORTUSER_NAME = "ExportUser";

	/**
	* 导出用户 属性
	*/
	@DbField(name = "Exporter", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<Integer> PROPERTY_EXPORTUSER = registerProperty(PROPERTY_EXPORTUSER_NAME,
			Integer.class, MY_CLASS);

	/**
	* 获取-导出用户
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_EXPORTUSER_NAME)
	public final Integer getExportUser() {
		return this.getProperty(PROPERTY_EXPORTUSER);
	}

	/**
	* 设置-导出用户
	* 
	* @param value 值
	*/
	public final void setExportUser(Integer value) {
		this.setProperty(PROPERTY_EXPORTUSER, value);
	}

	/**
	* 属性名称-导出日期
	*/
	private static final String PROPERTY_EXPORTDATE_NAME = "ExportDate";

	/**
	* 导出日期 属性
	*/
	@DbField(name = "ExportDate", type = DbFieldType.DATE, table = DB_TABLE_NAME)
	public static final IPropertyInfo<DateTime> PROPERTY_EXPORTDATE = registerProperty(PROPERTY_EXPORTDATE_NAME,
			DateTime.class, MY_CLASS);

	/**
	* 获取-导出日期
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_EXPORTDATE_NAME)
	public final DateTime getExportDate() {
		return this.getProperty(PROPERTY_EXPORTDATE);
	}

	/**
	* 设置-导出日期
	* 
	* @param value 值
	*/
	public final void setExportDate(DateTime value) {
		this.setProperty(PROPERTY_EXPORTDATE, value);
	}

	/**
	* 属性名称-导出时间
	*/
	private static final String PROPERTY_EXPORTTIME_NAME = "ExportTime";

	/**
	* 导出时间 属性
	*/
	@DbField(name = "ExportTime", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<Short> PROPERTY_EXPORTTIME = registerProperty(PROPERTY_EXPORTTIME_NAME,
			Short.class, MY_CLASS);

	/**
	* 获取-导出时间
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_EXPORTTIME_NAME)
	public final Short getExportTime() {
		return this.getProperty(PROPERTY_EXPORTTIME);
	}

	/**
	* 设置-导出时间
	* 
	* @param value 值
	*/
	public final void setExportTime(Short value) {
		this.setProperty(PROPERTY_EXPORTTIME, value);
	}

	/**
	* 属性名称-动机
	*/
	private static final String PROPERTY_CAUSE_NAME = "Cause";

	/**
	* 动机 属性
	*/
	@DbField(name = "Cause", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME)
	public static final IPropertyInfo<String> PROPERTY_CAUSE = registerProperty(PROPERTY_CAUSE_NAME, String.class,
			MY_CLASS);

	/**
	* 获取-动机
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_CAUSE_NAME)
	public final String getCause() {
		return this.getProperty(PROPERTY_CAUSE);
	}

	/**
	* 设置-动机
	* 
	* @param value 值
	*/
	public final void setCause(String value) {
		this.setProperty(PROPERTY_CAUSE, value);
	}

	/**
	* 属性名称-内容
	*/
	private static final String PROPERTY_CONTENT_NAME = "Content";

	/**
	* 内容 属性
	*/
	@DbField(name = "Content", type = DbFieldType.MEMO, table = DB_TABLE_NAME)
	public static final IPropertyInfo<String> PROPERTY_CONTENT = registerProperty(PROPERTY_CONTENT_NAME, String.class,
			MY_CLASS);

	/**
	* 获取-内容
	* 
	* @return 值
	*/
	@XmlElement(name = PROPERTY_CONTENT_NAME)
	public final String getContent() {
		return this.getProperty(PROPERTY_CONTENT);
	}

	/**
	* 设置-内容
	* 
	* @param value 值
	*/
	public final void setContent(String value) {
		this.setProperty(PROPERTY_CONTENT, value);
	}

	/**
	* 初始化数据
	*/
	@Override
	protected void initialize() {
		super.initialize();// 基类初始化，不可去除
		this.setObjectCode(MyConfiguration.applyVariables(BUSINESS_OBJECT_CODE));

	}

}
