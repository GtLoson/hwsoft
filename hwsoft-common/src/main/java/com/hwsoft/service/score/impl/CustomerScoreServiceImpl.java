/**
 * 
 */
package com.hwsoft.service.score.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.score.CustomerScoreDao;
import com.hwsoft.exception.score.ScoreException;
import com.hwsoft.model.score.CustomerScore;
import com.hwsoft.service.score.CustomerScoreService;
import com.hwsoft.spring.MessageSource;

/**
 * @author tzh
 *
 */
@Service("customerScoreService")
public class CustomerScoreServiceImpl implements CustomerScoreService {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CustomerScoreDao customerScoreDao;
	
	/* (non-Javadoc)
	 * @see com.hwsoft.service.score.CustomerScoreService#customerScore(int)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerScore addCustomerScore(int customerId) {
		
		CustomerScore customerScore = new CustomerScore();
		customerScore.setCustomerId(customerId);
		customerScore.setAvailableScore(0);
		customerScore.setTotalGainScore(0);
		customerScore.setTotalUsedSocre(0);
		
		return customerScoreDao.save(customerScore);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.score.CustomerScoreService#plusScore(int, int)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerScore plusScore(int customerId, int score) {
		CustomerScore customerScore = findByCustomerId(customerId);
		if(null == customerScore){
			customerScore = addCustomerScore(customerId);
		}
		customerScore.setAvailableScore(customerScore.getAvailableScore()+score);
		customerScore.setTotalGainScore(customerScore.getTotalGainScore()+score);
		return customerScoreDao.update(customerScore);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.score.CustomerScoreService#subtractScore(int, int)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public CustomerScore subtractScore(int customerId, int score) {
		CustomerScore customerScore = findByCustomerId(customerId);
		if(null == customerScore){
			throw new ScoreException(messageSource.getMessage("customer.socre.is.not.fund"));
		}
		if(customerScore.getAvailableScore() < score){
			throw new ScoreException(messageSource.getMessage("customer.socre.is.not.enough"));
		}
		customerScore.setAvailableScore(customerScore.getAvailableScore()-score);
		customerScore.setTotalUsedSocre(customerScore.getTotalUsedSocre()+score);
		return customerScoreDao.update(customerScore);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.score.CustomerScoreService#findByCustomerId(int)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public CustomerScore findByCustomerId(int customerId) {
		return customerScoreDao.findByCustomerId(customerId);
	}

}
