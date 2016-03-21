package com.hwsoft.dao.order.hwsoft;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.order.hwsoft.RepaymentProductOrder;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.vo.order.FailedProductOrderVo;
import com.hwsoft.vo.order.RepaymentProductDetailVo;
import com.hwsoft.vo.order.RepaymentProductOrderVo;


/**
 * 
 * @author tzh
 *
 */
@Repository("repaymentProductOrderDao")
public class RepaymentProductOrderDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return RepaymentProductOrder.class;
	}
	
	public RepaymentProductOrder save(RepaymentProductOrder repaymentProductOrder){
		return super.add(repaymentProductOrder);
	}
	
	public RepaymentProductOrder findByOrderFormI(String orderFormId) {
		String hql = " from RepaymentProductOrder where orderFormId=:orderFormId";
		return (RepaymentProductOrder) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("orderFormId", orderFormId).uniqueResult();
	}
	
	public List<RepaymentProductOrderVo> listAll(OrderStatus orderStatus,
			Date startTime, Date endTime, int from, int pageSize) {
		
		String sql = "SELECT ofp.id id,ofp.create_time createTime, ofp.order_form_id orderFormId," +
				"ofp.order_status orderStatus,ofp.order_type orderType,ofp.product_id productId,"+
				"p.product_name productName,ofp.interest interest,ofp.principal principal,ofp.default_interest defaultInterest," +
				"ofp.staff_id staffId,ofp.staff_name staffName,ofp.product_repay_record_id productRepayRecordId,prr.phase_number phaseNumber "+
				"FROM order_repayment_product ofp "+
				"LEFT JOIN product p ON ofp.product_id=p.id " +
				"LEFT JOIN product_repay_record prr on prr.id=ofp.product_repay_record_id " +
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
		
		System.out.println(sql);
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
				.setResultTransformer(Transformers.aliasToBean(RepaymentProductOrderVo.class)).setFirstResult(from).setMaxResults(pageSize);
		return sqlQuery.list();
	}

	public Long getTotalCount(OrderStatus orderStatus, Date startTime,
			Date endTime) {

		String sql = "SELECT count(ofp.id) "+
				"FROM order_repayment_product ofp "+
				"LEFT JOIN product p ON ofp.product_id=p.id " +
				"LEFT JOIN product_repay_record prr on prr.id=ofp.product_repay_record_id " +
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
	
	public RepaymentProductOrderVo findVoById(int id){
		String sql = "SELECT ofp.id id,ofp.create_time createTime, ofp.order_form_id orderFormId," +
				"ofp.order_status orderStatus,ofp.order_type orderType,ofp.product_id productId,"+
				"p.product_name productName,ofp.interest interest,ofp.principal principal,ofp.default_interest defaultInterest," +
				"ofp.staff_id staffId,ofp.staff_name staffName,ofp.product_repay_record_id productRepayRecordId,prr.phase_number phaseNumber "+
				"FROM order_repayment_product ofp "+
				"LEFT JOIN product p ON ofp.product_id=p.id " +
				"LEFT JOIN product_repay_record prr.id=ofp.product_repay_record_id " +
				"where ofp.id="+id;
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
				.setResultTransformer(Transformers.aliasToBean(RepaymentProductOrderVo.class));
		List<RepaymentProductOrderVo> vos = sqlQuery.list();
		
		if(null == vos || vos.size() == 0 ){
			return null;
		}
		
		return vos.get(0);
	}

	
	public List<RepaymentProductDetailVo> findDetailVosByOrderId(int orderId){
		String sql = "SELECT sad.id id, sad.product_sub_account_id productSubAccountId,"+
					"	sad.interest interest, sad.principal principal, sad.default_interest defaultInterest,"+
					"	c.id customerId,c.real_name realName,c.mobile mobile"+
					" FROM order_repayment_product_detail sad "+
					" LEFT JOIN product_sub_account psa ON psa.id=sad.product_sub_account_id"+
					" LEFT JOIN customer c ON c.id=psa.customer_id"+
					" WHERE sad.repayment_product_order_id="+orderId;
		
		@SuppressWarnings("deprecation")
		SQLQuery sqlQuery = (SQLQuery) getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("productSubAccountId", Hibernate.INTEGER)
				.addScalar("interest", Hibernate.DOUBLE)
				.addScalar("principal", Hibernate.DOUBLE)
				.addScalar("defaultInterest", Hibernate.DOUBLE)
				.addScalar("customerId", Hibernate.INTEGER)
				.addScalar("realName", Hibernate.STRING)
				.addScalar("mobile", Hibernate.TIMESTAMP)
				.setResultTransformer(Transformers.aliasToBean(RepaymentProductDetailVo.class));
		return sqlQuery.list();
	}
}
