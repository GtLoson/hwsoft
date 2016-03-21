/**
 * 
 */
package com.hwsoft.service.score;

import java.util.List;

import com.hwsoft.common.score.ScoreOperType;
import com.hwsoft.model.activity.WinnersRecord;
import com.hwsoft.model.score.ScoreRecord;

/**
 * @author tzh
 *
 */
public interface ScoreRecordService {

	public ScoreRecord addScoreRecord(int customerId,int score,ScoreOperType scoreOperType,String operator);
	
	
	/**
	 * 用户id可为空，为空即查询全部
	 * @param customerId
	 * @return
	 */
	public List<ScoreRecord> listAll(Integer customerId,int from, int pageSize);
	
	/**
	 * 用户id可为空，为空即查询全部
	 * @param customerId
	 * @return
	 */
	public long getTotalCount(Integer customerId);
	
}
