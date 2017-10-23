package org.colorcoding.ibas.importexport.excel.data;

import org.colorcoding.ibas.bobas.bo.BusinessObjects;

public class OrderLines extends BusinessObjects<OrderLine, Order> {

	private static final long serialVersionUID = 4298331479320992920L;

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

}
