package org.colorcoding.ibas.importexport.transformers;

import org.colorcoding.ibas.bobas.i18n.i18n;

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

	public static ITransformer create(String sign) throws TransformException {

		throw new TransformException(i18n.prop("msg_importexport_not_found_transformer", sign));
	}
}
