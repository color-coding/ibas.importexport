package org.colorcoding.ibas.importexport.transformer.template;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * 文件读取者
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class FileReader {
	private Template template;

	public final Template getTemplate() {
		return template;
	}

	public final void setTemplate(Template template) {
		this.template = template;
	}

	public abstract void read(File file) throws ReadFileException, IOException, InvalidFormatException;
}
