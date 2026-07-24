package org.colorcoding.ibas.importexport.html;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.colorcoding.ibas.bobas.data.KeyText;
import org.colorcoding.ibas.importexport.transformer.TransformerHtml;

/**
 * HTML 数据值提取 测试。
 *
 * <p>覆盖：TH-T10 ~ TH-T13</p>
 * <p>核心：{@link TransformerHtml#dataValue(String, Object)} 的 JsonPath 路径解析</p>
 */
public class TestHtmlDataValue extends AbstractHtmlTestCase {

	/** 测试桩：暴露 protected dataValue 供测试调用 */
	private static class HtmlStub extends TransformerHtml {
		@Override
		public List<KeyText> matchingTemplates(String boCode) {
			return new ArrayList<>();
		}

		@SuppressWarnings("unchecked")
		public <T> T callDataValue(String name, T defaults) {
			return (T) this.dataValue(name, defaults);
		}
	}

	private HtmlStub createStub(String json) {
		HtmlStub stub = new HtmlStub();
		InputStream data = buildJsonStream(json);
		stub.addInputData(data);
		return stub;
	}

	// ==================================================================
	// TH-T10: 简单属性路径
	// ==================================================================

	public void testTH_T10_SimpleProperty() {
		HtmlStub stub = createStub("[{\"Name\":\"Alice\",\"Code\":\"A001\"}]");
		assertEquals("Name value.", "Alice", stub.callDataValue("$[0].Name", ""));
		assertEquals("Code value.", "A001", stub.callDataValue("$[0].Code", ""));
	}

	// ==================================================================
	// TH-T11: 不存在的路径返回默认值
	// ==================================================================

	public void testTH_T11_PathNotFound_Default() {
		HtmlStub stub = createStub("[{\"Name\":\"Alice\"}]");
		assertEquals("Default for missing path.", "fallback",
				stub.callDataValue("$[0].NotExists", "fallback"));
		assertNull("Null default for missing path.", stub.callDataValue("$[0].NotExists", (String) null));
	}

	// ==================================================================
	// TH-T12: 数组路径提取--聚合子属性值
	// ==================================================================

	public void testTH_T12_ArrayPath_Aggregate() {
		String json = "[{\"Items\":[{\"Name\":\"A\"},{\"Name\":\"B\"},{\"Name\":\"C\"}]}]";
		HtmlStub stub = createStub(json);
		// $[0].Items[].Name 应聚合为 "A,B,C"
		Object value = stub.callDataValue("$[0].Items[].Name", "");
		assertNotNull("Array path value not null.", value);
		String strValue = String.valueOf(value);
		assertTrue("Contains 'A'.", strValue.contains("A"));
		assertTrue("Contains 'B'.", strValue.contains("B"));
		assertTrue("Contains 'C'.", strValue.contains("C"));
	}

	// ==================================================================
	// TH-T13: 嵌套属性链
	// ==================================================================

	public void testTH_T13_NestedPropertyChain() {
		String json = "[{\"Customer\":{\"Name\":\"Acme Corp\",\"Phone\":\"123456\"}}]";
		HtmlStub stub = createStub(json);
		assertEquals("Nested name.", "Acme Corp",
				stub.callDataValue("$[0].Customer.Name", ""));
		assertEquals("Nested phone.", "123456",
				stub.callDataValue("$[0].Customer.Phone", ""));
	}
}
