/**
 * 
 */
package com.hwsoft.service.product;

import java.util.List;

import com.hwsoft.model.product.ProductRiskControl;

/**
 * @author tzh
 *
 */
public interface ProductRiskControlService {

	public ProductRiskControl add(int productId, String desc, List<String> picPaths,String mortgager,String guaranteeCompany);
	
	public ProductRiskControl findByProductId(int productId);
	
}
