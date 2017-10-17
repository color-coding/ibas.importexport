package org.colorcoding.ibas.importexport.bo.dataexporttemplate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.core.IPropertyInfo;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.mapping.DbField;
import org.colorcoding.ibas.bobas.mapping.DbFieldType;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.data.emAreaType;
import org.colorcoding.ibas.importexport.data.emDataSourceType;
import org.colorcoding.ibas.importexport.data.emHorizontalJustification;
import org.colorcoding.ibas.importexport.data.emVerticalJustification;

/**
 * 获取-数据导出模板-项
 * 
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = DataExportTemplateItem.BUSINESS_OBJECT_NAME, namespace = MyConfiguration.NAMESPACE_BO)
public class DataExportTemplateItem extends BusinessObject<DataExportTemplateItem> implements IDataExportTemplateItem {

	/**
	 * 序列化版本标记
	 */
	private static final long serialVersionUID = -618042295812872502L;

	/**
	 * 当前类型
	 */
	private static final Class<?> MY_CLASS = DataExportTemplateItem.class;

	/**
	 * 数据库表
	 */
	public static final String DB_TABLE_NAME = "${Company}_IE_DET1";

	/**
	 * 业务对象编码
	 */
	public static final String BUSINESS_OBJECT_CODE = "${Company}_IE_EXPORTTEMPLATE";

	/**
	 * 业务对象名称
	 */
	public static final String BUSINESS_OBJECT_NAME = "DataExportTemplateItem";

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
	 * 属性名称-行号
	 */
	private static final String PROPERTY_LINEID_NAME = "LineId";

	/**
	 * 行号 属性
	 */
	@DbField(name = "LineId", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = true)
	public static final IPropertyInfo<Integer> PROPERTY_LINEID = registerProperty(PROPERTY_LINEID_NAME, Integer.class,
			MY_CLASS);

	/**
	 * 获取-行号
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_LINEID_NAME)
	public final Integer getLineId() {
		return this.getProperty(PROPERTY_LINEID);
	}

	/**
	 * 设置-行号
	 * 
	 * @param value
	 *            值
	 */
	public final void setLineId(Integer value) {
		this.setProperty(PROPERTY_LINEID, value);
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
	 * 属性名称-区域类型
	 */
	private static final String PROPERTY_AREATYPE_NAME = "AreaType";

	/**
	 * 区域类型 属性
	 */
	@DbField(name = "AreaType", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emAreaType> PROPERTY_AREATYPE = registerProperty(PROPERTY_AREATYPE_NAME,
			emAreaType.class, MY_CLASS);

	/**
	 * 获取-区域类型
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_AREATYPE_NAME)
	public final emAreaType getAreaType() {
		return this.getProperty(PROPERTY_AREATYPE);
	}

	/**
	 * 设置-区域类型
	 * 
	 * @param value
	 *            值
	 */
	public final void setAreaType(emAreaType value) {
		this.setProperty(PROPERTY_AREATYPE, value);
	}

	/**
	 * 属性名称-项标识
	 */
	private static final String PROPERTY_ITEMUID_NAME = "ItemUID";

	/**
	 * 项标识 属性
	 */
	@DbField(name = "ItemUID", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_ITEMUID = registerProperty(PROPERTY_ITEMUID_NAME, String.class,
			MY_CLASS);

	/**
	 * 获取-项标识
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ITEMUID_NAME)
	public final String getItemUID() {
		return this.getProperty(PROPERTY_ITEMUID);
	}

	/**
	 * 设置-项标识
	 * 
	 * @param value
	 *            值
	 */
	public final void setItemUID(String value) {
		this.setProperty(PROPERTY_ITEMUID, value);
	}

	/**
	 * 属性名称-项左坐标
	 */
	private static final String PROPERTY_ITEMLEFT_NAME = "ItemLeft";

	/**
	 * 项左坐标 属性
	 */
	@DbField(name = "ItemLeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_ITEMLEFT = registerProperty(PROPERTY_ITEMLEFT_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-项左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ITEMLEFT_NAME)
	public final Integer getItemLeft() {
		return this.getProperty(PROPERTY_ITEMLEFT);
	}

	/**
	 * 设置-项左坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setItemLeft(Integer value) {
		this.setProperty(PROPERTY_ITEMLEFT, value);
	}

	/**
	 * 属性名称-项上坐标
	 */
	private static final String PROPERTY_ITEMTOP_NAME = "ItemTop";

	/**
	 * 项上坐标 属性
	 */
	@DbField(name = "ItemTop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_ITEMTOP = registerProperty(PROPERTY_ITEMTOP_NAME, Integer.class,
			MY_CLASS);

	/**
	 * 获取-项上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ITEMTOP_NAME)
	public final Integer getItemTop() {
		return this.getProperty(PROPERTY_ITEMTOP);
	}

	/**
	 * 设置-项上坐标
	 * 
	 * @param value
	 *            值
	 */
	public final void setItemTop(Integer value) {
		this.setProperty(PROPERTY_ITEMTOP, value);
	}

	/**
	 * 属性名称-数据源
	 */
	private static final String PROPERTY_SOURCETYPE_NAME = "SourceType";

	/**
	 * 数据源 属性
	 */
	@DbField(name = "SourceType", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emDataSourceType> PROPERTY_SOURCETYPE = registerProperty(PROPERTY_SOURCETYPE_NAME,
			emDataSourceType.class, MY_CLASS);

	/**
	 * 获取-数据源
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_SOURCETYPE_NAME)
	public final emDataSourceType getSourceType() {
		return this.getProperty(PROPERTY_SOURCETYPE);
	}

	/**
	 * 设置-数据源
	 * 
	 * @param value
	 *            值
	 */
	public final void setSourceType(emDataSourceType value) {
		this.setProperty(PROPERTY_SOURCETYPE, value);
	}

	/**
	 * 属性名称-项字符串
	 */
	private static final String PROPERTY_ITEMSTRING_NAME = "ItemString";

	/**
	 * 项字符串 属性
	 */
	@DbField(name = "ItemString", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_ITEMSTRING = registerProperty(PROPERTY_ITEMSTRING_NAME,
			String.class, MY_CLASS);

	/**
	 * 获取-项字符串
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ITEMSTRING_NAME)
	public final String getItemString() {
		return this.getProperty(PROPERTY_ITEMSTRING);
	}

	/**
	 * 设置-项字符串
	 * 
	 * @param value
	 *            值
	 */
	public final void setItemString(String value) {
		this.setProperty(PROPERTY_ITEMSTRING, value);
	}

	/**
	 * 属性名称-项是否可见
	 */
	private static final String PROPERTY_ITEMVISIBLE_NAME = "ItemVisible";

	/**
	 * 项是否可见 属性
	 */
	@DbField(name = "ItemVisible", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emYesNo> PROPERTY_ITEMVISIBLE = registerProperty(PROPERTY_ITEMVISIBLE_NAME,
			emYesNo.class, MY_CLASS);

	/**
	 * 获取-项是否可见
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ITEMVISIBLE_NAME)
	public final emYesNo getItemVisible() {
		return this.getProperty(PROPERTY_ITEMVISIBLE);
	}

	/**
	 * 设置-项是否可见
	 * 
	 * @param value
	 *            值
	 */
	public final void setItemVisible(emYesNo value) {
		this.setProperty(PROPERTY_ITEMVISIBLE, value);
	}

	/**
	 * 属性名称-项宽度
	 */
	private static final String PROPERTY_WIDTH_NAME = "Width";

	/**
	 * 项宽度 属性
	 */
	@DbField(name = "Width", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_WIDTH = registerProperty(PROPERTY_WIDTH_NAME, Integer.class,
			MY_CLASS);

	/**
	 * 获取-项宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_WIDTH_NAME)
	public final Integer getWidth() {
		return this.getProperty(PROPERTY_WIDTH);
	}

	/**
	 * 设置-项宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setWidth(Integer value) {
		this.setProperty(PROPERTY_WIDTH, value);
	}

	/**
	 * 属性名称-项高度
	 */
	private static final String PROPERTY_HEIGHT_NAME = "Height";

	/**
	 * 项高度 属性
	 */
	@DbField(name = "Height", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_HEIGHT = registerProperty(PROPERTY_HEIGHT_NAME, Integer.class,
			MY_CLASS);

	/**
	 * 获取-项高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_HEIGHT_NAME)
	public final Integer getHeight() {
		return this.getProperty(PROPERTY_HEIGHT);
	}

	/**
	 * 设置-项高度
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
	 * 属性名称-左线长度
	 */
	private static final String PROPERTY_LEFTLINE_NAME = "LeftLine";

	/**
	 * 左线长度 属性
	 */
	@DbField(name = "LeftLine", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_LEFTLINE = registerProperty(PROPERTY_LEFTLINE_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-左线长度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_LEFTLINE_NAME)
	public final Integer getLeftLine() {
		return this.getProperty(PROPERTY_LEFTLINE);
	}

	/**
	 * 设置-左线长度
	 * 
	 * @param value
	 *            值
	 */
	public final void setLeftLine(Integer value) {
		this.setProperty(PROPERTY_LEFTLINE, value);
	}

	/**
	 * 属性名称-右线长度
	 */
	private static final String PROPERTY_RIGHTLINE_NAME = "RightLine";

	/**
	 * 右线长度 属性
	 */
	@DbField(name = "RightLine", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_RIGHTLINE = registerProperty(PROPERTY_RIGHTLINE_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-右线长度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_RIGHTLINE_NAME)
	public final Integer getRightLine() {
		return this.getProperty(PROPERTY_RIGHTLINE);
	}

	/**
	 * 设置-右线长度
	 * 
	 * @param value
	 *            值
	 */
	public final void setRightLine(Integer value) {
		this.setProperty(PROPERTY_RIGHTLINE, value);
	}

	/**
	 * 属性名称-上线长度
	 */
	private static final String PROPERTY_TOPLINE_NAME = "TopLine";

	/**
	 * 上线长度 属性
	 */
	@DbField(name = "TopLine", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_TOPLINE = registerProperty(PROPERTY_TOPLINE_NAME, Integer.class,
			MY_CLASS);

	/**
	 * 获取-上线长度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_TOPLINE_NAME)
	public final Integer getTopLine() {
		return this.getProperty(PROPERTY_TOPLINE);
	}

	/**
	 * 设置-上线长度
	 * 
	 * @param value
	 *            值
	 */
	public final void setTopLine(Integer value) {
		this.setProperty(PROPERTY_TOPLINE, value);
	}

	/**
	 * 属性名称-下线长度
	 */
	private static final String PROPERTY_BOTTOMLINE_NAME = "BottomLine";

	/**
	 * 下线长度 属性
	 */
	@DbField(name = "BottomLine", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BOTTOMLINE = registerProperty(PROPERTY_BOTTOMLINE_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-下线长度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BOTTOMLINE_NAME)
	public final Integer getBottomLine() {
		return this.getProperty(PROPERTY_BOTTOMLINE);
	}

	/**
	 * 设置-下线长度
	 * 
	 * @param value
	 *            值
	 */
	public final void setBottomLine(Integer value) {
		this.setProperty(PROPERTY_BOTTOMLINE, value);
	}

	/**
	 * 属性名称-字体名称
	 */
	private static final String PROPERTY_FONTNAME_NAME = "FontName";

	/**
	 * 字体名称 属性
	 */
	@DbField(name = "FontName", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_FONTNAME = registerProperty(PROPERTY_FONTNAME_NAME, String.class,
			MY_CLASS);

	/**
	 * 获取-字体名称
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_FONTNAME_NAME)
	public final String getFontName() {
		return this.getProperty(PROPERTY_FONTNAME);
	}

	/**
	 * 设置-字体名称
	 * 
	 * @param value
	 *            值
	 */
	public final void setFontName(String value) {
		this.setProperty(PROPERTY_FONTNAME, value);
	}

	/**
	 * 属性名称-字体大小
	 */
	private static final String PROPERTY_FONTSIZE_NAME = "FontSize";

	/**
	 * 字体大小 属性
	 */
	@DbField(name = "FontSize", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_FONTSIZE = registerProperty(PROPERTY_FONTSIZE_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-字体大小
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_FONTSIZE_NAME)
	public final Integer getFontSize() {
		return this.getProperty(PROPERTY_FONTSIZE);
	}

	/**
	 * 设置-字体大小
	 * 
	 * @param value
	 *            值
	 */
	public final void setFontSize(Integer value) {
		this.setProperty(PROPERTY_FONTSIZE, value);
	}

	/**
	 * 属性名称-文本样式
	 */
	private static final String PROPERTY_TEXTSTYLE_NAME = "TextStyle";

	/**
	 * 文本样式 属性
	 */
	@DbField(name = "TextStyle", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_TEXTSTYLE = registerProperty(PROPERTY_TEXTSTYLE_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-文本样式
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_TEXTSTYLE_NAME)
	public final Integer getTextStyle() {
		return this.getProperty(PROPERTY_TEXTSTYLE);
	}

	/**
	 * 设置-文本样式
	 * 
	 * @param value
	 *            值
	 */
	public final void setTextStyle(Integer value) {
		this.setProperty(PROPERTY_TEXTSTYLE, value);
	}

	/**
	 * 属性名称-水平对齐方式
	 */
	private static final String PROPERTY_HORIZONTALJUSTIFICATION_NAME = "HorizontalJustification";

	/**
	 * 水平对齐方式 属性
	 */
	@DbField(name = "XJustific", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emHorizontalJustification> PROPERTY_HORIZONTALJUSTIFICATION = registerProperty(
			PROPERTY_HORIZONTALJUSTIFICATION_NAME, emHorizontalJustification.class, MY_CLASS);

	/**
	 * 获取-水平对齐方式
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_HORIZONTALJUSTIFICATION_NAME)
	public final emHorizontalJustification getHorizontalJustification() {
		return this.getProperty(PROPERTY_HORIZONTALJUSTIFICATION);
	}

	/**
	 * 设置-水平对齐方式
	 * 
	 * @param value
	 *            值
	 */
	public final void setHorizontalJustification(emHorizontalJustification value) {
		this.setProperty(PROPERTY_HORIZONTALJUSTIFICATION, value);
	}

	/**
	 * 属性名称-竖直对齐方式
	 */
	private static final String PROPERTY_VERTICALJUSTIFICATION_NAME = "VerticalJustification";

	/**
	 * 竖直对齐方式 属性
	 */
	@DbField(name = "YJustific", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emVerticalJustification> PROPERTY_VERTICALJUSTIFICATION = registerProperty(
			PROPERTY_VERTICALJUSTIFICATION_NAME, emVerticalJustification.class, MY_CLASS);

	/**
	 * 获取-竖直对齐方式
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_VERTICALJUSTIFICATION_NAME)
	public final emVerticalJustification getVerticalJustification() {
		return this.getProperty(PROPERTY_VERTICALJUSTIFICATION);
	}

	/**
	 * 设置-竖直对齐方式
	 * 
	 * @param value
	 *            值
	 */
	public final void setVerticalJustification(emVerticalJustification value) {
		this.setProperty(PROPERTY_VERTICALJUSTIFICATION, value);
	}

	/**
	 * 属性名称-背景色-红
	 */
	private static final String PROPERTY_BACKGROUND_RED_NAME = "Background_Red";

	/**
	 * 背景色-红 属性
	 */
	@DbField(name = "BGRed", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BACKGROUND_RED = registerProperty(PROPERTY_BACKGROUND_RED_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-背景色-红
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BACKGROUND_RED_NAME)
	public final Integer getBackground_Red() {
		return this.getProperty(PROPERTY_BACKGROUND_RED);
	}

	/**
	 * 设置-背景色-红
	 * 
	 * @param value
	 *            值
	 */
	public final void setBackground_Red(Integer value) {
		this.setProperty(PROPERTY_BACKGROUND_RED, value);
	}

	/**
	 * 属性名称-背景色-绿
	 */
	private static final String PROPERTY_BACKGROUND_GREEN_NAME = "Background_Green";

	/**
	 * 背景色-绿 属性
	 */
	@DbField(name = "BGGreen", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BACKGROUND_GREEN = registerProperty(
			PROPERTY_BACKGROUND_GREEN_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-背景色-绿
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BACKGROUND_GREEN_NAME)
	public final Integer getBackground_Green() {
		return this.getProperty(PROPERTY_BACKGROUND_GREEN);
	}

	/**
	 * 设置-背景色-绿
	 * 
	 * @param value
	 *            值
	 */
	public final void setBackground_Green(Integer value) {
		this.setProperty(PROPERTY_BACKGROUND_GREEN, value);
	}

	/**
	 * 属性名称-背景色-蓝
	 */
	private static final String PROPERTY_BACKGROUND_BLUE_NAME = "Background_Blue";

	/**
	 * 背景色-蓝 属性
	 */
	@DbField(name = "BGBlue", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BACKGROUND_BLUE = registerProperty(
			PROPERTY_BACKGROUND_BLUE_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-背景色-蓝
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BACKGROUND_BLUE_NAME)
	public final Integer getBackground_Blue() {
		return this.getProperty(PROPERTY_BACKGROUND_BLUE);
	}

	/**
	 * 设置-背景色-蓝
	 * 
	 * @param value
	 *            值
	 */
	public final void setBackground_Blue(Integer value) {
		this.setProperty(PROPERTY_BACKGROUND_BLUE, value);
	}

	/**
	 * 属性名称-前景色-红
	 */
	private static final String PROPERTY_FOREGROUND_RED_NAME = "Foreground_Red";

	/**
	 * 前景色-红 属性
	 */
	@DbField(name = "FGRed", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_FOREGROUND_RED = registerProperty(PROPERTY_FOREGROUND_RED_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-前景色-红
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_FOREGROUND_RED_NAME)
	public final Integer getForeground_Red() {
		return this.getProperty(PROPERTY_FOREGROUND_RED);
	}

	/**
	 * 设置-前景色-红
	 * 
	 * @param value
	 *            值
	 */
	public final void setForeground_Red(Integer value) {
		this.setProperty(PROPERTY_FOREGROUND_RED, value);
	}

	/**
	 * 属性名称-前景色-绿
	 */
	private static final String PROPERTY_FOREGROUND_GREEN_NAME = "Foreground_Green";

	/**
	 * 前景色-绿 属性
	 */
	@DbField(name = "FGGreen", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_FOREGROUND_GREEN = registerProperty(
			PROPERTY_FOREGROUND_GREEN_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-前景色-绿
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_FOREGROUND_GREEN_NAME)
	public final Integer getForeground_Green() {
		return this.getProperty(PROPERTY_FOREGROUND_GREEN);
	}

	/**
	 * 设置-前景色-绿
	 * 
	 * @param value
	 *            值
	 */
	public final void setForeground_Green(Integer value) {
		this.setProperty(PROPERTY_FOREGROUND_GREEN, value);
	}

	/**
	 * 属性名称-前景色-蓝
	 */
	private static final String PROPERTY_FOREGROUND_BLUE_NAME = "Foreground_Blue";

	/**
	 * 前景色-蓝 属性
	 */
	@DbField(name = "FGBlue", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_FOREGROUND_BLUE = registerProperty(
			PROPERTY_FOREGROUND_BLUE_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-前景色-蓝
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_FOREGROUND_BLUE_NAME)
	public final Integer getForeground_Blue() {
		return this.getProperty(PROPERTY_FOREGROUND_BLUE);
	}

	/**
	 * 设置-前景色-蓝
	 * 
	 * @param value
	 *            值
	 */
	public final void setForeground_Blue(Integer value) {
		this.setProperty(PROPERTY_FOREGROUND_BLUE, value);
	}

	/**
	 * 属性名称-高亮显示色-红
	 */
	private static final String PROPERTY_MARKER_RED_NAME = "Marker_Red";

	/**
	 * 高亮显示色-红 属性
	 */
	@DbField(name = "MrkrRed", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_MARKER_RED = registerProperty(PROPERTY_MARKER_RED_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-高亮显示色-红
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_MARKER_RED_NAME)
	public final Integer getMarker_Red() {
		return this.getProperty(PROPERTY_MARKER_RED);
	}

	/**
	 * 设置-高亮显示色-红
	 * 
	 * @param value
	 *            值
	 */
	public final void setMarker_Red(Integer value) {
		this.setProperty(PROPERTY_MARKER_RED, value);
	}

	/**
	 * 属性名称-高亮显示色-绿
	 */
	private static final String PROPERTY_MARKER_GREEN_NAME = "Marker_Green";

	/**
	 * 高亮显示色-绿 属性
	 */
	@DbField(name = "MrkrGreen", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_MARKER_GREEN = registerProperty(PROPERTY_MARKER_GREEN_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-高亮显示色-绿
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_MARKER_GREEN_NAME)
	public final Integer getMarker_Green() {
		return this.getProperty(PROPERTY_MARKER_GREEN);
	}

	/**
	 * 设置-高亮显示色-绿
	 * 
	 * @param value
	 *            值
	 */
	public final void setMarker_Green(Integer value) {
		this.setProperty(PROPERTY_MARKER_GREEN, value);
	}

	/**
	 * 属性名称-高亮显示色-蓝
	 */
	private static final String PROPERTY_MARKER_BLUE_NAME = "Marker_Blue";

	/**
	 * 高亮显示色-蓝 属性
	 */
	@DbField(name = "MrkrBlue", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_MARKER_BLUE = registerProperty(PROPERTY_MARKER_BLUE_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-高亮显示色-蓝
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_MARKER_BLUE_NAME)
	public final Integer getMarker_Blue() {
		return this.getProperty(PROPERTY_MARKER_BLUE);
	}

	/**
	 * 设置-高亮显示色-蓝
	 * 
	 * @param value
	 *            值
	 */
	public final void setMarker_Blue(Integer value) {
		this.setProperty(PROPERTY_MARKER_BLUE, value);
	}

	/**
	 * 属性名称-框架色-红
	 */
	private static final String PROPERTY_BORDER_RED_NAME = "Border_Red";

	/**
	 * 框架色-红 属性
	 */
	@DbField(name = "BrdrRed", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BORDER_RED = registerProperty(PROPERTY_BORDER_RED_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-框架色-红
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BORDER_RED_NAME)
	public final Integer getBorder_Red() {
		return this.getProperty(PROPERTY_BORDER_RED);
	}

	/**
	 * 设置-框架色-红
	 * 
	 * @param value
	 *            值
	 */
	public final void setBorder_Red(Integer value) {
		this.setProperty(PROPERTY_BORDER_RED, value);
	}

	/**
	 * 属性名称-框架色-绿
	 */
	private static final String PROPERTY_BORDER_GREEN_NAME = "Border_Green";

	/**
	 * 框架色-绿 属性
	 */
	@DbField(name = "BrdrGreen", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BORDER_GREEN = registerProperty(PROPERTY_BORDER_GREEN_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-框架色-绿
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BORDER_GREEN_NAME)
	public final Integer getBorder_Green() {
		return this.getProperty(PROPERTY_BORDER_GREEN);
	}

	/**
	 * 设置-框架色-绿
	 * 
	 * @param value
	 *            值
	 */
	public final void setBorder_Green(Integer value) {
		this.setProperty(PROPERTY_BORDER_GREEN, value);
	}

	/**
	 * 属性名称-框架色-蓝
	 */
	private static final String PROPERTY_BORDER_BLUE_NAME = "Border_Blue";

	/**
	 * 框架色-蓝 属性
	 */
	@DbField(name = "BrdrBlue", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BORDER_BLUE = registerProperty(PROPERTY_BORDER_BLUE_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-框架色-蓝
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BORDER_BLUE_NAME)
	public final Integer getBorder_Blue() {
		return this.getProperty(PROPERTY_BORDER_BLUE);
	}

	/**
	 * 设置-框架色-蓝
	 * 
	 * @param value
	 *            值
	 */
	public final void setBorder_Blue(Integer value) {
		this.setProperty(PROPERTY_BORDER_BLUE, value);
	}

	/**
	 * 属性名称-显示格式
	 */
	private static final String PROPERTY_VALUEFORMAT_NAME = "ValueFormat";

	/**
	 * 显示格式 属性
	 */
	@DbField(name = "ValFormat", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_VALUEFORMAT = registerProperty(PROPERTY_VALUEFORMAT_NAME,
			String.class, MY_CLASS);

	/**
	 * 获取-显示格式
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_VALUEFORMAT_NAME)
	public final String getValueFormat() {
		return this.getProperty(PROPERTY_VALUEFORMAT);
	}

	/**
	 * 设置-显示格式
	 * 
	 * @param value
	 *            值
	 */
	public final void setValueFormat(String value) {
		this.setProperty(PROPERTY_VALUEFORMAT, value);
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
