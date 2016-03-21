/**
 *
 */
package com.hwsoft.dao.activity;

import com.hwsoft.common.acitivity.PrizeStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.activity.Prize;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tzh
 */
@Repository("prizeDao")
public class PrizeDao extends BaseDao {

  /* (non-Javadoc)
   * @see com.hwsoft.dao.BaseDao#entityClass()
   */
  @Override
  protected Class<?> entityClass() {
    return Prize.class;
  }

  @SuppressWarnings("unchecked")
  public List<Prize> listAllProgreesing(int from, int pageSize) {
    String hql = " from Prize where status=:status ";
    return super.getSessionFactory().getCurrentSession().createQuery(hql)
        .setParameter("status", PrizeStatus.PROGREESING)
        .setFirstResult(from)
        .setMaxResults(pageSize)
        .list();
  }

  public Prize save(Prize prize){
    return super.add(prize);
  }

  @SuppressWarnings("unchecked")
  public List<Prize> listAll(int from, int pageSize) {
    String hql = " from Prize";
    return super.getSessionFactory().getCurrentSession().createQuery(hql)
        .setFirstResult(from)
        .setMaxResults(pageSize)
        .list();
  }

  public long getTotalCountProgreesing() {
    String hql = "select count(*) from Prize where status=:status ";
    Object object = super.getSessionFactory().getCurrentSession().createQuery(hql)
        .setParameter("status", PrizeStatus.PROGREESING)
        .uniqueResult();
    return Long.parseLong(object.toString());
  }

  public Long getTotalCount() {
    String hql = "select count(*) from Prize";
    Object object = super.getSessionFactory().getCurrentSession().createQuery(hql)
        .uniqueResult();
    return Long.parseLong(object.toString());
  }

  public Prize findById(int prizeId) {
    return super.get(prizeId);
  }

  public Prize update(Prize prize) {
    return super.update(prize);
  }


}
