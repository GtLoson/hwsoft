package com.hwsoft.service.order.hwsoft.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.conf.BusinessConf;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.dao.order.hwsoft.RechargeOrderDao;
import com.hwsoft.exception.user.RechargeException;
import com.hwsoft.model.customer.RechargeRecord;
import com.hwsoft.model.order.hwsoft.RechargeOrder;
import com.hwsoft.service.customer.RechargeRecordService;
import com.hwsoft.service.order.hwsoft.RechargeOrderService;
import com.hwsoft.util.order.OrderUtil;


@Service("rechargeOrderService")
public class RechargeOrderServiceImpl implements RechargeOrderService {

	@Autowired
	private RechargeOrderDao rechargeOrderDao;
	
	@Autowired
	private RechargeRecordService rechargeRecordService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RechargeOrder add(int rechargeRecordId) {
		
		RechargeRecord rechargeRecord = rechargeRecordService.findById(rechargeRecordId);
		if(null == rechargeRecord){
			throw new RechargeException("未找到充值记录");
		}
		
		RechargeOrder rechargeOrder = new RechargeOrder();
		rechargeOrder.setAmount(rechargeRecord.getAmount());
		rechargeOrder.setBankCardNumber(rechargeRecord.getBankCardNumber());
		rechargeOrder.setBankCode(rechargeRecord.getBankCode());
		rechargeOrder.setCreateTime(new Date());
		rechargeOrder.setCustomerId(rechargeRecord.getCustomerId());
		rechargeOrder.setCustomerSubAccountId(rechargeRecord.getCustomerSubAccountId());
		rechargeOrder.setFee(rechargeRecord.getFee());
		rechargeOrder.setOrderStatus(OrderStatus.WAITING_HANDLE);
		rechargeOrder.setOrderType(OrderType.RECHARGE);
		rechargeOrder.setProductChannelId(rechargeRecord.getProductChannelId());
		rechargeOrder.setRealFeeCostAccount(rechargeRecord.getRealFeeCostAccount());
		rechargeOrder.setRechargeRecordId(rechargeRecordId);
		rechargeOrder.setUserBankCardId(rechargeRecord.getUserBankCardId());
		
		rechargeOrder = rechargeOrderDao.save(rechargeOrder);
		
		//订单编号
		String orderFormId = OrderUtil.getOrderFormId(rechargeOrder.getOrderType().ordinal(), 
				rechargeOrder.getId(), BusinessConf.hwsoft_ORDER_SERIALNUMBER_LENGTH);
		rechargeOrder.setOrderFormId(orderFormId);
		
		return rechargeOrder;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public RechargeOrder findByRechargeRecordId(int rechargeRecordId) {
		return rechargeOrderDao.findByRechargeRecordId(rechargeRecordId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RechargeOrder updateFailed(String orderFormId, String payOrderFormId, String code,
			String msg, String response) {
		
		RechargeOrder rechargeOrder = findByOrderFormId(orderFormId);
		if(null == rechargeOrder){
			throw new RechargeException("未找到充值订单");
		}
		
		RechargeRecord rechargeRecord = rechargeRecordService.findById(rechargeOrder.getRechargeRecordId());
		if(null == rechargeRecord){
			throw new RechargeException("未找到充值记录");
		}
		
		if(rechargeOrder.getOrderStatus().equals(OrderStatus.HANDLE_SUCESS)){
			return rechargeOrder;
		}
		
		rechargeOrder.setBackCode(code);
		rechargeOrder.setBackMsg(msg);
		rechargeOrder.setResponse(response);
		rechargeOrder.setFee(rechargeRecord.getFee());
		rechargeOrder.setRealFeeCostAccount(rechargeRecord.getRealFeeCostAccount());
		rechargeOrder.setSuccessAmount(rechargeRecord.getSuccessAmount());
		rechargeOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
		rechargeOrder.setPayOrderFormId(payOrderFormId);
		
		return rechargeOrderDao.update(rechargeOrder);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RechargeOrder updateSuccess(String orderFormId, String payOrderFormId, String code,
			String msg, String response) {
		

		RechargeOrder rechargeOrder = findByOrderFormId(orderFormId);
		if(null == rechargeOrder){
			throw new RechargeException("未找到充值订单");
		}
		RechargeRecord rechargeRecord = rechargeRecordService.findById(rechargeOrder.getRechargeRecordId());
		if(null == rechargeRecord){
			throw new RechargeException("未找到充值记录");
		}
		rechargeOrder.setBackCode(code);
		rechargeOrder.setBackMsg(msg);
		rechargeOrder.setResponse(response);
		rechargeOrder.setPayOrderFormId(payOrderFormId);
		rechargeOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
		
		rechargeOrder.setFee(rechargeRecord.getFee());
		rechargeOrder.setRealFeeCostAccount(rechargeRecord.getRealFeeCostAccount());
		rechargeOrder.setSuccessAmount(rechargeRecord.getSuccessAmount());
		
		return rechargeOrderDao.update(rechargeOrder);
		
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public RechargeOrder findByOrderFormId(String orderFormId) {
		return rechargeOrderDao.findByOrderFormId(orderFormId);
	}

}
