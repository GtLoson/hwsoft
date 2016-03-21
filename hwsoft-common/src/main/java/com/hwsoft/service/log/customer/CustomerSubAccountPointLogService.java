package com.hwsoft.service.log.customer;


import com.hwsoft.common.point.CustomerSubAccountPointType;
import com.hwsoft.model.log.CustomerSubAccountPointLog;

public interface CustomerSubAccountPointLogService {

	
	public CustomerSubAccountPointLog add(double amount, double availableAmount, double freezonAmount,CustomerSubAccountPointType type,String remark,
			String orderFormId, int customerSubAccountId, int fromCustomerSubAccountId, Integer toCustomerSubAccountId,int realFromCustomerSubAccountId,
			boolean plus,int customerId,Integer productId,Integer staffId,String staffName);
	
}
