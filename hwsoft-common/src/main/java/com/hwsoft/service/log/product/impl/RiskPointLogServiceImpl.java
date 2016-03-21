package com.hwsoft.service.log.product.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.log.product.RiskPointLogDao;
import com.hwsoft.model.log.product.RiskPointLog;
import com.hwsoft.service.log.product.RiskPointLogService;

/**
 * 
 * @author tzh
 *
 */
@Service("riskPointLogService")
public class RiskPointLogServiceImpl implements RiskPointLogService {

	@Autowired
	private RiskPointLogDao riskPointLogDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RiskPointLog addRiskPointLog(String orderFormId, Integer staffId,
			String staffName, String notes) {
		
		RiskPointLog riskPointLog = new RiskPointLog();
		riskPointLog.setCreateTime(new Date());
		riskPointLog.setNotes(notes);
		riskPointLog.setOrderFormId(orderFormId);
		riskPointLog.setStaffId(staffId);
		riskPointLog.setStaffName(staffName);
		
		return riskPointLogDao.save(riskPointLog);
	}

}
