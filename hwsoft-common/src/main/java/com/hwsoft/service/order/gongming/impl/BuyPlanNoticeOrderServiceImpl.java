/**
 * 
 */
package com.hwsoft.service.order.gongming.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.product.ProductBuyerRecordStatus;
import com.hwsoft.dao.order.gongming.BuyPlanNoticeOderDao;
import com.hwsoft.exception.gongming.BuyPlanNoticeOrderException;
import com.hwsoft.model.order.common.BuyPlanOrder;
import com.hwsoft.model.order.gongming.BuyPlanNoticeOrder;
import com.hwsoft.service.order.gongming.BuyPlanNoticeOrderService;
import com.hwsoft.service.order.gongming.BuyPlanOrderService;
import com.hwsoft.service.product.ProductSubAccountService;

/**
 * @author tzh
 *
 */
@Service("buyPlanNoticeOrderService")
public class BuyPlanNoticeOrderServiceImpl implements BuyPlanNoticeOrderService {
	
	private Log logger = LogFactory.getLog(BuyPlanNoticeOrderServiceImpl.class);
	
	@Autowired
	private BuyPlanNoticeOderDao buyPlanNoticeOderDao;
	
	@Autowired
	private BuyPlanOrderService buyPlanOrderService;
	
    @Autowired
    private ProductSubAccountService productSubAccountService;

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.BuyPlanNoticeOrderService#addNoticeOrder(java.lang.String, com.hwsoft.common.product.ProductBuyerRecordStatus, com.hwsoft.common.product.ProductBuyerRecordStatus, java.util.Date)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public BuyPlanNoticeOrder addNoticeOrder(String buyPlanOrderFormId,
			ProductBuyerRecordStatus currentStatus,
			ProductBuyerRecordStatus beforeStatus, 
			Date updateTime,String response) {
		
		BuyPlanOrder buyPlanOrder = buyPlanOrderService.findBuyPlanOrderByOrderFormId(buyPlanOrderFormId);
		if(null == buyPlanOrder){
			throw new BuyPlanNoticeOrderException("产品申购订单未找到");
		}
		if(currentStatus.ordinal() < buyPlanOrder.getOrderStatus().ordinal()){
			logger.info("订单状态异常");
//			throw new BuyPlanNoticeOrderException("订单状态异常");
			return null;
		}
		
		BuyPlanNoticeOrder buyPlanNoticeOrder = new BuyPlanNoticeOrder();
		buyPlanNoticeOrder.setBeforeStatus(beforeStatus);
		buyPlanNoticeOrder.setMemberId(buyPlanOrder.getMemberId());
		buyPlanNoticeOrder.setProductId(buyPlanOrder.getProductId());
		buyPlanNoticeOrder.setStatus(currentStatus);
		buyPlanNoticeOrder.setStatusUpdateTime(updateTime);
		buyPlanNoticeOrder.setThirdProductId(buyPlanOrder.getThirdProductId());
		buyPlanNoticeOrder.setUserId(buyPlanOrder.getUserId());
		buyPlanNoticeOrder.setResponse(response);
		buyPlanNoticeOrder.setProductBuyerRecordId(buyPlanOrder.getProductBuyerRecordId());
		buyPlanNoticeOrder.setBuyPlanOrderFormId(buyPlanOrderFormId);
		buyPlanNoticeOderDao.save(buyPlanNoticeOrder);
//		if(currentStatus.equals(buyPlanOrder.getOrderStatus())){
//			return buyPlanNoticeOrder;
//		} else {
//			if(currentStatus.equals(ProductBuyerRecordStatus.HAS_PAIED)){//订单支付
//				productSubAccountService.updateByPaySuccess(buyPlanOrder.getProductBuyerRecordId(), buyPlanOrder.getBuySuccessAmount(),null);
//			} else if(currentStatus.equals(ProductBuyerRecordStatus.ORDER_TERMINATED)){ //订单失效
//				productSubAccountService.updateByPlaceOrderFailed(buyPlanOrder.getProductBuyerRecordId(),null);
//			} else if(currentStatus.equals(ProductBuyerRecordStatus.HAS_BID)){ //已投标
//				productSubAccountService.updateByOrderHasBid(buyPlanOrder.getProductBuyerRecordId(),null);
//			} else if(currentStatus.equals(ProductBuyerRecordStatus.BIDING)){ //投标中
//				productSubAccountService.updateByOrderBiding(buyPlanOrder.getProductBuyerRecordId(),null);
//			} else if(currentStatus.equals(ProductBuyerRecordStatus.TRANSFERRING)){ //债权转让中
//				productSubAccountService.updateByOrderTransferring(buyPlanOrder.getProductBuyerRecordId(),null);
//			} else if(currentStatus.equals(ProductBuyerRecordStatus.REDEEMED)){ //已赎回
//				productSubAccountService.updateByOrderRedeemed(buyPlanOrder.getProductBuyerRecordId(),null,null);
//			} else if(currentStatus.equals(ProductBuyerRecordStatus.HAS_WITHDRAWAL)){ //已提现
//				productSubAccountService.updateByOrderHasWaithdrawal(buyPlanOrder.getProductBuyerRecordId(),null);
//			} else if(currentStatus.equals(ProductBuyerRecordStatus.WAITTING_REFUND)){ //待退款
//				productSubAccountService.updateByOrderWattingRefund(buyPlanOrder.getProductBuyerRecordId(),null);
//			} else if(currentStatus.equals(ProductBuyerRecordStatus.HAS_REFUND)){ //已退款
//				productSubAccountService.updateByOrderHasRefund(buyPlanOrder.getProductBuyerRecordId(),null);
//			} else if(currentStatus.equals(ProductBuyerRecordStatus.REDEMPTION)){ //赎回中
//				productSubAccountService.updateByOrderRedemption(buyPlanOrder.getProductBuyerRecordId(),null);
//			}
//		}
		
		return buyPlanNoticeOrder;
	}

}
