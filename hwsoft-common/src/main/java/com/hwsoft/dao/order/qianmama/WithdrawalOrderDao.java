package com.hwsoft.dao.order.hwsoft;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.order.hwsoft.WithdrawalOrder;

/**
 * 
 * @author tzh
 *
 */
@Repository("withdrawalOrderDao")
public class WithdrawalOrderDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return WithdrawalOrder.class;
	}
	
	public WithdrawalOrder save(WithdrawalOrder withdrawalOrder){
		return super.add(withdrawalOrder);
		
	}
	
	public WithdrawalOrder update(WithdrawalOrder withdrawalOrder){
		return super.update(withdrawalOrder);
	}
	
	public WithdrawalOrder findById(int id){
		return super.get(id);
	}
	
	public WithdrawalOrder findByOrderFormId(String orderFormId){
		return super.getByCollumn("orderFormId", orderFormId);
	}
	
	public WithdrawalOrder findByWithdrawalRecordId(int withdrawalRecordId) {
		return super.getByCollumn("withdrawalRecordId", ""+withdrawalRecordId);
	}

}
