package org.colorcoding.ibas.importexport.bo.exporttemplate;

import java.beans.PropertyChangeEvent;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.bo.BusinessObjects;
import org.colorcoding.ibas.bobas.common.ICondition;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.data.emAreaType;

/**
 * 导出模板-项 集合
 */
@XmlType(name = ExportTemplateItems.BUSINESS_OBJECT_NAME, namespace = MyConfiguration.NAMESPACE_BO)
@XmlSeeAlso({ ExportTemplateItem.class })
public class ExportTemplateItems extends BusinessObjects<IExportTemplateItem, IExportTemplate>
		implements IExportTemplateItems {

	/**
	 * 业务对象名称
	 */
	public static final String BUSINESS_OBJECT_NAME = "ExportTemplateItems";

	/**
	 * 序列化版本标记
	 */
	private static final long serialVersionUID = 54273132019907719L;

	/**
	 * 构造方法
	 */
	public ExportTemplateItems() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param parent
	 *            父项对象
	 */
	public ExportTemplateItems(IExportTemplate parent, emAreaType areaType) {
		super(parent);
		this.areaType = areaType;
	}

	/**
	 * 元素类型
	 */
	public Class<?> getElementType() {
		return ExportTemplateItem.class;
	}

	/**
	 * 创建导出模板-项
	 * 
	 * @return 导出模板-项
	 */
	public IExportTemplateItem create() {
		IExportTemplateItem item = new ExportTemplateItem();
		if (this.add(item)) {
			return item;
		}
		return null;
	}

	private emAreaType areaType;

	@Override
	protected void afterAddItem(IExportTemplateItem item) {
		super.afterAddItem(item);
		item.setArea(this.areaType);
	}

	@Override
	public ICriteria getElementCriteria() {
		ICriteria criteria = super.getElementCriteria();
		ICondition condition = criteria.getConditions().create();
		condition.setAlias(ExportTemplateItem.PROPERTY_AREA.getName());
		condition.setValue(this.areaType);
		return criteria;
	}

	@Override
	public void onParentPropertyChanged(PropertyChangeEvent evt) {
		super.onParentPropertyChanged(evt);
	}
}
