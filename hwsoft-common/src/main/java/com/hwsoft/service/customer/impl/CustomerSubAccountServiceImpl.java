/**
 * 
 */
package com.hwsoft.service.customer.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.customer.AccountStatus;
import com.hwsoft.dao.customer.CustomerSubAccountDao;
import com.hwsoft.exception.user.CustomerSubAccountExcption;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.customer.CustomerSubAccount;
import com.hwsoft.model.point.CustomerSubAccountPoint;
import com.hwsoft.model.product.ProductChannel;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.customer.CustomerSubAccountService;
import com.hwsoft.service.point.CustomerSubAccountPointService;
import com.hwsoft.service.product.ProductChannelService;
import com.hwsoft.spring.MessageSource;

/**
 * @author tzh
 *
 */
@Service("customerSubAccountService")
public class CustomerSubAccountServiceImpl implements CustomerSubAccountService {
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CustomerSubAccountDao customerSubAccountDao;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductChannelService productChannelService;
	
	@Autowired
	private CustomerSubAccountPointService customerSubAccountPointService;
	
	/* (non-Javadoc)
	 * @see com.hwsoft.service.user.CustomerSubAccountService#addCustomerSubAccount(int, java.lang.String, int)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerSubAccount addCustomerSubAccount(int customerId,
			String memberId, int productChannelId) {
		
		Customer customer = customerService.findById(customerId);
		if(null == customer){
			throw new CustomerSubAccountExcption(messageSource.getMessage("cutomer.not.fund"));
		}
		ProductChannel productChannel = productChannelService.findById(productChannelId);
		if(null == productChannel){
			throw new CustomerSubAccountExcption(messageSource.getMessage("productChannel.not.fund"));
		}
		Date date = new Date();
		CustomerSubAccount customerSubAccount = new CustomerSubAccount();
		customerSubAccount.setCustomerId(customerId);
		customerSubAccount.setMemberId(memberId);
		customerSubAccount.setProductChannelId(productChannelId);
		customerSubAccount.setRegTime(date);
		customerSubAccount.setStatus(AccountStatus.USING);
		customerSubAccount = customerSubAccountDao.save(customerSubAccount);
		// 同时需要添加渠道账户资金记录
		CustomerSubAccountPoint customerSubAccountPoint = new CustomerSubAccountPoint();
		customerSubAccountPoint.setAvailablePoints(0D);
		customerSubAccountPoint.setCustomerSubAccountId(customerSubAccount.getId());
		customerSubAccountPoint.setFrozenPoints(0D);
		customerSubAccountPoint.setUpdateTime(date);
		customerSubAccountPointService.addCustomerSubAccountPoint(customerSubAccountPoint);
		
		return customerSubAccount;
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.user.CustomerSubAccountService#findByCustomerSubAccountByUserIdAndProductChannelType(int, com.hwsoft.common.product.ProductChannelType)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public CustomerSubAccount findByCustomerSubAccountByUserIdAndProductChannelId(
			int customerId, int productChannelId) {
		return customerSubAccountDao.findByCustomerSubAccountByUserIdAndProductChannel(customerId, productChannelId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public CustomerSubAccount findById(int customerSubAccountId) {
		return customerSubAccountDao.findById(customerSubAccountId);
	}

}
