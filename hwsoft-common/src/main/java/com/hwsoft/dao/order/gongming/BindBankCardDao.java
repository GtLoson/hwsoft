/**
 *
 */
package com.hwsoft.dao.order.gongming;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.order.gongming.BindBankCardOrder;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.vo.order.BindBankCardOrderVo;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author tzh
 */
@Repository("bindBankCardDao")
public class BindBankCardDao extends BaseDao {

  /* (non-Javadoc)
   * @see com.hwsoft.dao.BaseDao#entityClass()
   */
  @Override
  protected Class<?> entityClass() {
    return BindBankCardOrder.class;
  }

  public BindBankCardOrder save(BindBankCardOrder bindBankCardOrder) {
    return super.add(bindBankCardOrder);
  }

  public BindBankCardOrder update(BindBankCardOrder bindBankCardOrder) {
    return super.update(bindBankCardOrder);
  }

  public BindBankCardOrder findByOrderFormId(final String orderFormId) {
    String hql = " from BindBankCardOrder where orderFormId=:orderFormId";
    return (BindBankCardOrder) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("orderFormId", orderFormId).uniqueResult();
  }


  /**
   * 获取产品
   *
   * @param from
   * @param pageSize
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<BindBankCardOrderVo> list(OrderStatus orderStatus, Date startTime, Date endTime, int from, int pageSize, String mobile) {


    String sql = "   SELECT obbc.id id,c.id userId,c.real_name realName,c.mobile mobile ," +
        "	obbc.order_form_id orderFormId,obbc.order_status orderStatus," +
        "	obbc.user_bank_card_id userBankCardId,obbc.bank_card_number bankCardNumber," +
        "	obbc.bank_brance_name bankBranchName,obbc.bank_phone_number bankPhoneNumber," +
        "	ubc.bank_name bankName," +
        "	ubc.bank_card_province province,ubc.bank_card_city city," +
        "	obbc.create_time createTime," +
        "	obbc.back_code backCode, obbc.back_msg backMsg,obbc.response response" +
        " FROM order_bind_bank_card obbc " +
        " LEFT JOIN customer c ON obbc.user_id=c.id" +
        " LEFT JOIN user_bank_card ubc ON ubc.id = obbc.user_bank_card_id";
    sql += " where 1=1 ";

    if (null != orderStatus) {
      sql += " and obbc.order_status='" + orderStatus.name() + "'";
    }

    if (null != startTime) {
      sql += " and obbc.create_time>='" + DateTools.dateToString(startTime) + "'";
    }
    if (null != endTime) {
      sql += " and obbc.create_time<='" + DateTools.dateToString(endTime) + "'";
    }
    if (null != mobile) {
      sql += " and c.mobile ='" + mobile + "'";
    }
    //TODO 处理查询条件
    @SuppressWarnings("deprecation")
    SQLQuery sqlQuery = (SQLQuery) getSessionFactory().getCurrentSession().createSQLQuery(sql)
        .addScalar("userBankCardId", Hibernate.INTEGER)
        .addScalar("bankCardNumber", Hibernate.STRING)
        .addScalar("userId", Hibernate.INTEGER)
        .addScalar("bankBranchName", Hibernate.STRING)
        .addScalar("mobile", Hibernate.STRING)
        .addScalar("backCode", Hibernate.STRING)
        .addScalar("backMsg", Hibernate.STRING)
        .addScalar("response", Hibernate.STRING)
        .addScalar("realName", Hibernate.STRING)
        .addScalar("bankPhoneNumber", Hibernate.STRING)
        .addScalar("createTime", Hibernate.TIMESTAMP)
        .addScalar("id", Hibernate.INTEGER)
        .addScalar("orderFormId", Hibernate.STRING)
        .addScalar("orderStatus", Hibernate.STRING)
        .addScalar("bankName", Hibernate.STRING)
        .addScalar("province", Hibernate.STRING)
        .addScalar("city", Hibernate.STRING)

        .setResultTransformer(Transformers.aliasToBean(BindBankCardOrderVo.class)).setFirstResult(from).setMaxResults(pageSize);
    return sqlQuery.list();
  }

  /**
   * 总条数
   *
   * @return
   */
  public Long getTotalCount(OrderStatus orderStatus, Date startTime, Date endTime,String mobile) {

    String sql = "SELECT count(obbc.id)" +
        " FROM order_bind_bank_card obbc " +
        " LEFT JOIN customer c ON obbc.user_id=c.id" +
        " LEFT JOIN user_bank_card ubc ON ubc.id = obbc.user_bank_card_id";
    sql += " where 1=1 ";

    if (null != orderStatus) {
      sql += " and obbc.order_status='" + orderStatus.name() + "'";
    }

    if (null != startTime) {
      sql += " and obbc.create_time>='" + DateTools.dateToString(startTime) + "'";
    }
    if (null != endTime) {
      sql += " and obbc.create_time<='" + DateTools.dateToString(endTime) + "'";
    }
    if (null != mobile) {
      sql += " and c.mobile ='" + mobile + "'";
    }
    Object object = getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult();
    return null == object ? 0 : Long.parseLong(object.toString());
  }

}
