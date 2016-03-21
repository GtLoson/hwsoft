package com.hwsoft.util.interest.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hwsoft.common.product.ProductRepayRecordStatus;
import com.hwsoft.model.product.Product;
import com.hwsoft.model.product.ProductRepayRecord;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.interest.InterestCalculatorMode;
import com.hwsoft.util.math.CalculateUtil;

/**
 * 按日计息,按月付息,到期还本
 * 
 * @author tzh
 * 
 */
public class DailyInterestMonthRepayInterestCalculator implements
		InterestCalculatorMode {

	@Override
	public List<ProductRepayRecord> calcutorInterest(Product product,
			int borrowCustomerSubAccountId) {
		// 获取周期,月份
		int months = product.getMaturityDuration();

		// 获取利率
		double interstRatio = product.getInterest();

		// 获取总金额=份数*每份金额=总金额-虚增金额？？？？
		double principal = product.getTotalAmount();
		if (null != product.getDummyBoughtAmount()
				&& product.isEnableDummyBoughtAmount()) {
			principal = CalculateUtil.doubleSubtract(product.getTotalAmount(),
					CalculateUtil.doubleMultiply(product.getDummyBoughtAmount(), product.getBaseAmount(), 4));
		}
		// 期初待还本金
		double initPrincipal = principal;

		// 期末待还本金
		double finalPrincipal = 0D;

		Date now = new Date();

		Date lastMonth = DateTools.getNextMonthtime(now, months);

		int totalDays = DateTools.getBetweenDayNumber(now, lastMonth);

		// 总利息=总金额*（利率/100)*天数/365
		double totalInterest = CalculateUtil.doubleDivide(CalculateUtil
				.doubleMultiply(
						CalculateUtil.doubleMultiply(principal, interstRatio),
						totalDays), CalculateUtil.doubleMultiply(365, 100), 4);

		// 日息=总利息/天数
		double dayInterest = CalculateUtil.doubleDivide(totalInterest,
				totalDays, 4);
		System.out.println(totalDays + "-" + totalInterest + "-" + dayInterest);
		// 月利息
		double monthInterst = 0D;// 这个需要计算天数 日息*天数

		// 剩余利息
		double leaveInterset = totalInterest;

		// 当期日期

		Date lastRepayDate = now;

		List<ProductRepayRecord> productRepayRecords = new ArrayList<ProductRepayRecord>();

		for (int i = 0; i < months; i++) { // 这个地方应该是月
			double monthPrincipal = 0D;

			// 应还日期
			Date repayDate = DateTools.getNextMonthtime(now, i + 1);

			if (i == months - 1) {
				monthInterst = leaveInterset;
				monthPrincipal = initPrincipal;
			} else {
				int days = DateTools.getBetweenDayNumber(lastRepayDate,
						repayDate);
				monthInterst = CalculateUtil.doubleMultiply(days, dayInterest,
						4);
			}
			finalPrincipal = CalculateUtil.doubleSubtract(initPrincipal,
					monthPrincipal, 4);
			leaveInterset = CalculateUtil.doubleSubtract(leaveInterset,
					monthInterst, 4);
			// 创建还款记录对象
			ProductRepayRecord productRepayRecord = new ProductRepayRecord();
			productRepayRecord.setAlreadyGuarantedAmount(0D);// 风险金实际垫付费用（实际支付费用+风险金实际垫付费用=本息）
			productRepayRecord.setAlreadyRepaidAmount(0D);// 用户实际支付费用（实际支付费用+风险金实际垫付费用=本息）
			// 固定值，统一处理
			productRepayRecord
					.setCustomerSubAccountId(borrowCustomerSubAccountId);// 借入者id（渠道子账户）
			productRepayRecord.setGuarantedDate(null);// 垫付还款日期
			productRepayRecord.setFinalPrincipal(finalPrincipal);// 期末待还本金 =
																	// 还完本期后剩余未还本金
			productRepayRecord.setGuarantorId(null);// 如果垫付，则记录该期的垫付人
			productRepayRecord.setInitialPrincipal(initPrincipal);// 期初待还本金 =
																	// 包含本期的所有剩余未还本金
			productRepayRecord.setPhaseNumber(i + 1);// 期
			productRepayRecord.setProductId(product.getId());
			productRepayRecord.setRealyRepayDate(null);// 实际还款日期
			productRepayRecord.setRepaidByGuarantor(false);// 该期是否被垫付
			productRepayRecord.setRepayDate(repayDate);// 应还款日期
			productRepayRecord.setRepayInterest(monthInterst);// 应还利息
			productRepayRecord.setRepayPrincipal(monthPrincipal);// 当期应还本金
			productRepayRecord.setRepayPrincipalAndInterest(CalculateUtil
					.doubleAdd(monthPrincipal, monthInterst, 4));// 应还还本息（本金+利息）
			productRepayRecord.setStatus(ProductRepayRecordStatus.UNREPAID);// 还款记录状态

			productRepayRecords.add(productRepayRecord);

			initPrincipal = finalPrincipal;
			lastRepayDate = repayDate;
		}

		return productRepayRecords;
	}

	public static void main(String[] args) {
		Product product = new Product();
		product.setTotalAmount(10000D);
		product.setDummyBoughtAmount(0D);
		product.setInterest(12D);
		product.setMaturityDuration(12);

		List<ProductRepayRecord> productRepayRecords = new DailyInterestMonthRepayInterestCalculator()
				.calcutorInterest(product, 1);

		for (ProductRepayRecord productRepayRecord : productRepayRecords) {
			System.out.println("第"
					+ productRepayRecord.getPhaseNumber()
					+ "期应还利息："
					+ productRepayRecord.getRepayInterest()
					+ "元，应还本金："
					+ productRepayRecord.getRepayPrincipal()
					+ "元，应还本息："
					+ productRepayRecord.getRepayPrincipalAndInterest()
					+ ",应还日期："
					+ DateTools.dateTime2String(productRepayRecord
							.getRepayDate()));

		}
		System.out.println(new DailyInterestMonthRepayInterestCalculator().calcutorTotalInterest(product, 1000000));
	}

	@Override
	public double calcutorTotalInterest(Product product, double amount) {
		// 获取周期,月份
		int months = product.getMaturityDuration();

		// 获取利率
		double interstRatio = product.getInterest();

		double principal = amount;

		Date now = new Date();

		Date lastMonth = DateTools.getNextMonthtime(now, months);

		int totalDays = DateTools.getBetweenDayNumber(now, lastMonth);

		// 总利息=总金额*（利率/100)*天数/365
		double totalInterest = CalculateUtil.doubleDivide(CalculateUtil
				.doubleMultiply(
						CalculateUtil.doubleMultiply(principal, interstRatio),
						totalDays), CalculateUtil.doubleMultiply(365, 100), 4);
		return totalInterest;
	}

}
