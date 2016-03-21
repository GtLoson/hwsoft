/**
 * 
 */
package com.hwsoft.service.product;



import com.hwsoft.model.product.ProductSaleTimeInfo;

/**
 * @author tzh
 *
 */
public interface ProductSaleTimeInfoService {

	public ProductSaleTimeInfo addProductSaleTimeInfo(Integer productId,Integer startHour,Integer startMinute,
    		Integer endHour,Integer endMinute);
	
	public ProductSaleTimeInfo findByProductId(int productId);

}
