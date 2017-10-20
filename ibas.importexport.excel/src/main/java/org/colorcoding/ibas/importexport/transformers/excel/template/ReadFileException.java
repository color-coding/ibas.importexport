package org.colorcoding.ibas.importexport.transformers.excel.template;

public class ReadFileException extends Exception {

	private static final long serialVersionUID = -3567030339264693945L;

	public ReadFileException() {
		super();
	}

	public ReadFileException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ReadFileException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ReadFileException(String arg0) {
		super(arg0);
	}

	public ReadFileException(Throwable arg0) {
		super(arg0);
	}

}
