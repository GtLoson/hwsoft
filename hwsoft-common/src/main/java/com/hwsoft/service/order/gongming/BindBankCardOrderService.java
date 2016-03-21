/**
 * 
 */
package com.hwsoft.service.order.gongming;

import java.util.Date;
import java.util.List;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.model.order.gongming.BindBankCardOrder;
import com.hwsoft.vo.order.BindBankCardOrderVo;

/**
 * @author tzh
 *
 */
public interface BindBankCardOrderService {

	public BindBankCardOrder addBankCardOrder(int userId,int userBankCardId,String memberId,String bankCode,String bankCardNumber,String bankPhone,String provice,String city,String brance);
	
	public BindBankCardOrder startBind(String orderFormId);
	
	public BindBankCardOrder updateBankCardOrderBySuccess(String orderFormId,String backCode,String backMsg,String response);
	
	public BindBankCardOrder updateBankCardOrderByFailed(String orderFormId,String backCode,String backMsg,String response);
	
	public List<BindBankCardOrderVo> listAll(OrderStatus orderStatus,Date startTime,Date endTime,int from, int pageSize,String mobile);

	public Long getTotalCount(OrderStatus orderStatus,Date startTime,Date endTime,String mobile);
}
