package org.colorcoding.ibas.importexport.excel;

import java.io.File;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.dataexporttemplate.DataExportTemplate;
import org.colorcoding.ibas.importexport.transformer.TransformException;
import org.colorcoding.ibas.importexport.transformers.excel.ExcelTransformer;
import org.colorcoding.ibas.importexport.transformers.excel.TransformerExcel;

import junit.framework.TestCase;

public class testTransformer extends TestCase {

	public void testTransformExcel() throws TransformException {
		// 测试对象到excel文件
		TransformerExcel transformer = new TransformerExcel();
		IBusinessObject[] data = new IBusinessObject[] { new DataExportTemplate(), new DataExportTemplate(),
				new DataExportTemplate() };
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
