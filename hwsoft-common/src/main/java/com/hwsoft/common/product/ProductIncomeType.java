/**
 *
 */
package com.hwsoft.common.product;

import com.google.common.base.Strings;

/**
 * 产品收益方式(付息方式)
 *
 * @author tzh
 */
public enum ProductIncomeType {
  
  

  AVERAGE_CAPITAL_PLUS_INTEREST {
    @Override
    public String toString() {
      return "等额本息";
    }

    @Override
    public ProductDateUnit productDateUnit() {
      return ProductDateUnit.MONTHS;
    }
  },
  PWRIOD_REPAYS_CAPTITAL {
    @Override
    public String toString() {
      return "按月计息，每月付息，到期还本";
    }

    @Override
    public ProductDateUnit productDateUnit() {
      return ProductDateUnit.MONTHS;
    }
  },
  PERIOD_REPAYS_CAPTITAL() {
    @Override
    public String toString() {
      return "按月计息，到期还本付息";
    }

    @Override
    public ProductDateUnit productDateUnit() {
      return ProductDateUnit.MONTHS;
    }
  },
  DAILY_INTEREST() {
    @Override
    public String toString() {
      return "按日计息,到期还本还息";
    }

    @Override
    public ProductDateUnit productDateUnit() {
      return ProductDateUnit.DAY;
    }
  },
  DAILY_INTEREST_MONTH_REPAY() {
    @Override
    public String toString() {
      return "按日计息,按月付息,到期还本";
    }

    @Override
    public ProductDateUnit productDateUnit() {
      return ProductDateUnit.MONTHS;
    }
  };

  public abstract ProductDateUnit productDateUnit();
  

  public static ProductIncomeType buildFromString(String typeString) {
    if (!Strings.isNullOrEmpty(typeString)) {
      for (ProductIncomeType productIncomeType : ProductIncomeType.values()) {
        if (productIncomeType.toString().endsWith(typeString)) {
          return productIncomeType;
        }
      }
    }
    return null;
  }
}
