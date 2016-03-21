/**
 * 
 */
package com.hwsoft.exception.appversion;

import com.hwsoft.exception.BaseException;

/**
 * 应用版本异常
 */
public class AppVersionException extends BaseException{

	/**
	 *
	 */
	private static final long serialVersionUID = 2963046185115012229L;

	public AppVersionException() {
		super();
	}

	public AppVersionException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppVersionException(String message) {
		super(message);
	}

	public AppVersionException(Throwable cause) {
		super(cause);
	}

}
