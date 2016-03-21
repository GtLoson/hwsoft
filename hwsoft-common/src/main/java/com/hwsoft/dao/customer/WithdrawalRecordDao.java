package com.hwsoft.dao.customer;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.hwsoft.common.customer.WithdrawalStatus;
import com.hwsoft.common.customer.WithdrawalType;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.customer.WithdrawalRecord;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.string.StringUtils;
import com.hwsoft.vo.customer.WithdrawalRecordVo;

/**
 * 
 * @author tzh
 *
 */
@Repository("withdrawalRecordDao")
public class WithdrawalRecordDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return WithdrawalRecord.class;
	}
	
	public WithdrawalRecord save(WithdrawalRecord withdrawalRecord){
		return super.add(withdrawalRecord);
	}
	
	public WithdrawalRecord update(WithdrawalRecord withdrawalRecord){
		return super.update(withdrawalRecord);
	}
	
	public WithdrawalRecord findById(int id){
		return super.get(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<WithdrawalRecordVo> list(WithdrawalType type,WithdrawalStatus status, String mobile, String timeCollumn, Date startDate,Date endDate,int from,int pageSize){
		
		String sql = "select wr.id id,wr.amount amount,wr.customer_sub_account_id customerSubAccountId," +
				"	wr.customer_id customerId,wr.type type,wr.status status,wr.apply_time applyTime," +
				"   wr.audit_time auditTime,wr.withdrawal_time withdrawalTime,wr.notes notes,wr.audit_staff_id auditStaffId," +
				"   wr.audit_staff_name auditStaffName,wr.withdrawal_staff_id withdrawalStaffId,wr.withdrawal_staff_name withdrawalStaffName," +
				"	c.mobile mobile,c.real_name realName,wr.withdrawal_notes withdrawalNotes,wr.again again" +
				" from withdrawal_record wr " +
				" left join customer c on wr.customer_id=c.id" +
				" where 1=1 ";
		
		if(null != type){
			sql += " and wr.type='"+type.name()+"'";
		}
		
		if(null != status){
			sql += " and wr.status='"+status.name()+"'";
		} else {
			sql += " and (wr.status='"+WithdrawalStatus.AUDITED_FAILED.name()+"' or wr.status='"+WithdrawalStatus.WITHDRAWAL_FAILED+"' " +
					" or wr.status='"+WithdrawalStatus.WITHDRAWAL_SUCCESS.name()+"') ";
		}
		
		if(!StringUtils.isEmpty(mobile)){
			sql += " and  c.mobile = '"+mobile+"'";
		}
		
		if(!StringUtils.isEmpty(timeCollumn)){
			if(null != startDate){
				sql += " and wr."+timeCollumn+">='"+DateTools.dateToString(startDate)+"'";
			}
			
			if(null != endDate){
				sql += " and wr."+timeCollumn+"<='"+DateTools.dateToString(endDate)+"'";
			}
		}
		
		sql += "ORDER  BY wr.apply_time DESC ";


		System.out.println("提现记录sql:"+sql);
		
		@SuppressWarnings("deprecation")
		SQLQuery sqlQuery = (SQLQuery) getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("amount", Hibernate.DOUBLE)
				.addScalar("customerSubAccountId", Hibernate.INTEGER)
				.addScalar("type", Hibernate.STRING)
				.addScalar("status", Hibernate.STRING)
				.addScalar("applyTime", Hibernate.TIMESTAMP)
				.addScalar("auditTime", Hibernate.TIMESTAMP)
				.addScalar("withdrawalTime", Hibernate.TIMESTAMP)
				.addScalar("notes", Hibernate.STRING)
				.addScalar("auditStaffId", Hibernate.INTEGER)
				.addScalar("withdrawalStaffId", Hibernate.INTEGER)
				.addScalar("withdrawalStaffName", Hibernate.STRING)
				.addScalar("mobile", Hibernate.STRING)
				.addScalar("realName", Hibernate.STRING)
				.addScalar("withdrawalNotes", Hibernate.STRING)
				.addScalar("again", Hibernate.BOOLEAN)
				.setResultTransformer(Transformers.aliasToBean(WithdrawalRecordVo.class)).setFirstResult(from).setMaxResults(pageSize);
		return sqlQuery.list();
	}
	
	
	public long getTotalCount(WithdrawalType type, WithdrawalStatus status,
			String mobile, String timeCollumn, Date startDate, Date endDate) {
		String sql = "select count(wr.id) " +
				" from withdrawal_record wr " +
				" left join customer c on wr.customer_id=c.id" +
				" where 1=1 ";
		
		if(null != type){
			sql += " and wr.type='"+type.name()+"'";
		}
		
		if(null != status){
			sql += " and wr.status='"+status.name()+"'";
		} else {
			sql += " and (wr.status='"+WithdrawalStatus.AUDITED_FAILED.name()+"' or wr.status='"+WithdrawalStatus.WITHDRAWAL_FAILED+"' " +
					" or wr.status='"+WithdrawalStatus.WITHDRAWAL_SUCCESS.name()+"') ";
		}
		
		if(!StringUtils.isEmpty(mobile)){
			sql += "  and  c.mobile = '"+mobile+"'";
		}
		
		if(!StringUtils.isEmpty(timeCollumn)){
			if(null != startDate){
				sql += " and wr."+timeCollumn+">='"+DateTools.dateToString(startDate)+"'";
			}
			
			if(null != endDate){
				sql += " and wr."+timeCollumn+"<='"+DateTools.dateToString(endDate)+"'";
			}
		}
		
		Object object = getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
        return null == object ? 0 : Long.parseLong(object.toString());
	}

	public List<Integer> listAllPayProgress() {
		String hql = " select id from WithdrawalRecord where status=:status1 ";
		return super.getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("status1", WithdrawalStatus.BANK_DEALING)
				.list();
	}
	
	public List<WithdrawalRecordVo> list(int customerId, Integer customerSubAccountId,
			int from, int pageSize) {
		String sql = "select wr.id id,wr.amount amount,wr.customer_sub_account_id customerSubAccountId," +
				"	wr.customer_id customerId,wr.type type,wr.status status,wr.apply_time applyTime," +
				"   wr.audit_time auditTime,wr.withdrawal_time withdrawalTime,wr.notes notes,wr.audit_staff_id auditStaffId," +
				"   wr.audit_staff_name auditStaffName,wr.withdrawal_staff_id withdrawalStaffId,wr.withdrawal_staff_name withdrawalStaffName," +
				"	c.mobile mobile,c.real_name realName,wr.withdrawal_notes withdrawalNotes,wr.again again" +
				" from withdrawal_record wr " +
				" left join customer c on wr.customer_id=c.id" +
				" where  ";
		
		if(null != customerSubAccountId){
			sql += "  wr.customer_sub_account_id= "+customerSubAccountId;
		} else {
			sql += " wr.customer_id="+customerId;
		}
		
		sql += " order by wr.id desc";
		
		@SuppressWarnings("deprecation")
		SQLQuery sqlQuery = (SQLQuery) getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("amount", Hibernate.DOUBLE)
				.addScalar("customerSubAccountId", Hibernate.INTEGER)
				.addScalar("type", Hibernate.STRING)
				.addScalar("status", Hibernate.STRING)
				.addScalar("applyTime", Hibernate.TIMESTAMP)
				.addScalar("auditTime", Hibernate.TIMESTAMP)
				.addScalar("withdrawalTime", Hibernate.TIMESTAMP)
				.addScalar("notes", Hibernate.STRING)
				.addScalar("auditStaffId", Hibernate.INTEGER)
				.addScalar("withdrawalStaffId", Hibernate.INTEGER)
				.addScalar("withdrawalStaffName", Hibernate.STRING)
				.addScalar("mobile", Hibernate.STRING)
				.addScalar("realName", Hibernate.STRING)
				.addScalar("withdrawalNotes", Hibernate.STRING)
				.addScalar("again", Hibernate.BOOLEAN)
				.setResultTransformer(Transformers.aliasToBean(WithdrawalRecordVo.class)).setFirstResult(from).setMaxResults(pageSize);
		return sqlQuery.list();
	}
	
	public long listTotalCount(int customerId, Integer customerSubAccountId) {
		String sql = "select count(wr.id) " +
				" from withdrawal_record wr " +
				" left join customer c on wr.customer_id=c.id" +
				" where  ";
		
		if(null != customerSubAccountId){
			sql += "  wr.customer_sub_account_id= "+customerSubAccountId;
		} else {
			sql += " wr.customer_id= "+customerId;
		}
		
		Object object = getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
        return null == object ? 0 : Long.parseLong(object.toString());
	}

	
}
