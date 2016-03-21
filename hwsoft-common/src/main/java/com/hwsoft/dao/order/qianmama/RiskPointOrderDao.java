package com.hwsoft.dao.order.hwsoft;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.order.hwsoft.RiskPointOrder;


/**
 * 
 * @author tzh
 *
 */
@Repository("riskPointOrderDao")
public class RiskPointOrderDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return RiskPointOrder.class;
	}
	
	public RiskPointOrder save(RiskPointOrder riskPointOrder){
		return super.add(riskPointOrder);
	}
	
	public RiskPointOrder update(RiskPointOrder riskPointOrder){
		return super.update(riskPointOrder);
	}
	
	public RiskPointOrder findByOrderFormId(String orderFormId){
		String hql = " from RiskPointOrder where orderFormId=:orderFormId";
		return (RiskPointOrder) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("orderFormId", orderFormId).uniqueResult();
	}
}
