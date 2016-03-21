/**
 * 
 */
package com.hwsoft.service.point;

import java.util.List;

import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.model.point.CustomerSubAccountPoint;

/**
 * @author tzh
 *
 */
public interface CustomerSubAccountPointService {

	public CustomerSubAccountPoint addCustomerSubAccountPoint(CustomerSubAccountPoint customerSubAccountPoint);
	
	
	public CustomerSubAccountPoint findCustomerSubAccountPointByCustomerSubAccountId(int customerSubAccountId);
	
	
	public CustomerSubAccountPoint update(CustomerSubAccountPoint customerSubAccountPoint);
	
	/**
	 * 系统账户转账
	 * @param fromCustomerId
	 * @param toCustomerId
	 * @param productChannelType
	 * @return
	 */
	public CustomerSubAccountPoint systemTransfer(int fromCustomerId,int toCustomerId,ProductChannelType productChannelType,double amount,String notes,int staffId,String staffName);
	
	
	/**
	 * 系统账户转账
	 * @param fromCustomerId
	 * @param toCustomerId
	 * @param productChannelType
	 * @return
	 */
	public CustomerSubAccountPoint systemReceive(int fromCustomerId,int toCustomerId,ProductChannelType productChannelType,double amount,String notes,int staffId,String staffName);
	
	/**
	 * 系统现金奖励
	 * @param fromCustomerId
	 * @param toCustomerId
	 * @param productChannelType
	 * @return
	 */
	public CustomerSubAccountPoint systemRewardBatch(int fromCustomerId,String mobile,ProductChannelType productChannelType,double amount,String notes,int staffId,String staffName);
}
