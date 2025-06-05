package org.colorcoding.ibas.importexport.transformer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.logging.Logger;
import org.colorcoding.ibas.bobas.logging.LoggingLevel;
import org.colorcoding.ibas.bobas.serialization.ISerializer;

/**
 * 文件通过序列化转为业务对象
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class FileTransformerSerialization extends FileTransformer {

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
		ISerializer serializer = this.createSerializer();
		if (serializer == null) {
			throw new TransformException(I18N.prop("msg_ie_not_found_serializer", this.getClass().getSimpleName()));
		}
		Logger.log(LoggingLevel.INFO, "transformer: [%s] is running.", this.getClass().getSimpleName());
		Class<?>[] types = this.getKnownTypes().toArray(new Class<?>[] {});
		try (InputStream stream = new FileInputStream(this.getInputData())) {
			List<IBusinessObject> outDatas = new ArrayList<>();
			// 以UTF-8重新读取
			try (InputStreamReader streamReader = new InputStreamReader(stream, "utf-8")) {
				Logger.log(LoggingLevel.INFO, "transformer: [%s] to run deserialize.", this.getClass().getSimpleName());
				Object object = serializer.deserialize(streamReader, types);
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
				this.setOutputData(outDatas);
				Logger.log(LoggingLevel.INFO, "transformer: [%s] got bo count [%s].", this.getClass().getSimpleName(),
						this.getOutputData().size());
			}
		} catch (Exception e) {
			throw new TransformException(e);
		}
	}

	protected abstract ISerializer createSerializer();

}
