package org.colorcoding.ibas.importexport.transformers.excel;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.importexport.transformer.FileTransformer;
import org.colorcoding.ibas.importexport.transformer.TransformException;
import org.colorcoding.ibas.importexport.transformers.excel.template.ResolvingException;
import org.colorcoding.ibas.importexport.transformers.excel.template.Template;

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
		if (this.getInputData() == null) {
			return;
		}
		try {
			Template template = new Template();
			// 解析输入数据
			template.resolving(this.getInputData());
			IBusinessObject[] businessObjects = template.resolving();
			this.setOutputData(businessObjects);
		} catch (ResolvingException e) {
			throw new TransformException(e);
		}

	}

}
