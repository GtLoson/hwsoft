package com.hwsoft.service.order.hwsoft.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.conf.BusinessConf;
import com.hwsoft.common.customer.WithdrawalStatus;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.dao.order.hwsoft.WithdrawalOrderDao;
import com.hwsoft.exception.user.WithdrawalException;
import com.hwsoft.model.bank.UserBankCard;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.customer.WithdrawalRecord;
import com.hwsoft.model.order.hwsoft.WithdrawalOrder;
import com.hwsoft.service.bank.UserBankCardService;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.customer.WithdrawalRecordService;
import com.hwsoft.service.order.hwsoft.WithdrawalOrderService;
import com.hwsoft.util.order.OrderUtil;

/**
 * 
 * @author tzh
 * 
 */
@Service("withdrawalOrderService")
public class WithdrawalOrderServiceImpl implements WithdrawalOrderService {

	@Autowired
	private WithdrawalOrderDao withdrawalOrderDao;
	
	@Autowired
	private WithdrawalRecordService withdrawalRecordService;
	
	@Autowired
	private UserBankCardService userBankCardService;
	
	@Autowired
	private CustomerSubAccountService customerSubAccountService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public WithdrawalOrder add(int withdrawalRecordId) {
		WithdrawalRecord withdrawalRecord = withdrawalRecordService.findById(withdrawalRecordId);
		if(null == withdrawalRecord){
			throw new WithdrawalException("提现记录未找到");
		}
		
		if(withdrawalRecord.getStatus().equals(WithdrawalStatus.BANK_DEALING)
				|| withdrawalRecord.getStatus().equals(WithdrawalStatus.WITHDRAWAL_CANCEL)
				|| withdrawalRecord.getStatus().equals(WithdrawalStatus.WITHDRAWAL_FAILED)
				|| withdrawalRecord.getStatus().equals(WithdrawalStatus.WITHDRAWAL_SUCCESS)
				|| withdrawalRecord.getStatus().equals(WithdrawalStatus.WITHDRAWALING)){
			throw new WithdrawalException("该状态不能进行提现操作");
		}
		
		
		WithdrawalOrder withdrawalOrder2 = findByWithdrawalRecordId(withdrawalRecord.getId());
		
		if(null != withdrawalOrder2){
			throw new WithdrawalException("该提现记录已经生成过订单，不能再次生成");
		}
		
		UserBankCard userBankCard = userBankCardService.findUserBankCardById(withdrawalRecord.getUserBankCardId());
		//  生成订单
		WithdrawalOrder withdrawalOrder = new WithdrawalOrder();
		Date now = new Date();
		withdrawalOrder.setAmount(withdrawalRecord.getAmount());
		withdrawalOrder.setBankCode(userBankCard.getBankCode());
		withdrawalOrder.setCreateTime(now);
		withdrawalOrder.setCustomerId(withdrawalRecord.getCustomerId());
		withdrawalOrder.setCustomerSubAccountId(withdrawalRecord.getCustomerSubAccountId());
		withdrawalOrder.setFee(withdrawalRecord.getFee());//提现手续费1元
		withdrawalOrder.setOrderStatus(OrderStatus.WAITING_HANDLE);
		withdrawalOrder.setOrderType(withdrawalRecord.getType().getOrderType());
//		withdrawalOrder.setProductChannelId(withdrawalRecord.getpro);//渠道id
		withdrawalOrder.setRealFeeCostAccount(withdrawalRecord.getRealFeeCostAccount());//系统账户
		withdrawalOrder.setUserBankCardId(withdrawalRecord.getUserBankCardId());
		withdrawalOrder.setWithdrawalRecordId(withdrawalRecordId);
		withdrawalOrder.setBankCardNumber(withdrawalRecord.getBankCardNumber());
		
		CustomerSubAccount customerSubAccount = customerSubAccountService.findById(withdrawalRecord.getCustomerSubAccountId());
		
		withdrawalOrder.setProductChannelId(customerSubAccount.getProductChannelId());
		withdrawalOrderDao.save(withdrawalOrder);
		//订单编号
		String orderFormId = OrderUtil.getOrderFormId(withdrawalOrder.getOrderType().ordinal(), 
				withdrawalOrder.getId(), BusinessConf.hwsoft_ORDER_SERIALNUMBER_LENGTH);
		withdrawalOrder.setOrderFormId(orderFormId);

		return withdrawalOrder;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public WithdrawalOrder updateByRequestStart(String orderFormId) {

		WithdrawalOrder withdrawalOrder = findByOrderFormId(orderFormId);
		if(null == withdrawalOrder){
			throw new WithdrawalException("提现请求未找到");
		}
		withdrawalOrder.setOrderStatus(OrderStatus.IN_HANDLE);
		
		return withdrawalOrderDao.update(withdrawalOrder);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public WithdrawalOrder updateBySuccess(String orderFormId,String code,String msg,String response) {
		
		WithdrawalOrder withdrawalOrder = findByOrderFormId(orderFormId);
		if(null == withdrawalOrder){
			throw new WithdrawalException("提现请求未找到");
		}
		
		withdrawalOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
		withdrawalOrder.setBackCode(code);
		withdrawalOrder.setBackMsg(msg);
		withdrawalOrder.setResponse(response);
		
		withdrawalRecordService.updateBankDeal(withdrawalOrder.getWithdrawalRecordId(),withdrawalOrder.getOrderFormId(),null);
		
		//TODO 发短信提示用户等待银行处理
		return withdrawalOrderDao.update(withdrawalOrder);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public WithdrawalOrder updateByFailed(String orderFormId,String code,String msg,String response) {
		
		WithdrawalOrder withdrawalOrder = findByOrderFormId(orderFormId);
		if(null == withdrawalOrder){
			throw new WithdrawalException("提现请求未找到");
		}
		
		if(withdrawalOrder.getOrderStatus().equals(OrderStatus.HANDLE_SUCESS)){
			return withdrawalOrder;
		}
		
		withdrawalOrder.setOrderStatus(OrderStatus.HANDLE_FAILED);
		withdrawalOrder.setBackCode(code);
		withdrawalOrder.setBackMsg(msg);
		withdrawalOrder.setResponse(response);
		
		withdrawalRecordService.updateWithdrawalFailed(withdrawalOrder.getWithdrawalRecordId(), withdrawalOrder.getOrderFormId(), null);
		//TODO 发短信提示用户等待银行处理
		return withdrawalOrderDao.update(withdrawalOrder);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WithdrawalOrder findByOrderFormId(String orderFormId) {
		return withdrawalOrderDao.findByOrderFormId(orderFormId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public WithdrawalOrder updateByPayOrderFormId(String orderFormId,
			String payOrderFormId) {
		WithdrawalOrder withdrawalOrder = findByOrderFormId(orderFormId);
		if(null == withdrawalOrder){
			throw new WithdrawalException("提现请求未找到");
		}
		withdrawalOrder.setPayOrderFormId(payOrderFormId);
		return withdrawalOrderDao.update(withdrawalOrder);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WithdrawalOrder findByWithdrawalRecordId(int withdrawalRecordId) {
		return withdrawalOrderDao.findByWithdrawalRecordId(withdrawalRecordId);
	}

}
