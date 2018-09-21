package org.colorcoding.ibas.importexport.test.transformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.colorcoding.ibas.bobas.core.BOFactory;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializerFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplateItem;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplateItem;
import org.colorcoding.ibas.importexport.transformer.JsonTransformer;
import org.colorcoding.ibas.importexport.transformer.TemplateTransformer;
import org.colorcoding.ibas.importexport.transformer.TransformException;
import org.colorcoding.ibas.importexport.transformer.XmlTransformer;

import junit.framework.TestCase;

public class TestTransformer extends TestCase {

	private String createFileData(String type) throws IOException {
		// 加载命名空间
		for (Class<?> item : BOFactory.create().loadClasses("org.colorcoding.ibas")) {
			BOFactory.create().register(item);
		}
		// 创建测试数据
		ExportTemplate template = new ExportTemplate();
		template.setBOCode(ExportTemplate.BUSINESS_OBJECT_CODE);
		IExportTemplateItem item = template.getRepetitions().create();
		item.setItemID(DateTime.getNow().toString());
		item = template.getRepetitions().create();
		item.setItemID(DateTime.getNow().toString());
		item = template.getRepetitions().create();
		item.setItemID(DateTime.getNow().toString());

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
		transformer.addKnownType(ExportTemplate.class);
		transformer.addKnownType(ExportTemplateItem.class);
		// 测试转换BO
		transformer.setInputData(new File(this.createFileData(JsonTransformer.TYPE_NAME)));
		transformer.transform();
		for (Object item : transformer.getOutputData()) {
			System.out.println(item.toString());
		}
	}

	public void testXML() throws TransformException, IOException {
		XmlTransformer transformer = new XmlTransformer();
		transformer.addKnownType(ExportTemplate.class);
		transformer.addKnownType(ExportTemplateItem.class);
		// 测试转换BO
		transformer.setInputData(new File(this.createFileData(XmlTransformer.TYPE_NAME)));
		transformer.transform();
		for (Object item : transformer.getOutputData()) {
			System.out.println(item.toString());
		}
	}

	public void testRegex() {
		String content = "select Name from CC_SYS_USER where DocEntry = ($[0].DataOwner)(${PAGE_%s_DATA_INDEX})";
		// 从内容上截取路径数组
		Matcher matcher = Pattern.compile(TemplateTransformer.PARAM_PATTERN).matcher(content);
		while (matcher.find()) {
			System.out.println(matcher.group(0).substring(1));
		}
		// 复杂一点儿的
		content = "select CONCAT(N'($[0].ShippingAddresss[${PAGE_%s_DATA_INDEX}].Province)', N'($[0].ShippingAddresss[${PAGE_%s_DATA_INDEX}].City)', N'($[0].ShippingAddresss[0].District)', N'($[0].ShippingAddresss[0].Street)') as 'Address'";
		matcher = Pattern.compile(TemplateTransformer.PARAM_PATTERN).matcher(content);
		while (matcher.find()) {
			System.out.println(matcher.group(0).substring(1));
		}
	}
}
