/**
 * 
 */
package com.hwsoft.service.order.gongming;

import java.util.Date;
import java.util.List;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.product.ProductBidType;
import com.hwsoft.model.order.common.BuyPlanOrder;
import com.hwsoft.vo.order.BuyOrderVo;

/**
 * @author tzh
 *
 */
public interface BuyPlanOrderService {

	public BuyPlanOrder addBuyPlanOrder(int userId,int productId,double amount,int userBankCardId);
	
	
	public BuyPlanOrder startBuyPlanOrder(String orderFormId);
	
	
	public BuyPlanOrder findBuyPlanOrderByOrderFormId(String orderFormId);
	
	public BuyPlanOrder updateByBuySuccess(String orderFormId,String code,String backMsg, double buySuccessAmount,String response);
	
	public BuyPlanOrder updateByBuyFailed(String orderFormId,String code,String backMsg, double buySuccessAmount,String response);
	
	public BuyPlanOrder updateByRequestFailed(String orderFormId);
	

    public List<BuyOrderVo> listAll(OrderStatus orderStatus,Date startTime,Date endTime,String mobile,String bankCardNumber,int from, int pageSize);

    public Long getTotalCount(OrderStatus orderStatus,Date startTime,Date endTime,String mobile,String bankCardNumber);
    
    
    //添加投标订单（钱妈妈自身使用）
    public BuyPlanOrder addBuyPlanOrderForBid(int customerId, int sharNum, int productId,
  		  ProductBidType productBidType, Integer bidProductId, Integer bidProductSubAccountId);
    
    /**
     * 根据产品id查询购买订单
     * @param productId
     * @return
     */
    public List<BuyPlanOrder> listByProductId(int productId);
    
}
