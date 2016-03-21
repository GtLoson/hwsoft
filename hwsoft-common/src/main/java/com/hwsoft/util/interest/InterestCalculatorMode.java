package com.hwsoft.util.interest;

import java.util.List;

import com.hwsoft.model.product.Product;
import com.hwsoft.model.product.ProductRepayRecord;

/**
 * 利息处理接口
 * @author tzh
 *
 */
public interface InterestCalculatorMode {

	
	/**
	 * 还款记录处理类
	 * @param product
	 * @param borrowCustomerSubAccountId
	 * @return
	 */
	public List<ProductRepayRecord> calcutorInterest(Product product,int borrowCustomerSubAccountId);
	
	/**
	 * 总利息计算
	 * @param product
	 * @param borrowCustomerSubAccountId
	 * @return
	 */
	public double calcutorTotalInterest(Product product,double amount );
	
}
