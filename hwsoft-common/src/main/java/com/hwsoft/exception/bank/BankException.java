/**
 * 
 */
package com.hwsoft.exception.bank;

import com.hwsoft.exception.BaseException;


/**
 * @author tzh
 *
 */
public class BankException extends BaseException {

	/**
	 *
	 */
	private static final long serialVersionUID = -1049120816398230953L;

	public BankException() {
		super();
	}

	public BankException(String message, Throwable cause) {
		super(message, cause);
	}

	public BankException(String message) {
		super(message);
	}

	public BankException(Throwable cause) {
		super(cause);
	}


}
