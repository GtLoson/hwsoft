package com.hwsoft.dao.log.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.log.product.LendingProductLog;


/**
 * 
 * @author tzh
 *
 */
@Repository("lendingProductLogDao")
public class LendingProductLogDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return LendingProductLog.class;
	}

	public LendingProductLog save(LendingProductLog lendingProductLog){
		return super.add(lendingProductLog);
	}
	
}
