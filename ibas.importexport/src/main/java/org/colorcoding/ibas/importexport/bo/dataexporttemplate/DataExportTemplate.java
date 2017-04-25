package org.colorcoding.ibas.importexport.bo.dataexporttemplate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.core.IPropertyInfo;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.emApprovalStatus;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.mapping.BOCode;
import org.colorcoding.ibas.bobas.mapping.DbField;
import org.colorcoding.ibas.bobas.mapping.DbFieldType;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.MyConsts;
import org.colorcoding.ibas.importexport.data.emPaperSize;

/**
 * 获取-数据导出模板
 * 
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = DataExportTemplate.BUSINESS_OBJECT_NAME, namespace = MyConsts.NAMESPACE_BO)
@XmlRootElement(name = DataExportTemplate.BUSINESS_OBJECT_NAME, namespace = MyConsts.NAMESPACE_BO)
@BOCode(DataExportTemplate.BUSINESS_OBJECT_CODE)
public class DataExportTemplate extends BusinessObject<DataExportTemplate> implements IDataExportTemplate {

	/**
	 * 序列化版本标记
	 */
	private static final long serialVersionUID = -6055737609031073385L;

	/**
	 * 当前类型
	 */
	private static final Class<?> MY_CLASS = DataExportTemplate.class;

	/**
	 * 数据库表
	 */
	public static final String DB_TABLE_NAME = "${Company}_IE_ODET";

	/**
	 * 业务对象编码
	 */
	public static final String BUSINESS_OBJECT_CODE = "${Company}_IE_EXPORTTEMPLATE";

	/**
	 * 业务对象名称
	 */
	public static final String BUSINESS_OBJECT_NAME = "DataExportTemplate";

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
	 * 属性名称-审批状态
	 */
	private static final String PROPERTY_APPROVALSTATUS_NAME = "ApprovalStatus";

	/**
	 * 审批状态 属性
	 */
	@DbField(name = "ApvlStatus", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emApprovalStatus> PROPERTY_APPROVALSTATUS = registerProperty(
			PROPERTY_APPROVALSTATUS_NAME, emApprovalStatus.class, MY_CLASS);

	/**
	 * 获取-审批状态
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_APPROVALSTATUS_NAME)
	public final emApprovalStatus getApprovalStatus() {
		return this.getProperty(PROPERTY_APPROVALSTATUS);
	}

	/**
	 * 设置-审批状态
	 * 
	 * @param value
	 *            值
	 */
	public final void setApprovalStatus(emApprovalStatus value) {
		this.setProperty(PROPERTY_APPROVALSTATUS, value);
	}

	/**
	 * 属性名称-参考1
	 */
	private static final String PROPERTY_REFERENCE1_NAME = "Reference1";

	/**
	 * 参考1 属性
	 */
	@DbField(name = "Ref1", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_REFERENCE1 = registerProperty(PROPERTY_REFERENCE1_NAME,
			String.class, MY_CLASS);

	/**
	 * 获取-参考1
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REFERENCE1_NAME)
	public final String getReference1() {
		return this.getProperty(PROPERTY_REFERENCE1);
	}

	/**
	 * 设置-参考1
	 * 
	 * @param value
	 *            值
	 */
	public final void setReference1(String value) {
		this.setProperty(PROPERTY_REFERENCE1, value);
	}

	/**
	 * 属性名称-参考2
	 */
	private static final String PROPERTY_REFERENCE2_NAME = "Reference2";

	/**
	 * 参考2 属性
	 */
	@DbField(name = "Ref2", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_REFERENCE2 = registerProperty(PROPERTY_REFERENCE2_NAME,
			String.class, MY_CLASS);

	/**
	 * 获取-参考2
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REFERENCE2_NAME)
	public final String getReference2() {
		return this.getProperty(PROPERTY_REFERENCE2);
	}

	/**
	 * 设置-参考2
	 * 
	 * @param value
	 *            值
	 */
	public final void setReference2(String value) {
		this.setProperty(PROPERTY_REFERENCE2, value);
	}

	/**
	 * 属性名称-备注
	 */
	private static final String PROPERTY_REMARKS_NAME = "Remarks";

	/**
	 * 备注 属性
	 */
	@DbField(name = "Remarks", type = DbFieldType.MEMO, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_REMARKS = registerProperty(PROPERTY_REMARKS_NAME, String.class,
			MY_CLASS);

	/**
	 * 获取-备注
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REMARKS_NAME)
	public final String getRemarks() {
		return this.getProperty(PROPERTY_REMARKS);
	}

	/**
	 * 设置-备注
	 * 
	 * @param value
	 *            值
	 */
	public final void setRemarks(String value) {
		this.setProperty(PROPERTY_REMARKS, value);
	}

	/**
	 * 属性名称-编码
	 */
	private static final String PROPERTY_CODE_NAME = "Code";

	/**
	 * 编码 属性
	 */
	@DbField(name = "Code", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_CODE = registerProperty(PROPERTY_CODE_NAME, String.class,
			MY_CLASS);

	/**
	 * 获取-编码
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_CODE_NAME)
	public final String getCode() {
		return this.getProperty(PROPERTY_CODE);
	}

	/**
	 * 设置-编码
	 * 
	 * @param value
	 *            值
	 */
	public final void setCode(String value) {
		this.setProperty(PROPERTY_CODE, value);
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
	private static final String PROPERTY_LEFTMARGIN_NAME = "LeftMargin";

	/**
	 * 左边距 属性
	 */
	@DbField(name = "LMargin", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_LEFTMARGIN = registerProperty(PROPERTY_LEFTMARGIN_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-左边距
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_LEFTMARGIN_NAME)
	public final Integer getLeftMargin() {
		return this.getProperty(PROPERTY_LEFTMARGIN);
	}

	/**
	 * 设置-左边距
	 * 
	 * @param value
	 *            值
	 */
	public final void setLeftMargin(Integer value) {
		this.setProperty(PROPERTY_LEFTMARGIN, value);
	}

	/**
	 * 属性名称-右边距
	 */
	private static final String PROPERTY_RIGHTMARGIN_NAME = "RightMargin";

	/**
	 * 右边距 属性
	 */
	@DbField(name = "RMargin", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_RIGHTMARGIN = registerProperty(PROPERTY_RIGHTMARGIN_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-右边距
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_RIGHTMARGIN_NAME)
	public final Integer getRightMargin() {
		return this.getProperty(PROPERTY_RIGHTMARGIN);
	}

	/**
	 * 设置-右边距
	 * 
	 * @param value
	 *            值
	 */
	public final void setRightMargin(Integer value) {
		this.setProperty(PROPERTY_RIGHTMARGIN, value);
	}

	/**
	 * 属性名称-上边距
	 */
	private static final String PROPERTY_TOPMARGIN_NAME = "TopMargin";

	/**
	 * 上边距 属性
	 */
	@DbField(name = "TMargin", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_TOPMARGIN = registerProperty(PROPERTY_TOPMARGIN_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-上边距
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_TOPMARGIN_NAME)
	public final Integer getTopMargin() {
		return this.getProperty(PROPERTY_TOPMARGIN);
	}

	/**
	 * 设置-上边距
	 * 
	 * @param value
	 *            值
	 */
	public final void setTopMargin(Integer value) {
		this.setProperty(PROPERTY_TOPMARGIN, value);
	}

	/**
	 * 属性名称-下边距
	 */
	private static final String PROPERTY_BOTTOMMARGIN_NAME = "BottomMargin";

	/**
	 * 下边距 属性
	 */
	@DbField(name = "BMargin", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BOTTOMMARGIN = registerProperty(PROPERTY_BOTTOMMARGIN_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-下边距
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BOTTOMMARGIN_NAME)
	public final Integer getBottomMargin() {
		return this.getProperty(PROPERTY_BOTTOMMARGIN);
	}

	/**
	 * 设置-下边距
	 * 
	 * @param value
	 *            值
	 */
	public final void setBottomMargin(Integer value) {
		this.setProperty(PROPERTY_BOTTOMMARGIN, value);
	}

	/**
	 * 属性名称-是否可以修改
	 */
	private static final String PROPERTY_CANCHANGE_NAME = "CanChange";

	/**
	 * 是否可以修改 属性
	 */
	@DbField(name = "CanChange", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emYesNo> PROPERTY_CANCHANGE = registerProperty(PROPERTY_CANCHANGE_NAME,
			emYesNo.class, MY_CLASS);

	/**
	 * 获取-是否可以修改
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_CANCHANGE_NAME)
	public final emYesNo getCanChange() {
		return this.getProperty(PROPERTY_CANCHANGE);
	}

	/**
	 * 设置-是否可以修改
	 * 
	 * @param value
	 *            值
	 */
	public final void setCanChange(emYesNo value) {
		this.setProperty(PROPERTY_CANCHANGE, value);
	}

	/**
	 * 属性名称-纸张大小
	 */
	private static final String PROPERTY_PAPERSIZE_NAME = "PaperSize";

	/**
	 * 纸张大小 属性
	 */
	@DbField(name = "PaperSize", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emPaperSize> PROPERTY_PAPERSIZE = registerProperty(PROPERTY_PAPERSIZE_NAME,
			emPaperSize.class, MY_CLASS);

	/**
	 * 获取-纸张大小
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PAPERSIZE_NAME)
	public final emPaperSize getPaperSize() {
		return this.getProperty(PROPERTY_PAPERSIZE);
	}

	/**
	 * 设置-纸张大小
	 * 
	 * @param value
	 *            值
	 */
	public final void setPaperSize(emPaperSize value) {
		this.setProperty(PROPERTY_PAPERSIZE, value);
	}

	/**
	 * 属性名称-语言编码
	 */
	private static final String PROPERTY_LANGUAGECODE_NAME = "LanguageCode";

	/**
	 * 语言编码 属性
	 */
	@DbField(name = "Language", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_LANGUAGECODE = registerProperty(PROPERTY_LANGUAGECODE_NAME,
			String.class, MY_CLASS);

	/**
	 * 获取-语言编码
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_LANGUAGECODE_NAME)
	public final String getLanguageCode() {
		return this.getProperty(PROPERTY_LANGUAGECODE);
	}

	/**
	 * 设置-语言编码
	 * 
	 * @param value
	 *            值
	 */
	public final void setLanguageCode(String value) {
		this.setProperty(PROPERTY_LANGUAGECODE, value);
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
	private static final String PROPERTY_STARTOFSECTIONLEFT_NAME = "StartOfSectionLeft";

	/**
	 * 开始部分-左坐标 属性
	 */
	@DbField(name = "SSLeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_STARTOFSECTIONLEFT = registerProperty(
			PROPERTY_STARTOFSECTIONLEFT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-开始部分-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_STARTOFSECTIONLEFT_NAME)
	public final Integer getStartOfSectionLeft() {
		return this.getProperty(PROPERTY_STARTOFSECTIONLEFT);
	}

	/**
	 * 设置-开始部分-左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setStartOfSectionLeft(Integer value) {
		this.setProperty(PROPERTY_STARTOFSECTIONLEFT, value);
	}

	/**
	 * 属性名称-开始部分-上坐标
	 */
	private static final String PROPERTY_STARTOFSECTIONTOP_NAME = "StartOfSectionTop";

	/**
	 * 开始部分-上坐标 属性
	 */
	@DbField(name = "SSTop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_STARTOFSECTIONTOP = registerProperty(
			PROPERTY_STARTOFSECTIONTOP_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-开始部分-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_STARTOFSECTIONTOP_NAME)
	public final Integer getStartOfSectionTop() {
		return this.getProperty(PROPERTY_STARTOFSECTIONTOP);
	}

	/**
	 * 设置-开始部分-上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setStartOfSectionTop(Integer value) {
		this.setProperty(PROPERTY_STARTOFSECTIONTOP, value);
	}

	/**
	 * 属性名称-开始部分-宽度
	 */
	private static final String PROPERTY_STARTOFSECTIONWIDTH_NAME = "StartOfSectionWidth";

	/**
	 * 开始部分-宽度 属性
	 */
	@DbField(name = "SSWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_STARTOFSECTIONWIDTH = registerProperty(
			PROPERTY_STARTOFSECTIONWIDTH_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-开始部分-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_STARTOFSECTIONWIDTH_NAME)
	public final Integer getStartOfSectionWidth() {
		return this.getProperty(PROPERTY_STARTOFSECTIONWIDTH);
	}

	/**
	 * 设置-开始部分-宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setStartOfSectionWidth(Integer value) {
		this.setProperty(PROPERTY_STARTOFSECTIONWIDTH, value);
	}

	/**
	 * 属性名称-开始部分-高度
	 */
	private static final String PROPERTY_STARTOFSECTIONHEIGHT_NAME = "StartOfSectionHeight";

	/**
	 * 开始部分-高度 属性
	 */
	@DbField(name = "SSHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_STARTOFSECTIONHEIGHT = registerProperty(
			PROPERTY_STARTOFSECTIONHEIGHT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-开始部分-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_STARTOFSECTIONHEIGHT_NAME)
	public final Integer getStartOfSectionHeight() {
		return this.getProperty(PROPERTY_STARTOFSECTIONHEIGHT);
	}

	/**
	 * 设置-开始部分-高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setStartOfSectionHeight(Integer value) {
		this.setProperty(PROPERTY_STARTOFSECTIONHEIGHT, value);
	}

	/**
	 * 属性名称-重复区域头-左坐标
	 */
	private static final String PROPERTY_REPETITIVEAREAHEADERLEFT_NAME = "RepetitiveAreaHeaderLeft";

	/**
	 * 重复区域头-左坐标 属性
	 */
	@DbField(name = "RAHLeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIVEAREAHEADERLEFT = registerProperty(
			PROPERTY_REPETITIVEAREAHEADERLEFT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域头-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIVEAREAHEADERLEFT_NAME)
	public final Integer getRepetitiveAreaHeaderLeft() {
		return this.getProperty(PROPERTY_REPETITIVEAREAHEADERLEFT);
	}

	/**
	 * 设置-重复区域头-左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitiveAreaHeaderLeft(Integer value) {
		this.setProperty(PROPERTY_REPETITIVEAREAHEADERLEFT, value);
	}

	/**
	 * 属性名称-重复区域头-上坐标
	 */
	private static final String PROPERTY_REPETITIVEAREAHEADERTOP_NAME = "RepetitiveAreaHeaderTop";

	/**
	 * 重复区域头-上坐标 属性
	 */
	@DbField(name = "RAHTop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIVEAREAHEADERTOP = registerProperty(
			PROPERTY_REPETITIVEAREAHEADERTOP_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域头-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIVEAREAHEADERTOP_NAME)
	public final Integer getRepetitiveAreaHeaderTop() {
		return this.getProperty(PROPERTY_REPETITIVEAREAHEADERTOP);
	}

	/**
	 * 设置-重复区域头-上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitiveAreaHeaderTop(Integer value) {
		this.setProperty(PROPERTY_REPETITIVEAREAHEADERTOP, value);
	}

	/**
	 * 属性名称-重复区域头-宽度
	 */
	private static final String PROPERTY_REPETITIVEAREAHEADERWIDTH_NAME = "RepetitiveAreaHeaderWidth";

	/**
	 * 重复区域头-宽度 属性
	 */
	@DbField(name = "RAHWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIVEAREAHEADERWIDTH = registerProperty(
			PROPERTY_REPETITIVEAREAHEADERWIDTH_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域头-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIVEAREAHEADERWIDTH_NAME)
	public final Integer getRepetitiveAreaHeaderWidth() {
		return this.getProperty(PROPERTY_REPETITIVEAREAHEADERWIDTH);
	}

	/**
	 * 设置-重复区域头-宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitiveAreaHeaderWidth(Integer value) {
		this.setProperty(PROPERTY_REPETITIVEAREAHEADERWIDTH, value);
	}

	/**
	 * 属性名称-重复区域头-高度
	 */
	private static final String PROPERTY_REPETITIVEAREAHEADERHEIGHT_NAME = "RepetitiveAreaHeaderHeight";

	/**
	 * 重复区域头-高度 属性
	 */
	@DbField(name = "RAHHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIVEAREAHEADERHEIGHT = registerProperty(
			PROPERTY_REPETITIVEAREAHEADERHEIGHT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域头-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIVEAREAHEADERHEIGHT_NAME)
	public final Integer getRepetitiveAreaHeaderHeight() {
		return this.getProperty(PROPERTY_REPETITIVEAREAHEADERHEIGHT);
	}

	/**
	 * 设置-重复区域头-高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitiveAreaHeaderHeight(Integer value) {
		this.setProperty(PROPERTY_REPETITIVEAREAHEADERHEIGHT, value);
	}

	/**
	 * 属性名称-重复区域-左坐标
	 */
	private static final String PROPERTY_REPETITIVEAREALEFT_NAME = "RepetitiveAreaLeft";

	/**
	 * 重复区域-左坐标 属性
	 */
	@DbField(name = "RALeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIVEAREALEFT = registerProperty(
			PROPERTY_REPETITIVEAREALEFT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIVEAREALEFT_NAME)
	public final Integer getRepetitiveAreaLeft() {
		return this.getProperty(PROPERTY_REPETITIVEAREALEFT);
	}

	/**
	 * 设置-重复区域-左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitiveAreaLeft(Integer value) {
		this.setProperty(PROPERTY_REPETITIVEAREALEFT, value);
	}

	/**
	 * 属性名称-重复区域-上坐标
	 */
	private static final String PROPERTY_REPETITIVEAREATOP_NAME = "RepetitiveAreaTop";

	/**
	 * 重复区域-上坐标 属性
	 */
	@DbField(name = "RATop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIVEAREATOP = registerProperty(
			PROPERTY_REPETITIVEAREATOP_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIVEAREATOP_NAME)
	public final Integer getRepetitiveAreaTop() {
		return this.getProperty(PROPERTY_REPETITIVEAREATOP);
	}

	/**
	 * 设置-重复区域-上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitiveAreaTop(Integer value) {
		this.setProperty(PROPERTY_REPETITIVEAREATOP, value);
	}

	/**
	 * 属性名称-重复区域-宽度
	 */
	private static final String PROPERTY_REPETITIVEAREAWIDTH_NAME = "RepetitiveAreaWidth";

	/**
	 * 重复区域-宽度 属性
	 */
	@DbField(name = "RAWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIVEAREAWIDTH = registerProperty(
			PROPERTY_REPETITIVEAREAWIDTH_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIVEAREAWIDTH_NAME)
	public final Integer getRepetitiveAreaWidth() {
		return this.getProperty(PROPERTY_REPETITIVEAREAWIDTH);
	}

	/**
	 * 设置-重复区域-宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitiveAreaWidth(Integer value) {
		this.setProperty(PROPERTY_REPETITIVEAREAWIDTH, value);
	}

	/**
	 * 属性名称-重复区域-高度
	 */
	private static final String PROPERTY_REPETITIVEAREAHEIGHT_NAME = "RepetitiveAreaHeight";

	/**
	 * 重复区域-高度 属性
	 */
	@DbField(name = "RAHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIVEAREAHEIGHT = registerProperty(
			PROPERTY_REPETITIVEAREAHEIGHT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIVEAREAHEIGHT_NAME)
	public final Integer getRepetitiveAreaHeight() {
		return this.getProperty(PROPERTY_REPETITIVEAREAHEIGHT);
	}

	/**
	 * 设置-重复区域-高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitiveAreaHeight(Integer value) {
		this.setProperty(PROPERTY_REPETITIVEAREAHEIGHT, value);
	}

	/**
	 * 属性名称-重复区域脚-左坐标
	 */
	private static final String PROPERTY_REPETITIVEAREAFOOTERLEFT_NAME = "RepetitiveAreaFooterLeft";

	/**
	 * 重复区域脚-左坐标 属性
	 */
	@DbField(name = "RAFLeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIVEAREAFOOTERLEFT = registerProperty(
			PROPERTY_REPETITIVEAREAFOOTERLEFT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域脚-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIVEAREAFOOTERLEFT_NAME)
	public final Integer getRepetitiveAreaFooterLeft() {
		return this.getProperty(PROPERTY_REPETITIVEAREAFOOTERLEFT);
	}

	/**
	 * 设置-重复区域脚-左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitiveAreaFooterLeft(Integer value) {
		this.setProperty(PROPERTY_REPETITIVEAREAFOOTERLEFT, value);
	}

	/**
	 * 属性名称-重复区域脚-上坐标
	 */
	private static final String PROPERTY_REPETITIVEAREAFOOTERTOP_NAME = "RepetitiveAreaFooterTop";

	/**
	 * 重复区域脚-上坐标 属性
	 */
	@DbField(name = "RAFTop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIVEAREAFOOTERTOP = registerProperty(
			PROPERTY_REPETITIVEAREAFOOTERTOP_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域脚-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIVEAREAFOOTERTOP_NAME)
	public final Integer getRepetitiveAreaFooterTop() {
		return this.getProperty(PROPERTY_REPETITIVEAREAFOOTERTOP);
	}

	/**
	 * 设置-重复区域脚-上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitiveAreaFooterTop(Integer value) {
		this.setProperty(PROPERTY_REPETITIVEAREAFOOTERTOP, value);
	}

	/**
	 * 属性名称-重复区域脚-宽度
	 */
	private static final String PROPERTY_REPETITIVEAREAFOOTERWIDTH_NAME = "RepetitiveAreaFooterWidth";

	/**
	 * 重复区域脚-宽度 属性
	 */
	@DbField(name = "RAFWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIVEAREAFOOTERWIDTH = registerProperty(
			PROPERTY_REPETITIVEAREAFOOTERWIDTH_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域脚-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIVEAREAFOOTERWIDTH_NAME)
	public final Integer getRepetitiveAreaFooterWidth() {
		return this.getProperty(PROPERTY_REPETITIVEAREAFOOTERWIDTH);
	}

	/**
	 * 设置-重复区域脚-宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitiveAreaFooterWidth(Integer value) {
		this.setProperty(PROPERTY_REPETITIVEAREAFOOTERWIDTH, value);
	}

	/**
	 * 属性名称-重复区域脚-高度
	 */
	private static final String PROPERTY_REPETITIVEAREAFOOTERHEIGHT_NAME = "RepetitiveAreaFooterHeight";

	/**
	 * 重复区域脚-高度 属性
	 */
	@DbField(name = "RAFHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_REPETITIVEAREAFOOTERHEIGHT = registerProperty(
			PROPERTY_REPETITIVEAREAFOOTERHEIGHT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-重复区域脚-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_REPETITIVEAREAFOOTERHEIGHT_NAME)
	public final Integer getRepetitiveAreaFooterHeight() {
		return this.getProperty(PROPERTY_REPETITIVEAREAFOOTERHEIGHT);
	}

	/**
	 * 设置-重复区域脚-高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRepetitiveAreaFooterHeight(Integer value) {
		this.setProperty(PROPERTY_REPETITIVEAREAFOOTERHEIGHT, value);
	}

	/**
	 * 属性名称-结束部分-左坐标
	 */
	private static final String PROPERTY_ENDOFSECTIONLEFT_NAME = "EndOfSectionLeft";

	/**
	 * 结束部分-左坐标 属性
	 */
	@DbField(name = "ESLeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_ENDOFSECTIONLEFT = registerProperty(
			PROPERTY_ENDOFSECTIONLEFT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-结束部分-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ENDOFSECTIONLEFT_NAME)
	public final Integer getEndOfSectionLeft() {
		return this.getProperty(PROPERTY_ENDOFSECTIONLEFT);
	}

	/**
	 * 设置-结束部分-左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setEndOfSectionLeft(Integer value) {
		this.setProperty(PROPERTY_ENDOFSECTIONLEFT, value);
	}

	/**
	 * 属性名称-结束部分-上坐标
	 */
	private static final String PROPERTY_ENDOFSECTIONTOP_NAME = "EndOfSectionTop";

	/**
	 * 结束部分-上坐标 属性
	 */
	@DbField(name = "ESTop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_ENDOFSECTIONTOP = registerProperty(
			PROPERTY_ENDOFSECTIONTOP_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-结束部分-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ENDOFSECTIONTOP_NAME)
	public final Integer getEndOfSectionTop() {
		return this.getProperty(PROPERTY_ENDOFSECTIONTOP);
	}

	/**
	 * 设置-结束部分-上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setEndOfSectionTop(Integer value) {
		this.setProperty(PROPERTY_ENDOFSECTIONTOP, value);
	}

	/**
	 * 属性名称-结束部分-宽度
	 */
	private static final String PROPERTY_ENDOFSECTIONWIDTH_NAME = "EndOfSectionWidth";

	/**
	 * 结束部分-宽度 属性
	 */
	@DbField(name = "ESWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_ENDOFSECTIONWIDTH = registerProperty(
			PROPERTY_ENDOFSECTIONWIDTH_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-结束部分-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ENDOFSECTIONWIDTH_NAME)
	public final Integer getEndOfSectionWidth() {
		return this.getProperty(PROPERTY_ENDOFSECTIONWIDTH);
	}

	/**
	 * 设置-结束部分-宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setEndOfSectionWidth(Integer value) {
		this.setProperty(PROPERTY_ENDOFSECTIONWIDTH, value);
	}

	/**
	 * 属性名称-结束部分-高度
	 */
	private static final String PROPERTY_ENDOFSECTIONHEIGHT_NAME = "EndOfSectionHeight";

	/**
	 * 结束部分-高度 属性
	 */
	@DbField(name = "ESHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_ENDOFSECTIONHEIGHT = registerProperty(
			PROPERTY_ENDOFSECTIONHEIGHT_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-结束部分-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ENDOFSECTIONHEIGHT_NAME)
	public final Integer getEndOfSectionHeight() {
		return this.getProperty(PROPERTY_ENDOFSECTIONHEIGHT);
	}

	/**
	 * 设置-结束部分-高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setEndOfSectionHeight(Integer value) {
		this.setProperty(PROPERTY_ENDOFSECTIONHEIGHT, value);
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
	 * 属性名称-区域间隔
	 */
	private static final String PROPERTY_AREAMARGIN_NAME = "AreaMargin";

	/**
	 * 区域间隔 属性
	 */
	@DbField(name = "AreaMargin", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_AREAMARGIN = registerProperty(PROPERTY_AREAMARGIN_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-区域间隔
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_AREAMARGIN_NAME)
	public final Integer getAreaMargin() {
		return this.getProperty(PROPERTY_AREAMARGIN);
	}

	/**
	 * 设置-区域间隔
	 * 
	 * @param value
	 *            值
	 */
	public final void setAreaMargin(Integer value) {
		this.setProperty(PROPERTY_AREAMARGIN, value);
	}

	/**
	 * 属性名称-业务对象编码
	 */
	private static final String PROPERTY_BOCODE_NAME = "BOCode";

	/**
	 * 业务对象编码 属性
	 */
	@DbField(name = "BOCode", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_BOCODE = registerProperty(PROPERTY_BOCODE_NAME, String.class,
			MY_CLASS);

	/**
	 * 获取-业务对象编码
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BOCODE_NAME)
	public final String getBOCode() {
		return this.getProperty(PROPERTY_BOCODE);
	}

	/**
	 * 设置-业务对象编码
	 * 
	 * @param value
	 *            值
	 */
	public final void setBOCode(String value) {
		this.setProperty(PROPERTY_BOCODE, value);
	}

	/**
	 * 属性名称-平台简码
	 */
	private static final String PROPERTY_PLANTFORM_NAME = "Plantform";

	/**
	 * 平台简码 属性
	 */
	@DbField(name = "Plantform", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_PLANTFORM = registerProperty(PROPERTY_PLANTFORM_NAME,
			String.class, MY_CLASS);

	/**
	 * 获取-平台简码
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PLANTFORM_NAME)
	public final String getPlantform() {
		return this.getProperty(PROPERTY_PLANTFORM);
	}

	/**
	 * 设置-平台简码
	 * 
	 * @param value
	 *            值
	 */
	public final void setPlantform(String value) {
		this.setProperty(PROPERTY_PLANTFORM, value);
	}

	/**
	 * 属性名称-输出份数
	 */
	private static final String PROPERTY_COPYNUMBER_NAME = "CopyNumber";

	/**
	 * 输出份数 属性
	 */
	@DbField(name = "CopyNum", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_COPYNUMBER = registerProperty(PROPERTY_COPYNUMBER_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-输出份数
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_COPYNUMBER_NAME)
	public final Integer getCopyNumber() {
		return this.getProperty(PROPERTY_COPYNUMBER);
	}

	/**
	 * 设置-输出份数
	 * 
	 * @param value
	 *            值
	 */
	public final void setCopyNumber(Integer value) {
		this.setProperty(PROPERTY_COPYNUMBER, value);
	}

	/**
	 * 属性名称-数据导出模板-项
	 */
	private static final String PROPERTY_DATAEXPORTTEMPLATEITEMS_NAME = "DataExportTemplateItems";

	/**
	 * 数据导出模板-项的集合属性
	 * 
	 */
	public static final IPropertyInfo<IDataExportTemplateItems> PROPERTY_DATAEXPORTTEMPLATEITEMS = registerProperty(
			PROPERTY_DATAEXPORTTEMPLATEITEMS_NAME, IDataExportTemplateItems.class, MY_CLASS);

	/**
	 * 获取-数据导出模板-项集合
	 * 
	 * @return 值
	 */
	@XmlElementWrapper(name = PROPERTY_DATAEXPORTTEMPLATEITEMS_NAME)
	@XmlElement(name = DataExportTemplateItem.BUSINESS_OBJECT_NAME, type = DataExportTemplateItem.class)
	public final IDataExportTemplateItems getDataExportTemplateItems() {
		return this.getProperty(PROPERTY_DATAEXPORTTEMPLATEITEMS);
	}

	/**
	 * 设置-数据导出模板-项集合
	 * 
	 * @param value
	 *            值
	 */
	public final void setDataExportTemplateItems(IDataExportTemplateItems value) {
		this.setProperty(PROPERTY_DATAEXPORTTEMPLATEITEMS, value);
	}

	/**
	 * 初始化数据
	 */
	@Override
	protected void initialize() {
		super.initialize();// 基类初始化，不可去除
		this.setDataExportTemplateItems(new DataExportTemplateItems(this));
		this.setObjectCode(MyConfiguration.applyVariables(BUSINESS_OBJECT_CODE));

	}

}
