package com.hwsoft.service.order.hwsoft.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.conf.BusinessConf;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.common.product.ProductBidType;
import com.hwsoft.dao.order.hwsoft.LendingProductOrderDao;
import com.hwsoft.exception.lending.LendingException;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.order.common.BuyPlanOrder;
import com.hwsoft.model.order.hwsoft.LendingProductOrder;
import com.hwsoft.model.order.hwsoft.LendingProductSubAccountDetail;
import com.hwsoft.model.product.Product;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.log.product.LendingProductLogService;
import com.hwsoft.service.order.gongming.BuyPlanOrderService;
import com.hwsoft.service.order.hwsoft.LendingProductOrderService;
import com.hwsoft.service.product.ProductService;
import com.hwsoft.util.math.CalculateUtil;
import com.hwsoft.util.order.OrderUtil;
import com.hwsoft.vo.order.LendingProductOrderVo;
import com.hwsoft.vo.order.LendingProductSubAccountDetailVo;

/**
 * 
 * @author tzh
 *
 */
@Service("lendingProductOrderService")
public class LendingProductOrderServiceImpl implements
		LendingProductOrderService {

	@Autowired
	private BuyPlanOrderService buyPlanOrderService;
	
	@Autowired
	private LendingProductOrderDao lendingProductOrderDao;
	
	@Autowired	
	private LendingProductLogService lendingProductLogService;
	
	@Autowired
	private ProductService productService ;
	
	@Autowired
	private CustomerSubAccountService customerSubAccountService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public LendingProductOrder addLendingProductOrder(int productId,
			int staffId, String staffName) {
		
		Product product = productService.findById(productId);
		
		CustomerSubAccount customerSubAccount = customerSubAccountService.findById(product.getCustomerSubAccountId());
		if(null == customerSubAccount){
			throw new LendingException("借款人未找到");
		}
		
		// 添加订单
		Date now = new Date();
		LendingProductOrder lendingProductOrder = new LendingProductOrder();
		lendingProductOrder.setCreateTime(now);
		lendingProductOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
		lendingProductOrder.setOrderType(OrderType.LENDING_hwsoft_LOAN);
		lendingProductOrder.setProductId(productId);
		//CalculateUtil.doubleMultiply(product.getSaleAmount(),product.getBaseAmount(),4)
		lendingProductOrder.setAmount(CalculateUtil.doubleSubtract(product.getTotalAmount(), product.getDummyBoughtAmount() == null ? 0 : product.getDummyBoughtAmount(), 4));
		lendingProductOrder.setOpenAmount(CalculateUtil.doubleSubtract(product.getTotalAmount(), product.getDummyBoughtAmount() == null ? 0 : product.getDummyBoughtAmount(), 4));
		lendingProductOrder.setBorrowCustomerId(customerSubAccount.getCustomerId());
		lendingProductOrder.setBorrowCustomerSubAccountId(customerSubAccount.getId());
		
		// 放款详情，
		List<BuyPlanOrder> buyPlanOrders = buyPlanOrderService.listByProductId(productId);
		if(null == buyPlanOrders || buyPlanOrders.size() == 0){
			throw new LendingException("未找到该产品的购买订单");
		}
		
		List<LendingProductSubAccountDetail> lendingProductSubAccountDetails = new ArrayList<LendingProductSubAccountDetail>();
		for(BuyPlanOrder buyPlanOrder : buyPlanOrders){
			LendingProductSubAccountDetail lendingProductSubAccountDetail = new LendingProductSubAccountDetail();
			if(buyPlanOrder.getOrderStatus().equals(OrderStatus.HANDLE_SUCESS) && buyPlanOrder.getOrderType().equals(OrderType.BID_hwsoft_LOAN)){
				lendingProductSubAccountDetail.setAmount(buyPlanOrder.getBuySuccessAmount());
				lendingProductSubAccountDetail.setBidOrderFormId(buyPlanOrder.getOrderFormId());
				lendingProductSubAccountDetail.setProductId(productId);
				if(buyPlanOrder.getOrderType().equals(OrderType.BID_hwsoft_LOAN)){
					lendingProductSubAccountDetail.setProductBidType(ProductBidType.HANDLE_BID);
				}else {
					lendingProductSubAccountDetail.setProductBidType(ProductBidType.GONGMING_PRODUCT_BUY);
				}
				lendingProductSubAccountDetails.add(lendingProductSubAccountDetail);
			}
			
		}
		
		lendingProductOrder.setSubAccountDetails(lendingProductSubAccountDetails);
		
		lendingProductOrderDao.save(lendingProductOrder);
		// 分账详情，待处理,暂未分账，线下自行处理
		
		//订单编号
		String orderFormId = OrderUtil.getOrderFormId(lendingProductOrder.getOrderType().ordinal(), 
				lendingProductOrder.getId(), BusinessConf.hwsoft_ORDER_SERIALNUMBER_LENGTH);
		lendingProductOrder.setOrderFormId(orderFormId);
		
		
		// 添加订单日志
		String notes = "管理员【"+staffName+"】对产品【"+productId+"】进行放款操作";
		lendingProductLogService.addLendingProductLog(productId, orderFormId, staffId, staffName, notes);
		
		// 处理放款，放款记录，产品及相关账户处理，资金及记录相关处理，提现
		productService.updateLendingProduct(productId);
		
		return lendingProductOrder;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<LendingProductOrderVo> listAll(OrderStatus orderStatus,
			Date startTime, Date endTime, int from, int pageSize) {
		return lendingProductOrderDao.listAll(orderStatus, startTime, endTime, from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Long getTotalCount(OrderStatus orderStatus, Date startTime,
			Date endTime) {
		return lendingProductOrderDao.getTotalCount(orderStatus, startTime, endTime);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public LendingProductOrderVo findWholeById(int id) {
		LendingProductOrderVo lendingProductOrderVo = lendingProductOrderDao.findVoById(id);
		if(null != lendingProductOrderVo){
			List<LendingProductSubAccountDetailVo> vos = lendingProductOrderDao.finDetailVosByOrderId(id);
			lendingProductOrderVo.setDetailVos(vos);
		}
		return lendingProductOrderVo;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public LendingProductOrder findByOrderFormId(String orderFormId) {
		return lendingProductOrderDao.findByOrderFormId(orderFormId);
	}

}
