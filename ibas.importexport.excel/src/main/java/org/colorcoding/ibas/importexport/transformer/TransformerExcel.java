package org.colorcoding.ibas.importexport.transformer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.ICondition;
import org.colorcoding.ibas.bobas.common.ICriteria;
import org.colorcoding.ibas.bobas.common.IOperationResult;
import org.colorcoding.ibas.bobas.data.DateTime;
import org.colorcoding.ibas.bobas.mapping.BOCode;
import org.colorcoding.ibas.bobas.organization.OrganizationFactory;
import org.colorcoding.ibas.bobas.repository.InvalidTokenException;
import org.colorcoding.ibas.importexport.MyConfiguration;
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
 * @author Niuren.Zhu
 *
 */
@TransformerInfo("TO_FILE_XLSX")
public class TransformerExcel extends TransformerFile {

	public final static String TYPE_NAME = "xlsx";

	@Override
	public void transform() throws TransformException {
		if (this.getInputData() == null) {
			return;
		}
		try {
			Template template = new Template();
			// 解析输入数据
			for (IBusinessObject bo : this.getInputData()) {
				template.resolving(bo);
			}
			// 更新模板描述
			this.describing(template);
			// 输出文件
			File file = new File(this.getWorkFolder() + File.separator + template.getName() + "_"
					+ DateTime.getNow().getTime() + "." + TYPE_NAME);
			if (!file.getParentFile().exists()) {
				file.mkdirs();
			}
			template.write(file);
			this.setOutputData(new ArrayList<>());
			this.getOutputData().add(file);
		} catch (ResolvingException | InvalidTokenException | WriteFileException | IOException e) {
			throw new TransformException(e);
		}
	}

	/**
	 * 描述模板
	 * 
	 * @param template
	 * @throws InvalidTokenException
	 * @throws Exception
	 */
	protected void describing(Template template) throws InvalidTokenException {
		if (template == null) {
			return;
		}
		ICriteria criteria = null;
		IOperationResult<IBOInformation> opRslt = null;
		BORepositoryInitialFantasyShell boRepository = new BORepositoryInitialFantasyShell();
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
							if (itemInfo != null) {
								property.setDescription(itemInfo.getDescription());
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
								String boCode = object.getBindingClass().getAnnotation(BOCode.class).value();
								condition.setAlias(BOInformation.PROPERTY_CODE.getName());
								condition.setValue(MyConfiguration.applyVariables(boCode));
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
