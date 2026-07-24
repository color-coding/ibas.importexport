package org.colorcoding.ibas.importexport.test.transformer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.colorcoding.ibas.bobas.common.DateTimes;
import org.colorcoding.ibas.bobas.common.Decimals;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.KeyText;
import org.colorcoding.ibas.importexport.transformer.TemplateTransformer;
import org.colorcoding.ibas.importexport.transformer.TransformException;

/**
 * 模板转换者 测试。
 *
 * <p>覆盖：TF-T20 ~ TF-T25</p>
 * <p>核心：{@link TemplateTransformer} 的变量管理、值格式化、查询容错</p>
 */
public class TestTemplateTransformer extends AbstractTransformerTestCase {

	/** 测试桩：暴露 protected 方法用于测试 */
	private static class TemplateStub extends TemplateTransformer {
		@Override
		protected <T> T dataValue(String name, T defaults) {
			return defaults;
		}

		@Override
		public void transform() throws TransformException {
			// no-op：仅测试辅助方法
		}

		@Override
		public List<KeyText> matchingTemplates(String boCode) {
			return new ArrayList<>();
		}

		public <T> T callParamValue(String name, T defaults) {
			return this.paramValue(name, defaults);
		}

		public String callFormatValue(Object value, String format) {
			return this.formatValue(value, format);
		}

		public <T> T callQueryValue(String query, T defaults) {
			return this.queryValue(query, defaults);
		}
	}

	// ==================================================================
	// TF-T20: newParam / paramValue 基本存取
	// ==================================================================

	public void testTF_T20_ParamSetGet() {
		TemplateStub stub = new TemplateStub();
		stub.newParam("Name", "Niuren");
		stub.newParam("Count", 42);

		assertEquals("String param.", "Niuren", stub.callParamValue("Name", ""));
		assertEquals("Integer param.", Integer.valueOf(42), stub.callParamValue("Count", 0));
	}

	// ==================================================================
	// TF-T21: paramValue 未设置时返回默认值
	// ==================================================================

	public void testTF_T21_ParamDefault() {
		TemplateStub stub = new TemplateStub();
		assertEquals("Default string.", "fallback", stub.callParamValue("NotExists", "fallback"));
		assertEqualsBD("Default decimal.", Decimals.valueOf(10), stub.callParamValue("NotExists", Decimals.valueOf(10)));
		assertNull("Default null.", stub.callParamValue("NotExists", (String) null));
	}

	// ==================================================================
	// TF-T22: formatValue 无格式返回 String.valueOf
	// ==================================================================

	public void testTF_T22_FormatNoFormat() {
		TemplateStub stub = new TemplateStub();
		assertEquals("String value.", "hello", stub.callFormatValue("hello", null));
		assertEquals("Integer value.", "123", stub.callFormatValue(123, ""));
		assertEquals("Null value.", "", stub.callFormatValue(null, null));
	}

	// ==================================================================
	// TF-T23: formatValue 日期/整数/小数 格式
	// ==================================================================

	public void testTF_T23_FormattedValues() {
		TemplateStub stub = new TemplateStub();
		DateTime now = DateTimes.valueOf(2026, 7, 25);

		// 日期格式 %tY -> 年份
		String dateResult = stub.callFormatValue(now, "%tY");
		assertEquals("Date year formatted.", "2026", dateResult);

		// 整数格式 %d
		String intResult = stub.callFormatValue(42, "%05d");
		assertEquals("Integer formatted.", "00042", intResult);

		// 小数格式 %f
		String decResult = stub.callFormatValue(3.14159, "%.2f");
		assertEquals("Decimal formatted.", "3.14", decResult);
	}

	// ==================================================================
	// TF-T24: formatValue 人民币转换（￥标记）
	// ==================================================================

	public void testTF_T24_FormatChineseYuan() {
		TemplateStub stub = new TemplateStub();
		BigDecimal amount = Decimals.valueOf("1234.56");
		String result = stub.callFormatValue(amount, "%￥");
		assertNotNull("Yuan result not null.", result);
		assertTrue("Yuan result not empty.", result.length() > 0);
		// 人民币大写应包含“壹”或“元”等字符
		assertTrue("Yuan result contains currency char.", result.contains("元") || result.contains("壹"));
	}

	// ==================================================================
	// TF-T25: queryValue 空/null 返回默认值（不触达数据库）
	// ==================================================================

	public void testTF_T25_QueryEmptyReturnsDefault() {
		TemplateStub stub = new TemplateStub();
		assertEquals("Null query default.", "def", stub.callQueryValue(null, "def"));
		assertEquals("Empty query default.", "def", stub.callQueryValue("", "def"));
		assertEqualsBD("Null query decimal default.", Decimals.valueOf(99),
				stub.callQueryValue(null, Decimals.valueOf(99)));
	}
}
