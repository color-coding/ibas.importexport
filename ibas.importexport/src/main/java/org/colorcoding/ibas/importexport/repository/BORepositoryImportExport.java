package org.colorcoding.ibas.importexport.repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.function.Consumer;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.bo.IBOMasterData;
import org.colorcoding.ibas.bobas.bo.IBOMasterDataLine;
import org.colorcoding.ibas.bobas.bo.IBOStorageTag;
import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.common.ConditionOperation;
import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.ICondition;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.ISort;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.common.SortType;
import org.colorcoding.ibas.bobas.core.BOFactory;
import org.colorcoding.ibas.bobas.core.IBOFactory;
import org.colorcoding.ibas.bobas.core.ITrackStatusOperator;
import org.colorcoding.ibas.bobas.core.RepositoryException;
import org.colorcoding.ibas.bobas.core.fields.IFieldData;
import org.colorcoding.ibas.bobas.core.fields.IManagedFields;
import org.colorcoding.ibas.bobas.data.FileData;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.bobas.ownership.IDataOwnership;
import org.colorcoding.ibas.bobas.repository.BORepositoryServiceApplication;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializerFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;
import org.colorcoding.ibas.importexport.data.DataExportInfo;
import org.colorcoding.ibas.importexport.data.emDataUpdateMethod;
import org.colorcoding.ibas.importexport.transformer.FileTransformer;
import org.colorcoding.ibas.importexport.transformer.FileTransformerSerialization;
import org.colorcoding.ibas.importexport.transformer.IFileTransformer;
import org.colorcoding.ibas.importexport.transformer.ITemplateTransformer;
import org.colorcoding.ibas.importexport.transformer.ITransformer;
import org.colorcoding.ibas.importexport.transformer.ITransformerFile;
import org.colorcoding.ibas.importexport.transformer.TransformerFactory;
import org.colorcoding.ibas.importexport.transformer.TransformerInfo;

/**
 * ImportExport仓库
 */
public class BORepositoryImportExport extends BORepositoryServiceApplication
		implements IBORepositoryImportExportSvc, IBORepositoryImportExportApp {

	public static final String MSG_TRANSFORMER_IMPORT_DATA = "transformer: using [%s] import data.";
	public static final String MSG_TRANSFORMER_EXPORT_DATA = "transformer: using [%s] export data.";
	public static final String MSG_TRANSFORMER_EXPORT_DATA_TO_FILE = "transformer: export data to file [%s].";

	private volatile static IBOFactory boFactory;

	/**
	 * 业务对象工厂
	 * 
	 * @return
	 */
	public static IBOFactory getBOFactory() {
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
										// 仅注册业务对象
										if (BusinessObject.class.isAssignableFrom(item)) {
											boFactory.register(item);
										}
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
	 * @param criteria 查询
	 * @param token    口令
	 * @return 操作结果
	 */
	public OperationResult<ExportTemplate> fetchExportTemplate(ICriteria criteria, String token) {
		return super.fetch(criteria, token, ExportTemplate.class);
	}

	/**
	 * 查询-数据导出模板（提前设置用户口令）
	 * 
	 * @param criteria 查询
	 * @return 操作结果
	 */
	public IOperationResult<IExportTemplate> fetchExportTemplate(ICriteria criteria) {
		return new OperationResult<IExportTemplate>(this.fetchExportTemplate(criteria, this.getUserToken()));
	}

	/**
	 * 保存-数据导出模板
	 * 
	 * @param bo    对象实例
	 * @param token 口令
	 * @return 操作结果
	 */
	public OperationResult<ExportTemplate> saveExportTemplate(ExportTemplate bo, String token) {
		return super.save(bo, token);
	}

	/**
	 * 保存-数据导出模板（提前设置用户口令）
	 * 
	 * @param bo 对象实例
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
		try (ByteArrayOutputStream writer = new ByteArrayOutputStream()) {
			this.setUserToken(token);
			Class<?> boType = getBOFactory().getClass(boCode);
			if (boType == null) {
				throw new Exception(I18N.prop("msg_ie_not_found_class", boCode));
			}
			ISerializer<?> serializer = SerializerFactory.create().createManager().create(type);
			if (serializer == null) {
				throw new Exception(I18N.prop("msg_ie_not_found_serializer", type));
			}
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
	 * @param data         数据
	 * @param updateMethod 数据更新方式
	 * @param token        口令
	 * @return 操作结果
	 */
	public OperationResult<String> importData(FileData data, emDataUpdateMethod updateMethod, String token) {
		OperationResult<String> opRslt = null;
		try {
			this.setUserToken(token);
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
			IFileTransformer transformer = TransformerFactory.create().create(type);
			if (transformer instanceof FileTransformerSerialization) {
				((FileTransformerSerialization) transformer).setBOFactory(getBOFactory());
			}
			if (updateMethod == emDataUpdateMethod.MODIFY) {
				// 更新时个别管理字段状态
				if (transformer instanceof FileTransformer) {
					((FileTransformer) transformer).setIndividualStatus(true);
				}
			}
			Logger.log(MessageLevel.DEBUG, MSG_TRANSFORMER_IMPORT_DATA, transformer.getClass().getName());
			// 转换文件数据到业务对象
			transformer.setInputData(new File(data.getLocation()));
			transformer.transform();
			boolean myTrans = this.beginTransaction();
			try {
				opRslt = new OperationResult<String>();
				// 返回存储事务标记
				opRslt.addInformations("IDENTIFY_DATA_COUNT", String.valueOf(transformer.getOutputData().size()),
						"DATA_IMPORT");
				opRslt.addInformations("REPOSITORY_TRANSACTION_ID", this.getRepository().getTransactionId(),
						"DATA_IMPORT");
				// 保存业务对象
				IBusinessObject newItem;
				IOperationResult<?> opRsltExists, opRsltDelete, opRsltSave;
				for (int j = 0; j < transformer.getOutputData().size(); j++) {
					newItem = transformer.getOutputData().get(j);
					try {
						// 调试模式，输出识别对象
						if (MyConfiguration.isDebugMode()) {
							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append("transformer:");
							stringBuilder.append(" ");
							stringBuilder.append("be imported data");
							stringBuilder.append(System.getProperty("line.seperator", "\n"));
							stringBuilder.append("  ");
							stringBuilder.append(newItem.toString("xml"));
							Logger.log(MessageLevel.DEBUG, stringBuilder.toString());
						}
						// 导入的数据，源标记为I
						if (newItem instanceof IBOStorageTag) {
							IBOStorageTag tag = (IBOStorageTag) newItem;
							tag.setDataSource(MyConfiguration.SIGN_DATA_SOURCE);
						}
						// 设置数据所有者
						if (newItem instanceof IDataOwnership) {
							IDataOwnership ownership = (IDataOwnership) newItem;
							if (ownership.getDataOwner() == null || ownership.getDataOwner() == 0) {
								ownership.setDataOwner(this.getCurrentUser().getId());
							}
							if (ownership.getOrganization() == null || ownership.getOrganization().isEmpty()) {
								ownership.setOrganization(this.getCurrentUser().getBelong());
							}
						}
						// 判断对象是否存在
						ICriteria criteria = newItem.getCriteria();
						if (criteria != null && !criteria.getConditions().isEmpty()) {
							opRsltExists = this.fetch(criteria, token, newItem.getClass());
							if (!opRsltExists.getResultObjects().isEmpty()) {
								// 已存在数据
								if (updateMethod == emDataUpdateMethod.REPLACE) {
									// 替换数据（旧的删除，保存新的）
									for (Object oldItem : opRsltExists.getResultObjects()) {
										// 删除旧数据
										if (oldItem instanceof IBusinessObject) {
											IBusinessObject boItem = (IBusinessObject) oldItem;
											boItem.delete();
											opRsltDelete = this.save(boItem, token);
											if (opRsltDelete.getError() != null) {
												throw opRsltDelete.getError();
											}
											opRslt.addInformations("DELETED_EXISTS_DATA", boItem.toString(),
													"DATA_IMPORT");
										}
										// 主数据保存
										if (oldItem instanceof IBOMasterData && newItem instanceof IBOMasterData) {
											// 主数据则保留DocEntry值，否则丢失关联关系
											IBOMasterData objMaster = (IBOMasterData) newItem;
											IBOMasterData itemMaster = (IBOMasterData) oldItem;
											// 对象信息复制
											objMaster.setDocEntry(itemMaster.getDocEntry());
											if (objMaster instanceof IBOStorageTag
													&& itemMaster instanceof IBOStorageTag) {
												IBOStorageTag objTag = (IBOStorageTag) objMaster;
												IBOStorageTag itemTag = (IBOStorageTag) itemMaster;
												objTag.setLogInst(itemTag.getLogInst());
												objTag.setCreateActionId(itemTag.getCreateActionId());
												objTag.setCreateDate(itemTag.getCreateDate());
												objTag.setCreateTime(itemTag.getCreateTime());
												objTag.setCreateUserSign(itemTag.getCreateUserSign());
												objTag.setDataSource(itemTag.getDataSource());
											}
											if (objMaster instanceof ITrackStatusOperator) {
												ITrackStatusOperator objOptor = (ITrackStatusOperator) objMaster;
												objOptor.markOld();
												objOptor.markDirty();
											}
										} else if (oldItem instanceof IBOMasterDataLine
												&& newItem instanceof IBOMasterDataLine) {
											// 主数据行保留LineId值，否则丢失关联关系
											IBOMasterDataLine objMaster = (IBOMasterDataLine) newItem;
											IBOMasterDataLine itemMaster = (IBOMasterDataLine) oldItem;
											// 对象信息复制
											objMaster.setLineId(itemMaster.getLineId());
											if (objMaster instanceof IBOStorageTag
													&& itemMaster instanceof IBOStorageTag) {
												IBOStorageTag objTag = (IBOStorageTag) objMaster;
												IBOStorageTag itemTag = (IBOStorageTag) itemMaster;
												objTag.setLogInst(itemTag.getLogInst());
												objTag.setCreateActionId(itemTag.getCreateActionId());
												objTag.setCreateDate(itemTag.getCreateDate());
												objTag.setCreateTime(itemTag.getCreateTime());
												objTag.setCreateUserSign(itemTag.getCreateUserSign());
												objTag.setDataSource(itemTag.getDataSource());
											}
											if (objMaster instanceof ITrackStatusOperator) {
												ITrackStatusOperator objOptor = (ITrackStatusOperator) objMaster;
												objOptor.markOld();
												objOptor.markDirty();
											}
										}
									}
								} else if (updateMethod == emDataUpdateMethod.MODIFY) {
									IFieldData oldField = null;
									IManagedFields newFields = null;
									IManagedFields oldFields = null;
									for (Object oldItem : opRsltExists.getResultObjects()) {
										if (newItem.getClass() != oldItem.getClass()) {
											continue;
										}
										if (!(oldItem instanceof IBusinessObject)) {
											continue;
										}
										if (newItem instanceof IManagedFields) {
											newFields = (IManagedFields) newItem;
											oldFields = (IManagedFields) oldItem;
											for (IFieldData newField : newFields.getFields()) {
												if (!newField.isSavable()) {
													continue;
												}
												oldField = oldFields.getField(newField.getName());
												if (oldField != null) {
													oldField.setValue(newField.getValue());
													if (((IBusinessObject) oldItem).isDirty() == false
															&& oldItem instanceof ITrackStatusOperator) {
														((ITrackStatusOperator) oldItem).markDirty();
													}
												}
											}
										} else {
											continue;
										}
										newItem = (IBusinessObject) oldItem;
									}
									if (oldField == null || newItem.isDirty() == false) {
										// 无效数据
										continue;
									}
								} else {
									// 跳过已存在数据
									continue;
								}
							}
						}
						opRsltSave = this.save(newItem, token);
						if (opRsltSave.getError() != null) {
							throw opRsltSave.getError();
						}
						for (Object item : opRsltSave.getResultObjects()) {
							opRslt.addResultObjects(item.toString());
						}
					} catch (Exception e) {
						throw new Exception(I18N.prop("msg_ie_input_data_faild", j + 1), e);
					}
				}
				opRslt.addInformations("SAVE_DATA_COUNT", String.valueOf(opRslt.getResultObjects().size()),
						"DATA_IMPORT");
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
		return this.importData(data, emDataUpdateMethod.SKIP);
	}

	public IOperationResult<String> importData(FileData data, emDataUpdateMethod updateMethod) {
		return this.importData(data, updateMethod, this.getUserToken());
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
			Logger.log(MessageLevel.DEBUG, MSG_TRANSFORMER_EXPORT_DATA, transformer.getClass().getName());
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
					throw new Exception(I18N.prop("msg_ie_no_transformed_data"));
				}
				Logger.log(MessageLevel.DEBUG, MSG_TRANSFORMER_EXPORT_DATA_TO_FILE, file.getPath());
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
				templateTransformer.setWorkFolder(MyConfiguration.getTempFolder());
				templateTransformer.setTemplate(template);
				templateTransformer.setInputData(info.getContent());
				templateTransformer.transform();
				File file = templateTransformer.getOutputData().firstOrDefault();
				if (file == null) {
					throw new Exception(I18N.prop("msg_ie_no_transformed_data"));
				}
				Logger.log(MessageLevel.DEBUG, MSG_TRANSFORMER_EXPORT_DATA_TO_FILE, file.getPath());
				FileData fileData = new FileData();
				fileData.setFileName(file.getName());
				fileData.setLocation(file.getPath());
				opRslt.addResultObjects(fileData);
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
			for (TransformerInfo transformer : TransformerFactory.create().getTransformers().keySet()) {
				if (tCondition != null) {
					if (tCondition.getOperation() == ConditionOperation.START) {
						if (!transformer.name().startsWith(tCondition.getValue())) {
							continue;
						}
					} else {
						throw new Exception(I18N.prop("msg_bobas_invaild_condition_operation"));
					}
				}
				if (transformer.template()) {
					if (criteria.getBusinessObject() != null && !criteria.getBusinessObject().isEmpty()) {
						ICriteria tpCriteria = new Criteria();
						tpCriteria.setNoChilds(true);
						ICondition condition = tpCriteria.getConditions().create();
						condition.setAlias(ExportTemplate.PROPERTY_ACTIVATED.getName());
						condition.setValue(emYesNo.YES);
						condition = tpCriteria.getConditions().create();
						condition.setAlias(ExportTemplate.PROPERTY_BOCODE.getName());
						condition.setValue(criteria.getBusinessObject());
						ISort sort = tpCriteria.getSorts().create();
						sort.setAlias(ExportTemplate.PROPERTY_OBJECTKEY.getName());
						sort.setSortType(SortType.DESCENDING);
						IOperationResult<IExportTemplate> opRsltET = this.fetchExportTemplate(tpCriteria);
						if (opRsltET.getError() != null) {
							throw opRsltET.getError();
						}
						String name = transformer.name();
						if (name.indexOf("_") > 0) {
							name = name.substring(name.lastIndexOf("_") + 1).toLowerCase();
						}
						for (IExportTemplate template : opRsltET.getResultObjects()) {
							DataExportInfo info = new DataExportInfo();
							info.setTransformer(transformer.name());
							info.setTemplate(String.valueOf(template.getObjectKey()));
							info.setDescription(I18N.prop("msg_ie_exporter_description", name, template.getName(),
									template.getWidth(), template.getHeight()));
							operationResult.addResultObjects(info);
						}
					}
				} else {
					DataExportInfo info = new DataExportInfo();
					info.setTransformer(transformer.name());
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
