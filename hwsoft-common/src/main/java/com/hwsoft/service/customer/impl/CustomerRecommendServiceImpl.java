/**
 * 
 */
package com.hwsoft.service.customer.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.customer.CustomerRecommendDao;
import com.hwsoft.model.customer.CustomerRecommend;
import com.hwsoft.service.customer.CustomerRecommendService;

/**
 * @author tzh
 *
 */
@Service("customerRecommendService")
public class CustomerRecommendServiceImpl implements CustomerRecommendService {
	
	@Autowired
	private CustomerRecommendDao customerRecommendDao;

	/* (non-Javadoc)
	 * @see com.hwsoft.service.customer.CustomerRecommendService#add(int, int)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerRecommend add(int recommendCustomerId,
			int recommendedCustomerId) {
		
		//TODO 推荐注册奖励积分没有处理
		
		CustomerRecommend customerRecommend = new CustomerRecommend();
		customerRecommend.setRecommendCustomerId(recommendCustomerId);
		customerRecommend.setRecommendedCustomerId(recommendedCustomerId);
		customerRecommend.setRecommendTime(new Date());
		return customerRecommendDao.save(customerRecommend);
	}

}
