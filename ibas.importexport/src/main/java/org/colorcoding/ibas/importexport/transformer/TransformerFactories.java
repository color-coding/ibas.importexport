package org.colorcoding.ibas.importexport.transformer;

import java.util.ArrayList;
import java.util.List;

import org.colorcoding.ibas.bobas.data.KeyText;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.importexport.MyConfiguration;

public final class TransformerFactories {

	public static final String MSG_REGISTER_TRANSFORMER_FACTORY = "transformer: register factory [%s].";
	public static final String MSG_NOT_FOUND_TRANSFORMER = "transformer: not found [%s]'s transformer.";

	private volatile static TransformerFactories instance;

	public static TransformerFactories create() {
		if (instance == null) {
			synchronized (TransformerFactories.class) {
				if (instance == null) {
					instance = new TransformerFactories();
					instance.init();
				}
			}
		}
		return instance;
	}

	private TransformerFactories() {
	}

	protected void init() {
		String factories = MyConfiguration.getConfigValue(MyConfiguration.CONFIG_ITEM_TRANSFORMER_FACTORIES);
		if (factories != null && factories.length() > 0) {
			for (String item : factories.split(";")) {
				if (item != null && item.length() > 0) {
					try {
						Class<?> type = Class.forName(item);
						if (type != null && TransformerFactory.class.isAssignableFrom(type)) {
							Object factory = type.newInstance();
							if (factory instanceof TransformerFactory) {
								this.getFactories().add((TransformerFactory) factory);
								Logger.log(MessageLevel.DEBUG, MSG_REGISTER_TRANSFORMER_FACTORY, type.getName());
							}
						}
					} catch (Exception e) {
						Logger.log(e);
					}
				}
			}
		}
		// 添加默认转换者
		this.getFactories().add(new TransformerFactory() {
			@Override
			public KeyText[] getTransformers() {
				return new KeyText[] { new KeyText(JsonTransformer.NAME, JsonTransformer.NAME),
						new KeyText(XmlTransformer.NAME, XmlTransformer.NAME) };
			}

			@Override
			public ITransformer<?, ?> create(String sign) {
				if (JsonTransformer.NAME.equalsIgnoreCase(sign)) {
					return new JsonTransformer();
				} else if (XmlTransformer.NAME.equalsIgnoreCase(sign)) {
					return new XmlTransformer();
				}
				return null;
			}
		});
	}

	private List<TransformerFactory> factories;

	public final List<TransformerFactory> getFactories() {
		if (this.factories == null) {
			this.factories = new ArrayList<>();
		}
		return factories;
	}

	public final ITransformer<?, ?> create(String sign) {
		for (TransformerFactory factory : this.getFactories()) {
			ITransformer<?, ?> transformer = factory.create(sign);
			if (transformer != null) {
				return transformer;
			}
		}
		return null;
	}
}