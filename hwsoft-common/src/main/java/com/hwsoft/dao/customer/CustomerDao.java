/**
 *
 */
package com.hwsoft.dao.customer;

import com.hwsoft.common.customer.CustomerSource;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.util.string.StringUtils;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;
import java.util.List;

/**
 * @author tzh
 */
@Repository("customerDao")
public class CustomerDao extends BaseDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return Customer.class;
	}

	public Customer save(Customer customer) {
		return super.add(customer);
	}

	public Customer findById(int customerId) {
		return super.get(customerId);
	}

	public List<Customer> list(String mobile, int from, int pageSize) {
		String hql = "from Customer";
		if (!StringUtils.isEmpty(mobile)) {
			hql += " where mobile='" + mobile + "'";
		}
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql).setFirstResult(from)
				.setMaxResults(pageSize);
		List<Customer> Customers = query.list();
		return Customers;
	}

	public Customer updateCustomer(Customer customer) {
		return super.update(customer);
	}

	public Long getTotalCount(String mobile) {
		String hql = "select count(id) from Customer";
		if (!StringUtils.isEmpty(mobile)) {
			hql += " where mobile='" + mobile + "'";
		}
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return Long.parseLong(query.uniqueResult().toString());
	}

	public Customer findByUsername(String username) {
		String hql = " from Customer where username=:username";
		return (Customer) super.getSessionFactory().getCurrentSession()
				.createQuery(hql).setParameter("username", username)
				.uniqueResult();
	}

	public Customer findByMobile(String mobile) {
		String hql = " from Customer where mobile=:mobile";
		return (Customer) super.getSessionFactory().getCurrentSession()
				.createQuery(hql).setParameter("mobile", mobile).uniqueResult();
	}

	public List<Customer> findByUserSourceType(CustomerSource customerSource) {
		String hql = " from Customer where customerSource=:customerSource";
		return (List<Customer>) super.getSessionFactory().getCurrentSession()
				.createQuery(hql).setParameter("customerSource", customerSource).list();
	}

	public Customer findByIdCard(String idCard) {
		String hql = " from Customer where idCard=:idCard";
		return (Customer) super.getSessionFactory().getCurrentSession()
				.createQuery(hql).setParameter("idCard", idCard).uniqueResult();
	}

	/**
	 * 根据来源查询用户信息
	 * @param customerSources
	 * @return
	 */
	public List<Customer> findByCustomerSource(EnumSet<CustomerSource> customerSources) {
		String hql = " from Customer where ";
		if(null == customerSources || customerSources.size() == 0 ){
			return null;
		}
		hql += " customerSource in (";
		int i = 0 ;
		for(CustomerSource customerSource : customerSources){
			i++;
			if(i == customerSources.size() ){
				hql += "'"+customerSource.name()+"')";
			} else {
				hql += "'"+customerSource.name()+"',";
			}
		}
		
		return super.getSessionFactory().getCurrentSession()
				.createQuery(hql).list();
	}
}
