package org.colorcoding.ibas.importexport.transformer;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplateItem;
import org.colorcoding.ibas.importexport.data.emDataSourceType;

public abstract class TemplateTransformer extends Transformer<InputStream, File> implements ITemplateTransformer {

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
	 * @param name
	 *            变量名
	 * @param value
	 *            值
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
	 * @param name
	 *            名称
	 * @param defaults
	 *            未取到的默认值
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
			if (template.getValueFormat() != null && !template.getValueFormat().isEmpty()) {
				return String.format(template.getValueFormat(), tmp);
			}
			return String.valueOf(tmp);
		}
		return value;
	}

	protected abstract <T> T dataValue(String name, T defaults);
}
