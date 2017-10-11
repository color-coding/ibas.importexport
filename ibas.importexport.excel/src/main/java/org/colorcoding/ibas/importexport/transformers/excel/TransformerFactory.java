package org.colorcoding.ibas.importexport.transformers.excel;

import org.colorcoding.ibas.importexport.transformer.ITransformer;

/**
 * 转换者工厂
 * 
 * @author Niuren.Zhu
 *
 */
public class TransformerFactory {
	private static TransformerFactory instance;

	public static TransformerFactory create() {
		if (instance == null) {
			synchronized (TransformerFactory.class) {
				if (instance == null) {
					instance = new TransformerFactory();
				}
			}
		}
		return instance;
	}

	public ITransformer<?, ?> create(String sign) {
		if (ExcelTransformer.TYPE_NAME.equalsIgnoreCase(sign)) {
			return new ExcelTransformer();
		}
		return null;
	}
}
