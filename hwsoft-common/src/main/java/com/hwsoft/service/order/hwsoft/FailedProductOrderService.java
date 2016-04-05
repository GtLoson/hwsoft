package com.hwsoft.service.order.hwsoft;

import java.util.Date;
import java.util.List;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.model.order.hwsoft.FailedProductOrder;
import com.hwsoft.vo.order.BuyOrderVo;
import com.hwsoft.vo.order.FailedProductOrderVo;

public interface FailedProductOrderService {

	/**
	 * 未投标流标
	 * @param product
	 * @param staffId
	 * @param staffName
	 * @param notes
	 * @return
	 */
	public FailedProductOrder addFailedProductOrderNoBid(int productId,int staffId,String staffName,String notes);
	
	
	/**
	 * 已投标流标
	 * @param product
	 * @param staffId
	 * @param staffName
	 * @param notes
	 * @return
	 */
	public FailedProductOrder addFailedProductOrderBid(int productId,int staffId,String staffName,String notes);
	
	
    public List<FailedProductOrderVo> listAll(OrderStatus orderStatus,Date startTime,Date endTime,int from, int pageSize);

    public Long getTotalCount(OrderStatus orderStatus,Date startTime,Date endTime);
    
}
