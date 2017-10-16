package org.colorcoding.ibas.importexport.transformers.excel.template;

/**
 * 不能识别的异常
 * 
 * @author Niuren.Zhu
 *
 */
public class NotRecognizedException extends Exception {

	private static final long serialVersionUID = -3567030339264693945L;

	public NotRecognizedException() {
		super();
	}

	public NotRecognizedException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public NotRecognizedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotRecognizedException(String arg0) {
		super(arg0);
	}

	public NotRecognizedException(Throwable arg0) {
		super(arg0);
	}

}
