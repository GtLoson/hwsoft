/**
 *
 */
package com.hwsoft.service.product.impl;

import com.hwsoft.common.conf.Conf;
import com.hwsoft.common.point.CustomerSubAccountPointType;
import com.hwsoft.common.product.*;
import com.hwsoft.common.score.ScoreOperType;
import com.hwsoft.dao.product.ProductSubAccountDao;
import com.hwsoft.dao.statistcs.CustomerStatistcsDao;
import com.hwsoft.exception.bid.BidException;
import com.hwsoft.exception.failed.FailedProductException;
import com.hwsoft.exception.lending.LendingException;
import com.hwsoft.exception.product.ProductException;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.point.CustomerSubAccountPoint;
import com.hwsoft.model.product.Product;
import com.hwsoft.model.product.ProductAccount;
import com.hwsoft.model.product.ProductChannel;
import com.hwsoft.model.product.ProductOfthatRegister;
import com.hwsoft.model.product.ProductSubAccount;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.log.customer.CustomerSubAccountPointLogService;
import com.hwsoft.service.point.CustomerSubAccountPointService;
import com.hwsoft.service.product.*;
import com.hwsoft.service.score.ScoreRecordService;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.math.CalculateUtil;
import com.hwsoft.vo.product.BaseProductSubAccountVo;
import com.hwsoft.vo.product.ProductSubAccountDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author tzh
 */
@Service("productSubAccountService")
public class ProductSubAccountServiceImpl implements ProductSubAccountService {

  private static Logger logger = Logger.getLogger("productSubAccountServiceImpl");

  @Autowired
  private ProductSubAccountDao productSubAccountDao;

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductChannelService productChannelService;

  @Autowired
  private CustomerSubAccountService customerSubAccountService;

  @Autowired
  private ProductAccountService productAccountService;

  @Autowired
  private ProductSubAccountEarningRecordService productSubAccountEarningRecordService;

  @Autowired
  private ProductSubAccountPointLogService productSubAccountPointLogService;

  @Autowired
  private CustomerStatistcsDao customerStatistcsDao;
  
  @Autowired
  private ScoreRecordService scoreRecordService;
  
  @Autowired
  private CustomerSubAccountPointService customerSubAccountPointService;
  
  @Autowired
  private ProductOfThatRegisterService productOfThatRegisterService;
  
  @Autowired
  private CustomerSubAccountPointLogService customerSubAccountPointLogService;
  
  @Autowired
  private CustomerService customerService;
  
  @Autowired
  private ProductRecoveryRecordService productRecoveryRecordService;
  
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public long getProductSubAccountTotalCount(){
    return productSubAccountDao.getTotalCount();
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<ProductSubAccount> listAll(int from, int pageSize) {
    return productSubAccountDao.listAll(from,pageSize);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount add(int customerId, double amount, int productId, String orderFormId, String bankCardNumber, int userBankCardId) {
    Product product = productService.findById(productId);
    if (null == product) {
      logger.info("产品未找到");
      throw new ProductException("产品未找到");
    }
    if (!product.getProductStatus().equals(ProductStatus.SALES)) {
      logger.info("该产品不在销售期");
      throw new ProductException("该产品不在销售期");
    }
    CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(customerId, product.getProductChannelId());
    if (null == customerSubAccount) {
      throw new ProductException("共鸣账户未找到");
    }

    ProductChannel productChannel = productChannelService.findById(product.getProductChannelId());
    if (null == productChannel) {
      logger.info("没有找到产品渠道：" + ProductChannelType.GONGMING.toString());
      throw new ProductException("没有找到产品渠道：" + ProductChannelType.GONGMING.toString());
    }
    if (product.getProductChannelId() != productChannel.getId()) {
      logger.info("该产品不是来自共鸣");
      throw new ProductException("该产品不是来自共鸣");
    }
    if (CalculateUtil.doubleSubtract(product.getRemainingAmount(), amount, 2) < 0) {
      logger.info("申购金额超过产品可售金额");
      throw new ProductException("申购金额超过产品可售金额");
    }
    ProductSubAccount productBuyerRecord = new ProductSubAccount();
    productBuyerRecord.setAmount(amount);
    productBuyerRecord.setBuyTime(new Date());
    productBuyerRecord.setCustomerId(customerSubAccount.getCustomerId());
    productBuyerRecord.setProductId(product.getId());
    productBuyerRecord.setStatus(ProductBuyerRecordStatus.PURCHASE);
    productBuyerRecord.setOrderFormId(orderFormId);
    productBuyerRecord.setBankCardNumber(bankCardNumber);
    productBuyerRecord.setUserBankCardId(userBankCardId);
    productBuyerRecord.setProductBidType(ProductBidType.GONGMING_PRODUCT_BUY);//共鸣产品购买
    productBuyerRecord.setShareNum(0);
    // 处理产品子账户
    ProductAccount productBuyer = productAccountService.findByCustomerIdAndProductId(customerId, productId);
    if (null == productBuyer) {
      productBuyer = productAccountService.add(customerId, productId, amount);
    } else {
      productAccountService.updateByPlaceOrderSuccess(productBuyer.getId(),
          productBuyer.getBuyAmount(),
          CalculateUtil.doubleAdd(productBuyer.getPurchaseAmount(), amount, 2));
    }
    productBuyerRecord.setCustomerSubAccountId(customerSubAccount.getId());
    productBuyerRecord.setProductBuyerId(productBuyer.getId());
    //处理产品
    productService.updateProductWaittingAmount(product.getId(), CalculateUtil.doubleAdd(product.getWaittingPayAmount(), amount, 2),amount);
    return productSubAccountDao.add(productBuyerRecord);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateByBuySuccess(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateByPlaceOrderSuccess(int productSubAccountId) {
    ProductSubAccount productBuyerRecord = findById(productSubAccountId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }
    productBuyerRecord.setStatus(ProductBuyerRecordStatus.HAVE_PLACE_ORDER);

    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
    
    return productSubAccountDao.update(productBuyerRecord);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#findById(int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public ProductSubAccount findById(int productSubAccountId) {
    return productSubAccountDao.findById(productSubAccountId);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateByPlaceOrderFailed(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateByPlaceOrderFailed(int productSubAccountId, Date effectiveDate) {
    
    /*
    ProductSubAccount productBuyerRecord = findById(productSubAccountId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }
    productBuyerRecord.setStatus(ProductBuyerRecordStatus.ORDER_TERMINATED);
    if (null != effectiveDate) {
      productBuyerRecord.setEffectiveDate(effectiveDate);
    }
    
    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
    
    return productSubAccountDao.update(productBuyerRecord);
    */
    return updateByOrderTerminated(productSubAccountId,effectiveDate);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateByPaySuccess(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateByPaySuccess(int productSubAccountId, double successAmount, Date effectiveDate) {
    ProductSubAccount productBuyerRecord = findById(productSubAccountId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }
    productBuyerRecord.setSuccessAmount(successAmount);
    productBuyerRecord.setShareNum((int)productBuyerRecord.getSuccessAmount());
    productBuyerRecord.setWaittingPrincipal(successAmount);
    productBuyerRecord.setStatus(ProductBuyerRecordStatus.HAS_PAIED);
    productBuyerRecord.setEffectiveDate(effectiveDate);
    // 更新产品子账户，更新产品
    productAccountService.updateByPaySuccess(productBuyerRecord.getProductBuyerId(), productBuyerRecord.getId());
    // 添加资金记录
    productSubAccountPointLogService.addProductSubAccountPointLog(productBuyerRecord.getCustomerId(), productBuyerRecord.getCustomerSubAccountId(),
        productBuyerRecord.getId(), productBuyerRecord.getProductId(), productBuyerRecord.getCustomerSubAccountId(),
        productBuyerRecord.getCustomerId(), ProductSubAccountPointLogType.BUY_PRODUCT, successAmount);
    
    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
    
    //TODO 处理渠道够买人数
    //添加积分赠送
 // 送积分
    try {
    	int score = (int) (successAmount/10);
        scoreRecordService.addScoreRecord(productBuyerRecord.getCustomerId(), score, ScoreOperType.INVITE_SEND,Conf.SCORE_OPERATOR_SYSTEM);
    } catch (Exception e1) {
        e1.printStackTrace();
        logger.info("投资赠送积分失败，id=" + productBuyerRecord.getCustomerId());
    }
    return productSubAccountDao.update(productBuyerRecord);
  }
  
  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateByPayFailed(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateByOrderTerminated(int productSubAccountId, Date effectiveDate) {
    ProductSubAccount productBuyerRecord = findById(productSubAccountId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }
    productBuyerRecord.setSuccessAmount(0D);
    productBuyerRecord.setStatus(ProductBuyerRecordStatus.ORDER_TERMINATED);
    productSubAccountDao.update(productBuyerRecord);
    if (null != effectiveDate) {
      productBuyerRecord.setEffectiveDate(effectiveDate);
    }
    // 更新产品子账户，更新产品
    productAccountService.updateByOrderTerminated(productBuyerRecord.getProductBuyerId(), productBuyerRecord.getId());
    
    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
    
    return productBuyerRecord;
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateByOrderHasBid(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateByOrderHasBid(int productSubAccountId, Date effectiveDate) {
    ProductSubAccount productBuyerRecord = findById(productSubAccountId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }
    productBuyerRecord.setStatus(ProductBuyerRecordStatus.HAS_BID);
    if (null != effectiveDate) {
      productBuyerRecord.setEffectiveDate(effectiveDate);
    }
    
    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
    
    return productSubAccountDao.update(productBuyerRecord);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateByOrderBiding(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateByOrderBiding(int productSubAccountId, Date effectiveDate) {
    ProductSubAccount productBuyerRecord = findById(productSubAccountId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }

    productBuyerRecord.setStatus(ProductBuyerRecordStatus.BIDING);
    if (null != effectiveDate) {
      productBuyerRecord.setEffectiveDate(effectiveDate);
    }
    
    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
    return productSubAccountDao.update(productBuyerRecord);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateByOrderHasRefund(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateByOrderHasRefund(int productSubAccountId, Date effectiveDate) { //已退款，共鸣暂时未使用该状态
    ProductSubAccount productBuyerRecord = findById(productSubAccountId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }
    productBuyerRecord.setStatus(ProductBuyerRecordStatus.HAS_REFUND);
    if (null != effectiveDate) {
      productBuyerRecord.setEffectiveDate(effectiveDate);
    }
    
    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
    // 需要产品子账户处理金额
    return productSubAccountDao.update(productBuyerRecord);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateByOrderHasWaithdrawal(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateByOrderHasWaithdrawal(int productSubAccountId, Date effectiveDate) {//已提现
    ProductSubAccount productBuyerRecord = findById(productSubAccountId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }
    productBuyerRecord.setStatus(ProductBuyerRecordStatus.HAS_WITHDRAWAL);
    // 处理提现金额
    productBuyerRecord.setWithdrawalAmount(CalculateUtil.doubleAdd(productBuyerRecord.getSuccessAmount(), productBuyerRecord.getTotalInterest(), 2));

    //待收本金
    productBuyerRecord.setWaittingPrincipal(0);
    
    // 需要产品子账户处理金额
    if (null != effectiveDate) {
      productBuyerRecord.setEffectiveDate(effectiveDate);
    }
    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
    
    productSubAccountPointLogService.addProductSubAccountPointLog(productBuyerRecord.getCustomerId(), productBuyerRecord.getCustomerSubAccountId(),
        productBuyerRecord.getId(), productBuyerRecord.getProductId(), productBuyerRecord.getCustomerSubAccountId(),
        productBuyerRecord.getCustomerId(), ProductSubAccountPointLogType.WITH_DRAW_CASH, productBuyerRecord.getWithdrawalAmount());
    return productSubAccountDao.update(productBuyerRecord);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateByOrderRedeemed(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateByOrderRedeemed(int productSubAccountId, Date effectiveDate, Date endDate) {//已赎回
    ProductSubAccount productBuyerRecord = findById(productSubAccountId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }
    productBuyerRecord.setStatus(ProductBuyerRecordStatus.REDEEMED);
    if (null != effectiveDate) {
      productBuyerRecord.setEffectiveDate(effectiveDate);
    }
    if (null != endDate) {
      productBuyerRecord.setEndDate(endDate);
    }
    productBuyerRecord.setWithdrawalAmount(CalculateUtil.doubleSubtract(
        CalculateUtil.doubleAdd(productBuyerRecord.getSuccessAmount(), productBuyerRecord.getTotalInterest(), 2),
        productBuyerRecord.getFee(), 2));
    
    // 需要产品子账户处理金额
    productBuyerRecord.setWaittingPrincipal(0);
    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
    return productSubAccountDao.update(productBuyerRecord);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateByOrderRedemption(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateByOrderRedemption(int productSubAccountId, Date effectiveDate) {//赎回中
    ProductSubAccount productBuyerRecord = findById(productSubAccountId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }

    productBuyerRecord.setStatus(ProductBuyerRecordStatus.REDEMPTION);
    if (null != effectiveDate) {
      productBuyerRecord.setEffectiveDate(effectiveDate);
    }
    
    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
    
    return productSubAccountDao.update(productBuyerRecord);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateByOrderTransferring(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateByOrderTransferring(int productSubAccountId, Date effectiveDate) {
    ProductSubAccount productBuyerRecord = findById(productSubAccountId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }

    productBuyerRecord.setStatus(ProductBuyerRecordStatus.REDEMPTION);
    if (null != effectiveDate) {
      productBuyerRecord.setEffectiveDate(effectiveDate);
    }
    
    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
    
    return productSubAccountDao.update(productBuyerRecord);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateByOrderWattingRefund(int)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateByOrderWattingRefund(int productSubAccountId, Date effectiveDate) {//TODO 待退款，共鸣暂时未使用该状态
    ProductSubAccount productBuyerRecord = findById(productSubAccountId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }
    if (null != effectiveDate) {
      productBuyerRecord.setEffectiveDate(effectiveDate);
    }
    productBuyerRecord.setStatus(ProductBuyerRecordStatus.REDEMPTION);
    
    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
    
    // 需要确认
    return productSubAccountDao.update(productBuyerRecord);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductBuyerRecordService#updateInterest(int, double, double, double)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount updateInterest(String orderFormId, double totalInterest,
                                          double dailyInterest, double fee) {

    ProductSubAccount productBuyerRecord = findByOrderFormId(orderFormId);
    if (null == productBuyerRecord) {
      throw new ProductException("产品子账户未找到");
    }

    productBuyerRecord.setTotalInterest(totalInterest);
    productBuyerRecord.setDailyInterest(dailyInterest);
    productBuyerRecord.setFee(fee);
    productBuyerRecord.setLastInterestUpdateDate(new Date());
//		Product product = productService.findById(productBuyerRecord.getProductId());
    String yieldPeriod = null;
    Date now = new Date();
    //TODO 有其他收益方式的时候需要进行判断处理
//		if(product.getProductIncomeType().equals(ProductIncomeType.DAILY_INTEREST)){
    Date date = DateTools.stringToDate(DateTools.getNextDayTime(DateTools.dateToString(now, DateTools.DATE_PATTERN_DEFAULT), -1), DateTools.DATE_PATTERN_DEFAULT);
    yieldPeriod = DateTools.dateToString(date, "yyyy-MM-dd");
//		} else 
    // 更新收益记录
    productSubAccountEarningRecordService.add(productBuyerRecord.getId(), dailyInterest, yieldPeriod);
    // 更新产品子账户
    productAccountService.updateInterest(productBuyerRecord.getProductBuyerId(), dailyInterest);

    productSubAccountPointLogService.addProductSubAccountPointLog(productBuyerRecord.getCustomerId(), productBuyerRecord.getCustomerSubAccountId(),
        productBuyerRecord.getId(), productBuyerRecord.getProductId(), productBuyerRecord.getCustomerSubAccountId(),
        productBuyerRecord.getCustomerId(), ProductSubAccountPointLogType.RECEIVE_INTEREST, dailyInterest);
    
    return productSubAccountDao.update(productBuyerRecord);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductSubAccountService#synchronousStatus(java.lang.String, com.hwsoft.common.product.ProductBuyerRecordStatus, double, java.util.Date, java.util.Date)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public ProductSubAccount synchronousStatus(String orderFormId,
                                             ProductBuyerRecordStatus status, double orderAmount, double successAmount,
                                             Date effectiveDate, Date endDate) {
    ProductSubAccount productSubAccount = findByOrderFormId(orderFormId);
    if (null == productSubAccount) {
        throw new ProductException("产品子账户未找到");
    }
    if (null != effectiveDate && null == productSubAccount.getEffectiveDate()) {
        productSubAccount.setEffectiveDate(effectiveDate);
    }
    if (null != endDate && null == productSubAccount.getEndDate()) {
        productSubAccount.setEndDate(endDate);
    }
    if (status.ordinal() <= productSubAccount.getStatus().ordinal()) {
    	logger.info("通知订单状态："+status.toString()+";当前订单状态："+productSubAccount.getStatus().toString());
    	return null;
    }
    if(status.ordinal() > ProductBuyerRecordStatus.HAS_PAIED.ordinal() 
    		&& (status.equals(ProductBuyerRecordStatus.HAS_BID)
    				|| status.equals(ProductBuyerRecordStatus.BIDING)
    				|| status.equals(ProductBuyerRecordStatus.TRANSFERRING )
    				|| status.equals(ProductBuyerRecordStatus.TRANSFERRING)
    				|| status.equals(ProductBuyerRecordStatus.REDEEMED)
    				|| status.equals(ProductBuyerRecordStatus.HAS_WITHDRAWAL)
    				|| status.equals(ProductBuyerRecordStatus.HAS_REFUND)
    				|| status.equals(ProductBuyerRecordStatus.REDEMPTION))
    				&& productSubAccount.getStatus().ordinal() < ProductBuyerRecordStatus.HAS_PAIED.ordinal() ){
    	updateByPaySuccess(productSubAccount.getId(), successAmount, effectiveDate);
    }
  
    if (status.equals(ProductBuyerRecordStatus.HAS_PAIED)) {//子账户支付
      updateByPaySuccess(productSubAccount.getId(), successAmount, effectiveDate);
    } else if (status.equals(ProductBuyerRecordStatus.ORDER_TERMINATED)) { //子账户失效
      updateByOrderTerminated(productSubAccount.getId(), effectiveDate);
    } else if (status.equals(ProductBuyerRecordStatus.HAS_BID)) { //已投标
      updateByOrderHasBid(productSubAccount.getId(), effectiveDate);
    } else if (status.equals(ProductBuyerRecordStatus.BIDING)) { //投标中
      updateByOrderBiding(productSubAccount.getId(), effectiveDate);
    } else if (status.equals(ProductBuyerRecordStatus.TRANSFERRING)) { //债权转让中
      updateByOrderTransferring(productSubAccount.getId(), effectiveDate);
    } else if (status.equals(ProductBuyerRecordStatus.REDEEMED)) { //已赎回
      updateByOrderRedeemed(productSubAccount.getId(), effectiveDate, endDate);
    } else if (status.equals(ProductBuyerRecordStatus.HAS_WITHDRAWAL)) { //已提现
      updateByOrderHasWaithdrawal(productSubAccount.getId(), effectiveDate);
    } else if (status.equals(ProductBuyerRecordStatus.WAITTING_REFUND)) { //待退款
      updateByOrderWattingRefund(productSubAccount.getId(), effectiveDate);
    } else if (status.equals(ProductBuyerRecordStatus.HAS_REFUND)) { //已退款
      updateByOrderHasRefund(productSubAccount.getId(), effectiveDate);
    } else if (status.equals(ProductBuyerRecordStatus.REDEMPTION)) { //赎回中
      updateByOrderRedemption(productSubAccount.getId(), effectiveDate);
    }
    
    //直接更新产品数据
    productService.updateProductByPaySuccess(productSubAccount.getProductId(), productSubAccount.getId(),successAmount);
    
    return productSubAccount;
  }
  
  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductSubAccountService#findOrderFormIdByOrderStatus(java.util.EnumSet)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<String> findOrderFormIdByOrderStatus(EnumSet<ProductBuyerRecordStatus> statusSet) {
    return productSubAccountDao.findOrderFormIdByOrderStatus(statusSet);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<String> findAllOrderFormId() {
    return productSubAccountDao.listAllOrderFormId();
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.product.ProductSubAccountService#findProductSubAccountByProductId(int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<BaseProductSubAccountVo> findProductSubAccountByProductId(
      int productId) {
    return customerStatistcsDao.findProductSubAccountVoByProductId(productId);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<BaseProductSubAccountVo> findProductSubAccountByCustomerId(
      int customerId) {
    return customerStatistcsDao.findProductSubAccountVoByCustomerId(customerId);
  }

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProductSubAccount findByOrderFormId(String orderFormId) {
		return productSubAccountDao.findByOrderFormId(orderFormId);
	}

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public long getProductSubAccountTotalCountOfStatus(ProductBuyerRecordStatus productBuyerRecordStatus) {
    return 0;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public int countAllDetails(ProductBuyerRecordStatus productBuyerRecordStatus,String mobile,Date startDate,Date endDate,String productName){
    return productSubAccountDao.orderListCount(productBuyerRecordStatus,mobile,startDate,endDate,productName);
  } 

  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<ProductSubAccountDetail> listAllDetails(int from, int pageSize,ProductBuyerRecordStatus productBuyerRecordStatus,String mobile,Date startDate,Date endDate,String productName){
    return productSubAccountDao.orderDetailInfoList(from,pageSize,productBuyerRecordStatus,mobile,startDate,endDate,productName);
  }

  @Override
  public List<ProductSubAccount> listAllOfStatus(int from, int pageSize, ProductBuyerRecordStatus productBuyerRecordStatus, String mobile, Date startDate, Date endDate, String productName) {
    return null;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public String exportOrderDetail(){
    return null;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<ProductSubAccountDetail> listAllDetails() {
    return productSubAccountDao.orderDetailInfoList();
  }


  /**
   * 投标
   * @param customerId	用户id
   * @param sharNum		购买份额
   * @param productId	产品id
   * @param orderFormId	订单编号
   * @return
   */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductSubAccount bid(int customerId, int sharNum, int productId,
			String orderFormId,ProductBidType productBidType, Integer bidProductId, Integer bidProductSubAccountId) {
		
		// 进行投标处理，1、添加产品子账户，2、添加/更新产品账户，3、资金冻结，4、记录资金记录，6、更新产品数据
		Product product = productService.findById(productId);
	    if (null == product) {
	      logger.info("产品未找到");
	      throw new BidException("产品未找到");
	    }
	    if (!product.getProductStatus().equals(ProductStatus.SALES)) {
	      logger.info("该产品不在销售期");
	      throw new BidException("该产品不在销售期");
	    }
	    CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(customerId, product.getProductChannelId());
	    if (null == customerSubAccount) {
	      throw new BidException("渠道账户未找到");
	    }

	    ProductChannel productChannel = productChannelService.findById(product.getProductChannelId());
	    if (null == productChannel) {
	      logger.info("没有找到产品渠道：");
	      throw new BidException("没有找到产品渠道：" );
	    }
	    if (product.getProductChannelId() != productChannel.getId()) {
	      logger.info("产品渠道不一致");
	      throw new BidException("产品渠道不一致");
	    }
	    double amount = CalculateUtil.doubleMultiply(sharNum, product.getBaseAmount(), 4);
//	    if (CalculateUtil.doubleSubtract(product.getRemainingAmount(), amount, 4) < 0) {
//	      logger.info("申购金额超过产品可售金额");
//	      throw new BidException("申购金额超过产品可售金额");
//	    }
	    
	    if (CalculateUtil.doubleSubtract(product.getRemainingAmount(), sharNum, 4) < 0) {
		      logger.info("申购份额超过产品剩余可售份额");
		      throw new BidException("申购份额超过产品剩余可售份额");
		}
	    
	    CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(customerSubAccount.getId());
	    
	    if (CalculateUtil.doubleSubtract(customerSubAccountPoint.getAvailablePoints(), amount, 4) < 0) {
		      logger.info("可用余额不足");
		      throw new BidException("可用余额不足");
		}
	    //处理资金
	    customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleSubtract(customerSubAccountPoint.getAvailablePoints(), amount, 4));
	    customerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getFrozenPoints(), amount, 4));
	    customerSubAccountPointService.update(customerSubAccountPoint);
	    // 添加资金记录
	    String remark = "投标【"+product.getProductName()+"】"+sharNum+"份（每份"+product.getBaseAmount()+"元）";
	    customerSubAccountPointLogService.add(amount,customerSubAccountPoint.getAvailablePoints(),customerSubAccountPoint.getFrozenPoints(),
	    		CustomerSubAccountPointType.BID_FREEZEN, remark, orderFormId, 
	    		customerSubAccount.getId(), customerSubAccount.getId(), customerSubAccount.getId(), customerSubAccount.getId(),
	    		false, customerId,productId,null,null);
	    
	    
	    Date now = new Date();
	    ProductSubAccount productBuyerRecord = new ProductSubAccount();
	    productBuyerRecord.setAmount(amount);
	    productBuyerRecord.setShareNum(sharNum);
	    productBuyerRecord.setBuyTime(now);
	    productBuyerRecord.setCustomerId(customerSubAccount.getCustomerId());
	    productBuyerRecord.setProductId(product.getId());
	    productBuyerRecord.setStatus(ProductBuyerRecordStatus.BID_SUCCESS);//状态直接更新为已投标
	    productBuyerRecord.setOrderFormId(orderFormId);
		productBuyerRecord.setProductBidType(productBidType);
		productBuyerRecord.setBidProductId(bidProductId);
		productBuyerRecord.setBidProductSubAccountId(bidProductSubAccountId);
		productBuyerRecord.setWaittingPrincipal(amount);
		productBuyerRecord.setSuccessAmount(amount);
		
	    // 处理产品账户
//	    ProductAccount productBuyer = productAccountService.findByCustomerIdAndProductId(customerId, productId);
//	    if (null == productBuyer) {
	      // 重新处理这里
	    ProductAccount productBuyer = productAccountService.addForBid(customerId, productId, amount, sharNum, productBidType, bidProductId, bidProductSubAccountId);
//	    } else {
//	    	 // 重新处理这里
//	      productAccountService.updateByPlaceOrderSuccess(productBuyer.getId(),
//	          productBuyer.getBuyAmount(),
//	          CalculateUtil.doubleAdd(productBuyer.getPurchaseAmount(), amount, 2));
//	    }
	    productBuyerRecord.setCustomerSubAccountId(customerSubAccount.getId());
	    productBuyerRecord.setProductBuyerId(productBuyer.getId());
	    //处理产品
	    
	    //更新待支付金额
	    productService.updateProductWaittingAmount(product.getId(), CalculateUtil.doubleAdd(product.getWaittingPayAmount(), sharNum, 2),sharNum);

	    productBuyerRecord = productSubAccountDao.add(productBuyerRecord);

	    /**
	     * 更新投资成功金额
	     */
	    productService.updateProductByPaySuccess(product.getId(), productBuyerRecord.getId(),sharNum);
	    

	    productAccountService.updateStatus(productBuyerRecord.getProductBuyerId());
		
	    // 添加份额
  		ProductOfthatRegister productOfthatRegister = new ProductOfthatRegister();
  		productOfthatRegister.setStatus(ProductOfThatStatus.CONFIRMING);
  		productOfthatRegister.setBidProductId(bidProductId);
  		productOfthatRegister.setBidProductSubAccountId(bidProductSubAccountId);
  		productOfthatRegister.setCreateTime(now);
  		productOfthatRegister.setCutomerSubAccountId(customerSubAccount.getId());
  		productOfthatRegister.setHasExitOfThatNumber(0);
  		productOfthatRegister.setHasTransferOfThatNumber(0);
  		productOfthatRegister.setOfThatAmount(product.getBaseAmount());
  		productOfthatRegister.setOfThatIdentifier(orderFormId);
  		productOfthatRegister.setOfThatNumber(sharNum);
  		productOfthatRegister.setOrginalCreditorId(customerSubAccount.getId());
//  		productOfthatRegister.setOrginalOfThatId(orderFormId);
  		productOfthatRegister.setProductId(productId);
  		productOfthatRegister.setProductSubAccountId(productBuyerRecord.getId());
  		productOfthatRegister.setType(ProductBidType.HANDLE_BID);//手动投标
  		productOfthatRegister.setTotalOfThatNumber(sharNum);
  		productOfThatRegisterService.addProductOfthatRegister(productOfthatRegister);
  		
  		
  	   try {
  	    	int score = (int) (amount/10);
  	        scoreRecordService.addScoreRecord(productBuyerRecord.getCustomerId(), score, ScoreOperType.INVITE_SEND,Conf.SCORE_OPERATOR_SYSTEM);
  	    } catch (Exception e1) {
  	        e1.printStackTrace();
  	        logger.info("投资赠送积分失败，id=" + productBuyerRecord.getCustomerId());
  	    }
  		
	    return productBuyerRecord;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductSubAccount updateForFailed(int productAccountId,int productId) {
		List<ProductSubAccount> productSubAccountList = findByProductAccountId(productAccountId);
		if(null == productSubAccountList || productSubAccountList.size() == 0){
			return null;
		}
		Product product = productService.findById(productId);
	    if (null == product) {
	      logger.info("产品未找到");
	      throw new FailedProductException("产品未找到");
	    }
		for(ProductSubAccount productSubAccount : productSubAccountList){
			productSubAccount.setStatus(ProductBuyerRecordStatus.FAILED);
			productSubAccountDao.update(productSubAccount);
			
			productAccountService.updateStatus(productSubAccount.getProductBuyerId());
		    //处理资金
			CustomerSubAccount customerSubAccount = customerSubAccountService.findById(productSubAccount.getCustomerSubAccountId());
		    if (null == customerSubAccount) {
		      throw new FailedProductException("渠道账户未找到");
		    }
		    
		    CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(customerSubAccount.getId());
		    customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getAvailablePoints(), productSubAccount.getAmount(), 4));
		    customerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleSubtract(customerSubAccountPoint.getFrozenPoints(), productSubAccount.getAmount(), 4));
		    customerSubAccountPointService.update(customerSubAccountPoint);
		    // 处理资金记录
		    String remark = "流标，投标订单编号【"+productSubAccount.getOrderFormId()+"】，产品【"+product.getProductName()+"】";
		    customerSubAccountPointLogService.add(productSubAccount.getAmount(), customerSubAccountPoint.getAvailablePoints(),customerSubAccountPoint.getFrozenPoints(),
		    		CustomerSubAccountPointType.FAILED, remark, productSubAccount.getOrderFormId(), 
		    		customerSubAccount.getId(), customerSubAccount.getId(), customerSubAccount.getId(), customerSubAccount.getId(),
		    		false, productSubAccount.getCustomerId(),productId,null,null);
			
		}
		
		
		
		
		return null;
	}
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<ProductSubAccount> findByProductAccountId(int productAccountId) {
		return productSubAccountDao.findByProductAccountId(productAccountId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductSubAccount updateForLending(int productAccountId,
			int productId) {
		List<ProductSubAccount> productSubAccountList = findByProductAccountId(productAccountId);
		if(null == productSubAccountList || productSubAccountList.size() == 0){
			return null;
		}
		Product product = productService.findById(productId);
	    if (null == product) {
	      logger.info("产品未找到");
	      throw new LendingException("产品未找到");
	    }
		for(ProductSubAccount productSubAccount : productSubAccountList){
			if(!ProductBuyerRecordStatus.BID_SUCCESS.equals(productSubAccount.getStatus())){
				continue;
			}
			productSubAccount.setStatus(ProductBuyerRecordStatus.PROFITING);
			productSubAccountDao.update(productSubAccount);
			
			productAccountService.updateStatus(productSubAccount.getProductBuyerId());
		    //处理资金
			CustomerSubAccount customerSubAccount = customerSubAccountService.findById(productSubAccount.getCustomerSubAccountId());
		    if (null == customerSubAccount) {
		      throw new LendingException("渠道账户未找到");
		    }
		    
		    CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(customerSubAccount.getId());
//		    customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getAvailablePoints(), productSubAccount.getAmount(), 4));
		    
		    if(customerSubAccountPoint.getFrozenPoints() < productSubAccount.getSuccessAmount()){
		    	throw new LendingException("用户资金账户冻结余额不足，渠道账户id:"+customerSubAccountPoint.getId());
		    }
		    
		    customerSubAccountPoint.setFrozenPoints(CalculateUtil.doubleSubtract(customerSubAccountPoint.getFrozenPoints(), productSubAccount.getAmount(), 4));
		    customerSubAccountPointService.update(customerSubAccountPoint);
		    // 处理资金记录
		    int borrowCustomerSubAccountId = product.getCustomerSubAccountId();
		    
		    String remark = "放款解冻，投标订单编号【"+productSubAccount.getOrderFormId()+"】，产品【"+product.getProductName()+"】,金额【-"+productSubAccount.getAmount()+"】";
		    customerSubAccountPointLogService.add(productSubAccount.getAmount(),customerSubAccountPoint.getAvailablePoints(),customerSubAccountPoint.getFrozenPoints(),
		    		CustomerSubAccountPointType.LENDING, remark, productSubAccount.getOrderFormId(), 
		    		customerSubAccount.getId(), customerSubAccount.getId(), borrowCustomerSubAccountId, customerSubAccount.getId(),
		    		false, productSubAccount.getCustomerId(),productId,null,null);
			
		    //TODO 处理借款人资金处理
		    
		    CustomerSubAccountPoint borrowCustomerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(borrowCustomerSubAccountId);
		    
		    borrowCustomerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(borrowCustomerSubAccountPoint.getAvailablePoints(), productSubAccount.getAmount(), 4));
		    customerSubAccountPointService.update(borrowCustomerSubAccountPoint);
		    
		    CustomerSubAccount borrowCustomerSubAccount = customerSubAccountService.findById(borrowCustomerSubAccountId);
		    
		    String remarkBorrow = "放款收款，投标订单编号【"+productSubAccount.getOrderFormId()+"】，产品【"+product.getProductName()+"】,金额【+"+productSubAccount.getAmount()+"】";
		    customerSubAccountPointLogService.add(productSubAccount.getAmount(), borrowCustomerSubAccountPoint.getAvailablePoints(),borrowCustomerSubAccountPoint.getFrozenPoints(),
		    		CustomerSubAccountPointType.LENDING, remarkBorrow, productSubAccount.getOrderFormId(), 
		    		borrowCustomerSubAccountId, customerSubAccount.getId(), borrowCustomerSubAccountId, customerSubAccount.getId(),
		    		false, borrowCustomerSubAccount.getCustomerId(),productId,null,null);
		    
		    
//		    // 处理积分
//		    try {
//		    	int score = (int) (productSubAccount.getAmount()/10);
//		        scoreRecordService.addScoreRecord(productSubAccount.getCustomerId(), score, ScoreOperType.INVITE_SEND,Conf.SCORE_OPERATOR_SYSTEM);
//		    } catch (Exception e1) {
//		        e1.printStackTrace();
//		        logger.info("投资赠送积分失败，投资记录=" + productSubAccount.getId());
//		    }
//		    
//		    
		}
		
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductSubAccount updateForRepayment(int productSubAccountId,
			double interest, double principal, double defaultInterest,String orderFormId,
			int realFromCustomerSubAccountId, int productRepayRecordId,int phaseNumber, 
			ProductRecoveryRecordType type) {
		ProductSubAccount productSubAccount = findById(productSubAccountId);
		//所有资金都需要回到主账户
		productSubAccount.setTotalRepaiedInterest(CalculateUtil.doubleAdd(productSubAccount.getTotalRepaiedInterest(), interest, 4));
		productSubAccount.setTotalDefaulAndInterest(CalculateUtil.doubleAdd(productSubAccount.getTotalDefaulAndInterest(),CalculateUtil.doubleAdd(interest, defaultInterest, 4), 4));
		productSubAccount.setTotalInterest(productSubAccount.getTotalRepaiedInterest());
		productSubAccount.setWaittingPrincipal(CalculateUtil.doubleSubtract(productSubAccount.getWaittingPrincipal(), principal, 4));
		productSubAccountDao.update(productSubAccount);
		
		// 处理产品账户
		productAccountService.updateForRepayment(productSubAccount.getProductBuyerId(), interest, principal, defaultInterest);
		
		// 处理资金记录(回收的资金进入渠道账户)，资金记录需要记录3次,同时需要更新平衡金账户资金
		
		//用户账户资金
		CustomerSubAccountPoint customerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(productSubAccount.getCustomerSubAccountId());
		//用户账户收入包括：利息、罚息和本金，每个都需要记录一次资金记录
		
		//产品
		Product product = productService.findById(productSubAccount.getProductId());
		
		//平衡金账户
		Customer balanceCustomer = customerService.findByUsername(Conf.SYSTEM_CUSTOMER_BALANCE);
		CustomerSubAccount balanceCustomerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(balanceCustomer.getId(), product.getProductChannelId());
		CustomerSubAccountPoint balanceCustomerSubAccountPoint = customerSubAccountPointService.findCustomerSubAccountPointByCustomerSubAccountId(balanceCustomerSubAccount.getId());
		
		//本金资金记录
		if(principal > 0){
			customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getAvailablePoints(), principal,4));
			balanceCustomerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleSubtract(balanceCustomerSubAccountPoint.getAvailablePoints(), principal, 4));
			String remark = "产品【"+product.getProductName()+"】"+type.toString()+"回收本金"+principal+"元";
			customerSubAccountPointLogService.add(principal, customerSubAccountPoint.getAvailablePoints(), customerSubAccountPoint.getFrozenPoints(), 
					CustomerSubAccountPointType.REPAYMENT_PRICIPAL , remark, orderFormId, productSubAccount.getCustomerSubAccountId(), 
					balanceCustomerSubAccount.getId(), productSubAccount.getCustomerSubAccountId(),realFromCustomerSubAccountId, true, 
					productSubAccount.getCustomerId(), productSubAccount.getProductId(),null,null);
			
			String balanceRemark = "产品【"+product.getProductName()+"】"+type.toString()+"本金"+principal+"元";
			customerSubAccountPointLogService.add(principal, balanceCustomerSubAccountPoint.getAvailablePoints(), balanceCustomerSubAccountPoint.getFrozenPoints(), 
					CustomerSubAccountPointType.REPAYMENT_PRICIPAL , balanceRemark, orderFormId, balanceCustomerSubAccount.getId(), 
					balanceCustomerSubAccount.getId(), productSubAccount.getCustomerSubAccountId(), realFromCustomerSubAccountId, false, 
					productSubAccount.getCustomerId(), productSubAccount.getProductId(),null,null);
		}
		//利息资金记录
		if(interest > 0){
			customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getAvailablePoints(), interest,4));
			balanceCustomerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleSubtract(balanceCustomerSubAccountPoint.getAvailablePoints(), interest, 4));
			String remark = "产品【"+product.getProductName()+"】"+type.toString()+"回收利息"+interest+"元";
			customerSubAccountPointLogService.add(interest, customerSubAccountPoint.getAvailablePoints(), customerSubAccountPoint.getFrozenPoints(), 
					CustomerSubAccountPointType.REPAYMENT_INTEREST , remark, orderFormId, productSubAccount.getCustomerSubAccountId(), 
					balanceCustomerSubAccount.getId(), productSubAccount.getCustomerSubAccountId(), realFromCustomerSubAccountId, true, 
					productSubAccount.getCustomerId(), productSubAccount.getProductId(),null,null);
			
			String balanceRemark = "产品【"+product.getProductName()+"】"+type.toString()+"利息"+interest+"元";
			customerSubAccountPointLogService.add(interest, balanceCustomerSubAccountPoint.getAvailablePoints(), balanceCustomerSubAccountPoint.getFrozenPoints(), 
					CustomerSubAccountPointType.REPAYMENT_INTEREST , balanceRemark, orderFormId, balanceCustomerSubAccount.getId(), 
					balanceCustomerSubAccount.getId(), productSubAccount.getCustomerSubAccountId(), realFromCustomerSubAccountId, false, 
					productSubAccount.getCustomerId(), productSubAccount.getProductId(),null,null);
		}
		//罚息资金记录
		if(defaultInterest > 0){
			customerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleAdd(customerSubAccountPoint.getAvailablePoints(), defaultInterest,4));
			balanceCustomerSubAccountPoint.setAvailablePoints(CalculateUtil.doubleSubtract(balanceCustomerSubAccountPoint.getAvailablePoints(), defaultInterest, 4));
			String remark = "产品【"+product.getProductName()+"】"+type.toString()+"罚息"+defaultInterest+"元";
			customerSubAccountPointLogService.add(defaultInterest, customerSubAccountPoint.getAvailablePoints(), customerSubAccountPoint.getFrozenPoints(), 
					CustomerSubAccountPointType.REPAYMENT_DEFAULT_INTEREST , remark, orderFormId, productSubAccount.getCustomerSubAccountId(), 
					balanceCustomerSubAccount.getId(), productSubAccount.getCustomerSubAccountId(), realFromCustomerSubAccountId, true, 
					productSubAccount.getCustomerId(), productSubAccount.getProductId(),null,null);
			
			String balanceRemark = "产品【"+product.getProductName()+"】"+type.toString()+"还款罚息"+defaultInterest+"元";
			customerSubAccountPointLogService.add(defaultInterest, balanceCustomerSubAccountPoint.getAvailablePoints(), balanceCustomerSubAccountPoint.getFrozenPoints(), 
					CustomerSubAccountPointType.REPAYMENT_DEFAULT_INTEREST , balanceRemark, orderFormId, balanceCustomerSubAccount.getId(), 
					balanceCustomerSubAccount.getId(), productSubAccount.getCustomerSubAccountId(), realFromCustomerSubAccountId, false, 
					productSubAccount.getCustomerId(), productSubAccount.getProductId(),null,null);
		}
		customerSubAccountPointService.update(balanceCustomerSubAccountPoint);
		customerSubAccountPointService.update(customerSubAccountPoint);
		
		productAccountService.updateStatus(productSubAccount.getProductBuyerId());
		
		// 添加回收记录
		if(null != type){//type == null 是逾期还款，还款记录已经添加
			productRecoveryRecordService.addProductRecoveryRecord(productSubAccount.getProductId(), productSubAccountId, 
					productRepayRecordId, phaseNumber, interest, principal, defaultInterest, type);
		}
		
		return productSubAccount;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductSubAccount updateInterest(double dailyInterest,
			int productSubAccountId) {
		
		ProductSubAccount productSubAccount = findById(productSubAccountId);
	    if (null == productSubAccount) {
	        throw new ProductException("产品子账户未找到");
	    }
	    Date now = new Date();
	    //需要做时间判断
	    Date lastDate = productSubAccount.getLastInterestUpdateDate();
	    if(null == lastDate){
	    	Product product = productService.findById(productSubAccount.getProductId());
	    	lastDate = product.getPassTime();
	    }
	    
	    System.out.println(now+"---"+lastDate);
	    int days = DateTools.getBetweenDayNumber(DateTools.stringToDate(DateTools.dateToString(lastDate,DateTools.DATE_PATTERN_DAY)+DateTools.DAY_FIRST_TIME, DateTools.DATE_PATTERN_DEFAULT), 
	    		DateTools.stringToDate(DateTools.dateToString(now,DateTools.DATE_PATTERN_DAY)+DateTools.DAY_FIRST_TIME, DateTools.DATE_PATTERN_DEFAULT));
	  
	    System.out.println("距离上次更新时间："+days);
	    if(days == 0){
	    	 // 更新产品子账户
	  	    productAccountService.updateInterest(productSubAccount.getProductBuyerId(), dailyInterest);
	    	return productSubAccount;
	    } else {
	    	  double totalInterest = CalculateUtil.doubleMultiply(dailyInterest, days, 4);
	  	    
	  	    productSubAccount.setDailyInterest(dailyInterest);
	  	    productSubAccount.setLastInterestUpdateDate(now);
	  	    
	  	    //TODO 这个地方还需要再考虑一下
	  	    productSubAccount.setTotalInterest(CalculateUtil.doubleAdd(productSubAccount.getTotalInterest(), totalInterest, 4));
	  	    String yieldPeriod = DateTools.dateToString(now, DateTools.DATE_PATTERN_DAY);
	  	    
	  	    // 添加收益记录
	  	    productSubAccountEarningRecordService.add(productSubAccountId, dailyInterest, yieldPeriod);

	  	    productSubAccountPointLogService.addProductSubAccountPointLog(productSubAccount.getCustomerId(), productSubAccount.getCustomerSubAccountId(),
	  	    		productSubAccount.getId(), productSubAccount.getProductId(), productSubAccount.getCustomerSubAccountId(),
	  	    		productSubAccount.getCustomerId(), ProductSubAccountPointLogType.CONUNT_INTEREST, dailyInterest);
	  	    
	  	    productSubAccount = productSubAccountDao.update(productSubAccount);
	  	    
	  	    // 更新产品子账户
	  	    productAccountService.updateInterest(productSubAccount.getProductBuyerId(), dailyInterest);
	  	    
	  	    return productSubAccount;
	    }
	    
	  
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ProductSubAccount> findByCustomerId(int customerId) {
		return productSubAccountDao.findByCustomerId(customerId);

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductSubAccount updateOver(int productId) {
		
		List<ProductSubAccount> productSubAccounts = findByProductId(productId);
		
		if(null == productSubAccounts || productSubAccounts.size() == 0){
			return null;
		}
		
		for(ProductSubAccount productSubAccount : productSubAccounts){
			if(!ProductBuyerRecordStatus.isContain(productSubAccount.getStatus(), ProductBuyerRecordStatus.FAILED_STATUS)){
				productSubAccount.setStatus(ProductBuyerRecordStatus.OVER);
				productSubAccountDao.update(productSubAccount);
				productAccountService.updateStatus(productSubAccount.getProductBuyerId());
			}
		}
		
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ProductSubAccount> findByProductId(int productId) {
		return productSubAccountDao.findByProductId(productId);
	}
}
