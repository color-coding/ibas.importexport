package org.colorcoding.ibas.importexport.transformer;

import java.io.InputStream;
import java.io.OutputStream;

import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;

/**
 * 输入流使用模板转为输出流
 * 
 * @author Niuren.Zhu
 *
 */
public interface ITemplateTransformer extends ITransformer<InputStream, OutputStream> {

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
	String getTemplate();
}
