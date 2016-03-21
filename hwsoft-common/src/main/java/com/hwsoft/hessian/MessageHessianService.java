/**
 * 
 */
package com.hwsoft.hessian;

/**
 * @author tzh
 *
 */
public interface MessageHessianService {
	public boolean notifyMobileSender(String content, String receiver,
			int level, String sendType);
}
