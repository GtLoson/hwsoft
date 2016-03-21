/**
 * 
 */
package com.hwsoft.dao.point;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.point.CustomerSubAccountPoint;

/**
 * @author tzh
 *
 */
@Repository("customerSubAccountPointDao")
public class CustomerSubAccountPointDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return CustomerSubAccountPoint.class;
	}
	
	public CustomerSubAccountPoint save(CustomerSubAccountPoint customerSubAccountPoint){
		return super.add(customerSubAccountPoint);
	}
	
	public CustomerSubAccountPoint findCustomerSubAccountPointByCustomerSubAccountId(
			int customerSubAccountId) {
		String hql = " from CustomerSubAccountPoint where customerSubAccountId=:customerSubAccountId";
		return (CustomerSubAccountPoint) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("customerSubAccountId", customerSubAccountId).uniqueResult();
	}
	
	public CustomerSubAccountPoint update(
			CustomerSubAccountPoint customerSubAccountPoint) {
		return super.update(customerSubAccountPoint);
	}

}
