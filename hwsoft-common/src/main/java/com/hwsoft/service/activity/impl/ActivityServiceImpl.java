/**
 *
 */
package com.hwsoft.service.activity.impl;

import com.hwsoft.common.acitivity.ActivityStatus;
import com.hwsoft.dao.activity.ActivityDao;
import com.hwsoft.exception.activity.ActivityException;
import com.hwsoft.model.activity.Activity;
import com.hwsoft.service.activity.ActivityService;
import com.hwsoft.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author tzh
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

  @Autowired
  private ActivityDao activityDao;

  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public Activity getLatestActivity(){
    return activityDao.getLatestActivity();
  }
  /* (non-Javadoc)
   * @see com.hwsoft.service.activity.ActivityService#getTotalCountProgreesing()
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public long getTotalCountProgreesing() {
    return activityDao.getTotalCountProgreesing();
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.activity.ActivityService#listAllProgreesing(int, int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<Activity> listAllProgreesing(int from, int pageSize) {
    return activityDao.listAllProgreesing(from, pageSize);
  }

  /**
   * @return allCount
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public long getAllTotalCount() {
    return activityDao.getAllCount();
  }

  /**
   * @param from     from
   * @param pageSize pageSize
   * @return list
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<Activity> listAll(int from, int pageSize) {
    return activityDao.listAll(from, pageSize);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Activity addActivity(String path, String name, String url, Date startDate, Date endDate, String status) {

    checkActivityParameter(path, "文件上传错误");
    checkActivityParameter(name, "活动名称不能为空");
    checkActivityParameter(url, "落地页地址不能为空");
    checkActivityParameter(startDate, "开始日期为空");
    checkActivityParameter(endDate, "结束日期为空");
    checkActivityParameter(status, "状态字符串不能为空");

    Activity activity = new Activity();
    Date now = new Date();
    activity.setCreateTime(now);
    activity.setUpdateTime(now);
    activity.setName(name);
    activity.setUrl(url);
    activity.setPicPath(path);
    activity.setStartDate(startDate);
    activity.setEndDate(endDate);
    activity.setStatus(ActivityStatus.from(status));
    activityDao.addActivity(activity);
    return activity;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public Activity getById(Integer activityId) {
    return activityDao.getById(activityId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Activity updateActivity(Integer activityId,
                                 String picPath,
                                 String name,
                                 String url,
                                 String status,
                                 Date startDate,
                                 Date endDate) {

    Activity currentActivity = getById(activityId);
    if (null == currentActivity) {
      throw new ActivityException("Id：" + activityId + "的活动不存在");
    }

    checkActivityParameter(name, "活动名称不能为空");
    checkActivityParameter(url, "落地页地址不能为空");
    checkActivityParameter(startDate, "开始日期为空");
    checkActivityParameter(endDate, "结束日期为空");
    checkActivityParameter(status, "状态字符串不能为空");

    currentActivity.setName(name);
    currentActivity.setUrl(url);
    currentActivity.setStartDate(startDate);
    currentActivity.setEndDate(endDate);
    currentActivity.setUpdateTime(new Date());
    if (null != picPath) {
      currentActivity.setPicPath(picPath);
    }
    currentActivity.setStatus(ActivityStatus.from(status));

    return activityDao.updateActivity(currentActivity);
  }

  private void checkActivityParameter(String parameter, String msg) {
    if (StringUtils.isEmpty(parameter)) {
      throw new ActivityException(msg);
    }
  }

  private void checkActivityParameter(Object parameter, String msg) {
    if (null == parameter) {
      throw new ActivityException(msg);
    }
  }
}
