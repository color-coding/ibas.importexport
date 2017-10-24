package org.colorcoding.ibas.importexport.excel;

import java.io.File;
import java.io.IOException;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.bo.IBusinessObjects;
import org.colorcoding.ibas.bobas.bo.IUserField;
import org.colorcoding.ibas.bobas.core.fields.IFieldData;
import org.colorcoding.ibas.bobas.core.fields.IManageFields;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.Decimal;
import org.colorcoding.ibas.bobas.data.emDocumentStatus;
import org.colorcoding.ibas.bobas.mapping.DbFieldType;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.excel.data.BatchItem;
import org.colorcoding.ibas.importexport.excel.data.Order;
import org.colorcoding.ibas.importexport.excel.data.OrderLine;
import org.colorcoding.ibas.importexport.transformers.excel.template.Cell;
import org.colorcoding.ibas.importexport.transformers.excel.template.Object;
import org.colorcoding.ibas.importexport.transformers.excel.template.Property;
import org.colorcoding.ibas.importexport.transformers.excel.template.ResolvingException;
import org.colorcoding.ibas.importexport.transformers.excel.template.Template;
import org.colorcoding.ibas.importexport.transformers.excel.template.WriteFileException;

import junit.framework.TestCase;

public class testTemplate extends TestCase {

	public void testRresolving() throws ResolvingException, WriteFileException, IOException {
		Order order = new Order();
		IUserField userField01 = order.getUserFields().addUserField("U_0001", DbFieldType.ALPHANUMERIC);
		IUserField userField02 = order.getUserFields().addUserField("U_0002", DbFieldType.DECIMAL);
		IUserField userField03 = order.getUserFields().addUserField("U_0003", DbFieldType.DATE);
		order.setDocEntry(916);
		order.setDocumentStatus(emDocumentStatus.RELEASED);
		order.setDocumentDate(DateTime.getToday());
		userField01.setValue("i'm niuren.zhu.");
		userField02.setValue(new Decimal("999.1999"));
		userField03.setValue(DateTime.getMaxValue());
		order.getUser().setCode("manager");
		order.getUser().setName("管理员大叔");
		OrderLine line = order.getOrderLines().create();
		line.setItemCode("A00001");
		line.setQuantity(199);
		BatchItem batch = line.getBatchItems().create();
		batch.setBatchCode("20171231");
		batch.setCount(199);
		line = order.getOrderLines().create();
		line.setItemCode("A00002");
		line.setQuantity("168.99");

		// order.markOld(true); // 设置为老数据，测试模板输出内容
		// order = new Order();// 测试纯模板输出

		// 测试对象解析
		Template template1 = new Template();
		template1.resolving(order);
		this.print(template1);
		File file = new File(String.format("%s%soutput_%s.xlsx", MyConfiguration.getWorkFolder(), File.separator,
				DateTime.getNow().getTime()));
		template1.write(file);
		System.out.println(file.getPath());
		// 测试文件解析
		Template template2 = new Template();
		template2.resolving(file);
		this.print(template2);
		// 测试对象还原
		IBusinessObject[] businessObjects = template2.resolving();
		System.out.println(String.format("bo count %s", businessObjects.length));
		for (IBusinessObject boItem : businessObjects) {
			if (boItem instanceof IManageFields) {
				this.print((IManageFields) boItem);
			}
		}
	}

	private void print(IManageFields boFields) {
		System.out.println(String.format("%s", boFields.toString()));
		for (IFieldData fieldData : boFields.getFields()) {
			if (fieldData.getValue() instanceof IManageFields) {
				this.print((IManageFields) fieldData.getValue());
			} else if (fieldData.getValue() instanceof IBusinessObjects<?, ?>) {
				for (IBusinessObject item : (IBusinessObjects<?, ?>) fieldData.getValue()) {
					if (item instanceof IManageFields) {
						this.print((IManageFields) item);
					}
				}
			} else {
				System.out.println(String.format("%s", fieldData.toString()));
			}
		}
	}

	private void print(Template template) {
		System.out.println(String.format("%s", template.toString()));
		System.out.println(String.format("%s", template.getHead().toString()));
		for (Object object : template.getObjects()) {
			System.out.println(String.format("  %s %s", object.toString(),
					object.getBindingClass() != null ? object.getBindingClass().getSimpleName() : "---"));
			for (Property property : object.getProperties()) {
				System.out.println(String.format("    %s %s", property.toString(),
						property.getBindingClass() != null ? property.getBindingClass().getSimpleName() : "---"));
			}
		}
		System.out.println(String.format("%s: ", template.getDatas().toString()));
		for (int i = 0; i < template.getDatas().getRows().size(); i++) {
			Cell[] row = template.getDatas().getRows().get(i);
			for (Cell cell : row) {
				System.out.print(cell == null || cell.getValue() == null ? "" : cell.getValue());
				System.out.print(",");
			}
			System.out.println();
		}
	}
}