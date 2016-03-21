/**
 *
 */
package com.hwsoft.common.acitivity;

import com.hwsoft.util.exception.ParamException;

/**
 * @author tzh
 */
public enum PrizeStatus {

  NOT_START() {
    @Override
    public String toString() {
      return "未开始兑换";
    }

  },
  PROGREESING() {
    @Override
    public String toString() {
      return "兑换中";
    }

  },
  HAS_END() {
    @Override
    public String toString() {
      return "结束兑换";
    }

  };

  public static   PrizeStatus from(String status) {
    for (PrizeStatus activityStatus : PrizeStatus.values()) {
      if (activityStatus.name().equals(status)) {
        return activityStatus;
      }
    }
    throw new ParamException("物品状态转换枚举异常:" + status);
  }

}
