package org.colorcoding.ibas.importexport.transformer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.colorcoding.ibas.bobas.bo.BOFactory;
import org.colorcoding.ibas.bobas.data.ArrayList;
import org.colorcoding.ibas.bobas.logging.Logger;
import org.colorcoding.ibas.bobas.logging.LoggingLevel;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializationFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * xml文件转换为业务对象
 * 
 * @author Niuren.Zhu
 *
 */
@TransformerInfo(name = "FILE_XML_TO")
public class XmlTransformer extends FileTransformerSerialization {

	public final static String TYPE_NAME = "xml";
	protected final static String NODE_BO_CODE_NAME = "ObjectCode";

	@Override
	protected ISerializer createSerializer() {
		return SerializationFactory.createManager().create(TYPE_NAME);
	}

	@Override
	public List<Class<?>> getKnownTypes() {
		List<Class<?>> knownTypes = super.getKnownTypes();
		knownTypes.add(ArrayList.class);
		try (InputStream inputStream = new FileInputStream(this.getInputData())) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			Element root = document.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName(NODE_BO_CODE_NAME);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				String boCode = node.getTextContent();
				if (boCode == null || boCode.isEmpty()) {
					continue;
				}
				boCode = MyConfiguration.applyVariables(boCode);
				Class<?> boType = BOFactory.classOf(boCode);
				if (boType == null) {
					Logger.log(LoggingLevel.WARN, "transformer: [%s] not found [%s]'s class.",
							this.getClass().getSimpleName(), boCode);
				} else if (!knownTypes.contains(boType)) {
					Logger.log(LoggingLevel.INFO, "transformer: [%s] found class [%s|%s].",
							this.getClass().getSimpleName(), boCode, boType.getName());
					knownTypes.add(boType);
				}
			}
		} catch (Exception e) {
			Logger.log(LoggingLevel.DEBUG, e);
		}
		return knownTypes;
	}

}
