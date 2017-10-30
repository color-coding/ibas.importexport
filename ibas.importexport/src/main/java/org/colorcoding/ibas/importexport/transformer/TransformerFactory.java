package org.colorcoding.ibas.importexport.transformer;

import org.colorcoding.ibas.bobas.data.KeyText;

/**
 * 转换者工厂
 * 
 * @author Niuren.Zhu
 *
 */
public interface TransformerFactory {

	/**
	 * 获取-支持的转换者
	 * 
	 * @return
	 */

	KeyText[] getTransformers();

	/**
	 * 创建转换者
	 * 
	 * @param sign
	 *            转换者标识
	 * @return
	 */
	ITransformer<?, ?> create(String sign);
}
