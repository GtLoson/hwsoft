/**
 * 
 */
package com.hwsoft.exception.product;

import com.hwsoft.exception.BaseException;

/**
 * @author tzh
 *
 */
public class ProductException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4016537961935578857L;

	public ProductException() {
		super();
	}

	public ProductException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductException(String message) {
		super(message);
	}

	public ProductException(Throwable cause) {
		super(cause);
	}

}
