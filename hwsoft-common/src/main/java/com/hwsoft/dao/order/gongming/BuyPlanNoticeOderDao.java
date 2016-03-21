/**
 * 
 */
package com.hwsoft.dao.order.gongming;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.order.gongming.BuyPlanNoticeOrder;

/**
 * @author tzh
 *
 */
@Repository("buyPlanNoticeOderDao")
public class BuyPlanNoticeOderDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return BuyPlanNoticeOrder.class;
	}
	
	public BuyPlanNoticeOrder save(BuyPlanNoticeOrder buyPlanNoticeOrder){
		return super.add(buyPlanNoticeOrder);
	}
	
	public BuyPlanNoticeOrder update(BuyPlanNoticeOrder buyPlanNoticeOrder){
		return super.update(buyPlanNoticeOrder);
	}

}
