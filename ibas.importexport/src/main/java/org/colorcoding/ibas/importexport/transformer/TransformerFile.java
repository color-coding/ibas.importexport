package org.colorcoding.ibas.importexport.transformer;

import java.io.File;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;

/**
 * 业务对象转换为文件
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class TransformerFile extends Transformer<IBusinessObject[], File> implements ITransformerFile {

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
