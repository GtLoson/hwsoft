/**
 * 
 */
package com.hwsoft.dao.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductSubAccountPointLog;

/**
 * @author tzh
 *
 */
@Repository("productSubAccountPointLogDao")
public class ProductSubAccountPointLogDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return ProductSubAccountPointLog.class;
	}
	
	public ProductSubAccountPointLog save(ProductSubAccountPointLog productSubAccountPointLog){
		return super.add(productSubAccountPointLog);
	}

}
