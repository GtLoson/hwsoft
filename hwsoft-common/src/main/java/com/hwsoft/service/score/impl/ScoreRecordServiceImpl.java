/**
 * 
 */
package com.hwsoft.service.score.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.score.ScoreOperType;
import com.hwsoft.dao.score.ScoreRecordDao;
import com.hwsoft.exception.score.ScoreException;
import com.hwsoft.model.activity.WinnersRecord;
import com.hwsoft.model.score.ScoreRecord;
import com.hwsoft.service.score.CustomerScoreService;
import com.hwsoft.service.score.ScoreRecordService;
import com.hwsoft.spring.MessageSource;

/**
 * @author tzh
 *
 */
@Service("scoreRecordService")
public class ScoreRecordServiceImpl implements ScoreRecordService {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CustomerScoreService customerScoreService;
	
	@Autowired
	private ScoreRecordDao scoreRecordDao;
	

	/* (non-Javadoc)
	 * @see com.hwsoft.service.score.ScoreRecordService#addScoreRecord(int, int, com.hwsoft.common.score.ScoreOperType)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ScoreRecord addScoreRecord(int customerId, int score,
			ScoreOperType scoreOperType,String operator) {
		if(null == scoreOperType){
			throw new ScoreException(messageSource.getMessage("score.oper.type.not.null"));
		}
		ScoreRecord scoreRecord = new ScoreRecord();
		scoreRecord.setCustomerId(customerId);
		scoreRecord.setScore(score);
		scoreRecord.setScoreOperType(scoreOperType);
		scoreRecord.setPlus(scoreOperType.isPlus());
		scoreRecord.setCreateTime(new Date());
		scoreRecord.setOperator(operator);
		if(scoreOperType.isPlus()){
			customerScoreService.plusScore(customerId, score);
		} else {
			customerScoreService.subtractScore(customerId, score);
		}
		
		return scoreRecordDao.save(scoreRecord);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<ScoreRecord> listAll(Integer customerId, int from,
			int pageSize) {
		return scoreRecordDao.listAll(customerId, from, pageSize);
	}


	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public long getTotalCount(Integer customerId) {
		return scoreRecordDao.getTotalCount(customerId);
	}

}
