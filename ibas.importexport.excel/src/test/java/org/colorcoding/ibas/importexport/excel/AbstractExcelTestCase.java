package org.colorcoding.ibas.importexport.excel;

import java.io.File;

import org.colorcoding.ibas.bobas.bo.BOFactory;
import org.colorcoding.ibas.bobas.common.DateTimes;
import org.colorcoding.ibas.bobas.configuration.Configuration;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplateItem;

import junit.framework.TestCase;

/**
 * Excel 转换者测试基类。
 *
 * <p>提供：BO 构造、断言辅助等公共方法。</p>
 * <p>风格参考：ibas.purchase.AbstractPurchaseQuantityTestCase</p>
 */
public abstract class AbstractExcelTestCase extends TestCase {

	static {
		// 使用英文，跳过 TransformerExcel.describing() 的数据库查询
		Configuration.addConfigValue(
				org.colorcoding.ibas.bobas.MyConfiguration.CONFIG_ITEM_LANGUAGE_CODE, "en");
	}

	/** 注册业务对象命名空间 */
	protected void registerBONamespaces() {
		for (Class<?> item : BOFactory.loadClasses("org.colorcoding.ibas")) {
			BOFactory.register(item);
		}
	}

	/** 构造测试用导出模板（含重复项） */
	protected ExportTemplate buildExportTemplate(int itemCount) {
		ExportTemplate template = new ExportTemplate();
		template.setBOCode(ExportTemplate.BUSINESS_OBJECT_CODE);
		template.setActivated(emYesNo.YES);
		String stamp = DateTimes.now().toString("yyyyMMddHHmmss");
		for (int i = 0; i < itemCount; i++) {
			IExportTemplateItem item = template.getRepetitions().create();
			item.setItemID(String.format("%s-%s", stamp, i));
		}
		return template;
	}

	/** 断言文件存在且扩展名正确 */
	protected void assertExcelFile(String msg, File file) {
		assertNotNull(msg + " [file is null].", file);
		assertTrue(msg + " [file not exists: " + file.getPath() + "].", file.exists());
		assertTrue(msg + " [not xlsx].", file.getName().toLowerCase().endsWith(".xlsx"));
		assertTrue(msg + " [file is empty].", file.length() > 0);
	}
}
