/**
 * 
 */
package com.hwsoft.dao.statistcs;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;

/**
 * @author tzh
 *
 */
@Repository("productStatistcsDao")
public class ProductStatistcsDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return null;
	}
	
	/**
	 * 今日总收益
	 * @param customerId
	 * @return
	 */
	public double querySalesAmount(int productId){
		
		String sql = "select IF(SUM(success_amount) IS NULL , 0 ,SUM(success_amount)) from product_sub_account psa where psa.product_id="+productId;
		
		
		return Double.parseDouble(super.getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult().toString());
	
	}

	public double querySalesNum(int productId) {
		
		String sql = "select IF(SUM(share_num) IS NULL , 0 ,SUM(share_num)) from product_sub_account psa where psa.product_id="+productId;
		
		
		return Double.parseDouble(super.getSessionFactory().getCurrentSession().createSQLQuery(sql).uniqueResult().toString());
	}

	
}
