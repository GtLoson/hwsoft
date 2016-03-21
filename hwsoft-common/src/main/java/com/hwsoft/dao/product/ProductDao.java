/**
 *
 */
package com.hwsoft.dao.product;

import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.common.product.ProductStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.Product;
import com.hwsoft.util.date.DateTools;
import com.hwsoft.util.string.StringUtils;
import com.hwsoft.vo.product.BaseProductVo;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author tzh
 */
@Repository("productDao")
public class ProductDao extends BaseDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hwsoft.dao.BaseDao#entityClass()
	 */
	@Override
	protected Class<?> entityClass() {
		return Product.class;
	}

	/**
	 * 添加产品
	 * 
	 * @param product
	 * @return
	 */
	public Product save(Product product) {
		return super.add(product);
	}

	/**
	 * 获取产品
	 * 
	 * @param from
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Product> list(int from, int pageSize) {
		String hql = "from Product";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql).setFirstResult(from)
				.setMaxResults(pageSize);
		List<Product> Products = query.list();
		return Products;
	}

	/**
	 * 总条数
	 * 
	 * @return
	 */
	public Long getTotalCount() {
		return super.getTotalCount();
	}

	public Product findProductByThirdId(int channelId, String thirdId) {
		String hql = "from Product where thirdProductId=:thirdProductId and productChannelId=:productChannelId";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql)
				.setParameter("thirdProductId", thirdId)
				.setParameter("productChannelId", channelId);
		return (Product) query.uniqueResult();
	}

	public Product updateProduct(Product product) {
		return super.update(product);
	}

	public Product getProductByName(String name) {
		String hql = "from Product where productName = :productName";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql)
				.setParameter("productName", name);
		return (Product) query.uniqueResult();
	}

	public Product findById(int productId) {
		return super.get(productId);
	}

	public Long getTotalCount(Integer productChannelId, String productName,
			Boolean show, ProductStatus... productStatus) {
		String hql = "select count(id) from Product where 1=1 ";
		if (null != productChannelId) {
			hql += " and productChannelId= " + productChannelId;
		}
		if (null != show && show) {
			hql += " and show = true";
		}

		if (null != productStatus) {
			hql += addMultiStatus(productStatus);
		}

		if (!StringUtils.isEmpty(productName)) {
			hql += " and productName like'%" + productName + "%'";
		}

		System.out.println("product count:" + hql);

		Query query = super.getSessionFactory().getCurrentSession()
				.createQuery(hql);
		return (Long) query.uniqueResult();
	}

	private String addMultiStatus(ProductStatus... status) {
		if (null == status) {
			return "";
		}
		String hqlTmp = "";
		if (status.length == 1) {
			hqlTmp += " and productStatus ='" + status[0].name() + "'";
		} else {
			hqlTmp += " and productStatus in (";
			for (int index = 0; index < status.length; index++) {
				if (index != status.length - 1) {
					hqlTmp += "'" + status[index].name() + "',";
				} else {
					hqlTmp += "'" + status[index].name() + "'";
				}
			}
			hqlTmp += ")";
		}
		return hqlTmp;
	}

	@SuppressWarnings("unchecked")
	public List<Product> listAll(Integer productChannelId, String productName,
			int from, int pageSize, Boolean show,
			ProductStatus... productStatus) {
		String hql = "from Product where 1=1  ";
		if (null != productChannelId) {
			hql += " and productChannelId= " + productChannelId;
		}
		if (null != show && show) {
			hql += " and show = true";
		}

		if (null != productStatus) {
			hql += addMultiStatus(productStatus);
		}
		if (!StringUtils.isEmpty(productName)) {
			hql += " and productName like'%" + productName + "%'";
		}

		hql += " order by createTime desc";

		System.out.println("product list:" + hql);

		Query query = super.getSessionFactory().getCurrentSession()
				.createQuery(hql).setFirstResult(from).setMaxResults(pageSize);
		return query.list();
	}

	public Product findProductByRecommend(int productChannelId) {
		String hql = "from Product where recommend=:recommend and productChannelId=:productChannelId";
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql).setParameter("recommend", true)
				.setParameter("productChannelId", productChannelId);
		return (Product) query.uniqueResult();
	}

	public List<Integer> listALlByStatus(ProductStatus... productStatus) {
		String hql = "select id from Product where ";

		for (int index = 0; index < productStatus.length; index++) {
			if (index != productStatus.length - 1) {
				hql += " productStatus='" + productStatus[index].name() + "' or ";
			} else {
				hql += "productStatus='" + productStatus[index].name() + "'";
			}
		}

		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	public long getTotalCountForRepayment(
			Integer productChannelId, ProductStatus productStatus,Date startDate, Date endDate) {
		
		String hql = "select count(id) from Product where productStatus='"+productStatus.name()+"'";
		if(null != productChannelId){
			hql += " and productChannelId="+productChannelId;
		}
		if(null != productStatus){
			hql += " and productStatus='"+productStatus.name()+"'";
		}
		
		if(null != startDate || null != endDate){
			hql += " and id in (select productId from ProductRepayRecord where 1=1 ";
			if(null != startDate){
				hql += " and repayDate >='"+DateTools.dateToString(startDate, DateTools.DATE_PATTERN_DAY)+" 00:00:00'";
			}
			if(null != endDate){
				hql += " and repayDate <='"+DateTools.dateToString(endDate, DateTools.DATE_PATTERN_DAY)+" 23:59:59'";
			}
			hql += ")";
		}
		Query query = super.getSessionFactory().getCurrentSession()
				.createQuery(hql);
		return (Long) query.uniqueResult();
	}

	public List<Product> listForRepayment(
			Integer productChannelId, ProductStatus productStatus,
			Date startDate, Date endDate, int from, int pageSize) {
		
		String hql = " from Product " +
				" where 1 = 1 ";
		
		if(null != productChannelId){
			hql += " and productChannelId="+productChannelId;
		}
		if(null != productStatus){
			hql += " and productStatus='"+productStatus.name()+"'";
		}
		
		if(null != startDate || null != endDate){
			hql += " and id in (select productId from ProductRepayRecord where 1=1 ";
			if(null != startDate){
				hql += " and repayDate >='"+DateTools.dateToString(startDate, DateTools.DATE_PATTERN_DAY)+" 00:00:00'";
			}
			if(null != endDate){
				hql += " and repayDate <='"+DateTools.dateToString(endDate, DateTools.DATE_PATTERN_DAY)+" 23:59:59'";
			}
			hql += ")";
		}
		
		Query query = super.getSessionFactory().getCurrentSession()
				.createQuery(hql).setFirstResult(from).setMaxResults(pageSize);
		return query.list();
	}
	
}
