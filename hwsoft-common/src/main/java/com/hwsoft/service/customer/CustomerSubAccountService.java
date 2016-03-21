/**
 * 
 */
package com.hwsoft.service.customer;

import com.hwsoft.model.customer.CustomerSubAccount;

/**
 * @author tzh
 *
 */
public interface CustomerSubAccountService {

	public CustomerSubAccount addCustomerSubAccount(int customerId,String memberId,int productChannelId);
	
//	public CustomerSubAccount findByCustomerSubAccountByUserIdAndProductChannelType(int customerId,ProductChannelType productChannelType);
	
	public CustomerSubAccount findByCustomerSubAccountByUserIdAndProductChannelId(int customerId,int productChannelId);
	
	
	public CustomerSubAccount findById(int customerSubAccountId);
}
