package org.colorcoding.ibas.importexport.repository.updater;

import org.colorcoding.ibas.bobas.bo.IBusinessObject;
import org.colorcoding.ibas.bobas.data.List;

public class NonUpdater extends DataUpdater {

	@Override
	public IBusinessObject apply(IBusinessObject newData, List<IBusinessObject> oldDatas) {
		return null;
	}

}
