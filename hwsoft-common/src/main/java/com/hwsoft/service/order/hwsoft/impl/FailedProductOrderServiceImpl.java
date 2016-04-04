package com.hwsoft.service.order.hwsoft.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.conf.BusinessConf;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.common.product.ProductStatus;
import com.hwsoft.dao.order.hwsoft.FailedProductOrderDao;
import com.hwsoft.exception.failed.FailedProductException;
import com.hwsoft.model.order.hwsoft.FailedProductOrder;
import com.hwsoft.model.product.Product;
import com.hwsoft.service.log.product.FailedProductLogService;
import com.hwsoft.service.order.hwsoft.FailedProductOrderService;
import com.hwsoft.service.product.ProductService;
import com.hwsoft.util.order.OrderUtil;
import com.hwsoft.vo.order.FailedProductOrderVo;

/**
 * 流标
 * @author tzh
 *
 */
@Service("failedProductOrderService")
public class FailedProductOrderServiceImpl implements FailedProductOrderService {
	
	@Autowired
	private FailedProductOrderDao failedProductOrderDao;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FailedProductLogService failedProductLogService;

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public FailedProductOrder addFailedProductOrderNoBid(int productId,
			int staffId, String staffName, String notes) {
		
		Product product = productService.findById(productId);
		if(null == product){
			throw new FailedProductException("产品不存在");
		}
		
		if(product.getProductStatus().equals(ProductStatus.LOCK_UP) || product.getProductStatus().equals(ProductStatus.OPEN_REDEMPTION) 
				|| product.getProductStatus().equals(ProductStatus.REDEMPTION) 
				|| product.getProductStatus().equals(ProductStatus.ENDED)){
			throw new FailedProductException("该产品现在不能流标");
		}
		
		//流标处理，
		if(product.getProductStatus().equals(ProductStatus.SALES) || product.getProductStatus().equals(ProductStatus.SALES_OVER)){
			//throw new FailedProductException("操作用户不存在");
			return addFailedProductOrderBid(productId, staffId, staffName, notes);
		} else {
			//未开标，直接将状态更新为流标，同时添加流标订单（或者说流标日志）
			FailedProductOrder failedProductOrder = new FailedProductOrder();
			failedProductOrder.setCreateTime(new Date());
			failedProductOrder.setNotes(notes);
			failedProductOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
			failedProductOrder.setOrderType(OrderType.FAILED_hwsoft_LOAN);
			failedProductOrder.setProductId(productId);
			failedProductOrder.setStaffId(staffId);
			failedProductOrder.setStaffName(staffName);
			failedProductOrderDao.save(failedProductOrder);
			String orderFormId = OrderUtil.getOrderFormId(failedProductOrder.getOrderType().ordinal(), 
					failedProductOrder.getId(), BusinessConf.hwsoft_ORDER_SERIALNUMBER_LENGTH);
			failedProductOrder.setOrderFormId(orderFormId);
			
			// 添加订单日志
			failedProductLogService.addFailedProductLog(productId, orderFormId, staffId, staffName, notes);
			
			return failedProductOrder;
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public FailedProductOrder addFailedProductOrderBid(int productId,
			int staffId, String staffName, String notes) {
		Product product = productService.findById(productId);
		if(null == product){
			throw new FailedProductException("产品不存在");
		}
		
		if(product.getProductStatus().equals(ProductStatus.LOCK_UP) || product.getProductStatus().equals(ProductStatus.OPEN_REDEMPTION) 
				|| product.getProductStatus().equals(ProductStatus.REDEMPTION) 
				|| product.getProductStatus().equals(ProductStatus.ENDED)){
			throw new FailedProductException("该产品现在不能流标");
		}
		
		//流标处理，
		if(product.getProductStatus().equals(ProductStatus.SALES) || product.getProductStatus().equals(ProductStatus.SALES_OVER)){
			//throw new FailedProductException("操作用户不存在");
			//未开标，直接将状态更新为流标，同时添加流标订单（或者说流标日志）
			FailedProductOrder failedProductOrder = new FailedProductOrder();
			failedProductOrder.setCreateTime(new Date());
			failedProductOrder.setNotes(notes);
			failedProductOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
			failedProductOrder.setOrderType(OrderType.FAILED_hwsoft_LOAN);
			failedProductOrder.setProductId(productId);
			failedProductOrder.setStaffId(staffId);
			failedProductOrder.setStaffName(staffName);
			failedProductOrderDao.save(failedProductOrder);
			String orderFormId = OrderUtil.getOrderFormId(failedProductOrder.getOrderType().ordinal(), 
					failedProductOrder.getId(), BusinessConf.hwsoft_ORDER_SERIALNUMBER_LENGTH);
			failedProductOrder.setOrderFormId(orderFormId);
			
			failedProductLogService.addFailedProductLog(productId, orderFormId, staffId, staffName, notes);
			// 处理产品状态
			productService.updateFailedProduct(productId);
			
			return failedProductOrder;
		} else {
			//未开标，直接将状态更新为流标，同时添加流标订单（或者说流标日志）
			
			return addFailedProductOrderNoBid(productId, staffId, staffName, notes);
			
		}
		
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<FailedProductOrderVo> listAll(OrderStatus orderStatus,
			Date startTime, Date endTime, int from, int pageSize) {
		return failedProductOrderDao.listAll(orderStatus, startTime, endTime, from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Long getTotalCount(OrderStatus orderStatus, Date startTime,
			Date endTime) {
		return failedProductOrderDao.getTotalCount(orderStatus, startTime, endTime);
	}

}
