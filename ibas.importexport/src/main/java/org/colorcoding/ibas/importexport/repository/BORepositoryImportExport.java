package org.colorcoding.ibas.importexport.repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.function.Consumer;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.common.ConditionOperation;
import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.ICondition;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.core.BOFactory;
import org.colorcoding.ibas.bobas.core.IBOFactory;
import org.colorcoding.ibas.bobas.core.RepositoryException;
import org.colorcoding.ibas.bobas.data.FileData;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.bobas.repository.BORepositoryServiceApplication;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializerFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;
import org.colorcoding.ibas.importexport.data.DataExportInfo;
import org.colorcoding.ibas.importexport.transformer.FileTransformer;
import org.colorcoding.ibas.importexport.transformer.IFileTransformer;
import org.colorcoding.ibas.importexport.transformer.ITemplateTransformer;
import org.colorcoding.ibas.importexport.transformer.ITransformer;
import org.colorcoding.ibas.importexport.transformer.ITransformerFile;
import org.colorcoding.ibas.importexport.transformer.TransformerFactory;

/**
 * ImportExport仓库
 */
public class BORepositoryImportExport extends BORepositoryServiceApplication
		implements IBORepositoryImportExportSvc, IBORepositoryImportExportApp {

	private volatile static IBOFactory boFactory;

	/**
	 * 业务对象工厂
	 * 
	 * @return
	 */
	protected static IBOFactory getBOFactory() {
		if (boFactory == null) {
			synchronized (BORepositoryImportExport.class) {
				if (boFactory == null) {
					boFactory = BOFactory.create();
					// 加载可识别命名空间类型
					Consumer<String> classLoader = new Consumer<String>() {

						@Override
						public void accept(String t) {
							if (t == null || t.isEmpty()) {
								return;
							}
							String[] namespaces = null;
							if (t.indexOf(";") > 0) {
								namespaces = t.split(";");
							} else {
								namespaces = new String[] { t };
							}
							for (String namesapce : namespaces) {
								if (namesapce != null && !namesapce.isEmpty()) {
									Logger.log(MessageLevel.INFO, "import export: load [%s]'s classes.", namesapce);
									for (Class<?> item : boFactory.loadClasses(namesapce)) {
										boFactory.register(item);
									}
								}
							}
						}
					};
					classLoader.accept("org.colorcoding.ibas");
					classLoader.accept(MyConfiguration.getConfigValue(MyConfiguration.CONFIG_ITEM_SCANING_PACKAGES));
				}
			}
		}
		return boFactory;
	}

	// --------------------------------------------------------------------------------------------//
	/**
	 * 查询-数据导出模板
	 * 
	 * @param criteria
	 *            查询
	 * @param token
	 *            口令
	 * @return 操作结果
	 */
	public OperationResult<ExportTemplate> fetchExportTemplate(ICriteria criteria, String token) {
		return super.fetch(criteria, token, ExportTemplate.class);
	}

	/**
	 * 查询-数据导出模板（提前设置用户口令）
	 * 
	 * @param criteria
	 *            查询
	 * @return 操作结果
	 */
	public IOperationResult<IExportTemplate> fetchExportTemplate(ICriteria criteria) {
		return new OperationResult<IExportTemplate>(this.fetchExportTemplate(criteria, this.getUserToken()));
	}

	/**
	 * 保存-数据导出模板
	 * 
	 * @param bo
	 *            对象实例
	 * @param token
	 *            口令
	 * @return 操作结果
	 */
	public OperationResult<ExportTemplate> saveExportTemplate(ExportTemplate bo, String token) {
		return super.save(bo, token);
	}

	/**
	 * 保存-数据导出模板（提前设置用户口令）
	 * 
	 * @param bo
	 *            对象实例
	 * @return 操作结果
	 */
	public IOperationResult<IExportTemplate> saveExportTemplate(IExportTemplate bo) {
		return new OperationResult<IExportTemplate>(this.saveExportTemplate((ExportTemplate) bo, this.getUserToken()));
	}

	// --------------------------------------------------------------------------------------------//
	@Override
	public IOperationResult<String> schema(String boCode, String type) {
		return this.schema(boCode, type, this.getUserToken());
	}

	@Override
	public OperationResult<String> schema(String boCode, String type, String token) {
		OperationResult<String> opRslt = new OperationResult<String>();
		try {
			this.setUserToken(token);
			Class<?> boType = getBOFactory().getClass(boCode);
			if (boType == null) {
				throw new Exception(I18N.prop("msg_ie_not_found_class", boCode));
			}
			ISerializer<?> serializer = SerializerFactory.create().createManager().create(type);
			if (serializer == null) {
				throw new Exception(I18N.prop("msg_ie_not_found_serializer", type));
			}
			ByteArrayOutputStream writer = new ByteArrayOutputStream();
			serializer.getSchema(boType, writer);
			opRslt.addResultObjects(new String(writer.toByteArray(), "utf-8"));
		} catch (Exception e) {
			opRslt = new OperationResult<String>(e);
			Logger.log(e);
		}
		return opRslt;
	}

	// --------------------------------------------------------------------------------------------//

	/**
	 * 导入数据
	 * 
	 * @param data
	 *            数据
	 * @param update
	 *            更新数据
	 * @param token
	 *            口令
	 * @return 操作结果
	 */
	public OperationResult<String> importData(FileData data, boolean update, String token) {
		OperationResult<String> opRslt = null;
		try {
			this.setUserToken(token);
			if (data == null || data.getOriginalName().indexOf(".") < 0) {
				throw new Exception(I18N.prop("msg_bobas_invalid_data"));
			}
			// 创建转换者
			String type = String
					.format(FileTransformer.GROUP_TEMPLATE,
							data.getOriginalName().substring(data.getOriginalName().lastIndexOf(".") + 1))
					.toUpperCase();
			ITransformer<?, ?> transformer = TransformerFactory.create().create(type);
			if (!(transformer instanceof IFileTransformer)) {
				throw new Exception(I18N.prop("msg_ie_not_found_transformer", type));
			}
			IFileTransformer fileTransformer = (IFileTransformer) transformer;
			// 转换文件数据到业务对象
			fileTransformer.setInputData(new File(data.getLocation()));
			fileTransformer.transform();
			boolean myTrans = this.beginTransaction();
			try {
				opRslt = new OperationResult<String>();
				// 返回存储事务标记
				opRslt.addInformations("REPOSITORY_TRANSACTION_ID", this.getRepository().getTransactionId(),
						"DATA_IMPORT");
				// 保存业务对象
				for (IBusinessObject object : fileTransformer.getOutputData()) {
					// 判断对象是否存在
					ICriteria criteria = object.getCriteria();
					if (criteria != null && !criteria.getConditions().isEmpty()) {
						IOperationResult<?> opRsltExists = this.fetch(criteria, token, object.getClass());
						if (!opRsltExists.getResultObjects().isEmpty()) {
							// 已存在数据
							if (update) {
								// 强制保存，删除旧数据
								for (Object item : opRsltExists.getResultObjects()) {
									if (item instanceof IBusinessObject) {
										IBusinessObject boItem = (IBusinessObject) item;
										boItem.delete();
										IOperationResult<?> opRsltDelete = this.save(boItem, token);
										if (opRsltDelete.getError() != null) {
											throw opRsltDelete.getError();
										}
										opRslt.addInformations("DELETED_EXISTS_DATA", boItem.toString(), "DATA_IMPORT");
									}
								}
							} else {
								// 非强制保存，跳过
								continue;
							}
						}
					}
					IOperationResult<IBusinessObject> opRsltSave = this.save(object, token);
					if (opRsltSave.getError() != null) {
						throw opRsltSave.getError();
					}
					for (IBusinessObject item : opRsltSave.getResultObjects()) {
						opRslt.addResultObjects(item.toString());
					}
				}
				if (myTrans) {
					this.commitTransaction();
				}
			} catch (Exception e) {
				if (myTrans) {
					try {
						this.rollbackTransaction();
					} catch (RepositoryException e1) {
						Logger.log(e1);
					}
				}
				throw e;
			}
		} catch (Exception e) {
			opRslt = new OperationResult<String>(e);
			Logger.log(e);
		}
		return opRslt;
	}

	public IOperationResult<String> importData(FileData data) {
		return this.importData(data, false);
	}

	public IOperationResult<String> importData(FileData data, boolean update) {
		return this.importData(data, update, this.getUserToken());
	}

	// --------------------------------------------------------------------------------------------//
	@Override
	public IOperationResult<FileData> exportData(DataExportInfo info) {
		return this.exportData(info, this.getUserToken());
	}

	@Override
	public OperationResult<FileData> exportData(DataExportInfo info, String token) {
		OperationResult<FileData> opRslt = new OperationResult<FileData>();
		try {
			this.setUserToken(token);
			// 获取导出的模板
			ITransformer<?, ?> transformer = TransformerFactory.create().create(info.getTransformer());
			if (transformer == null) {
				throw new Exception(I18N.prop("msg_ie_not_found_transformer", info.getTransformer()));
			}
			if (transformer instanceof ITransformerFile) {
				// 查询数据
				ICriteria criteria = info.getCriteria();
				ITransformerFile fileTransformer = (ITransformerFile) transformer;
				fileTransformer.setWorkFolder(MyConfiguration.getTempFolder());
				if (criteria == null || criteria.getBusinessObject() == null
						|| criteria.getBusinessObject().isEmpty()) {
					throw new Exception(I18N.prop("msg_bobas_invaild_criteria"));
				}
				// 获取导出的对象类型
				Class<?> boType = getBOFactory().getClass(criteria.getBusinessObject());
				if (boType == null) {
					throw new Exception(I18N.prop("msg_ie_not_found_class", criteria.getBusinessObject()));
				}
				if (criteria.getConditions().isEmpty()) {
					// 没有条件，认为是只要模板
					Object object = boType.newInstance();
					if (object instanceof IBusinessObject) {
						fileTransformer.setInputData(new IBusinessObject[] { (IBusinessObject) object });
					}
				} else {
					// 查询并返回数据
					@SuppressWarnings("unchecked")
					IOperationResult<IBusinessObject> opRsltFetch = this.fetch(criteria, token,
							(Class<IBusinessObject>) boType);
					if (opRsltFetch.getError() != null) {
						throw opRsltFetch.getError();
					}
					fileTransformer.setInputData(opRsltFetch.getResultObjects().toArray(new IBusinessObject[] {}));
				}
				// 导出数据
				fileTransformer.transform();
				File file = fileTransformer.getOutputData().firstOrDefault();
				if (file == null) {
					throw new Exception(I18N.prop("msg_ie_invaild_data"));
				}
				FileData fileData = new FileData();
				fileData.setFileName(file.getName());
				fileData.setLocation(file.getPath());
				opRslt.addResultObjects(fileData);
			} else if (transformer instanceof ITemplateTransformer) {
				ICriteria criteria = new Criteria();
				ICondition condition = criteria.getConditions().create();
				condition.setAlias(ExportTemplate.PROPERTY_ACTIVATED.getName());
				condition.setValue(emYesNo.YES);
				condition = criteria.getConditions().create();
				condition.setAlias(ExportTemplate.PROPERTY_OBJECTKEY.getName());
				condition.setValue(info.getTemplate());
				IOperationResult<IExportTemplate> opRstlTP = this.fetchExportTemplate(criteria);
				if (opRstlTP.getError() != null) {
					throw opRstlTP.getError();
				}
				IExportTemplate template = opRstlTP.getResultObjects().firstOrDefault();
				if (template == null) {
					throw new Exception(I18N.prop("msg_ie_not_found_template", info.getTemplate()));
				}
				ITemplateTransformer templateTransformer = (ITemplateTransformer) transformer;
				templateTransformer.setTemplate(template);
				templateTransformer.setInputData(info.getContent());
				templateTransformer.transform();
				OutputStream data = templateTransformer.getOutputData().firstOrDefault();
				if (data == null) {
					throw new Exception(I18N.prop("msg_ie_invaild_data"));
				}
			}
		} catch (Exception e) {
			opRslt = new OperationResult<FileData>(e);
			Logger.log(e);
		}
		return opRslt;
	}

	// --------------------------------------------------------------------------------------------//

	@Override
	public IOperationResult<DataExportInfo> fetchDataExporter(ICriteria criteria) {
		return this.fetchDataExporter(criteria, this.getUserToken());
	}

	@Override
	public OperationResult<DataExportInfo> fetchDataExporter(ICriteria criteria, String token) {
		try {
			this.setUserToken(token);
			ICondition tCondition = criteria.getConditions()
					.firstOrDefault(c -> c.getAlias().equalsIgnoreCase(DataExportInfo.CONDITION_ALIAS_TRANSFORMER));
			OperationResult<DataExportInfo> operationResult = new OperationResult<>();
			for (String transformer : TransformerFactory.create().getTransformers().keySet()) {
				if (tCondition != null) {
					if (tCondition.getOperation() == ConditionOperation.START) {
						if (!transformer.startsWith(tCondition.getValue())) {
							continue;
						}
					} else {
						throw new Exception(I18N.prop("msg_bobas_invaild_condition_operation"));
					}
				}
				if (criteria.getBusinessObject() != null && !criteria.getBusinessObject().isEmpty()) {
					ICriteria tpCriteria = new Criteria();
					ICondition condition = tpCriteria.getConditions().create();
					condition.setAlias(ExportTemplate.PROPERTY_ACTIVATED.getName());
					condition.setValue(emYesNo.YES);
					condition = tpCriteria.getConditions().create();
					condition.setAlias(ExportTemplate.PROPERTY_BOCODE.getName());
					condition.setValue(criteria.getBusinessObject());
					IOperationResult<IExportTemplate> opRsltET = this.fetchExportTemplate(tpCriteria);
					if (opRsltET.getError() != null) {
						throw opRsltET.getError();
					}
					for (IExportTemplate template : opRsltET.getResultObjects()) {
						DataExportInfo info = new DataExportInfo();
						info.setTransformer(transformer);
						info.setTemplate(String.valueOf(template.getObjectKey()));
						operationResult.addResultObjects(info);
					}
				} else {
					DataExportInfo info = new DataExportInfo();
					info.setTransformer(transformer);
					operationResult.addResultObjects(info);
				}
			}
			return operationResult;
		} catch (Exception e) {
			Logger.log(e);
			return new OperationResult<DataExportInfo>(e);
		}
	}

	// --------------------------------------------------------------------------------------------//

}
