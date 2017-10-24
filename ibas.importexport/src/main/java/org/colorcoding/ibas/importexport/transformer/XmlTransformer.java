package org.colorcoding.ibas.importexport.transformer;

import java.io.FileInputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.colorcoding.ibas.bobas.core.BOFactory;
import org.colorcoding.ibas.bobas.messages.Logger;
import org.colorcoding.ibas.bobas.messages.MessageLevel;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializerFactory;
import org.colorcoding.ibas.bobas.data.ArrayList;
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
public class XmlTransformer extends FileSerializationTransformer {

	public final static String TYPE_NAME = "xml";
	public final static String NODE_BO_CODE_NAME = "ObjectCode";
	public final static String NAME = String.format(GROUP_TEMPLATE, TYPE_NAME).toUpperCase();

	@Override
	protected ISerializer<?> createSerializer() {
		return SerializerFactory.create().createManager().create(TYPE_NAME);
	}

	@Override
	public List<Class<?>> getKnownTypes() {
		List<Class<?>> knownTypes = super.getKnownTypes();
		knownTypes.add(ArrayList.class);
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new FileInputStream(this.getInputData()));
			Element root = document.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName(NODE_BO_CODE_NAME);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				String boCode = node.getTextContent();
				if (boCode == null || boCode.isEmpty()) {
					continue;
				}
				Class<?> boType = BOFactory.create().getBOClass(boCode);
				if (boType == null) {
					Logger.log(MessageLevel.WARN, "transformer: [%s] not found [%s]'s class.",
							this.getClass().getSimpleName(), boCode);
				} else if (!knownTypes.contains(boType)) {
					Logger.log(MessageLevel.INFO, "transformer: [%s] found class [%s|%s].",
							this.getClass().getSimpleName(), boCode, boType.getName());
					knownTypes.add(boType);
				}
			}
		} catch (Exception e) {
			Logger.log(e);
		}
		return knownTypes;
	}

}
