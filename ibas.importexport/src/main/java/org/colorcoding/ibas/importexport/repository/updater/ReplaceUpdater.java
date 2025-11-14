package org.colorcoding.ibas.importexport.repository.updater;

import org.colorcoding.ibas.bobas.bo.BusinessObject;
import org.colorcoding.ibas.bobas.bo.IBOMasterData;
import org.colorcoding.ibas.bobas.bo.IBOMasterDataLine;
import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.bo.IBusinessObjects;
import org.colorcoding.ibas.bobas.core.IPropertyInfo;
import org.colorcoding.ibas.bobas.data.List;

public class ReplaceUpdater extends DataUpdater {

	@Override
	public IBusinessObject apply(IBusinessObject newData, List<IBusinessObject> oldDatas) {
		// 替换数据（旧的删除，保存新的）
		for (IBusinessObject oldData : oldDatas) {
			// 主数据类型，保留核心字段
			if (oldData instanceof IBOMasterData && newData instanceof IBOMasterData) {
				// 主数据则保留DocEntry值，否则丢失关联关系
				IBOMasterData newMaster = (IBOMasterData) newData;
				IBOMasterData oldMaster = (IBOMasterData) oldData;
				// 对象信息复制
				newMaster.setDocEntry(oldMaster.getDocEntry());
				this.tagsOf(newMaster, oldMaster);
				this.fillsOf(newData, oldData);
			} else if (oldData instanceof IBOMasterDataLine && newData instanceof IBOMasterDataLine) {
				// 主数据行保留LineId值，否则丢失关联关系
				IBOMasterDataLine newMaster = (IBOMasterDataLine) newData;
				IBOMasterDataLine oldMaster = (IBOMasterDataLine) oldData;
				// 对象信息复制
				newMaster.setLineId(oldMaster.getLineId());
				this.tagsOf(newMaster, oldMaster);
				this.fillsOf(newData, oldData);
			} else {
				// 删除旧数据
				oldData.delete();
			}
		}
		return newData;
	}

	protected void fillsOf(IBusinessObject target, IBusinessObject source) {
		if (!(target instanceof BusinessObject)) {
			return;
		}
		BusinessObject<?> tarData = (BusinessObject<?>) target;
		tarData.markOld();
		tarData.markDirty();
		if (!(source instanceof BusinessObject)) {
			return;
		}
		BusinessObject<?> souData = (BusinessObject<?>) source;
		// 复制原子项，使之随新项保存而删除
		IBusinessObjects<IBusinessObject, ?> tarList, souList;
		for (IPropertyInfo<?> property : souData.properties()) {
			if (IBusinessObject.class.isAssignableFrom(property.getValueType())) {
				tarData.setProperty(property, souData.getProperty(property));
			} else if (IBusinessObjects.class.isAssignableFrom(property.getValueType())) {
				tarList = tarData.getProperty(property);
				souList = souData.getProperty(property);
				for (IBusinessObject item : souList) {
					tarList.add(item);
					item.delete();
				}
			}
		}

	}

}
