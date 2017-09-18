package org.colorcoding.ibas.importexport.transformers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.messages.MessageLevel;
import org.colorcoding.ibas.bobas.messages.Logger;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.importexport.MyConfiguration;

/**
 * 文件转换者（业务对象&文件）
 * 
 * @author Niuren.Zhu
 *
 */
public abstract class FileTransformer extends Transformer {

	private String outputFolder;

	/**
	 * 设置-输出目录
	 * 
	 * @return
	 */
	public final String getOutputFolder() {
		if (this.outputFolder == null || this.outputFolder.isEmpty()) {
			this.outputFolder = MyConfiguration.getDataFolder();
		}
		return outputFolder;
	}

	/**
	 * 获取-输出目录
	 * 
	 * @param outputFolder
	 */
	public final void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	/**
	 * 获取-转换的数据（此处为文件路径）
	 */
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
	 * 设置待转换的数据（此处要求文件路径）
	 */
	@Override
	public void setData(Object data) {
		if (data == null) {
			this.setFileData(null);
		} else if (data instanceof String) {
			this.setFileData(new File((String) data));
		} else if (data instanceof File) {
			this.setFileData((File) data);
		} else {
			super.setData(data);
		}
	}

	private File fileData;

	/**
	 * 获取-待转换数据
	 * 
	 * @return
	 */
	protected final File getFileData() {
		return fileData;
	}

	/**
	 * 设置-待转换数据
	 * 
	 * @param fileData
	 */
	private final void setFileData(File fileData) {
		this.fileData = fileData;
	}

	@Override
	public void addBOs(Object[] items) {
		this.setFileData(null);
		super.addBOs(items);
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
					I18N.prop("msg_importexport_not_found_serializer", this.getClass().getSimpleName()));
		}
		try {
			if (this.getBOs() == null || this.getBOs().length == 0) {
				Class<?>[] types = this.getKnownTypes().toArray(new Class<?>[] {});
				Logger.log(MessageLevel.INFO, "transformer: [%s] to run deserialize.",
						this.getClass().getSimpleName());
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
				Logger.log(MessageLevel.INFO, "transformer: [%s] got bo count [%s].",
						this.getClass().getSimpleName(), this.getBOs().length);
			} else {
				Logger.log(MessageLevel.INFO, "transformer: [%s] to run serialize.",
						this.getClass().getSimpleName());
				this.getOutputFiles().clear();
				for (int i = 0; i < this.getBOs().length; i++) {
					Object item = this.getBOs()[i];
					StringBuilder filePath = new StringBuilder();
					filePath.append(this.getOutputFolder());
					filePath.append(File.separator);
					filePath.append(item.getClass().getSimpleName());
					filePath.append("_");
					filePath.append(UUID.randomUUID().toString());
					filePath.append(".");
					filePath.append(this.getExtension());
					File file = new File(filePath.toString());
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					if (!file.exists()) {
						file.createNewFile();
					}
					OutputStream outputStream = new FileOutputStream(file);
					serializer.serialize(item, outputStream);
					outputStream.close();
					this.getOutputFiles().add(file.getPath());
				}
				Logger.log(MessageLevel.INFO, "transformer: [%s] output file count [%s].",
						this.getClass().getSimpleName(), this.getOutputFiles().size());
			}
		} catch (Exception e) {
			Logger.log(e);
			throw new TransformException(e);
		}
	}

	/**
	 * 获取数据的输入流
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	protected InputStream getDataStream() throws FileNotFoundException {
		return new FileInputStream(this.getFileData());
	}

	/**
	 * 获取输出文件扩展名
	 * 
	 * @return
	 */
	protected abstract String getExtension();

	/**
	 * 创建序列化程序
	 * 
	 * @return
	 */
	protected abstract ISerializer<?> createSerializer();

}
