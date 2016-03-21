/**
 * 
 */
package com.hwsoft.service.score;

import com.hwsoft.model.score.CustomerScore;

/**
 * @author tzh
 *
 */
public interface CustomerScoreService {

	public CustomerScore addCustomerScore(int customerId);
	
	public CustomerScore plusScore(int customerId,int score);
	
	public CustomerScore subtractScore(int customerId,int score);
	
	public CustomerScore findByCustomerId(int customerId);
}
