package com.hwsoft.dao.log.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.log.product.RepaymentProductLog;


/**
 * 
 * @author tzh
 *
 */
@Repository("repaymentProductLogDao")
public class RepaymentProductLogDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return RepaymentProductLog.class;
	}

	
	public RepaymentProductLog save(RepaymentProductLog repaymentProductLog){
		return super.add(repaymentProductLog);
	}
	
}
