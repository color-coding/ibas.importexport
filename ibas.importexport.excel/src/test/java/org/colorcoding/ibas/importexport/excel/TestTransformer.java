package org.colorcoding.ibas.importexport.excel;

import java.io.File;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.transformer.ExcelTransformer;
import org.colorcoding.ibas.importexport.transformer.TransformException;
import org.colorcoding.ibas.importexport.transformer.TransformerExcel;

import junit.framework.TestCase;

public class TestTransformer extends TestCase {

	public void testTransformExcel() throws TransformException {
		// 测试对象到excel文件（新建对象不输出IBOTagStorage属性）
		TransformerExcel transformer = new TransformerExcel();
		IBusinessObject[] data = new IBusinessObject[] { new ExportTemplate(), new ExportTemplate(),
				new ExportTemplate() };
		transformer.setInputData(data);
		transformer.setWorkFolder(MyConfiguration.getWorkFolder());
		transformer.transform();
		System.out.println(String.format("put file: %s", transformer.getOutputData().firstOrDefault()));
	}

	public void testExcelTransform() throws TransformException {
		// 测试excel文件到对象
		ExcelTransformer transformer = new ExcelTransformer();
		File file = new File("");
		transformer.setInputData(file);
		transformer.transform();
		System.out.println(String.format("got bo: %s", transformer.getOutputData().size()));
	}
}
