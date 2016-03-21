package com.hwsoft.service.order.hwsoft;

import com.hwsoft.model.order.hwsoft.WithdrawalOrder;

public interface WithdrawalOrderService {

	public WithdrawalOrder add(int withdrawalRecordId);
	
	public WithdrawalOrder updateByRequestStart(String orderFormId);
	
	public WithdrawalOrder updateBySuccess(String orderFormId,String code,String msg,String response);
	
	public WithdrawalOrder updateByFailed(String orderFormId,String code,String msg,String response);
	
	public WithdrawalOrder findByOrderFormId(String orderFormId);
	
	public WithdrawalOrder updateByPayOrderFormId(String orderFormId,String payOrderFormId);
	
	public WithdrawalOrder findByWithdrawalRecordId(int withdrawalRecordId);
	
}
