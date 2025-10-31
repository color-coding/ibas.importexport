package org.colorcoding.ibas.importexport.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.common.Strings;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exportrecord.ExportRecord;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.data.DataExportInfo;
import org.colorcoding.ibas.importexport.repository.BORepositoryImportExport;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

/**
 * ImportExport 数据服务JSON
 */
@Path("data")
public class DataService extends BORepositoryImportExport {
	// --------------------------------------------------------------------------------------------//
	/**
	 * 获取业务对象schema
	 * 
	 * @param boCode 业务对象编码
	 * @param type   schema类型
	 * @param token  口令
	 * @return 操作结果
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("schema")
	public OperationResult<String> schema(@QueryParam("boCode") String boCode, @QueryParam("type") String type,
			@HeaderParam("authorization") String authorization, @QueryParam("token") String token) {
		return super.schema(boCode, type, MyConfiguration.optToken(authorization, token));
	}

	// --------------------------------------------------------------------------------------------//

	/**
	 * 获取数据导出者
	 * 
	 * @param criteria 查询
	 * @return 操作结果
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("fetchDataExporter")
	public OperationResult<DataExportInfo> fetchDataExporter(Criteria criteria,
			@HeaderParam("authorization") String authorization, @QueryParam("token") String token) {
		return super.fetchDataExporter(criteria, MyConfiguration.optToken(authorization, token));
	}

	// --------------------------------------------------------------------------------------------//
	/**
	 * 查询-数据导出模板
	 * 
	 * @param criteria 查询
	 * @param token    口令
	 * @return 操作结果
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("fetchExportTemplate")
	public OperationResult<ExportTemplate> fetchExportTemplate(Criteria criteria,
			@HeaderParam("authorization") String authorization, @QueryParam("token") String token) {
		return super.fetchExportTemplate(criteria, MyConfiguration.optToken(authorization, token));
	}

	/**
	 * 保存-数据导出模板
	 * 
	 * @param bo    对象实例
	 * @param token 口令
	 * @return 操作结果
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("saveExportTemplate")
	public OperationResult<ExportTemplate> saveExportTemplate(ExportTemplate bo,
			@HeaderParam("authorization") String authorization, @QueryParam("token") String token) {
		return super.saveExportTemplate(bo, MyConfiguration.optToken(authorization, token));
	}

	// --------------------------------------------------------------------------------------------//
	/**
	 * 查询-导出日志
	 * 
	 * @param criteria 查询
	 * @param token    口令
	 * @return 操作结果
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("fetchExportRecord")
	public OperationResult<ExportRecord> fetchExportRecord(Criteria criteria,
			@HeaderParam("authorization") String authorization, @QueryParam("token") String token) {
		return super.fetchExportRecord(criteria, MyConfiguration.optToken(authorization, token));
	}

	/**
	 * 保存-导出日志
	 * 
	 * @param bo    对象实例
	 * @param token 口令
	 * @return 操作结果
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("saveExportRecord")
	public OperationResult<ExportRecord> saveExportRecord(ExportRecord bo,
			@HeaderParam("authorization") String authorization, @QueryParam("token") String token) {
		return super.saveExportRecord(bo, MyConfiguration.optToken(authorization, token));
	}

	/**
	 * 记录导出
	 * 
	 * @param boKeys 对象标识
	 * @param cause  原因
	 * @param token  口令
	 * @return 操作结果
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("writeExportRecord")
	public OperationResult<ExportRecord> writeExportRecord(FormDataMultiPart formData,
			@HeaderParam("authorization") String authorization, @QueryParam("token") String token) {
		String boKeys = Strings.VALUE_EMPTY;
		String cause = Strings.VALUE_EMPTY;
		String content = Strings.VALUE_EMPTY;
		for (List<FormDataBodyPart> bodyParts : formData.getFields().values()) {
			for (FormDataBodyPart bodyPart : bodyParts) {
				if ("boKeys".equalsIgnoreCase(bodyPart.getName())) {
					boKeys = bodyPart.getValue();
				} else if ("cause".equalsIgnoreCase(bodyPart.getName())) {
					cause = bodyPart.getValue();
				} else if ("content".equalsIgnoreCase(bodyPart.getName())) {
					content = bodyPart.getValue();
				}
			}
		}
		return super.writeExportRecord(boKeys, cause, content, MyConfiguration.optToken(authorization, token));
	}
	// --------------------------------------------------------------------------------------------//

}
