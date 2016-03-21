/**
 *
 */
package com.hwsoft.common.acitivity;

import com.hwsoft.util.exception.ParamException;

/**
 * @author tzh
 */
public enum ActivityStatus {

  NOT_START() {
    @Override
    public String toString() {
      return "未开始";
    }

  },
  PROGREESING() {
    @Override
    public String toString() {
      return "进行中";
    }

  },
  HAS_END() {
    @Override
    public String toString() {
      return "已经结束";
    }
  };


  public static ActivityStatus from(String status) {
    for (ActivityStatus activityStatus : ActivityStatus.values()) {
      if (activityStatus.name().equals(status)) {
        return activityStatus;
      }
    }
    throw new ParamException("活动状态转换枚举异常:" + status);
  }

}
