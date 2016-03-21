/**
 * 
 */
package com.hwsoft.service.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.product.ProductInfoDao;
import com.hwsoft.model.product.ProductInfo;
import com.hwsoft.service.product.ProductInfoService;

/**
 * @author tzh
 *
 */
@Service("productInfoService")
public class ProductInfoServiceImpl implements ProductInfoService {
	
	
	@Autowired
	private ProductInfoDao productInfoDao;

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductInfoService#productInfo(int, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductInfo add(int productId, String desc,
			String desciptionTitle, String mortgageDesc, List<String> picPaths,String activityDesc) {
		
		
		ProductInfo productInfo = new ProductInfo();
		productInfo.setDesciption(desc);
		productInfo.setDesciptionTitle(desciptionTitle);
		productInfo.setMortgageDesc(mortgageDesc);
		productInfo.setProductId(productId);
		productInfo.setPicPaths(picPaths);
		productInfo.setActivityDesc(activityDesc);
		return productInfoDao.save(productInfo);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductInfoService#findByProductId(int)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public ProductInfo findByProductId(int productId) {
		return productInfoDao.findByProductId(productId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductInfo update(ProductInfo productInfo) {
		return productInfoDao.update(productInfo);
	}

}
