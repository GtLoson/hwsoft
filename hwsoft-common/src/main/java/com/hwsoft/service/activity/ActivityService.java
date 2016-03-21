/**
 *
 */
package com.hwsoft.service.activity;

import com.hwsoft.model.activity.Activity;

import java.util.Date;
import java.util.List;

/**
 * @author tzh
 */
public interface ActivityService {

  public List<Activity> listAllProgreesing(int from, int pageSize);

  public long getTotalCountProgreesing();

  public long getAllTotalCount();
  
  public Activity getLatestActivity();

  public List<Activity> listAll(int from, int pageSize);

  public Activity addActivity(String path, String name, String url, Date startDate, Date endDate, String status);

  public Activity getById(Integer activityId);

  public Activity updateActivity(Integer activityId,String picPath, String name, String url, String status, Date startDate, Date endDate);
}
