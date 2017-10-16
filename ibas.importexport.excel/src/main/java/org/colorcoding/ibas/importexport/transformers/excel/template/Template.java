package org.colorcoding.ibas.importexport.transformers.excel.template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.bo.IBusinessObjects;
import org.colorcoding.ibas.bobas.core.IPropertyInfo;
import org.colorcoding.ibas.bobas.core.PropertyInfoList;
import org.colorcoding.ibas.bobas.core.PropertyInfoManager;

/**
 * 模板（sheet）
 * 
 * @author Niuren.Zhu
 *
 */
public class Template extends Area {

	private Head head;

	/**
	 * 获取-模板头
	 * 
	 * @return
	 */
	public final Head getHead() {
		return head;
	}

	/**
	 * 设置-模板头
	 * 
	 * @param head
	 */
	public final void setHead(Head head) {
		this.head = head;
	}

	private List<Object> objects;

	/**
	 * 获取-模板拥有对象
	 * 
	 * @return
	 */
	public final Object[] getObjects() {
		if (this.objects == null) {
			this.objects = new ArrayList<>();
		}
		return this.objects.toArray(new Object[] {});
	}

	/**
	 * 添加-模板拥有对象
	 * 
	 * @param object
	 */
	protected final void addObject(Object object) {
		if (this.objects == null) {
			this.objects = new ArrayList<>();
		}
		this.objects.add(object);
	}

	/**
	 * 解析对象，形成模板
	 * 
	 * @param bo
	 *            待解析对象
	 * @throws NotRecognizedException
	 *             无法识别异常
	 */
	public final void resolving(IBusinessObject bo) throws NotRecognizedException {
		try {
			this.resolving(bo.getClass());
		} catch (Exception e) {
			throw new NotRecognizedException(e);
		}
	}

	/**
	 * 解析对象，形成模板
	 * 
	 * @param type
	 *            待解析的类型
	 * @throws NotRecognizedException
	 */
	public final void resolving(Class<?> type) throws NotRecognizedException {
		this.setName(type.getName());
		for (Object object : this.resolvingObject(type)) {
			this.addObject(object);
		}
	}

	/**
	 * 解析对象区域
	 * 
	 * @param bo
	 * @return
	 * @throws NotRecognizedException
	 */
	protected List<Object> resolvingObject(Class<?> type) throws NotRecognizedException {
		List<Object> objects = new ArrayList<>();
		// 根对象
		Object object = new Object();
		object.resolving(type);
		objects.add(object);
		// 分析属性的对象
		PropertyInfoList pInfoList = PropertyInfoManager.getPropertyInfoList(type);
		for (IPropertyInfo<?> pInfo : pInfoList) {
			if (IBusinessObjects.class.isAssignableFrom(pInfo.getValueType())) {
				objects.addAll(this.resolvingObject(pInfo.getValueType()));
			}
		}
		return objects;
	}

	/**
	 * 解析文件，形成模板
	 * 
	 * @param file
	 *            待分析文件
	 * @throws NotRecognizedException
	 *             无法识别异常
	 */
	public final void resolving(File file) throws NotRecognizedException {

	}

}
