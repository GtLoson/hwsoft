/**
 * 
 */
package com.hwsoft.service.activity.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.acitivity.PrizeStatus;
import com.hwsoft.common.acitivity.WinnersRecordStatus;
import com.hwsoft.common.score.ScoreOperType;
import com.hwsoft.dao.activity.WinnersRecordDao;
import com.hwsoft.exception.activity.ActivityException;
import com.hwsoft.exception.activity.WinnersRecordException;
import com.hwsoft.model.activity.Prize;
import com.hwsoft.model.activity.WinnersRecord;
import com.hwsoft.service.activity.PrizeService;
import com.hwsoft.service.activity.WinnersRecordService;
import com.hwsoft.service.score.ScoreRecordService;
import com.hwsoft.spring.MessageSource;
import com.hwsoft.vo.activity.WinnersRecordVo;

/**
 * @author tzh
 *
 */
@Service("winnersRecordService")
public class WinnersRecordServiceImpl implements WinnersRecordService {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private WinnersRecordDao winnersRecordDao;
	
	@Autowired
	private PrizeService prizeService;
	
	@Autowired
	private ScoreRecordService scoreRecordService;

	/* (non-Javadoc)
	 * @see com.hwsoft.service.activity.WinnersRecordService#getTotalCount(java.lang.Integer)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public long getTotalCount(Integer customerId) {
		return winnersRecordDao.getTotalCount(customerId);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.activity.WinnersRecordService#listAll(java.lang.Integer, int, int)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<WinnersRecord> listAll(Integer customerId, int from,
			int pageSize) {
		return winnersRecordDao.listAll(customerId, from, pageSize);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.activity.WinnersRecordService#exchange(int, int)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public WinnersRecord exchange(int customerId, int prizeId) {
		
		Prize prize = prizeService.findById(prizeId);
		if(null == prize){
			throw new ActivityException(messageSource.getMessage("prize.is.not.fund"));
		}
		if(!prize.getStatus().equals(PrizeStatus.PROGREESING)){
			throw new ActivityException(messageSource.getMessage("prize.is.not.exchange"));
		}
		
		prize = prizeService.exchange(customerId, prizeId);
		Date now = new Date();
		WinnersRecord winnersRecord = new WinnersRecord();
		winnersRecord.setAcitivityId(prize.getActivityId());
		winnersRecord.setCustomerId(customerId);
		winnersRecord.setPrizeId(prizeId);
		winnersRecord.setPrizeName(prize.getPrizeName());
		winnersRecord.setPrizeTime(now);
		winnersRecord.setScore(prize.getNeedScore());
		winnersRecord.setStatus(WinnersRecordStatus.HAS_APPLIED);
		winnersRecord.setUpdateTime(now);
		//没有更新积分信息
		// scoreRecordService.addScoreRecord(customerId, prize.getNeedScore(), ScoreOperType.EXCHANGE);
		return winnersRecordDao.save(winnersRecord);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<WinnersRecordVo> listAllBySearch(Integer customerId, int from,
			int pageSize) {
		return winnersRecordDao.listAllBySearch(customerId, from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public long getTotalCountBySearch(Integer customerId) {
		return winnersRecordDao.getTotalCountBySearch(customerId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public WinnersRecord updateGiveUp(int winndersRecordId) {
		
		WinnersRecord winnersRecord = findById(winndersRecordId);
		if(null == winnersRecord){
			throw new WinnersRecordException("兑换记录未找到");
		}
		winnersRecord.setStatus(WinnersRecordStatus.HAS_GIVE);
		return winnersRecordDao.update(winnersRecord);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public WinnersRecord findById(int winndersRecordId) {
		return winnersRecordDao.findById(winndersRecordId);
	}

}
