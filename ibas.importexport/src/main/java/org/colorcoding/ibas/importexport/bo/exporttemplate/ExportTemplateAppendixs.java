package org.colorcoding.ibas.importexport.bo.exporttemplate;

import java.beans.PropertyChangeEvent;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.bo.BusinessObjects;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.ISort;
import org.colorcoding.ibas.bobas.common.SortType;
import org.colorcoding.ibas.importexport.MyConfiguration;

/**
 * 导出模板-附录 集合
 */
@XmlType(name = ExportTemplateAppendixs.BUSINESS_OBJECT_NAME, namespace = MyConfiguration.NAMESPACE_BO)
@XmlSeeAlso({ ExportTemplateAppendix.class })
public class ExportTemplateAppendixs extends BusinessObjects<IExportTemplateAppendix, IExportTemplate>
		implements IExportTemplateAppendixs {

	/**
	 * 业务对象名称
	 */
	public static final String BUSINESS_OBJECT_NAME = "ExportTemplateAppendixs";

	/**
	 * 序列化版本标记
	 */
	private static final long serialVersionUID = -4304637334973521157L;

	/**
	 * 构造方法
	 */
	public ExportTemplateAppendixs() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param parent 父项对象
	 */
	public ExportTemplateAppendixs(IExportTemplate parent) {
		super(parent);
	}

	/**
	 * 元素类型
	 */
	public Class<?> getElementType() {
		return ExportTemplateAppendix.class;
	}

	/**
	 * 创建导出模板-附录
	 * 
	 * @return 导出模板-附录
	 */
	public IExportTemplateAppendix create() {
		IExportTemplateAppendix item = new ExportTemplateAppendix();
		if (this.add(item)) {
			return item;
		}
		return null;
	}

	@Override
	protected void afterAddItem(IExportTemplateAppendix item) {
		super.afterAddItem(item);
	}

	@Override
	public ICriteria getElementCriteria() {
		ICriteria criteria = super.getElementCriteria();
		ISort sort = criteria.getSorts().create();
		sort.setAlias(ExportTemplateAppendix.PROPERTY_OBJECTKEY.getName());
		sort.setSortType(SortType.ASCENDING);
		sort = criteria.getSorts().create();
		sort.setAlias(ExportTemplateAppendix.PROPERTY_PAGEORDER.getName());
		sort.setSortType(SortType.ASCENDING);
		return criteria;
	}

	@Override
	public void onParentPropertyChanged(PropertyChangeEvent evt) {
		super.onParentPropertyChanged(evt);
	}
}
