package com.hwsoft.service.statistics.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.statistices.StatisticesDateType;
import com.hwsoft.dao.statistcs.PlatStatisticsDao;
import com.hwsoft.service.statistics.PlatStatisticsService;
import com.hwsoft.vo.statistices.RegisterStaistices;


/**
 * 
 * @author tzh
 *
 */
@Service("platStatisticsService")
public class PlatStatisticsServiceImpl implements PlatStatisticsService {
	
	@Autowired
	private PlatStatisticsDao platStatisticsDao;

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<RegisterStaistices> findRegisterStatistices(Date startDate,
			Date endDate, Integer channelId,
			StatisticesDateType statisticesDateType,boolean channelEd) {
		return platStatisticsDao.findRegisterStatistices(startDate, endDate, channelId, statisticesDateType,channelEd);
	}

}
