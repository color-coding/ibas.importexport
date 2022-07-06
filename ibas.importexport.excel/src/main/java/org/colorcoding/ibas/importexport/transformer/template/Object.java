package org.colorcoding.ibas.importexport.transformer.template;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.colorcoding.ibas.bobas.approval.IApprovalData;
import org.colorcoding.ibas.bobas.bo.IBODocument;
import org.colorcoding.ibas.bobas.bo.IBODocumentLine;
import org.colorcoding.ibas.bobas.bo.IBOMasterData;
import org.colorcoding.ibas.bobas.bo.IBOMasterDataLine;
import org.colorcoding.ibas.bobas.bo.IBOSeriesKey;
import org.colorcoding.ibas.bobas.bo.IBOSimple;
import org.colorcoding.ibas.bobas.bo.IBOSimpleLine;
import org.colorcoding.ibas.bobas.bo.IBOStorageTag;
import org.colorcoding.ibas.bobas.bo.IBOTagCanceled;
import org.colorcoding.ibas.bobas.bo.IBOTagDeleted;
import org.colorcoding.ibas.bobas.bo.IBOTagReferenced;
import org.colorcoding.ibas.bobas.bo.IBOUserFields;
import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.bo.IBusinessObjects;
import org.colorcoding.ibas.bobas.bo.IUserField;
import org.colorcoding.ibas.bobas.bo.UserField;
import org.colorcoding.ibas.bobas.bo.UserFieldManager;
import org.colorcoding.ibas.bobas.common.ConditionOperation;
import org.colorcoding.ibas.bobas.common.Criteria;
import org.colorcoding.ibas.bobas.common.IChildCriteria;
import org.colorcoding.ibas.bobas.common.ICondition;
import org.colorcoding.ibas.bobas.common.OperationResult;
import org.colorcoding.ibas.bobas.core.BOFactory;
import org.colorcoding.ibas.bobas.core.fields.IFieldData;
import org.colorcoding.ibas.bobas.core.fields.IManagedFields;
import org.colorcoding.ibas.bobas.data.emYesNo;
import org.colorcoding.ibas.bobas.mapping.DbFieldType;
import org.colorcoding.ibas.bobas.message.Logger;
import org.colorcoding.ibas.bobas.organization.OrganizationFactory;
import org.colorcoding.ibas.bobas.ownership.IDataOwnership;
import org.colorcoding.ibas.initialfantasy.bo.boinformation.BOInformation;
import org.colorcoding.ibas.initialfantasy.bo.boinformation.BOPropertyInformation;
import org.colorcoding.ibas.initialfantasy.bo.boinformation.IBOPropertyInformation;
import org.colorcoding.ibas.initialfantasy.repository.BORepositoryInitialFantasy;

/**
 * 模板-对象区，类
 * 
 * @author Niuren.Zhu
 *
 */
public class Object extends BindingArea<Template> {

	public static final int OBJECT_STARTING_ROW = 1;
	public static final int OBJECT_STARTING_COLUMN = 0;

	public Object() {
		this.setStartingRow(OBJECT_STARTING_ROW);
		this.setEndingRow(this.getStartingRow());
		this.setStartingColumn(OBJECT_STARTING_COLUMN);
		this.setEndingColumn(AREA_AUTO_REGION);
	}

	@Override
	public int getIndex() {
		if (this.getParent() instanceof Template) {
			Template parent = (Template) this.getParent();
			for (int i = 0; i < parent.getObjects().length; i++) {
				if (parent.getObjects()[i] == this) {
					return i;
				}
			}
		}
		return -1;
	}

	private Property[] properties;

	public final Property[] getProperties() {
		if (this.properties == null) {
			this.properties = new Property[] {};
		}
		return properties;
	}

	final void setProperties(Property[] properties) {
		this.properties = properties;
	}

	/**
	 * 解析对象，形成模板
	 * 
	 * @param bo    待解析对象
	 * @param skips 跳过不解析的属性
	 * @throws ResolvingException 无法识别异常
	 */
	public final void resolving(IBusinessObject bo, String[] skips) throws ResolvingException {
		this.setBindingClass(bo.getClass());
		List<Property> properties = new ArrayList<>();
		IManagedFields fields = (IManagedFields) bo;
		for (IFieldData field : fields.getFields()) {
			// 不保存的对象不导出
			if (!field.isSavable()) {
				continue;
			}
			// 对象不再处理
			if (IBusinessObjects.class.isInstance(field.getValue())) {
				continue;
			}
			// 对象不再处理
			if (IBusinessObject.class.isInstance(field.getValue())) {
				continue;
			}
			// 跳过不解析的属性
			if (skips != null) {
				boolean done = false;
				for (String property : skips) {
					if (field.getName().equals(property)) {
						done = true;
						break;
					}
				}
				if (done) {
					continue;
				}
			}
			// 过滤属性
			if (bo.isNew()) {
				// 新建对象
				Function<Class<?>, Boolean> isSkip = new Function<Class<?>, Boolean>() {
					@Override
					public Boolean apply(Class<?> t) {
						try {
							if (t.getDeclaredMethod("get" + field.getName()) != null) {
								return true;
							}
						} catch (SecurityException | NoSuchMethodException e) {
						}
						return false;
					}
				};
				if (isSkip.apply(IBOStorageTag.class)) {
					// 存储标记跳过
					continue;
				}
				if (isSkip.apply(IBOTagCanceled.class)) {
					// 取消标记跳过
					continue;
				}
				if (isSkip.apply(IBOTagDeleted.class)) {
					// 删除标记跳过
					continue;
				}
				if (isSkip.apply(IBOTagReferenced.class)) {
					// 引用标记跳过
					continue;
				}
				if (isSkip.apply(IBOSeriesKey.class)) {
					// 编号服务跳过
					continue;
				}
				if (isSkip.apply(IApprovalData.class)) {
					// 审批标记跳过
					continue;
				}
				if (isSkip.apply(IDataOwnership.class)) {
					// 数据所有者跳过
					continue;
				}
				if (field.getName().equals(IBOSimple.MASTER_PRIMARY_KEY_NAME)) {
					continue;
				}
				if (field.getName().equals(IBOSimpleLine.SECONDARY_PRIMARY_KEY_NAME)) {
					continue;
				}
				if (field.getName().equals(IBODocument.MASTER_PRIMARY_KEY_NAME)) {
					continue;
				}
				if (field.getName().equals(IBODocumentLine.SECONDARY_PRIMARY_KEY_NAME)) {
					continue;
				}
				if (field.getName().equals(IBOMasterData.SERIAL_NUMBER_KEY_NAME)) {
					continue;
				}
				if (field.getName().equals(IBOMasterDataLine.SECONDARY_PRIMARY_KEY_NAME)) {
					continue;
				}
			}
			Property property = new Property();
			property.setParent(this);
			property.setName(field.getName());
			property.setBindingClass(field.getValueType());
			property.setStartingRow(property.getParent().getEndingRow() + 1);
			property.setEndingRow(property.getStartingRow());
			property.setStartingColumn(property.getParent().getStartingColumn() + properties.size());
			property.setEndingColumn(property.getStartingColumn());
			properties.add(property);
		}
		if (bo.isNew() && bo instanceof IBOUserFields) {
			// 新建时，注册自定义字段
			String boCode = BOFactory.create().getCode(this.getBindingClass());
			if (boCode == null) {
				boCode = BOFactory.create().getCode(this.getParent().getHead().getBindingClass());
			}
			if (boCode != null && !boCode.isEmpty()) {
				Criteria criteria = new Criteria();
				ICondition condition = criteria.getConditions().create();
				condition.setAlias(BOInformation.PROPERTY_CODE.getName());
				condition.setOperation(ConditionOperation.START);
				condition.setValue(boCode);
				condition = criteria.getConditions().create();
				condition.setAlias(BOInformation.PROPERTY_NAME.getName());
				condition.setValue(bo.getClass().getSimpleName());
				IChildCriteria childCriteria = criteria.getChildCriterias().create();
				childCriteria.setPropertyPath(BOInformation.PROPERTY_BOPROPERTYINFORMATIONS.getName());
				childCriteria.setOnlyHasChilds(true);
				condition = childCriteria.getConditions().create();
				condition.setAlias(BOPropertyInformation.PROPERTY_SYSTEMED.getName());
				condition.setOperation(ConditionOperation.EQUAL);
				condition.setValue(emYesNo.NO);
				BORepositoryInitialFantasy boRepository = new BORepositoryInitialFantasy();
				OperationResult<BOInformation> opRslt = boRepository.fetchBOInformation(criteria,
						OrganizationFactory.SYSTEM_USER.getToken());
				Boolean done;
				IBOUserFields boFields = (IBOUserFields) bo;
				for (BOInformation boItem : opRslt.getResultObjects()) {
					for (IBOPropertyInformation ptyItem : boItem.getBOPropertyInformations()) {
						if (ptyItem.getPropertyName() == null) {
							continue;
						}
						if (!ptyItem.getPropertyName().startsWith(UserField.USER_FIELD_PREFIX_SIGN)) {
							continue;
						}

						if (boFields.getUserFields() != null) {
							done = false;
							for (IUserField userField : boFields.getUserFields()) {
								if (userField.getName().equals(ptyItem.getPropertyName())) {
									done = true;
									break;
								}
							}
							if (done) {
								continue;
							}
						}
						try {
							Property property = new Property();
							property.setParent(this);
							property.setName(ptyItem.getPropertyName());
							property.setBindingClass(
									UserFieldManager.getFieldType(DbFieldType.valueOf(ptyItem.getDataType(), true)));
							property.setStartingRow(property.getParent().getEndingRow() + 1);
							property.setEndingRow(property.getStartingRow());
							property.setStartingColumn(property.getParent().getStartingColumn() + properties.size());
							property.setEndingColumn(property.getStartingColumn());
							properties.add(property);
						} catch (Exception e) {
							Logger.log(e);
						}
					}
				}
			}
		}
		this.setProperties(properties.toArray(new Property[] {}));
	}

	@Override
	public String toString() {
		return String.format("{object: %s}", super.toString());
	}
}
