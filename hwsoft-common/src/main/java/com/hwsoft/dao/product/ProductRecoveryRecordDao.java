package com.hwsoft.dao.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductRecoveryRecord;


/**
 * 
 * @author tzh
 *
 */
@Repository("productRecoveryRecordDao")
public class ProductRecoveryRecordDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return ProductRecoveryRecord.class;
	}
	
	public ProductRecoveryRecord save(ProductRecoveryRecord productRecoveryRecord){
		return super.add(productRecoveryRecord);
	}

}
