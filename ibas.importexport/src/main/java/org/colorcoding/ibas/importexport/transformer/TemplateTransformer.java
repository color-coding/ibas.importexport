package org.colorcoding.ibas.importexport.transformer;

import java.io.InputStream;
import java.io.OutputStream;

import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;

public abstract class TemplateTransformer extends Transformer<InputStream, OutputStream>
		implements ITransformer<InputStream, OutputStream> {

	private IExportTemplate template;

	public final IExportTemplate getTemplate() {
		return template;
	}

	public final void setTemplate(IExportTemplate template) {
		this.template = template;
	}
}
