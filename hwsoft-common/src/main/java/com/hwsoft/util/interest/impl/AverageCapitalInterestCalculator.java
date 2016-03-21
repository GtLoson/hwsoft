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
 * 等额本息
 * 
 * @author tzh
 * 
 */
public class AverageCapitalInterestCalculator implements InterestCalculatorMode {

	@Override
	public List<ProductRepayRecord> calcutorInterest(Product product,
			int borrowCustomerSubAccountId) {

		// 利率
		double interest = product.getInterest();

		// 期限
		int months = product.getMaturityDuration();

		// 获取总金额=份数*每份金额=总金额-虚增金额？？？？
		double principal = product.getTotalAmount();
		if (null != product.getDummyBoughtAmount()
				&& product.isEnableDummyBoughtAmount()) {
			principal = CalculateUtil.doubleSubtract(product.getTotalAmount(),
					CalculateUtil.doubleMultiply(product.getDummyBoughtAmount(), product.getBaseAmount(), 4));
		}
		/**
		 * 期初待还本金 = 上一起期末待还本金
		 */
		double initialPrincipal = principal;
		/**
		 * 期末待还本息 每一期的减本期
		 */
		double finalPrincipal = 0d;

		// 月利率
		double interestOfMonth = CalculateUtil.doubleDivide(interest, 12 * 100,
				10);

		// 计算次方
		double pow4Principal = Math.pow(
				CalculateUtil.doubleAdd(1, interestOfMonth), months);

		/**
		 * 每月应还金额（本金+利息）
		 */
		double repayPrincipalInterest = CalculateUtil.doubleMultiply(
				CalculateUtil.doubleMultiply(principal, interestOfMonth),
				pow4Principal)
				/ (pow4Principal - 1);

		/**
		 * 总利息
		 */
		double totalInterest = CalculateUtil.doubleDivide(CalculateUtil
				.doubleMultiply(principal, CalculateUtil.doubleAdd(
						CalculateUtil.doubleMultiply(CalculateUtil
								.doubleSubtract(CalculateUtil.doubleMultiply(
										months, interestOfMonth), 1),
								pow4Principal), 1)), pow4Principal - 1, 4);
		// 当期日期
		Date now = new Date();
		List<ProductRepayRecord> productRepayRecords = new ArrayList<ProductRepayRecord>();
		for (int i = 0; i < months; i++) {
			double repayPrincipal = 0;
			double pow4Earnings = Math.pow((1 + interestOfMonth), i);
			double earnings = 0D;
			if (i == months - 1) {
				// 最后一期抹平账
				earnings = totalInterest;// CalculateUtil.doubleSubtract(totalInterest,
											// earnings, 2 );
				repayPrincipal = initialPrincipal;
				finalPrincipal = 0;
			} else {
				/**
				 * 计算本月应还利息 计算公式：贷款金额 *月利率*((1+月利率)的总期数次方 -1+月利率的
				 * 当前期数-1次方)/((1+月利率)的总期数次方-1)
				 */
				// earnings =
				// CalculateUtil.doubleDivide(
				// CalculateUtil.doubleMultiply(
				// CalculateUtil.doubleMultiply(principal, interestOfMonth),
				// CalculateUtil.doubleSubtract(pow4Principal, pow4Earnings)
				// ),
				// CalculateUtil.doubleSubtract(pow4Principal, 1),
				// 4);
				earnings = CalculateUtil.doubleDivide(CalculateUtil
						.doubleMultiply(CalculateUtil.doubleMultiply(principal,
								interestOfMonth), CalculateUtil.doubleSubtract(
								pow4Principal, pow4Earnings)), CalculateUtil
						.doubleSubtract(pow4Principal, 1), 4);

				repayPrincipal = CalculateUtil.doubleSubtract(
						repayPrincipalInterest, earnings, 4);
//				System.out.println(repayPrincipalInterest + "-"
//						+ repayPrincipal + "-" + earnings);
				// 计算期末应还本本金
				finalPrincipal = CalculateUtil.doubleSubtract(initialPrincipal,
						repayPrincipal, 4);

			}

			Date repayDate = DateTools.getNextMonthtime(now, i + 1);

			totalInterest = CalculateUtil.doubleSubtract(totalInterest,
					earnings, 4);

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
			productRepayRecord.setInitialPrincipal(initialPrincipal);// 期初待还本金 =
																		// 包含本期的所有剩余未还本金
			productRepayRecord.setPhaseNumber(i + 1);// 期
			productRepayRecord.setProductId(product.getId());
			productRepayRecord.setRealyRepayDate(null);// 实际还款日期
			productRepayRecord.setRepaidByGuarantor(false);// 该期是否被垫付
			productRepayRecord.setRepayDate(repayDate);// 应还款日期
			productRepayRecord.setRepayInterest(earnings);// 应还利息
			productRepayRecord.setRepayPrincipal(repayPrincipal);// 当期应还本金
			productRepayRecord.setRepayPrincipalAndInterest(CalculateUtil
					.doubleAdd(earnings, repayPrincipal, 4));// 应还还本息（本金+利息）
			productRepayRecord.setStatus(ProductRepayRecordStatus.UNREPAID);// 还款记录状态

			productRepayRecords.add(productRepayRecord);

			initialPrincipal = finalPrincipal;
		}

		return productRepayRecords;
	}

	public static void main(String[] args) {
		Product product = new Product();
		product.setTotalAmount(10000D);
		product.setDummyBoughtAmount(0D);
		product.setInterest(12D);
		product.setMaturityDuration(12);

		List<ProductRepayRecord> productRepayRecords = new AverageCapitalInterestCalculator()
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
		System.out.println(new AverageCapitalInterestCalculator().calcutorTotalInterest(product, 1000000));
	}

	@Override
	public double calcutorTotalInterest(Product product, double amount) {
		// 利率
		double interest = product.getInterest();

		// 期限
		int months = product.getMaturityDuration();

		double principal = amount;


		// 月利率
		double interestOfMonth = CalculateUtil.doubleDivide(interest, 12 * 100,
				10);

		// 计算次方
		double pow4Principal = Math.pow(
				CalculateUtil.doubleAdd(1, interestOfMonth), months);

		/**
		 * 总利息
		 */
		double totalInterest = CalculateUtil.doubleDivide(CalculateUtil
				.doubleMultiply(principal, CalculateUtil.doubleAdd(
						CalculateUtil.doubleMultiply(CalculateUtil
								.doubleSubtract(CalculateUtil.doubleMultiply(
										months, interestOfMonth), 1),
								pow4Principal), 1)), pow4Principal - 1, 4);
		return totalInterest;
	}

}
