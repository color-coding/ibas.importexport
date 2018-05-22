package org.colorcoding.ibas.importexport.transformer.template;

import java.io.File;
import java.io.IOException;

/**
 * 文件写入者
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class FileWriter {

	private Template template;

	public final Template getTemplate() {
		return template;
	}

	public final void setTemplate(Template template) {
		this.template = template;
	}

	public abstract void write(File file) throws WriteFileException, IOException;
}
