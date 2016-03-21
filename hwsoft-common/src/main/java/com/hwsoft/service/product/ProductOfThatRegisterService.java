package com.hwsoft.service.product;

import java.util.List;

import com.hwsoft.model.product.ProductOfthatRegister;

/**
 * 产品权益service
 * @author tzh
 *
 */
public interface ProductOfThatRegisterService {

	public ProductOfthatRegister addProductOfthatRegister(ProductOfthatRegister productOfthatRegister);
	
	/**
	 * 根据产品id更新产品权益(流标)
	 * @param productId
	 * @return
	 */
	public ProductOfthatRegister updateProductOfthatRegisterForFailedProduct(int productId);
	
	/**
	 * 根据产品id查询产品权益
	 * @param productId
	 * @return
	 */
	public List<ProductOfthatRegister> findByProductId(int productId);
	
	
	/**
	 * 根据产品id查询产品权益
	 * @param productId
	 * @return
	 */
	public List<ProductOfthatRegister> updateLeding(int productId);
	
	/**
	 * 根据产品id查询产品权益
	 * @param productId
	 * @return
	 */
	public List<ProductOfthatRegister> updateClosed(int productId);
}
