/**
 *
 */
package com.hwsoft.dao.validate;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.validate.MobileValidate;
import com.hwsoft.util.string.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author tzh
 */
@Repository("mobileValidateDao")
public class MobileValidateDao extends BaseDao {

  /* (non-Javadoc)
   * @see com.hwsoft.dao.BaseDao#entityClass()
   */
  @Override
  protected Class<?> entityClass() {
    return MobileValidate.class;
  }

  public MobileValidate save(MobileValidate mobileValidate) {
    return super.add(mobileValidate);
  }

  public MobileValidate update(MobileValidate mobileValidate) {
    return super.update(mobileValidate);
  }

  public MobileValidate findByMobile(final String mobile) {
    String hql = "from MobileValidate where mobile=:mobile";
    return (MobileValidate) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("mobile", mobile).uniqueResult();
  }

  public long getCount(String mobile, Date start, Date end) {
    Session session = getSessionFactory().getCurrentSession();
    String hql = "select count(*) from MobileValidate where mobile  = :mobile and sendTime >= :start and sendTime <= :end";
    if (StringUtils.isEmpty(mobile)) {
      hql = "select count(*) from MobileValidate where  sendTime >= :start and sendTime <= :end";
      Query query = session.createQuery(hql).setParameter("start", start).setParameter("end", end);
      Object count = query.uniqueResult();
      return (Long) count;
    }
    Query query = session.createQuery(hql).setParameter("mobile", mobile).setParameter("start", start).setParameter("end", end);
    Object count = query.uniqueResult();
    return (Long) count;
  }

  public List<MobileValidate> listAllOfMobile(String mobile, int pageSize, int from, Date start, Date end) {
    Session session = getSessionFactory().getCurrentSession();
    String hql = "from MobileValidate where mobile = :mobile and sendTime >= :start and sendTime <= :end order by sendTime desc";
    if (StringUtils.isEmpty(mobile)) {
      hql = "from MobileValidate where sendTime >= :start and sendTime <= :end order by sendTime desc";
      Query query = session.createQuery(hql).setFirstResult(from).setMaxResults(pageSize).setParameter("start", start).setParameter("end", end);
      List<MobileValidate> MobileValidates = query.list();
      return MobileValidates;
    }

    Query query = session.createQuery(hql).setFirstResult(from).setParameter("mobile", mobile).setMaxResults(pageSize).setParameter("start", start).setParameter("end", end);
    List<MobileValidate> MobileValidates = query.list();
    return MobileValidates;
  }
}
