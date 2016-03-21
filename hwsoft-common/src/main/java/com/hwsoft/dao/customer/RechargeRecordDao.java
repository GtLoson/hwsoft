package com.hwsoft.dao.customer;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.customer.RechargeRecordStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.customer.RechargeRecord;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.string.StringUtils;
import com.hwsoft.vo.customer.RechargeRecordVo;
import com.hwsoft.vo.customer.WithdrawalRecordVo;


/**
 * 
 * @author tzh
 *
 */
@Repository("rechargeRecordDao")
public class RechargeRecordDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return RechargeRecord.class;
	}
	
	public RechargeRecord save(RechargeRecord rechargeRecord){
		return super.add(rechargeRecord);
	}
	
	public RechargeRecord update(RechargeRecord rechargeRecord){
		return super.update(rechargeRecord);
	}
	
	public RechargeRecord findById(int id){
		return super.get(id);
	}
	
//	public RechargeRecord findByOrderFormId(String orderFormId){
//		String hql = " from RechargeRecord where orderFormId=orderFormId";
//		return (RechargeRecord) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("orderFormId", orderFormId).uniqueResult();
//	}
	
	
	/**
	 * 查询所有处理中的数据
	 * @param orderFormId
	 * @return
	 */
	public List<Integer> findAllProgress(){
		
		String hql = " select id from RechargeRecord where status= :status1 or status=:status2";
		return super.getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("status1", RechargeRecordStatus.RECHARGEING)
				.setParameter("status2", RechargeRecordStatus.RECHARGEING)
				.list();
	}
	
	
	public List<RechargeRecordVo> list(int customerId,
			Integer customerSubAccountId, int from, int pageSize) {
		
		String sql = " select rr.id id,rr.customer_id customerId,rr.customer_sub_account_id customerSubAccountId," +
					"	rr.status status, rr.product_channel_id productChannelId,rr.create_time createTime," +
					"	rr.arrive_time arriveTime,rr.amount amount,rr.success_amount successAmount,rr.fee fee," +
					"	rr.real_fee_cost_account realFeeCostAccount,c.real_name realName,c.mobile mobile" +
					" from recharge_record rr " +
					" left join customer c on rr.customer_id=c.id " +
					" where ";
		
		if(null != customerSubAccountId){
			sql += "  rr.customer_sub_account_id= "+customerSubAccountId;
		} else {
			sql += " rr.customer_id= "+customerId;
		}
		
		sql += " ORDER  BY  rr.create_time DESC ";
		
		@SuppressWarnings("deprecation")
		SQLQuery sqlQuery = (SQLQuery) getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("amount", Hibernate.DOUBLE)
				.addScalar("customerSubAccountId", Hibernate.INTEGER)
				.addScalar("status", Hibernate.STRING)
				.addScalar("createTime", Hibernate.TIMESTAMP)
				.addScalar("arriveTime", Hibernate.TIMESTAMP)
				.addScalar("customerId", Hibernate.INTEGER)
				.addScalar("productChannelId", Hibernate.INTEGER)
				.addScalar("successAmount", Hibernate.DOUBLE)
				.addScalar("fee", Hibernate.DOUBLE)
				.addScalar("mobile", Hibernate.STRING)
				.addScalar("realName", Hibernate.STRING)
				.addScalar("realFeeCostAccount", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(RechargeRecordVo.class)).setFirstResult(from).setMaxResults(pageSize);
		return sqlQuery.list();
	}

	public long listTotalCount(int customerId, Integer customerSubAccountId) {
		
		String sql = " select count(rr.id)" +
				" from recharge_record rr " +
				" where ";
	
		if(null != customerSubAccountId){
			sql += "  rr.customer_sub_account_id= "+customerSubAccountId;
		} else {
			sql += " rr.customer_id= "+customerId;
		}
		
		Object object = getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
        return null == object ? 0 : Long.parseLong(object.toString());
	}
	
	public List<RechargeRecordVo> listAll(RechargeRecordStatus status,
			Date startTime, Date endTime, String mobile, int from, int pageSize) {
		String sql = " select rr.id id,rr.customer_id customerId,rr.customer_sub_account_id customerSubAccountId," +
				"	rr.status status, rr.product_channel_id productChannelId,rr.create_time createTime," +
				"	rr.arrive_time arriveTime,rr.amount amount,rr.success_amount successAmount,rr.fee fee," +
				"	rr.real_fee_cost_account realFeeCostAccount,c.real_name realName,c.mobile mobile" +
				" from recharge_record rr " +
				" left join customer c on rr.customer_id=c.id " +
				" where 1=1 ";
	
		if(null != status){
			sql += " and rr.status='"+status.name()+"'";
		} 
		
		if(!StringUtils.isEmpty(mobile)){
			sql += " and c.mobile = '"+mobile+"'";
		}
		
		if(null != startTime){
			sql += " and rr.create_time>='"+DateTools.dateToString(startTime)+"'";
		}
		
		if(null != endTime){
			sql += " and rr.create_time<='"+DateTools.dateToString(endTime)+"'";
		}
		
		sql += " ORDER BY rr.id DESC ";
		
		@SuppressWarnings("deprecation")
		SQLQuery sqlQuery = (SQLQuery) getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("amount", Hibernate.DOUBLE)
				.addScalar("customerSubAccountId", Hibernate.INTEGER)
				.addScalar("status", Hibernate.STRING)
				.addScalar("createTime", Hibernate.TIMESTAMP)
				.addScalar("arriveTime", Hibernate.TIMESTAMP)
				.addScalar("customerId", Hibernate.INTEGER)
				.addScalar("productChannelId", Hibernate.INTEGER)
				.addScalar("successAmount", Hibernate.DOUBLE)
				.addScalar("fee", Hibernate.DOUBLE)
				.addScalar("mobile", Hibernate.STRING)
				.addScalar("realName", Hibernate.STRING)
				.addScalar("realFeeCostAccount", Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(RechargeRecordVo.class)).setFirstResult(from).setMaxResults(pageSize);
		return sqlQuery.list();
	}

	public long listAllTotalCount(RechargeRecordStatus status, Date startTime,
			Date endTime, String mobile) {
		
		String sql = " select count(rr.id)" +
				" from recharge_record rr " +
				" left join customer c on rr.customer_id=c.id " +
				" where 1=1 ";
	
		if(null != status){
			sql += " and rr.status='"+status.name()+"'";
		} 
		
		if(!StringUtils.isEmpty(mobile)){
			sql += " and c.mobile = '"+mobile+"'";
		}
		
		if(null != startTime){
			sql += " and rr.create_time>='"+DateTools.dateToString(startTime)+"'";
		}
		
		if(null != endTime){
			sql += " and rr.create_time<='"+DateTools.dateToString(endTime)+"'";
		}

		Object object = getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
        return null == object ? 0 : Long.parseLong(object.toString());
	}

}
