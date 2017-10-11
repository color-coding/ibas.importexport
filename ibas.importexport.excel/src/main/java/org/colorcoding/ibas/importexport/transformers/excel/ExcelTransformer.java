package org.colorcoding.ibas.importexport.transformers.excel;

import org.colorcoding.ibas.importexport.transformer.FileTransformer;
import org.colorcoding.ibas.importexport.transformer.TransformException;

/**
 * xlsx文件转换业务对象
 * 
 * @author Niuren.Zhu
 *
 */
public class ExcelTransformer extends FileTransformer {

	public final static String TYPE_NAME = "xlsx";
	public final static String NAME = String.format(GROUP_TEMPLATE, TYPE_NAME).toUpperCase();

	@Override
	public void transform() throws TransformException {
		// TODO Auto-generated method stub

	}

}
