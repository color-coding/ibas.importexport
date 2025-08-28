package org.colorcoding.ibas.importexport.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.ICondition;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.ISort;
import org.colorcoding.ibas.bobas.common.SortType;
import org.colorcoding.ibas.bobas.data.KeyText;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.organization.OrganizationFactory;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplateItem;
import org.colorcoding.ibas.importexport.data.DataConvert;
import org.colorcoding.ibas.importexport.data.emDataSourceType;
import org.colorcoding.ibas.importexport.repository.BORepositoryImportExport;

public abstract class ExportTemplateTransformer extends TemplateTransformer {

	/**
	 * 变量样式，(XXXXXX)
	 */
	public static final String PARAM_PATTERN = "(?=\\(\\$)[^\\)]+";
	public static final String PARAM_PATTERN_TEMPLATE = "(%s)";
	public static final String PARAM_PAGE_INDEX = "${PAGE_INDEX}";
	public static final String PARAM_PAGE_TOTAL = "${PAGE_TOTAL}";
	public static final String PARAM_PAGE_MAIN_TOTAL = "${PAGE_MAIN_TOTAL}";
	public static final String PARAM_DATA_SIZE = "${DATA_SIZE}";
	public static final String PARAM_DATA_INDEX = "${DATA_INDEX}";
	public static final String PARAM_TIME_NOW = "${TIME_NOW}";
	public static final String PARAM_TEMPLATE_PAGE_DATA_INDEX = "${PAGE_%s_DATA_INDEX}";
	public static final String PARAM_PAGE_DATA_BEGIN = "${PAGE_DATA_BEGIN}";
	public static final String PARAM_PAGE_DATA_END = "${PAGE_DATA_END}";

	@Override
	public List<KeyText> matchingTemplates(String boCode) {
		List<KeyText> templates = new ArrayList<KeyText>();
		if (!DataConvert.isNullOrEmpty(boCode)) {
			ICriteria tpCriteria = new Criteria();
			tpCriteria.setNoChilds(true);
			ICondition condition = tpCriteria.getConditions().create();
			condition.setAlias(ExportTemplate.PROPERTY_ACTIVATED.getName());
			condition.setValue(emYesNo.YES);
			condition = tpCriteria.getConditions().create();
			condition.setAlias(ExportTemplate.PROPERTY_BOCODE.getName());
			condition.setValue(boCode);
			ISort sort = tpCriteria.getSorts().create();
			sort.setAlias(ExportTemplate.PROPERTY_OBJECTKEY.getName());
			sort.setSortType(SortType.DESCENDING);
			try {
				BORepositoryImportExport boRepository = new BORepositoryImportExport();
				boRepository.setUserToken(OrganizationFactory.SYSTEM_USER.getToken());
				IOperationResult<IExportTemplate> opRslt = boRepository.fetchExportTemplate(tpCriteria);
				if (opRslt.getError() != null) {
					throw opRslt.getError();
				}
				for (IExportTemplate template : opRslt.getResultObjects()) {
					templates.add(new KeyText(template.getObjectKey().toString(),
							String.format("%s(%s*%s)", template.getName(), template.getWidth(), template.getHeight())));
				}
			} catch (Exception e) {
			}
		}
		return templates;
	}

	private IExportTemplate exportTemplate;

	public final IExportTemplate getExportTemplate() {
		if (this.exportTemplate == null) {
			ICriteria criteria = new Criteria();
			criteria.setResultCount(1);
			ICondition condition = criteria.getConditions().create();
			condition.setAlias(ExportTemplate.PROPERTY_ACTIVATED.getName());
			condition.setValue(emYesNo.YES);
			condition = criteria.getConditions().create();
			condition.setAlias(ExportTemplate.PROPERTY_OBJECTKEY.getName());
			condition.setValue(this.getTemplate());
			try {
				BORepositoryImportExport boRepository = new BORepositoryImportExport();
				boRepository.setUserToken(OrganizationFactory.SYSTEM_USER.getToken());
				IOperationResult<IExportTemplate> opRslt = boRepository.fetchExportTemplate(criteria);
				if (opRslt.getError() != null) {
					throw opRslt.getError();
				}
				if (opRslt.getResultObjects().isEmpty()) {
					throw new Exception(I18N.prop("msg_ie_not_found_template", this.getTemplate()));
				}
				for (IExportTemplate item : opRslt.getResultObjects()) {
					this.exportTemplate = item;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return exportTemplate;
	}

	public final void setExportTemplate(IExportTemplate template) {
		this.exportTemplate = template;
	}

	/**
	 * 获取模板元素值
	 * 
	 * @param template
	 * @return
	 */
	protected String templateValue(IExportTemplateItem template) {
		String value = template.getItemString();
		if (value == null) {
			return "";
		}
		if (template.getSourceType() == emDataSourceType.PATH) {
			Object tmp = null;
			if (value.startsWith("${") && value.endsWith("}")) {
				// 变量
				tmp = this.paramValue(value, null);
			} else {
				// 对象属性
				tmp = this.dataValue(value, null);
			}
			if (tmp == null) {
				return "";
			}
			return this.formatValue(tmp, template.getValueFormat());
		}
		if (template.getSourceType() == emDataSourceType.QUERY) {
			// 处理语句中的变量
			Matcher matcher = Pattern.compile(PARAM_PATTERN).matcher(value);
			while (matcher.find()) {
				// 带格式名称${}
				String pName = matcher.group(0).substring(1);
				Object pValue = null;
				if (pName.startsWith("${") && pName.endsWith("}")) {
					// 变量
					pValue = this.paramValue(pName, null);
				} else if (pName.startsWith("$")) {
					// 对象属性
					pValue = this.dataValue(pName, null);
				}
				if (pValue != null) {
					value = value.replace(String.format(PARAM_PATTERN_TEMPLATE, pName), String.valueOf(pValue));
				}
			}
			return this.formatValue(this.queryValue(value, null), template.getValueFormat());
		}
		return value;
	}

	protected abstract <T> T dataValue(String name, T defaults);
}
