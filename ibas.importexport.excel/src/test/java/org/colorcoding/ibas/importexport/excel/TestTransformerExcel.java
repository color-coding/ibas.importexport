package org.colorcoding.ibas.importexport.excel;

import java.io.File;
import java.util.List;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplateItem;
import org.colorcoding.ibas.importexport.transformer.ExcelTransformer;
import org.colorcoding.ibas.importexport.transformer.TransformException;
import org.colorcoding.ibas.importexport.transformer.TransformerExcel;

/**
 * Excel 转换者 测试。
 *
 * <p>覆盖：TX-E01 ~ TX-E05</p>
 * <p>核心：{@link TransformerExcel}（BO->Excel）与 {@link ExcelTransformer}（Excel->BO）的导出、导入、往返</p>
 */
public class TestTransformerExcel extends AbstractExcelTestCase {

	private static final int ITEM_COUNT = 3;

	// ==================================================================
	// TX-E01: 导出单个 BO 到 Excel 文件
	// ==================================================================

	public void testTX_E01_ExportSingleBO() throws Exception {
		registerBONamespaces();
		ExportTemplate template = buildExportTemplate(ITEM_COUNT);

		TransformerExcel transformer = new TransformerExcel();
		transformer.setWorkFolder(MyConfiguration.getTempFolder());
		transformer.addInputData(template);
		transformer.transform();

		List<File> output = transformer.getOutputData();
		assertEquals("Output file count.", 1, output.size());
		assertExcelFile("Exported single BO.", output.get(0));
	}

	// ==================================================================
	// TX-E02: 导出带子项的 BO，验证子项行数
	// ==================================================================

	public void testTX_E02_ExportBOWithChildren() throws Exception {
		registerBONamespaces();
		ExportTemplate template = buildExportTemplate(ITEM_COUNT);

		TransformerExcel transformer = new TransformerExcel();
		transformer.setWorkFolder(MyConfiguration.getTempFolder());
		transformer.addInputData(template);
		transformer.transform();

		File excelFile = transformer.getOutputData().get(0);
		assertExcelFile("Exported BO with children.", excelFile);

		// 导入验证子项
		ExcelTransformer importer = new ExcelTransformer();
		importer.addInputData(excelFile);
		importer.transform();
		List<IBusinessObject> imported = importer.getOutputData();

		assertEquals("Imported BO count.", 1, imported.size());
		assertTrue("Is ExportTemplate.", imported.get(0) instanceof ExportTemplate);
		IExportTemplate result = (IExportTemplate) imported.get(0);
		assertEquals("Repetitions count preserved.", ITEM_COUNT, result.getRepetitions().size());
	}

	/** 构造带唯一标识的测试模板（避免读取端合并重复行） */
	private ExportTemplate buildUniqueTemplate(int seq, int itemCount) {
		ExportTemplate template = new ExportTemplate();
		template.setBOCode(ExportTemplate.BUSINESS_OBJECT_CODE);
		template.setActivated(emYesNo.YES);
		template.setName(String.format("Template-%d", seq));
		for (int i = 0; i < itemCount; i++) {
			IExportTemplateItem item = template.getRepetitions().create();
			item.setItemID(String.format("T%d-%d", seq, i));
		}
		return template;
	}

	// ==================================================================
	// TX-E03: 批量导出（多次 transform 后一次性 getOutputData）
	// ==================================================================

	public void testTX_E03_BatchExport() throws Exception {
		registerBONamespaces();
		final int batchSize = 2;
		final int batches = 3;

		TransformerExcel transformer = new TransformerExcel();
		transformer.setWorkFolder(MyConfiguration.getTempFolder());

		int seq = 0;
		for (int b = 0; b < batches; b++) {
			for (int i = 0; i < batchSize; i++) {
				transformer.addInputData(buildUniqueTemplate(seq++, 1));
			}
			transformer.transform();
		}

		List<File> output = transformer.getOutputData();
		assertEquals("Single output file for batch.", 1, output.size());
		assertExcelFile("Batch exported file.", output.get(0));

		// 导入验证总行数
		ExcelTransformer importer = new ExcelTransformer();
		importer.addInputData(output.get(0));
		importer.transform();
		List<IBusinessObject> imported = importer.getOutputData();

		// 每个 BO 的 Name 唯一，不会被读取端合并
		assertEquals("Imported BO count matches batch.", batchSize * batches, imported.size());
	}

	// ==================================================================
	// TX-E04: 往返验证--导出后导入，属性值保持一致
	// ==================================================================

	public void testTX_E04_RoundTripProperties() throws Exception {
		registerBONamespaces();
		ExportTemplate template = buildExportTemplate(2);
		template.setName("RoundTrip-Test");
		template.setActivated(org.colorcoding.ibas.bobas.data.emYesNo.YES);
		// 设置子项属性
		int idx = 0;
		for (IExportTemplateItem item : template.getRepetitions()) {
			item.setItemID(String.format("RT-%d", idx++));
		}

		// 导出
		TransformerExcel exporter = new TransformerExcel();
		exporter.setWorkFolder(MyConfiguration.getTempFolder());
		exporter.addInputData(template);
		exporter.transform();
		File excelFile = exporter.getOutputData().get(0);

		// 导入
		ExcelTransformer importer = new ExcelTransformer();
		importer.addInputData(excelFile);
		importer.transform();
		List<IBusinessObject> imported = importer.getOutputData();

		assertEquals("Imported count.", 1, imported.size());
		IExportTemplate result = (IExportTemplate) imported.get(0);
		assertEquals("Name preserved.", "RoundTrip-Test", result.getName());
		assertEquals("Activated preserved.", org.colorcoding.ibas.bobas.data.emYesNo.YES,
				result.getActivated());
		assertEquals("Repetitions count.", 2, result.getRepetitions().size());
		assertEquals("First item ID preserved.", "RT-0", result.getRepetitions().get(0).getItemID());
		assertEquals("Second item ID preserved.", "RT-1", result.getRepetitions().get(1).getItemID());
	}

	// ==================================================================
	// TX-E05: 空输入 transform 不抛异常
	// ==================================================================

	public void testTX_E05_EmptyInput_NoOutput() throws Exception {
		TransformerExcel transformer = new TransformerExcel();
		transformer.setWorkFolder(MyConfiguration.getTempFolder());
		// 未添加任何输入数据
		transformer.transform();
		try {
			transformer.getOutputData();
			fail("Expected TransformException when no data transformed.");
		} catch (TransformException e) {
			// expected: no data -> no output
		}
	}
}
