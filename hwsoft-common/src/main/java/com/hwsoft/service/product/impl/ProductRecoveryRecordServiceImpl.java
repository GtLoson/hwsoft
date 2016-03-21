package com.hwsoft.service.product.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.product.ProductRecoveryRecordType;
import com.hwsoft.dao.product.ProductRecoveryRecordDao;
import com.hwsoft.model.product.ProductRecoveryRecord;
import com.hwsoft.service.product.ProductRecoveryRecordService;


/**
 * 
 * @author tzh
 *
 */
@Service("productRecoveryRecordService")
public class ProductRecoveryRecordServiceImpl implements
		ProductRecoveryRecordService {
	
	@Autowired
	private ProductRecoveryRecordDao productRecoveryRecordDao;

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductRecoveryRecord addProductRecoveryRecord(int productId, int productSubAccountId, int productRepayRecordId,
			int phaseNumber,double repayInterest, double repayPrincipal, 
			double repayDefaultInterest, ProductRecoveryRecordType type){
	
		
		ProductRecoveryRecord productRecoveryRecord = new ProductRecoveryRecord();
		
		productRecoveryRecord.setPhaseNumber(phaseNumber);
		productRecoveryRecord.setProductId(productId);
		productRecoveryRecord.setProductSubAccountId(productSubAccountId);
		productRecoveryRecord.setProductRepayRecordId(productRepayRecordId);
		productRecoveryRecord.setRealyRepayDate(new Date());
		productRecoveryRecord.setRepayDefaultInterest(repayDefaultInterest);
		productRecoveryRecord.setRepayInterest(repayInterest);
		productRecoveryRecord.setRepayPrincipal(repayPrincipal);
		productRecoveryRecord.setType(type);
		
		return productRecoveryRecordDao.save(productRecoveryRecord);
	}

}
