package org.colorcoding.ibas.importexport.bo.exporttemplate;

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
import org.colorcoding.ibas.importexport.data.emJustificationHorizontal;
import org.colorcoding.ibas.importexport.data.emJustificationVertical;
import org.colorcoding.ibas.importexport.data.emTextStyle;

/**
 * 导出模板-项
 * 
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = ExportTemplateItem.BUSINESS_OBJECT_NAME, namespace = MyConfiguration.NAMESPACE_BO)
public class ExportTemplateItem extends BusinessObject<ExportTemplateItem> implements IExportTemplateItem {

	/**
	 * 序列化版本标记
	 */
	private static final long serialVersionUID = -1757942458262327926L;

	/**
	 * 当前类型
	 */
	private static final Class<?> MY_CLASS = ExportTemplateItem.class;

	/**
	 * 数据库表
	 */
	public static final String DB_TABLE_NAME = "CC_IE_EXT1";

	/**
	 * 业务对象编码
	 */
	public static final String BUSINESS_OBJECT_CODE = "CC_IE_EXPORTTEMPLATE";

	/**
	 * 业务对象名称
	 */
	public static final String BUSINESS_OBJECT_NAME = "ExportTemplateItem";

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
	 * 属性名称-区域
	 */
	private static final String PROPERTY_AREA_NAME = "Area";

	/**
	 * 区域 属性
	 */
	@DbField(name = "Area", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emAreaType> PROPERTY_AREA = registerProperty(PROPERTY_AREA_NAME, emAreaType.class,
			MY_CLASS);

	/**
	 * 获取-区域
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_AREA_NAME)
	public final emAreaType getArea() {
		return this.getProperty(PROPERTY_AREA);
	}

	/**
	 * 设置-区域
	 * 
	 * @param value
	 *            值
	 */
	public final void setArea(emAreaType value) {
		this.setProperty(PROPERTY_AREA, value);
	}

	/**
	 * 属性名称-项标识
	 */
	private static final String PROPERTY_ITEMID_NAME = "ItemID";

	/**
	 * 项标识 属性
	 */
	@DbField(name = "ItemID", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_ITEMID = registerProperty(PROPERTY_ITEMID_NAME, String.class,
			MY_CLASS);

	/**
	 * 获取-项标识
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ITEMID_NAME)
	public final String getItemID() {
		return this.getProperty(PROPERTY_ITEMID);
	}

	/**
	 * 设置-项标识
	 * 
	 * @param value
	 *            值
	 */
	public final void setItemID(String value) {
		this.setProperty(PROPERTY_ITEMID, value);
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
	private static final String PROPERTY_ITEMWIDTH_NAME = "ItemWidth";

	/**
	 * 项宽度 属性
	 */
	@DbField(name = "ItemWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_ITEMWIDTH = registerProperty(PROPERTY_ITEMWIDTH_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-项宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ITEMWIDTH_NAME)
	public final Integer getItemWidth() {
		return this.getProperty(PROPERTY_ITEMWIDTH);
	}

	/**
	 * 设置-项宽度
	 * 
	 * @param value
	 *            值
	 */
	public final void setItemWidth(Integer value) {
		this.setProperty(PROPERTY_ITEMWIDTH, value);
	}

	/**
	 * 属性名称-项高度
	 */
	private static final String PROPERTY_ITEMHEIGHT_NAME = "ItemHeight";

	/**
	 * 项高度 属性
	 */
	@DbField(name = "ItemHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_ITEMHEIGHT = registerProperty(PROPERTY_ITEMHEIGHT_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-项高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_ITEMHEIGHT_NAME)
	public final Integer getItemHeight() {
		return this.getProperty(PROPERTY_ITEMHEIGHT);
	}

	/**
	 * 设置-项高度
	 * 
	 * @param value
	 *            值
	 */
	public final void setItemHeight(Integer value) {
		this.setProperty(PROPERTY_ITEMHEIGHT, value);
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
	 * 属性名称-左线长度
	 */
	private static final String PROPERTY_LINELEFT_NAME = "LineLeft";

	/**
	 * 左线长度 属性
	 */
	@DbField(name = "LeftLine", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_LINELEFT = registerProperty(PROPERTY_LINELEFT_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-左线长度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_LINELEFT_NAME)
	public final Integer getLineLeft() {
		return this.getProperty(PROPERTY_LINELEFT);
	}

	/**
	 * 设置-左线长度
	 * 
	 * @param value
	 *            值
	 */
	public final void setLineLeft(Integer value) {
		this.setProperty(PROPERTY_LINELEFT, value);
	}

	/**
	 * 属性名称-右线长度
	 */
	private static final String PROPERTY_LINERIGHT_NAME = "LineRight";

	/**
	 * 右线长度 属性
	 */
	@DbField(name = "RightLine", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_LINERIGHT = registerProperty(PROPERTY_LINERIGHT_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-右线长度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_LINERIGHT_NAME)
	public final Integer getLineRight() {
		return this.getProperty(PROPERTY_LINERIGHT);
	}

	/**
	 * 设置-右线长度
	 * 
	 * @param value
	 *            值
	 */
	public final void setLineRight(Integer value) {
		this.setProperty(PROPERTY_LINERIGHT, value);
	}

	/**
	 * 属性名称-上线长度
	 */
	private static final String PROPERTY_LINETOP_NAME = "LineTop";

	/**
	 * 上线长度 属性
	 */
	@DbField(name = "TopLine", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_LINETOP = registerProperty(PROPERTY_LINETOP_NAME, Integer.class,
			MY_CLASS);

	/**
	 * 获取-上线长度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_LINETOP_NAME)
	public final Integer getLineTop() {
		return this.getProperty(PROPERTY_LINETOP);
	}

	/**
	 * 设置-上线长度
	 * 
	 * @param value
	 *            值
	 */
	public final void setLineTop(Integer value) {
		this.setProperty(PROPERTY_LINETOP, value);
	}

	/**
	 * 属性名称-下线长度
	 */
	private static final String PROPERTY_LINEBOTTOM_NAME = "LineBottom";

	/**
	 * 下线长度 属性
	 */
	@DbField(name = "BottomLine", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_LINEBOTTOM = registerProperty(PROPERTY_LINEBOTTOM_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-下线长度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_LINEBOTTOM_NAME)
	public final Integer getLineBottom() {
		return this.getProperty(PROPERTY_LINEBOTTOM);
	}

	/**
	 * 设置-下线长度
	 * 
	 * @param value
	 *            值
	 */
	public final void setLineBottom(Integer value) {
		this.setProperty(PROPERTY_LINEBOTTOM, value);
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
	@DbField(name = "TextStyle", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emTextStyle> PROPERTY_TEXTSTYLE = registerProperty(PROPERTY_TEXTSTYLE_NAME,
			emTextStyle.class, MY_CLASS);

	/**
	 * 获取-文本样式
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_TEXTSTYLE_NAME)
	public final emTextStyle getTextStyle() {
		return this.getProperty(PROPERTY_TEXTSTYLE);
	}

	/**
	 * 设置-文本样式
	 * 
	 * @param value
	 *            值
	 */
	public final void setTextStyle(emTextStyle value) {
		this.setProperty(PROPERTY_TEXTSTYLE, value);
	}

	/**
	 * 属性名称-水平对齐方式
	 */
	private static final String PROPERTY_JUSTIFICATIONHORIZONTAL_NAME = "JustificationHorizontal";

	/**
	 * 水平对齐方式 属性
	 */
	@DbField(name = "XJustific", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emJustificationHorizontal> PROPERTY_JUSTIFICATIONHORIZONTAL = registerProperty(
			PROPERTY_JUSTIFICATIONHORIZONTAL_NAME, emJustificationHorizontal.class, MY_CLASS);

	/**
	 * 获取-水平对齐方式
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_JUSTIFICATIONHORIZONTAL_NAME)
	public final emJustificationHorizontal getJustificationHorizontal() {
		return this.getProperty(PROPERTY_JUSTIFICATIONHORIZONTAL);
	}

	/**
	 * 设置-水平对齐方式
	 * 
	 * @param value
	 *            值
	 */
	public final void setJustificationHorizontal(emJustificationHorizontal value) {
		this.setProperty(PROPERTY_JUSTIFICATIONHORIZONTAL, value);
	}

	/**
	 * 属性名称-竖直对齐方式
	 */
	private static final String PROPERTY_JUSTIFICATIONVERTICAL_NAME = "JustificationVertical";

	/**
	 * 竖直对齐方式 属性
	 */
	@DbField(name = "YJustific", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emJustificationVertical> PROPERTY_JUSTIFICATIONVERTICAL = registerProperty(
			PROPERTY_JUSTIFICATIONVERTICAL_NAME, emJustificationVertical.class, MY_CLASS);

	/**
	 * 获取-竖直对齐方式
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_JUSTIFICATIONVERTICAL_NAME)
	public final emJustificationVertical getJustificationVertical() {
		return this.getProperty(PROPERTY_JUSTIFICATIONVERTICAL);
	}

	/**
	 * 设置-竖直对齐方式
	 * 
	 * @param value
	 *            值
	 */
	public final void setJustificationVertical(emJustificationVertical value) {
		this.setProperty(PROPERTY_JUSTIFICATIONVERTICAL, value);
	}

	/**
	 * 属性名称-背景色-红
	 */
	private static final String PROPERTY_BACKGROUNDRED_NAME = "BackgroundRed";

	/**
	 * 背景色-红 属性
	 */
	@DbField(name = "BGRed", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BACKGROUNDRED = registerProperty(PROPERTY_BACKGROUNDRED_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-背景色-红
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BACKGROUNDRED_NAME)
	public final Integer getBackgroundRed() {
		return this.getProperty(PROPERTY_BACKGROUNDRED);
	}

	/**
	 * 设置-背景色-红
	 * 
	 * @param value
	 *            值
	 */
	public final void setBackgroundRed(Integer value) {
		this.setProperty(PROPERTY_BACKGROUNDRED, value);
	}

	/**
	 * 属性名称-背景色-绿
	 */
	private static final String PROPERTY_BACKGROUNDGREEN_NAME = "BackgroundGreen";

	/**
	 * 背景色-绿 属性
	 */
	@DbField(name = "BGGreen", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BACKGROUNDGREEN = registerProperty(
			PROPERTY_BACKGROUNDGREEN_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-背景色-绿
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BACKGROUNDGREEN_NAME)
	public final Integer getBackgroundGreen() {
		return this.getProperty(PROPERTY_BACKGROUNDGREEN);
	}

	/**
	 * 设置-背景色-绿
	 * 
	 * @param value
	 *            值
	 */
	public final void setBackgroundGreen(Integer value) {
		this.setProperty(PROPERTY_BACKGROUNDGREEN, value);
	}

	/**
	 * 属性名称-背景色-蓝
	 */
	private static final String PROPERTY_BACKGROUNDBLUE_NAME = "BackgroundBlue";

	/**
	 * 背景色-蓝 属性
	 */
	@DbField(name = "BGBlue", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BACKGROUNDBLUE = registerProperty(PROPERTY_BACKGROUNDBLUE_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-背景色-蓝
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BACKGROUNDBLUE_NAME)
	public final Integer getBackgroundBlue() {
		return this.getProperty(PROPERTY_BACKGROUNDBLUE);
	}

	/**
	 * 设置-背景色-蓝
	 * 
	 * @param value
	 *            值
	 */
	public final void setBackgroundBlue(Integer value) {
		this.setProperty(PROPERTY_BACKGROUNDBLUE, value);
	}

	/**
	 * 属性名称-前景色-红
	 */
	private static final String PROPERTY_FOREGROUNDRED_NAME = "ForegroundRed";

	/**
	 * 前景色-红 属性
	 */
	@DbField(name = "FGRed", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_FOREGROUNDRED = registerProperty(PROPERTY_FOREGROUNDRED_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-前景色-红
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_FOREGROUNDRED_NAME)
	public final Integer getForegroundRed() {
		return this.getProperty(PROPERTY_FOREGROUNDRED);
	}

	/**
	 * 设置-前景色-红
	 * 
	 * @param value
	 *            值
	 */
	public final void setForegroundRed(Integer value) {
		this.setProperty(PROPERTY_FOREGROUNDRED, value);
	}

	/**
	 * 属性名称-前景色-绿
	 */
	private static final String PROPERTY_FOREGROUNDGREEN_NAME = "ForegroundGreen";

	/**
	 * 前景色-绿 属性
	 */
	@DbField(name = "FGGreen", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_FOREGROUNDGREEN = registerProperty(
			PROPERTY_FOREGROUNDGREEN_NAME, Integer.class, MY_CLASS);

	/**
	 * 获取-前景色-绿
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_FOREGROUNDGREEN_NAME)
	public final Integer getForegroundGreen() {
		return this.getProperty(PROPERTY_FOREGROUNDGREEN);
	}

	/**
	 * 设置-前景色-绿
	 * 
	 * @param value
	 *            值
	 */
	public final void setForegroundGreen(Integer value) {
		this.setProperty(PROPERTY_FOREGROUNDGREEN, value);
	}

	/**
	 * 属性名称-前景色-蓝
	 */
	private static final String PROPERTY_FOREGROUNDBLUE_NAME = "ForegroundBlue";

	/**
	 * 前景色-蓝 属性
	 */
	@DbField(name = "FGBlue", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_FOREGROUNDBLUE = registerProperty(PROPERTY_FOREGROUNDBLUE_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-前景色-蓝
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_FOREGROUNDBLUE_NAME)
	public final Integer getForegroundBlue() {
		return this.getProperty(PROPERTY_FOREGROUNDBLUE);
	}

	/**
	 * 设置-前景色-蓝
	 * 
	 * @param value
	 *            值
	 */
	public final void setForegroundBlue(Integer value) {
		this.setProperty(PROPERTY_FOREGROUNDBLUE, value);
	}

	/**
	 * 属性名称-高亮显示色-红
	 */
	private static final String PROPERTY_MARKERRED_NAME = "MarkerRed";

	/**
	 * 高亮显示色-红 属性
	 */
	@DbField(name = "MrkrRed", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_MARKERRED = registerProperty(PROPERTY_MARKERRED_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-高亮显示色-红
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_MARKERRED_NAME)
	public final Integer getMarkerRed() {
		return this.getProperty(PROPERTY_MARKERRED);
	}

	/**
	 * 设置-高亮显示色-红
	 * 
	 * @param value
	 *            值
	 */
	public final void setMarkerRed(Integer value) {
		this.setProperty(PROPERTY_MARKERRED, value);
	}

	/**
	 * 属性名称-高亮显示色-绿
	 */
	private static final String PROPERTY_MARKERGREEN_NAME = "MarkerGreen";

	/**
	 * 高亮显示色-绿 属性
	 */
	@DbField(name = "MrkrGreen", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_MARKERGREEN = registerProperty(PROPERTY_MARKERGREEN_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-高亮显示色-绿
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_MARKERGREEN_NAME)
	public final Integer getMarkerGreen() {
		return this.getProperty(PROPERTY_MARKERGREEN);
	}

	/**
	 * 设置-高亮显示色-绿
	 * 
	 * @param value
	 *            值
	 */
	public final void setMarkerGreen(Integer value) {
		this.setProperty(PROPERTY_MARKERGREEN, value);
	}

	/**
	 * 属性名称-高亮显示色-蓝
	 */
	private static final String PROPERTY_MARKERBLUE_NAME = "MarkerBlue";

	/**
	 * 高亮显示色-蓝 属性
	 */
	@DbField(name = "MrkrBlue", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_MARKERBLUE = registerProperty(PROPERTY_MARKERBLUE_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-高亮显示色-蓝
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_MARKERBLUE_NAME)
	public final Integer getMarkerBlue() {
		return this.getProperty(PROPERTY_MARKERBLUE);
	}

	/**
	 * 设置-高亮显示色-蓝
	 * 
	 * @param value
	 *            值
	 */
	public final void setMarkerBlue(Integer value) {
		this.setProperty(PROPERTY_MARKERBLUE, value);
	}

	/**
	 * 属性名称-框架色-红
	 */
	private static final String PROPERTY_BORDERRED_NAME = "BorderRed";

	/**
	 * 框架色-红 属性
	 */
	@DbField(name = "BrdrRed", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BORDERRED = registerProperty(PROPERTY_BORDERRED_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-框架色-红
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BORDERRED_NAME)
	public final Integer getBorderRed() {
		return this.getProperty(PROPERTY_BORDERRED);
	}

	/**
	 * 设置-框架色-红
	 * 
	 * @param value
	 *            值
	 */
	public final void setBorderRed(Integer value) {
		this.setProperty(PROPERTY_BORDERRED, value);
	}

	/**
	 * 属性名称-框架色-绿
	 */
	private static final String PROPERTY_BORDERGREEN_NAME = "BorderGreen";

	/**
	 * 框架色-绿 属性
	 */
	@DbField(name = "BrdrGreen", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BORDERGREEN = registerProperty(PROPERTY_BORDERGREEN_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-框架色-绿
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BORDERGREEN_NAME)
	public final Integer getBorderGreen() {
		return this.getProperty(PROPERTY_BORDERGREEN);
	}

	/**
	 * 设置-框架色-绿
	 * 
	 * @param value
	 *            值
	 */
	public final void setBorderGreen(Integer value) {
		this.setProperty(PROPERTY_BORDERGREEN, value);
	}

	/**
	 * 属性名称-框架色-蓝
	 */
	private static final String PROPERTY_BORDERBLUE_NAME = "BorderBlue";

	/**
	 * 框架色-蓝 属性
	 */
	@DbField(name = "BrdrBlue", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_BORDERBLUE = registerProperty(PROPERTY_BORDERBLUE_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-框架色-蓝
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BORDERBLUE_NAME)
	public final Integer getBorderBlue() {
		return this.getProperty(PROPERTY_BORDERBLUE);
	}

	/**
	 * 设置-框架色-蓝
	 * 
	 * @param value
	 *            值
	 */
	public final void setBorderBlue(Integer value) {
		this.setProperty(PROPERTY_BORDERBLUE, value);
	}

	/**
	 * 初始化数据
	 */
	@Override
	protected void initialize() {
		super.initialize();// 基类初始化，不可去除
		this.setObjectCode(BUSINESS_OBJECT_CODE);
		this.setItemVisible(emYesNo.YES);
		this.setTextStyle(emTextStyle.REGULAR);
		this.setJustificationHorizontal(emJustificationHorizontal.CENTER);
		this.setJustificationVertical(emJustificationVertical.CENTER);
	}

}
