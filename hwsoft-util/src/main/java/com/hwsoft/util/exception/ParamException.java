/**
 * 
 */
package com.hwsoft.util.exception;

/**
 * @author tzh
 *
 */
public class ParamException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 15448296425614746L;

	public ParamException() {
		super();
	}

	public ParamException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParamException(String message) {
		super(message);
	}

	public ParamException(Throwable cause) {
		super(cause);
	}

}
