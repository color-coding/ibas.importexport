package org.colorcoding.ibas.importexport.repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.bo.IBODocument;
import org.colorcoding.ibas.bobas.bo.IBOLine;
import org.colorcoding.ibas.bobas.bo.IBOMasterData;
import org.colorcoding.ibas.bobas.bo.IBOMasterDataLine;
import org.colorcoding.ibas.bobas.bo.IBOSimple;
import org.colorcoding.ibas.bobas.bo.IBOStorageTag;
import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.common.ConditionOperation;
import org.colorcoding.ibas.bobas.common.ConditionRelationship;
import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.ICondition;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.core.BOFactory;
import org.colorcoding.ibas.bobas.core.IBOFactory;
import org.colorcoding.ibas.bobas.core.IBusinessObjectBase;
import org.colorcoding.ibas.bobas.core.IBusinessObjectsBase;
import org.colorcoding.ibas.bobas.core.ITrackStatusOperator;
import org.colorcoding.ibas.bobas.core.RepositoryException;
import org.colorcoding.ibas.bobas.core.fields.IFieldData;
import org.colorcoding.ibas.bobas.core.fields.IManagedFields;
import org.colorcoding.ibas.bobas.data.ArrayList;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.data.FileData;
import org.colorcoding.ibas.bobas.data.KeyText;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.bobas.organization.OrganizationFactory;
import org.colorcoding.ibas.bobas.ownership.IDataOwnership;
import org.colorcoding.ibas.bobas.repository.BORepositoryServiceApplication;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializerFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exportrecord.ExportRecord;
import org.colorcoding.ibas.importexport.bo.exportrecord.IExportRecord;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;
import org.colorcoding.ibas.importexport.data.DataConvert;
import org.colorcoding.ibas.importexport.data.DataExportInfo;
import org.colorcoding.ibas.importexport.data.emDataUpdateMethod;
import org.colorcoding.ibas.importexport.transformer.FileTransformer;
import org.colorcoding.ibas.importexport.transformer.FileTransformerSerialization;
import org.colorcoding.ibas.importexport.transformer.IFileTransformer;
import org.colorcoding.ibas.importexport.transformer.ITemplateTransformer;
import org.colorcoding.ibas.importexport.transformer.ITransformer;
import org.colorcoding.ibas.importexport.transformer.ITransformerFile;
import org.colorcoding.ibas.importexport.transformer.TemplateTransformer;
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
			OperationResult<String> operationResult = new OperationResult<String>();
			// 返回存储事务标记
			operationResult.addInformations("IDENTIFY_DATA_COUNT", String.valueOf(transformer.getOutputData().size()),
					"DATA_IMPORT");
			operationResult.addInformations("REPOSITORY_TRANSACTION_ID", this.getRepository().getTransactionId(),
					"DATA_IMPORT");
			// 保存业务对象
			IBusinessObject newItem;
			IOperationResult<?> opRsltExists, opRsltDelete, opRsltSave;
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
						for (IFieldData keyField : newKeys) {
							oldField = oldFields.getField(keyField.getName());
							if (oldField != null) {
								if (keyField.getValue() == oldField.getValue()) {
									continue;
								}
								if (String.valueOf(keyField.getValue()).equals(String.valueOf(oldField.getValue()))) {
									continue;
								}
							}
							matched = false;
							break;
						}
						// 找匹配的数据
						if (matched) {
							// 同步主键
							if (newFields instanceof IBOMasterData) {
								((IBOMasterData) newFields).setDocEntry(((IBOMasterData) oldFields).getDocEntry());
							} else if (newFields instanceof IBODocument) {
								((IBODocument) newFields).setDocEntry(((IBODocument) oldFields).getDocEntry());
							} else if (newFields instanceof IBOSimple) {
								((IBOSimple) newFields).setObjectKey(((IBOSimple) oldFields).getObjectKey());
							} else if (newFields instanceof IBOLine) {
								((IBOLine) newFields).setLineId(((IBOLine) oldFields).getLineId());
							}
							for (IFieldData newField : newFields.getFields()) {
								if (newField.getValue() == null) {
									continue;
								}
								if (newField.getValue() instanceof IBusinessObjectsBase<?>) {
									// 是数组，则子项比较
									oldField = oldFields.getField(newField.getName());
									if (oldField != null && oldField.getValue() instanceof IBusinessObjectsBase<?>) {
										for (IBusinessObjectBase newItem : ((IBusinessObjectsBase<?>) newField
												.getValue())) {
											if (this.apply(newItem, ((IBusinessObjectsBase<?>) oldField.getValue())
													.toArray()) == null) {
												// 子项未匹配到，则添加
												((IBusinessObjectsBase<IBusinessObjectBase>) oldField.getValue())
														.add(newItem);
											}
										}
									}
									continue;
								} else if (!newField.isSavable()) {
									// 非保存退出
									continue;
								}
								// 替换原值
								oldField = oldFields.getField(newField.getName());
								if (oldField != null) {
									oldField.setValue(newField.getValue());
									if (((IBusinessObject) oldData).isDirty() == false
											&& oldData instanceof ITrackStatusOperator) {
										((ITrackStatusOperator) oldData).markDirty();
									}
								}
							}
							return oldData;
						}
					}
					return null;
				}
			};
			for (int i = 0; i < transformer.getOutputData().size(); i++) {
				newItem = transformer.getOutputData().get(i);
				try {
					// 调试模式，输出识别对象
					if (MyConfiguration.isDebugMode()) {
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append("transformer:");
						stringBuilder.append(" ");
						stringBuilder.append("be imported data");
						stringBuilder.append(System.getProperty("line.seperator", "\n"));
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
					boolean myTrans = this.beginTransaction();
					try {
						// 处理已存在的对象实例
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
											operationResult.addInformations("DELETED_EXISTS_DATA", boItem.toString(),
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
							}
						}
						// 保存新对象实例
						opRsltSave = this.save(newItem, token);
						if (opRsltSave.getError() != null) {
							throw opRsltSave.getError();
						}
						for (Object item : opRsltSave.getResultObjects()) {
							operationResult.addResultObjects(item.toString());
						}
						if (myTrans) {
							this.commitTransaction();
						}
					} catch (Exception e) {
						operationResult.addResultObjects(e.getMessage());
						if (myTrans) {
							try {
								this.rollbackTransaction();
							} catch (RepositoryException e1) {
								Logger.log(e1);
							}
							continue;
						}
						throw e;
					}
				} catch (Exception e) {
					throw new Exception(I18N.prop("msg_ie_input_data_faild", i + 1), e);
				}
			}
			operationResult.addInformations("SAVE_DATA_COUNT",
					String.valueOf(operationResult.getResultObjects().size()), "DATA_IMPORT");
			return operationResult;
		} catch (Exception e) {
			Logger.log(e);
			return new OperationResult<String>(e);
		}
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
		try {
			this.setUserToken(token);
			// 获取导出的模板
			ITransformer<?, ?> transformer = TransformerFactory.create().create(info.getTransformer());
			if (transformer == null) {
				throw new Exception(I18N.prop("msg_ie_not_found_transformer", info.getTransformer()));
			}
			OperationResult<FileData> operationResult = new OperationResult<FileData>();
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
				FileData fileData = new FileData();
				fileData.setFileName(file.getName());
				fileData.setLocation(file.getPath());
				operationResult.addResultObjects(fileData);
			} else if (transformer instanceof ITemplateTransformer) {
				ITemplateTransformer templateTransformer = (ITemplateTransformer) transformer;
				templateTransformer.setWorkFolder(MyConfiguration.getTempFolder());
				templateTransformer.setTemplate(info.getTemplate());
				templateTransformer.setInputData(info.getContent());
				templateTransformer.transform();
				File file = templateTransformer.getOutputData().firstOrDefault();
				if (file == null) {
					throw new Exception(I18N.prop("msg_ie_no_transformed_data"));
				}
				FileData fileData = new FileData();
				fileData.setFileName(file.getName());
				fileData.setLocation(file.getPath());
				operationResult.addResultObjects(fileData);
			}
			for (FileData item : operationResult.getResultObjects()) {
				Logger.log(MessageLevel.DEBUG, MSG_TRANSFORMER_EXPORT_DATA_TO_FILE, item.getLocation());
			}
			return operationResult;
		} catch (Exception e) {
			Logger.log(e);
			return new OperationResult<FileData>(e);
		}
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
			ICondition nCondition = criteria.getConditions()
					.firstOrDefault(c -> c.getAlias().equalsIgnoreCase(DataExportInfo.CONDITION_ALIAS_TRANSFORMER));
			ICondition pCondition = criteria.getConditions()
					.firstOrDefault(c -> c.getAlias().equalsIgnoreCase(DataExportInfo.CONDITION_ALIAS_PRINTABLE));
			OperationResult<DataExportInfo> operationResult = new OperationResult<>();
			for (TransformerInfo transformer : TransformerFactory.create().getTransformers().keySet()) {
				if (nCondition != null) {
					if (nCondition.getOperation() == ConditionOperation.START) {
						if (!transformer.name().startsWith(nCondition.getValue())) {
							continue;
						}
					} else if (nCondition.getOperation() == ConditionOperation.EQUAL) {
						if (!transformer.name().equals(nCondition.getValue())) {
							continue;
						}
					} else {
						throw new Exception(I18N.prop("msg_bobas_invaild_condition_operation"));
					}
				}
				if (pCondition != null) {
					emYesNo value = emYesNo.NO;
					if (DataConvert.isNumeric(pCondition.getValue())) {
						value = emYesNo.valueOf(Integer.valueOf(pCondition.getValue()));
					} else {
						value = emYesNo.valueOf(pCondition.getValue());
					}
					if (pCondition.getOperation() == ConditionOperation.EQUAL) {
						if (value == emYesNo.YES) {
							if (!transformer.printable()) {
								continue;
							}
						} else {
							if (transformer.printable()) {
								continue;
							}
						}
					} else {
						throw new Exception(I18N.prop("msg_bobas_invaild_condition_operation"));
					}
				}
				if (transformer.template()) {
					ITransformer<?, ?> instance = TransformerFactory.create().create(transformer.name());
					if (instance instanceof TemplateTransformer) {
						TemplateTransformer templateTransformer = (TemplateTransformer) instance;
						String name = transformer.name();
						if (name.indexOf("_") > 0) {
							name = name.substring(name.lastIndexOf("_") + 1).toLowerCase();
						}
						for (KeyText template : templateTransformer.matchingTemplates(criteria.getBusinessObject())) {
							DataExportInfo info = new DataExportInfo();
							info.setTransformer(transformer.name());
							info.setContentType(transformer.contentType());
							info.setTemplate(String.valueOf(template.getKey()));
							info.setDescription(I18N.prop("msg_ie_exporter_description", name, template.getText()));
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
	/**
	 * 查询-导出日志
	 * @param criteria 查询
	 * @param token 口令
	 * @return 操作结果
	 */
	public OperationResult<ExportRecord> fetchExportRecord(ICriteria criteria, String token) {
		return super.fetch(criteria, token, ExportRecord.class);
	}

	/**
	 * 查询-导出日志（提前设置用户口令）
	 * @param criteria 查询
	 * @return 操作结果
	 */
	public IOperationResult<IExportRecord> fetchExportRecord(ICriteria criteria) {
		return new OperationResult<IExportRecord>(this.fetchExportRecord(criteria, this.getUserToken()));
	}

	/**
	 * 保存-导出日志
	 * @param bo 对象实例
	 * @param token 口令
	 * @return 操作结果
	 */
	public OperationResult<ExportRecord> saveExportRecord(ExportRecord bo, String token) {
		return super.save(bo, token);
	}

	/**
	 * 保存-导出日志（提前设置用户口令）
	 * @param bo 对象实例
	 * @return 操作结果
	 */
	public IOperationResult<IExportRecord> saveExportRecord(IExportRecord bo) {
		return new OperationResult<IExportRecord>(this.saveExportRecord((ExportRecord) bo, this.getUserToken()));
	}

	@Override
	public IOperationResult<IExportRecord> writeExportRecord(String boKeys, String cause, String content) {
		return this.writeExportRecord(boKeys, cause, content);
	}

	@Override
	public OperationResult<ExportRecord> writeExportRecord(String boKeys, String cause, String content, String token) {
		try {
			this.setUserToken(token);
			ICriteria criteria = Criteria.create(boKeys);
			if (criteria == null || DataConvert.isNullOrEmpty(criteria.getBusinessObject())
					|| criteria.getConditions().isEmpty()) {
				throw new Exception(I18N.prop("msg_ie_invaild_bo_keys"));
			}
			ExportRecord record = new ExportRecord();
			record.setCause(cause);
			record.setBOKeys(boKeys);
			record.setContent(content);
			record.setBOCode(criteria.getBusinessObject());
			record.setExportDate(DateTime.getToday());
			record.setExportTime(Short.valueOf(DateTime.getNow().toString("HHmm")));
			record.setExportUser(this.getCurrentUser().getId());
			// 获取导出的对象并修改状态

			BORepositoryImportExport boRepository = new BORepositoryImportExport();
			try {
				boRepository.beginTransaction();
				if ("PRINT".equalsIgnoreCase(cause) && MyConfiguration
						.getConfigValue(MyConfiguration.CONFIG_ITEM_ENABLED_EXPORT_RECORD_TO_PRINTED, true)) {
					// 打印导出，尝试修改单据状态
					Class<?> boType = getBOFactory().getClass(criteria.getBusinessObject());
					if (boType == null || !IBusinessObject.class.isAssignableFrom(boType)) {
						throw new Exception(I18N.prop("msg_ie_not_found_class", criteria.getBusinessObject()));
					}
					ArrayList<Class<?>> interfaces = new ArrayList<>(Arrays.asList(boType.getInterfaces()));
					if (interfaces.firstOrDefault(
							c -> "IDOCUMENTPRINTEDOPERATOR".equalsIgnoreCase(c.getSimpleName())) != null) {
						criteria.setResultCount(1);
						ICondition condition = criteria.getConditions().create();
						condition.setBracketOpen(1);
						condition.setAlias("Printed");
						condition.setValue(emYesNo.NO);
						condition = criteria.getConditions().create();
						condition.setBracketClose(1);
						condition.setAlias("Printed");
						condition.setOperation(ConditionOperation.IS_NULL);
						condition.setRelationship(ConditionRelationship.OR);

						@SuppressWarnings("unchecked")
						IOperationResult<IBusinessObject> opRsltFetch = boRepository.fetch(criteria,
								OrganizationFactory.SYSTEM_USER.getToken(), (Class<IBusinessObject>) boType);
						if (opRsltFetch.getError() != null) {
							throw opRsltFetch.getError();
						}
						IOperationResult<IBusinessObject> opRsltSave;
						for (IBusinessObject item : opRsltFetch.getResultObjects()) {
							if (item instanceof BusinessObject) {
								IFieldData fieldData = ((BusinessObject<?>) item).getField("Printed");
								if (fieldData != null && fieldData.getValueType() == emYesNo.class) {
									if (fieldData.getValue() != emYesNo.YES) {
										fieldData.setValue(emYesNo.YES);
										if (!item.isDirty()) {
											((BusinessObject<?>) item).markDirty();
										}
										opRsltSave = boRepository.save(item,
												OrganizationFactory.SYSTEM_USER.getToken());
										if (opRsltSave.getError() != null) {
											throw opRsltSave.getError();
										}
										if (item instanceof IBOStorageTag) {
											record.setBOInst(((IBOStorageTag) item).getLogInst());
										}
									}
								}
							}
						}
					}
				}
				OperationResult<ExportRecord> opRsltRecord = boRepository.saveExportRecord(record, token);
				if (boRepository.inTransaction()) {
					boRepository.commitTransaction();
				}
				return opRsltRecord;
			} catch (Exception e) {
				if (boRepository.inTransaction()) {
					boRepository.rollbackTransaction();
				}
				throw e;
			}
		} catch (Exception e) {
			return new OperationResult<>(e);
		}
	}

	// --------------------------------------------------------------------------------------------//

}
