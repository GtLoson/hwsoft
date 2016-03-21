/**
 * 
 */
package com.hwsoft.dao.order.gongming;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.order.gongming.UserAccountOrder;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.vo.order.AccountOrderVo;

/**
 * @author tzh
 *
 */
@Repository("userAccountOrderDao")
public class UserAccountOrderDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return UserAccountOrder.class;
	}
	
	public UserAccountOrder save(UserAccountOrder userAccountOrder){
		return super.add(userAccountOrder);
	}

	public UserAccountOrder update(UserAccountOrder userAccountOrder){
		return super.update(userAccountOrder);
	}
	
	public UserAccountOrder findOrderFormId(final String orderFormId){
		String hql = " from UserAccountOrder where orderFormId=:orderFormId";
		return (UserAccountOrder) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("orderFormId", orderFormId).uniqueResult();
	}
	
	  /**
     * 获取产品
     *
     * @param from
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<AccountOrderVo> list(OrderStatus orderStatus,Date startTime,Date endTime,int from, int pageSize) {


    	
    	String sql = "   SELECT p.id productChannelId, p.product_channel_name productChannelName, c.id userId,oua.id_card idCard,oua.mobile mobile,"+
			        " oua.real_name realName,oua.id id,oua.back_code backCode, oua.back_msg backMsg,oua.create_time createTime , oua.member_id memberId,"+
			        " oua.order_form_id orderFormId,oua.order_status orderStatus, oua.response response"+
				    " FROM order_user_account oua  "+
				    " LEFT JOIN product_channel p  ON oua.product_channel_id = p.id   LEFT JOIN customer c   ON oua.user_id = c.id ";
    	sql += " where 1=1 ";
    	
    	if(null != orderStatus){
    		sql += " and oua.order_status='"+orderStatus.name()+"'";
    	}
    	
    	if(null != startTime){
    		sql += " and oua.create_time>='"+DateTools.dateToString(startTime)+"'";
    	}
    	if(null != endTime){
    		sql += " and oua.create_time<='"+DateTools.dateToString(endTime)+"'";
    	}				    	
    	//TODO 处理查询条件
    	@SuppressWarnings("deprecation")
		SQLQuery sqlQuery = (SQLQuery) getSessionFactory().getCurrentSession().createSQLQuery(sql)
    			.addScalar("productChannelId", Hibernate.INTEGER)
    			.addScalar("productChannelName", Hibernate.STRING)
    			.addScalar("userId", Hibernate.INTEGER)
    			.addScalar("idCard", Hibernate.STRING)
    			.addScalar("mobile", Hibernate.STRING)
    			.addScalar("backCode", Hibernate.STRING)
    			.addScalar("backMsg", Hibernate.STRING)
    			.addScalar("response", Hibernate.STRING)
    			.addScalar("realName", Hibernate.STRING)
    			.addScalar("memberId", Hibernate.STRING)
    			.addScalar("createTime", Hibernate.TIMESTAMP)
    			.addScalar("id", Hibernate.INTEGER)
    			.addScalar("orderFormId", Hibernate.STRING)
    			.addScalar("orderStatus", Hibernate.STRING)
    			.addScalar("realName", Hibernate.STRING)
    			.setResultTransformer(Transformers.aliasToBean(AccountOrderVo.class)).setFirstResult(from).setMaxResults(pageSize);
    	return sqlQuery.list();
    }

    /**
     * 总条数
     *
     * @return
     */
    public Long getTotalCount(OrderStatus orderStatus,Date startTime,Date endTime){
    	
    	String sql = "SELECT count(oua.id)"+
    			 " FROM order_user_account oua  "+
				    " LEFT JOIN product_channel p  ON oua.product_channel_id = p.id   LEFT JOIN customer c   ON oua.user_id = c.id ";
    	sql += " where 1=1 ";
    	
    	if(null != orderStatus){
    		sql += " and oua.order_status='"+orderStatus.name()+"'";
    	}
    	
    	if(null != startTime){
    		sql += " and oua.create_time>='"+DateTools.dateToString(startTime)+"'";
    	}
    	if(null != endTime){
    		sql += " and oua.create_time<='"+DateTools.dateToString(endTime)+"'";
    	}
    	Object object = getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
        return null == object ? 0 : Long.parseLong(object.toString());
    }
	
}
