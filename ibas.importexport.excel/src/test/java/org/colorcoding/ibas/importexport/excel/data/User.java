package org.colorcoding.ibas.importexport.excel.data;

import javax.xml.bind.annotation.XmlElement;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.core.IPropertyInfo;

public class User extends BusinessObject<User> {

	private static final long serialVersionUID = -7909224736602993602L;

	private static final Class<?> MY_CLASS = User.class;

	public static final String BUSINESS_OBJECT_NAME = "User";

	private static final String PROPERTY_CODE_NAME = "Code";

	public static final IPropertyInfo<String> PROPERTY_CODE = registerProperty(PROPERTY_CODE_NAME, String.class,
			MY_CLASS);

	@XmlElement(name = PROPERTY_CODE_NAME)
	public final String getCode() {
		return this.getProperty(PROPERTY_CODE);
	}

	public final void setCode(String value) {
		this.setProperty(PROPERTY_CODE, value);
	}

	private static final String PROPERTY_NAME_NAME = "Name";

	public static final IPropertyInfo<String> PROPERTY_NAME = registerProperty(PROPERTY_NAME_NAME, String.class,
			MY_CLASS);

	@XmlElement(name = PROPERTY_NAME_NAME)
	public final String getName() {
		return this.getProperty(PROPERTY_NAME);
	}

	public final void setName(String value) {
		this.setProperty(PROPERTY_NAME, value);
	}

}
