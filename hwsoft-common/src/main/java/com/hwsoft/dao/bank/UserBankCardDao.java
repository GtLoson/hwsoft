/**
 * 
 */
package com.hwsoft.dao.bank;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.bank.UserBankCard;
import com.hwsoft.vo.bank.UserBankCardVo;

/**
 * @author tzh
 *
 */
@Repository("userBankCardDao")
public class UserBankCardDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return UserBankCard.class;
	}
	
	public UserBankCard save(UserBankCard userBankCard){
		return super.add(userBankCard);
	}
	
	public UserBankCard findById(int id){
		return super.get(id);
	}
	
	public UserBankCard update(UserBankCard userBankCard){
		return super.update(userBankCard);
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<UserBankCardVo> findUserBankCardVosByUserIdAndProductId(int userId,int productId){
		String sql = "SELECT"+
					 " ubc.id id,"+
					// " ubc.bank_branch_name bankBranchName,"+
					// " ubc.bank_card_city bankCardCity,"+
					" ubc.bank_card_number bankCardNumber,"+
					// " ubc.bank_card_province bankCardProvince,"+
					// " ubc.bank_code bankCode,"+
					 " ubc.bank_id bankId,"+
					 " ubc.bank_name bankName,"+
					// " ubc.create_time createTime,"+
					// " ubc.customer_id customerId,"+
					 // " ubc.customer_sub_account_id customerSubAccountId,"+
					 " ubc.enable enable,"+
					 //" ubc.member_id memberId,"+
					 //" ubc.reserve_phone_number reservePhoneNumber,"+
					 " ubc.status statusName," +
					 " b.bank_pic_path bankPicPath," +
					 " b.bank_day_limit bankDayLimit," +
					 " b.bank_time_limit bankTimeLimit "+
					 " FROM user_bank_card ubc"+
					 " LEFT JOIN product_bank pb ON ubc.bank_id=pb.bank_id"+
					 " LEFT JOIN bank b ON b.id = ubc.bank_id"+
					 "	WHERE ubc.enable=TRUE AND b.enable=TRUE" +
				   " and ubc.status ='BINDED'"+
					 " and pb.enable=TRUE " +
					 " and ubc.customer_id="+userId+" and pb.product_id="+productId;
		
		SQLQuery sqlQuery = (SQLQuery) super.getSessionFactory().getCurrentSession().createSQLQuery(sql)
							.addScalar("id", Hibernate.INTEGER)
							.addScalar("bankCardNumber",Hibernate.STRING)
							.addScalar("bankId", Hibernate.INTEGER)
							.addScalar("bankName", Hibernate.STRING)
							.addScalar("enable", Hibernate.BOOLEAN)
							.addScalar("statusName", Hibernate.STRING)
							.addScalar("bankPicPath", Hibernate.STRING)
							.addScalar("bankDayLimit", Hibernate.DOUBLE)
							.addScalar("bankTimeLimit", Hibernate.DOUBLE)
							.setResultTransformer(Transformers.aliasToBean(UserBankCardVo.class));
		
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBankCard> findUserBankCardByBankCardNumber(String bankCardNumber) {
		
		String hql = " from UserBankCard where bankCardNumber=:bankCardNumber and enable=true";
		return (List<UserBankCard>) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("bankCardNumber", bankCardNumber).list();
	}

	public List<UserBankCard> findAllEnableBySubAccountId(
			int customerSubAccountId) {
		String hql = " from UserBankCard where customerSubAccountId=:customerSubAccountId and enable=true";
		return (List<UserBankCard>) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("customerSubAccountId", customerSubAccountId).list();
	}
	
	public List<UserBankCardVo> findByChannelId(int productChannelId,int customerId) {
		
		String sql = "SELECT"+
				 " ubc.id id,"+
				// " ubc.bank_branch_name bankBranchName,"+
				// " ubc.bank_card_city bankCardCity,"+
				" ubc.bank_card_number bankCardNumber,"+
				// " ubc.bank_card_province bankCardProvince,"+
				// " ubc.bank_code bankCode,"+
				 " ubc.bank_id bankId,"+
				 " ubc.bank_name bankName,"+
				// " ubc.create_time createTime,"+
				// " ubc.customer_id customerId,"+
				 // " ubc.customer_sub_account_id customerSubAccountId,"+
				 " ubc.enable enable,"+
				 //" ubc.member_id memberId,"+
				 //" ubc.reserve_phone_number reservePhoneNumber,"+
				 " ubc.status statusName," +
				 " b.bank_pic_path bankPicPath," +
				 " b.bank_pic_HD_path bankHDPicPath," +
				 " b.bank_day_limit bankDayLimit," +
				 " b.bank_time_limit bankTimeLimit "+
				 " FROM user_bank_card ubc"+
				 " LEFT JOIN bank b ON b.id = ubc.bank_id"+
				 " left join customer_sub_account csa on ubc.customer_sub_account_id=csa.id"+
				 "	WHERE ubc.enable=TRUE AND b.enable=TRUE" +
			   " and (ubc.status ='BINDED' or ubc.status='NO_BIND' or ubc.status='PEDDING')"+
				
			   //" and pb.enable=TRUE " +
				 " and ubc.customer_id="+customerId+" and csa.product_channel_id="+productChannelId;
	
	SQLQuery sqlQuery = (SQLQuery) super.getSessionFactory().getCurrentSession().createSQLQuery(sql)
						.addScalar("id", Hibernate.INTEGER)
						.addScalar("bankCardNumber",Hibernate.STRING)
						.addScalar("bankId", Hibernate.INTEGER)
						.addScalar("bankName", Hibernate.STRING)
						.addScalar("enable", Hibernate.BOOLEAN)
						.addScalar("statusName", Hibernate.STRING)
						.addScalar("bankPicPath", Hibernate.STRING)
						.addScalar("bankHDPicPath", Hibernate.STRING)
						.addScalar("bankDayLimit", Hibernate.DOUBLE)
						.addScalar("bankTimeLimit", Hibernate.DOUBLE)
						.setResultTransformer(Transformers.aliasToBean(UserBankCardVo.class));
	
	return sqlQuery.list();
	}
}
