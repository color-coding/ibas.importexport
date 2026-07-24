package org.colorcoding.ibas.importexport.html;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.transformer.TransformException;
import org.colorcoding.ibas.importexport.transformer.TransformerHtml;

/**
 * HTML 转换者 测试。
 *
 * <p>覆盖：TH-T01 ~ TH-T04</p>
 * <p>核心：{@link TransformerHtml} 的模板渲染、数据填充、分页计算</p>
 */
public class TestTransformerHtml extends AbstractHtmlTestCase {

	// ==================================================================
	// TH-T01: 基本 HTML 生成（模板 + 数据 -> HTML 文件）
	// ==================================================================

	public void testTH_T01_BasicHtmlGeneration() throws Exception {
		ExportTemplate template = buildMinimalTemplate(30);
		addRepetitionItem(template, "col_name", "$[0].Items[].Name", 50, 0, 200, 30);

		String json = "[{\"Items\":[{\"Name\":\"Alice\"},{\"Name\":\"Bob\"}]}]";
		InputStream data = buildJsonStream(json);

		TransformerHtml transformer = new TransformerHtml();
		transformer.setExportTemplate(template);
		transformer.addInputData(data);
		transformer.setWorkFolder(MyConfiguration.getTempFolder());
		transformer.transform();

		List<File> output = transformer.getOutputData();
		assertEquals("Output file count.", 1, output.size());
		File htmlFile = output.get(0);
		assertTrue("File exists.", htmlFile.exists());
		assertTrue("File is .html.", htmlFile.getName().endsWith(".html"));
		assertTrue("File not empty.", htmlFile.length() > 0);
	}

	// ==================================================================
	// TH-T02: HTML 包含数据值
	// ==================================================================

	public void testTH_T02_HtmlContainsDataValues() throws Exception {
		ExportTemplate template = buildMinimalTemplate(30);
		addRepetitionItem(template, "col_name", "$[0].Items[].Name", 50, 0, 200, 30);

		String json = "[{\"Items\":[{\"Name\":\"AliceZhang\"},{\"Name\":\"BobLi\"}]}]";
		InputStream data = buildJsonStream(json);

		TransformerHtml transformer = new TransformerHtml();
		transformer.setExportTemplate(template);
		transformer.addInputData(data);
		transformer.setWorkFolder(MyConfiguration.getTempFolder());
		transformer.transform();

		File htmlFile = transformer.getOutputData().get(0);
		String content = new String(Files.readAllBytes(htmlFile.toPath()), "utf-8");
		assertTrue("HTML contains 'AliceZhang'.", content.contains("AliceZhang"));
		assertTrue("HTML contains 'BobLi'.", content.contains("BobLi"));
		assertTrue("HTML has <html> tag.", content.contains("<html>"));
		assertTrue("HTML has </html> tag.", content.contains("</html>"));
	}

	// ==================================================================
	// TH-T03: 分页计算--数据超出单页时生成多页
	// ==================================================================

	public void testTH_T03_MultiPagePagination() throws Exception {
		// 重复区高度 100px，页面可用高度 = 842 - 50 - 50 = 742
		// 每页可容纳 742 / 100 = 7 条数据
		// 10 条数据需要 2 页
		ExportTemplate template = buildMinimalTemplate(100);
		addRepetitionItem(template, "col_name", "$[0].Items[].Name", 50, 0, 200, 100);

		StringBuilder json = new StringBuilder("[{\"Items\":[");
		for (int i = 0; i < 10; i++) {
			if (i > 0) json.append(",");
			json.append(String.format("{\"Name\":\"Item%d\"}", i));
		}
		json.append("]}]");
		InputStream data = buildJsonStream(json.toString());

		TransformerHtml transformer = new TransformerHtml();
		transformer.setExportTemplate(template);
		transformer.addInputData(data);
		transformer.setWorkFolder(MyConfiguration.getTempFolder());
		transformer.transform();

		File htmlFile = transformer.getOutputData().get(0);
		String content = new String(Files.readAllBytes(htmlFile.toPath()), "utf-8");
		// 至少包含 2 个页 div
		int pageDivCount = countOccurrences(content, "id=\"page_");
		assertTrue("At least 2 page divs.", pageDivCount >= 2);
		// 包含全部 10 条数据
		for (int i = 0; i < 10; i++) {
			assertTrue("HTML contains Item" + i, content.contains("Item" + i));
		}
	}

	// ==================================================================
	// TH-T04: 无模板时抛异常
	// ==================================================================

	public void testTH_T04_NoTemplate_Throws() throws Exception {
		TransformerHtml transformer = new TransformerHtml();
		transformer.addInputData(buildJsonStream("[]"));
		transformer.setWorkFolder(MyConfiguration.getTempFolder());
		try {
			transformer.transform();
			fail("Expected TransformException when no template set.");
		} catch (TransformException e) {
			// expected
		}
	}

	/** 统计子串出现次数 */
	private int countOccurrences(String content, String substring) {
		int count = 0;
		int idx = 0;
		while ((idx = content.indexOf(substring, idx)) >= 0) {
			count++;
			idx += substring.length();
		}
		return count;
	}
}
