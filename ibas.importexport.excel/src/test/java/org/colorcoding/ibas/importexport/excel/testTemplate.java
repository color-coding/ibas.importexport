package org.colorcoding.ibas.importexport.excel;

import java.io.File;
import java.io.IOException;

import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.DataExportTemplate;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.IDataExportTemplateItem;
import org.colorcoding.ibas.importexport.transformers.excel.template.Cell;
import org.colorcoding.ibas.importexport.transformers.excel.template.Object;
import org.colorcoding.ibas.importexport.transformers.excel.template.ParsingException;
import org.colorcoding.ibas.importexport.transformers.excel.template.Property;
import org.colorcoding.ibas.importexport.transformers.excel.template.Template;
import org.colorcoding.ibas.importexport.transformers.excel.template.WriteFileException;

import junit.framework.TestCase;

public class testTemplate extends TestCase {

	public void testRresolving() throws ParsingException, WriteFileException, IOException {
		DataExportTemplate data = new DataExportTemplate();
		System.out.println(data.getClass().getName());
		System.out.println(data.getDataExportTemplateItems().create().getClass().getName());
		data.setBOCode(DataExportTemplate.BUSINESS_OBJECT_CODE);
		data.setCopyNumber(100);
		data.setCreateDate(DateTime.getToday());
		IDataExportTemplateItem item = data.getDataExportTemplateItems().create();
		item.setItemUID("0001");
		item.setItemVisible(emYesNo.NO);
		item = data.getDataExportTemplateItems().create();
		item.setItemUID("0002");
		item.setItemVisible(emYesNo.YES);

		data.markOld(true); // 设置为老数据，测试模板输出内容
		// data = new DataExportTemplate();// 测试纯模板输出

		Template template = new Template();
		template.resolving(data);
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
		System.out.println(String.format("%s: ", template.getDatas().toString()));
		for (int i = 0; i < template.getDatas().getRows().size(); i++) {
			Cell[] row = template.getDatas().getRows().get(i);
			for (Cell cell : row) {
				System.out.print(cell == null ? "" : cell.getValue());
				System.out.print(",");
			}
			System.out.println();
		}
		File file = new File(String.format("%s%soutput_%s.xlsx", MyConfiguration.getWorkFolder(), File.separator,
				DateTime.getNow().getTime()));
		template.write(file);
		System.out.println(file.getPath());
	}
}