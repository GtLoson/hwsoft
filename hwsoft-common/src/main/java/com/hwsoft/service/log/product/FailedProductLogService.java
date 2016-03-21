package com.hwsoft.service.log.product;

import com.hwsoft.model.log.product.FailedProductLog;

public interface FailedProductLogService {

	/**
	 * 添加流标订单
	 * @param productId
	 * @param orderFormId
	 * @param staffId
	 * @param staffName
	 * @param notes
	 * @return
	 */
	public FailedProductLog addFailedProductLog(int productId,String orderFormId,int staffId,String staffName,String notes);
	
}
