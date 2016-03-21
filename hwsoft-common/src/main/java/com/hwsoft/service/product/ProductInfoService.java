/**
 * 
 */
package com.hwsoft.service.product;

import java.util.List;

import com.hwsoft.model.product.ProductInfo;

/**
 * @author tzh
 *
 */
public interface ProductInfoService {

	public ProductInfo add(int productId, String desc, String desciptionTitle, String mortgageDesc, List<String> picPaths,String activityDesc);
	public ProductInfo findByProductId(int productId);
	
	public ProductInfo update(ProductInfo productInfo);
}
