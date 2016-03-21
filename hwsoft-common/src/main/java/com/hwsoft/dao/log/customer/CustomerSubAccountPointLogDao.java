package com.hwsoft.dao.log.customer;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.log.CustomerSubAccountPointLog;
import com.hwsoft.model.point.CustomerSubAccountPoint;

@Repository("customerSubAccountPointLogDao")
public class CustomerSubAccountPointLogDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return CustomerSubAccountPointLog.class;
	}

	public CustomerSubAccountPointLog save(CustomerSubAccountPointLog customerSubAccountPointLog){
		return super.add(customerSubAccountPointLog);
	}
	
}
