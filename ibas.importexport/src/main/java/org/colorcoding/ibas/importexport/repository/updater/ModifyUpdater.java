package org.colorcoding.ibas.importexport.repository.updater;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.bo.IBusinessObjects;
import org.colorcoding.ibas.bobas.core.fields.IFieldData;
import org.colorcoding.ibas.bobas.core.fields.IManagedFields;
import org.colorcoding.ibas.bobas.data.List;

public class ModifyUpdater extends DataUpdater {

	public ModifyUpdater() {
		this.setUniqueKeyMode(true);
	}

	@Override
	public boolean isRequiredChilds(Object newData) {
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public IBusinessObject apply(IBusinessObject newData, List<IBusinessObject> oldDatas) {
		if (oldDatas == null || newData == null) {
			return null;
		}
		if (!(newData instanceof IManagedFields)) {
			return null;
		}
		IManagedFields newFields = (IManagedFields) newData;
		// 使用唯一键，否则使用主键
		IFieldData[] newKeys = this.isUniqueKeyMode() ? newFields.getFields(c -> c.isUniqueKey())
				: newFields.getFields(c -> c.isPrimaryKey());
		if (newKeys == null || newKeys.length == 0) {
			// 无检索键，无法比较，退出
			return null;
		}
		// 开始匹配
		boolean matched;
		IFieldData oldField = null;
		IFieldData newField = null;
		IManagedFields oldFields = null;
		for (IBusinessObject oldData : oldDatas) {
			if (oldData == null) {
				continue;
			}
			if (newData.getClass() != oldData.getClass()) {
				continue;
			}
			oldFields = (IManagedFields) oldData;
			// 找匹配的数据
			matched = true;
			for (IFieldData newKey : newKeys) {
				oldField = oldFields.getField(newKey.getName());
				if (oldField != null) {
					if (newKey.getValue() == oldField.getValue()) {
						continue;
					}
					if (String.valueOf(newKey.getValue()).equals(String.valueOf(oldField.getValue()))) {
						continue;
					}
				}
				matched = false;
				break;
			}
			if (matched == false) {
				continue;
			}
			// 同步主键和唯一键
			for (IFieldData item : oldFields.getFields(c -> c.isPrimaryKey() || c.isUniqueKey())) {
				newField = newFields.getField(item.getName());
				if (newField == null) {
					continue;
				}
				newField.setValue(item.getValue());
			}
			// 同步修改过的值
			for (IFieldData item : newFields.getFields()) {
				if (item.isPrimaryKey()) {
					continue;
				}
				if (item.isUniqueKey()) {
					continue;
				}
				if (item.getValue() instanceof IBusinessObjects) {
					// 是数组，则子项比较
					oldField = oldFields.getField(item.getName());
					if (oldField == null || !(oldField.getValue() instanceof IBusinessObjects)) {
						continue;
					}
					for (IBusinessObject newItem : ((IBusinessObjects<?, ?>) item.getValue())) {
						if (this.apply(newItem, ((List<IBusinessObject>) oldField.getValue())) == null) {
							/*
							 * // 子项未匹配到 if (this.isUniqueKeyMode()) { // 唯一键模式，为找到对象，则尝试使用主键方式
							 * this.setUniqueKeyMode(false); if (this.apply(newItem,
							 * ((List<IBusinessObject>) oldField.getValue())) != null) { // 主键匹配到，则下一条
							 * continue; } this.setUniqueKeyMode(true); }
							 */
							// 都没有匹配到，则添加
							((IBusinessObjects<IBusinessObject, ?>) oldField.getValue()).add(newItem);
						}
					}
				} else {
					if (!item.isDirty()) {
						continue;
					}
					// 替换原值
					oldField = oldFields.getField(item.getName());
					if (oldField != null) {
						oldField.setValue(item.getValue());
					}
				}
			}
			// 同步标记信息
			this.tagsOf(newData, oldData);
			// 返回找到的数据
			return oldData;
		}
		return null;
	}

}
