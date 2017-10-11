package org.colorcoding.ibas.importexport.transformers.excel;

import org.colorcoding.ibas.importexport.transformer.TransformException;
import org.colorcoding.ibas.importexport.transformer.TransformerFile;

/**
 * 业务对象转换xlsx文件
 * 
 * @author Niuren.Zhu
 *
 */
public class TransformerExcel extends TransformerFile {

	public final static String TYPE_NAME = "xlsx";
	public final static String NAME = String.format(GROUP_TEMPLATE, TYPE_NAME).toUpperCase();

	@Override
	public void transform() throws TransformException {
		// TODO Auto-generated method stub

	}

}
