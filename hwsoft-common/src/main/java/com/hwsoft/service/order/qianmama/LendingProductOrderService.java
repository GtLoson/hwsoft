package com.hwsoft.service.order.hwsoft;

import java.util.Date;
import java.util.List;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.model.order.hwsoft.LendingProductOrder;
import com.hwsoft.vo.order.LendingProductOrderVo;


/**
 * 放款订单
 * @author tzh
 *
 */
public interface LendingProductOrderService {

	public LendingProductOrder addLendingProductOrder(int productId,int staffId,String staffName);
	
	public List<LendingProductOrderVo> listAll(OrderStatus orderStatus,
			Date startTime, Date endTime, int from, int pageSize) ;

	public Long getTotalCount(OrderStatus orderStatus, Date startTime,
			Date endTime) ;

	public LendingProductOrderVo findWholeById(int id);
	
	public LendingProductOrder findByOrderFormId(String orderFormId);
	
}
