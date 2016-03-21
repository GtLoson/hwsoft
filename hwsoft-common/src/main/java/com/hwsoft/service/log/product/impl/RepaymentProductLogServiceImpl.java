package com.hwsoft.service.log.product.impl;

import java.util.Date;

import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.log.product.RepaymentProductLogDao;
import com.hwsoft.model.log.product.RepaymentProductLog;
import com.hwsoft.service.log.product.RepaymentProductLogService;

/**
 * 
 * @author tzh
 *
 */
@Service("repaymentProductLogService")
public class RepaymentProductLogServiceImpl implements
		RepaymentProductLogService {
	
	@Autowired
	private RepaymentProductLogDao repaymentProductLogDao;

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public RepaymentProductLog addRepaymentProductLog(int productId,
			String orderFormId, int staffId, String staffName, String notes) {
		
		RepaymentProductLog repaymentProductLog = new RepaymentProductLog();
		repaymentProductLog.setCreateTime(new Date());
		repaymentProductLog.setNotes(notes);
		repaymentProductLog.setOrderFormId(orderFormId);
		repaymentProductLog.setProductId(productId);
		repaymentProductLog.setStaffId(staffId);
		repaymentProductLog.setStaffName(staffName);
		
		return repaymentProductLogDao.save(repaymentProductLog);
	}

}
