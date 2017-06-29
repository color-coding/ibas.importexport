package org.colorcoding.ibas.importexport.transformers;

import java.util.ArrayList;

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
	public void addBOs(Iterable<Object> items) {
		if (items == null) {
			return;
		}
		if (this.bos == null) {
			this.bos = new ArrayList<Object>();
		}
		for (Object object : items) {
			this.bos.add(object);
		}
	}

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

}
