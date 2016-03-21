/**
 * 
 */
package com.hwsoft.service.statistics;


import java.util.EnumSet;
import java.util.List;

import com.hwsoft.common.product.ProductBuyerStatus;
import com.hwsoft.vo.customer.CustomerAccountVo;
import com.hwsoft.vo.product.BaseProductAccountVo;
import com.hwsoft.vo.product.BaseProductSubAccountVo;

/**
 * @author tzh
 *
 */
public interface CustomerStatisticsService {

	public CustomerAccountVo findCustomerAccountVo(int customerId);
	
	/**
	 * 今日总收益
	 * @param customerId
	 * @return
	 */
	public double todayTotalEarnings(int customerId);

	/**
	 * 用户累计收益
	 * @param customerId
	 * @return
	 */
	public double totalEarnings(int customerId);

	/**
	 * 总资产
	 * @param customerId
	 * @return
	 */
	public double totalProductAssets(int customerId);
	
	/**
	 * 
	 * @param customerId
	 * @return
	 */
	public List<BaseProductAccountVo> findProductAccountVoList(EnumSet<ProductBuyerStatus> productBuyerStatusSet,int customerId);
	
	public BaseProductAccountVo findProductAccountVoById(int productAccountId);
	
	/**
	 * 
	 * @param customerId
	 * @param productId
	 * @return
	 */
	public List<BaseProductSubAccountVo> findProductSubAccountVoList(int productAccountId);
	
	public BaseProductSubAccountVo findProductSubAccountVoBySubAccountId(int productSubAccountId);
	
	
	
	public double findProductTotolInterest(int productAccountId);
	
	
	/**
	 * 总可用余额
	 * @param customerId
	 * @return
	 */
	public double totalAvailable(int customerId,Integer productChannelId);
	
	/**
	 * 总冻结金额
	 * @param customerId
	 * @return
	 */
	public double totalFreeze(int customerId);
}
