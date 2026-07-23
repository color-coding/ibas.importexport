package org.colorcoding.ibas.importexport.repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.function.Consumer;

import org.colorcoding.ibas.bobas.bo.BOFactory;
import org.colorcoding.ibas.bobas.bo.BOUtilities;
import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.bo.IBOStorageTag;
import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.common.ConditionOperation;
import org.colorcoding.ibas.bobas.common.ConditionRelationship;
import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.DateTimes;
import org.colorcoding.ibas.bobas.common.Enums;
import org.colorcoding.ibas.bobas.common.Files;
import org.colorcoding.ibas.bobas.common.ICondition;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.common.Numbers;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.common.Strings;
import org.colorcoding.ibas.bobas.core.fields.IFieldData;
import org.colorcoding.ibas.bobas.core.fields.IManagedFields;
import org.colorcoding.ibas.bobas.data.ArrayList;
import org.colorcoding.ibas.bobas.data.KeyText;
import org.colorcoding.ibas.bobas.data.List;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.db.DbTransaction;
import org.colorcoding.ibas.bobas.file.FileItem;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.message.MessageLevel;
import org.colorcoding.ibas.bobas.organization.OrganizationFactory;
import org.colorcoding.ibas.bobas.ownership.IDataOwnership;
import org.colorcoding.ibas.bobas.repository.BORepositoryServiceApplication;
import org.colorcoding.ibas.bobas.repository.RepositoryException;
import org.colorcoding.ibas.bobas.serialization.ISerializer;
import org.colorcoding.ibas.bobas.serialization.SerializationFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.bo.exportrecord.ExportRecord;
import org.colorcoding.ibas.importexport.bo.exportrecord.IExportRecord;
import org.colorcoding.ibas.importexport.bo.exporttemplate.ExportTemplate;
import org.colorcoding.ibas.importexport.bo.exporttemplate.IExportTemplate;
import org.colorcoding.ibas.importexport.data.DataExportInfo;
import org.colorcoding.ibas.importexport.data.emDataUpdateMethod;
import org.colorcoding.ibas.importexport.repository.updater.DataUpdater;
import org.colorcoding.ibas.importexport.repository.updater.Factory;
import org.colorcoding.ibas.importexport.transformer.FileTransformer;
import org.colorcoding.ibas.importexport.transformer.TemplateTransformer;
import org.colorcoding.ibas.importexport.transformer.Transformer;
import org.colorcoding.ibas.importexport.transformer.TransformerFactory;
import org.colorcoding.ibas.importexport.transformer.TransformerFile;
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
			// 初始化工厂
			initFactory();
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
		try {
			this.setUserToken(token);
			if (data == null || data.getName().indexOf(".") < 0) {
				throw new Exception(I18N.prop("msg_bobas_invalid_data"));
			}
			// 初始化工厂
			initFactory();
			// 创建转换者
			String type = Files.extensionOf(data.getName());
			// 带宏的excel文件，识别为普通问
			if (Strings.endsWith(type, "xlsm", true)) {
				type = "xlsx";
			}
			type = String.format(FileTransformer.GROUP_TEMPLATE, type).toUpperCase();
			FileTransformer transformer = TransformerFactory.create().create(type);
			// 更新时个别管理字段状态
			if (updateMethod == emDataUpdateMethod.MODIFY) {
				transformer.setIndividualStatus(true);
			}
			Logger.log(MessageLevel.DEBUG, MSG_TRANSFORMER_IMPORT_DATA, transformer.getClass().getName());
			// 转换文件数据到业务对象
			transformer.setInputData(Files.valueOf(data.getPath()));
			transformer.transform();
			OperationResult<String> operationResult = new OperationResult<String>();
			// 返回存储事务标记
			operationResult.addInformations("IDENTIFY_DATA_COUNT", String.valueOf(transformer.getOutputData().size()),
					"DATA_IMPORT");
			if (this.getTransaction() != null) {
				// 整体保存时，记录事务ID
				operationResult.addInformations("REPOSITORY_TRANSACTION_ID", this.getTransaction().getId(),
						"DATA_IMPORT");
			}
			List<IBusinessObject> outputDatas = transformer.getOutputData();
			transformer = null;
			type = null;
			// 保存业务对象
			IBOStorageTag tag;
			ICriteria criteria;
			IBusinessObject newItem;
			IDataOwnership ownership;
			DataUpdater dataUpdater;
			StringBuilder stringBuilder;
			IOperationResult<IBusinessObject> opRsltExists, opRsltDelete, opRsltSave;

			for (int i = 0; i < outputDatas.size(); i++) {
				try {
					newItem = outputDatas.get(i);
					// 调试模式，输出识别对象
					if (MyConfiguration.isDebugMode()) {
						stringBuilder = new StringBuilder();
						stringBuilder.append("transformer:");
						stringBuilder.append(" ");
						stringBuilder.append("be imported data");
						stringBuilder.append(System.getProperty("line.seperator", "\n"));
						stringBuilder.append(BOUtilities.toXmlString(newItem));
						Logger.log(MessageLevel.DEBUG, stringBuilder.toString());
						stringBuilder = null;
					}
					// 导入的数据，源标记为I
					if (newItem instanceof IBOStorageTag) {
						tag = (IBOStorageTag) newItem;
						tag.setDataSource(MyConfiguration.SIGN_DATA_SOURCE);
						tag = null;
					}
					// 设置数据所有者
					if (newItem instanceof IDataOwnership) {
						ownership = (IDataOwnership) newItem;
						if (ownership.getDataOwner() == null || ownership.getDataOwner() == 0) {
							ownership.setDataOwner(this.getCurrentUser().getId());
						}
						if (ownership.getOrganization() == null || ownership.getOrganization().isEmpty()) {
							ownership.setOrganization(this.getCurrentUser().getBelong());
						}
						ownership = null;
					}
					boolean myTrans = this.beginTransaction();
					try {
						// 处理已存在的对象实例
						criteria = newItem.getCriteria();
						dataUpdater = Factory.create(updateMethod);
						if (criteria == null && newItem instanceof IManagedFields
								&& updateMethod == emDataUpdateMethod.MODIFY) {
							// 尝试使用主键检索
							criteria = new Criteria();
							IManagedFields fields = (IManagedFields) newItem;
							for (IFieldData field : fields.getFields(c -> c.isPrimaryKey())) {
								ICondition condition = criteria.getConditions().create();
								condition.setAlias(field.getName());
								condition.setValue(field.getValue());
							}
							// 无有效条件，则查询失效
							if (criteria.getConditions().isEmpty()) {
								criteria = null;
							} else {
								// 不使用唯一键
								dataUpdater.setUniqueKeyMode(false);
							}
						}
						if (criteria != null && !criteria.getConditions().isEmpty()) {
							// 是否需要查询子项
							if (!dataUpdater.isRequiredChilds(newItem)) {
								criteria.setNoChilds(true);
							}
							opRsltExists = this.fetch(newItem.getClass(), criteria, token);
							if (!opRsltExists.getResultObjects().isEmpty()) {
								newItem = dataUpdater.apply(newItem, opRsltExists.getResultObjects());
								for (IBusinessObject oldItem : opRsltExists.getResultObjects()) {
									// 删除旧数据
									if (oldItem.isDeleted() == true) {
										opRsltDelete = this.save(oldItem, token);
										if (opRsltDelete.getError() != null) {
											throw opRsltDelete.getError();
										}
										operationResult.addInformations("DELETED_EXISTS_DATA", oldItem.toString(),
												"DATA_IMPORT");
										opRsltDelete = null;
									}
								}
							} else if (updateMethod == emDataUpdateMethod.MODIFY) {
								// 原有修改，未查到，则跳过
								newItem = null;
							} else {
								// 其他未查到，则新建
								if (newItem.isNew() == false && newItem instanceof BusinessObject) {
									((BusinessObject<?>) newItem).markNew();
								}
							}
							opRsltExists = null;
						}
						if (newItem == null) {
							if (myTrans) {
								this.commitTransaction();
							}
							continue;
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
						criteria = null;
						opRsltSave = null;
						dataUpdater = null;
					} catch (Exception e) {
						operationResult.addInformations("IMPORT_ERROR", e.getMessage(), "DATA_IMPORT");
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
					newItem = null;
					outputDatas.set(i, null);
					// 自行管理的事务，清理缓存释放业务逻辑影响对象的引用
					if (myTrans && this.getTransaction() instanceof DbTransaction) {
						((DbTransaction) this.getTransaction()).clearCache();
					}
				} catch (Exception e) {
					throw new Exception(I18N.prop("msg_ie_input_data_failed", i + 1), e);
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
		try {
			this.setUserToken(token);
			// 获取导出的模板
			Transformer<?, ?> transformer = TransformerFactory.create().create(info.getTransformer());
			if (transformer == null) {
				throw new Exception(I18N.prop("msg_ie_not_found_transformer", info.getTransformer()));
			}
			// 初始化工厂
			initFactory();

			OperationResult<FileItem> operationResult = new OperationResult<FileItem>();
			Logger.log(MessageLevel.DEBUG, MSG_TRANSFORMER_EXPORT_DATA, transformer.getClass().getName());
			if (transformer instanceof TransformerFile) {
				// 查询数据
				ICriteria criteria = info.getCriteria();
				TransformerFile fileTransformer = (TransformerFile) transformer;
				fileTransformer.setWorkFolder(MyConfiguration.getTempFolder());
				if (criteria == null || criteria.getBusinessObject() == null
						|| criteria.getBusinessObject().isEmpty()) {
					throw new Exception(I18N.prop("msg_bobas_invalid_criteria"));
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
				operationResult.addResultObjects(fileItem);
			} else if (transformer instanceof TemplateTransformer) {
				TemplateTransformer templateTransformer = (TemplateTransformer) transformer;
				templateTransformer.setWorkFolder(MyConfiguration.getTempFolder());
				templateTransformer.setTemplate(info.getTemplate());
				templateTransformer.setInputData(info.getContent());
				// 传递参数
				templateTransformer.newParam("UserToken", token); // 用户口令
				templateTransformer.newParam("EmbedImage", true); // 签入图片

				templateTransformer.transform();
				File file = templateTransformer.getOutputData().firstOrDefault();
				if (file == null) {
					throw new Exception(I18N.prop("msg_ie_no_transformed_data"));
				}
				Logger.log(MessageLevel.DEBUG, MSG_TRANSFORMER_EXPORT_DATA_TO_FILE, file.getPath());
				FileItem fileItem = new FileItem();
				fileItem.setName(file.getName());
				fileItem.setPath(file.getPath());
				operationResult.addResultObjects(fileItem);
			}
			for (FileItem item : operationResult.getResultObjects()) {
				Logger.log(MessageLevel.DEBUG, MSG_TRANSFORMER_EXPORT_DATA_TO_FILE, item.getPath());
			}
			return operationResult;
		} catch (Exception e) {
			Logger.log(e);
			return new OperationResult<FileItem>(e);
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
						throw new Exception(I18N.prop("msg_bobas_invalid_condition_operation"));
					}
				}
				if (pCondition != null) {
					emYesNo value = emYesNo.NO;
					if (Numbers.isNumeric(pCondition.getValue())) {
						value = Enums.valueOf(emYesNo.class, Integer.valueOf(pCondition.getValue()));
					} else {
						value = Enums.valueOf(emYesNo.class, pCondition.getValue());
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
						throw new Exception(I18N.prop("msg_bobas_invalid_condition_operation"));
					}
				}
				if (transformer.template()) {
					Transformer<?, ?> instance = TransformerFactory.create().create(transformer.name());
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
	 * 
	 * @param criteria 查询
	 * @param token    口令
	 * @return 操作结果
	 */
	public OperationResult<ExportRecord> fetchExportRecord(ICriteria criteria, String token) {
		return super.fetch(ExportRecord.class, criteria, token);
	}

	/**
	 * 查询-导出日志（提前设置用户口令）
	 * 
	 * @param criteria 查询
	 * @return 操作结果
	 */
	public IOperationResult<IExportRecord> fetchExportRecord(ICriteria criteria) {
		return new OperationResult<IExportRecord>(this.fetchExportRecord(criteria, this.getUserToken()));
	}

	/**
	 * 保存-导出日志
	 * 
	 * @param bo    对象实例
	 * @param token 口令
	 * @return 操作结果
	 */
	public OperationResult<ExportRecord> saveExportRecord(ExportRecord bo, String token) {
		return super.save(bo, token);
	}

	/**
	 * 保存-导出日志（提前设置用户口令）
	 * 
	 * @param bo 对象实例
	 * @return 操作结果
	 */
	public IOperationResult<IExportRecord> saveExportRecord(IExportRecord bo) {
		return new OperationResult<IExportRecord>(this.saveExportRecord((ExportRecord) bo, this.getUserToken()));
	}

	@Override
	public IOperationResult<IExportRecord> writeExportRecord(String boKeys, String cause, String content) {
		return this.writeExportRecord(boKeys, cause, content, this.getUserToken());
	}

	@Override
	public OperationResult<ExportRecord> writeExportRecord(String boKeys, String cause, String content, String token) {
		try (BORepositoryImportExport boRepository = new BORepositoryImportExport()) {
			boRepository.setUserToken(token);
			ICriteria criteria = Criteria.create(boKeys);
			if (criteria == null || Strings.isNullOrEmpty(criteria.getBusinessObject())
					|| criteria.getConditions().isEmpty()) {
				throw new Exception(I18N.prop("msg_ie_invalid_bo_keys"));
			}
			ExportRecord record = new ExportRecord();
			record.setCause(cause);
			record.setBOKeys(boKeys);
			record.setContent(content);
			record.setBOCode(criteria.getBusinessObject());
			record.setExportDate(DateTimes.today());
			record.setExportTime(Short.valueOf(DateTimes.now().toString("HHmm")));
			record.setExportUser(boRepository.getCurrentUser().getId());
			// 获取导出的对象并修改状态
			try {
				boRepository.beginTransaction();
				if ("PRINT".equalsIgnoreCase(cause) && MyConfiguration
						.getConfigValue(MyConfiguration.CONFIG_ITEM_ENABLED_EXPORT_RECORD_TO_PRINTED, true)) {
					// 打印导出，尝试修改单据状态
					initFactory(); // 初始化工厂
					Class<?> boType = BOFactory.classOf(criteria.getBusinessObject());
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
						IOperationResult<IBusinessObject> opRsltFetch = boRepository.fetch(
								(Class<IBusinessObject>) boType, criteria, OrganizationFactory.SYSTEM_USER.getToken());
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
