package org.colorcoding.ibas.importexport.transformers.excel;

import org.colorcoding.ibas.bobas.data.KeyText;
import org.colorcoding.ibas.importexport.transformer.ITransformer;

/**
 * 转换者工厂
 * 
 * @author Niuren.Zhu
 *
 */
public class TransformerFactory implements org.colorcoding.ibas.importexport.transformer.TransformerFactory {

	@Override
	public KeyText[] getTransformers() {
		return new KeyText[] { new KeyText(ExcelTransformer.NAME, ExcelTransformer.NAME),
				new KeyText(TransformerExcel.NAME, TransformerExcel.NAME) };
	}

	@Override
	public ITransformer<?, ?> create(String sign) {
		if (ExcelTransformer.NAME.equalsIgnoreCase(sign)) {
			return new ExcelTransformer();
		} else if (TransformerExcel.NAME.equalsIgnoreCase(sign)) {
			return new TransformerExcel();
		}
		return null;
	}
}
