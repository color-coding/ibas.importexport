package org.colorcoding.ibas.importexport.excel.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.bo.IBOUserFields;
import org.colorcoding.ibas.bobas.core.IPropertyInfo;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.emDocumentStatus;

public class Order extends BusinessObject<Order> implements IBOUserFields {

	private static final long serialVersionUID = -8288483206699400773L;

	/**
	 * 当前类型
	 */
	private static final Class<?> MY_CLASS = Order.class;

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

	private static final String PROPERTY_DOCUMENTSTATUS_NAME = "DocumentStatus";

	public static final IPropertyInfo<emDocumentStatus> PROPERTY_DOCUMENTSTATUS = registerProperty(
			PROPERTY_DOCUMENTSTATUS_NAME, emDocumentStatus.class, MY_CLASS);

	@XmlElement(name = PROPERTY_DOCUMENTSTATUS_NAME)
	public final emDocumentStatus getDocumentStatus() {
		return this.getProperty(PROPERTY_DOCUMENTSTATUS);
	}

	public final void setDocumentStatus(emDocumentStatus value) {
		this.setProperty(PROPERTY_DOCUMENTSTATUS, value);
	}

	private static final String PROPERTY_DOCUMENTDATE_NAME = "DocumentDate";

	public static final IPropertyInfo<DateTime> PROPERTY_DOCUMENTDATE = registerProperty(PROPERTY_DOCUMENTDATE_NAME,
			DateTime.class, MY_CLASS);

	@XmlElement(name = PROPERTY_DOCUMENTDATE_NAME)
	public final DateTime getDocumentDate() {
		return this.getProperty(PROPERTY_DOCUMENTDATE);
	}

	public final void setDocumentDate(DateTime value) {
		this.setProperty(PROPERTY_DOCUMENTDATE, value);
	}

	private static final String PROPERTY_ORDERLINES_NAME = "OrderLines";

	public static final IPropertyInfo<OrderLines> PROPERTY_ORDERLINES = registerProperty(PROPERTY_ORDERLINES_NAME,
			OrderLines.class, MY_CLASS);

	@XmlElementWrapper(name = PROPERTY_ORDERLINES_NAME)
	@XmlElement(name = OrderLine.BUSINESS_OBJECT_NAME, type = OrderLine.class)
	public final OrderLines getOrderLines() {
		return this.getProperty(PROPERTY_ORDERLINES);
	}

	public final void setOrderLines(OrderLines value) {
		this.setProperty(PROPERTY_ORDERLINES, value);
	}

	private static final String PROPERTY_USER_NAME = "User";

	public static final IPropertyInfo<User> PROPERTY_USER = registerProperty(PROPERTY_USER_NAME, User.class, MY_CLASS);

	@XmlElement(name = PROPERTY_USER_NAME)
	public final User getUser() {
		return this.getProperty(PROPERTY_USER);
	}

	public final void setUser(User value) {
		this.setProperty(PROPERTY_USER, value);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.setUser(new User());
		this.setOrderLines(new OrderLines());

	}
}
