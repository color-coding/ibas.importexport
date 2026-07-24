package org.colorcoding.ibas.importexport.test.transformer;

import org.colorcoding.ibas.importexport.transformer.ITransformer;
import org.colorcoding.ibas.importexport.transformer.JsonTransformer;
import org.colorcoding.ibas.importexport.transformer.TransformException;
import org.colorcoding.ibas.importexport.transformer.TransformerFactory;
import org.colorcoding.ibas.importexport.transformer.TransformerInfo;
import org.colorcoding.ibas.importexport.transformer.XmlTransformer;

/**
 * 转换者工厂 测试。
 *
 * <p>覆盖：TF-T30 ~ TF-T33</p>
 * <p>核心：{@link TransformerFactory} 按签名创建转换者、未知签名异常</p>
 */
public class TestTransformerFactory extends AbstractTransformerTestCase {

	/** 获取转换者签名（@TransformerInfo.name） */
	private String signOf(Class<?> clazz) {
		TransformerInfo info = clazz.getAnnotation(TransformerInfo.class);
		assertNotNull("TransformerInfo annotation exists.", info);
		return info.name();
	}

	// ==================================================================
	// TF-T30: create(FILE_JSON_TO) 返回 JsonTransformer 实例
	// ==================================================================

	public void testTF_T30_CreateJsonTransformer() throws Exception {
		String sign = signOf(JsonTransformer.class);
		ITransformer<?, ?> transformer = TransformerFactory.create().create(sign);
		assertNotNull("Created transformer not null.", transformer);
		assertTrue("Is JsonTransformer.", transformer instanceof JsonTransformer);
	}

	// ==================================================================
	// TF-T31: create(FILE_XML_TO) 返回 XmlTransformer 实例
	// ==================================================================

	public void testTF_T31_CreateXmlTransformer() throws Exception {
		String sign = signOf(XmlTransformer.class);
		ITransformer<?, ?> transformer = TransformerFactory.create().create(sign);
		assertNotNull("Created transformer not null.", transformer);
		assertTrue("Is XmlTransformer.", transformer instanceof XmlTransformer);
	}

	// ==================================================================
	// TF-T32: create(null) 抛 TransformException
	// ==================================================================

	public void testTF_T32_CreateNullSign_Throws() {
		try {
			TransformerFactory.create().create(null);
			fail("Expected TransformException for null sign.");
		} catch (TransformException e) {
			// expected
		}
	}

	// ==================================================================
	// TF-T33: create(未知签名) 抛 TransformException
	// ==================================================================

	public void testTF_T33_CreateUnknownSign_Throws() {
		try {
			TransformerFactory.create().create("NOT_EXIST_TRANSFORMER");
			fail("Expected TransformException for unknown sign.");
		} catch (TransformException e) {
			// expected
		}
	}
}
