package org.colorcoding.ibas.importexport.transformer;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.bo.IBODocument;
import org.colorcoding.ibas.bobas.bo.IBODocumentLine;
import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.data.emDocumentStatus;
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
			template.setIndividualStatus(this.isIndividualStatus());
			// 解析输入数据
			template.resolving(this.getInputData());
			IBusinessObject[] businessObjects = template.resolving();
			// 重置状态
			IBODocument document;
			IBODocumentLine documentLine;
			emDocumentStatus status;
			for (IBusinessObject item : businessObjects) {
				document = null;
				documentLine = null;
				status = null;
				// 记录单据状态
				if (item instanceof IBODocument) {
					document = (IBODocument) item;
					status = document.getDocumentStatus();
				} else if (item instanceof IBODocumentLine) {
					documentLine = (IBODocumentLine) item;
					status = documentLine.getLineStatus();
				}
				// 对象重置
				if (item instanceof BusinessObject<?>) {
					((BusinessObject<?>) item).reset();
				}
				// 恢复单据状态
				if (document != null && document.getDocumentStatus() != status) {
					document.setDocumentStatus(status);
				} else if (documentLine != null && documentLine.getLineStatus() != status) {
					documentLine.setLineStatus(status);
				}
			}
			this.setOutputData(businessObjects);
		} catch (ResolvingException e) {
			throw new TransformException(e);
		}

	}

}
