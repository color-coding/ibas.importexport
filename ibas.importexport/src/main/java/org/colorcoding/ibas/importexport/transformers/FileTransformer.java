package org.colorcoding.ibas.importexport.transformers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

import org.colorcoding.ibas.bobas.i18n.i18n;
import org.colorcoding.ibas.bobas.messages.MessageLevel;
import org.colorcoding.ibas.bobas.messages.RuntimeLog;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.importexport.MyConfiguration;

/**
 * 转换者
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class FileTransformer extends Transformer {

	private String outputFolder;

	public final String getOutputFolder() {
		if (this.outputFolder == null || this.outputFolder.isEmpty()) {
			this.outputFolder = MyConfiguration.getDataFolder();
		}
		return outputFolder;
	}

	public final void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	@Override
	public String[] getData() {
		return this.getOutputFiles().toArray(new String[] {});
	}

	private ArrayList<String> outputFiles;

	public final ArrayList<String> getOutputFiles() {
		if (this.outputFiles == null) {
			this.outputFiles = new ArrayList<>();
		}
		return outputFiles;
	}

	/**
	 * 转换
	 * 
	 * @throws TransformException
	 */
	public void transform() throws TransformException {
		ISerializer<?> serializer = this.createSerializer();
		if (serializer == null) {
			throw new TransformException(
					i18n.prop("msg_importexport_not_found_serializer", this.getClass().getSimpleName()));
		}
		Class<?> types = this.getKnownTypes();
		if (this.getBOs() == null || this.getBOs().length == 0) {
			RuntimeLog.log(MessageLevel.INFO, "transformer: [%s] to run deserialize.", this.getClass().getSimpleName());
			Object object = serializer.deserialize(this.getDataStream(), types);
			if (object != null) {
				// 结果为数组或集合，拆散
				if (object instanceof Iterable) {
					Iterable<?> iterable = (Iterable<?>) object;
					for (Object item : iterable) {
						this.addBO(item);
					}
				} else if (object.getClass().isArray()) {
					for (int i = 0; i < Array.getLength(object); i++) {
						this.addBO(Array.get(object, i));
					}
				} else {
					this.addBO(object);
				}
			}
		} else {
			RuntimeLog.log(MessageLevel.INFO, "transformer: [%s] to run serialize.", this.getClass().getSimpleName());
			this.getOutputFiles().clear();
			for (int i = 0; i < this.getBOs().length; i++) {
				try {
					Object item = this.getBOs()[i];
					StringBuilder filePath = new StringBuilder();
					filePath.append(this.getOutputFolder());
					filePath.append(File.separator);
					filePath.append(item.getClass().getSimpleName());
					filePath.append("_");
					filePath.append(UUID.randomUUID().toString());
					filePath.append(".");
					filePath.append(this.getExtension());
					OutputStream outputStream = new FileOutputStream(filePath.toString());
					serializer.serialize(item, outputStream, types);
					outputStream.close();
					this.getOutputFiles().add(filePath.toString());
				} catch (IOException e) {
					throw new TransformException(e);
				}
			}
		}
	}

	/**
	 * 获取数据的输入流
	 * 
	 * @return
	 */
	protected abstract InputStream getDataStream();

	/**
	 * 获取输出文件扩展名
	 * 
	 * @return
	 */
	protected abstract String getExtension();

	/**
	 * 获取已知的类型
	 * 
	 * @return
	 */
	protected abstract Class<?> getKnownTypes();

	/**
	 * 创建序列化程序
	 * 
	 * @return
	 */
	protected abstract ISerializer<?> createSerializer();

}
