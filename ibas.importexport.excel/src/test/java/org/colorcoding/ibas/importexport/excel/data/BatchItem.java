package org.colorcoding.ibas.importexport.excel.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.core.IPropertyInfo;

@XmlType()
public class BatchItem extends BusinessObject<OrderLine> {

	private static final long serialVersionUID = 8379964688893987198L;

	public static final String BUSINESS_OBJECT_NAME = "BatchItem";

	private static final Class<?> MY_CLASS = BatchItem.class;

	private static final String PROPERTY_BATCHCODE_NAME = "BatchCode";

	public static final IPropertyInfo<String> PROPERTY_BATCHCODE = registerProperty(PROPERTY_BATCHCODE_NAME,
			String.class, MY_CLASS);

	@XmlElement(name = PROPERTY_BATCHCODE_NAME)
	public final String getBatchCode() {
		return this.getProperty(PROPERTY_BATCHCODE);
	}

	public final void setBatchCode(String value) {
		this.setProperty(PROPERTY_BATCHCODE, value);
	}

	private static final String PROPERTY_COUNT_NAME = "Count";

	public static final IPropertyInfo<Integer> PROPERTY_COUNT = registerProperty(PROPERTY_COUNT_NAME, Integer.class,
			MY_CLASS);

	@XmlElement(name = PROPERTY_COUNT_NAME)
	public final Integer getCount() {
		return this.getProperty(PROPERTY_COUNT);
	}

	public final void setCount(Integer value) {
		this.setProperty(PROPERTY_COUNT, value);
	}

}
