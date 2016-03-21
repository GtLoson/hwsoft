/**
 * 
 */
package com.hwsoft.dao.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductInfo;

/**
 * @author tzh
 *
 */
@Repository("productInfoDao")
public class ProductInfoDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return ProductInfo.class;
	}
	
	public ProductInfo save(ProductInfo productInfo){
		return super.add(productInfo);
	}

	public ProductInfo update(ProductInfo productInfo){
		return super.update(productInfo);
	}
	
	public ProductInfo findById(int id){
		return super.get(id);
	}
	
	public ProductInfo findByProductId(int productId){
		String hql = " from ProductInfo where productId=:productId";
		return (ProductInfo) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("productId", productId).uniqueResult();
	}
}
