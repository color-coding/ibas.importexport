package org.colorcoding.ibas.importexport.excel;

import org.colorcoding.ibas.importexport.bo.dataexporttemplate.DataExportTemplate;
import org.colorcoding.ibas.importexport.transformers.excel.template.NotRecognizedException;
import org.colorcoding.ibas.importexport.transformers.excel.template.Object;
import org.colorcoding.ibas.importexport.transformers.excel.template.Property;
import org.colorcoding.ibas.importexport.transformers.excel.template.Template;

import junit.framework.TestCase;

public class testTemplate extends TestCase {

	public void testRresolving() throws NotRecognizedException {
		DataExportTemplate bObject = new DataExportTemplate();
		System.out.println(bObject.getClass().getName());
		System.out.println(bObject.getDataExportTemplateItems().create().getClass().getName());

		Template template = new Template();
		template.resolving(new DataExportTemplate());
		System.out.println(String.format("%s", template.toString()));
		for (Object object : template.getObjects()) {
			System.out.println(String.format("  %s %s", object.toString(),
					object.getBindingClass() != null ? object.getBindingClass().getSimpleName() : "---"));
			for (Property property : object.getProperties()) {
				System.out.println(String.format("    %s %s", property.toString(),
						property.getBindingClass() != null ? property.getBindingClass().getSimpleName() : "---"));
			}
		}
	}
}