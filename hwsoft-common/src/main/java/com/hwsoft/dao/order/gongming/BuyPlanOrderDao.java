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
import com.hwsoft.model.order.common.BuyPlanOrder;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.string.StringUtils;
import com.hwsoft.vo.order.BuyOrderVo;

/**
 * @author tzh
 *
 */
@Repository("buyPlanOrderDao")
public class BuyPlanOrderDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return BuyPlanOrder.class;
	}
	
	public BuyPlanOrder add(BuyPlanOrder buyPlanOrder){
		return super.add(buyPlanOrder);
	}

	public BuyPlanOrder update(BuyPlanOrder buyPlanOrder){
		return super.update(buyPlanOrder);
	}
	public BuyPlanOrder findByOrderFormId(String orderFormId) {
		
		String hql = " from BuyPlanOrder where orderFormId=:orderFormId";
		
		return (BuyPlanOrder) getSessionFactory().getCurrentSession().createQuery(hql).setParameter("orderFormId", orderFormId).uniqueResult();
	}
	
	
	  /**
     * 获取产品
     *
     * @param from
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<BuyOrderVo> list(OrderStatus orderStatus,Date startTime,Date endTime,String mobile,String bankCardNumber,int from, int pageSize) {


    	
    	String sql = "SELECT p.id productId,p.product_name productName,ubc.bank_id bankId,ubc.bank_name bankName,obp.user_bank_card_number bankCardNumber,"+
					"		obp.back_code backCode,obp.back_msg backMsg,obp.response response, obp.buy_amount buyAmount,obp.buy_success_amount buySuccessAmount,"+
					"		obp.create_time createTime,obp.id id,obp.order_form_id orderFormId,obp.order_status orderStatus," +
					"		c.real_name realName,c.id userId,c.mobile mobile,obp.order_type orderType"+
					"	FROM order_buy_plan obp "+
					"	LEFT JOIN product p ON obp.product_id = p.id"+
					"	LEFT JOIN customer c ON obp.user_id = c.id"+
					"	LEFT JOIN user_bank_card ubc ON ubc.id = obp.user_bank_card_id";
    	sql += " where 1=1 ";
    	
    	if(null != orderStatus){
    		sql += " and obp.order_status='"+orderStatus.name()+"'";
    	}
    	
    	if(null != startTime){
    		sql += " and obp.create_time>='"+DateTools.dateToString(startTime)+"'";
    	}
    	if(null != endTime){
    		sql += " and obp.create_time<='"+DateTools.dateToString(endTime)+"'";
    	}		
    	if(!StringUtils.isEmpty(mobile)){
    		sql += " and c.mobile='"+mobile+"'";
    	}
    	if(!StringUtils.isEmpty(bankCardNumber)){
    		sql += " and obp.user_bank_card_number like '%"+bankCardNumber+"%'";
    	}
    	// 处理查询条件
    	@SuppressWarnings("deprecation")
		SQLQuery sqlQuery = (SQLQuery) getSessionFactory().getCurrentSession().createSQLQuery(sql)
    			.addScalar("productId", Hibernate.INTEGER)
    			.addScalar("productName", Hibernate.STRING)
    			.addScalar("bankId", Hibernate.INTEGER)
    			.addScalar("bankName", Hibernate.STRING)
    			.addScalar("bankCardNumber", Hibernate.STRING)
    			.addScalar("backCode", Hibernate.STRING)
    			.addScalar("backMsg", Hibernate.STRING)
    			.addScalar("response", Hibernate.STRING)
    			.addScalar("buyAmount", Hibernate.DOUBLE)
    			.addScalar("buySuccessAmount", Hibernate.DOUBLE)
    			.addScalar("createTime", Hibernate.TIMESTAMP)
    			.addScalar("id", Hibernate.INTEGER)
    			.addScalar("orderFormId", Hibernate.STRING)
    			.addScalar("orderStatus", Hibernate.STRING)
    			.addScalar("realName", Hibernate.STRING)
    			.addScalar("userId", Hibernate.INTEGER)
    			.addScalar("mobile", Hibernate.STRING)
    			.addScalar("orderType", Hibernate.STRING)
    			.setResultTransformer(Transformers.aliasToBean(BuyOrderVo.class)).setFirstResult(from).setMaxResults(pageSize);
    	return sqlQuery.list();
    }

    /**
     * 总条数
     *
     * @return
     */
    public Long getTotalCount(OrderStatus orderStatus,Date startTime,Date endTime,String mobile,String bankCardNumber){
    	
    	String sql = "SELECT count(obp.id)"+
				"	FROM order_buy_plan obp "+
				"	LEFT JOIN product p ON obp.product_id = p.id"+
				"	LEFT JOIN customer c ON obp.user_id = c.id"+
				"	LEFT JOIN user_bank_card ubc ON ubc.id = obp.user_bank_card_id";
    	sql += " where 1=1 ";
    	
    	if(null != orderStatus){
    		sql += " and obp.order_status='"+orderStatus.name()+"'";
    	}
    	
    	if(null != startTime){
    		sql += " and obp.create_time>='"+DateTools.dateToString(startTime)+"'";
    	}
    	if(null != endTime){
    		sql += " and obp.create_time<='"+DateTools.dateToString(endTime)+"'";
    	}
    	if(!StringUtils.isEmpty(mobile)){
    		sql += " and c.mobile='"+mobile+"'";
    	}
    	if(!StringUtils.isEmpty(bankCardNumber)){
    		sql += " and obp.user_bank_card_number like '%"+bankCardNumber+"%'";
    	}
    	Object object = getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
        return null == object ? 0 : Long.parseLong(object.toString());
    }
    
    
    public List<BuyPlanOrder> listByProductId(int productId) {
		
		String hql = " from BuyPlanOrder where productId=:productId";
		
		return super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("productId", productId).list();
	}
}
