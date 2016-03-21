package com.hwsoft.dao.product;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hwsoft.common.product.ProductRepayRecordStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.product.ProductRepayRecord;
import com.hwsoft.util.date.DateTools;


/**
 * 
 * @author tzh
 *
 */
@Repository("productRepayRecordDao")
public class ProductRepayRecordDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return ProductRepayRecord.class;
	}
	
	public ProductRepayRecord save(ProductRepayRecord productRepayRecord){
		return super.add(productRepayRecord);
	}

	
	public ProductRepayRecord update(ProductRepayRecord productRepayRecord){
		return super.update(productRepayRecord);
	}
	
	public ProductRepayRecord findById(int id) {
		return super.get(id);
	}
	
	public List<ProductRepayRecord> listAllUnpay(int productId) {
		String hql = " from ProductRepayRecord where status=:status and productId=:productId";
		return super.getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("status", ProductRepayRecordStatus.UNREPAID)
				.setParameter("productId", productId).list();
	}
	
	public ProductRepayRecord findByLastProductRepayRecord(int productId) {
		String hql = " from ProductRepayRecord where (status=:status or status=:status1 or status=:status2)  and productId=:productId order by phaseNumber desc";
		List<ProductRepayRecord> productRepayRecords = super.getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("status", ProductRepayRecordStatus.REPAID)
				.setParameter("status1", ProductRepayRecordStatus.OVER_DUE)
				.setParameter("status2", ProductRepayRecordStatus.OVERDUE_CLOSED)
				.setParameter("productId", productId).setFirstResult(0).setMaxResults(1).list();
		if(null == productRepayRecords || productRepayRecords.size() == 0){
			return null;
		}
		
		return productRepayRecords.get(0);
	}
	
	public ProductRepayRecord findByNewProductRepayRecord(int productId) {
		String hql = " from ProductRepayRecord where status=:status and productId=:productId order by id";
		List<ProductRepayRecord> productRepayRecords = super.getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("status", ProductRepayRecordStatus.UNREPAID)
				.setParameter("productId", productId).setFirstResult(0).setMaxResults(1).list();
		if(null == productRepayRecords || productRepayRecords.size() == 0){
			return null;
		}
		
		return productRepayRecords.get(0);
	}
	
	public List<Integer> findTodayRepay() {
		
		String today = DateTools.dateToString(new Date(), DateTools.DATE_PATTERN_DAY);
		String startDate = today + DateTools.DAY_FIRST_TIME;
		String endDate = today + DateTools.DAY_LAST_TIME;
		
		String hql = "select id from product_repay_record where status='"+ ProductRepayRecordStatus.UNREPAID.name()
				+"' and repay_date >='"+startDate+"' and repay_date <='"+endDate+"'";
		System.out.println("查询今日应还："+hql);
		return super.getSessionFactory().getCurrentSession().createSQLQuery(hql).list();
	}
	
	public List<Integer> listAllDue(int productId) {
		
		String hql = "select id from ProductRepayRecord where status=:status and productId=:productId";
		
		return super.getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("status", ProductRepayRecordStatus.OVER_DUE)
				.setParameter("productId", productId).list();
	}
	
	public List<ProductRepayRecord> listByProductId(int productId) {
		String hql = " from ProductRepayRecord where  productId=:productId";
		return super.getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("productId", productId).list();
	}
	
	public List<ProductRepayRecord> listAllOverDue(int productId) {
		
		String hql = " from ProductRepayRecord where status=:status and productId=:productId";
		return super.getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("status", ProductRepayRecordStatus.OVER_DUE)
				.setParameter("productId", productId).list();
	}
	
	public ProductRepayRecord findByLastestProductRepayRecord(int productId) {
		String hql = " from ProductRepayRecord where productId=:productId order by id desc";
		List<ProductRepayRecord> productRepayRecords = super.getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("productId", productId).setFirstResult(0).setMaxResults(1).list();
		if(null == productRepayRecords || productRepayRecords.size() == 0){
			return null;
		}
		
		return productRepayRecords.get(0);
	}
	
}
