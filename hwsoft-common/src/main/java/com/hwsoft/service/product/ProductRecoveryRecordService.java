package com.hwsoft.service.product;


import com.hwsoft.common.product.ProductRecoveryRecordType;
import com.hwsoft.model.product.ProductRecoveryRecord;

public interface ProductRecoveryRecordService {

	public ProductRecoveryRecord addProductRecoveryRecord(int productId, int productSubAccountId, int productRepayRecordId,
			int phaseNumber,double repayInterest, double repayPrincipal, double repayDefaultInterest, ProductRecoveryRecordType type);
	
}
