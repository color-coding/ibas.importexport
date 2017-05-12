package org.colorcoding.ibas.importexport.bo.dataexporttemplate;

import java.beans.PropertyChangeEvent;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.bo.BusinessObjects;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.importexport.MyConsts;

/**
 * 数据导出模板-项 集合
 */
@XmlType(name = DataExportTemplateItems.BUSINESS_OBJECT_NAME, namespace = MyConsts.NAMESPACE_BO)
@XmlSeeAlso({ DataExportTemplateItem.class })
public class DataExportTemplateItems extends BusinessObjects<IDataExportTemplateItem, IDataExportTemplate>
		implements IDataExportTemplateItems {

	/**
	 * 业务对象名称
	 */
	public static final String BUSINESS_OBJECT_NAME = "DataExportTemplateItems";

	/**
	 * 序列化版本标记
	 */
	private static final long serialVersionUID = -1167542794896482087L;

	/**
	 * 构造方法
	 */
	public DataExportTemplateItems() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param parent
	 *            父项对象
	 */
	public DataExportTemplateItems(IDataExportTemplate parent) {
		super(parent);
	}

	/**
	 * 元素类型
	 */
	public Class<?> getElementType() {
		return DataExportTemplateItem.class;
	}

	/**
	 * 创建数据导出模板-项
	 * 
	 * @return 数据导出模板-项
	 */
	public IDataExportTemplateItem create() {
		IDataExportTemplateItem item = new DataExportTemplateItem();
		if (this.add(item)) {
			return item;
		}
		return null;
	}

	@Override
	protected void afterAddItem(IDataExportTemplateItem item) {
		super.afterAddItem(item);
	}

	@Override
	public ICriteria getElementCriteria() {
		ICriteria criteria = super.getElementCriteria();
		return criteria;
	}

	@Override
	public void onParentPropertyChanged(PropertyChangeEvent evt) {
		super.onParentPropertyChanged(evt);
	}
}
