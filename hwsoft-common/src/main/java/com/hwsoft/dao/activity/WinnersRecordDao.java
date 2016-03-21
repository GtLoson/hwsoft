/**
 * 
 */
package com.hwsoft.dao.activity;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.activity.WinnersRecord;
import com.hwsoft.vo.activity.WinnersRecordVo;
import com.hwsoft.vo.product.BaseProductAccountVo;

/**
 * @author tzh
 *
 */
@Repository("winnersRecordDao")
public class WinnersRecordDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return WinnersRecord.class;
	}
	
	/* (non-Javadoc)
	 * @see com.hwsoft.service.activity.WinnersRecordService#getTotalCount(java.lang.Integer)
	 */
	public long getTotalCount(Integer customerId) {
		String hql = " select count(id) from WinnersRecord ";
		if(null != customerId){
			hql += " where customerId="+customerId;
		}
		return Long.parseLong(super.getSessionFactory().getCurrentSession().createQuery(hql).uniqueResult().toString());
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.activity.WinnersRecordService#listAll(java.lang.Integer, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<WinnersRecord> listAll(Integer customerId, int from,
			int pageSize) {
		
		String hql = " from WinnersRecord ";
		if(null != customerId){
			hql += " where customerId="+customerId;
		}
		return super.getSessionFactory().getCurrentSession().createQuery(hql).setFirstResult(from).setMaxResults(pageSize).list();
	}
	
	public WinnersRecord save(WinnersRecord winnersRecord){
		return super.add(winnersRecord);
	}
	
	
	/* (non-Javadoc)
	 * @see com.hwsoft.service.activity.WinnersRecordService#getTotalCount(java.lang.Integer)
	 */
	public long getTotalCountBySearch(Integer customerId) {
		String hql = " select count(id) from WinnersRecord ";
		if(null != customerId){
			hql += " where customerId="+customerId;
		}
		return Long.parseLong(super.getSessionFactory().getCurrentSession().createQuery(hql).uniqueResult().toString());
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.activity.WinnersRecordService#listAll(java.lang.Integer, int, int)
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<WinnersRecordVo> listAllBySearch(Integer customerId, int from,
			int pageSize) {
		
		String sql = "SELECT wr.id id,wr.customer_id customerId,wr.prize_id prizeId,wr.prize_name prizeName,wr.prize_time prizeTime,"+
					 "		wr.score score,wr.status status, wr.update_time updateTime,c.real_name realName,c.mobile mobile,a.name activityName"+
					 "	FROM winners_record wr"+
					 "	LEFT JOIN customer c ON wr.customer_id = c.id"+
					 "	LEFT JOIN activity a ON wr.acitivity_id = a.id";
		if(null != customerId){
			sql += " where wr.customer_id="+customerId;
		}
		return super.getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("customerId", Hibernate.INTEGER)
				.addScalar("prizeId", Hibernate.INTEGER)
				.addScalar("prizeName", Hibernate.STRING)
				.addScalar("prizeTime", Hibernate.DATE)
				.addScalar("score", Hibernate.INTEGER)
				.addScalar("status", Hibernate.STRING)
				.addScalar("updateTime", Hibernate.DATE)
				.addScalar("realName", Hibernate.STRING)
				.addScalar("mobile", Hibernate.STRING)
				.addScalar("activityName", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(WinnersRecordVo.class)).list();
	}
	
	public WinnersRecord update(WinnersRecord winnersRecord){
		return super.update(winnersRecord);
	}
	
	public WinnersRecord findById(int id){
		return super.get(id);
	}
	

}
