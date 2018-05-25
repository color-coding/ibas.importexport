package org.colorcoding.ibas.importexport.transformer;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.importexport.transformer.template.ResolvingException;
import org.colorcoding.ibas.importexport.transformer.template.Template;

/**
 * xlsx文件转换业务对象
 * 
 * @author Niuren.Zhu
 *
 */
@TransformerInfo(name = "FILE_XLSX_TO")
public class ExcelTransformer extends FileTransformer {

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
