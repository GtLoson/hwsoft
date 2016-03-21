/**
 * 
 */
package com.hwsoft.service.order.gongming.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.conf.BusinessConf;
import com.hwsoft.common.customer.Gender;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.dao.order.gongming.UserAccountOrderDao;
import com.hwsoft.exception.gongming.UserAccountOrderException;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.order.gongming.UserAccountOrder;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.order.gongming.UserAccountOrderService;
import com.hwsoft.util.order.OrderUtil;
import com.hwsoft.vo.order.AccountOrderVo;

/**
 * @author tzh
 *
 */
@Repository("userAccountOrderService")
public class UserAccountOrderServiceImpl implements UserAccountOrderService {
	
	@Autowired
	private UserAccountOrderDao userAccountOrderDao;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerSubAccountService customerSubAccountService;
	
	

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.UserAccountOrderService#add(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.hwsoft.common.customer.Gender, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public UserAccountOrder addByCreate(int customerId, String mobile,
			String idCard, String realName, String email, String nickName,
			Gender gender, String address, String educationLevel,int productChannelId) {
		
		Customer customer = customerService.findById(customerId);
		if(null == customer){
			throw new UserAccountOrderException("用户未找到");
		}
		UserAccountOrder userAccountOrder = new UserAccountOrder();
		userAccountOrder.setAddress(address);
		userAccountOrder.setCreateTime(new Date());
		userAccountOrder.setEducationLevel(educationLevel);
		userAccountOrder.setEmail(email);
		userAccountOrder.setGender(gender);
		userAccountOrder.setIdCard(idCard);
		userAccountOrder.setMobile(mobile);
		userAccountOrder.setNickName(nickName);
		userAccountOrder.setOrderStatus(OrderStatus.WAITING_HANDLE);
		userAccountOrder.setOrderType(OrderType.CREATE_USER_ACCOUNT);
		userAccountOrder.setRealName(realName);
		userAccountOrder.setUserId(customerId);
		userAccountOrder.setProductChannelId(productChannelId);
		userAccountOrderDao.save(userAccountOrder);
		userAccountOrder.setOrderFormId(OrderUtil.getOrderFormId(OrderType.CREATE_USER_ACCOUNT.ordinal(), userAccountOrder.getId(), BusinessConf.GONGMING_ORDER_SERIALNUMBER_LENGTH));
		
		return userAccountOrder;
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.UserAccountOrderService#addByUpdate(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.hwsoft.common.customer.Gender, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public UserAccountOrder addByUpdate(int customerId, String mobile,String memberId,
			String idCard, String realName, String email, String nickName,
			Gender gender, String address, String educationLevel,int customerSubAccountId) {
		Customer customer = customerService.findById(customerId);
		if(null == customer){
			throw new UserAccountOrderException("用户未找到");
		}
		UserAccountOrder userAccountOrder = new UserAccountOrder();
		userAccountOrder.setAddress(address);
		userAccountOrder.setCreateTime(new Date());
		userAccountOrder.setEducationLevel(educationLevel);
		userAccountOrder.setEmail(email);
		userAccountOrder.setGender(gender);
		userAccountOrder.setIdCard(idCard);
		userAccountOrder.setMobile(mobile);
		userAccountOrder.setNickName(nickName);
		userAccountOrder.setOrderStatus(OrderStatus.WAITING_HANDLE);
		userAccountOrder.setOrderType(OrderType.UPDATE_USER_ACCOUNT);
		userAccountOrder.setRealName(realName);
		userAccountOrder.setUserId(customerId);
		userAccountOrder.setCustomerSubAccountId(customerSubAccountId);
		userAccountOrder.setMemberId(memberId);
		userAccountOrderDao.save(userAccountOrder);
		userAccountOrder.setOrderFormId(OrderUtil.getOrderFormId(OrderType.CREATE_USER_ACCOUNT.ordinal(), userAccountOrder.getId(), BusinessConf.GONGMING_ORDER_SERIALNUMBER_LENGTH));
		
		return userAccountOrder;
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.UserAccountOrderService#start(java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public UserAccountOrder start(String orderFormId) {
		
		UserAccountOrder userAccountOrder = userAccountOrderDao.findOrderFormId(orderFormId);
		if(null == userAccountOrder){
			throw new UserAccountOrderException("订单未找到");
		}
		userAccountOrder.setOrderStatus(OrderStatus.IN_HANDLE);
		return userAccountOrderDao.update(userAccountOrder);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.UserAccountOrderService#updateByCreateSuccess(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public UserAccountOrder updateByCreateSuccess(String orderFormId,
			String code, String backMsg, String response, String memberId) {
		
		UserAccountOrder userAccountOrder = userAccountOrderDao.findOrderFormId(orderFormId);
		if(null == userAccountOrder){
			throw new UserAccountOrderException("订单未找到");
		}
		userAccountOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
		userAccountOrder.setBackCode(code);
		userAccountOrder.setBackMsg(backMsg);
		userAccountOrder.setResponse(response);
		userAccountOrder.setMemberId(memberId);
		customerSubAccountService.addCustomerSubAccount(userAccountOrder.getUserId(), 
				memberId, userAccountOrder.getProductChannelId());
		
		return userAccountOrderDao.update(userAccountOrder);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.order.gongming.UserAccountOrderService#updateByCreateSuccess(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public UserAccountOrder updateByCreateFailed(String orderFormId,
			String code, String backMsg, String response) {
		UserAccountOrder userAccountOrder = userAccountOrderDao.findOrderFormId(orderFormId);
		if(null == userAccountOrder){
			throw new UserAccountOrderException("订单未找到");
		}
		userAccountOrder.setOrderStatus(OrderStatus.HANDLE_FAILED);
		userAccountOrder.setBackCode(code);
		userAccountOrder.setBackMsg(backMsg);
		userAccountOrder.setResponse(response);
		
		return userAccountOrderDao.update(userAccountOrder);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<AccountOrderVo> listAll(OrderStatus orderStatus,
			Date startTime, Date endTime, int from, int pageSize) {
		return userAccountOrderDao.list(orderStatus, startTime, endTime, from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Long getTotalCount(OrderStatus orderStatus, Date startTime,
			Date endTime) {
		return userAccountOrderDao.getTotalCount(orderStatus, startTime, endTime);
	}

}
