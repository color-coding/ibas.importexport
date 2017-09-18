package org.colorcoding.ibas.importexport.transformers;

import java.util.List;

import org.colorcoding.ibas.bobas.core.BOFactory;
import org.colorcoding.ibas.bobas.messages.MessageLevel;
import org.colorcoding.ibas.bobas.messages.Logger;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializerFactory;
import org.colorcoding.ibas.bobas.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json文件转换者
 * 
 * @author Niuren.Zhu
 *
 */
public class JsonTransformer extends FileTransformer {

	public final static String TYPE_NAME = "json";
	public final static String NODE_BO_CODE_NAME = "ObjectCode";

	@Override
	protected ISerializer<?> createSerializer() {
		return SerializerFactory.create().createManager().create(TYPE_NAME);
	}

	@Override
	protected String getExtension() {
		return TYPE_NAME;
	}

	@Override
	public List<Class<?>> getKnownTypes() {
		List<Class<?>> knownTypes = super.getKnownTypes();
		knownTypes.add(ArrayList.class);
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(this.getDataStream());
			List<JsonNode> nodes = root.findValues(NODE_BO_CODE_NAME);
			for (JsonNode node : nodes) {
				String boCode = node.textValue();
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
