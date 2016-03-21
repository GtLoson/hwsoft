package com.hwsoft.service.log.product.impl;

import java.util.Date;

import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.log.product.LendingProductLogDao;
import com.hwsoft.model.log.product.LendingProductLog;
import com.hwsoft.service.log.product.LendingProductLogService;


/**
 * 
 * @author tzh
 *
 */
@Service("lendingProductLogService")
public class LendingProductLogServiceImpl implements LendingProductLogService{

	@Autowired
	private LendingProductLogDao lendingProductLogDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public LendingProductLog addLendingProductLog(int productId,
			String orderFormId, int staffId, String staffName, String notes) {
		
		LendingProductLog lendingProductLog = new LendingProductLog();
		lendingProductLog.setCreateTime(new Date());
		lendingProductLog.setNotes(notes);
		lendingProductLog.setOrderFormId(orderFormId);
		lendingProductLog.setProductId(productId);
		lendingProductLog.setStaffId(staffId);
		lendingProductLog.setStaffName(staffName);
		
		return lendingProductLogDao.save(lendingProductLog);
	}

}
