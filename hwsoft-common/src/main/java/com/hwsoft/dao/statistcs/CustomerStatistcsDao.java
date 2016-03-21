/**
 * 
 */
package com.hwsoft.dao.statistcs;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.product.ProductBuyerRecordStatus;
import com.hwsoft.common.product.ProductBuyerStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.vo.product.BaseProductAccountVo;
import com.hwsoft.vo.product.BaseProductSubAccountVo;

/**
 * @author tzh
 *
 */
@Repository("customerStatistcsDao")
public class CustomerStatistcsDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return null;
	}
	
	/**
	 * 今日总收益
	 * @param customerId
	 * @return
	 */
	public double todayTotalEarnings(int customerId){
		//TODO 暂时是
		String hql = " select IF(SUM(daily_interest) IS NULL , 0 ,SUM(daily_interest)) from product_sub_account where customer_id="+customerId +
						" and status in (";
		for(ProductBuyerRecordStatus status : ProductBuyerRecordStatus.INTEREST_BEARING_STATUS){
			hql += "'"+status.name()+"',";
		}
		hql = hql.substring(0, hql.length()-1);
		hql += ") and last_interest_update_date='"+DateTools.dateToString(new Date(), DateTools.DATE_PATTERN_DAY)+"'";
		return Double.parseDouble(super.getSessionFactory().getCurrentSession().createSQLQuery(hql).uniqueResult().toString());
	
	}

	/**
	 * 用户累计收益
	 * @param customerId
	 * @return 用户的累计收益
	 */
	public double totalEarnings(int customerId) {
		//TODO 这里是这个用户所有历史订单的数据
		String hql = " select IF(SUM(total_interest) IS NULL , 0 ,SUM(total_interest)) from product_sub_account where customer_id="+customerId;

		return Double.parseDouble(super.getSessionFactory().getCurrentSession().createSQLQuery(hql).uniqueResult().toString());
	}

	/**
	 * 产品总资产 = 待收利息+待收本金
	 * 总资产=+可用余额+冻结金额
	 * 
	 * @param customerId
	 * @return
	 */
	public double totalProductAssets(int customerId){
		//TODO 暂时是
		String hql = " select " +
					"IF(sum(total_interest) IS NULL , 0 ,sum(total_interest))" +
					"+IF(sum(waitting_principal) IS NULL , 0 ,sum(waitting_principal))" +
					"from product_sub_account where customer_id="+customerId +
						" and status in (";
		for(ProductBuyerRecordStatus status : ProductBuyerRecordStatus.INTEREST_BEARING_STATUS){
			hql += "'"+status.name()+"',";
		}
		hql = hql.substring(0, hql.length()-1);
		hql += ")";
		return Double.parseDouble(super.getSessionFactory().getCurrentSession().createSQLQuery(hql).uniqueResult().toString());
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<BaseProductAccountVo> findProductAccountVo(EnumSet<ProductBuyerStatus> productBuyerStatusSet, int customerId) {
		String sql = " select pa.id id,pa.total_interest totalEarnings,pa.buy_amount totalBuyAmount," +
					" p.id productId,p.product_name productName,if(sum(psa.daily_interest) is null,0,sum(psa.daily_interest)  ) todayTotalEarnings" +
					" from product_account pa " +
					" left join product p on pa.product_id = p.id " +
					" left join product_sub_account psa on psa.product_buyer_id = pa.id" +
					" where pa.customer_id="+customerId;
		int i = 0 ;
		if(null != productBuyerStatusSet || productBuyerStatusSet.size() != 0){
			sql += " and ( ";
			
			for(ProductBuyerStatus productBuyerStatus : productBuyerStatusSet){
				i ++;
				sql += " pa.product_buyer_status='"+productBuyerStatus.name()+"'";
				if(i < productBuyerStatusSet.size() ){
					sql += " or ";
				}
			}
			sql += " )";
		}
		
		
		sql += " group by pa.id desc";
		return super.getSessionFactory().getCurrentSession().createSQLQuery(sql)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("totalEarnings", Hibernate.BIG_DECIMAL)
					.addScalar("totalBuyAmount", Hibernate.BIG_DECIMAL)
					.addScalar("productId", Hibernate.INTEGER)
					.addScalar("productName", Hibernate.STRING)
					.addScalar("todayTotalEarnings", Hibernate.BIG_DECIMAL)
					.setResultTransformer(Transformers.aliasToBean(BaseProductAccountVo.class)).list();
	}
	
	
	@SuppressWarnings("deprecation")
	public BaseProductAccountVo findProductAccountVoById(int productAccountId) {
		String sql = " select pa.id id,pa.total_interest totalEarnings,pa.buy_amount totalBuyAmount," +
				" p.id productId,p.product_name productName,if(sum(psa.daily_interest) is null , 0 , sum(psa.daily_interest) ) todayTotalEarnings," +
				" psa.status status" +
				" from product_account pa " +
				" left join product p on pa.product_id = p.id " +
				" left join product_sub_account psa on psa.product_buyer_id=pa.id" +
				" where product_buyer_id="+productAccountId;
		
		return (BaseProductAccountVo) super.getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("id", Hibernate.INTEGER)
				.addScalar("totalEarnings", Hibernate.BIG_DECIMAL)
				.addScalar("totalBuyAmount", Hibernate.BIG_DECIMAL)
				.addScalar("productId", Hibernate.INTEGER)
				.addScalar("productName", Hibernate.STRING)
				.addScalar("todayTotalEarnings", Hibernate.BIG_DECIMAL)
				.setResultTransformer(Transformers.aliasToBean(BaseProductAccountVo.class)).uniqueResult();
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<BaseProductSubAccountVo> findProductSubAccountVo(
			int productAccountId) {
		String sql = "SELECT psa.id id,psa.success_amount totalBuyAmount,psa.total_interest totalEarnings,"+
					 "		psa.daily_interest todayTotalEarnings,psa.product_id productId,"+
					 "		psa.bank_card_number bankCardNumer,psa.buy_time buyTime,psa.status status,"+ 
					 "		b.bank_pic_path bankIco,p.product_name productName,obp.back_msg errorMsg,psa.share_num shareNum"+
					 "	FROM product_sub_account psa "+
					 "	LEFT JOIN user_bank_card ubc ON ubc.id=psa.user_bank_card_id "+
					 "	LEFT JOIN bank b ON b.id = ubc.bank_id"+
					 "	LEFT JOIN product p ON p.id=psa.product_id " +
					 "	LEFT JOIN order_buy_plan obp on obp.product_buyer_record_id=psa.id"+
					 "	WHERE psa.product_buyer_id="+productAccountId;
		return super.getSessionFactory().getCurrentSession().createSQLQuery(sql)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("totalEarnings", Hibernate.BIG_DECIMAL)
					.addScalar("totalBuyAmount", Hibernate.BIG_DECIMAL)
					.addScalar("productId", Hibernate.INTEGER)
					.addScalar("productName", Hibernate.STRING)
					.addScalar("todayTotalEarnings", Hibernate.BIG_DECIMAL)
					.addScalar("bankCardNumer", Hibernate.STRING)
					.addScalar("buyTime", Hibernate.DATE)
					.addScalar("status", Hibernate.STRING)
					.addScalar("bankIco", Hibernate.STRING)
					.addScalar("errorMsg", Hibernate.STRING)
					.addScalar("shareNum", Hibernate.INTEGER)
					.setResultTransformer(Transformers.aliasToBean(BaseProductSubAccountVo.class)).list();
	}

	
	@SuppressWarnings("deprecation")
	public BaseProductSubAccountVo findProductSubAccountVoBySubAccountId(
			int productSubAccountId) {
		String sql = "SELECT psa.id id,psa.success_amount totalBuyAmount,psa.total_interest totalEarnings,"+
					 "		psa.daily_interest todayTotalEarnings,psa.product_id productId,"+
					 "		psa.bank_card_number bankCardNumer,psa.buy_time buyTime,psa.status status,"+ 
					 "		b.bank_pic_path bankIco,p.product_name productName,obp.back_msg errorMsg,psa.share_num shareNum"+
					 "	FROM product_sub_account psa "+
					 "	LEFT JOIN user_bank_card ubc ON ubc.id=psa.user_bank_card_id "+
					 "	LEFT JOIN bank b ON b.id = ubc.bank_id"+
					 "	LEFT JOIN product p ON p.id=psa.product_id "+
					 "	LEFT JOIN order_buy_plan obp on obp.product_buyer_record_id=psa.id"+
					 "	WHERE psa.id="+productSubAccountId;
		return (BaseProductSubAccountVo) super.getSessionFactory().getCurrentSession().createSQLQuery(sql)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("totalEarnings", Hibernate.BIG_DECIMAL)
					.addScalar("totalBuyAmount", Hibernate.BIG_DECIMAL)
					.addScalar("productId", Hibernate.INTEGER)
					.addScalar("productName", Hibernate.STRING)
					.addScalar("todayTotalEarnings", Hibernate.BIG_DECIMAL)
					.addScalar("bankCardNumer", Hibernate.STRING)
					.addScalar("buyTime", Hibernate.DATE)
					.addScalar("status", Hibernate.STRING)
					.addScalar("bankIco", Hibernate.STRING)
					.addScalar("errorMsg", Hibernate.STRING)
					.addScalar("shareNum", Hibernate.INTEGER)
					.setResultTransformer(Transformers.aliasToBean(BaseProductSubAccountVo.class)).uniqueResult();
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<BaseProductSubAccountVo> findProductSubAccountVoByCustomerId(
			int customerId) {
		String sql = "SELECT psa.id id,psa.success_amount totalBuyAmount,psa.total_interest totalEarnings,"+
					 "		psa.daily_interest todayTotalEarnings,psa.product_id productId,"+
					 "		psa.bank_card_number bankCardNumer,psa.buy_time buyTime,psa.status status,"+ 
					 "		b.bank_pic_path bankIco,p.product_name productName,psa.amount amount,obp.back_msg errorMsg," +
					 "		p.start_interest_bearing_date startInterestBearingDate,p.end_interest_bearing_date endInterestBearingDate," +
					 "		psa.share_num shareNum"+
					 "	FROM product_sub_account psa "+
					 "	LEFT JOIN user_bank_card ubc ON ubc.id=psa.user_bank_card_id "+
					 "	LEFT JOIN bank b ON b.id = ubc.bank_id"+
					 "	LEFT JOIN product p ON p.id=psa.product_id "+
					 "	LEFT JOIN order_buy_plan obp on obp.product_buyer_record_id=psa.id"+
					 "	WHERE psa.customer_id="+customerId +" order by psa.id desc";
		return super.getSessionFactory().getCurrentSession().createSQLQuery(sql)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("totalEarnings", Hibernate.BIG_DECIMAL)
					.addScalar("totalBuyAmount", Hibernate.BIG_DECIMAL)
					.addScalar("productId", Hibernate.INTEGER)
					.addScalar("productName", Hibernate.STRING)
					.addScalar("todayTotalEarnings", Hibernate.BIG_DECIMAL)
					.addScalar("amount", Hibernate.BIG_DECIMAL)
					.addScalar("bankCardNumer", Hibernate.STRING)
					.addScalar("buyTime", Hibernate.DATE)
					.addScalar("status", Hibernate.STRING)
					.addScalar("bankIco", Hibernate.STRING)
					.addScalar("errorMsg", Hibernate.STRING)
					.addScalar("startInterestBearingDate", Hibernate.DATE)
					.addScalar("endInterestBearingDate", Hibernate.DATE)
					.addScalar("shareNum", Hibernate.INTEGER)
					.setResultTransformer(Transformers.aliasToBean(BaseProductSubAccountVo.class)).list();
	}
	
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<BaseProductSubAccountVo> findProductSubAccountVoByProductId(
			int productId) {
		String sql = "SELECT psa.id id,psa.success_amount totalBuyAmount,psa.total_interest totalEarnings,"+
					 "		psa.daily_interest todayTotalEarnings,psa.product_id productId,"+
					 "		psa.bank_card_number bankCardNumer,psa.buy_time buyTime,psa.status status,"+ 
					 "		b.bank_pic_path bankIco,p.product_name productName,psa.amount amount,obp.back_msg errorMsg," +
					 "		p.start_interest_bearing_date startInterestBearingDate,p.end_interest_bearing_date endInterestBearingDate," +
					 "		c.real_name realName, b.bank_name bankName,psa.share_num shareNum"+
					 "	FROM product_sub_account psa "+
					 "	LEFT JOIN user_bank_card ubc ON ubc.id=psa.user_bank_card_id "+
					 "	LEFT JOIN bank b ON b.id = ubc.bank_id"+
					 "	LEFT JOIN product p ON p.id=psa.product_id "+
					 "	LEFT JOIN order_buy_plan obp on obp.product_buyer_record_id=psa.id" +
					 "	LEFT JOIN customer c on c.id=psa.customer_id "+
					 "	WHERE psa.product_id="+productId +" order by psa.id desc";
		return super.getSessionFactory().getCurrentSession().createSQLQuery(sql)
					.addScalar("id", Hibernate.INTEGER)
					.addScalar("totalEarnings", Hibernate.BIG_DECIMAL)
					.addScalar("totalBuyAmount", Hibernate.BIG_DECIMAL)
					.addScalar("productId", Hibernate.INTEGER)
					.addScalar("productName", Hibernate.STRING)
					.addScalar("todayTotalEarnings", Hibernate.BIG_DECIMAL)
					.addScalar("amount", Hibernate.BIG_DECIMAL)
					.addScalar("bankCardNumer", Hibernate.STRING)
					.addScalar("buyTime", Hibernate.DATE)
					.addScalar("status", Hibernate.STRING)
					.addScalar("bankIco", Hibernate.STRING)
					.addScalar("errorMsg", Hibernate.STRING)
					.addScalar("realName", Hibernate.STRING)
					.addScalar("bankName", Hibernate.STRING)
					.addScalar("startInterestBearingDate", Hibernate.DATE)
					.addScalar("endInterestBearingDate", Hibernate.DATE)
					.addScalar("shareNum", Hibernate.INTEGER)
					.setResultTransformer(Transformers.aliasToBean(BaseProductSubAccountVo.class)).list();
	}
	
	public double findProductTotolInterest(int productAccountId) {
		String hql = " select " +
				"IF(sum(total_interest) IS NULL , 0 ,sum(total_interest))" +
				"from product_sub_account where product_buyer_id="+productAccountId ;
		return Double.parseDouble(super.getSessionFactory().getCurrentSession().createSQLQuery(hql).uniqueResult().toString());
	}
	
	public double totalAvailable(int customerId,Integer productChannelId) {
		String hql = " select IF(sum(cp.available_points) IS NULL , 0 ,sum(cp.available_points))" +
				" from customer_sub_account c " +
				" left join customer_sub_account_point cp on c.id=cp.customer_sub_account_id" +
				" where c.customer_id=" +customerId;
		
		if(null != productChannelId){
			hql += " and c.product_channel_id = "+productChannelId;
		}
		return Double.parseDouble(super.getSessionFactory().getCurrentSession().createSQLQuery(hql).uniqueResult().toString());
	}

	public double totalFreeze(int customerId) {
		
		String hql = " select IF(sum(cp.frozen_points) IS NULL , 0 ,sum(cp.frozen_points))" +
					" from customer_sub_account c " +
					" left join customer_sub_account_point cp on c.id=cp.customer_sub_account_id" +
					" where c.customer_id=" +customerId;
		return Double.parseDouble(super.getSessionFactory().getCurrentSession().createSQLQuery(hql).uniqueResult().toString());
	}
	
}
