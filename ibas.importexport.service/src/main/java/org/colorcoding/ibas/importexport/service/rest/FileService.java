package org.colorcoding.ibas.importexport.service.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.data.FileData;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.messages.Logger;
import org.colorcoding.ibas.bobas.repository.jersey.FileRepositoryService;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.repository.BORepositoryImportExport;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
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
	public OperationResult<FileData> upload(@FormDataParam("file") InputStream fileStream,
			@FormDataParam("file") FormDataContentDisposition fileDisposition, @QueryParam("token") String token) {
		return super.save(fileStream, fileDisposition, token);
	}

	@POST
	@Path("import")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public OperationResult<String> importData(@FormDataParam("file") InputStream fileStream,
			@FormDataParam("file") FormDataContentDisposition fileDisposition, @QueryParam("token") String token) {
		OperationResult<String> opRslt = null;
		try {
			opRslt = new OperationResult<String>();
			// 保存文件
			OperationResult<FileData> opRsltFile = super.save(fileStream, fileDisposition, token);
			if (opRsltFile.getError() != null) {
				throw opRsltFile.getError();
			}
			if (opRsltFile.getResultCode() != 0) {
				throw new Error(opRsltFile.getMessage());
			}
			// 导入文件
			BORepositoryImportExport boRepository = new BORepositoryImportExport();
			boRepository.setUserToken(token);
			for (FileData data : opRsltFile.getResultObjects()) {
				IOperationResult<String> opRsltImport = boRepository.importData(data);
				if (opRsltImport.getError() != null) {
					throw opRsltImport.getError();
				}
				if (opRsltImport.getResultCode() != 0) {
					throw new Error(opRsltImport.getMessage());
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
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public byte[] exportData(Criteria criteria, @QueryParam("token") String token,
			@Context HttpServletResponse response) {
		try {
			// 获取导出的文件
			BORepositoryImportExport boRepository = new BORepositoryImportExport();
			boRepository.setUserToken(token);
			IOperationResult<FileData> opRsltExport = boRepository.exportData(criteria);
			if (opRsltExport.getError() != null) {
				throw opRsltExport.getError();
			}
			if (opRsltExport.getResultCode() != 0) {
				throw new Error(opRsltExport.getMessage());
			}
			FileData fileData = opRsltExport.getResultObjects().firstOrDefault();
			if (fileData != null) {
				// 数据存在，尝试转为字节数组
				File file = new File(fileData.getLocation());
				long fileSize = file.length();
				if (fileSize > Integer.MAX_VALUE) {
					throw new Exception(I18N.prop("msg_importexport_invaild_file_data"));
				}
				FileInputStream inputStream = new FileInputStream(file);
				byte[] buffer = new byte[(int) fileSize];
				int offset = 0;
				int numRead = 0;
				while (offset < buffer.length
						&& (numRead = inputStream.read(buffer, offset, buffer.length - offset)) >= 0) {
					offset += numRead;
				}
				inputStream.close();
				response.setHeader("content-disposition",
						String.format("attachment;filename=%s", fileData.getFileName()));// 为文件命名
				response.addHeader("content-type", "application/xml");
				return buffer;
			} else {
				// 无效的导出数据
				response.setHeader("content-disposition", "attachment;filename=INVALID_DATA");// 为文件命名
				response.addHeader("content-type", "application/xml");
				return new byte[] {};
			}
		} catch (Exception e) {
			Logger.log(e);
			throw new WebApplicationException(502);
		}
	}
}
