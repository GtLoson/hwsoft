/**
 *
 */
package com.hwsoft.dao.activity;

import com.hwsoft.common.acitivity.ActivityStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.activity.Activity;
import com.hwsoft.util.date.DateTools;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author tzh
 */
@Repository("activityDao")
public class ActivityDao extends BaseDao {

  /* (non-Javadoc)
   * @see com.hwsoft.dao.BaseDao#entityClass()
   */
  @Override
  protected Class<?> entityClass() {
    return Activity.class;
  }

  @SuppressWarnings("unchecked")
  public List<Activity> listAllProgreesing(int from, int pageSize) {
    String hql = " from Activity where status=:status and endDate > '" + DateTools.dateTime2String(new Date()) + "' order by createTime DESC ";
    return super.getSessionFactory().getCurrentSession().createQuery(hql)
        .setParameter("status", ActivityStatus.PROGREESING)
        .setFirstResult(from)
        .setMaxResults(pageSize)
        .list();
  }

  public long getAllCount() {
    String hql = "select count(*) from Activity";
    Object object = super.getSessionFactory().getCurrentSession().createQuery(hql)
        .uniqueResult();
    return Long.parseLong(object.toString());
  }

  public long getTotalCountProgreesing() {
    String hql = "select count(*) from Activity where status=:status and endDate > '" + DateTools.dateTime2String(new Date()) + "'";
    Object object = super.getSessionFactory().getCurrentSession().createQuery(hql)
        .setParameter("status", ActivityStatus.PROGREESING)
        .uniqueResult();
    return Long.parseLong(object.toString());
  }

  @SuppressWarnings("unchecked")
  public List<Activity> listAll(int from, int pageSize) {
    String hql = "from Activity";
    return super.getSessionFactory().getCurrentSession().createQuery(hql)
        .setFirstResult(from)
        .setMaxResults(pageSize)
        .list();
  }

  public Activity getById(Integer activityId) {
    return super.get(activityId);
  }

  public Activity addActivity(Activity activity) {
    return super.add(activity);
  }

  public Activity updateActivity(Activity activity) {
    return super.update(activity);
  }

  @SuppressWarnings("unchecked")
  public Activity getLatestActivity() {
    String hql = " from Activity where status=:status and endDate > '" + DateTools.dateTime2String(new Date()) + "'" + " order by createTime desc";
    List<Activity> activityList = (List<Activity>)super.getSessionFactory().getCurrentSession().createQuery(hql)
        .setParameter("status", ActivityStatus.PROGREESING)
        .list();
    if(null != activityList && activityList.size() > 0){
      return activityList.get(0);
    }else{
      return null;
    }
  }
}
