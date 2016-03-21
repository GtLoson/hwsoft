package com.hwsoft.dao.log.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.log.product.RiskPointLog;

/**
 * 
 * @author tzh
 *
 */
@Repository("riskPointLogDao")
public class RiskPointLogDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return RiskPointLog.class;
	}
	
	public RiskPointLog save(RiskPointLog riskPointLog){
		return super.add(riskPointLog);
	}

}
