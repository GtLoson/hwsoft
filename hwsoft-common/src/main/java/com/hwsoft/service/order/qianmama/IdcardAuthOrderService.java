package com.hwsoft.service.order.hwsoft;

import com.hwsoft.model.order.hwsoft.IdcardAuthOrder;

public interface IdcardAuthOrderService {
	
	public IdcardAuthOrder add(int customerId,String realName,String idcardNum);
	
	public IdcardAuthOrder findByOrderFormId(String orderFormId);
	
	public IdcardAuthOrder updateBySuccess(String orderFormId,String payOrderFormId,String backCode,String backMsg,String response);
	
	public IdcardAuthOrder updateByFailed(String orderFormId,String payOrderFormId,String backCode,String backMsg,String response);
	
	public IdcardAuthOrder updateByWaitting(String orderFormId,String payOrderFormId,String backCode,String backMsg,String response);

}
