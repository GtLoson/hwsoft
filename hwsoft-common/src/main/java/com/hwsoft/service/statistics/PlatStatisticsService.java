package com.hwsoft.service.statistics;

import java.util.Date;
import java.util.List;

import com.hwsoft.common.statistices.StatisticesDateType;
import com.hwsoft.vo.statistices.RegisterStaistices;

public interface PlatStatisticsService{
	
	/**
	 * 注册统计
	 * @param startDate
	 * @param endDate
	 * @param channelId
	 * @param statisticesDateType
	 * @return
	 */
	public List<RegisterStaistices> findRegisterStatistices(Date startDate,Date endDate,Integer channelId,StatisticesDateType statisticesDateType,boolean channelEd);

}
