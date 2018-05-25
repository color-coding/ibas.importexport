package org.colorcoding.ibas.importexport.transformer;

import java.io.File;
import java.io.InputStream;

import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;

/**
 * 输入流使用模板转为输出流
 * 
 * @author Niuren.Zhu
 *
 */
public interface ITemplateTransformer extends ITransformer<InputStream, File> {

	/**
	 * 设置-模板
	 * 
	 * @param template
	 */
	void setTemplate(IExportTemplate template);

	/**
	 * 获取-模板
	 * 
	 * @return
	 */
	IExportTemplate getTemplate();

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
