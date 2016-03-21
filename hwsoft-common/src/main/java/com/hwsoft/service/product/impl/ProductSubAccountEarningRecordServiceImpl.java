/**
 * 
 */
package com.hwsoft.service.product.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwsoft.dao.product.ProductSubAccountEarningRecordDao;
import com.hwsoft.exception.product.ProductException;
import com.hwsoft.model.product.ProductSubAccount;
import com.hwsoft.model.product.ProductSubAccountEarningsRecord;
import com.hwsoft.service.product.ProductSubAccountEarningRecordService;
import com.hwsoft.service.product.ProductSubAccountService;

/**
 * @author tzh
 *
 */
@Service("productSubAccountEarningRecordService")
public class ProductSubAccountEarningRecordServiceImpl implements
		ProductSubAccountEarningRecordService {
	
	@Autowired
	private ProductSubAccountEarningRecordDao productSubAccountEarningRecordDao;

	
	@Autowired
	private ProductSubAccountService productSubAccountService;
	
	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductSubAccountEarningRecordService#add(int, double, java.lang.String)
	 */
	@Override
	public ProductSubAccountEarningsRecord add(int productSubAccountId,
			double amount, String yieldPeriod) {
		
		ProductSubAccount productSubAccount = productSubAccountService.findById(productSubAccountId);
		if(null == productSubAccount){
			throw new ProductException("产品子账户未找到");
		}
		ProductSubAccountEarningsRecord productSubAccountEarningsRecord = new ProductSubAccountEarningsRecord();
		productSubAccountEarningsRecord.setAmount(amount);
		productSubAccountEarningsRecord.setEarningTime(new Date());
		productSubAccountEarningsRecord.setProductSubAccountId(productSubAccountId);
		productSubAccountEarningsRecord.setYieldPeriod(yieldPeriod);
		return productSubAccountEarningRecordDao.save(productSubAccountEarningsRecord);
	}

}
