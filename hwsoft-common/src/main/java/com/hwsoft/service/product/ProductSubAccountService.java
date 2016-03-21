/**
 *
 */
package com.hwsoft.service.product;


import com.hwsoft.common.product.ProductBidType;
import com.hwsoft.common.product.ProductBuyerRecordStatus;
import com.hwsoft.common.product.ProductRecoveryRecordType;
import com.hwsoft.model.product.ProductSubAccount;
import com.hwsoft.vo.product.BaseProductSubAccountVo;
import com.hwsoft.vo.product.ProductSubAccountDetail;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

/**
 * @author tzh
 */
public interface ProductSubAccountService {

  public ProductSubAccount add(int customerId, double amount, int productId, String orderFormId, String bankCardNumber, int userBankCardId);

  public ProductSubAccount updateByPlaceOrderSuccess(int productSubAccountId);

  public ProductSubAccount findById(int productSubAccountId);

  public ProductSubAccount updateByPlaceOrderFailed(int productSubAccountId, Date effectiveDate);

  public ProductSubAccount updateByPaySuccess(int productSubAccountId, double successAmount, Date effectiveDate);

  public ProductSubAccount updateByOrderTerminated(int productSubAccountId, Date effectiveDate);

  public ProductSubAccount updateByOrderHasBid(int productSubAccountId, Date effectiveDate);

  //投标中
  public ProductSubAccount updateByOrderBiding(int productSubAccountId, Date effectiveDate);

  //债权转让中
  public ProductSubAccount updateByOrderTransferring(int productSubAccountId, Date effectiveDate);

  //已赎回
  public ProductSubAccount updateByOrderRedeemed(int productSubAccountId, Date effectiveDate, Date endDate);

  //已提现
  public ProductSubAccount updateByOrderHasWaithdrawal(int productSubAccountId, Date effectiveDate);

  //待退款
  public ProductSubAccount updateByOrderWattingRefund(int productSubAccountId, Date effectiveDate);

  //已退款
  public ProductSubAccount updateByOrderHasRefund(int productSubAccountId, Date effectiveDate);

  //赎回中
  public ProductSubAccount updateByOrderRedemption(int productSubAccountId, Date effectiveDate);

  public long getProductSubAccountTotalCount();
  
  public long getProductSubAccountTotalCountOfStatus(ProductBuyerRecordStatus productBuyerRecordStatus);

  public List<ProductSubAccount> listAll(int from, int pageSize);
  
  public List<ProductSubAccountDetail> listAllDetails(int from, int pageSize,ProductBuyerRecordStatus productBuyerRecordStatus,String mobile,Date startDate,Date endDate,String productName);

  public List<ProductSubAccountDetail> listAllDetails();
  
  public int countAllDetails(ProductBuyerRecordStatus productBuyerRecordStatus,String mobile,Date startDate,Date endDate,String productName);
  
  public List<ProductSubAccount> listAllOfStatus(int from, int pageSize,ProductBuyerRecordStatus productBuyerRecordStatus,String mobile,Date startDate,Date endDate,String productName);

  /**
   * 收益更新
   *
   * @param id
   * @param totalInterest
   * @param dailyInterest
   * @param fee
   * @return
   */
  public ProductSubAccount updateInterest(String orderFormId, double totalInterest, double dailyInterest, double fee);

  /**
   * 同步产品子账户状态
   *
   * @return
   */
  public ProductSubAccount synchronousStatus(String orderFormId, ProductBuyerRecordStatus status, double orderAmount, double successAmount, Date effectiveDate, Date endDate);

  public List<String> findOrderFormIdByOrderStatus(EnumSet<ProductBuyerRecordStatus> statusSet);

  public List<String> findAllOrderFormId();

  /**
   * 根据产品id查询产品子账户（产品购买记录）
   *
   * @param productId
   * @return
   */
  public List<BaseProductSubAccountVo> findProductSubAccountByProductId(int productId);

  /**
   * 根据用户id查询产品子账户（产品购买记录）
   *
   * @param productId
   * @return
   */
  public List<BaseProductSubAccountVo> findProductSubAccountByCustomerId(int CustomerId);

  public ProductSubAccount findByOrderFormId(String orderFormId);
  
  public String exportOrderDetail();
  
  /**
   * 投标
   * @param customerId	用户id
   * @param sharNum		购买份额
   * @param productId	产品id
   * @param orderFormId	订单编号
   * @return
   */
  public ProductSubAccount bid(int customerId, int sharNum, int productId, String orderFormId,
		  ProductBidType productBidType, Integer bidProductId, Integer bidProductSubAccountId);
  
  
  /**
   * 流标更新
   * @param productAccountId
   * @return
   */
  public ProductSubAccount updateForFailed(int productAccountId,int productId);
  
  
  /**
   * 根据产品账户id查询产品子账户
   * @param productAccountId
   * @return
   */
  public List<ProductSubAccount> findByProductAccountId(int productAccountId);
  
  /**
   * 放款更新
   * @param productAccountId
   * @return
   */
  public ProductSubAccount updateForLending(int productAccountId,int productId);
  
  /**
   * 还款更新
   * @param productSubAccountId
   * @return
   */
  public ProductSubAccount updateForRepayment(int productSubAccountId,double interest,double principal,
		  double defaultInterest,String orderFormId,int realFromCustomerSubAccountId,
		  int productRepayRecordId,int phaseNumber, ProductRecoveryRecordType type);
  

  /**
   * 钱妈妈散标收益更新
   *
   * @param totalInterest
   * @param productSubAccountId
   * @return
   */
  public ProductSubAccount updateInterest(double dailyInterest, int productSubAccountId);
  
  /**
   * 根据用户id查询产品子账户（产品购买记录）
   *
   * @param CustomerId
   * @return
   */
  public List<ProductSubAccount> findByCustomerId(int customerId);
  
  /**
   * 结标更新
   * @param productId
   * @return
   */
  public ProductSubAccount updateOver(int productId);
  
  /**
   * 根据产品id查询产品子账户（产品购买记录）
   *
   * @param CustomerId
   * @return
   */
  public List<ProductSubAccount> findByProductId(int productId);
  
}
