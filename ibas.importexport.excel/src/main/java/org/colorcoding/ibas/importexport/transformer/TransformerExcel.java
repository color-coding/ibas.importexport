package org.colorcoding.ibas.importexport.transformer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.colorcoding.ibas.bobas.bo.BusinessObjectUnit;
import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.DateTimes;
import org.colorcoding.ibas.bobas.common.ICondition;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.i18n.I18N;
import org.colorcoding.ibas.bobas.organization.InvalidAuthorizationException;
import org.colorcoding.ibas.bobas.organization.OrganizationFactory;
import org.colorcoding.ibas.importexport.MyConfiguration;
import org.colorcoding.ibas.importexport.transformer.template.ExcelWriter;
import org.colorcoding.ibas.importexport.transformer.template.Property;
import org.colorcoding.ibas.importexport.transformer.template.ResolvingException;
import org.colorcoding.ibas.importexport.transformer.template.Template;
import org.colorcoding.ibas.importexport.transformer.template.WriteFileException;
import org.colorcoding.ibas.initialfantasy.bo.boinformation.BOInformation;
import org.colorcoding.ibas.initialfantasy.bo.boinformation.IBOInformation;
import org.colorcoding.ibas.initialfantasy.bo.boinformation.IBOPropertyInformation;
import org.colorcoding.ibas.initialfantasy.repository.BORepositoryInitialFantasyShell;

/**
 * 业务对象转换xlsx文件
 *
 * 调用顺序约束： 1. {@link #addInputData(IBusinessObject)} 可多次调用，添加待转换的业务对象 2.
 * {@link #transform()} 可多次调用（批量模式），每次处理当前输入数据并写入工作簿 3. {@link #getOutputData()}
 * 在全部 transform 完成后调用，将工作簿写入文件并返回结果
 *
 * @author Niuren.Zhu
 *
 */
@TransformerInfo(name = "TO_FILE_XLSX")
public class TransformerExcel extends TransformerFile {

	public final static String TYPE_NAME = "xlsx";
	public final static String PROPERTY_DATATYPE_ALPHANUMERIC = "Alphanumeric";

	/** 模板对象，首次转换时初始化，后续不变 */
	private Template template;
	/** 写入者，首次转换时初始化 */
	private ExcelWriter writer;

	@Override
	public void transform() throws TransformException {
		if (this.getInputData() == null || this.getInputData().isEmpty()) {
			return;
		}
		try {
			// 首次调用时初始化模板及写入者
			if (this.template == null) {
				this.template = new Template();
				this.template.setIndividualStatus(true);
			}
			// 解析输入数据
			for (IBusinessObject bo : this.getInputData()) {
				this.template.resolving(bo);
			}
			// 清理已处理的输入数据，释放内存
			this.getInputData().clear();
			if (this.writer == null) {
				// 首次：描述模板并开始写入（创建工作簿、写表头）
				this.describing(this.template);
				this.writer = new ExcelWriter();
				this.writer.setTemplate(this.template);
				this.writer.beginWrite();
			}
			// 写入当前批次的数据行
			this.writer.writeDatas();
			// 清理模板数据行，释放内存（保留结构）
			this.template.clearDatas();
		} catch (ResolvingException | WriteFileException | InvalidAuthorizationException e) {
			this.cleanup();
			throw new TransformException(e);
		}
	}

	@Override
	public List<File> getOutputData() throws TransformException {
		if (this.writer != null) {
			// 终极步骤：写入文件并关闭
			try {
				File file = new File(this.getWorkFolder() + File.separator + this.template.getName() + "_"
						+ DateTimes.now().getTime() + "." + TYPE_NAME);
				this.writer.endWrite(file);
				this.setOutputData(new File[] { file });
			} catch (WriteFileException | IOException e) {
				throw new TransformException(e);
			} finally {
				this.cleanup();
			}
		}
		return super.getOutputData();
	}

	/**
	 * 清理过程数据，释放内存及磁盘临时文件
	 */
	private void cleanup() {
		if (this.writer != null) {
			// 安全释放工作簿资源（关闭文件、清理临时文件）
			this.writer.dispose();
			this.writer = null;
		}
		this.template = null;
	}

	/**
	 * 描述模板
	 *
	 * @param template
	 * @throws InvalidAuthorizationException
	 * @throws Exception
	 */
	protected void describing(Template template) throws InvalidAuthorizationException {
		if (template == null) {
			return;
		}
		String language = I18N.getInstance().getLanguageCode();
		if (language != null && !language.startsWith("zh")) {
			// 非中文语言，使用英文
			return;
		}
		ICriteria criteria = null;
		IOperationResult<IBOInformation> opRslt = null;
		try (BORepositoryInitialFantasyShell boRepository = new BORepositoryInitialFantasyShell()) {
			boRepository.setUserToken(OrganizationFactory.SYSTEM_USER.getToken());
			// 描述表头
			criteria = new Criteria();
			ICondition condition = criteria.getConditions().create();
			condition.setAlias(BOInformation.PROPERTY_CODE.getName());
			if (template.getHead().getCode() != null && !template.getHead().getCode().isEmpty()) {
				// 查编码
				condition.setValue(template.getHead().getCode());
			} else {
				// 查名称
				condition.setValue(template.getHead().getName());
			}
			opRslt = boRepository.fetchBOInformation(criteria);
			IBOInformation masterInfo = opRslt.getResultObjects().firstOrDefault();
			if (masterInfo == null) {
				// 未找到对象描述
				return;
			}
			template.setDescription(masterInfo.getDescription());
			template.getHead().setDescription(masterInfo.getDescription());
			// 描述对象方法
			Consumer<IBOInformation> describingObject = new Consumer<IBOInformation>() {

				@Override
				public void accept(IBOInformation t) {
					if (t == null) {
						return;
					}
					for (org.colorcoding.ibas.importexport.transformer.template.Object object : template.getObjects()) {
						if (object.getName().equals(t.getName())) {
							object.setDescription(t.getDescription());
							for (Property property : object.getProperties()) {
								IBOPropertyInformation itemInfo = t.getBOPropertyInformations()
										.firstOrDefault(c -> c.getPropertyName().equals(property.getName()));
								if (itemInfo == null) {
									itemInfo = t.getBOPropertyInformations()
											.firstOrDefault(c -> c.getMapped().equals(property.getName()));
								}
								if (itemInfo != null) {
									property.setDescription(itemInfo.getDescription());
									if (PROPERTY_DATATYPE_ALPHANUMERIC.equalsIgnoreCase(itemInfo.getDataType())) {
										property.setDescription(String.format("%s (%d)", property.getDescription(),
												itemInfo.getEditSize()));
									}
								}
							}
						} else if (object.getName().startsWith(t.getName())) {
							String name = object.getName().substring(t.getName().length() + 1)
									.replace(Template.PROPERTY_PATH_LIST_SIGN, "");
							IBOPropertyInformation itemInfo = t.getBOPropertyInformations()
									.firstOrDefault(c -> c.getPropertyName().equals(name));
							if (itemInfo != null) {
								// 对象定义的属性
								ICriteria criteria = new Criteria();
								ICondition condition = criteria.getConditions().create();
								condition.setAlias(BOInformation.PROPERTY_CODE.getName());
								condition.setValue(itemInfo.getMapped());
								IBOInformation childInfo = boRepository.fetchBOInformation(criteria).getResultObjects()
										.firstOrDefault();
								if (childInfo != null) {
									childInfo.setName(object.getName());
									this.accept(childInfo);
								}
							} else {
								// 对象没有定义的，按类名称查询
								ICriteria criteria = new Criteria();
								ICondition condition = criteria.getConditions().create();
								try {
									String code = object.getBindingClass().getAnnotation(BusinessObjectUnit.class)
											.code();
									condition.setAlias(BOInformation.PROPERTY_CODE.getName());
									condition.setValue(MyConfiguration.applyVariables(code));
								} catch (Exception e) {
									condition.setAlias(BOInformation.PROPERTY_NAME.getName());
									condition.setValue(object.getBindingClass().getSimpleName());
								}
								IBOInformation childInfo = boRepository.fetchBOInformation(criteria).getResultObjects()
										.firstOrDefault();
								if (childInfo != null) {
									childInfo.setName(object.getName());
									this.accept(childInfo);
								}
							}
						}
					}

				}
			};
			describingObject.accept(masterInfo);
		}
	}

}
