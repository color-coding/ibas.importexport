package org.colorcoding.ibas.importexport.excel;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.bo.IBusinessObjects;
import org.colorcoding.ibas.bobas.bo.IUserField;
import org.colorcoding.ibas.bobas.bo.UserFieldsFactory;
import org.colorcoding.ibas.bobas.bo.UserFieldsManager;
import org.colorcoding.ibas.bobas.common.DateTimes;
import org.colorcoding.ibas.bobas.common.Decimals;
import org.colorcoding.ibas.bobas.core.fields.IFieldData;
import org.colorcoding.ibas.bobas.core.fields.IManagedFields;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.emDocumentStatus;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.excel.data.BatchItem;
import org.colorcoding.ibas.importexport.excel.data.Order;
import org.colorcoding.ibas.importexport.excel.data.OrderLine;
import org.colorcoding.ibas.importexport.transformer.template.Cell;
import org.colorcoding.ibas.importexport.transformer.template.Object;
import org.colorcoding.ibas.importexport.transformer.template.Property;
import org.colorcoding.ibas.importexport.transformer.template.ResolvingException;
import org.colorcoding.ibas.importexport.transformer.template.Template;
import org.colorcoding.ibas.importexport.transformer.template.WriteFileException;

import junit.framework.TestCase;

public class TestTemplate extends TestCase {

	public void testRresolving() throws ResolvingException, WriteFileException, IOException {
		UserFieldsManager userFieldsManager = UserFieldsFactory.createManager();
		userFieldsManager.registerUserField(Order.class, "U_0001", String.class);
		userFieldsManager.registerUserField(Order.class, "U_0002", BigDecimal.class);
		userFieldsManager.registerUserField(Order.class, "U_0003", DateTime.class);

		Order order = new Order();
		IUserField<String> userField01 = order.getUserFields().get("U_0001");
		IUserField<BigDecimal> userField02 = order.getUserFields().get("U_0002");
		IUserField<DateTime> userField03 = order.getUserFields().get("U_0003");
		order.setDocEntry(916);
		order.setDocumentStatus(emDocumentStatus.RELEASED);
		order.setDocumentDate(DateTimes.today());
		userField01.setValue("i'm niuren.zhu.");
		userField02.setValue(Decimals.valueOf("999.1999"));
		userField03.setValue(DateTimes.VALUE_MAX);
		order.getUser().setCode("manager");
		order.getUser().setName("管理员大叔");
		OrderLine line = order.getOrderLines().create();
		line.setItemCode("A00001");
		line.setQuantity(199);
		BatchItem batch = line.getBatchItems().create();
		batch.setBatchCode("20171231");
		batch.setCount(199);
		batch = line.getBatchItems().create();
		batch.setBatchCode("20180315");
		batch.setCount(66);
		line = order.getOrderLines().create();
		line.setItemCode("A00002");
		line.setQuantity("168.99");

		// order.markOld(true); // 设置为老数据，测试模板输出内容
		// order = new Order();// 测试纯模板输出

		// 测试对象解析
		Template template1 = new Template();
		template1.resolving(order);
		template1.resolving(order.clone());
		this.print(template1);
		File file = new File(String.format("%s%soutput_%s.xlsx", MyConfiguration.getWorkFolder(), File.separator,
				DateTimes.now().getTime()));
		template1.write(file);
		System.out.println(file.getPath());
		// 测试文件解析
		Template template2 = new Template();
		template2.resolving(file);
		order.setDocEntry(861);
		template2.resolving(order);
		this.print(template2);
		// 测试对象还原
		IBusinessObject[] businessObjects = template2.resolving();
		System.out.println(String.format("bo count %s", businessObjects.length));
		for (IBusinessObject boItem : businessObjects) {
			if (boItem instanceof IManagedFields) {
				this.print((IManagedFields) boItem);
			}
		}
	}

	private void print(IManagedFields boFields) {
		System.out.println(String.format("%s", boFields.toString()));
		for (IFieldData fieldData : boFields.getFields()) {
			if (fieldData.getValue() instanceof IManagedFields) {
				this.print((IManagedFields) fieldData.getValue());
			} else if (fieldData.getValue() instanceof IBusinessObjects<?, ?>) {
				for (IBusinessObject item : (IBusinessObjects<?, ?>) fieldData.getValue()) {
					if (item instanceof IManagedFields) {
						this.print((IManagedFields) item);
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