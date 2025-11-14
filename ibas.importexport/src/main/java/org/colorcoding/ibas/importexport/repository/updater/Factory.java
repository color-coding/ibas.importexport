package org.colorcoding.ibas.importexport.repository.updater;

import org.colorcoding.ibas.importexport.data.emDataUpdateMethod;

public class Factory {
	private Factory() {
	}

	public static DataUpdater create(emDataUpdateMethod method) {
		if (method == emDataUpdateMethod.REPLACE) {
			return new ReplaceUpdater();
		} else if (method == emDataUpdateMethod.MODIFY) {
			return new ModifyUpdater();
		}
		return new NonUpdater();
	}

}
