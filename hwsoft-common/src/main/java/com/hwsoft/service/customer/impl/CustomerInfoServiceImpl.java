/**
 * 
 */
package com.hwsoft.service.customer.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.customer.CustomerInfoDao;
import com.hwsoft.model.customer.CustomerInfo;
import com.hwsoft.service.customer.CustomerInfoService;

/**
 * @author tzh
 *
 */
@Service("customerService")
public class CustomerInfoServiceImpl implements CustomerInfoService {
	
	@Autowired
	private CustomerInfoDao customerInfoDao;

	/* (non-Javadoc)
	 * @see com.hwsoft.service.customer.CustomerInfoService#findByUserId(int)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public CustomerInfo findByUserId(int userId) {
		return customerInfoDao.findByUserId(userId);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.customer.CustomerInfoService#save(com.hwsoft.model.customer.CustomerInfo)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerInfo add(CustomerInfo customerInfo) {
		return customerInfoDao.save(customerInfo);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.customer.CustomerInfoService#update(com.hwsoft.model.customer.CustomerInfo)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerInfo update(CustomerInfo customerInfo) {
		return customerInfoDao.update(customerInfo);
	}


}
