package com.hwsoft.exception.user;

import com.hwsoft.exception.BaseException;

public class WithdrawalException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 36540844535199312L;

	public WithdrawalException() {
		super();
	}

	public WithdrawalException(String message, Throwable cause) {
		super(message, cause);
	}

	public WithdrawalException(String message) {
		super(message);
	}

	public WithdrawalException(Throwable cause) {
		super(cause);
	}

}
