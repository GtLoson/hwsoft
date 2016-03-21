/**
 * 
 */
package com.hwsoft.exception.appmessage;

import com.hwsoft.exception.BaseException;

/**
 * 应用消息异常
 */
public class AppMessageException extends BaseException{

	/**
	 *
	 */
	private static final long serialVersionUID = 2963046185115012229L;

	public AppMessageException() {
		super();
	}

	public AppMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppMessageException(String message) {
		super(message);
	}

	public AppMessageException(Throwable cause) {
		super(cause);
	}

}
