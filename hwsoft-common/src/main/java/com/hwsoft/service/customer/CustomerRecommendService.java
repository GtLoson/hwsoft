/**
 * 
 */
package com.hwsoft.service.customer;

import com.hwsoft.model.customer.CustomerRecommend;

/**
 * @author tzh
 *
 */
public interface CustomerRecommendService {

	public CustomerRecommend add(int recommendCustomerId,int recommendedCustomerId);
	
}
