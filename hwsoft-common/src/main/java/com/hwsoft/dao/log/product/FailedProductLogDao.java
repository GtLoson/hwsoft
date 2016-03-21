package com.hwsoft.dao.log.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.log.product.FailedProductLog;

@Repository("failedProductLogDao")
public class FailedProductLogDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return FailedProductLog.class;
	}

	public FailedProductLog save(FailedProductLog failedProductLog){
		return super.add(failedProductLog);
	}
	
}
