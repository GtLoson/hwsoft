/**
 * 
 */
package com.hwsoft.dao.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductAgreementTemplate;

/**
 * @author tzh
 *
 */
@Repository("productAgreementTemplateDao")
public class ProductAgreementTemplateDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return ProductAgreementTemplate.class;
	}
	
	public ProductAgreementTemplate save(ProductAgreementTemplate productAgreementTemplate){
		return super.add(productAgreementTemplate);
	}
	public ProductAgreementTemplate update(ProductAgreementTemplate productAgreementTemplate){
		return super.update(productAgreementTemplate);
	}

	public ProductAgreementTemplate findByProductId(int productId) {
		String hql = " from ProductAgreementTemplate where productId=:productId";
		return (ProductAgreementTemplate) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("productId", productId).uniqueResult();
	}
	
}
