/**
 * 
 */
package com.hwsoft.dao.score;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.activity.WinnersRecord;
import com.hwsoft.model.score.ScoreRecord;

/**
 * @author tzh
 *
 */
@Repository("scoreRecordDao")
public class ScoreRecordDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return ScoreRecord.class;
	}
	
	public ScoreRecord save(ScoreRecord scoreRecord){
		return super.add(scoreRecord);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.activity.WinnersRecordService#getTotalCount(java.lang.Integer)
	 */
	public long getTotalCount(Integer customerId) {
		String hql = " select count(id) from ScoreRecord ";
		if(null != customerId){
			hql += " where customerId="+customerId;
		}
		return Long.parseLong(super.getSessionFactory().getCurrentSession().createQuery(hql).uniqueResult().toString());
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.activity.WinnersRecordService#listAll(java.lang.Integer, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<ScoreRecord> listAll(Integer customerId, int from,
			int pageSize) {
		
		String hql = " from ScoreRecord ";
		if(null != customerId){
			hql += " where customerId="+customerId;
		}
		
		hql+="order by createTime desc";
		
		return super.getSessionFactory().getCurrentSession().createQuery(hql).setFirstResult(from).setMaxResults(pageSize).list();
	}
	
}
