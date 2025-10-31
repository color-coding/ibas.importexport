package org.colorcoding.ibas.importexport.data;

import java.io.InputStream;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.core.Serializable;

/**
 * 数据导出信息
 * 
 * @author Niuren.Zhu
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "DataExportInfo")
@XmlRootElement(name = "DataExportInfo")
public class DataExportInfo extends Serializable {

	public static final String CONDITION_ALIAS_TRANSFORMER = "Transformer";
	public static final String CONDITION_ALIAS_PRINTABLE = "Printable";

	private static final long serialVersionUID = 2424238078421045124L;

	private String transformer;

	@XmlElement(name = "Transformer")
	public final String getTransformer() {
		return transformer;
	}

	public final void setTransformer(String transformer) {
		this.transformer = transformer;
	}

	private String template;

	@XmlElement(name = "Template")
	public final String getTemplate() {
		return template;
	}

	public final void setTemplate(String template) {
		this.template = template;
	}

	private Criteria criteria;

	@XmlElement(name = "Criteria")
	public final Criteria getCriteria() {
		return criteria;
	}

	public final void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	private String description;

	@XmlElement(name = "Description")
	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	private String contentType;

	@XmlElement(name = "ContentType")
	public final String getContentType() {
		return contentType;
	}

	public final void setContentType(String contentType) {
		this.contentType = contentType;
	}

	private InputStream content;

	public final InputStream getContent() {
		return content;
	}

	public final void setContent(InputStream content) {
		this.content = content;
	}
}
