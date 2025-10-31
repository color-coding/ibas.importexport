package org.colorcoding.ibas.importexport.transformer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 转换者说明
 * 
 * @author Niuren.Zhu
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TransformerInfo {
	/**
	 * 名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 格式
	 * @return
	 */
	String contentType() default "";

	/**
	 * 是否使用模板
	 * 
	 * @return
	 */
	boolean template() default false;

	/**
	 * 可用于打印
	 * 
	 * @return
	 */
	boolean printable() default false;
}
