package org.colorcoding.ibas.importexport.transformers;

/**
 * 转换者
 * 
 * @author Niuren.Zhu
 *
 */
public interface ITransformer {

	/**
	 * 设置-数据
	 * 
	 * @param data
	 */
	void setData(Object data);

	/**
	 * 获取-数据
	 * 
	 * @return
	 */
	Object getData();

	/**
	 * 设置-业务对象
	 * 
	 * @param items
	 */
	void addBOs(Iterable<Object> items);

	/**
	 * 获取-业务对象
	 * 
	 * @return
	 */
	Object[] getBOs();

	/**
	 * 转换
	 * 
	 * @throws TransformException
	 */
	void transform() throws TransformException;
}
