/**
 * 
 */
package com.hwsoft.service.order.gongming.impl;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.bank.UserBankCardBindStatus;
import com.hwsoft.common.conf.BusinessConf;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.common.product.ProductBidType;
import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.common.product.ProductStatus;
import com.hwsoft.dao.order.gongming.BuyPlanOrderDao;
import com.hwsoft.exception.bid.BidException;
import com.hwsoft.exception.product.ProductException;
import com.hwsoft.model.bank.UserBankCard;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.order.common.BuyPlanOrder;
import com.hwsoft.model.product.Product;
import com.hwsoft.model.product.ProductSaleTimeInfo;
import com.hwsoft.model.product.ProductSubAccount;
import com.hwsoft.model.product.ProductChannel;
import com.hwsoft.service.bank.UserBankCardService;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.log.product.BidProductLogService;
import com.hwsoft.service.order.gongming.BuyPlanOrderService;
import com.hwsoft.service.product.ProductSaleTimeInfoService;
import com.hwsoft.service.product.ProductSubAccountService;
import com.hwsoft.service.product.ProductChannelService;
import com.hwsoft.service.product.ProductService;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.math.CalculateUtil;
import com.hwsoft.util.order.OrderUtil;
import com.hwsoft.vo.order.BuyOrderVo;

/**
 * @author tzh
 *
 */
@Service("buyPlanOrderService")
public class BuyPlanOrderServiceImpl implements BuyPlanOrderService {


	private static Logger logger = Logger.getLogger("buyPlanOrderServiceImpl");
	
	@Autowired
	private BuyPlanOrderDao buyPlanOrderDao;
	
	@Autowired
	private ProductService productService;
	
    @Autowired
    private ProductChannelService productChannelService;
    
    @Autowired
    private CustomerSubAccountService customerSubAccountService;
    
    @Autowired
    private UserBankCardService userBankCardService;
    
    @Autowired
    private ProductSubAccountService productSubAccountService;
    
    @Autowired
	private ProductSaleTimeInfoService productSaleTimeInfoService;
    
    @Autowired
    private BidProductLogService bidProductLogService;
    
	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.BuyPlanOrderService#addBuyPlanOrder(int, int, double, int)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public BuyPlanOrder addBuyPlanOrder(int userId, int productId,
			double amount, int userBankCardId) {

		
		Product product = productService.findById(productId);
		if(null == product){
			logger.info("产品未找到");
			throw new ProductException("产品未找到");
		}
		if(!product.getProductStatus().equals(ProductStatus.SALES)){
			logger.info("该产品不在销售期");
			throw new ProductException("该产品不在销售期");
		}
		
		ProductSaleTimeInfo productSaleTimeInfo = productSaleTimeInfoService.findByProductId(product.getId());
		if(null != productSaleTimeInfo){
			//TODO 进行时间判断
			int hour = DateTools.getCurrentStateHour();
			int minute = DateTools.getCurrentStateMinute();
			
			if( hour < productSaleTimeInfo.getStartHour() || hour > productSaleTimeInfo.getEndHour()){
				logger.info("对不起，第三方正在结算中，不能购买");
				throw new ProductException("对不起，第三方正在结算中，不能购买");
			}
			if(hour == productSaleTimeInfo.getStartHour() && minute < productSaleTimeInfo.getStartMinute()){
				
				logger.info("对不起，第三方正在结算中，不能购买");
				throw new ProductException("对不起，第三方正在结算中，不能购买");
			}
			
			if(hour == productSaleTimeInfo.getEndHour() && minute > productSaleTimeInfo.getEndMinute()){
				logger.info("对不起，第三方正在结算中，不能购买");
				throw new ProductException("对不起，第三方正在结算中，不能购买");
			}
		}
		ProductChannel productChannel = productChannelService.findById(product.getProductChannelId());
		if(null == productChannel){
			logger.info("没有找到产品渠道："+ProductChannelType.GONGMING.toString());
			throw new ProductException("没有找到产品渠道："+ProductChannelType.GONGMING.toString());
		}
		if(product.getProductChannelId() != productChannel.getId()){
			logger.info("该产品不是来自共鸣");
			throw new ProductException("该产品不是来自共鸣");
		}
		if(amount < product.getMinAmount() || amount > product.getMaxAmount()){
			logger.info("申购金额不正确");
			throw new ProductException("申购金额不正确");
		}
		if(amount % product.getBaseAmount() != 0){
			logger.info("申购金额超过产品可售金额");
			throw new ProductException("申购金额不正确");
		}
		//double dummy_bought_amount
		double dummyBoughtAmount = 0D;
		if(product.isEnableDummyBoughtAmount() && null != product.getDummyBoughtAmount()){
			dummyBoughtAmount = product.getDummyBoughtAmount();
		}
		if(CalculateUtil.doubleSubtract(CalculateUtil.doubleAdd(product.getRemainingAmount(),dummyBoughtAmount,2), amount, 2) < 0){
			logger.info("申购金额超过产品可售金额");
			throw new ProductException("申购金额超过产品可售金额");
		}
		CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(userId, productChannel.getId());
		if(null == customerSubAccount){
			throw new ProductException("共鸣账户未找到");
		}
		
		UserBankCard userBankCard = userBankCardService.findUserBankCardById(userBankCardId);
		if(null == userBankCard){
			logger.info("申购银行卡不存在");
			throw new ProductException("申购银行卡不存在");
		}
		if(userBankCard.getCustomerSubAccountId() != customerSubAccount.getId() 
        		&& !userBankCard.getCustomerSubAccountId().equals(customerSubAccount.getId())){
			logger.info("申购银行卡不属于该共鸣账户:  "+ "userbankcard: "+userBankCard.toString() +" customerSubAccount  "+ customerSubAccount.toString());
			throw new ProductException("申购银行卡不属于该共鸣账户");
		}
		if(!userBankCard.getStatus().equals(UserBankCardBindStatus.BINDED)){
			logger.info("申购银行卡未绑定或已经解除绑定");
			throw new ProductException("申购银行卡未绑定或已经解除绑定");
		}
		
		BuyPlanOrder buyPlanOrder = new BuyPlanOrder();
		buyPlanOrder.setBuyAmount(amount);
		buyPlanOrder.setCreateTime(new Date());
		buyPlanOrder.setMemberId(customerSubAccount.getMemberId());
		buyPlanOrder.setOrderStatus(OrderStatus.WAITING_HANDLE);
		buyPlanOrder.setProductId(product.getId());
		buyPlanOrder.setThirdProductId(product.getThirdProductId());
		buyPlanOrder.setUserBankCardId(userBankCard.getId());
		buyPlanOrder.setUserBankCardNumber(userBankCard.getBankCardNumber());
		buyPlanOrder.setUserId(customerSubAccount.getCustomerId());
		buyPlanOrder.setOrderType(OrderType.BUY_PLAN);
		buyPlanOrder.setBuyShareNum(new Double(amount).intValue());
		buyPlanOrderDao.add(buyPlanOrder);
		String orderFormId = OrderUtil.getOrderFormId(buyPlanOrder.getOrderType().ordinal(), 
				buyPlanOrder.getId(), BusinessConf.GONGMING_ORDER_SERIALNUMBER_LENGTH);
		
		buyPlanOrder.setOrderFormId(orderFormId);
		
		// 添加产品购买记录和产品子账户
		ProductSubAccount productBuyerRecord = productSubAccountService.add(
				customerSubAccount.getCustomerId(), amount, productId,
				buyPlanOrder.getOrderFormId(),userBankCard.getBankCardNumber(),userBankCardId);
		buyPlanOrder.setProductBuyerRecordId(productBuyerRecord.getId());
		
		return buyPlanOrder;
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.BuyPlanOrderService#findBuyPlanOrderByOrderFormId(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public BuyPlanOrder findBuyPlanOrderByOrderFormId(String orderFormId) {
		return buyPlanOrderDao.findByOrderFormId(orderFormId);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.BuyPlanOrderService#startBuyPlanOrder(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public BuyPlanOrder startBuyPlanOrder(String orderFormId) {
		BuyPlanOrder buyPlanOrder = findBuyPlanOrderByOrderFormId(orderFormId);
		if(null == buyPlanOrder){
			logger.info("申购订单请求未找到");
			throw new ProductException("申购订单请求未找到");
		}
		buyPlanOrder.setOrderStatus(OrderStatus.IN_HANDLE);
		return buyPlanOrderDao.update(buyPlanOrder);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.BuyPlanOrderService#updateByBuySuccess(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public BuyPlanOrder updateByBuySuccess(String orderFormId, String code,
			String backMsg,double buySuccessAmount,String response) {
		BuyPlanOrder buyPlanOrder = findBuyPlanOrderByOrderFormId(orderFormId);
		if(null == buyPlanOrder){
			logger.info("申购订单请求未找到");
			throw new ProductException("申购订单请求未找到");
		}
		
		buyPlanOrder.setBackCode(code);
		buyPlanOrder.setBackMsg(backMsg);
		buyPlanOrder.setBuySuccessAmount(buySuccessAmount);
		buyPlanOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
		buyPlanOrder.setResponse(response);
		// 处理产品订单
		productSubAccountService.updateByPlaceOrderSuccess(buyPlanOrder.getProductBuyerRecordId());
		
		return buyPlanOrderDao.update(buyPlanOrder);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.BuyPlanOrderService#updateByBuyFailed(java.lang.String, java.lang.String, java.lang.String, double)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public BuyPlanOrder updateByBuyFailed(String orderFormId, String code,
			String backMsg, double buySuccessAmount,String response) {
		BuyPlanOrder buyPlanOrder = findBuyPlanOrderByOrderFormId(orderFormId);
		if(null == buyPlanOrder){
			logger.info("申购订单请求未找到");
			throw new ProductException("申购订单请求未找到");
		}
		buyPlanOrder.setBackCode(code);
		buyPlanOrder.setBackMsg(backMsg);
		buyPlanOrder.setBuySuccessAmount(buySuccessAmount);
		buyPlanOrder.setBuySuccessAmount(new Double(buySuccessAmount).intValue());
		buyPlanOrder.setOrderStatus(OrderStatus.HANDLE_FAILED);
		buyPlanOrder.setResponse(response);
		// 处理产品订单
		productSubAccountService.updateByPlaceOrderFailed(buyPlanOrder.getProductBuyerRecordId(),null);
		return buyPlanOrderDao.update(buyPlanOrder);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.BuyPlanOrderService#updateByRequestFailed(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public BuyPlanOrder updateByRequestFailed(String orderFormId) {
		BuyPlanOrder buyPlanOrder = findBuyPlanOrderByOrderFormId(orderFormId);
		if(null == buyPlanOrder){
			logger.info("申购订单请求未找到");
			throw new ProductException("申购订单请求未找到");
		}
		buyPlanOrder.setOrderStatus(OrderStatus.REQUEST_FAILED);
		// 处理产品订单
		productSubAccountService.updateByPlaceOrderFailed(buyPlanOrder.getProductBuyerRecordId(),null);
		return buyPlanOrderDao.update(buyPlanOrder);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<BuyOrderVo> listAll(OrderStatus orderStatus,Date startTime,Date endTime,String mobile,String bankCardNumber,int from, int pageSize) {
		return buyPlanOrderDao.list(orderStatus, startTime, endTime,mobile,bankCardNumber, from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Long getTotalCount(OrderStatus orderStatus,Date startTime,Date endTime,String mobile,String bankCardNumber) {
		
		return buyPlanOrderDao.getTotalCount(orderStatus, startTime, endTime,mobile,bankCardNumber);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public BuyPlanOrder addBuyPlanOrderForBid(int customerId, int sharNum,
			int productId, ProductBidType productBidType,
			Integer bidProductId, Integer bidProductSubAccountId) {
		
		Product product = productService.findById(productId);
		if(null == product){
			logger.info("产品未找到");
			throw new ProductException("产品未找到");
		}
		if(!product.getProductStatus().equals(ProductStatus.SALES)){
			logger.info("该产品不在销售期");
			throw new ProductException("该产品不在销售期");
		}
		
		ProductSaleTimeInfo productSaleTimeInfo = productSaleTimeInfoService.findByProductId(product.getId());
		if(null != productSaleTimeInfo){
			//TODO 进行时间判断
			int hour = DateTools.getCurrentStateHour();
			int minute = DateTools.getCurrentStateMinute();
			
			if( hour < productSaleTimeInfo.getStartHour() || hour > productSaleTimeInfo.getEndHour()){
				logger.info("对不起，第三方正在结算中，不能购买");
				throw new ProductException("对不起，第三方正在结算中，不能购买");
			}
			if(hour == productSaleTimeInfo.getStartHour() && minute < productSaleTimeInfo.getStartMinute()){
				
				logger.info("对不起，第三方正在结算中，不能购买");
				throw new ProductException("对不起，第三方正在结算中，不能购买");
			}
			
			if(hour == productSaleTimeInfo.getEndHour() && minute > productSaleTimeInfo.getEndMinute()){
				logger.info("对不起，第三方正在结算中，不能购买");
				throw new ProductException("对不起，第三方正在结算中，不能购买");
			}
		}
		ProductChannel productChannel = productChannelService.findById(product.getProductChannelId());
		if(null == productChannel){
			logger.info("没有找到产品渠道："+product.getProductChannelId());
			throw new ProductException("没有找到产品渠道："+product.getProductChannelId());
		}
		if(product.getProductChannelId() != productChannel.getId()){
			logger.info("产品渠道不一致");
			throw new ProductException("产品渠道不一致");
		}
		if(sharNum < new Double(product.getMinAmount()).intValue()){
			logger.info("不能小于最低购买份额");
		      throw new BidException("不能小于最低购买份额");
		}
		
	    double amount = CalculateUtil.doubleMultiply(sharNum, product.getBaseAmount(), 4);
	    
//	    if (CalculateUtil.doubleSubtract(product.getRemainingAmount(), sharNum, 4) < 0) {
//	      logger.info("申购份额超过产品剩余可售份额");
//	      throw new BidException("申购份额超过产品剩余可售份额");
//	    }
//		
	    Double dummyBoughtAmount = 0D;
		if(product.isEnableDummyBoughtAmount() && null != product.getDummyBoughtAmount()){
			dummyBoughtAmount = product.getDummyBoughtAmount();
		}
		if(product.getRemainingAmount().intValue() < sharNum +dummyBoughtAmount.intValue() ){
			throw new BidException("对不起，剩余可购份额不足");
		}
	    
//		if(CalculateUtil.doubleSubtract(product.getRemainingAmount(), amount, 2) < 0){
//			logger.info("购买份额超过产品可售份额");
//			throw new ProductException("购买份额额超过产品可售份额");
//		}
		CustomerSubAccount customerSubAccount = customerSubAccountService.findByCustomerSubAccountByUserIdAndProductChannelId(customerId, productChannel.getId());
		if(null == customerSubAccount){
			throw new ProductException("投资账户未找到");
		}
		
		BuyPlanOrder buyPlanOrder = new BuyPlanOrder();
		buyPlanOrder.setBuyAmount(amount);
		buyPlanOrder.setCreateTime(new Date());
		buyPlanOrder.setMemberId(customerSubAccount.getMemberId());
		buyPlanOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);//请求成功
		buyPlanOrder.setProductId(product.getId());
		buyPlanOrder.setThirdProductId(product.getThirdProductId());
		buyPlanOrder.setUserId(customerSubAccount.getCustomerId());
		buyPlanOrder.setOrderType(OrderType.BID_hwsoft_LOAN);
		buyPlanOrder.setBuySuccessShareNum(sharNum);
		buyPlanOrder.setBuyShareNum(sharNum);
		buyPlanOrder.setBuySuccessAmount(amount);
		buyPlanOrderDao.add(buyPlanOrder);
		
		String orderFormId = OrderUtil.getOrderFormId(buyPlanOrder.getOrderType().ordinal(), 
				buyPlanOrder.getId(), BusinessConf.hwsoft_ORDER_SERIALNUMBER_LENGTH);
		
		buyPlanOrder.setOrderFormId(orderFormId);
		
		// 添加产品购买记录和产品子账户
		ProductSubAccount productBuyerRecord = productSubAccountService.bid(customerId, sharNum, productId, orderFormId, productBidType, bidProductId, bidProductSubAccountId);
		buyPlanOrder.setProductBuyerRecordId(productBuyerRecord.getId());
		
		// 处理订单日志
		String notes = "用户投标，productId:"+productId+",orderFormId:"+orderFormId+",productSubAccountId:"+productBuyerRecord.getId()+",shareNum:"+sharNum;
		bidProductLogService.add(orderFormId, productBidType, productId, productBuyerRecord.getId(), notes, bidProductId, bidProductSubAccountId, sharNum);
		return buyPlanOrder;
		
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<BuyPlanOrder> listByProductId(int productId) {
		return buyPlanOrderDao.listByProductId(productId);
	}

}
