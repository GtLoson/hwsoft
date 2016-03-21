package com.hwsoft.util.interest;

import java.util.Date;
import java.util.List;

import com.hwsoft.common.product.ProductIncomeType;
import com.hwsoft.model.product.Product;
import com.hwsoft.model.product.ProductRepayRecord;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.interest.impl.*;
import com.hwsoft.util.math.CalculateUtil;


/**
 * 利息处理工具类
 * @author tzh
 *
 */
public class InterestCalculatorModeUtil {
	
	
	/**
	 * 处理还款记录
	 * @param product
	 * @param borrowCustomerSubAccountId
	 * @return
	 */
	public static List<ProductRepayRecord> calcutorInterest(Product product,int borrowCustomerSubAccountId){
		
		if(null == product || null == product.getProductIncomeType()){
			return null;
		}
		
		ProductIncomeType productIncomeType = product.getProductIncomeType();
		
		InterestCalculatorMode interestCalculatorMode = null;
		
		switch (productIncomeType) {
		/**
		 * 等额本息
		 */
		case AVERAGE_CAPITAL_PLUS_INTEREST:
			interestCalculatorMode = new AverageCapitalInterestCalculator();
			break;
		/**
		 * 按月计息，每月付息，到期还本
		 */
		case PWRIOD_REPAYS_CAPTITAL:
			interestCalculatorMode = new PwriodRepaysCaptitalInterestCalcuator();
			break;
		/**
		 * 按月计息，到期还本付息
		 */
		case PERIOD_REPAYS_CAPTITAL:
			interestCalculatorMode = new PeriodRepaysCapitialInterestCalcuator();
			break;
		/**
		 * 按日计息,到期还本还息
		 */
		case DAILY_INTEREST:
			interestCalculatorMode = new DailyInterestInterestCalculator();
			break;
		/**
		 * 按日计息,按月付息,到期还本
		 */
		case DAILY_INTEREST_MONTH_REPAY:
			interestCalculatorMode = new DailyInterestMonthRepayInterestCalculator();
			break;
		default:
			break;
		}
		
		return interestCalculatorMode.calcutorInterest(product, borrowCustomerSubAccountId);
		
	}
	
	
	/**
	 * 计算投资利息
	 * @param product
	 * @param amount
	 * @return
	 */
	public static double calcutorTotalInterest(Product product, double amount){
		if(null == product || null == product.getProductIncomeType()){
			return 0;
		}
		
		ProductIncomeType productIncomeType = product.getProductIncomeType();
		
		InterestCalculatorMode interestCalculatorMode = null;
		
		switch (productIncomeType) {
		/**
		 * 等额本息
		 */
		case AVERAGE_CAPITAL_PLUS_INTEREST:
			interestCalculatorMode = new AverageCapitalInterestCalculator();
			break;
		/**
		 * 按月计息，每月付息，到期还本
		 */
		case PWRIOD_REPAYS_CAPTITAL:
			interestCalculatorMode = new PwriodRepaysCaptitalInterestCalcuator();
			break;
		/**
		 * 按月计息，到期还本付息
		 */
		case PERIOD_REPAYS_CAPTITAL:
			interestCalculatorMode = new PeriodRepaysCapitialInterestCalcuator();
			break;
		/**
		 * 按日计息,到期还本还息
		 */
		case DAILY_INTEREST:
			interestCalculatorMode = new DailyInterestInterestCalculator();
			break;
		/**
		 * 按日计息,按月付息,到期还本
		 */
		case DAILY_INTEREST_MONTH_REPAY:
			interestCalculatorMode = new DailyInterestMonthRepayInterestCalculator();
			break;
		default:
			break;
		}
		
		return interestCalculatorMode.calcutorTotalInterest(product, amount);
	} 
	
	/**
	 * 计算当期实时利息
	 * @param
	 * @return
	 */
	public static double realTimeInterest(double totalInterest,Date startDate,Date endDate){
		
		//总天数
		int totalDays = DateTools.getBetweenDayNumber(DateTools.stringToDate(DateTools.dateToString(startDate,DateTools.DATE_PATTERN_DAY)+DateTools.DAY_FIRST_TIME, DateTools.DATE_PATTERN_DEFAULT), 
	    		DateTools.stringToDate(DateTools.dateToString(endDate,DateTools.DATE_PATTERN_DAY)+DateTools.DAY_FIRST_TIME, DateTools.DATE_PATTERN_DEFAULT));
		System.out.println("总天数"+totalDays);
		//
		
		int usedDays = DateTools.getBetweenDayNumber(DateTools.stringToDate(DateTools.dateToString(startDate,DateTools.DATE_PATTERN_DAY)+DateTools.DAY_FIRST_TIME, DateTools.DATE_PATTERN_DEFAULT), 
	    		DateTools.stringToDate(DateTools.dateToString(new Date(),DateTools.DATE_PATTERN_DAY)+DateTools.DAY_FIRST_TIME, DateTools.DATE_PATTERN_DEFAULT));
		System.out.println("使用天数："+usedDays);
		if(usedDays == 0){
			return 0;
		}
		return CalculateUtil.doubleDivide(CalculateUtil.doubleMultiply(totalInterest, usedDays),totalDays,4);
		
	}

	/**
	 * 计算日息
	 * @param
	 * @return
	 */
	public static double dailyInterest(double totalInterest,Date startDate,Date endDate){
		
		//总天数
		int totalDays = DateTools.getBetweenDayNumber(startDate, endDate);
		System.out.println(totalDays);
		//
//		int usedDays = DateTools.getBetweenDayNumber(startDate, new Date());
//		System.out.println(usedDays);
		return CalculateUtil.doubleDivide(totalInterest,totalDays,4);
	}

	/**
	 * 银行活期收益计算
	 * @param amount
	 * @param product
	 * @return
	 */
	public static double bankInterest(double amount,Product product){
		InterestCalculatorMode interestCalculatorMode = new BankInterestCalcalator();
		return interestCalculatorMode.calcutorTotalInterest(product,amount);
	}
	
	public static void main(String[] args) {
		try {
			System.out.println();
			System.out.println(dailyInterest(3407.4521,DateTools.stringToDateDay("2015-05-31"),DateTools.stringToDateDay("2015-08-31")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
