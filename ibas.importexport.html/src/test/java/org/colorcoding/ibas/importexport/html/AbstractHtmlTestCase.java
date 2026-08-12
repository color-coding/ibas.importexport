package org.colorcoding.ibas.importexport.html;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplateItem;
import org.colorcoding.ibas.importexport.data.emDataSourceType;

import junit.framework.TestCase;

/**
 * HTML 转换者测试基类。
 *
 * <p>提供：模板构造、JSON 数据构造等公共方法。</p>
 * <p>风格参考：ibas.purchase.AbstractPurchaseQuantityTestCase</p>
 */
public abstract class AbstractHtmlTestCase extends TestCase {

	/** 构造最小化导出模板（A4 尺寸，仅重复区） */
	protected ExportTemplate buildMinimalTemplate(int repetitionHeight) {
		ExportTemplate template = new ExportTemplate();
		template.setObjectKey(1);
		template.setName("Test-Template");
		template.setActivated(emYesNo.YES);
		template.setBOCode("TEST_BO");
		// A4 尺寸（72 DPI）
		template.setWidth(595);
		template.setHeight(842);
		template.setDpi(72);
		// 页边距
		template.setMarginTop(50);
		template.setMarginBottom(50);
		template.setMarginArea(10);
		// 无页眉页脚、无开始结束区
		template.setPageHeaderHeight(0);
		template.setPageFooterHeight(0);
		template.setStartSectionHeight(0);
		template.setEndSectionHeight(0);
		// 重复区
		template.setRepetitionHeaderHeight(0);
		template.setRepetitionFooterHeight(0);
		template.setRepetitionHeight(repetitionHeight);
		template.setRepetitionHeaderLeft(50);
		template.setRepetitionHeaderWidth(495);
		template.setRepetitionLeft(50);
		template.setRepetitionWidth(495);
		return template;
	}

	/** 添加一个重复区数据项（PATH 类型，JsonPath 表达式） */
	protected void addRepetitionItem(ExportTemplate template, String itemID, String jsonPath, int left,
			int top, int width, int height) {
		IExportTemplateItem item = template.getRepetitions().create();
		item.setItemID(itemID);
		item.setItemString(jsonPath);
		item.setSourceType(emDataSourceType.PATH);
		item.setItemLeft(left);
		item.setItemTop(top);
		item.setItemWidth(width);
		item.setItemHeight(height);
		item.setItemVisible(emYesNo.YES);
	}

	/** 构造 JSON 输入数据流 */
	protected InputStream buildJsonStream(String json) {
		return new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
	}
}
