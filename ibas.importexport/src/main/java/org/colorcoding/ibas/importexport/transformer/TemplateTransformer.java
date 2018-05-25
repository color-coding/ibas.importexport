package org.colorcoding.ibas.importexport.transformer;

import java.io.File;
import java.io.InputStream;

import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;

public abstract class TemplateTransformer extends Transformer<InputStream, File> implements ITemplateTransformer {

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
		return this.workFolder;
	}
}
