package com.hwsoft.service.log.product;

import com.hwsoft.model.log.product.LendingProductLog;
import com.hwsoft.model.log.product.RepaymentProductLog;


/**
 * 还款订单日志
 * @author tzh
 *
 */
public interface RepaymentProductLogService {

	/**
	 * 
	 * @param productId
	 * @param orderFormId
	 * @param staffId
	 * @param staffName
	 * @param notes
	 * @return
	 */
	public RepaymentProductLog addRepaymentProductLog(int productId,String orderFormId,int staffId,String staffName,String notes);
	
}
