package org.colorcoding.ibas.importexport.transformers;

import java.util.ArrayList;
import java.util.List;

import org.colorcoding.ibas.bobas.i18n.I18N;

/**
 * 转换者
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class Transformer implements ITransformer {

	private ArrayList<Object> bos;

	/**
	 * 设置-业务对象
	 * 
	 * @param data
	 */
	public final void addBOs(List<Object> items) {
		if (items == null) {
			return;
		}
		this.addBOs(items.toArray(new Object[] {}));
	}

	/**
	 * 添加待转换业务对象
	 * 
	 * @param items
	 */
	public void addBOs(Object[] items) {
		if (items == null) {
			return;
		}
		if (this.bos == null) {
			this.bos = new ArrayList<Object>();
		} else {
			this.bos.clear();
		}
		for (Object object : items) {
			this.bos.add(object);
		}
	}

	/**
	 * 获取-业务对象
	 * 
	 * @return
	 */
	public Object[] getBOs() {
		if (this.bos == null) {
			this.bos = new ArrayList<Object>();
		}
		return this.bos.toArray(new Object[] {});
	}

	/**
	 * 添加转换结果
	 * 
	 * @param item
	 */
	protected void addBO(Object item) {
		if (item == null) {
			return;
		}
		if (this.bos == null) {
			this.bos = new ArrayList<Object>();
		}
		this.bos.add(item);
	}

	/**
	 * 设置-转换数据
	 */
	public void setData(Object data) {
		throw new RuntimeException(I18N.prop("msg_importexport_invaild_data"));
	}

	private List<Class<?>> knownTypes;

	public List<Class<?>> getKnownTypes() {
		if (this.knownTypes == null) {
			this.knownTypes = new ArrayList<>();
		}
		return knownTypes;
	}

	/**
	 * 添加已知类型
	 * 
	 * @param type
	 */
	public final void addKnownType(Class<?> type) {
		if (this.knownTypes == null) {
			this.knownTypes = new ArrayList<>();
		}
		this.knownTypes.add(type);
	}
}
