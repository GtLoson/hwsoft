/**
 * 
 */
package com.hwsoft.spring;

import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author tzh
 *
 */
public class MessageSource extends ReloadableResourceBundleMessageSource {
	
	public final String getMessage(String code){
		return super.getMessage(code, null, Locale.getDefault());
	}
}
