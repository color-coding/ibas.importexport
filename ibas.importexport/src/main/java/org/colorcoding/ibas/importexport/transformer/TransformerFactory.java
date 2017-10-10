package org.colorcoding.ibas.importexport.transformer;

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

	public final static String GROUP_FILE_TO = "FILE_%s_TO";

	public ITransformer<?, ?> create(String sign) {
		if (String.format(GROUP_FILE_TO, JsonTransformer.TYPE_NAME).equalsIgnoreCase(sign)) {
			return new JsonTransformer();
		} else if (String.format(GROUP_FILE_TO, XmlTransformer.TYPE_NAME).equalsIgnoreCase(sign)) {
			return new XmlTransformer();
		}
		return null;
	}
}
