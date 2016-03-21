package com.hwsoft.service.log.product.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.product.ProductBidType;
import com.hwsoft.dao.log.product.BidProductLogDao;
import com.hwsoft.model.log.product.BidProductLog;
import com.hwsoft.service.log.product.BidProductLogService;

@Service("bidProductLogService")
public class BidProductLogServiceImpl implements BidProductLogService {

	@Autowired
	private BidProductLogDao bidProductLogDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public BidProductLog add(String orderFormId, ProductBidType productBidType,
			int productId, int productSubAccountId, String notes,
			Integer bidProductId, Integer bidProductSubAccountId,
			Integer shareNum) {
		
		BidProductLog bidProductLog = new BidProductLog();
		bidProductLog.setBidProductId(bidProductId);
		bidProductLog.setBidProductSubAccountId(bidProductSubAccountId);
		bidProductLog.setCreateTime(new Date());
		bidProductLog.setNotes(notes);
		bidProductLog.setOrderFormId(orderFormId);
		bidProductLog.setProductBidType(productBidType);
		bidProductLog.setProductId(productId);
		bidProductLog.setProductSubAccountId(productSubAccountId);
		bidProductLog.setShareNum(shareNum);
		return bidProductLogDao.save(bidProductLog);
	}

}
