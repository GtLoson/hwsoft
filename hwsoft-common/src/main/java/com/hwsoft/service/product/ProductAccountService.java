/**
 * 
 */
package com.hwsoft.service.product;


import java.util.List;

import com.hwsoft.common.product.ProductBidType;
import com.hwsoft.common.product.ProductBuyerStatus;
import com.hwsoft.model.product.ProductAccount;

/**
 * @author tzh
 *
 */
public interface ProductAccountService {

	public ProductAccount add(int customerId,int productId,double purchaseAmount);
	
	/**
	 * 下单成功更新
	 * @return
	 */
	public ProductAccount updateByPlaceOrderSuccess(int id,double buyAmount,double purchaseAmount);
	
	public ProductAccount findByCustomerIdAndProductId(int customerId,int productId);
	
	public ProductAccount findById(int id);
	
	public ProductAccount updateByPaySuccess(int id,int buyerRecordId);
	
	public ProductAccount updateByOrderTerminated(int id,int buyerRecordId);
	
	public ProductAccount updateByOrderHasWaithdrawal(int id,int buyerRecordId,double amount);
	
	public ProductAccount updateInterest(int id,double amount);
	
	/**
	 * 产品累计购买人数
	 * @param productId
	 * @return
	 */
	public long totalBuyNum(int productId);
	/**
	 * 投标添加
	 * @param customerId
	 * @param productId
	 * @param purchaseAmount
	 * @return
	 */
	public ProductAccount addForBid(int customerId,int productId,double purchaseAmount,int shareNum,
			ProductBidType productBidType, Integer bidProductId, Integer bidProductSubAccountId);
	
//	/**
//	 * 投标跟新
//	 * @param customerId
//	 * @param productId
//	 * @param purchaseAmount
//	 * @return
//	 */
//	public ProductAccount updateForBid(int id,double purchaseAmount,int shareNum);
	
	/**
	 * 流标更新
	 * @param productId
	 * @return
	 */
	public ProductAccount updateForFailed(int productId);
	
	/**
	 * 根据产品id查询产品账户
	 * @param productId
	 * @return
	 */
	public List<ProductAccount> findByProductId(int productId);
	

	/**
	 * 放款更新
	 * @param productId
	 * @return
	 */
	public ProductAccount updateForLending(int productId);
	
	
	/**
	 * 还款更新
	 * @param productAccountId
	 * @param interest
	 * @param principal
	 * @param defaultInterest
	 * @return
	 */
	public ProductAccount updateForRepayment(int productAccountId, double interest, double principal,
			double defaultInterest) ;
	
	
	/**
	 * 查询所有收益中的产品账号
	 * @return
	 */
	public List<Integer> findAllNormalProductAccountId();
	
	/**
	 * 状态更新
	 * @param productId
	 * @return
	 */
	public ProductAccount updateStatus(int productAccountId);
}


