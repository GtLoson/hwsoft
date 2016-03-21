/**
 * 
 */
package com.hwsoft.service.order.gongming.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.conf.BusinessConf;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.dao.order.gongming.BindBankCardDao;
import com.hwsoft.exception.bank.UserBankCardException;
import com.hwsoft.model.order.gongming.BindBankCardOrder;
import com.hwsoft.service.bank.UserBankCardService;
import com.hwsoft.service.order.gongming.BindBankCardOrderService;
import com.hwsoft.util.order.OrderUtil;
import com.hwsoft.vo.order.BindBankCardOrderVo;

/**
 * @author tzh
 *
 */
@Service("bindBankCardOrderService")
public class BindBankCardOrderServiceImpl implements BindBankCardOrderService {
	
	@Autowired
	private BindBankCardDao bindBankCardDao;
	
	@Autowired
	private UserBankCardService userBankCardService;

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.BindBankCardOrderService#addBankCardOrder(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public BindBankCardOrder addBankCardOrder(int userId, int userBankCardId, String memberId,
			String bankCode, String bankCardNumber, String bankPhone,
			String provice, String city, String brance) {
		
		BindBankCardOrder bankCardOrder = new BindBankCardOrder();
		bankCardOrder.setUserId(userId);
		bankCardOrder.setCity(city);
		bankCardOrder.setBankBranchName(brance);
		bankCardOrder.setCreateTime(new Date());
		bankCardOrder.setBankCode(bankCode);
		bankCardOrder.setBankCardNumber(bankCardNumber);
		bankCardOrder.setMemberId(memberId);
		bankCardOrder.setOrderStatus(OrderStatus.WAITING_HANDLE);
		bankCardOrder.setOrderType(OrderType.BIND_BANK_CARD);
		bankCardOrder.setProvince(provice);
		bankCardOrder.setUserBankCardId(userBankCardId);
		bankCardOrder.setBankPhoneNumber(bankPhone);
		bindBankCardDao.save(bankCardOrder);
		bankCardOrder.setOrderFormId(OrderUtil.getOrderFormId(OrderType.BIND_BANK_CARD.ordinal(), 
				bankCardOrder.getId(), BusinessConf.GONGMING_ORDER_SERIALNUMBER_LENGTH));
		
		return bankCardOrder;
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.BindBankCardOrderService#updateBankCardOrderByFailed(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public BindBankCardOrder updateBankCardOrderByFailed(String orderFormId,String backCode,String backMsg,String response) {
		BindBankCardOrder bankCardOrder = bindBankCardDao.findByOrderFormId(orderFormId);
		if(null == bankCardOrder){
			throw new UserBankCardException("绑卡订单未找到");
		}
		bankCardOrder.setOrderStatus(OrderStatus.HANDLE_FAILED);
		bankCardOrder.setBackCode(backCode);
		bankCardOrder.setBackMsg(backMsg);
		bankCardOrder.setResponse(response);
		userBankCardService.bindBankCardFailed(bankCardOrder.getUserBankCardId());
		return bankCardOrder;
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.BindBankCardOrderService#updateBankCardOrderBySuccess(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public BindBankCardOrder updateBankCardOrderBySuccess(String orderFormId,String backCode,String backMsg,String response) {
		BindBankCardOrder bankCardOrder = bindBankCardDao.findByOrderFormId(orderFormId);
		if(null == bankCardOrder){
			throw new UserBankCardException("绑卡订单未找到");
		}
		bankCardOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
		bankCardOrder.setBackCode(backCode);
		bankCardOrder.setBackMsg(backMsg);
		bankCardOrder.setResponse(response);
		userBankCardService.bindBankCardSuccess(bankCardOrder.getUserBankCardId(),null);
		return bankCardOrder;
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.BindBankCardOrderService#startBind(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public BindBankCardOrder startBind(String orderFormId) {
		BindBankCardOrder bankCardOrder = bindBankCardDao.findByOrderFormId(orderFormId);
		if(null == bankCardOrder){
			throw new UserBankCardException("绑卡订单未找到");
		}
		bankCardOrder.setOrderStatus(OrderStatus.IN_HANDLE);
		return bindBankCardDao.update(bankCardOrder);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<BindBankCardOrderVo> listAll(OrderStatus orderStatus,
			Date startTime, Date endTime, int from, int pageSize,String mobile) {
		return bindBankCardDao.list(orderStatus, startTime, endTime, from, pageSize,mobile);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Long getTotalCount(OrderStatus orderStatus, Date startTime,
			Date endTime,String mobile) {
		return bindBankCardDao.getTotalCount(orderStatus, startTime, endTime,mobile);
	}


}
