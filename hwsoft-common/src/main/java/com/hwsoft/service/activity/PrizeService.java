/**
 * 
 */
package com.hwsoft.service.activity;

import java.util.List;

import com.hwsoft.common.acitivity.PrizeStatus;
import com.hwsoft.model.activity.Prize;

/**
 * @author tzh
 *
 */
public interface PrizeService {
	
	public List<Prize> listAllProgreesing(int from, int pageSize);

	public List<Prize> listAll(int from, int pageSize);

	public long getTotalCount();
	
	public long getTotalCountProgreesing();
	
	public Prize findById(int prizeId);
	
	public Prize exchange(int customerId,int prizeId);

	public Prize addPrize(String name,String desc,Integer needScore,String status);
}
