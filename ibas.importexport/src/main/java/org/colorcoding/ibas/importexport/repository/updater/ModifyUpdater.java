package org.colorcoding.ibas.importexport.repository.updater;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.bo.IBusinessObjects;
import org.colorcoding.ibas.bobas.core.fields.IFieldData;
import org.colorcoding.ibas.bobas.core.fields.IManagedFields;
import org.colorcoding.ibas.bobas.data.List;

public class ModifyUpdater extends DataUpdater {

	@SuppressWarnings("unchecked")
	@Override
	public IBusinessObject apply(IBusinessObject newData, List<IBusinessObject> oldDatas) {
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
		for (IBusinessObject oldData : oldDatas) {
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
					if (String.valueOf(item.getValue()).equals(String.valueOf(oldField.getValue()))) {
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
				this.tagsOf(newFields, oldFields);

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
						if (oldField != null && oldField.getValue() instanceof IBusinessObjects<?, ?>) {
							for (IBusinessObject newItem : ((IBusinessObjects<?, ?>) item.getValue())) {
								if (this.apply(newItem, ((List<IBusinessObject>) oldField.getValue())) == null) {
									// 子项未匹配到，则添加
									((IBusinessObjects<IBusinessObject, ?>) oldField.getValue()).add(newItem);
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

}
