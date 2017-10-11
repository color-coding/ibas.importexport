package org.colorcoding.ibas.importexport.transformer;

import java.io.File;
import java.util.List;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.util.ArrayList;
import org.colorcoding.ibas.bobas.util.Collection;

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

	private Collection<File> outputData;

	protected void setOutputData(List<File> data) {
		if (data == null) {
			return;
		}
		Collection<File> tmps = new ArrayList<>();
		tmps.addAll(data);
		this.setOutputData(tmps);
	}

	protected void setOutputData(Collection<File> data) {
		if (data == null) {
			return;
		}
		this.outputData = data;
	}

	@Override
	public Collection<File> getOutputData() throws TransformException {
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
