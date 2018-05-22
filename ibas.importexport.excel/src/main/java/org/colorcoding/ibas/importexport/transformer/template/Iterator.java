package org.colorcoding.ibas.importexport.transformer.template;

public interface Iterator<E> extends java.util.Iterator<E> {
	/**
	 * 游标返回上个记录
	 */
	void back();
}
