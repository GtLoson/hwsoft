/**
 * 
 */
package com.hwsoft.service.product.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.product.ProductSubAccountPointLogType;
import com.hwsoft.dao.product.ProductSubAccountPointLogDao;
import com.hwsoft.model.product.Product;
import com.hwsoft.model.product.ProductSubAccountPointLog;
import com.hwsoft.service.product.ProductService;
import com.hwsoft.service.product.ProductSubAccountPointLogService;
import com.hwsoft.spring.MessageSource;
import com.hwsoft.util.string.StringUtils;

/**
 * @author tzh
 *
 */
@Service("productSubAccountPointLogService")
public class ProductSubAccountPointLogServiceImpl implements
		ProductSubAccountPointLogService {

	@Autowired
	private ProductSubAccountPointLogDao productSubAccountPointLogDao;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ProductService productService;
	
	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductSubAccountPointLogService#addProductSubAccountPointLog(int, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductSubAccountPointLog addProductSubAccountPointLog(
			int customerId, Integer customerSubAccountId,
			Integer productSubAccountId, Integer productId,
			Integer fromCustomer, Integer toCustomer,
			ProductSubAccountPointLogType pointLogType,double amount) {
		
		String notes = getNotes(pointLogType, productId, amount);//单独处理
		ProductSubAccountPointLog pointLog = new ProductSubAccountPointLog();
		pointLog.setAmount(amount);
		pointLog.setCreateTime(new Date());
		pointLog.setCustomerId(customerId);
		pointLog.setCustomerSubAccountId(customerSubAccountId);
		pointLog.setFromCustomer(fromCustomer);
		pointLog.setNotes(notes);
		pointLog.setProductId(productId);
		pointLog.setProductSubAccountId(productSubAccountId);
		pointLog.setRealyFromCustomerId(fromCustomer);
		pointLog.setToCustomer(toCustomer);
		pointLog.setType(pointLogType);
		
		return productSubAccountPointLogDao.save(pointLog);
	}

	private String getNotes(ProductSubAccountPointLogType pointLogType,Integer productId,double amount){
		
		String notes = "产品【%1$s】，%2$s %3$s元";
		Product product = productService.findById(productId);
		return String.format(notes, product.getProductName(),pointLogType.toString(),amount);
	}
	
}
