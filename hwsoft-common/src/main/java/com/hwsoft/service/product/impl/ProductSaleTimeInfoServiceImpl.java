/**
 * 
 */
package com.hwsoft.service.product.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.product.ProductSaleTimeInfoDao;
import com.hwsoft.model.product.ProductSaleTimeInfo;
import com.hwsoft.service.product.ProductSaleTimeInfoService;

/**
 * @author tzh
 *
 */
@Service("productSaleTimeInfoService")
public class ProductSaleTimeInfoServiceImpl implements
		ProductSaleTimeInfoService {

	@Autowired
	private ProductSaleTimeInfoDao productSaleTimeInfoDao;
	
	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductSaleTimeInfoService#addProductSaleTimeInfo(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductSaleTimeInfo addProductSaleTimeInfo(Integer productId,
			Integer startHour, Integer startMinute, Integer endHour,
			Integer endMinute) {
		ProductSaleTimeInfo info = findByProductId(productId);
		
		if(null != info){
			info.setEndHour(endHour);
			info.setEndMinute(endMinute);
			info.setProductId(productId);
			info.setStartHour(startHour);
			info.setStartMinute(startMinute);
			return productSaleTimeInfoDao.update(info);
		} else {
			info = new ProductSaleTimeInfo();
			info.setEndHour(endHour);
			info.setEndMinute(endMinute);
			info.setProductId(productId);
			info.setStartHour(startHour);
			info.setStartMinute(startMinute);
			return productSaleTimeInfoDao.save(info);
		}
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductSaleTimeInfoService#findByProductId(int)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductSaleTimeInfo findByProductId(int productId) {
		return productSaleTimeInfoDao.findByProductId(productId);
	}

}
