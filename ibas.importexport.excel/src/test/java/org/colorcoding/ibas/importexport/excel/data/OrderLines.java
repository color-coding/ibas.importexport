package org.colorcoding.ibas.importexport.excel.data;

import java.beans.PropertyChangeEvent;

import org.colorcoding.ibas.bobas.bo.BusinessObjects;

public class OrderLines extends BusinessObjects<OrderLine, Order> {

	private static final long serialVersionUID = 4298331479320992920L;

	public OrderLines(Order parent) {
		super(parent);
	}

	@Override
	public Class<?> getElementType() {
		return OrderLine.class;
	}

	@Override
	public OrderLine create() {
		OrderLine orderLine = new OrderLine();
		this.add(orderLine);
		return orderLine;
	}

	@Override
	protected void afterAddItem(OrderLine item) {
		super.afterAddItem(item);
		item.setDocEntry(this.getParent().getDocEntry());
		item.setLineId(this.size());
	}

	@Override
	protected void onParentPropertyChanged(PropertyChangeEvent evt) {
		super.onParentPropertyChanged(evt);
		if (evt.getPropertyName().equals(Order.PROPERTY_DOCENTRY.getName())) {
			for (OrderLine item : this) {
				item.setDocEntry(this.getParent().getDocEntry());
			}
		}
	}

}
