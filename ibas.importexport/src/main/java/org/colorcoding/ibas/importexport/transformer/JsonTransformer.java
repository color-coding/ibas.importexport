package org.colorcoding.ibas.importexport.transformer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.json.JsonValue;

import org.colorcoding.ibas.bobas.bo.BOFactory;
import org.colorcoding.ibas.bobas.common.Strings;
import org.colorcoding.ibas.bobas.data.ArrayList;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializationFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;

/**
 * json文件转换为业务对象
 * 
 * @author Niuren.Zhu
 *
 */
@TransformerInfo(name = "FILE_JSON_TO")
public class JsonTransformer extends FileTransformerSerialization {

	public final static String TYPE_NAME = "json";
	protected final static String NODE_BO_CODE_NAME = "ObjectCode";

	protected ISerializer createSerializer() {
		return SerializationFactory.createManager().create(TYPE_NAME);
	}

	public List<Class<?>> getKnownTypes() {
		List<Class<?>> knownTypes = super.getKnownTypes();
		knownTypes.add(ArrayList.class);
		try (InputStream inputStream = new FileInputStream(this.getInputData())) {
			JsonArray jsonArray = null;
			JsonStructure jsonStructure = Json.createReader(inputStream).read();
			if (jsonStructure.getValueType() == JsonValue.ValueType.OBJECT) {
				JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
				arrayBuilder.add(jsonStructure);
				jsonArray = arrayBuilder.build();
			} else if (jsonStructure.getValueType() == JsonValue.ValueType.ARRAY) {
				jsonArray = jsonStructure.asJsonArray();
			}
			if (jsonArray != null) {
				JsonObject jsonObject;
				for (int i = 0; i < jsonArray.size(); i++) {
					jsonObject = jsonArray.getJsonObject(i);
					String boCode = jsonObject.getString(NODE_BO_CODE_NAME);
					if (!Strings.isNullOrEmpty(boCode)) {
						boCode = MyConfiguration.applyVariables(boCode);
						Class<?> boType = BOFactory.classOf(boCode);
						if (boType == null) {
							Logger.log(MessageLevel.WARN, "transformer: [%s] not found [%s]'s class.",
									this.getClass().getSimpleName(), boCode);
						} else if (!knownTypes.contains(boType)) {
							Logger.log(MessageLevel.INFO, "transformer: [%s] found class [%s|%s].",
									this.getClass().getSimpleName(), boCode, boType.getName());
							knownTypes.add(boType);
						}
					}
				}
			}
		} catch (Exception e) {
			Logger.log(MessageLevel.DEBUG, e);
		}
		return knownTypes;
	}

}
