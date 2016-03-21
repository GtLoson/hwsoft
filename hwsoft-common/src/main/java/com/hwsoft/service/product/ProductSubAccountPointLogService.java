/**
 * 
 */
package com.hwsoft.service.product;

import com.hwsoft.common.product.ProductSubAccountPointLogType;
import com.hwsoft.model.product.ProductSubAccountPointLog;

/**
 * @author tzh
 *
 */
public interface ProductSubAccountPointLogService {
	
	public ProductSubAccountPointLog addProductSubAccountPointLog(int customerId,Integer customerSubAccountId,
			Integer productSubAccountId,Integer productId,Integer fromCustomer,Integer toCustomer,
			ProductSubAccountPointLogType pointLogType,double amount);
	
}
