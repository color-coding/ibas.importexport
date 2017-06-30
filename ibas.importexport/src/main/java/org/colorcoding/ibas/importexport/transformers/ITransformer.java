package org.colorcoding.ibas.importexport.transformers;

import java.util.List;

/**
 * 转换者
 * 
 * 待转换bo有数据时，进行BO到数据的转换，否则反之。
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
	 * 添加待转换业务对象
	 * 
	 * @param items
	 */
	void addBOs(List<Object> items);

	/**
	 * 添加待转换业务对象
	 * 
	 * @param items
	 */
	void addBOs(Object[] items);

	/**
	 * 获取-业务对象
	 * 
	 * @return
	 */
	Object[] getBOs();

	/**
	 * 获取已知类型
	 * 
	 * @return
	 */
	List<Class<?>> getKnownTypes();

	/**
	 * 添加已知类型
	 * 
	 * @param type
	 */
	void addKnownType(Class<?> type);

	/**
	 * 转换
	 * 
	 * @throws TransformException
	 */
	void transform() throws TransformException;
}
