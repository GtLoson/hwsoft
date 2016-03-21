/**
 * 
 */
package com.hwsoft.dao.product;

import com.hwsoft.vo.product.BaseProductSubAccountVo;
import com.hwsoft.vo.product.ProductSubAccountDetail;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.hwsoft.common.product.ProductBuyerStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductAccount;

import java.util.List;

/**
 * @author tzh
 *
 */
@Repository("productAccountDao")
public class ProductAccountDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return ProductAccount.class;
	}

	public ProductAccount add(ProductAccount productBuyer){
		return super.add(productBuyer);
	}
	
	public ProductAccount update(ProductAccount productBuyer){
		return super.update(productBuyer);
	}
	
	public ProductAccount findById(final int id){
		return super.get(id);
	}
	
	public ProductAccount findByCustomerIdAndProductId(final int customerId,final int productId){
		
		String hql = " from ProductAccount where customerId=:customerId and productId=:productId";
		return (ProductAccount) super.getSessionFactory().getCurrentSession().createQuery(hql)
						.setParameter("customerId", customerId)
						.setParameter("productId", productId)
						.uniqueResult();
	}
	
	public long totalBuyNum(int productId) {
		String hql = "select count(id) from ProductAccount where productId=:productId";
		Object object =  super.getSessionFactory().getCurrentSession().createQuery(hql)
						.setParameter("productId", productId)
						.uniqueResult();
		
		return Long.parseLong(object.toString());
	}

	public long totalCount(){
		return super.getTotalCount();
	}
	
	
	public List<ProductAccount> findByProductId(int productId) {
		
		String hql = " from ProductAccount where productId=:productId";
		return super.getSessionFactory().getCurrentSession().createQuery(hql)
						.setParameter("productId", productId)
						.list();
	}
	public List<Integer> findAllNormalProductAccountId() {
		String hql = "select id from ProductAccount where productBuyerStatus =:productBuyerStatus ";
		return (List<Integer>) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("productBuyerStatus", ProductBuyerStatus.NORMAL).list();
	}
	
}
