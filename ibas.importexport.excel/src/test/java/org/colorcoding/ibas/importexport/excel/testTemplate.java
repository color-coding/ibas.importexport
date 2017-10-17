package org.colorcoding.ibas.importexport.excel;

import java.io.File;
import java.io.IOException;

import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.DataExportTemplate;
import org.colorcoding.ibas.importexport.transformers.excel.template.Object;
import org.colorcoding.ibas.importexport.transformers.excel.template.ParsingException;
import org.colorcoding.ibas.importexport.transformers.excel.template.Property;
import org.colorcoding.ibas.importexport.transformers.excel.template.Template;
import org.colorcoding.ibas.importexport.transformers.excel.template.WriteFileException;

import junit.framework.TestCase;

public class testTemplate extends TestCase {

	public void testRresolving() throws ParsingException, WriteFileException, IOException {
		DataExportTemplate bObject = new DataExportTemplate();
		System.out.println(bObject.getClass().getName());
		System.out.println(bObject.getDataExportTemplateItems().create().getClass().getName());

		Template template = new Template();
		template.resolving(new DataExportTemplate());
		System.out.println(String.format("%s", template.toString()));
		System.out.println(String.format("%s", template.getHead().toString()));
		for (Object object : template.getObjects()) {
			System.out.println(String.format("  %s %s", object.toString(),
					object.getBindingClass() != null ? object.getBindingClass().getSimpleName() : "---"));
			for (Property property : object.getProperties()) {
				System.out.println(String.format("    %s %s", property.toString(),
						property.getBindingClass() != null ? property.getBindingClass().getSimpleName() : "---"));
			}
		}
		File file = new File(String.format("%s%soutput_%s.xlsx", MyConfiguration.getWorkFolder(), File.separator,
				DateTime.getNow().getTime()));
		template.write(file);
		System.out.println(file.getPath());
	}
}