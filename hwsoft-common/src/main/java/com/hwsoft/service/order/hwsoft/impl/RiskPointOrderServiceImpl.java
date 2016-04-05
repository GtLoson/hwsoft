package com.hwsoft.service.order.hwsoft.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.conf.BusinessConf;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.dao.order.hwsoft.RiskPointOrderDao;
import com.hwsoft.model.order.hwsoft.RiskPointOrder;
import com.hwsoft.service.log.product.RiskPointLogService;
import com.hwsoft.service.order.hwsoft.RiskPointOrderService;
import com.hwsoft.util.order.OrderUtil;

/**
 * 
 * @author tzh
 *
 */
@Service("riskPointOrderService")
public class RiskPointOrderServiceImpl implements RiskPointOrderService {

	@Autowired
	private RiskPointOrderDao riskPointOrderDao;
	
	@Autowired
	private RiskPointLogService riskPointLogService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RiskPointOrder add(Integer productId, OrderType orderType, double amount,
			Integer productRepayRecordId,String notes,String repaymentOrderFormId) {
		RiskPointOrder riskPointOrder = new RiskPointOrder();
		riskPointOrder.setAmount(amount);
		riskPointOrder.setCreateTime(new Date());
		riskPointOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
		riskPointOrder.setOrderType(orderType);
		riskPointOrder.setProductRepayRecordId(productRepayRecordId);
		riskPointOrder.setProductId(productId);
		riskPointOrder.setRepaymentOrderFormId(repaymentOrderFormId);
		
		riskPointOrderDao.save(riskPointOrder);
		
		//订单编号
		String orderFormId = OrderUtil.getOrderFormId(riskPointOrder.getOrderType().ordinal(), 
				riskPointOrder.getId(), BusinessConf.hwsoft_ORDER_SERIALNUMBER_LENGTH);
		riskPointOrder.setOrderFormId(orderFormId);
		
		// 添加日志
		riskPointLogService.addRiskPointLog(orderFormId, null, null, notes);
		
		return riskPointOrder;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RiskPointOrder update(RiskPointOrder riskPointOrder) {
		return riskPointOrderDao.update(riskPointOrder);
	}

}
