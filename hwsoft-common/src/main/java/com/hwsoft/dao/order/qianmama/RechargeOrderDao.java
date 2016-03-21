package com.hwsoft.dao.order.hwsoft;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.order.hwsoft.RechargeOrder;

@Repository("rechargeOrderDao")
public class RechargeOrderDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return RechargeOrder.class;
	}
	
	public RechargeOrder save(RechargeOrder rechargeOrder){
		return super.add(rechargeOrder);
		
	}
	
	public RechargeOrder update(RechargeOrder rechargeOrder){
		return super.update(rechargeOrder);
	}
	
	public RechargeOrder findById(int id){
		return super.get(id);
	}
	
	public RechargeOrder findByOrderFormId(String orderFormId){
		return super.getByCollumn("orderFormId", orderFormId);
	}
	
	public RechargeOrder findByRechargeRecordId(int rechargeRecordId) {
		return super.getByCollumn("rechargeRecordId", ""+rechargeRecordId);
	}

}
