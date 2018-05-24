package org.colorcoding.ibas.importexport.service.rest;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.data.FileData;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.repository.jersey.FileRepositoryService;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializerFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.data.DataExportInfo;
import org.colorcoding.ibas.importexport.repository.BORepositoryImportExport;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

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
	public OperationResult<String> importData(FormDataMultiPart formData, @FormDataParam("update") String sUpdate,
			@QueryParam("token") String token) {
		OperationResult<String> opRslt = null;
		try {
			opRslt = new OperationResult<String>();
			// 保存文件
			OperationResult<FileData> opRsltFile = super.save(formData.getField("file"), token);
			if (opRsltFile.getError() != null) {
				throw opRsltFile.getError();
			}
			// 导入文件
			BORepositoryImportExport boRepository = new BORepositoryImportExport();
			boRepository.setUserToken(token);
			for (FileData data : opRsltFile.getResultObjects()) {
				IOperationResult<String> opRsltImport = boRepository.importData(data, Boolean.parseBoolean(sUpdate));
				if (opRsltImport.getError() != null) {
					throw opRsltImport.getError();
				}
				// 记录结果
				opRslt.addResultObjects(opRsltImport.getResultObjects());
			}
		} catch (Exception e) {
			opRslt = new OperationResult<String>(e);
			Logger.log(e);
		}
		return opRslt;
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
				FormDataBodyPart bodyPart = formData.getField(name.toLowerCase());
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
				throw opRsltExport.getError();
			}
			FileData fileData = opRsltExport.getResultObjects().firstOrDefault();
			if (fileData != null) {
				// 数据存在，尝试转为字节数组
				// 为文件命名
				response.setHeader("Content-Disposition",
						String.format("attachment;filename=%s", fileData.getFileName()));
				// 设置内容类型
				response.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				OutputStream os = response.getOutputStream();
				os.write(fileData.getFileBytes());
				os.flush();
			} else {
				// 无效的导出数据
				throw new WebApplicationException(404);
			}
		} catch (WebApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
}
