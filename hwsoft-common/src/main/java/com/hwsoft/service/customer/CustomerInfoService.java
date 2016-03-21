/**
 * 
 */
package com.hwsoft.service.customer;

import com.hwsoft.model.customer.CustomerInfo;

/**
 * @author tzh
 *
 */
public interface CustomerInfoService {

	public CustomerInfo add(CustomerInfo customerInfo);
	
	public CustomerInfo update(CustomerInfo customerInfo);
	
	public CustomerInfo findByUserId(final int userId);
	
}
