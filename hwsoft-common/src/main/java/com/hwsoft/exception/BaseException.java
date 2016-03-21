/**
 * 
 */
package com.hwsoft.exception;

/**
 * @author tzh
 *
 */
public class BaseException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5774875451141220570L;

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

}
