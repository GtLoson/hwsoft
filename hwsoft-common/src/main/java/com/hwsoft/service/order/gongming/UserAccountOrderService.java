/**
 * 
 */
package com.hwsoft.service.order.gongming;

import java.util.Date;
import java.util.List;

import com.hwsoft.common.customer.Gender;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.model.order.gongming.UserAccountOrder;
import com.hwsoft.vo.order.AccountOrderVo;

/**
 * @author tzh
 *
 */
public interface UserAccountOrderService {
	
	public UserAccountOrder addByCreate(int customerId,
			String mobile,String idCard,String realName,
			String email,String nickName,Gender gender,
			String address,String educationLevel,int productChannelId);
	
	public UserAccountOrder addByUpdate(int customerId,String memberId,
			String mobile,String idCard,String realName,
			String email,String nickName,Gender gender,
			String address,String educationLevel,int customerSubAccountId);
	
	public UserAccountOrder start(String orderFormId);
	
	
	public UserAccountOrder updateByCreateSuccess(
			String orderFormId,String code,
			String backMsg,String response,
			String memberId);
	
	public UserAccountOrder updateByCreateFailed(
			String orderFormId,String code,
			String backMsg,String response);
	
	public List<AccountOrderVo> listAll(OrderStatus orderStatus,Date startTime,Date endTime,int from, int pageSize);

	public Long getTotalCount(OrderStatus orderStatus,Date startTime,Date endTime);
}
