package com.hwsoft.service.log.product.impl;

import java.util.Date;

import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.log.product.FailedProductLogDao;
import com.hwsoft.model.log.product.FailedProductLog;
import com.hwsoft.service.log.product.FailedProductLogService;

/**
 * 
 * @author tzh
 *
 */
@Service("failedProductLogService")
public class FailedProductLogServiceImpl implements FailedProductLogService {
	
	@Autowired
	private FailedProductLogDao failedProductLogDao;

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public FailedProductLog addFailedProductLog(int productId,
			String orderFormId, int staffId, String staffName, String notes) {
		
		FailedProductLog failedProductLog = new FailedProductLog();
		failedProductLog.setCreateTime(new Date());
		failedProductLog.setNotes(notes);
		failedProductLog.setOrderFormId(orderFormId);
		failedProductLog.setProductId(productId);
		failedProductLog.setStaffId(staffId);
		failedProductLog.setStaffName(staffName);
		return failedProductLogDao.save(failedProductLog);
	}

}
