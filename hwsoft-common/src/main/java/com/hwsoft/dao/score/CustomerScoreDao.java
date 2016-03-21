/**
 * 
 */
package com.hwsoft.dao.score;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.score.CustomerScore;

/**
 * @author tzh
 *
 */
@Repository("customerScoreDao")
public class CustomerScoreDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return CustomerScore.class;
	}
	
	public CustomerScore save(CustomerScore customerScore){
		return super.add(customerScore);
	}
	
	public CustomerScore update(CustomerScore customerScore){
		return super.update(customerScore);
	}
	
	public CustomerScore findByCustomerId(int customerId) {
		String hql = " from CustomerScore where customerId=:customerId";
		return (CustomerScore) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("customerId", customerId).uniqueResult();
	}

}
