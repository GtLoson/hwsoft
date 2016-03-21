/**
 * 
 */
package com.hwsoft.dao.product;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductChannel;

/**
 * @author tzh
 *
 */
@Repository("productChannelDao")
public class ProductChannelDao extends BaseDao {

	/* (non-Javadoc)
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return ProductChannel.class;
	}
	
	public ProductChannel save(ProductChannel productChannel){
		return super.add(productChannel);
	}
	
	public ProductChannel update(ProductChannel productChannel){
		return super.update(productChannel);
	}

	public ProductChannel findByType(ProductChannelType productChannelType){
		String hql = "from ProductChannel where productChannelType=:productChannelType";
		Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setParameter("productChannelType", productChannelType);
		return (ProductChannel) query.uniqueResult();
	}
	public ProductChannel findById(int productChannelId) {
		return super.get(productChannelId);
	}
	
	  /**
     * 获取产品
     *
     * @param from
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<ProductChannel> list(int from, int pageSize) {
        String hql = "from ProductChannel";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setFirstResult(from).setMaxResults( pageSize);
        List<ProductChannel> Products = query.list();
        return Products;
    }

	/**
	 *
	 * @return
	 */
	public List<ProductChannel> list(){
		String hql = "from ProductChannel";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		List<ProductChannel> Products = query.list();
		return Products;
	}

    /**
     * 总条数
     *
     * @return
     */
    public Long getTotalCount(){
        return super.getTotalCount();
    }
}
