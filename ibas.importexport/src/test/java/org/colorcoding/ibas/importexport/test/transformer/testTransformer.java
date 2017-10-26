package org.colorcoding.ibas.importexport.test.transformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializerFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.DataExportTemplate;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.DataExportTemplateItem;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.IDataExportTemplateItem;
import org.colorcoding.ibas.importexport.transformer.JsonTransformer;
import org.colorcoding.ibas.importexport.transformer.TransformException;
import org.colorcoding.ibas.importexport.transformer.XmlTransformer;

import junit.framework.TestCase;

public class testTransformer extends TestCase {

	private String createFileData(String type) throws IOException {
		DataExportTemplate template = new DataExportTemplate();
		template.setBOCode(DataExportTemplate.BUSINESS_OBJECT_CODE);
		IDataExportTemplateItem item = template.getDataExportTemplateItems().create();
		item.setItemUID(DateTime.getNow().toString());
		item = template.getDataExportTemplateItems().create();
		item.setItemUID(DateTime.getNow().toString());
		item = template.getDataExportTemplateItems().create();
		item.setItemUID(DateTime.getNow().toString());

		ISerializer<?> serializer = SerializerFactory.create().createManager().create(type);
		String filePath = String.format("%s%s~%s.%s", MyConfiguration.getDataFolder(), File.separator,
				UUID.randomUUID().toString(), type);
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream outputStream = new FileOutputStream(file);
		serializer.serialize(template, outputStream);
		return file.getPath();
	}

	public void testJSON() throws TransformException, IOException {
		JsonTransformer transformer = new JsonTransformer();
		transformer.addKnownType(DataExportTemplate.class);
		transformer.addKnownType(DataExportTemplateItem.class);
		// 测试转换BO
		transformer.setInputData(new File(this.createFileData(JsonTransformer.TYPE_NAME)));
		transformer.transform();
		for (Object item : transformer.getOutputData()) {
			System.out.println(item.toString());
		}
	}

	public void testXML() throws TransformException, IOException {
		XmlTransformer transformer = new XmlTransformer();
		transformer.addKnownType(DataExportTemplate.class);
		transformer.addKnownType(DataExportTemplateItem.class);
		// 测试转换BO
		transformer.setInputData(new File(this.createFileData(XmlTransformer.TYPE_NAME)));
		transformer.transform();
		for (Object item : transformer.getOutputData()) {
			System.out.println(item.toString());
		}
	}
}
