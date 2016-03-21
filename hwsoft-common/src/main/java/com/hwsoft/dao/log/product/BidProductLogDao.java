package com.hwsoft.dao.log.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.log.product.BidProductLog;

/**
 * 投标订单日志
 * @author tzh
 *
 */
@Repository("bidProductLogDao")
public class BidProductLogDao extends BaseDao{

	@Override
	protected Class<?> entityClass() {
		return BidProductLog.class;
	}
	
	public BidProductLog save(BidProductLog bidProductLog){
		return super.add(bidProductLog);
	}
	
}
