/**
 * 
 */
package com.hwsoft.service.product;


import com.hwsoft.model.product.ProductSubAccountEarningsRecord;

/**
 * @author tzh
 *
 */
public interface ProductSubAccountEarningRecordService {
	
	public ProductSubAccountEarningsRecord add(int productSubAccountId,double amount,String yieldPeriod);
	
}
