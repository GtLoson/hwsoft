package com.hwsoft.service.log.product;

import com.hwsoft.model.log.product.RiskPointLog;

public interface RiskPointLogService {

	
	public RiskPointLog addRiskPointLog(String orderFormId,Integer staffId,String staffName,String notes);
	
}
