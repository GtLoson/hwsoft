package com.hwsoft.service.order.hwsoft.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.conf.BusinessConf;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.dao.order.hwsoft.IdcardAuthOrderDao;
import com.hwsoft.exception.user.IdcardAuthException;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.order.hwsoft.IdcardAuthOrder;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.order.hwsoft.IdcardAuthOrderService;
import com.hwsoft.util.idcard.IDVerifierUtil;
import com.hwsoft.util.order.OrderUtil;

/**
 * 
 * @author tzh
 *
 */
@Service("idcardAuthOrderService")
public class IdcardAuthOrderServiceImpl implements IdcardAuthOrderService {
	
	@Autowired
	private IdcardAuthOrderDao idcardAuthOrderDao;

	@Autowired
	private CustomerService customerService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public IdcardAuthOrder add(int customerId, String realName, String idcardNum) {
		
		if(!IDVerifierUtil.isIdCard(idcardNum)){
			throw new IdcardAuthException("身份证号码不正确");
		}
		
		Customer customer = customerService.findById(customerId);
		if(null == customer){
			throw new IdcardAuthException("用户未找到");
		}
		
		if(customer.isIdCardAuth()){
			throw new IdcardAuthException("该用户已经进行过实名认证");
		}
		
		Customer customer2 = customerService.findByIdCard(idcardNum);
	    if (null != customer2 && customer2.isIdCardAuth()) {
	        throw new IdcardAuthException("该身份证已经被绑定");
	    }
		
		IdcardAuthOrder idcardAuthOrder = new IdcardAuthOrder();
		idcardAuthOrder.setCreateTime(new Date());
		idcardAuthOrder.setIdcardNum(idcardNum);
		idcardAuthOrder.setCustomerId(customerId);
		idcardAuthOrder.setOrderStatus(OrderStatus.WAITING_HANDLE);
		idcardAuthOrder.setOrderType(OrderType.IDCARD_AUTH);
		idcardAuthOrder.setRealName(realName);
		
		idcardAuthOrder = idcardAuthOrderDao.save(idcardAuthOrder);
		//订单编号
		String orderFormId = OrderUtil.getOrderFormId(idcardAuthOrder.getOrderType().ordinal(), 
				idcardAuthOrder.getId(), BusinessConf.hwsoft_ORDER_SERIALNUMBER_LENGTH);
		idcardAuthOrder.setOrderFormId(orderFormId);
	     
		customerService.identityVerification(customerId, idcardNum, realName, false);
		
		return idcardAuthOrder;
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public IdcardAuthOrder findByOrderFormId(String orderFormId) {
		return idcardAuthOrderDao.findByOrderFormId(orderFormId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public IdcardAuthOrder updateBySuccess(String orderFormId, String payOrderFormId,String backCode,
			String backMsg, String response) {
		
		IdcardAuthOrder idcardAuthOrder = findByOrderFormId(orderFormId);
		if(null == idcardAuthOrder){
			throw new IdcardAuthException("实名认证订单未找到");
		}
		
		idcardAuthOrder.setBackCode(backCode);
		idcardAuthOrder.setBackMsg(backMsg);
		idcardAuthOrder.setResponse(response);
		idcardAuthOrder.setOrderStatus(OrderStatus.HANDLE_SUCESS);
		idcardAuthOrder.setPayOrderFormId(payOrderFormId);
		
		customerService.identityVerification(idcardAuthOrder.getCustomerId(), idcardAuthOrder.getIdcardNum(), 
				idcardAuthOrder.getRealName(), true);
		
		return idcardAuthOrderDao.update(idcardAuthOrder);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public IdcardAuthOrder updateByFailed(String orderFormId, String payOrderFormId,String backCode,
			String backMsg, String response) {
		IdcardAuthOrder idcardAuthOrder = findByOrderFormId(orderFormId);
		if(null == idcardAuthOrder){
			throw new IdcardAuthException("实名认证订单未找到");
		}
		
		if(idcardAuthOrder.getOrderStatus().equals(OrderStatus.HANDLE_SUCESS)){
			return idcardAuthOrder;
		}
		
		idcardAuthOrder.setBackCode(backCode);
		idcardAuthOrder.setBackMsg(backMsg);
		idcardAuthOrder.setResponse(response);
		idcardAuthOrder.setOrderStatus(OrderStatus.HANDLE_FAILED);
		idcardAuthOrder.setPayOrderFormId(payOrderFormId);
		
		return idcardAuthOrderDao.update(idcardAuthOrder);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public IdcardAuthOrder updateByWaitting(String orderFormId,
			String payOrderFormId, String backCode, String backMsg,
			String response) {
		
		
		IdcardAuthOrder idcardAuthOrder = findByOrderFormId(orderFormId);
		if(null == idcardAuthOrder){
			throw new IdcardAuthException("实名认证订单未找到");
		}
		
		if(idcardAuthOrder.getOrderStatus().equals(OrderStatus.HANDLE_SUCESS)){
			return idcardAuthOrder;
		}
		
		idcardAuthOrder.setBackCode(backCode);
		idcardAuthOrder.setBackMsg(backMsg);
		idcardAuthOrder.setResponse(response);
		idcardAuthOrder.setOrderStatus(OrderStatus.IN_HANDLE);
		idcardAuthOrder.setPayOrderFormId(payOrderFormId);
		
		return idcardAuthOrderDao.update(idcardAuthOrder);
	}

}
