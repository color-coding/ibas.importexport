package org.colorcoding.ibas.importexport.service.rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.data.DataConvert;
import org.colorcoding.ibas.bobas.data.FileData;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.bobas.repository.jersey.FileRepositoryService;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.ISerializerManager;
import org.colorcoding.ibas.bobas.serialization.SerializerFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.data.DataExportInfo;
import org.colorcoding.ibas.importexport.data.DataWrapping;
import org.colorcoding.ibas.importexport.data.emDataUpdateMethod;
import org.colorcoding.ibas.importexport.repository.BORepositoryImportExport;
import org.colorcoding.ibas.importexport.transformer.FileTransformer;
import org.colorcoding.ibas.importexport.transformer.FileTransformerSerialization;
import org.colorcoding.ibas.importexport.transformer.IFileTransformer;
import org.colorcoding.ibas.importexport.transformer.TransformerFactory;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

@Path("file")
public class FileService extends FileRepositoryService {

	public final static String CONFIG_ITEM_IMPORT_WORK_FOLDER = "ImportWorkFolder";

	public FileService() {
		this.getRepository().setRepositoryFolder(
				MyConfiguration.getConfigValue(CONFIG_ITEM_IMPORT_WORK_FOLDER, MyConfiguration.getTempFolder()));
	}

	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationResult<FileData> upload(FormDataMultiPart formData, @QueryParam("token") String token) {
		return super.save(formData.getField("file"), token);
	}

	@POST
	@Path("import")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationResult<String> importData(FormDataMultiPart formData, @QueryParam("token") String token) {
		OperationResult<String> opRslt = null;
		try {
			// 处理请求参数
			OperationResult<FileData> opRsltFile = null;
			emDataUpdateMethod updateMethod = emDataUpdateMethod.SKIP;
			for (List<FormDataBodyPart> bodyParts : formData.getFields().values()) {
				for (FormDataBodyPart bodyPart : bodyParts) {
					if ("file".equalsIgnoreCase(bodyPart.getName())) {
						opRsltFile = super.save(bodyPart, token);
						if (opRsltFile.getError() != null) {
							throw opRsltFile.getError();
						}
					} else if ("updateMethod".equalsIgnoreCase(bodyPart.getName())) {
						String value = bodyPart.getValue();
						if (!DataConvert.isNullOrEmpty(value)) {
							updateMethod = DataConvert.convert(emDataUpdateMethod.class, value.toUpperCase());
						}
					}
				}
			}
			if (opRsltFile == null || opRsltFile.getResultObjects().isEmpty()) {
				throw new Exception(I18N.prop("msg_ie_invaild_data"));
			}
			// 导入文件
			BORepositoryImportExport boRepository = new BORepositoryImportExport();
			boRepository.setUserToken(token);
			opRslt = new OperationResult<String>();
			for (FileData data : opRsltFile.getResultObjects()) {
				IOperationResult<String> opRsltImport = boRepository.importData(data, updateMethod);
				if (opRsltImport.getError() != null) {
					throw opRsltImport.getError();
				}
				// 记录结果
				opRslt.addResultObjects(opRsltImport.getResultObjects());
				opRslt.addInformations(opRsltImport.getInformations());
			}
		} catch (Exception e) {
			opRslt = new OperationResult<String>(e);
		}
		return opRslt;
	}

	@POST
	@Path("parse")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationResult<DataWrapping> parseData(FormDataMultiPart formData, @QueryParam("token") String token) {
		OperationResult<DataWrapping> opRslt = null;
		try {
			opRslt = new OperationResult<DataWrapping>();
			// 保存文件
			OperationResult<FileData> opRsltFile = super.save(formData.getField("file"), token);
			if (opRsltFile.getError() != null) {
				throw opRsltFile.getError();
			}
			// 解析文件
			IFileTransformer transformer;
			ISerializer<?> serializer = SerializerFactory.create().createManager().create(ISerializerManager.TYPE_JSON);
			for (FileData data : opRsltFile.getResultObjects()) {
				try {
					if (data == null || data.getOriginalName().indexOf(".") < 0) {
						throw new Exception(I18N.prop("msg_bobas_invalid_data"));
					}
					// 创建转换者
					String type = data.getOriginalName().substring(data.getOriginalName().lastIndexOf(".") + 1);
					if (type != null && type.equalsIgnoreCase("xlsm")) {
						// 带宏的excel文件，识别为普通问
						type = "xlsx";
					}
					type = String.format(FileTransformer.GROUP_TEMPLATE, type).toUpperCase();
					transformer = TransformerFactory.create().create(type);
					if (transformer instanceof FileTransformerSerialization) {
						((FileTransformerSerialization) transformer)
								.setBOFactory(BORepositoryImportExport.getBOFactory());
					}
					Logger.log(MessageLevel.DEBUG, BORepositoryImportExport.MSG_TRANSFORMER_IMPORT_DATA,
							transformer.getClass().getName());
					// 转换文件数据到业务对象
					transformer.setInputData(new File(data.getLocation()));
					transformer.transform();
					for (IBusinessObject object : transformer.getOutputData()) {
						ByteArrayOutputStream writer = new ByteArrayOutputStream();
						serializer.serialize(object, writer);
						opRslt.addResultObjects(new DataWrapping(writer.toString()));
					}
				} catch (Exception e) {
					Logger.log(e);
				}
			}
		} catch (Exception e) {
			opRslt = new OperationResult<DataWrapping>(e);
		}
		return opRslt;
	}

	protected String nameElement(String name) {
		int index = 0;
		for (char item : name.toCharArray()) {
			if (Character.isUpperCase(item)) {
				index++;
			} else {
				break;
			}
		}
		if (index > 0) {
			if (index == 1 || index == name.length()) {
				name = name.substring(0, index).toLowerCase() + name.substring(index);
			} else {
				index -= 1;
				name = name.substring(0, index).toLowerCase() + name.substring(index);
			}
		}
		return name;
	}

	@POST
	@Path("export")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public void exportData(FormDataMultiPart formData, @Context HttpServletResponse response,
			@QueryParam("token") String token) {
		try {
			// 获取导出的文件
			ISerializer<?> serializer = SerializerFactory.create().createManager().create("json");
			DataExportInfo info = new DataExportInfo();
			for (Method method : DataExportInfo.class.getMethods()) {
				if (method.getParameterCount() != 1) {
					continue;
				}
				String name = method.getName();
				if (name.startsWith("set")) {
					name = name.substring(3);
				} else {
					continue;
				}
				name = this.nameElement(name);
				FormDataBodyPart bodyPart = formData.getField(name);
				if (bodyPart == null) {
					continue;
				}
				Class<?> clazz = method.getParameterTypes()[0];
				Object value = null;
				if (clazz == String.class) {
					value = bodyPart.getValueAs(String.class);
				} else if (clazz == InputStream.class) {
					value = bodyPart.getValueAs(InputStream.class);
				} else {
					value = serializer.deserialize(bodyPart.getValueAs(InputStream.class), clazz);
				}
				try {
					method.invoke(info, value);
				} catch (Exception e) {
					Logger.log(e);
				}
			}
			BORepositoryImportExport boRepository = new BORepositoryImportExport();
			boRepository.setUserToken(token);
			IOperationResult<FileData> opRsltExport = boRepository.exportData(info);
			if (opRsltExport.getError() != null) {
				throw new WebApplicationException(
						Response.status(500).type(MediaType.APPLICATION_JSON).entity(opRsltExport).build());
			}
			FileData fileData = opRsltExport.getResultObjects().firstOrDefault();
			if (fileData != null) {
				// 数据存在，尝试转为字节数组
				// 为文件命名
				response.setHeader("Content-Disposition",
						String.format("attachment;filename=%s", fileData.getFileName()));
				// 设置内容类型
				if (info.getContentType() != null && !info.getContentType().isEmpty()) {
					response.setContentType(info.getContentType());
				} else {
					response.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				}
				OutputStream os = response.getOutputStream();
				os.write(fileData.getFileBytes());
				os.flush();
			} else {
				// 无效的导出数据
				throw new WebApplicationException(500);
			}
		} catch (WebApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
}
