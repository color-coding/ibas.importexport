package org.colorcoding.ibas.importexport.test.transformer;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplateItem;
import org.colorcoding.ibas.importexport.transformer.JsonTransformer;
import org.colorcoding.ibas.importexport.transformer.TransformException;
import org.colorcoding.ibas.importexport.transformer.XmlTransformer;

/**
 * 文件转换者 测试。
 *
 * <p>覆盖：TF-T10 ~ TF-T15</p>
 * <p>核心：{@link JsonTransformer} / {@link XmlTransformer} 的文件→BO 往返转换</p>
 */
public class TestFileTransformer extends AbstractTransformerTestCase {

	// ==================================================================
	// TF-T10: JSON 单对象往返（序列化→反序列化）
	// ==================================================================

	public void testTF_T10_JsonSingleRoundTrip() throws Exception {
		registerBONamespaces();
		ExportTemplate template = buildExportTemplate();

		File file = serializeToFile(template, SerializeType.JSON);
		assertFileExists("JSON file created.", file);

		JsonTransformer transformer = new JsonTransformer();
		transformer.addKnownType(ExportTemplate.class);
		transformer.addKnownType(ExportTemplateItem.class);
		transformer.addInputData(file);
		transformer.transform();

		List<IBusinessObject> output = transformer.getOutputData();
		assertBOCount("JSON single object output.", 1, output.size());
		assertTrue("Output is ExportTemplate.", output.get(0) instanceof ExportTemplate);
		assertEquals("BOCode preserved.", ExportTemplate.BUSINESS_OBJECT_CODE,
				((ExportTemplate) output.get(0)).getBOCode());
		assertEquals("Repetitions count preserved.", TEMPLATE_ITEM_COUNT,
				((ExportTemplate) output.get(0)).getRepetitions().size());
	}

	// ==================================================================
	// TF-T11: JSON 数组往返（ArrayList 根元素）
	// ==================================================================

	public void testTF_T11_JsonArrayRoundTrip() throws Exception {
		registerBONamespaces();
		ExportTemplate t1 = buildExportTemplate();
		ExportTemplate t2 = buildExportTemplate();

		// 序列化数组（需传入元素类型，否则 JAXB 无法 marshal）
		org.colorcoding.ibas.bobas.data.ArrayList<ExportTemplate> list = new org.colorcoding.ibas.bobas.data.ArrayList<>();
		list.add(t1);
		list.add(t2);
		File file = serializeToFile(list, SerializeType.JSON, ExportTemplate.class, ExportTemplateItem.class);
		assertFileExists("JSON array file created.", file);

		JsonTransformer transformer = new JsonTransformer();
		transformer.addKnownType(ExportTemplate.class);
		transformer.addKnownType(ExportTemplateItem.class);
		transformer.addInputData(file);
		transformer.transform();

		List<IBusinessObject> output = transformer.getOutputData();
		assertBOCount("JSON array output.", 2, output.size());
	}

	// ==================================================================
	// TF-T12: XML 单对象往返
	// ==================================================================

	public void testTF_T12_XmlSingleRoundTrip() throws Exception {
		registerBONamespaces();
		ExportTemplate template = buildExportTemplate();

		File file = serializeToFile(template, SerializeType.XML);
		assertFileExists("XML file created.", file);

		XmlTransformer transformer = new XmlTransformer();
		transformer.addKnownType(ExportTemplate.class);
		transformer.addKnownType(ExportTemplateItem.class);
		transformer.addInputData(file);
		transformer.transform();

		List<IBusinessObject> output = transformer.getOutputData();
		assertBOCount("XML single object output.", 1, output.size());
		assertTrue("Output is ExportTemplate.", output.get(0) instanceof ExportTemplate);
		assertEquals("Repetitions count preserved.", TEMPLATE_ITEM_COUNT,
				((ExportTemplate) output.get(0)).getRepetitions().size());
	}

	// ==================================================================
	// TF-T13: XML ArrayList 根元素识别（getKnownTypes 自动发现）
	//   说明：JAXB 无法正确序列化 ArrayList 内容到 XML（已知限制），
	//        此测试手动构造 ArrayList 根元素 XML，验证 XmlTransformer 能识别
	//        根元素并从中发现 BO 类型。
	// ==================================================================

	public void testTF_T13_XmlArrayListRootRecognized() throws Exception {
		registerBONamespaces();
		// 手动构造 ArrayList 根元素 XML（ExportTemplate 使用 bo 命名空间）
		String resolvedBOCode = MyConfiguration.applyVariables(ExportTemplate.BUSINESS_OBJECT_CODE);
		String xmlContent = String.format(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ "<ArrayList xmlns=\"%s\" xmlns:ns2=\"%s\">"
						+ "<ns2:ExportTemplate><ObjectCode>%s</ObjectCode></ns2:ExportTemplate>"
						+ "<ns2:ExportTemplate><ObjectCode>%s</ObjectCode></ns2:ExportTemplate>"
						+ "</ArrayList>",
				org.colorcoding.ibas.bobas.MyConfiguration.NAMESPACE_BOBAS_DATA, MyConfiguration.NAMESPACE_BO,
				resolvedBOCode, resolvedBOCode);
		File file = new File(String.format("%s%s~%s.xml", MyConfiguration.getDataFolder(), File.separator,
				UUID.randomUUID().toString()));
		try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
			writer.write(xmlContent);
		}
		assertFileExists("XML ArrayList file created.", file);

		XmlTransformer transformer = new XmlTransformer();
		transformer.addKnownType(ExportTemplate.class);
		transformer.addKnownType(ExportTemplateItem.class);
		transformer.addInputData(file);

		// 验证 getKnownTypes 识别 ArrayList 根元素并发现 ExportTemplate 类型
		List<Class<?>> knownTypes = transformer.getKnownTypes();
		assertTrue("KnownTypes contains ArrayList (root element).",
				knownTypes.contains(org.colorcoding.ibas.bobas.data.ArrayList.class));
		assertTrue("KnownTypes contains ExportTemplate (from ObjectCode).",
				knownTypes.contains(ExportTemplate.class));
	}

	// ==================================================================
	// TF-T14: 空输入 transform 不抛异常且无输出
	// ==================================================================

	public void testTF_T14_EmptyInput_NoOutput() throws Exception {
		JsonTransformer transformer = new JsonTransformer();
		transformer.addKnownType(ExportTemplate.class);
		// 未添加任何输入文件
		transformer.transform();
		try {
			transformer.getOutputData();
			fail("Expected TransformException when no input provided.");
		} catch (TransformException e) {
			// expected: no input -> no output
		}
	}

	// ==================================================================
	// TF-T15: getKnownTypes 从输入文件自动发现 BO 类型
	// ==================================================================

	public void testTF_T15_KnownTypesAutoDiscovered() throws Exception {
		registerBONamespaces();
		ExportTemplate template = buildExportTemplate();
		File file = serializeToFile(template, SerializeType.JSON);

		JsonTransformer transformer = new JsonTransformer();
		transformer.addInputData(file);

		List<Class<?>> knownTypes = transformer.getKnownTypes();
		assertTrue("KnownTypes contains ExportTemplate.",
				knownTypes.contains(ExportTemplate.class));
	}
}
