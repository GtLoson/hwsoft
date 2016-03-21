package com.hwsoft.service.log.product;

import com.hwsoft.model.log.product.LendingProductLog;

public interface LendingProductLogService {

	/**
	 * 添加放款订单
	 * @param productId
	 * @param orderFormId
	 * @param staffId
	 * @param staffName
	 * @param notes
	 * @return
	 */
	public LendingProductLog addLendingProductLog(int productId,String orderFormId,int staffId,String staffName,String notes);
	
}
