package com.hwsoft.hessian.service;

import java.util.List;

import com.hwsoft.common.order.OrderType;

/**
 * 交易平台钱妈妈业务hessian接口
 *
 * @author tzh
 */
public interface BusinessHessianService {

  /**
   * 手动投标
   *
   * @param customerId 用户id
   * @param productId  产品id
   * @param shareNum   购买份额数量
   * @return
   */
  public String handBid(int customerId, int productId, int shareNum);


  /**
   * 流标
   *
   * @param productId 产品id
   * @param staffId   操作用户id
   * @param notes     流标原因
   * @return
   */
  public String failed(int productId, int staffId, String notes);


  /**
   * 放款
   *
   * @param productId 产品id
   * @param staffId   操作人id
   * @return
   */
  public String lending(int productId, int staffId);


  //手动还款

  
  /**
   * 充值
   *
   * @return
   */
  public String recharege(int customerId, Integer userBankCardId, double amount, int productChannelId);

  /**
   * 充值结果处理
   *
   * @param response
   * @return
   */
  public String recharegeResult(String response);

  /**
   * 根据银行卡信息查询大额行号信息--支行信息
   *
   * @param cardNumber 卡号
   * @param bankCode   银行编号
   * @param branchName 支行关键字
   * @param cityCode   城市编号
   * @return 查询结果
   */
  public String prcptcdQuery(String cardNumber, String bankCode, String branchName, String cityCode);
  
  

  /**
   * 提现申请
   *
   * @return
   */
  public String withdrawalApply(int customerId, double amount, int productChannelId,
			int userBankCardId);
  
  /**
   * 提现
   * @param withdrawalRecordId
   * @param notes
   * @param staffId
   * @param staffName
   */
  public String withdrawal(int withdrawalRecordId, String notes, int staffId,
			String staffName) ;
  
  /**
   * 放款再次提现
   * @param amount
   * @param customerSubAccountId
   * @param userBankCardId
   * @return
   */
  public String lendingWithdrawalAgain(int withdrawalRecordId,int staffId,String staffName,String notes) ;
  
  /**
   * 实名认证
   * @param customerId
   * @param realName
   * @param idcardNum
   * @return
   */
  public String idcardAuth(int customerId,String realName,String idcardNum);
  
  /**
   * 正常还款
   * @param productId
   * @param orderType
   * @param staffId
   * @param staffName
   * @param repaymentIds
   * @return
   */
  public String repayNormal(int productId, int staffId, String staffName,int repaymentId);
  
  
  /**
   * 提前还款
   * @param productId
   * @param orderType
   * @param staffId
   * @param staffName
   * @param repaymentIds
   * @return
   */
  public String repayAdvance(int productId, int staffId, String staffName);
  
  /**
   * 逾期还款(逾期部分全部还款)
   * @param productId
   * @param orderType
   * @param staffId
   * @param staffName
   * @param repaymentIds
   * @return
   */
  public String repayDue(int productId, int staffId, String staffName);
  
  /**
   * 二次提现
   * @param withdrawalRecordId
   * @param notes
   * @param staffId
   * @param staffName
   */
  public String withdrawalAgain(int withdrawalRecordId, String notes, int staffId,
			String staffName) ;
  
  
}
