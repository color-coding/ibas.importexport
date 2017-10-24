package org.colorcoding.ibas.importexport.transformer;

import java.io.File;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.data.ArrayList;
import org.colorcoding.ibas.bobas.data.List;
import org.colorcoding.ibas.bobas.i18n.I18N;

/**
 * 业务对象转换为文件
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class TransformerFile implements ITransformerFile {

	/**
	 * 组模板
	 */
	public final static String GROUP_TEMPLATE = "TO_FILE_%s";

	private IBusinessObject[] inputData;

	protected IBusinessObject[] getInputData() {
		return this.inputData;
	}

	@Override
	public void setInputData(IBusinessObject[] data) {
		this.inputData = data;
	}

	private List<File> outputData;

	protected void setOutputData(java.util.List<File> data) {
		if (data == null) {
			return;
		}
		List<File> tmps = new ArrayList<>();
		tmps.addAll(data);
		this.setOutputData(tmps);
	}

	protected void setOutputData(List<File> data) {
		if (data == null) {
			return;
		}
		this.outputData = data;
	}

	@Override
	public List<File> getOutputData() throws TransformException {
		if (this.outputData == null) {
			throw new TransformException(I18N.prop("msg_importexport_invaild_data"));
		}
		return this.outputData;
	}

	private String workFolder;

	@Override
	public void setWorkFolder(String folder) {
		this.workFolder = folder;
	}

	@Override
	public String getWorkFolder() {
		return this.workFolder;
	}

}
