package org.colorcoding.ibas.importexport.test.transformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.UUID;

import org.colorcoding.ibas.bobas.bo.BOFactory;
import org.colorcoding.ibas.bobas.common.DateTimes;
import org.colorcoding.ibas.bobas.common.Decimals;
import org.colorcoding.ibas.bobas.configuration.Configuration;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializationFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplateItem;
import org.colorcoding.ibas.importexport.transformer.JsonTransformer;
import org.colorcoding.ibas.importexport.transformer.XmlTransformer;

import junit.framework.TestCase;

/**
 * 转换者测试基类。
 *
 * <p>提供：BO 构造、文件序列化、断言辅助等公共方法。</p>
 * <p>风格参考：ibas.purchase.AbstractPurchaseQuantityTestCase</p>
 */
public abstract class AbstractTransformerTestCase extends TestCase {

	/** 模板重复区行数（测试数据固定值） */
	protected static final int TEMPLATE_ITEM_COUNT = 3;

	static {
		// 注册转换者工厂配置，使 TransformerFactory 可发现 JsonTransformer / XmlTransformer
		Configuration.addConfigValue(MyConfiguration.CONFIG_ITEM_TRANSFORMER_FACTORY, String.format("%s;%s",
				JsonTransformer.class.getSimpleName(), XmlTransformer.class.getSimpleName()));
	}

	/** 序列化类型 */
	protected enum SerializeType {
		JSON(JsonTransformer.TYPE_NAME), XML(XmlTransformer.TYPE_NAME);

		private final String typeName;

		private SerializeType(String typeName) {
			this.typeName = typeName;
		}

		public String getTypeName() {
			return typeName;
		}
	}

	/** 注册业务对象命名空间，使 BOFactory 可识别 BO 类型 */
	protected void registerBONamespaces() {
		for (Class<?> item : BOFactory.loadClasses("org.colorcoding.ibas")) {
			BOFactory.register(item);
		}
	}

	/** 构造测试用导出模板（含 {@value #TEMPLATE_ITEM_COUNT} 个重复项） */
	protected ExportTemplate buildExportTemplate() {
		ExportTemplate template = new ExportTemplate();
		template.setBOCode(ExportTemplate.BUSINESS_OBJECT_CODE);
		String stamp = DateTimes.now().toString("yyyyMMddHHmmss");
		for (int i = 0; i < TEMPLATE_ITEM_COUNT; i++) {
			IExportTemplateItem item = template.getRepetitions().create();
			item.setItemID(String.format("%s-%s", stamp, i));
		}
		return template;
	}

	/** 序列化 BO 到临时文件，返回文件对象 */
	protected File serializeToFile(Object bo, SerializeType type, Class<?>... knownTypes) throws IOException {
		ISerializer serializer = SerializationFactory.createManager().create(type.getTypeName());
		String filePath = String.format("%s%s~%s.%s", MyConfiguration.getDataFolder(), File.separator,
				UUID.randomUUID().toString(), type.getTypeName());
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		try (OutputStream outputStream = new FileOutputStream(file)) {
			serializer.serialize(bo, outputStream, knownTypes);
		}
		return file;
	}

	/** 断言输出 BO 数量 */
	protected void assertBOCount(String msg, int expected, int actual) {
		assertEquals(msg, expected, actual);
	}

	/** 断言文件存在 */
	protected void assertFileExists(String msg, File file) {
		assertNotNull(msg + " [file is null].", file);
		assertTrue(msg + " [file not exists: " + file.getPath() + "].", file.exists());
	}

	/** BigDecimal 比较（null 视为零） */
	protected void assertEqualsBD(String msg, BigDecimal expected, BigDecimal actual) {
		BigDecimal e = expected == null ? Decimals.VALUE_ZERO : expected;
		BigDecimal a = actual == null ? Decimals.VALUE_ZERO : actual;
		assertEquals(msg + " expected=" + e + ", actual=" + a, 0, e.compareTo(a));
	}
}
