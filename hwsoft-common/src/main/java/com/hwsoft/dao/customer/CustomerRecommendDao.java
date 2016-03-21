/**
 * 
 */
package com.hwsoft.dao.customer;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.customer.CustomerRecommend;

/**
 * @author tzh
 *
 */
@Repository("customerRecommendDao")
public class CustomerRecommendDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return CustomerRecommend.class;
	}
	
	public CustomerRecommend save(CustomerRecommend customerRecommend){
		return super.add(customerRecommend);
	}
	

}
