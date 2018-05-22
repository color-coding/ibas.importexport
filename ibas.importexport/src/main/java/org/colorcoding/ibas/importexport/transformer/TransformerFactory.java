package org.colorcoding.ibas.importexport.transformer;

import java.util.HashMap;
import java.util.Map;

import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.importexport.MyConfiguration;

public final class TransformerFactory {

	public static final String MSG_REGISTER_TRANSFORMER_FACTORY = "transformer: register factory [%s].";
	public static final String MSG_NOT_FOUND_TRANSFORMER = "transformer: not found [%s]'s transformer.";

	private volatile static TransformerFactory instance;

	public static TransformerFactory create() {
		if (instance == null) {
			synchronized (TransformerFactory.class) {
				if (instance == null) {
					instance = new TransformerFactory();
					instance.init();
				}
			}
		}
		return instance;
	}

	private TransformerFactory() {
	}

	protected void init() {
		String factories = MyConfiguration.getConfigValue(MyConfiguration.CONFIG_ITEM_TRANSFORMER_FACTORY);
		if (factories == null || factories.isEmpty()) {
			return;
		}
		for (String item : factories.split(";")) {
			if (item == null || item.isEmpty()) {
				continue;
			}
			try {
				if (item.indexOf(".") < 0) {
					item = String.format("%s.%s", TransformerFactory.class.getName().substring(0,
							TransformerFactory.class.getName().lastIndexOf(".")), item);
				}
				Class<?> type = Class.forName(item);
				if (!ITransformer.class.isAssignableFrom(type)) {
					continue;
				}
				TransformerInfo info = type.getAnnotation(TransformerInfo.class);
				if (info == null) {
					continue;
				}
				if (info.value() == null || info.value().isEmpty()) {
					continue;
				}
				this.getTransformers().put(info.value(), type);
			} catch (Exception e) {
				Logger.log(e);
			}
		}
	}

	private Map<String, Class<?>> transformers;

	public final Map<String, Class<?>> getTransformers() {
		if (this.transformers == null) {
			this.transformers = new HashMap<>();
		}
		return transformers;
	}

	public final ITransformer<?, ?> create(String sign) throws TransformException {
		if (sign == null) {
			throw new TransformException(I18N.prop("msg_ie_invaild_data"));
		}
		Class<?> clazz = this.getTransformers().get(sign);
		if (clazz == null) {
			throw new TransformException(I18N.prop("msg_ie_not_found_transformer", sign));
		}
		try {
			return (ITransformer<?, ?>) clazz.newInstance();
		} catch (Exception e) {
			throw new TransformException(e);
		}
	}
}