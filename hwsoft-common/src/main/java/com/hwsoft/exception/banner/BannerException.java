/**
 * 
 */
package com.hwsoft.exception.banner;

import com.hwsoft.exception.BaseException;

/**
 * banner异常
 */
public class BannerException extends BaseException{

	/**
	 *
	 */
	private static final long serialVersionUID = 2963046185115012229L;

	public BannerException() {
		super();
	}

	public BannerException(String message, Throwable cause) {
		super(message, cause);
	}

	public BannerException(String message) {
		super(message);
	}

	public BannerException(Throwable cause) {
		super(cause);
	}

}
