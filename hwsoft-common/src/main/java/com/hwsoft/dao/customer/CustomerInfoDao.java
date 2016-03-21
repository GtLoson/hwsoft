/**
 * 
 */
package com.hwsoft.dao.customer;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.customer.CustomerInfo;

/**
 * @author tzh
 *
 */
@Repository("customerInfoDao")
public class CustomerInfoDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return CustomerInfo.class;
	}
	
	public CustomerInfo save(CustomerInfo customerInfo){
		return super.add(customerInfo);
	}
	
	public CustomerInfo update(CustomerInfo customerInfo){
		return super.update(customerInfo);
	}
	
	public CustomerInfo findByUserId(final int userId){
		String hql = " from CustomerInfo where customerId=:userId";
		return (CustomerInfo) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("userId", userId).uniqueResult();
	}

}
