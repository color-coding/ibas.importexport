package org.colorcoding.ibas.importexport.excel.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.core.IPropertyInfo;
import org.colorcoding.ibas.bobas.data.Decimal;

public class OrderLine extends BusinessObject<OrderLine> {

	private static final long serialVersionUID = 8379964688893987198L;

	public static final String BUSINESS_OBJECT_NAME = "OrderLine";

	private static final Class<?> MY_CLASS = OrderLine.class;

	private static final String PROPERTY_DOCENTRY_NAME = "DocEntry";

	public static final IPropertyInfo<Integer> PROPERTY_DOCENTRY = registerProperty(PROPERTY_DOCENTRY_NAME,
			Integer.class, MY_CLASS);

	@XmlElement(name = PROPERTY_DOCENTRY_NAME)
	public final Integer getDocEntry() {
		return this.getProperty(PROPERTY_DOCENTRY);
	}

	public final void setDocEntry(Integer value) {
		this.setProperty(PROPERTY_DOCENTRY, value);
	}

	private static final String PROPERTY_LINEID_NAME = "LineId";

	public static final IPropertyInfo<Integer> PROPERTY_LINEID = registerProperty(PROPERTY_LINEID_NAME, Integer.class,
			MY_CLASS);

	@XmlElement(name = PROPERTY_LINEID_NAME)
	public final Integer getLineId() {
		return this.getProperty(PROPERTY_LINEID);
	}

	public final void setLineId(Integer value) {
		this.setProperty(PROPERTY_LINEID, value);
	}

	private static final String PROPERTY_ITEMCODE_NAME = "ItemCode";

	public static final IPropertyInfo<String> PROPERTY_ITEMCODE = registerProperty(PROPERTY_ITEMCODE_NAME, String.class,
			MY_CLASS);

	@XmlElement(name = PROPERTY_ITEMCODE_NAME)
	public final String getItemCode() {
		return this.getProperty(PROPERTY_ITEMCODE);
	}

	public final void setItemCode(String value) {
		this.setProperty(PROPERTY_ITEMCODE, value);
	}

	private static final String PROPERTY_QUANTITY_NAME = "Quantity";

	public static final IPropertyInfo<Decimal> PROPERTY_QUANTITY = registerProperty(PROPERTY_QUANTITY_NAME,
			Decimal.class, MY_CLASS);

	@XmlElement(name = PROPERTY_QUANTITY_NAME)
	public final Decimal getQuantity() {
		return this.getProperty(PROPERTY_QUANTITY);
	}

	public final void setQuantity(Decimal value) {
		this.setProperty(PROPERTY_QUANTITY, value);
	}

	public final void setQuantity(String value) {
		this.setQuantity(new Decimal(value));
	}

	public final void setQuantity(int value) {
		this.setQuantity(new Decimal(value));
	}

	public final void setQuantity(double value) {
		this.setQuantity(new Decimal(value));
	}

	private static final String PROPERTY_BATCHITEMS_NAME = "BatchItems";

	public static final IPropertyInfo<BatchItems> PROPERTY_BATCHITEMS = registerProperty(PROPERTY_BATCHITEMS_NAME,
			BatchItems.class, MY_CLASS);

	@XmlElementWrapper(name = PROPERTY_BATCHITEMS_NAME)
	@XmlElement(name = BatchItem.BUSINESS_OBJECT_NAME, type = BatchItem.class)
	public final BatchItems getBatchItems() {
		return this.getProperty(PROPERTY_BATCHITEMS);
	}

	public final void setBatchItems(BatchItems value) {
		this.setProperty(PROPERTY_BATCHITEMS, value);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.setBatchItems(new BatchItems(this));

	}
}
