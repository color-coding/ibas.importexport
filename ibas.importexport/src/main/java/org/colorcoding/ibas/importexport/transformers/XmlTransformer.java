package org.colorcoding.ibas.importexport.transformers;

import java.io.InputStream;

import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializerFactory;

/**
 * xml文件转换者
 * 
 * @author Niuren.Zhu
 *
 */
public class XmlTransformer extends FileTransformer {

	public final static String TYPE_NAME = "xml";

	@Override
	public void setData(Object data) {
		// TODO Auto-generated method stub

	}

	@Override
	public InputStream getDataStream() {
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
		return SerializerFactory.create().createManager().create(TYPE_NAME);
	}

	@Override
	protected String getExtension() {
		return TYPE_NAME;
	}

}
