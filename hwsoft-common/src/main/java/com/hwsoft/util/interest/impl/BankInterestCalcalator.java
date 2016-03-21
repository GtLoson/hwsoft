package com.hwsoft.util.interest.impl;

import com.hwsoft.common.product.ProductDateUnit;
import com.hwsoft.model.product.Product;
import com.hwsoft.model.product.ProductRepayRecord;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.interest.InterestCalculatorMode;
import com.hwsoft.util.math.CalculateUtil;

import java.util.Date;
import java.util.List;

/**
 * 银行获取收益计算器
 */
public class BankInterestCalcalator implements InterestCalculatorMode {

  @Override
  public List<ProductRepayRecord> calcutorInterest(Product product, int borrowCustomerSubAccountId) {
    return null;
  }

  @Override
  public double calcutorTotalInterest(Product product, double amount) {

    int duration = product.getMaturityDuration();

    // 获取利率
    double interstRatio = 0.35;// product.getInterest();

    double principal = amount;

    int days = 0;

    // 总利息
    double totalInterest = 0;//
    
    if(ProductDateUnit.DAY.equals(product.getMaturityUnit())){
      days = duration;
    }else if(ProductDateUnit.MONTHS.equals(product.getMaturityUnit())){
      days = duration *30;
    }

    totalInterest = CalculateUtil.doubleDivide(CalculateUtil
        .doubleMultiply(
            CalculateUtil.doubleMultiply(principal, interstRatio),
            days), CalculateUtil.doubleMultiply(365, 100), 4);

    return totalInterest;
  }
}
