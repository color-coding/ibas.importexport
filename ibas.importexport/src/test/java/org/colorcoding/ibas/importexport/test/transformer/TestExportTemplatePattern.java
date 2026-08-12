package org.colorcoding.ibas.importexport.test.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.colorcoding.ibas.importexport.transformer.ExportTemplateTransformer;

/**
 * 导出模板转换者-变量模式 测试。
 *
 * <p>覆盖：TF-T40 ~ TF-T42</p>
 * <p>核心：{@link ExportTemplateTransformer#PARAM_PATTERN} 正则提取查询语句中的变量</p>
 */
public class TestExportTemplatePattern extends AbstractTransformerTestCase {

	/** 使用 PARAM_PATTERN 提取所有匹配项 */
	private List<String> extractMatches(String content) {
		List<String> matches = new ArrayList<>();
		Matcher matcher = Pattern.compile(ExportTemplateTransformer.PARAM_PATTERN).matcher(content);
		while (matcher.find()) {
			// 去掉前导 "$" 分隔符，保留完整变量表达式
			matches.add(matcher.group(0).substring(1));
		}
		return matches;
	}

	// ==================================================================
	// TF-T40: 简单路径变量提取
	// ==================================================================

	public void testTF_T40_SimplePathVariables() {
		String content = "select Name from CC_SYS_USER where DocEntry = ($[0].DataOwner)";
		List<String> matches = extractMatches(content);

		assertEquals("One variable matched.", 1, matches.size());
		assertEquals("Variable expression.", "$[0].DataOwner", matches.get(0));
	}

	// ==================================================================
	// TF-T41: 复杂嵌套变量提取（含分页索引变量）
	// ==================================================================

	public void testTF_T41_ComplexNestedVariables() {
		String content = "select CONCAT(N'($[0].ShippingAddresss[${PAGE_%s_DATA_INDEX}].Province)', "
				+ "N'($[0].ShippingAddresss[${PAGE_%s_DATA_INDEX}].City)', "
				+ "N'($[0].ShippingAddresss[0].District)', "
				+ "N'($[0].ShippingAddresss[0].Street)') as 'Address'";
		List<String> matches = extractMatches(content);

		assertEquals("Four variables matched.", 4, matches.size());
		assertEquals("First variable.", "$[0].ShippingAddresss[${PAGE_%s_DATA_INDEX}].Province", matches.get(0));
		assertEquals("Second variable.", "$[0].ShippingAddresss[${PAGE_%s_DATA_INDEX}].City", matches.get(1));
		assertEquals("Third variable.", "$[0].ShippingAddresss[0].District", matches.get(2));
		assertEquals("Fourth variable.", "$[0].ShippingAddresss[0].Street", matches.get(3));
	}

	// ==================================================================
	// TF-T42: PARAM_PATTERN_TEMPLATE 格式化替换
	//   说明：PARAM_PATTERN_TEMPLATE = "(%s)"，替换时括号作为模式的一部分被整体替换
	// ==================================================================

	public void testTF_T42_PatternTemplateReplacement() {
		String varName = "$[0].DataOwner";
		String content = String.format("where id = (%s)", varName);
		// 模拟 templateValue 中的替换逻辑：(pName) 整体替换为值
		String replaced = content.replace(
				String.format(ExportTemplateTransformer.PARAM_PATTERN_TEMPLATE, varName), "1001");
		assertEquals("Variable replaced (parens consumed).", "where id = 1001", replaced);
	}
}
