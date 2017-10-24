package org.colorcoding.ibas.importexport.transformer;

import org.colorcoding.ibas.bobas.data.List;

/**
 * 转换者
 * 
 * @author Niuren.Zhu
 *
 */
public interface ITransformer<IN, OUT> {

	/**
	 * 设置输入数据
	 * 
	 * @param data
	 */
	void setInputData(IN data);

	/**
	 * 获取转换后数据
	 * 
	 * @return
	 * @throws TransformException
	 */
	List<OUT> getOutputData() throws TransformException;

	/**
	 * 转换
	 * 
	 * @throws TransformException
	 */
	void transform() throws TransformException;
}
