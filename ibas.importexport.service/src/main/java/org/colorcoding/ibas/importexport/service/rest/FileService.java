package org.colorcoding.ibas.importexport.service.rest;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.data.FileData;
import org.colorcoding.ibas.bobas.repository.jersey.FileRepositoryService;
import org.colorcoding.ibas.importexport.repository.BORepositoryImportExport;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("file")
public class FileService extends FileRepositoryService {

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
		}
		return opRslt;
	}
}
