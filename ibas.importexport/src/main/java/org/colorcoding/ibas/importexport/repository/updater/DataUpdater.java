package org.colorcoding.ibas.importexport.repository.updater;

import java.util.function.BiFunction;

import org.colorcoding.ibas.bobas.bo.IBOStorageTag;
import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.data.List;

public abstract class DataUpdater implements BiFunction<IBusinessObject, List<IBusinessObject>, IBusinessObject> {

	public void tagsOf(Object target, Object source) {
		if (!(target instanceof IBOStorageTag)) {
			return;
		}
		if (!(source instanceof IBOStorageTag)) {
			return;
		}
		IBOStorageTag sourceTag = (IBOStorageTag) source;
		IBOStorageTag targetTag = (IBOStorageTag) target;
		targetTag.setLogInst(sourceTag.getLogInst());
		targetTag.setCreateActionId(sourceTag.getCreateActionId());
		targetTag.setCreateDate(sourceTag.getCreateDate());
		targetTag.setCreateTime(sourceTag.getCreateTime());
		targetTag.setCreateUserSign(sourceTag.getCreateUserSign());
		targetTag.setDataSource(sourceTag.getDataSource());
	}

	public abstract IBusinessObject apply(IBusinessObject newData, List<IBusinessObject> oldDatas);
}
