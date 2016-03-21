package com.hwsoft.dao.order.hwsoft;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.order.hwsoft.LendingProductOrder;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.vo.order.LendingProductOrderVo;
import com.hwsoft.vo.order.LendingProductSubAccountDetailVo;

/**
 * 
 * @author tzh
 *
 */
@Repository("lendingProductOrderDao")
public class LendingProductOrderDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return LendingProductOrder.class;
	}
	
	public LendingProductOrder save(LendingProductOrder lendingProductOrder){
		return super.add(lendingProductOrder);
	}
	
	public LendingProductOrder findByOrderFormId(String orderFormId){
		return super.getByCollumn("orderFormId", orderFormId);
	}
	
	
	public List<LendingProductOrderVo> listAll(OrderStatus orderStatus,
			Date startTime, Date endTime, int from, int pageSize) {
		String sql = "SELECT ofp.id id,ofp.create_time createTime, ofp.order_form_id orderFormId," +
				" ofp.order_status orderStatus,ofp.order_type orderType,ofp.product_id productId,"+
				" p.product_name productName,ofp.amount amount,ofp.open_amount openAmount"+
				" FROM order_lending_product ofp "+
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
				//.addScalar("notes", Hibernate.STRING)
				.addScalar("orderFormId", Hibernate.STRING)
				.addScalar("orderStatus", Hibernate.STRING)
				.addScalar("orderType", Hibernate.STRING)
				.addScalar("productId", Hibernate.INTEGER)
				.addScalar("productName", Hibernate.STRING)
				.addScalar("createTime", Hibernate.TIMESTAMP)
				.addScalar("amount", Hibernate.DOUBLE)
				.addScalar("openAmount", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(LendingProductOrderVo.class)).setFirstResult(from).setMaxResults(pageSize);
		return sqlQuery.list();
	}

	public Long getTotalCount(OrderStatus orderStatus, Date startTime,
			Date endTime) {
		
		String sql = "SELECT count(ofp.id)"+
				"FROM order_lending_product ofp "+
				"LEFT JOIN product p ON ofp.product_id=p.id "+
				"where 1=1";
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
	
	public LendingProductOrderVo findVoById(int id){
		String sql = "SELECT ofp.id id,ofp.create_time createTime, ofp.order_form_id orderFormId," +
				" ofp.order_status orderStatus,ofp.order_type orderType,ofp.product_id productId,"+
				" p.product_name productName,ofp.amount amount,ofp.open_amount openAmount"+
				" FROM order_lending_product ofp "+
				" LEFT JOIN product p ON ofp.product_id=p.id "+
				" where ofp.id="+id ;
		
		@SuppressWarnings("deprecation")
		SQLQuery sqlQuery = (SQLQuery) getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				//.addScalar("notes", Hibernate.STRING)
				.addScalar("orderFormId", Hibernate.STRING)
				.addScalar("orderStatus", Hibernate.STRING)
				.addScalar("orderType", Hibernate.STRING)
				.addScalar("productId", Hibernate.INTEGER)
				.addScalar("productName", Hibernate.STRING)
				.addScalar("createTime", Hibernate.TIMESTAMP)
				.addScalar("amount", Hibernate.DOUBLE)
				.addScalar("openAmount", Hibernate.DOUBLE)
				.setResultTransformer(Transformers.aliasToBean(LendingProductOrderVo.class));
		List<LendingProductOrderVo> vos = sqlQuery.list();
		if(null == vos || vos.size() == 0){
			return null;
		}
		
		return vos.get(0);
	}
	
	public List<LendingProductSubAccountDetailVo> finDetailVosByOrderId(int orderId){
		String sql = "SELECT sad.id id,sad.bid_order_form_id bidOrderFormId, sad.product_sub_account_id productSubAccountId, "+
					"	c.id customerId,c.real_name realName,c.mobile mobile,sad.amount amount,sad.product_bid_type productBidType"+
					" FROM order_lending_product_sub_account_detail sad "+
					" LEFT JOIN product_sub_account psa ON psa.id=sad.product_sub_account_id "+
					" LEFT JOIN customer c ON c.id=psa.customer_id "+
					" WHERE sad.lending_product_order_id="+orderId;
		
		@SuppressWarnings("deprecation")
		SQLQuery sqlQuery = (SQLQuery) getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("bidOrderFormId", Hibernate.STRING)
				.addScalar("productSubAccountId", Hibernate.INTEGER)
				.addScalar("customerId", Hibernate.INTEGER)
				.addScalar("realName", Hibernate.STRING)
				.addScalar("mobile", Hibernate.STRING)
				.addScalar("amount", Hibernate.STRING)
				.addScalar("productBidType", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(LendingProductSubAccountDetailVo.class));
		return sqlQuery.list();
		
	}

}
