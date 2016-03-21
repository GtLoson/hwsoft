/**
 * 
 */
package com.hwsoft.dao.product;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductBank;

/**
 * @author tzh
 *
 */
@Repository("productBankDao")
public class ProductBankDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return ProductBank.class;
	}
	
	public ProductBank save(ProductBank productBank){
		return super.add(productBank);
	}

	
	public void del(int productId){
		String sql = "delete from product_bank where product_id="+productId;
		super.getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate(); 
	}
}
