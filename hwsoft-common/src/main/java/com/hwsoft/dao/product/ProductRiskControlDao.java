/**
 * 
 */
package com.hwsoft.dao.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductInfo;
import com.hwsoft.model.product.ProductRiskControl;

/**
 * @author tzh
 *
 */
@Repository("productRiskControlDao")
public class ProductRiskControlDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return ProductRiskControl.class;
	}
	
	public ProductRiskControl save(ProductRiskControl productRiskControl){
		return super.add(productRiskControl);
	}

	public ProductRiskControl update(ProductRiskControl productRiskControl){
		return super.update(productRiskControl);
	}
	
	public ProductRiskControl findById(int id){
		return super.get(id);
	}
	
	public ProductRiskControl findByProductId(int productId){
		String hql = " from ProductRiskControl where productId=:productId";
		return (ProductRiskControl) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("productId", productId).uniqueResult();
	}
}
