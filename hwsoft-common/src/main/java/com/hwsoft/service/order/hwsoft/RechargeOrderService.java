package com.hwsoft.service.order.hwsoft;

import com.hwsoft.model.order.hwsoft.RechargeOrder;

public interface RechargeOrderService {
	
	public RechargeOrder add(int rechargeRecordId);

	public RechargeOrder findByRechargeRecordId(int rechargeRecordId);
	
//	public RechargeOrder updateByCheckSignFailed(String orderFormId,String code,String msg,String response);
	public RechargeOrder updateFailed(String orderFormId,String payOrderFormId, String code,String msg,String response);
	
	public RechargeOrder updateSuccess(String orderFormId,String payOrderFormId, String code,String msg,String response);
	
	public RechargeOrder findByOrderFormId(String orderFormId);
	
}
