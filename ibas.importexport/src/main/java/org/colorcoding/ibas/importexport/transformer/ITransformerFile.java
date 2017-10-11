package org.colorcoding.ibas.importexport.transformer;

import java.io.File;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;

/**
 * 业务对象转为文件
 * 
 * @author Niuren.Zhu
 *
 */
public interface ITransformerFile extends ITransformer<IBusinessObject[], File> {

	/**
	 * 设置-工作目录
	 * 
	 * @param folder
	 */
	void setWorkFolder(String folder);

	/**
	 * 获取-工作目录
	 * 
	 * @return
	 */
	String getWorkFolder();
}
