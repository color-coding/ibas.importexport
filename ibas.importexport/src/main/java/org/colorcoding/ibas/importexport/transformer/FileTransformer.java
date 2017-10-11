package org.colorcoding.ibas.importexport.transformer;

import java.io.File;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;

/**
 * 文件转换为业务对象
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class FileTransformer extends Transformer<File, IBusinessObject> implements IFileTransformer {

	/**
	 * 组模板
	 */
	public final static String GROUP_TEMPLATE = "FILE_%s_TO";
}
