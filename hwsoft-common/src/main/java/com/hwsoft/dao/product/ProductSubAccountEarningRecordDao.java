/**
 * 
 */
package com.hwsoft.dao.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductSubAccountEarningsRecord;

/**
 * @author tzh
 *
 */
@Repository("productSubAccountEarningRecord")
public class ProductSubAccountEarningRecordDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return ProductSubAccountEarningsRecord.class;
	}
	
	public ProductSubAccountEarningsRecord save(ProductSubAccountEarningsRecord productSubAccountEarningsRecord){
		return super.add(productSubAccountEarningsRecord);
	}

}
