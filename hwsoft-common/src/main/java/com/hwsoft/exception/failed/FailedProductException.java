package com.hwsoft.exception.failed;

import com.hwsoft.exception.BaseException;

public class FailedProductException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2751042778535719875L;

	public FailedProductException() {
		super();
	}

	public FailedProductException(String message, Throwable cause) {
		super(message, cause);
	}

	public FailedProductException(String message) {
		super(message);
	}

	public FailedProductException(Throwable cause) {
		super(cause);
	}

}
