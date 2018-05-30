package org.colorcoding.ibas.importexport.bo.exporttemplate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.core.IPropertyInfo;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.mapping.BOCode;
import org.colorcoding.ibas.bobas.mapping.DbField;
import org.colorcoding.ibas.bobas.mapping.DbFieldType;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.data.emAreaType;

/**
 * 导出模板
 * 
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = ExportTemplate.BUSINESS_OBJECT_NAME, namespace = MyConfiguration.NAMESPACE_BO)
@XmlRootElement(name = ExportTemplate.BUSINESS_OBJECT_NAME, namespace = MyConfiguration.NAMESPACE_BO)
@BOCode(ExportTemplate.BUSINESS_OBJECT_CODE)
public class ExportTemplate extends BusinessObject<ExportTemplate> implements IExportTemplate {

	/**
	 * 序列化版本标记
	 */
	private static final long serialVersionUID = 2449909921787074345L;

	/**
	 * 当前类型
	 */
	private static final Class<?> MY_CLASS = ExportTemplate.class;

	/**
	 * 数据库表
	 */
	public static final String DB_TABLE_NAME = "${Company}_IE_OEXT";

	/**
	 * 业务对象编码
	 */
	public static final String BUSINESS_OBJECT_CODE = "${Company}_IE_EXPORTTEMPLATE";

	/**
	 * 业务对象名称
	 */
	public static final String BUSINESS_OBJECT_NAME = "ExportTemplate";

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
	 * @param value
	 *            值
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
	@DbField(name = "Object", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
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
	 * @param value
	 *            值
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
	@DbField(name = "LogInst", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
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
	 * @param value
	 *            值
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
	@DbField(name = "DataSource", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
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
	 * @param value
	 *            值
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
	@DbField(name = "Series", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
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
	 * @param value
	 *            值
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
	@DbField(name = "CreateDate", type = DbFieldType.DATE, table = DB_TABLE_NAME, primaryKey = false)
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
	 * @param value
	 *            值
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
	@DbField(name = "CreateTime", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
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
	 * @param value
	 *            值
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
	@DbField(name = "UpdateDate", type = DbFieldType.DATE, table = DB_TABLE_NAME, primaryKey = false)
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
	 * @param value
	 *            值
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
	@DbField(name = "UpdateTime", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
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
	 * @param value
	 *            值
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
	@DbField(name = "Creator", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
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
	 * @param value
	 *            值
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
	@DbField(name = "Updator", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
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
	 * @param value
	 *            值
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
	@DbField(name = "CreateActId", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
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
	 * @param value
	 *            值
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
	@DbField(name = "UpdateActId", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
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
	 * @param value
	 *            值
	 */
	public final void setUpdateActionId(String value) {
		this.setProperty(PROPERTY_UPDATEACTIONID, value);
	}

	/**
	 * 属性名称-数据所有者
	 */
	private static final String PROPERTY_DATAOWNER_NAME = "DataOwner";

	/**
	 * 数据所有者 属性
	 */
	@DbField(name = "DataOwner", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_DATAOWNER = registerProperty(PROPERTY_DATAOWNER_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-数据所有者
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_DATAOWNER_NAME)
	public final Integer getDataOwner() {
		return this.getProperty(PROPERTY_DATAOWNER);
	}

	/**
	 * 设置-数据所有者
	 * 
	 * @param value
	 *            值
	 */
	public final void setDataOwner(Integer value) {
		this.setProperty(PROPERTY_DATAOWNER, value);
	}

	/**
	 * 属性名称-团队成员
	 */
	private static final String PROPERTY_TEAMMEMBERS_NAME = "TeamMembers";

	/**
	 * 团队成员 属性
	 */
	@DbField(name = "TeamMembers", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_TEAMMEMBERS = registerProperty(PROPERTY_TEAMMEMBERS_NAME,
			String.class, MY_CLASS);

	/**
	 * 获取-团队成员
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_TEAMMEMBERS_NAME)
	public final String getTeamMembers() {
		return this.getProperty(PROPERTY_TEAMMEMBERS);
	}

	/**
	 * 设置-团队成员
	 * 
	 * @param value
	 *            值
	 */
	public final void setTeamMembers(String value) {
		this.setProperty(PROPERTY_TEAMMEMBERS, value);
	}

	/**
	 * 属性名称-数据所属组织
	 */
	private static final String PROPERTY_ORGANIZATION_NAME = "Organization";

	/**
	 * 数据所属组织 属性
	 */
	@DbField(name = "OrgCode", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_ORGANIZATION = registerProperty(PROPERTY_ORGANIZATION_NAME,
			String.class, MY_CLASS);

	/**
	 * 获取-数据所属组织
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ORGANIZATION_NAME)
	public final String getOrganization() {
		return this.getProperty(PROPERTY_ORGANIZATION);
	}

	/**
	 * 设置-数据所属组织
	 * 
	 * @param value
	 *            值
	 */
	public final void setOrganization(String value) {
		this.setProperty(PROPERTY_ORGANIZATION, value);
	}

	/**
	 * 属性名称-名称
	 */
	private static final String PROPERTY_NAME_NAME = "Name";

	/**
	 * 名称 属性
	 */
	@DbField(name = "Name", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_NAME = registerProperty(PROPERTY_NAME_NAME, String.class,
			MY_CLASS);

	/**
	 * 获取-名称
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_NAME_NAME)
	public final String getName() {
		return this.getProperty(PROPERTY_NAME);
	}

	/**
	 * 设置-名称
	 * 
	 * @param value
	 *            值
	 */
	public final void setName(String value) {
		this.setProperty(PROPERTY_NAME, value);
	}

	/**
	 * 属性名称-已激活的
	 */
	private static final String PROPERTY_ACTIVATED_NAME = "Activated";

	/**
	 * 已激活的 属性
	 */
	@DbField(name = "Activated", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emYesNo> PROPERTY_ACTIVATED = registerProperty(PROPERTY_ACTIVATED_NAME,
			emYesNo.class, MY_CLASS);

	/**
	 * 获取-已激活的
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ACTIVATED_NAME)
	public final emYesNo getActivated() {
		return this.getProperty(PROPERTY_ACTIVATED);
	}

	/**
	 * 设置-已激活的
	 * 
	 * @param value
	 *            值
	 */
	public final void setActivated(emYesNo value) {
		this.setProperty(PROPERTY_ACTIVATED, value);
	}

	/**
	 * 属性名称-语言
	 */
	private static final String PROPERTY_LANGUAGE_NAME = "Language";

	/**
	 * 语言 属性
	 */
	@DbField(name = "Language", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_LANGUAGE = registerProperty(PROPERTY_LANGUAGE_NAME, String.class,
			MY_CLASS);

	/**
	 * 获取-语言
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_LANGUAGE_NAME)
	public final String getLanguage() {
		return this.getProperty(PROPERTY_LANGUAGE);
	}

	/**
	 * 设置-语言
	 * 
	 * @param value
	 *            值
	 */
	public final void setLanguage(String value) {
		this.setProperty(PROPERTY_LANGUAGE, value);
	}

	/**
	 * 属性名称-类型
	 */
	private static final String PROPERTY_CATEGORY_NAME = "Category";

	/**
	 * 类型 属性
	 */
	@DbField(name = "Category", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_CATEGORY = registerProperty(PROPERTY_CATEGORY_NAME, String.class,
			MY_CLASS);

	/**
	 * 获取-类型
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_CATEGORY_NAME)
	public final String getCategory() {
		return this.getProperty(PROPERTY_CATEGORY);
	}

	/**
	 * 设置-类型
	 * 
	 * @param value
	 *            值
	 */
	public final void setCategory(String value) {
		this.setProperty(PROPERTY_CATEGORY, value);
	}

	/**
	 * 属性名称-关联的业务对象
	 */
	private static final String PROPERTY_BOCODE_NAME = "BOCode";

	/**
	 * 关联的业务对象 属性
	 */
	@DbField(name = "BOCode", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_BOCODE = registerProperty(PROPERTY_BOCODE_NAME, String.class,
			MY_CLASS);

	/**
	 * 获取-关联的业务对象
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BOCODE_NAME)
	public final String getBOCode() {
		return this.getProperty(PROPERTY_BOCODE);
	}

	/**
	 * 设置-关联的业务对象
	 * 
	 * @param value
	 *            值
	 */
	public final void setBOCode(String value) {
		this.setProperty(PROPERTY_BOCODE, value);
	}

	/**
	 * 属性名称-关联的应用
	 */
	private static final String PROPERTY_APPLICATIONID_NAME = "ApplicationId";

	/**
	 * 关联的应用 属性
	 */
	@DbField(name = "AppId", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_APPLICATIONID = registerProperty(PROPERTY_APPLICATIONID_NAME,
			String.class, MY_CLASS);

	/**
	 * 获取-关联的应用
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_APPLICATIONID_NAME)
	public final String getApplicationId() {
		return this.getProperty(PROPERTY_APPLICATIONID);
	}

	/**
	 * 设置-关联的应用
	 * 
	 * @param value
	 *            值
	 */
	public final void setApplicationId(String value) {
		this.setProperty(PROPERTY_APPLICATIONID, value);
	}

	/**
	 * 属性名称-注释
	 */
	private static final String PROPERTY_NOTES_NAME = "Notes";

	/**
	 * 注释 属性
	 */
	@DbField(name = "Notes", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_NOTES = registerProperty(PROPERTY_NOTES_NAME, String.class,
			MY_CLASS);

	/**
	 * 获取-注释
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_NOTES_NAME)
	public final String getNotes() {
		return this.getProperty(PROPERTY_NOTES);
	}

	/**
	 * 设置-注释
	 * 
	 * @param value
	 *            值
	 */
	public final void setNotes(String value) {
		this.setProperty(PROPERTY_NOTES, value);
	}

	/**
	 * 属性名称-输出宽度
	 */
	private static final String PROPERTY_WIDTH_NAME = "Width";

	/**
	 * 输出宽度 属性
	 */
	@DbField(name = "Width", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_WIDTH = registerProperty(PROPERTY_WIDTH_NAME, Integer.class,
			MY_CLASS);

	/**
	 * 获取-输出宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_WIDTH_NAME)
	public final Integer getWidth() {
		return this.getProperty(PROPERTY_WIDTH);
	}

	/**
	 * 设置-输出宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setWidth(Integer value) {
		this.setProperty(PROPERTY_WIDTH, value);
	}

	/**
	 * 属性名称-输出高度
	 */
	private static final String PROPERTY_HEIGHT_NAME = "Height";

	/**
	 * 输出高度 属性
	 */
	@DbField(name = "Height", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_HEIGHT = registerProperty(PROPERTY_HEIGHT_NAME, Integer.class,
			MY_CLASS);

	/**
	 * 获取-输出高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_HEIGHT_NAME)
	public final Integer getHeight() {
		return this.getProperty(PROPERTY_HEIGHT);
	}

	/**
	 * 设置-输出高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setHeight(Integer value) {
		this.setProperty(PROPERTY_HEIGHT, value);
	}

	/**
	 * 属性名称-左边距
	 */
	private static final String PROPERTY_MARGINLEFT_NAME = "MarginLeft";

	/**
	 * 左边距 属性
	 */
	@DbField(name = "LMargin", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_MARGINLEFT = registerProperty(PROPERTY_MARGINLEFT_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-左边距
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_MARGINLEFT_NAME)
	public final Integer getMarginLeft() {
		return this.getProperty(PROPERTY_MARGINLEFT);
	}

	/**
	 * 设置-左边距
	 * 
	 * @param value
	 *            值
	 */
	public final void setMarginLeft(Integer value) {
		this.setProperty(PROPERTY_MARGINLEFT, value);
	}

	/**
	 * 属性名称-右边距
	 */
	private static final String PROPERTY_MARGINRIGHT_NAME = "MarginRight";

	/**
	 * 右边距 属性
	 */
	@DbField(name = "RMargin", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_MARGINRIGHT = registerProperty(PROPERTY_MARGINRIGHT_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-右边距
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_MARGINRIGHT_NAME)
	public final Integer getMarginRight() {
		return this.getProperty(PROPERTY_MARGINRIGHT);
	}

	/**
	 * 设置-右边距
	 * 
	 * @param value
	 *            值
	 */
	public final void setMarginRight(Integer value) {
		this.setProperty(PROPERTY_MARGINRIGHT, value);
	}

	/**
	 * 属性名称-上边距
	 */
	private static final String PROPERTY_MARGINTOP_NAME = "MarginTop";

	/**
	 * 上边距 属性
	 */
	@DbField(name = "TMargin", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_MARGINTOP = registerProperty(PROPERTY_MARGINTOP_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-上边距
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_MARGINTOP_NAME)
	public final Integer getMarginTop() {
		return this.getProperty(PROPERTY_MARGINTOP);
	}

	/**
	 * 设置-上边距
	 * 
	 * @param value
	 *            值
	 */
	public final void setMarginTop(Integer value) {
		this.setProperty(PROPERTY_MARGINTOP, value);
	}

	/**
	 * 属性名称-下边距
	 */
	private static final String PROPERTY_MARGINBOTTOM_NAME = "MarginBottom";

	/**
	 * 下边距 属性
	 */
	@DbField(name = "BMargin", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_MARGINBOTTOM = registerProperty(PROPERTY_MARGINBOTTOM_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-下边距
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_MARGINBOTTOM_NAME)
	public final Integer getMarginBottom() {
		return this.getProperty(PROPERTY_MARGINBOTTOM);
	}

	/**
	 * 设置-下边距
	 * 
	 * @param value
	 *            值
	 */
	public final void setMarginBottom(Integer value) {
		this.setProperty(PROPERTY_MARGINBOTTOM, value);
	}

	/**
	 * 属性名称-区域间隔
	 */
	private static final String PROPERTY_MARGINAREA_NAME = "MarginArea";

	/**
	 * 区域间隔 属性
	 */
	@DbField(name = "AMargin", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_MARGINAREA = registerProperty(PROPERTY_MARGINAREA_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-区域间隔
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_MARGINAREA_NAME)
	public final Integer getMarginArea() {
		return this.getProperty(PROPERTY_MARGINAREA);
	}

	/**
	 * 设置-区域间隔
	 * 
	 * @param value
	 *            值
	 */
	public final void setMarginArea(Integer value) {
		this.setProperty(PROPERTY_MARGINAREA, value);
	}

	/**
	 * 属性名称-页眉-左坐标
	 */
	private static final String PROPERTY_PAGEHEADERLEFT_NAME = "PageHeaderLeft";

	/**
	 * 页眉-左坐标 属性
	 */
	@DbField(name = "PHLeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_PAGEHEADERLEFT = registerProperty(PROPERTY_PAGEHEADERLEFT_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-页眉-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PAGEHEADERLEFT_NAME)
	public final Integer getPageHeaderLeft() {
		return this.getProperty(PROPERTY_PAGEHEADERLEFT);
	}

	/**
	 * 设置-页眉-左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setPageHeaderLeft(Integer value) {
		this.setProperty(PROPERTY_PAGEHEADERLEFT, value);
	}

	/**
	 * 属性名称-页眉-上坐标
	 */
	private static final String PROPERTY_PAGEHEADERTOP_NAME = "PageHeaderTop";

	/**
	 * 页眉-上坐标 属性
	 */
	@DbField(name = "PHTop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_PAGEHEADERTOP = registerProperty(PROPERTY_PAGEHEADERTOP_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-页眉-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PAGEHEADERTOP_NAME)
	public final Integer getPageHeaderTop() {
		return this.getProperty(PROPERTY_PAGEHEADERTOP);
	}

	/**
	 * 设置-页眉-上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setPageHeaderTop(Integer value) {
		this.setProperty(PROPERTY_PAGEHEADERTOP, value);
	}

	/**
	 * 属性名称-页眉-宽度
	 */
	private static final String PROPERTY_PAGEHEADERWIDTH_NAME = "PageHeaderWidth";

	/**
	 * 页眉-宽度 属性
	 */
	@DbField(name = "PHWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_PAGEHEADERWIDTH = registerProperty(
			PROPERTY_PAGEHEADERWIDTH_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-页眉-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PAGEHEADERWIDTH_NAME)
	public final Integer getPageHeaderWidth() {
		return this.getProperty(PROPERTY_PAGEHEADERWIDTH);
	}

	/**
	 * 设置-页眉-宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setPageHeaderWidth(Integer value) {
		this.setProperty(PROPERTY_PAGEHEADERWIDTH, value);
	}

	/**
	 * 属性名称-页眉-高度
	 */
	private static final String PROPERTY_PAGEHEADERHEIGHT_NAME = "PageHeaderHeight";

	/**
	 * 页眉-高度 属性
	 */
	@DbField(name = "PHHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_PAGEHEADERHEIGHT = registerProperty(
			PROPERTY_PAGEHEADERHEIGHT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-页眉-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PAGEHEADERHEIGHT_NAME)
	public final Integer getPageHeaderHeight() {
		return this.getProperty(PROPERTY_PAGEHEADERHEIGHT);
	}

	/**
	 * 设置-页眉-高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setPageHeaderHeight(Integer value) {
		this.setProperty(PROPERTY_PAGEHEADERHEIGHT, value);
	}

	/**
	 * 属性名称-开始部分-左坐标
	 */
	private static final String PROPERTY_STARTSECTIONLEFT_NAME = "StartSectionLeft";

	/**
	 * 开始部分-左坐标 属性
	 */
	@DbField(name = "SSLeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_STARTSECTIONLEFT = registerProperty(
			PROPERTY_STARTSECTIONLEFT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-开始部分-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_STARTSECTIONLEFT_NAME)
	public final Integer getStartSectionLeft() {
		return this.getProperty(PROPERTY_STARTSECTIONLEFT);
	}

	/**
	 * 设置-开始部分-左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setStartSectionLeft(Integer value) {
		this.setProperty(PROPERTY_STARTSECTIONLEFT, value);
	}

	/**
	 * 属性名称-开始部分-上坐标
	 */
	private static final String PROPERTY_STARTSECTIONTOP_NAME = "StartSectionTop";

	/**
	 * 开始部分-上坐标 属性
	 */
	@DbField(name = "SSTop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_STARTSECTIONTOP = registerProperty(
			PROPERTY_STARTSECTIONTOP_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-开始部分-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_STARTSECTIONTOP_NAME)
	public final Integer getStartSectionTop() {
		return this.getProperty(PROPERTY_STARTSECTIONTOP);
	}

	/**
	 * 设置-开始部分-上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setStartSectionTop(Integer value) {
		this.setProperty(PROPERTY_STARTSECTIONTOP, value);
	}

	/**
	 * 属性名称-开始部分-宽度
	 */
	private static final String PROPERTY_STARTSECTIONWIDTH_NAME = "StartSectionWidth";

	/**
	 * 开始部分-宽度 属性
	 */
	@DbField(name = "SSWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_STARTSECTIONWIDTH = registerProperty(
			PROPERTY_STARTSECTIONWIDTH_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-开始部分-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_STARTSECTIONWIDTH_NAME)
	public final Integer getStartSectionWidth() {
		return this.getProperty(PROPERTY_STARTSECTIONWIDTH);
	}

	/**
	 * 设置-开始部分-宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setStartSectionWidth(Integer value) {
		this.setProperty(PROPERTY_STARTSECTIONWIDTH, value);
	}

	/**
	 * 属性名称-开始部分-高度
	 */
	private static final String PROPERTY_STARTSECTIONHEIGHT_NAME = "StartSectionHeight";

	/**
	 * 开始部分-高度 属性
	 */
	@DbField(name = "SSHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_STARTSECTIONHEIGHT = registerProperty(
			PROPERTY_STARTSECTIONHEIGHT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-开始部分-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_STARTSECTIONHEIGHT_NAME)
	public final Integer getStartSectionHeight() {
		return this.getProperty(PROPERTY_STARTSECTIONHEIGHT);
	}

	/**
	 * 设置-开始部分-高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setStartSectionHeight(Integer value) {
		this.setProperty(PROPERTY_STARTSECTIONHEIGHT, value);
	}

	/**
	 * 属性名称-重复区域头-左坐标
	 */
	private static final String PROPERTY_REPETITIONHEADERLEFT_NAME = "RepetitionHeaderLeft";

	/**
	 * 重复区域头-左坐标 属性
	 */
	@DbField(name = "RAHLeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIONHEADERLEFT = registerProperty(
			PROPERTY_REPETITIONHEADERLEFT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域头-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIONHEADERLEFT_NAME)
	public final Integer getRepetitionHeaderLeft() {
		return this.getProperty(PROPERTY_REPETITIONHEADERLEFT);
	}

	/**
	 * 设置-重复区域头-左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionHeaderLeft(Integer value) {
		this.setProperty(PROPERTY_REPETITIONHEADERLEFT, value);
	}

	/**
	 * 属性名称-重复区域头-上坐标
	 */
	private static final String PROPERTY_REPETITIONHEADERTOP_NAME = "RepetitionHeaderTop";

	/**
	 * 重复区域头-上坐标 属性
	 */
	@DbField(name = "RAHTop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIONHEADERTOP = registerProperty(
			PROPERTY_REPETITIONHEADERTOP_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域头-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIONHEADERTOP_NAME)
	public final Integer getRepetitionHeaderTop() {
		return this.getProperty(PROPERTY_REPETITIONHEADERTOP);
	}

	/**
	 * 设置-重复区域头-上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionHeaderTop(Integer value) {
		this.setProperty(PROPERTY_REPETITIONHEADERTOP, value);
	}

	/**
	 * 属性名称-重复区域头-宽度
	 */
	private static final String PROPERTY_REPETITIONHEADERWIDTH_NAME = "RepetitionHeaderWidth";

	/**
	 * 重复区域头-宽度 属性
	 */
	@DbField(name = "RAHWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIONHEADERWIDTH = registerProperty(
			PROPERTY_REPETITIONHEADERWIDTH_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域头-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIONHEADERWIDTH_NAME)
	public final Integer getRepetitionHeaderWidth() {
		return this.getProperty(PROPERTY_REPETITIONHEADERWIDTH);
	}

	/**
	 * 设置-重复区域头-宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionHeaderWidth(Integer value) {
		this.setProperty(PROPERTY_REPETITIONHEADERWIDTH, value);
	}

	/**
	 * 属性名称-重复区域头-高度
	 */
	private static final String PROPERTY_REPETITIONHEADERHEIGHT_NAME = "RepetitionHeaderHeight";

	/**
	 * 重复区域头-高度 属性
	 */
	@DbField(name = "RAHHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIONHEADERHEIGHT = registerProperty(
			PROPERTY_REPETITIONHEADERHEIGHT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域头-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIONHEADERHEIGHT_NAME)
	public final Integer getRepetitionHeaderHeight() {
		return this.getProperty(PROPERTY_REPETITIONHEADERHEIGHT);
	}

	/**
	 * 设置-重复区域头-高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionHeaderHeight(Integer value) {
		this.setProperty(PROPERTY_REPETITIONHEADERHEIGHT, value);
	}

	/**
	 * 属性名称-重复区域-左坐标
	 */
	private static final String PROPERTY_REPETITIONLEFT_NAME = "RepetitionLeft";

	/**
	 * 重复区域-左坐标 属性
	 */
	@DbField(name = "RALeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIONLEFT = registerProperty(PROPERTY_REPETITIONLEFT_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIONLEFT_NAME)
	public final Integer getRepetitionLeft() {
		return this.getProperty(PROPERTY_REPETITIONLEFT);
	}

	/**
	 * 设置-重复区域-左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionLeft(Integer value) {
		this.setProperty(PROPERTY_REPETITIONLEFT, value);
	}

	/**
	 * 属性名称-重复区域-上坐标
	 */
	private static final String PROPERTY_REPETITIONTOP_NAME = "RepetitionTop";

	/**
	 * 重复区域-上坐标 属性
	 */
	@DbField(name = "RATop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIONTOP = registerProperty(PROPERTY_REPETITIONTOP_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIONTOP_NAME)
	public final Integer getRepetitionTop() {
		return this.getProperty(PROPERTY_REPETITIONTOP);
	}

	/**
	 * 设置-重复区域-上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionTop(Integer value) {
		this.setProperty(PROPERTY_REPETITIONTOP, value);
	}

	/**
	 * 属性名称-重复区域-宽度
	 */
	private static final String PROPERTY_REPETITIONWIDTH_NAME = "RepetitionWidth";

	/**
	 * 重复区域-宽度 属性
	 */
	@DbField(name = "RAWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIONWIDTH = registerProperty(
			PROPERTY_REPETITIONWIDTH_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIONWIDTH_NAME)
	public final Integer getRepetitionWidth() {
		return this.getProperty(PROPERTY_REPETITIONWIDTH);
	}

	/**
	 * 设置-重复区域-宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionWidth(Integer value) {
		this.setProperty(PROPERTY_REPETITIONWIDTH, value);
	}

	/**
	 * 属性名称-重复区域-高度
	 */
	private static final String PROPERTY_REPETITIONHEIGHT_NAME = "RepetitionHeight";

	/**
	 * 重复区域-高度 属性
	 */
	@DbField(name = "RAHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIONHEIGHT = registerProperty(
			PROPERTY_REPETITIONHEIGHT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIONHEIGHT_NAME)
	public final Integer getRepetitionHeight() {
		return this.getProperty(PROPERTY_REPETITIONHEIGHT);
	}

	/**
	 * 设置-重复区域-高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionHeight(Integer value) {
		this.setProperty(PROPERTY_REPETITIONHEIGHT, value);
	}

	/**
	 * 属性名称-重复区域脚-左坐标
	 */
	private static final String PROPERTY_REPETITIONFOOTERLEFT_NAME = "RepetitionFooterLeft";

	/**
	 * 重复区域脚-左坐标 属性
	 */
	@DbField(name = "RAFLeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIONFOOTERLEFT = registerProperty(
			PROPERTY_REPETITIONFOOTERLEFT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域脚-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIONFOOTERLEFT_NAME)
	public final Integer getRepetitionFooterLeft() {
		return this.getProperty(PROPERTY_REPETITIONFOOTERLEFT);
	}

	/**
	 * 设置-重复区域脚-左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionFooterLeft(Integer value) {
		this.setProperty(PROPERTY_REPETITIONFOOTERLEFT, value);
	}

	/**
	 * 属性名称-重复区域脚-上坐标
	 */
	private static final String PROPERTY_REPETITIONFOOTERTOP_NAME = "RepetitionFooterTop";

	/**
	 * 重复区域脚-上坐标 属性
	 */
	@DbField(name = "RAFTop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIONFOOTERTOP = registerProperty(
			PROPERTY_REPETITIONFOOTERTOP_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域脚-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIONFOOTERTOP_NAME)
	public final Integer getRepetitionFooterTop() {
		return this.getProperty(PROPERTY_REPETITIONFOOTERTOP);
	}

	/**
	 * 设置-重复区域脚-上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionFooterTop(Integer value) {
		this.setProperty(PROPERTY_REPETITIONFOOTERTOP, value);
	}

	/**
	 * 属性名称-重复区域脚-宽度
	 */
	private static final String PROPERTY_REPETITIONFOOTERWIDTH_NAME = "RepetitionFooterWidth";

	/**
	 * 重复区域脚-宽度 属性
	 */
	@DbField(name = "RAFWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIONFOOTERWIDTH = registerProperty(
			PROPERTY_REPETITIONFOOTERWIDTH_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域脚-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIONFOOTERWIDTH_NAME)
	public final Integer getRepetitionFooterWidth() {
		return this.getProperty(PROPERTY_REPETITIONFOOTERWIDTH);
	}

	/**
	 * 设置-重复区域脚-宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionFooterWidth(Integer value) {
		this.setProperty(PROPERTY_REPETITIONFOOTERWIDTH, value);
	}

	/**
	 * 属性名称-重复区域脚-高度
	 */
	private static final String PROPERTY_REPETITIONFOOTERHEIGHT_NAME = "RepetitionFooterHeight";

	/**
	 * 重复区域脚-高度 属性
	 */
	@DbField(name = "RAFHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIONFOOTERHEIGHT = registerProperty(
			PROPERTY_REPETITIONFOOTERHEIGHT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域脚-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIONFOOTERHEIGHT_NAME)
	public final Integer getRepetitionFooterHeight() {
		return this.getProperty(PROPERTY_REPETITIONFOOTERHEIGHT);
	}

	/**
	 * 设置-重复区域脚-高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionFooterHeight(Integer value) {
		this.setProperty(PROPERTY_REPETITIONFOOTERHEIGHT, value);
	}

	/**
	 * 属性名称-结束部分-左坐标
	 */
	private static final String PROPERTY_ENDSECTIONLEFT_NAME = "EndSectionLeft";

	/**
	 * 结束部分-左坐标 属性
	 */
	@DbField(name = "ESLeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_ENDSECTIONLEFT = registerProperty(PROPERTY_ENDSECTIONLEFT_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-结束部分-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ENDSECTIONLEFT_NAME)
	public final Integer getEndSectionLeft() {
		return this.getProperty(PROPERTY_ENDSECTIONLEFT);
	}

	/**
	 * 设置-结束部分-左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setEndSectionLeft(Integer value) {
		this.setProperty(PROPERTY_ENDSECTIONLEFT, value);
	}

	/**
	 * 属性名称-结束部分-上坐标
	 */
	private static final String PROPERTY_ENDSECTIONTOP_NAME = "EndSectionTop";

	/**
	 * 结束部分-上坐标 属性
	 */
	@DbField(name = "ESTop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_ENDSECTIONTOP = registerProperty(PROPERTY_ENDSECTIONTOP_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-结束部分-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ENDSECTIONTOP_NAME)
	public final Integer getEndSectionTop() {
		return this.getProperty(PROPERTY_ENDSECTIONTOP);
	}

	/**
	 * 设置-结束部分-上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setEndSectionTop(Integer value) {
		this.setProperty(PROPERTY_ENDSECTIONTOP, value);
	}

	/**
	 * 属性名称-结束部分-宽度
	 */
	private static final String PROPERTY_ENDSECTIONWIDTH_NAME = "EndSectionWidth";

	/**
	 * 结束部分-宽度 属性
	 */
	@DbField(name = "ESWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_ENDSECTIONWIDTH = registerProperty(
			PROPERTY_ENDSECTIONWIDTH_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-结束部分-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ENDSECTIONWIDTH_NAME)
	public final Integer getEndSectionWidth() {
		return this.getProperty(PROPERTY_ENDSECTIONWIDTH);
	}

	/**
	 * 设置-结束部分-宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setEndSectionWidth(Integer value) {
		this.setProperty(PROPERTY_ENDSECTIONWIDTH, value);
	}

	/**
	 * 属性名称-结束部分-高度
	 */
	private static final String PROPERTY_ENDSECTIONHEIGHT_NAME = "EndSectionHeight";

	/**
	 * 结束部分-高度 属性
	 */
	@DbField(name = "ESHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_ENDSECTIONHEIGHT = registerProperty(
			PROPERTY_ENDSECTIONHEIGHT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-结束部分-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ENDSECTIONHEIGHT_NAME)
	public final Integer getEndSectionHeight() {
		return this.getProperty(PROPERTY_ENDSECTIONHEIGHT);
	}

	/**
	 * 设置-结束部分-高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setEndSectionHeight(Integer value) {
		this.setProperty(PROPERTY_ENDSECTIONHEIGHT, value);
	}

	/**
	 * 属性名称-页脚-左坐标
	 */
	private static final String PROPERTY_PAGEFOOTERLEFT_NAME = "PageFooterLeft";

	/**
	 * 页脚-左坐标 属性
	 */
	@DbField(name = "PFLeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_PAGEFOOTERLEFT = registerProperty(PROPERTY_PAGEFOOTERLEFT_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-页脚-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PAGEFOOTERLEFT_NAME)
	public final Integer getPageFooterLeft() {
		return this.getProperty(PROPERTY_PAGEFOOTERLEFT);
	}

	/**
	 * 设置-页脚-左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setPageFooterLeft(Integer value) {
		this.setProperty(PROPERTY_PAGEFOOTERLEFT, value);
	}

	/**
	 * 属性名称-页脚-上坐标
	 */
	private static final String PROPERTY_PAGEFOOTERTOP_NAME = "PageFooterTop";

	/**
	 * 页脚-上坐标 属性
	 */
	@DbField(name = "PFTop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_PAGEFOOTERTOP = registerProperty(PROPERTY_PAGEFOOTERTOP_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-页脚-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PAGEFOOTERTOP_NAME)
	public final Integer getPageFooterTop() {
		return this.getProperty(PROPERTY_PAGEFOOTERTOP);
	}

	/**
	 * 设置-页脚-上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setPageFooterTop(Integer value) {
		this.setProperty(PROPERTY_PAGEFOOTERTOP, value);
	}

	/**
	 * 属性名称-页脚-宽度
	 */
	private static final String PROPERTY_PAGEFOOTERWIDTH_NAME = "PageFooterWidth";

	/**
	 * 页脚-宽度 属性
	 */
	@DbField(name = "PFWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_PAGEFOOTERWIDTH = registerProperty(
			PROPERTY_PAGEFOOTERWIDTH_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-页脚-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PAGEFOOTERWIDTH_NAME)
	public final Integer getPageFooterWidth() {
		return this.getProperty(PROPERTY_PAGEFOOTERWIDTH);
	}

	/**
	 * 设置-页脚-宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setPageFooterWidth(Integer value) {
		this.setProperty(PROPERTY_PAGEFOOTERWIDTH, value);
	}

	/**
	 * 属性名称-页脚-高度
	 */
	private static final String PROPERTY_PAGEFOOTERHEIGHT_NAME = "PageFooterHeight";

	/**
	 * 页脚-高度 属性
	 */
	@DbField(name = "PFHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_PAGEFOOTERHEIGHT = registerProperty(
			PROPERTY_PAGEFOOTERHEIGHT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-页脚-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PAGEFOOTERHEIGHT_NAME)
	public final Integer getPageFooterHeight() {
		return this.getProperty(PROPERTY_PAGEFOOTERHEIGHT);
	}

	/**
	 * 设置-页脚-高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setPageFooterHeight(Integer value) {
		this.setProperty(PROPERTY_PAGEFOOTERHEIGHT, value);
	}

	/**
	 * 属性名称-导出模板-页眉
	 */
	private static final String PROPERTY_PAGEHEADERS_NAME = "PageHeaders";

	/**
	 * 导出模板-页眉的集合属性
	 * 
	 */
	public static final IPropertyInfo<IExportTemplateItems> PROPERTY_PAGEHEADERS = registerProperty(
			PROPERTY_PAGEHEADERS_NAME, IExportTemplateItems.class, MY_CLASS);

	/**
	 * 获取-导出模板-页眉
	 * 
	 * @return 值
	 */
	@XmlElementWrapper(name = PROPERTY_PAGEHEADERS_NAME)
	@XmlElement(name = ExportTemplateItem.BUSINESS_OBJECT_NAME, type = ExportTemplateItem.class)
	public final IExportTemplateItems getPageHeaders() {
		return this.getProperty(PROPERTY_PAGEHEADERS);
	}

	/**
	 * 设置-导出模板-页眉
	 * 
	 * @param value
	 *            值
	 */
	public final void setPageHeaders(IExportTemplateItems value) {
		this.setProperty(PROPERTY_PAGEHEADERS, value);
	}

	/**
	 * 属性名称-导出模板-开始区
	 */
	private static final String PROPERTY_STARTSECTIONS_NAME = "StartSections";

	/**
	 * 导出模板-开始区的集合属性
	 * 
	 */
	public static final IPropertyInfo<IExportTemplateItems> PROPERTY_STARTSECTIONS = registerProperty(
			PROPERTY_STARTSECTIONS_NAME, IExportTemplateItems.class, MY_CLASS);

	/**
	 * 获取-导出模板-开始区
	 * 
	 * @return 值
	 */
	@XmlElementWrapper(name = PROPERTY_STARTSECTIONS_NAME)
	@XmlElement(name = ExportTemplateItem.BUSINESS_OBJECT_NAME, type = ExportTemplateItem.class)
	public final IExportTemplateItems getStartSections() {
		return this.getProperty(PROPERTY_STARTSECTIONS);
	}

	/**
	 * 设置-导出模板-开始区
	 * 
	 * @param value
	 *            值
	 */
	public final void setStartSections(IExportTemplateItems value) {
		this.setProperty(PROPERTY_STARTSECTIONS, value);
	}

	/**
	 * 属性名称-导出模板-重复区头
	 */
	private static final String PROPERTY_REPETITIONHEADERS_NAME = "RepetitionHeaders";

	/**
	 * 导出模板-重复区头的集合属性
	 * 
	 */
	public static final IPropertyInfo<IExportTemplateItems> PROPERTY_REPETITIONHEADERS = registerProperty(
			PROPERTY_REPETITIONHEADERS_NAME, IExportTemplateItems.class, MY_CLASS);

	/**
	 * 获取-导出模板-重复区头
	 * 
	 * @return 值
	 */
	@XmlElementWrapper(name = PROPERTY_REPETITIONHEADERS_NAME)
	@XmlElement(name = ExportTemplateItem.BUSINESS_OBJECT_NAME, type = ExportTemplateItem.class)
	public final IExportTemplateItems getRepetitionHeaders() {
		return this.getProperty(PROPERTY_REPETITIONHEADERS);
	}

	/**
	 * 设置-导出模板-重复区头
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionHeaders(IExportTemplateItems value) {
		this.setProperty(PROPERTY_REPETITIONHEADERS, value);
	}

	/**
	 * 属性名称-导出模板-重复区
	 */
	private static final String PROPERTY_REPETITIONS_NAME = "Repetitions";

	/**
	 * 导出模板-重复区的集合属性
	 * 
	 */
	public static final IPropertyInfo<IExportTemplateItems> PROPERTY_REPETITIONS = registerProperty(
			PROPERTY_REPETITIONS_NAME, IExportTemplateItems.class, MY_CLASS);

	/**
	 * 获取-导出模板-重复区
	 * 
	 * @return 值
	 */
	@XmlElementWrapper(name = PROPERTY_REPETITIONS_NAME)
	@XmlElement(name = ExportTemplateItem.BUSINESS_OBJECT_NAME, type = ExportTemplateItem.class)
	public final IExportTemplateItems getRepetitions() {
		return this.getProperty(PROPERTY_REPETITIONS);
	}

	/**
	 * 设置-导出模板-重复区
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitions(IExportTemplateItems value) {
		this.setProperty(PROPERTY_REPETITIONS, value);
	}

	/**
	 * 属性名称-导出模板-重复区脚
	 */
	private static final String PROPERTY_REPETITIONFOOTERS_NAME = "RepetitionFooters";

	/**
	 * 导出模板-重复区脚的集合属性
	 * 
	 */
	public static final IPropertyInfo<IExportTemplateItems> PROPERTY_REPETITIONFOOTERS = registerProperty(
			PROPERTY_REPETITIONFOOTERS_NAME, IExportTemplateItems.class, MY_CLASS);

	/**
	 * 获取-导出模板-重复区脚
	 * 
	 * @return 值
	 */
	@XmlElementWrapper(name = PROPERTY_REPETITIONFOOTERS_NAME)
	@XmlElement(name = ExportTemplateItem.BUSINESS_OBJECT_NAME, type = ExportTemplateItem.class)
	public final IExportTemplateItems getRepetitionFooters() {
		return this.getProperty(PROPERTY_REPETITIONFOOTERS);
	}

	/**
	 * 设置-导出模板-重复区脚
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitionFooters(IExportTemplateItems value) {
		this.setProperty(PROPERTY_REPETITIONFOOTERS, value);
	}

	/**
	 * 属性名称-导出模板-结束区
	 */
	private static final String PROPERTY_ENDSECTIONS_NAME = "EndSections";

	/**
	 * 导出模板-结束区的集合属性
	 * 
	 */
	public static final IPropertyInfo<IExportTemplateItems> PROPERTY_ENDSECTIONS = registerProperty(
			PROPERTY_ENDSECTIONS_NAME, IExportTemplateItems.class, MY_CLASS);

	/**
	 * 获取-导出模板-结束区
	 * 
	 * @return 值
	 */
	@XmlElementWrapper(name = PROPERTY_ENDSECTIONS_NAME)
	@XmlElement(name = ExportTemplateItem.BUSINESS_OBJECT_NAME, type = ExportTemplateItem.class)
	public final IExportTemplateItems getEndSections() {
		return this.getProperty(PROPERTY_ENDSECTIONS);
	}

	/**
	 * 设置-导出模板-结束区
	 * 
	 * @param value
	 *            值
	 */
	public final void setEndSections(IExportTemplateItems value) {
		this.setProperty(PROPERTY_ENDSECTIONS, value);
	}

	/**
	 * 属性名称-导出模板-页脚区
	 */
	private static final String PROPERTY_PAGEFOOTERS_NAME = "PageFooters";

	/**
	 * 导出模板-页脚区的集合属性
	 * 
	 */
	public static final IPropertyInfo<IExportTemplateItems> PROPERTY_PAGEFOOTERS = registerProperty(
			PROPERTY_PAGEFOOTERS_NAME, IExportTemplateItems.class, MY_CLASS);

	/**
	 * 获取-导出模板-页脚区
	 * 
	 * @return 值
	 */
	@XmlElementWrapper(name = PROPERTY_PAGEFOOTERS_NAME)
	@XmlElement(name = ExportTemplateItem.BUSINESS_OBJECT_NAME, type = ExportTemplateItem.class)
	public final IExportTemplateItems getPageFooters() {
		return this.getProperty(PROPERTY_PAGEFOOTERS);
	}

	/**
	 * 设置-导出模板-页脚区
	 * 
	 * @param value
	 *            值
	 */
	public final void setPageFooters(IExportTemplateItems value) {
		this.setProperty(PROPERTY_PAGEFOOTERS, value);
	}

	/**
	 * 初始化数据
	 */
	@Override
	protected void initialize() {
		super.initialize();// 基类初始化，不可去除
		this.setObjectCode(MyConfiguration.applyVariables(BUSINESS_OBJECT_CODE));
		this.setPageHeaders(new ExportTemplateItems(this, emAreaType.PAGE_HEADER));
		this.setStartSections(new ExportTemplateItems(this, emAreaType.START_SECTION));
		this.setRepetitionHeaders(new ExportTemplateItems(this, emAreaType.REPETITION_HEADER));
		this.setRepetitions(new ExportTemplateItems(this, emAreaType.REPETITION));
		this.setRepetitionFooters(new ExportTemplateItems(this, emAreaType.REPETITION_FOOTER));
		this.setEndSections(new ExportTemplateItems(this, emAreaType.END_SECTION));
		this.setPageFooters(new ExportTemplateItems(this, emAreaType.PAGE_FOOTER));
		this.setActivated(emYesNo.YES);
	}

}
