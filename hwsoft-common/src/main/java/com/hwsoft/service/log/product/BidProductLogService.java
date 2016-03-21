package com.hwsoft.service.log.product;

import com.hwsoft.common.product.ProductBidType;
import com.hwsoft.model.log.product.BidProductLog;

public interface BidProductLogService {

	public BidProductLog add(String orderFormId, ProductBidType productBidType,int productId,int productSubAccountId,
			String notes,Integer bidProductId,Integer bidProductSubAccountId,Integer shareNum);
	
}
