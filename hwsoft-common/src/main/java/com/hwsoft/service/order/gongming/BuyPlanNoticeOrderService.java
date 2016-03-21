/**
 * 
 */
package com.hwsoft.service.order.gongming;

import java.util.Date;

import com.hwsoft.common.product.ProductBuyerRecordStatus;
import com.hwsoft.model.order.gongming.BuyPlanNoticeOrder;

/**
 * @author tzh
 * 
 */
public interface BuyPlanNoticeOrderService {

	public BuyPlanNoticeOrder addNoticeOrder(String buyPlanOrderFormId,
			ProductBuyerRecordStatus currentStatus,
			ProductBuyerRecordStatus beforeStatus, 
			Date updateTime,String response);

}
