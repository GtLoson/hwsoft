package com.hwsoft.service.order.hwsoft;

import java.util.Date;
import java.util.List;


import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.model.order.hwsoft.RepaymentProductOrder;
import com.hwsoft.vo.order.RepaymentProductOrderVo;

public interface RepaymentProductOrderService {
	
	/**
	 * 添加还款订单
	 * @param productId
	 * @param orderType
	 * @param staffId
	 * @param staffName
	 * @param repaymentIds
	 * @return
	 */
	public RepaymentProductOrder add(int productId,OrderType orderType,int staffId,String staffName,List<Integer> repaymentIds);

	
	/**
	 * 根据订单编号，查询订单
	 * @param orderFormId
	 * @return
	 */
	public RepaymentProductOrder findByOrderFormI(String orderFormId);
	
	public List<RepaymentProductOrderVo> listAll(OrderStatus orderStatus,
			Date startTime, Date endTime, int from, int pageSize) ;

	public Long getTotalCount(OrderStatus orderStatus, Date startTime,
			Date endTime) ;
	
	/**
	 * 根据id，查询订单
	 * @param id
	 * @return
	 */
	public RepaymentProductOrderVo findWholeById(int id);

}
