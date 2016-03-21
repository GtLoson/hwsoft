package com.hwsoft.common.product;

/**
 * 产品角标类型
 */
public enum ProductCornerType {

  ACTIVITY() {
    @Override
    public String toString() {
      return "活动标";
    }
  },

  TASTE() {
    @Override
    public String toString() {
      return "体验标";
    }
  },

  SALE_OVER() {
    @Override
    public String toString() {
      return "售罄";
    }
  },

  LENDING() {
    @Override
    public String toString() {
      return "还款中";
    }
  },
  CLOSED() {
    @Override
    public String toString() {
      return "已结标";
    }
  }
  
}
