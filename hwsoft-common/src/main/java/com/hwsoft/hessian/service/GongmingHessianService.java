/**
 * 
 */
package com.hwsoft.hessian.service;

/**
 * 交易平台共鸣hessian接口
 * @author tzh
 *
 */
public interface GongmingHessianService {

	/**
	 * 同步共鸣计划
	 * @return resultVo对象转换而成的json数据
	 */
	public String synchronousGongmingPlan();
	
	/**
	 * 创建共鸣渠道账户
	 * @param customerId
	 * @return
	 */
	public String createGongmingAccount(int customerId);
	
	/**
	 * 创建共鸣渠道账户
	 * @param userBankCardId
	 * @return
	 */
	public String bindGongmingBankCard(int userBankCardId);
	
	/**
	 * 共鸣产品购买
	 * @param userId
	 * @param amount
	 * @param productId
	 * @param userBankCardId
	 * @return
	 */
	public String buyGongmingPlan(int userId,double amount,int productId,int userBankCardId);
	
	
	/**
	 * 更新共鸣渠道账户
	 * @param customerId
	 * @return
	 */
	public String updateGongmingAccount(int customerId);
	
}
