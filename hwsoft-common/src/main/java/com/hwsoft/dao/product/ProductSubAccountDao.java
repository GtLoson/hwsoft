/**
 *
 */
package com.hwsoft.dao.product;

import com.hwsoft.common.product.ProductBuyerRecordStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductSubAccount;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.string.StringUtils;
import com.hwsoft.vo.product.ProductSubAccountDetail;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

/**
 * @author tzh
 */
@Repository("productSubAccountDao")
public class ProductSubAccountDao extends BaseDao {

  /* (non-Javadoc)
   * @see com.hwsoft.dao.BaseDao#entityClass()
   */
  @Override
  protected Class<?> entityClass() {
    return ProductSubAccount.class;
  }

  public ProductSubAccount add(ProductSubAccount productBuyerRecord) {
    return super.add(productBuyerRecord);
  }

  public ProductSubAccount update(ProductSubAccount productBuyerRecord) {
    return super.update(productBuyerRecord);
  }

  public ProductSubAccount findById(final int productBuyerRecordId) {
    return super.get(productBuyerRecordId);
  }

  public ProductSubAccount findByOrderFormId(final String orderFormId) {
    String hql = " from ProductSubAccount where orderFormId=:orderFormId";
    return (ProductSubAccount) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("orderFormId", orderFormId).uniqueResult();
  }

  /**
   * 根据状态集合查询订单号集合
   *
   * @param statusSet
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<String> findOrderFormIdByOrderStatus(EnumSet<ProductBuyerRecordStatus> statusSet) {
    if (null == statusSet || statusSet.size() == 0) {
      return null;
    }
    String sql = " select order_form_id from product_sub_account where status in (";
    for (ProductBuyerRecordStatus status : statusSet) {
      sql += "'" + status.name() + "',";
    }
    sql = sql.substring(0, sql.length() - 1) + ")";
   //  System.out.println(sql);
    return super.getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
  }


  /**
   * 获取该状态的订单的数量
   *
   * @param productBuyerRecordStatus
   * @return
   */
  public long getCountOfStatus(ProductBuyerRecordStatus productBuyerRecordStatus) {
    String hql = "select count(id) from ProductSubAccount where status=:status";
    Object object = super.getSessionFactory().getCurrentSession().createQuery(hql)
        .setParameter("status", productBuyerRecordStatus)
        .uniqueResult();

    return Long.parseLong(object.toString());
  }

  /**
   * 所有订单
   *
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<String> listAllOrderFormId() {
    String sql = " select order_form_id from product_sub_account";
    return super.getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
  }

  @SuppressWarnings("unchecked")
  public List<ProductSubAccount> findProductSubAccountByProductId(
      int productId) {
    String hql = " from ProductSubAccount where productId=:productId";
    return super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("productId", productId).list();
  }

  public Long getTotalCount() {
    return super.getTotalCount();
  }

  /**
   * 订单列表*
   *
   * @param from
   * @param pageSize
   * @return
   */
  public List<ProductSubAccount> listAll(int from, int pageSize) {
    String hql = "from ProductSubAccount";
    Session session = getSessionFactory().getCurrentSession();
    Query query = session.createQuery(hql).setFirstResult(from).setMaxResults(pageSize);
    List<ProductSubAccount> productSubAccounts = query.list();
    return productSubAccounts;
  }


  public int orderListCount(ProductBuyerRecordStatus productBuyerRecordStatus,
                                                      String mobile,
                                                      Date startDate,
                                                      Date endDate, 
                                                      String productName){
    String baseSql = "SELECT psa.id " +
        "   FROM  " +
        "product_sub_account psa " +
        "LEFT  JOIN customer c " +
        "ON psa.customer_id = c.id " +
        "LEFT JOIN product p " +
        "on p.id = psa.product_id " +
        "LEFT JOIN user_bank_card ubc " +
        "on ubc.id = psa.user_bank_card_id " +
        "LEFT JOIN order_buy_plan obp " +
        "on obp.product_buyer_record_id=psa.id " +
        "LEFT JOIN channel ch " +
        "on ch.channel_id = c.channel_id  WHERE 1=1 ";

    if (null != productBuyerRecordStatus) {
        baseSql = baseSql + " and psa.status = '" + productBuyerRecordStatus.name() +"'";
      }

      if (!StringUtils.isEmpty(mobile)) {
        baseSql = baseSql + " and c.mobile = '" + mobile+"'";
      }

      if (null != startDate) {
        baseSql += " and psa.buy_time>='" + DateTools.dateToString(startDate) + "'";
      }
      if (null != endDate) {
        baseSql += " and psa.buy_time<='" + DateTools.dateToString(endDate) + "'";
      }

      if (!StringUtils.isEmpty(productName)) {
        baseSql = baseSql+" and p.product_name = '"+productName+"'";
      }


    // System.out.println(baseSql);
    
    return super.getSessionFactory().getCurrentSession().createSQLQuery(baseSql).list().size();
  }



  @SuppressWarnings({"deprecation", "unchecked"})
  public List<ProductSubAccountDetail> orderDetailInfoList(int from,
                                                           int pageSize,
                                                           ProductBuyerRecordStatus productBuyerRecordStatus, String mobile, Date startDate, Date endDate, String productName) {
    String baseSql = "SELECT p.product_name productName," +
        "ubc.bank_name bankName," +
        " psa.bank_card_number cardNumber," +
        " psa.amount amount," +
        " psa.success_amount successAmount," +
        " psa.buy_time buyTime, " +
        " c.real_name realName," +
        " c.mobile mobile," +
        " c.channel_id channelId," +
        "  ch.name channelName," +
        "  psa.total_interest totalInterest," +
        "  psa.daily_interest dayInterest," +
        "  p.start_interest_bearing_date startDate," +
        "  p.end_interest_bearing_date endDate," +
        "  psa.status status," +
        "  obp.response response " +
        "   FROM  " +
        "product_sub_account psa " +
        "LEFT  JOIN customer c " +
        "ON psa.customer_id = c.id " +
        "LEFT JOIN product p " +
        "on p.id = psa.product_id " +
        "LEFT JOIN user_bank_card ubc " +
        "on ubc.id = psa.user_bank_card_id " +
        "LEFT JOIN order_buy_plan obp " +
        "on obp.product_buyer_record_id=psa.id " +
        "LEFT JOIN channel ch " +
        "on ch.channel_id = c.channel_id  WHERE 1=1 ";

    if (null != productBuyerRecordStatus) {
      baseSql = baseSql + " and psa.status = '" + productBuyerRecordStatus.name() +"'";
    }

    if (!StringUtils.isEmpty(mobile)) {
      baseSql = baseSql + " and c.mobile = '" + mobile+"'";
    }

    if (null != startDate) {
      baseSql += " and psa.buy_time>='" + DateTools.dateToString(startDate) + "'";
    }
    if (null != endDate) {
      baseSql += " and psa.buy_time<='" + DateTools.dateToString(endDate) + "'";
    }

    if (!StringUtils.isEmpty(productName)) {
      baseSql = baseSql+" and p.product_name = '"+productName+"'";
    }


    String limitSql = " order by psa.buy_time DESC " + "limit "
        + from + "," + pageSize;

    // System.out.println(baseSql+limitSql);
    
    
    return super.getSessionFactory().getCurrentSession().createSQLQuery(baseSql+limitSql)
        .addScalar("productName", Hibernate.STRING)
        .addScalar("bankName", Hibernate.STRING)
        .addScalar("cardNumber", Hibernate.STRING)
        .addScalar("amount", Hibernate.DOUBLE)
        .addScalar("successAmount", Hibernate.DOUBLE)
        .addScalar("buyTime", Hibernate.TIMESTAMP)
        .addScalar("realName", Hibernate.STRING)
        .addScalar("mobile", Hibernate.STRING)
        .addScalar("channelId", Hibernate.INTEGER)
        .addScalar("channelName", Hibernate.STRING)
        .addScalar("totalInterest", Hibernate.DOUBLE)
        .addScalar("dayInterest", Hibernate.DOUBLE)
        .addScalar("startDate", Hibernate.DATE)
        .addScalar("endDate", Hibernate.DATE)
        .addScalar("status", Hibernate.STRING)
        .addScalar("response", Hibernate.STRING)
        .setResultTransformer(Transformers.aliasToBean(ProductSubAccountDetail.class)).list();
  }

  @SuppressWarnings({"deprecation", "unchecked"})
  public List<ProductSubAccountDetail> orderDetailInfoList() {
    String baseSql = "SELECT p.product_name productName," +
        "ubc.bank_name bankName," +
        " psa.bank_card_number cardNumber," +
        " psa.amount amount," +
        " psa.success_amount successAmount," +
        " psa.buy_time buyTime, " +
        " c.real_name realName," +
        " c.mobile mobile," +
        " c.channel_id channelId," +
        "  ch.name channelName," +
        "  psa.total_interest totalInterest," +
        "  psa.daily_interest dayInterest," +
        "  p.start_interest_bearing_date startDate," +
        "  p.end_interest_bearing_date endDate," +
        "  psa.status status," +
        "  obp.response response " +
        "   FROM  " +
        "product_sub_account psa " +
        "LEFT  JOIN customer c " +
        "ON psa.customer_id = c.id " +
        "LEFT JOIN product p " +
        "on p.id = psa.product_id " +
        "LEFT JOIN user_bank_card ubc " +
        "on ubc.id = psa.user_bank_card_id " +
        "LEFT JOIN order_buy_plan obp " +
        "on obp.product_buyer_record_id=psa.id " +
        "LEFT JOIN channel ch " +
        "on ch.channel_id = c.channel_id  WHERE 1=1";



    // System.out.println(baseSql);


    return super.getSessionFactory().getCurrentSession().createSQLQuery(baseSql)
        .addScalar("productName", Hibernate.STRING)
        .addScalar("bankName", Hibernate.STRING)
        .addScalar("cardNumber", Hibernate.STRING)
        .addScalar("amount", Hibernate.DOUBLE)
        .addScalar("successAmount", Hibernate.DOUBLE)
        .addScalar("buyTime", Hibernate.DATE)
        .addScalar("realName", Hibernate.STRING)
        .addScalar("mobile", Hibernate.STRING)
        .addScalar("channelId", Hibernate.INTEGER)
        .addScalar("channelName", Hibernate.STRING)
        .addScalar("totalInterest", Hibernate.DOUBLE)
        .addScalar("dayInterest", Hibernate.DOUBLE)
        .addScalar("startDate", Hibernate.DATE)
        .addScalar("endDate", Hibernate.DATE)
        .addScalar("status", Hibernate.STRING)
        .addScalar("response", Hibernate.STRING)
        .setResultTransformer(Transformers.aliasToBean(ProductSubAccountDetail.class)).list();
  }
  
  
	public List<ProductSubAccount> findByProductAccountId(int productAccountId) {
		String hql = " from ProductSubAccount where productBuyerId = :productAccountId";
		return super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("productAccountId", productAccountId).list();
	}
  	public List<ProductSubAccount> findByCustomerId(int customerId) {
	  
  		String hql = " from ProductSubAccount where customerId=:customerId"; 
		return super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("customerId", customerId).list();
	}
  	
  	public List<ProductSubAccount> findByProductId(int productId) {
  		String hql = " from ProductSubAccount where productId=:productId"; 
		return super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("productId", productId).list();
	}
  
}
