package org.colorcoding.ibas.importexport.repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.colorcoding.ibas.bobas.bo.BOFactory;
import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.bo.IBOMasterData;
import org.colorcoding.ibas.bobas.bo.IBOMasterDataLine;
import org.colorcoding.ibas.bobas.bo.IBOStorageTag;
import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.bo.IBusinessObjects;
import org.colorcoding.ibas.bobas.common.ConditionOperation;
import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.ICondition;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.ISort;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.common.SortType;
import org.colorcoding.ibas.bobas.common.Strings;
import org.colorcoding.ibas.bobas.core.fields.IFieldData;
import org.colorcoding.ibas.bobas.core.fields.IManagedFields;
import org.colorcoding.ibas.bobas.data.FileItem;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.bobas.ownership.IDataOwnership;
import org.colorcoding.ibas.bobas.repository.BORepositoryServiceApplication;
import org.colorcoding.ibas.bobas.repository.RepositoryException;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializationFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;
import org.colorcoding.ibas.importexport.data.DataConvert;
import org.colorcoding.ibas.importexport.data.DataExportInfo;
import org.colorcoding.ibas.importexport.data.emDataUpdateMethod;
import org.colorcoding.ibas.importexport.transformer.FileTransformer;
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
	public static final String MSG_TRANSFORMER_IMPORT_DATA_SIZE = "transformer: got data count [%s].";
	public static final String MSG_TRANSFORMER_EXPORT_DATA = "transformer: using [%s] export data.";
	public static final String MSG_TRANSFORMER_EXPORT_DATA_TO_FILE = "transformer: export data to file [%s].";

	private volatile static boolean INITIALIZED;

	/**
	 * 业务对象工厂
	 * 
	 * @return
	 */
	public static boolean initFactory() {
		if (INITIALIZED == false) {
			synchronized (BORepositoryImportExport.class) {
				if (INITIALIZED == false) {
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
									for (Class<?> item : BOFactory.loadClasses(namesapce)) {
										// 仅注册业务对象
										if (BusinessObject.class.isAssignableFrom(item)) {
											BOFactory.register(item);
										}
									}
								}
							}
						}
					};
					classLoader.accept("org.colorcoding.ibas");
					classLoader.accept(MyConfiguration.getConfigValue(MyConfiguration.CONFIG_ITEM_SCANING_PACKAGES));
					INITIALIZED = true;
				}
			}
		}
		return INITIALIZED;
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
		return super.fetch(ExportTemplate.class, criteria, token);
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
		try (ByteArrayOutputStream writer = new ByteArrayOutputStream()) {
			this.setUserToken(token);
			Class<?> boType = BOFactory.classOf(boCode);
			if (boType == null) {
				throw new Exception(I18N.prop("msg_ie_not_found_class", boCode));
			}
			ISerializer serializer = SerializationFactory.createManager().create(type);
			if (serializer == null) {
				throw new Exception(I18N.prop("msg_ie_not_found_serializer", type));
			}
			serializer.schema(boType, writer);
			return new OperationResult<String>().addResultObjects(new String(writer.toByteArray(), "utf-8"));
		} catch (Exception e) {
			Logger.log(e);
			return new OperationResult<String>(e);
		}
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
	public OperationResult<String> importData(FileItem data, emDataUpdateMethod updateMethod, String token) {
		OperationResult<String> opRslt = null;
		try {
			this.setUserToken(token);
			if (data == null || data.getName().indexOf(".") < 0) {
				throw new Exception(I18N.prop("msg_bobas_invalid_data"));
			}
			// 初始化工厂
			initFactory();
			// 创建转换者
			String type = data.getName().substring(data.getName().lastIndexOf(".") + 1);
			if (type != null && type.equalsIgnoreCase("xlsm")) {
				// 带宏的excel文件，识别为普通问
				type = "xlsx";
			}
			type = String.format(FileTransformer.GROUP_TEMPLATE, type).toUpperCase();
			IFileTransformer transformer = TransformerFactory.create().create(type);
			if (updateMethod == emDataUpdateMethod.MODIFY) {
				// 更新时个别管理字段状态
				if (transformer instanceof FileTransformer) {
					((FileTransformer) transformer).setIndividualStatus(true);
				}
			}
			Logger.log(MessageLevel.DEBUG, MSG_TRANSFORMER_IMPORT_DATA, transformer.getClass().getName());
			// 转换文件数据到业务对象
			transformer.setInputData(new File(data.getPath()));
			transformer.transform();
			Logger.log(MessageLevel.DEBUG, MSG_TRANSFORMER_IMPORT_DATA_SIZE, transformer.getOutputData().size());
			boolean myTrans = this.beginTransaction();
			try {
				opRslt = new OperationResult<String>();
				// 返回存储事务标记
				opRslt.addInformations("IDENTIFY_DATA_COUNT", String.valueOf(transformer.getOutputData().size()),
						"DATA_IMPORT");
				opRslt.addInformations("REPOSITORY_TRANSACTION_ID", this.getTransaction().getId(), "DATA_IMPORT");
				// 保存业务对象
				IBusinessObject newItem;
				BusinessObject<?> newOne;
				IOperationResult<IBusinessObject> opRsltExists, opRsltDelete, opRsltSave;
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
							stringBuilder.append(Strings.toXmlString(newItem));
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
							opRsltExists = this.fetch(newItem.getClass(), criteria, token);
							if (!opRsltExists.getResultObjects().isEmpty()) {
								// 已存在数据
								if (updateMethod == emDataUpdateMethod.REPLACE) {
									// 替换数据（旧的删除，保存新的）
									for (IBusinessObject oldItem : opRsltExists.getResultObjects()) {
										// 主数据保存
										if (oldItem instanceof IBOMasterData && newItem instanceof IBOMasterData) {
											// 主数据则保留DocEntry值，否则丢失关联关系
											IBOMasterData newMaster = (IBOMasterData) newItem;
											IBOMasterData oldMaster = (IBOMasterData) oldItem;
											// 对象信息复制
											newMaster.setDocEntry(oldMaster.getDocEntry());
											DataConvert.tagsOf(newMaster, oldMaster);
											if (newMaster instanceof BusinessObject<?>) {
												newOne = (BusinessObject<?>) newMaster;
												newOne.markOld();
												newOne.markDirty();
											}
										} else if (oldItem instanceof IBOMasterDataLine
												&& newItem instanceof IBOMasterDataLine) {
											// 主数据行保留LineId值，否则丢失关联关系
											IBOMasterDataLine newMaster = (IBOMasterDataLine) newItem;
											IBOMasterDataLine oldMaster = (IBOMasterDataLine) oldItem;
											// 对象信息复制
											newMaster.setLineId(oldMaster.getLineId());
											DataConvert.tagsOf(newMaster, oldMaster);
											if (newMaster instanceof BusinessObject<?>) {
												newOne = (BusinessObject<?>) newMaster;
												newOne.markOld();
												newOne.markDirty();
											}
										} else {
											// 删除旧数据
											oldItem.delete();
											opRsltDelete = this.save(oldItem, token);
											if (opRsltDelete.getError() != null) {
												throw opRsltDelete.getError();
											}
											opRslt.addInformations("DELETED_EXISTS_DATA", oldItem.toString(),
													"DATA_IMPORT");
										}
									}
								} else if (updateMethod == emDataUpdateMethod.MODIFY) {
									BiFunction<Object, Object[], Object> funcModify = new BiFunction<Object, Object[], Object>() {

										@Override
										@SuppressWarnings("unchecked")
										public Object apply(Object newData, Object[] oldDatas) {
											if (oldDatas == null || newData == null) {
												return null;
											}
											if (!(newData instanceof IManagedFields)) {
												return null;
											}
											IManagedFields newFields = (IManagedFields) newData;
											IFieldData[] newKeys = newFields.getFields(c -> c.isUniqueKey());
											if (newKeys == null || newKeys.length == 0) {
												// 无唯一键，无法比较，退出
												return null;
											}
											// 开始匹配
											boolean matched;
											IFieldData oldField = null;
											IFieldData newField = null;
											IManagedFields oldFields = null;
											for (Object oldData : oldDatas) {
												if (oldData == null) {
													continue;
												}
												if (newData.getClass() != oldData.getClass()) {
													continue;
												}
												matched = true;
												oldFields = (IManagedFields) oldData;
												for (IFieldData item : newKeys) {
													oldField = oldFields.getField(item.getName());
													if (oldField != null) {
														if (item.getValue() == oldField.getValue()) {
															continue;
														}
														if (String.valueOf(item.getValue())
																.equals(String.valueOf(oldField.getValue()))) {
															continue;
														}
													}
													matched = false;
													break;
												}
												// 找匹配的数据
												if (matched) {
													// 同步主键
													for (IFieldData item : oldFields.getFields(c -> c.isPrimaryKey())) {
														newField = newFields.getField(item.getName());
														if (newField != null) {
															newField.setValue(item.getValue());
														}
													}
													DataConvert.tagsOf(newFields, oldFields);

													for (IFieldData item : newFields.getFields()) {
														if (!item.isSavable()) {
															continue;
														}
														if (item.isPrimaryKey()) {
															continue;
														}
														if (item.isUniqueKey()) {
															continue;
														}
														if (!item.isDirty()) {
															continue;
														}
														if (item.getValue() instanceof IBusinessObjects) {
															// 是数组，则子项比较
															oldField = oldFields.getField(item.getName());
															if (oldField != null && oldField
																	.getValue() instanceof IBusinessObjects<?, ?>) {
																for (IBusinessObject newItem : ((IBusinessObjects<?, ?>) item
																		.getValue())) {
																	if (this.apply(newItem,
																			((IBusinessObjects<?, ?>) oldField
																					.getValue()).toArray()) == null) {
																		// 子项未匹配到，则添加
																		((IBusinessObjects<IBusinessObject, ?>) oldField
																				.getValue()).add(newItem);
																	}
																}
															}
															continue;
														}
														// 替换原值
														oldField = oldFields.getField(item.getName());
														if (oldField != null) {
															oldField.setValue(item.getValue());
														}
													}
													return oldData;
												}
											}
											return null;
										}
									};
									Object oldItem = funcModify.apply(newItem,
											opRsltExists.getResultObjects().toArray());
									if (oldItem instanceof IBusinessObject) {
										// 存在旧数据，替换
										newItem = (IBusinessObject) oldItem;
									}
								} else {
									// 跳过已存在数据
									continue;
								}
							} else {
								// 未查到，则新建
								if (newItem instanceof BusinessObject) {
									((BusinessObject<?>) newItem).markNew();
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

	public IOperationResult<String> importData(FileItem data) {
		return this.importData(data, emDataUpdateMethod.SKIP);
	}

	public IOperationResult<String> importData(FileItem data, emDataUpdateMethod updateMethod) {
		return this.importData(data, updateMethod, this.getUserToken());
	}

	// --------------------------------------------------------------------------------------------//
	@Override
	public IOperationResult<FileItem> exportData(DataExportInfo info) {
		return this.exportData(info, this.getUserToken());
	}

	@Override
	public OperationResult<FileItem> exportData(DataExportInfo info, String token) {
		OperationResult<FileItem> opRslt = new OperationResult<FileItem>();
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
				Class<?> boType = BOFactory.classOf(criteria.getBusinessObject());
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
					IOperationResult<IBusinessObject> opRsltFetch = this.fetch(boType, criteria, token);
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
				FileItem fileItem = new FileItem();
				fileItem.setName(file.getName());
				fileItem.setPath(file.getPath());
				opRslt.addResultObjects(fileItem);
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
				FileItem fileItem = new FileItem();
				fileItem.setName(file.getName());
				fileItem.setPath(file.getPath());
				opRslt.addResultObjects(fileItem);
			}
		} catch (Exception e) {
			opRslt = new OperationResult<FileItem>(e);
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
