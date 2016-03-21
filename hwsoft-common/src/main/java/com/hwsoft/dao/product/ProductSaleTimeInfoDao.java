/**
 * 
 */
package com.hwsoft.dao.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductSaleTimeInfo;

/**
 * @author tzh
 *
 */
@Repository("productSaleTimeInfoDao")
public class ProductSaleTimeInfoDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return ProductSaleTimeInfo.class;
	}
	
	public ProductSaleTimeInfo save(ProductSaleTimeInfo productSaleTimeInfo){
		return super.add(productSaleTimeInfo);
	}

	
	public ProductSaleTimeInfo update(ProductSaleTimeInfo productSaleTimeInfo){
		return super.update(productSaleTimeInfo);
	}
	
	public ProductSaleTimeInfo getById(Integer productSaleTimeInfoId){
		return super.get(productSaleTimeInfoId);
	}
	
	public ProductSaleTimeInfo findByProductId(int productId) {

		String hql = " from ProductSaleTimeInfo where productId="+productId;
		return (ProductSaleTimeInfo) super.getSessionFactory().getCurrentSession().createQuery(hql).uniqueResult();
	}
}
