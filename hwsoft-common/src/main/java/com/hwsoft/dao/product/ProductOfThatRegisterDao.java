package com.hwsoft.dao.product;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductOfthatRegister;

@Repository("productOfThatRegisterDao")
public class ProductOfThatRegisterDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return ProductOfthatRegister.class;
	}

	
	public ProductOfthatRegister save(ProductOfthatRegister productOfthatRegister){
		return super.add(productOfthatRegister);
	}
	
	public ProductOfthatRegister update(ProductOfthatRegister productOfthatRegister){
		return super.update(productOfthatRegister);
	}
	
	public List<ProductOfthatRegister> findByProductId(int productId) {
		
		String hql = " from ProductOfthatRegister where productId=:productId";
		
		return super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("productId", productId).list();
	}
}
