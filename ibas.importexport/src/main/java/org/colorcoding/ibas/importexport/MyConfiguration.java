package org.colorcoding.ibas.importexport;

import org.colorcoding.ibas.bobas.configuration.ConfigurationFactory;
import org.colorcoding.ibas.bobas.configuration.ConfigurationManager;

/**
 * 我的配置项
 */
public class MyConfiguration extends org.colorcoding.ibas.initialfantasy.MyConfiguration {

	private volatile static ConfigurationManager instance;

	public static ConfigurationManager create() {
		if (instance == null) {
			synchronized (MyConfiguration.class) {
				if (instance == null) {
					instance = ConfigurationFactory.createManager();
					instance.setConfigSign(MODULE_ID);
					instance.update();
				}
			}
		}
		return instance;
	}

	public static <P> P getConfigValue(String key, P defaultValue) {
		return create().getConfigValue(key, defaultValue);
	}

	public static String getConfigValue(String key) {
		return create().getConfigValue(key);
	}

	/**
	 * 模块标识
	 */
	public static final String MODULE_ID = "cbd4ef0f-ee88-4ea7-96c7-13f974026b58";

	/**
	 * 命名空间
	 */
	public static final String NAMESPACE_ROOT = "http://colorcoding.org/ibas/importexport/";

	/**
	 * 数据命名空间
	 */
	public static final String NAMESPACE_DATA = NAMESPACE_ROOT + "data";

	/**
	 * 业务对象命名空间
	 */
	public static final String NAMESPACE_BO = NAMESPACE_ROOT + "bo";

	/**
	 * 服务命名空间
	 */
	public static final String NAMESPACE_SERVICE = NAMESPACE_ROOT + "service";

	/**
	 * 配置项目-转换者工厂，多个时“;”分隔
	 */
	public final static String CONFIG_ITEM_TRANSFORMER_FACTORY = "TransformerFactory";

	/**
	 * 配置项目-扫描的命名空间，多个时“;”分隔
	 */
	public final static String CONFIG_ITEM_SCANING_PACKAGES = "ScaningPackages";

	/**
	 * 标记-数据源
	 */
	public static final String SIGN_DATA_SOURCE = "I";
}
