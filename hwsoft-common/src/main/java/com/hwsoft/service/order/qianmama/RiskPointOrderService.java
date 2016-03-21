package com.hwsoft.service.order.hwsoft;


import com.hwsoft.common.order.OrderType;
import com.hwsoft.model.order.hwsoft.RiskPointOrder;

/**
 * 
 * @author tzh
 * 
 */
public interface RiskPointOrderService {

	public RiskPointOrder add( Integer productId, OrderType orderType, double amount,
			Integer productRepayRecordId,String notes,String repaymentOrderFormId);

	
	public RiskPointOrder update(RiskPointOrder riskPointOrder);
}
