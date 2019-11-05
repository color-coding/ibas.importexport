package org.colorcoding.ibas.importexport.transformer;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.SqlQuery;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.Decimal;
import org.colorcoding.ibas.bobas.data.SingleValue;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.bobas.repository.BORepository4DbReadonly;
import org.colorcoding.ibas.bobas.repository.BORepositoryService;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplateItem;
import org.colorcoding.ibas.importexport.data.DataConvert;
import org.colorcoding.ibas.importexport.data.emDataSourceType;

public abstract class TemplateTransformer extends Transformer<InputStream, File> implements ITemplateTransformer {

	/**
	 * 变量样式，(XXXXXX)
	 */
	public static final String PARAM_PATTERN = "(?=\\(\\$)[^\\)]+";
	public static final String PARAM_PATTERN_TEMPLATE = "(%s)";
	public static final String PARAM_PAGE_INDEX = "${PAGE_INDEX}";
	public static final String PARAM_PAGE_TOTAL = "${PAGE_TOTAL}";
	public static final String PARAM_DATA_SIZE = "${DATA_SIZE}";
	public static final String PARAM_DATA_INDEX = "${DATA_INDEX}";
	public static final String PARAM_TIME_NOW = "${TIME_NOW}";
	public static final String PARAM_TEMPLATE_PAGE_DATA_INDEX = "${PAGE_%s_DATA_INDEX}";
	public static final String PARAM_PAGE_DATA_BEGIN = "${PAGE_DATA_BEGIN}";
	public static final String PARAM_PAGE_DATA_END = "${PAGE_DATA_END}";

	private IExportTemplate template;

	public final IExportTemplate getTemplate() {
		return template;
	}

	public final void setTemplate(IExportTemplate template) {
		this.template = template;
	}

	private String workFolder;

	public void setWorkFolder(String folder) {
		this.workFolder = folder;
	}

	public String getWorkFolder() {
		if (this.workFolder != null && !this.workFolder.isEmpty()) {
			if (!this.workFolder.endsWith(File.separator)) {
				this.workFolder += File.separator;
			}
		}
		return this.workFolder;
	}

	private Map<String, Object> params;

	/**
	 * 新变量值
	 * 
	 * @param name  变量名
	 * @param value 值
	 */
	protected <T> void newParam(String name, T value) {
		if (this.params == null) {
			this.params = new HashMap<>();
		}
		this.params.put(name, value);
		Logger.log(MessageLevel.DEBUG, "transformer: new param [%s], value [%s].", name, value);
	}

	/**
	 * 获取变量值
	 * 
	 * @param name     名称
	 * @param defaults 未取到的默认值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T paramValue(String name, T defaults) {
		if (this.params == null) {
			return defaults;
		}
		Object value = this.params.get(name);
		if (value == null) {
			return defaults;
		}
		return (T) value;
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

	/**
	 * 格式化值
	 * 
	 * @param value  值
	 * @param format 格式
	 * @return
	 */
	protected String formatValue(Object value, String format) {
		if (value == null) {
			return "";
		}
		if (format == null || format.isEmpty()) {
			return String.valueOf(value);
		}
		int pIndex, sIndex;
		// String.format 转换
		pIndex = format.indexOf("%");
		if (pIndex >= 0) {
			// 日期类型转换
			sIndex = format.indexOf("t");
			if (sIndex > 0 && sIndex > pIndex) {
				if (value instanceof String) {
					value = DateTime.valueOf((String) value);
				} else if (value instanceof Long) {
					value = DateTime.valueOf((Long) value);
				} else if (value instanceof Integer) {
					value = DateTime.valueOf(Long.valueOf(value.toString()));
				}
			}
			// 整数类型转换
			sIndex = format.indexOf("d");
			if (sIndex > 0 && sIndex > pIndex) {
				value = Long.valueOf(value.toString());
			}
			// 小数类型转换
			sIndex = format.indexOf("f");
			if (sIndex > 0 && sIndex > pIndex) {
				value = Double.valueOf(value.toString());
			}
			// 人民币转换
			sIndex = format.indexOf("￥");
			if (sIndex > 0 && sIndex > pIndex) {
				value = DataConvert.toChineseYuan(Decimal.valueOf(value.toString()));
				format = format.replace("￥", "s");
			}
			// 字符串格式化
			return String.format(format, value);
		}
		return String.valueOf(value);
	}

	/**
	 * 获取语句值
	 * 
	 * @param query    语句
	 * @param defaults 未取到的默认值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T queryValue(String query, T defaults) {
		if (query == null || query.isEmpty()) {
			return defaults;
		}
		try {
			BORepository4DbReadonly boRepository = new BORepository4DbReadonly(
					BORepositoryService.MASTER_REPOSITORY_SIGN);
			IOperationResult<SingleValue> operationResult = boRepository.fetch(new SqlQuery(query));
			if (operationResult.getError() != null) {
				throw operationResult.getError();
			}
			SingleValue value = operationResult.getResultObjects().firstOrDefault();
			if (value != null) {
				return (T) value.getValue();
			}
		} catch (Exception e) {
			Logger.log(MessageLevel.WARN, e);
		}
		return defaults;
	}

	protected abstract <T> T dataValue(String name, T defaults);
}
