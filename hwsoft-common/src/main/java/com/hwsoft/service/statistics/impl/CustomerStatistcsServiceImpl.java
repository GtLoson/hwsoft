/**
 *
 */
package com.hwsoft.service.statistics.impl;

import com.google.common.collect.Lists;
import com.hwsoft.common.product.ProductBuyerRecordStatus;
import com.hwsoft.common.product.ProductBuyerStatus;
import com.hwsoft.dao.statistcs.CustomerStatistcsDao;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.model.score.CustomerScore;
import com.hwsoft.service.customer.CustomerService;
import com.hwsoft.service.score.CustomerScoreService;
import com.hwsoft.service.statistics.CustomerStatisticsService;
import com.hwsoft.util.math.CalculateUtil;
import com.hwsoft.vo.customer.CustomerAccountVo;
import com.hwsoft.vo.product.BaseProductAccountVo;
import com.hwsoft.vo.product.BaseProductSubAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * @author tzh
 */
@Service("customerStatisticsService")
public class CustomerStatistcsServiceImpl implements CustomerStatisticsService {

  @Autowired
  private CustomerService customerService;

  @Autowired
  private CustomerScoreService customerScoreService;

  @Autowired
  private CustomerStatistcsDao customerStatistcsDao;

  /* (non-Javadoc)
   * @see com.hwsoft.service.statistics.CustomerStatisticsService#findCustomerAccountVo(int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public CustomerAccountVo findCustomerAccountVo(int customerId) {
    Customer customer = customerService.findById(customerId);
    if (null == customer) {
      return null;
    }

    CustomerAccountVo customerAccountVo = new CustomerAccountVo();
    customerAccountVo.setIdCardNumber(customer.getIdCard());
    customerAccountVo.setMobile(customer.getMobile());
    customerAccountVo.setRealName(customer.getRealName());
    customerAccountVo.setHasPayPwd(customer.getHasPayPassword());
    customerAccountVo.setRealNameAuthed(customer.isIdCardAuth());
    // 可用积分
    CustomerScore customerScore = customerScoreService.findByCustomerId(customerId);
    customerAccountVo.setAvailableScore(customerScore.getAvailableScore());
    // 今日收益，
    customerAccountVo.setTodayTotalEarnings(todayTotalEarnings(customerId));
    // 累计收益，
    customerAccountVo.setTotalEarnings(totalEarnings(customerId));
   
    //产品总资产
    double totalProductAssets = totalProductAssets(customerId);
    
    //总可用余额
    double totalAvailable = totalAvailable(customerId,null);
    customerAccountVo.setTotalAvailable(totalAvailable);	
    //总冻结金额
    double totalFreezen = totalFreeze(customerId);
    customerAccountVo.setTotalFreezen(totalFreezen);
    // 总资产
    customerAccountVo.setTotalAssets(CalculateUtil.doubleAdd(CalculateUtil.doubleAdd(totalAvailable, totalFreezen, 4),totalProductAssets,4));
    return customerAccountVo;
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.statistics.CustomerStatisticsService#todayTotalEarnings(int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public double todayTotalEarnings(int customerId) {
    return customerStatistcsDao.todayTotalEarnings(customerId);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public double totalEarnings(int customerId) {
    return customerStatistcsDao.totalEarnings(customerId);
  }

  /* (non-Javadoc)
     * @see com.hwsoft.service.statistics.CustomerStatisticsService#totalAssets(int)
     */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public double totalProductAssets(int customerId) {
    return customerStatistcsDao.totalProductAssets(customerId);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.statistics.CustomerStatisticsService#findProductAccountVo(int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<BaseProductAccountVo> findProductAccountVoList(EnumSet<ProductBuyerStatus> productBuyerStatusSet,int customerId) {
    List<BaseProductAccountVo> baseProductAccountVos = customerStatistcsDao.findProductAccountVo(productBuyerStatusSet, customerId);

    List<BaseProductAccountVo> baseProductAccountVos2 = new ArrayList<BaseProductAccountVo>();
    if (null != baseProductAccountVos) {
      for (BaseProductAccountVo baseProductAccountVo : baseProductAccountVos) {
        if (null != baseProductAccountVo.getId()) {
          baseProductAccountVos2.add(baseProductAccountVo);
        }
      }
      return baseProductAccountVos2;
    }

    return baseProductAccountVos;
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.statistics.CustomerStatisticsService#findProductSubAccountVo(int, int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<BaseProductSubAccountVo> findProductSubAccountVoList(int productAccountId) {
    // return
    List<BaseProductSubAccountVo> list = customerStatistcsDao.findProductSubAccountVo(productAccountId);
    List<BaseProductSubAccountVo> result = Lists.newArrayList();
    for(BaseProductSubAccountVo baseProductSubAccountVo:list) {
      BaseProductSubAccountVo vo = convertProductSubAccount(baseProductSubAccountVo);
      result.add(vo);
    }
    return result;
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.statistics.CustomerStatisticsService#findProductAccountVoById(int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public BaseProductAccountVo findProductAccountVoById(int productAccountId) {
    return customerStatistcsDao.findProductAccountVoById(productAccountId);
  }

  /* (non-Javadoc)
   * @see com.hwsoft.service.statistics.CustomerStatisticsService#findProductSubAccountVoBySubAccountId(int)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public BaseProductSubAccountVo findProductSubAccountVoBySubAccountId(
      int productSubAccountId) {
    return  customerStatistcsDao.findProductSubAccountVoBySubAccountId(productSubAccountId);
  }

  private BaseProductSubAccountVo convertProductSubAccount(BaseProductSubAccountVo baseProductSubAccountVo) {
    if (baseProductSubAccountVo == null) {
      return null;
    }
    String status = baseProductSubAccountVo.getStatus();
    if (null != status) {
      baseProductSubAccountVo.setStatus(ProductBuyerRecordStatus.fromString(status).toString());
      baseProductSubAccountVo.setStatusValue(status);
    }
    return baseProductSubAccountVo;
  }

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public double findProductTotolInterest(int productAccountId) {
		return customerStatistcsDao.findProductTotolInterest(productAccountId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public double totalAvailable(int customerId,Integer productChannelId) {
		return customerStatistcsDao.totalAvailable(customerId,productChannelId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public double totalFreeze(int customerId) {
		return customerStatistcsDao.totalFreeze(customerId);
	}
}
