/**
 * 
 */
package com.hwsoft.exception.user;

import com.hwsoft.exception.BaseException;

/**
 * @author tzh
 *
 */
public class CustomerException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7733675079468852939L;

	public CustomerException() {
		super();
	}

	public CustomerException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomerException(String message) {
		super(message);
	}

	public CustomerException(Throwable cause) {
		super(cause);
	}

}
