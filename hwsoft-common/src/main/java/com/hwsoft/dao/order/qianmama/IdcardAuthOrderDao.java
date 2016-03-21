package com.hwsoft.dao.order.hwsoft;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.order.hwsoft.IdcardAuthOrder;

/**
 * 
 * @author tzh
 *
 */
@Repository("idcardAuthOrderDao")
public class IdcardAuthOrderDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return IdcardAuthOrder.class;
	}
	
	public IdcardAuthOrder save(IdcardAuthOrder idcardAuthOrder){
		return super.add(idcardAuthOrder);
		
	}
	
	public IdcardAuthOrder update(IdcardAuthOrder idcardAuthOrder){
		return super.update(idcardAuthOrder);
	}
	
	public IdcardAuthOrder findById(int id){
		return super.get(id);
	}
	
	public IdcardAuthOrder findByOrderFormId(String orderFormId){
		return super.getByCollumn("orderFormId", orderFormId);
	}

	
}
