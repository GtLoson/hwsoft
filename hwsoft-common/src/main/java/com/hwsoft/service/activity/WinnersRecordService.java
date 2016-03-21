/**
 * 
 */
package com.hwsoft.service.activity;

import java.util.List;

import com.hwsoft.model.activity.WinnersRecord;
import com.hwsoft.vo.activity.WinnersRecordVo;

/**
 * @author tzh
 *
 */
public interface WinnersRecordService {
	
	/**
	 * 用户id可为空，为空即查询全部
	 * @param customerId
	 * @return
	 */
	public List<WinnersRecord> listAll(Integer customerId,int from, int pageSize);
	
	/**
	 * 用户id可为空，为空即查询全部
	 * @param customerId
	 * @return
	 */
	public long getTotalCount(Integer customerId);
	
	public WinnersRecord exchange(int customerId,int prizeId);
	
	/**
	 * 用户id可为空，为空即查询全部
	 * @param customerId
	 * @return
	 */
	public List<WinnersRecordVo> listAllBySearch(Integer customerId,int from, int pageSize);
	
	/**
	 * 用户id可为空，为空即查询全部
	 * @param customerId
	 * @return
	 */
	public long getTotalCountBySearch(Integer customerId);
	
	public WinnersRecord updateGiveUp(int winndersRecordId);
	
	public WinnersRecord findById(int winndersRecordId);
}
