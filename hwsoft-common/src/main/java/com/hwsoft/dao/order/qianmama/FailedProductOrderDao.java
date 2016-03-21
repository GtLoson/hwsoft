package com.hwsoft.dao.order.hwsoft;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.order.hwsoft.FailedProductOrder;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.vo.order.FailedProductOrderVo;

@Repository("failedProductOrderDao")
public class FailedProductOrderDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return FailedProductOrder.class;
	}

	public FailedProductOrder save(FailedProductOrder failedProductOrder){
		return super.add(failedProductOrder);
	}
	

	public List<FailedProductOrderVo> listAll(OrderStatus orderStatus,
			Date startTime, Date endTime, int from, int pageSize) {
		
		String sql = "SELECT ofp.id id,ofp.create_time createTime, ofp.notes notes,ofp.order_form_id orderFormId," +
					" ofp.order_status orderStatus,ofp.order_type orderType,ofp.product_id productId,ofp.staff_id staffId," +
					" ofp.staff_name staffName,"+
					" p.product_name productName"+
					" FROM order_failed_product ofp "+
					" LEFT JOIN product p ON ofp.product_id=p.id "+
					" where 1=1";
		if(null != orderStatus){
    		sql += " and ofp.order_status='"+orderStatus.name()+"'";
    	}
    	
    	if(null != startTime){
    		sql += " and ofp.create_time>='"+DateTools.dateToString(startTime)+"'";
    	}
    	if(null != endTime){
    		sql += " and ofp.create_time<='"+DateTools.dateToString(endTime)+"'";
    	}				
    	@SuppressWarnings("deprecation")
		SQLQuery sqlQuery = (SQLQuery) getSessionFactory().getCurrentSession().createSQLQuery(sql)
    			.addScalar("id", Hibernate.INTEGER)
    			.addScalar("notes", Hibernate.STRING)
    			.addScalar("orderFormId", Hibernate.STRING)
    			.addScalar("orderStatus", Hibernate.STRING)
    			.addScalar("orderType", Hibernate.STRING)
    			.addScalar("productId", Hibernate.INTEGER)
    			.addScalar("staffId", Hibernate.INTEGER)
    			.addScalar("staffName", Hibernate.STRING)
    			.addScalar("productName", Hibernate.STRING)
    			.addScalar("createTime", Hibernate.TIMESTAMP)
    			.setResultTransformer(Transformers.aliasToBean(FailedProductOrderVo.class)).setFirstResult(from).setMaxResults(pageSize);
    	return sqlQuery.list();
	}

	public Long getTotalCount(OrderStatus orderStatus, Date startTime,
			Date endTime) {
		String sql = "SELECT count(ofp.id)"+
				" FROM order_failed_product ofp "+
				" LEFT JOIN product p ON ofp.product_id=p.id "+
				" where 1=1";
		if(null != orderStatus){
			sql += " and ofp.order_status='"+orderStatus.name()+"'";
		}
		
		if(null != startTime){
			sql += " and ofp.create_time>='"+DateTools.dateToString(startTime)+"'";
		}
		if(null != endTime){
			sql += " and ofp.create_time<='"+DateTools.dateToString(endTime)+"'";
		}				
		Object object = getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
        return null == object ? 0 : Long.parseLong(object.toString());
	}

	
}
