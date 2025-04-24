package org.colorcoding.ibas.importexport.bo.exporttemplate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.core.IPropertyInfo;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.db.DbField;
import org.colorcoding.ibas.bobas.db.DbFieldType;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.data.emAreaType;

/**
 * 导出模板-附录
 * 
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = ExportTemplateAppendix.BUSINESS_OBJECT_NAME, namespace = MyConfiguration.NAMESPACE_BO)
public class ExportTemplateAppendix extends BusinessObject<ExportTemplateAppendix>
		implements IExportTemplateAppendix, IExportTemplateItemParent {

	/**
	 * 序列化版本标记
	 */
	private static final long serialVersionUID = -2531832995912118965L;

	/**
	 * 当前类型
	 */
	private static final Class<?> MY_CLASS = ExportTemplateAppendix.class;

	/**
	 * 数据库表
	 */
	public static final String DB_TABLE_NAME = "${Company}_IE_EXT2";

	/**
	 * 业务对象编码
	 */
	public static final String BUSINESS_OBJECT_CODE = "${Company}_IE_EXPORTTEMPLATE";

	/**
	 * 业务对象名称
	 */
	public static final String BUSINESS_OBJECT_NAME = "ExportTemplateAppendix";

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
	 * @param value 值
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
	 * @param value 值
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
	 * @param value 值
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
	 * @param value 值
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
	 * @param value 值
	 */
	public final void setUpdateActionId(String value) {
		this.setProperty(PROPERTY_UPDATEACTIONID, value);
	}

	/**
	 * 属性名称-页序号
	 */
	private static final String PROPERTY_PAGEORDER_NAME = "PageOrder";

	/**
	 * 页序号 属性
	 */
	@DbField(name = "PageOrder", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_PAGEORDER = registerProperty(PROPERTY_PAGEORDER_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-页序号
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PAGEORDER_NAME)
	public final Integer getPageOrder() {
		return this.getProperty(PROPERTY_PAGEORDER);
	}

	/**
	 * 设置-页序号
	 * 
	 * @param value 值
	 */
	public final void setPageOrder(Integer value) {
		this.setProperty(PROPERTY_PAGEORDER, value);
	}

	/**
	 * 属性名称-使用页眉
	 */
	private static final String PROPERTY_PAGEHEADER_NAME = "PageHeader";

	/**
	 * 使用页眉 属性
	 */
	@DbField(name = "PageHeader", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emYesNo> PROPERTY_PAGEHEADER = registerProperty(PROPERTY_PAGEHEADER_NAME,
			emYesNo.class, MY_CLASS);

	/**
	 * 获取-使用页眉
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PAGEHEADER_NAME)
	public final emYesNo getPageHeader() {
		return this.getProperty(PROPERTY_PAGEHEADER);
	}

	/**
	 * 设置-使用页眉
	 * 
	 * @param value 值
	 */
	public final void setPageHeader(emYesNo value) {
		this.setProperty(PROPERTY_PAGEHEADER, value);
	}

	/**
	 * 属性名称-使用页脚
	 */
	private static final String PROPERTY_PAGEFOOTER_NAME = "PageFooter";

	/**
	 * 使用页脚 属性
	 */
	@DbField(name = "PageFooter", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<emYesNo> PROPERTY_PAGEFOOTER = registerProperty(PROPERTY_PAGEFOOTER_NAME,
			emYesNo.class, MY_CLASS);

	/**
	 * 获取-使用页脚
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_PAGEFOOTER_NAME)
	public final emYesNo getPageFooter() {
		return this.getProperty(PROPERTY_PAGEFOOTER);
	}

	/**
	 * 设置-使用页脚
	 * 
	 * @param value 值
	 */
	public final void setPageFooter(emYesNo value) {
		this.setProperty(PROPERTY_PAGEFOOTER, value);
	}

	/**
	 * 属性名称-内容-左坐标
	 */
	private static final String PROPERTY_CONTENTLEFT_NAME = "ContentLeft";

	/**
	 * 内容-左坐标 属性
	 */
	@DbField(name = "CntLeft", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_CONTENTLEFT = registerProperty(PROPERTY_CONTENTLEFT_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-内容-左坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_CONTENTLEFT_NAME)
	public final Integer getContentLeft() {
		return this.getProperty(PROPERTY_CONTENTLEFT);
	}

	/**
	 * 设置-内容-左坐标
	 * 
	 * @param value 值
	 */
	public final void setContentLeft(Integer value) {
		this.setProperty(PROPERTY_CONTENTLEFT, value);
	}

	/**
	 * 属性名称-内容-上坐标
	 */
	private static final String PROPERTY_CONTENTTOP_NAME = "ContentTop";

	/**
	 * 内容-上坐标 属性
	 */
	@DbField(name = "CntTop", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_CONTENTTOP = registerProperty(PROPERTY_CONTENTTOP_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-内容-上坐标
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_CONTENTTOP_NAME)
	public final Integer getContentTop() {
		return this.getProperty(PROPERTY_CONTENTTOP);
	}

	/**
	 * 设置-内容-上坐标
	 * 
	 * @param value 值
	 */
	public final void setContentTop(Integer value) {
		this.setProperty(PROPERTY_CONTENTTOP, value);
	}

	/**
	 * 属性名称-内容-宽度
	 */
	private static final String PROPERTY_CONTENTWIDTH_NAME = "ContentWidth";

	/**
	 * 内容-宽度 属性
	 */
	@DbField(name = "CntWidth", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_CONTENTWIDTH = registerProperty(PROPERTY_CONTENTWIDTH_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-内容-宽度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_CONTENTWIDTH_NAME)
	public final Integer getContentWidth() {
		return this.getProperty(PROPERTY_CONTENTWIDTH);
	}

	/**
	 * 设置-内容-宽度
	 * 
	 * @param value 值
	 */
	public final void setContentWidth(Integer value) {
		this.setProperty(PROPERTY_CONTENTWIDTH, value);
	}

	/**
	 * 属性名称-内容-高度
	 */
	private static final String PROPERTY_CONTENTHEIGHT_NAME = "ContentHeight";

	/**
	 * 内容-高度 属性
	 */
	@DbField(name = "CntHeight", type = DbFieldType.NUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<Integer> PROPERTY_CONTENTHEIGHT = registerProperty(PROPERTY_CONTENTHEIGHT_NAME,
			Integer.class, MY_CLASS);

	/**
	 * 获取-内容-高度
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_CONTENTHEIGHT_NAME)
	public final Integer getContentHeight() {
		return this.getProperty(PROPERTY_CONTENTHEIGHT);
	}

	/**
	 * 设置-内容-高度
	 * 
	 * @param value 值
	 */
	public final void setContentHeight(Integer value) {
		this.setProperty(PROPERTY_CONTENTHEIGHT, value);
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
	 * @param value 值
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
	 * @param value 值
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
	 * @param value 值
	 */
	public final void setBackgroundBlue(Integer value) {
		this.setProperty(PROPERTY_BACKGROUNDBLUE, value);
	}

	/**
	 * 属性名称-背景图
	 */
	private static final String PROPERTY_BACKGROUNDIMAGE_NAME = "BackgroundImage";

	/**
	 * 背景图 属性
	 */
	@DbField(name = "BGImage", type = DbFieldType.ALPHANUMERIC, table = DB_TABLE_NAME, primaryKey = false)
	public static final IPropertyInfo<String> PROPERTY_BACKGROUNDIMAGE = registerProperty(PROPERTY_BACKGROUNDIMAGE_NAME,
			String.class, MY_CLASS);

	/**
	 * 获取-背景图
	 * 
	 * @return 值
	 */
	@XmlElement(name = PROPERTY_BACKGROUNDIMAGE_NAME)
	public final String getBackgroundImage() {
		return this.getProperty(PROPERTY_BACKGROUNDIMAGE);
	}

	/**
	 * 设置-背景图
	 * 
	 * @param value 值
	 */
	public final void setBackgroundImage(String value) {
		this.setProperty(PROPERTY_BACKGROUNDIMAGE, value);
	}

	/**
	 * 属性名称-内容
	 */
	private static final String PROPERTY_CONTENTS_NAME = "Contents";

	/**
	 * 内容的集合属性
	 * 
	 */
	public static final IPropertyInfo<IExportTemplateItems> PROPERTY_CONTENTS = registerProperty(PROPERTY_CONTENTS_NAME,
			IExportTemplateItems.class, MY_CLASS);

	/**
	 * 获取-内容
	 * 
	 * @return 值
	 */
	@XmlElementWrapper(name = PROPERTY_CONTENTS_NAME)
	@XmlElement(name = ExportTemplateItem.BUSINESS_OBJECT_NAME, type = ExportTemplateItem.class)
	public final IExportTemplateItems getContents() {
		return this.getProperty(PROPERTY_CONTENTS);
	}

	/**
	 * 设置-内容
	 * 
	 * @param value 值
	 */
	public final void setContents(IExportTemplateItems value) {
		this.setProperty(PROPERTY_CONTENTS, value);
	}

	/**
	 * 初始化数据
	 */
	@Override
	protected void initialize() {
		super.initialize();
		this.setObjectCode(MyConfiguration.applyVariables(BUSINESS_OBJECT_CODE));
		this.setContents(new ExportTemplateItems(this, emAreaType.APPENDIX));
		this.setPageHeader(emYesNo.YES);
		this.setPageFooter(emYesNo.YES);
	}

}
