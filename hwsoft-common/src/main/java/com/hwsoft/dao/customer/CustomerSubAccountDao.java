/**
 * 
 */
package com.hwsoft.dao.customer;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.customer.CustomerSubAccount;

/**
 * @author tzh
 *
 */
@Repository("customerSubAccountDao")
public class CustomerSubAccountDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return CustomerSubAccount.class;
	}
	
	public CustomerSubAccount save(CustomerSubAccount customerSubAccount){
		return add(customerSubAccount);
	}

	public CustomerSubAccount findByCustomerSubAccountByUserIdAndProductChannel(
			final int customerId,final int productChannelId) {
		
		String hql = "from CustomerSubAccount where customerId=:customerId and productChannelId=:productChannelId";
		return (CustomerSubAccount) super.getSessionFactory().getCurrentSession().createQuery(hql)
					.setParameter("customerId", customerId)
					.setParameter("productChannelId", productChannelId)
					.uniqueResult();
	}
	
	public CustomerSubAccount findById(int customerSubAccountId) {
		return super.get(customerSubAccountId);
	}
	
}
