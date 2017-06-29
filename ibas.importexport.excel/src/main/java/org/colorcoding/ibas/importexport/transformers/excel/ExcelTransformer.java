package org.colorcoding.ibas.importexport.transformers.excel;

import java.io.InputStream;

import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.importexport.transformers.FileTransformer;

/**
 * json文件转换者
 * 
 * @author Niuren.Zhu
 *
 */
public class ExcelTransformer extends FileTransformer {

	public final static String TYPE_NAME = "xlsx";

	@Override
	public void setData(Object data) {
		// TODO Auto-generated method stub

	}

	@Override
	protected InputStream getDataStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?> getKnownTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ISerializer<?> createSerializer() {
		// TODO Auto-generated method stub
		return null;
	}
}
