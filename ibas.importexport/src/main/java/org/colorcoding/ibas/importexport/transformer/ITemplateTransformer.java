package org.colorcoding.ibas.importexport.transformer;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.colorcoding.ibas.bobas.data.KeyText;

/**
 * 输入流使用模板转为输出流
 * 
 * @author Niuren.Zhu
 *
 */
public interface ITemplateTransformer extends ITransformer<InputStream, File> {

	/**
	 * 获取-模板
	 * 
	 * @return
	 */
	String getTemplate();

	/**
	 * 设置-模板
	 * 
	 * @param template
	 */
	void setTemplate(String template);

	/**
	 * 查询匹配的模板
	 * @param boCode
	 * @return
	 */
	List<KeyText> matchingTemplates(String boCode);

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
