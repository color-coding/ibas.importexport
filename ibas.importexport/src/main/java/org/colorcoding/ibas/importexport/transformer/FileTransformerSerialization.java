package org.colorcoding.ibas.importexport.transformer;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.core.BOFactory;
import org.colorcoding.ibas.bobas.core.IBOFactory;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.xml.sax.InputSource;

/**
 * 文件通过序列化转为业务对象
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class FileTransformerSerialization extends FileTransformer {

	private IBOFactory boFactory;

	public final IBOFactory getBOFactory() {
		if (this.boFactory == null) {
			this.boFactory = BOFactory.create();
		}
		return boFactory;
	}

	public final void setBOFactory(IBOFactory boFactory) {
		this.boFactory = boFactory;
	}

	private List<Class<?>> knownTypes;

	public List<Class<?>> getKnownTypes() {
		if (this.knownTypes == null) {
			this.knownTypes = new ArrayList<>();
		}
		return knownTypes;
	}

	/**
	 * 添加已知类型
	 * 
	 * @param type
	 */
	public final void addKnownType(Class<?> type) {
		if (this.knownTypes == null) {
			this.knownTypes = new ArrayList<>();
		}
		this.knownTypes.add(type);
	}

	/**
	 * 转换
	 * 
	 * @throws TransformException
	 */
	public void transform() throws TransformException {
		if (this.getInputData() == null) {
			return;
		}
		ISerializer<?> serializer = this.createSerializer();
		if (serializer == null) {
			throw new TransformException(I18N.prop("msg_ie_not_found_serializer", this.getClass().getSimpleName()));
		}
		Logger.log(MessageLevel.INFO, "transformer: [%s] is running.", this.getClass().getSimpleName());
		try {
			Class<?>[] types = this.getKnownTypes().toArray(new Class<?>[] {});
			Logger.log(MessageLevel.INFO, "transformer: [%s] to run deserialize.", this.getClass().getSimpleName());
			List<IBusinessObject> outDatas = new ArrayList<>();
			// 以UTF-8重新读取
			InputStreamReader streamReader = new InputStreamReader(new FileInputStream(this.getInputData()), "utf-8");
			Object object = serializer.deserialize(new InputSource(streamReader), types);
			streamReader.close();
			streamReader = null;
			// 结果为数组或集合，拆散
			if (object instanceof Iterable) {
				Iterable<?> iterable = (Iterable<?>) object;
				for (Object item : iterable) {
					if (item instanceof IBusinessObject) {
						outDatas.add((IBusinessObject) item);
					}
				}
			} else if (object.getClass().isArray()) {
				for (int i = 0; i < Array.getLength(object); i++) {
					Object item = Array.get(object, i);
					if (item instanceof IBusinessObject) {
						outDatas.add((IBusinessObject) item);
					}
				}
			} else if (object instanceof IBusinessObject) {
				outDatas.add((IBusinessObject) object);
			}
			// 重置状态
			for (IBusinessObject item : outDatas) {
				if (item instanceof BusinessObject<?>) {
					BusinessObject<?> bo = (BusinessObject<?>) item;
					bo.resetStatus();
				}
			}
			this.setOutputData(outDatas);
			Logger.log(MessageLevel.INFO, "transformer: [%s] got bo count [%s].", this.getClass().getSimpleName(),
					this.getOutputData().size());
		} catch (Exception e) {
			Logger.log(e);
			throw new TransformException(e);
		}
	}

	protected abstract ISerializer<?> createSerializer();

}
