package org.colorcoding.ibas.importexport.excel.data;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.ibas.bobas.bo.BusinessObjects;

@XmlType()
@XmlSeeAlso({ BatchItem.class })
public class BatchItems extends BusinessObjects<BatchItem, OrderLine> {

	public BatchItems(OrderLine parent) {
		super(parent);
	}

	private static final long serialVersionUID = 4298331479320992920L;

	@Override
	public Class<?> getElementType() {
		return BatchItem.class;
	}

	@Override
	public BatchItem create() {
		BatchItem item = new BatchItem();
		this.add(item);
		return item;
	}

}
