package org.colorcoding.ibas.importexport.transformers.excel.template;

/**
 * 解析的异常
 * 
 * @author Niuren.Zhu
 *
 */
public class ParsingException extends Exception {

	private static final long serialVersionUID = -3567030339264693945L;

	public ParsingException() {
		super();
	}

	public ParsingException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ParsingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ParsingException(String arg0) {
		super(arg0);
	}

	public ParsingException(Throwable arg0) {
		super(arg0);
	}

}
