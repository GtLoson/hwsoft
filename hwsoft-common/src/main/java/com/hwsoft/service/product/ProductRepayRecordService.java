package com.hwsoft.service.product;

import java.util.List;

import com.hwsoft.model.product.ProductRepayRecord;

public interface ProductRepayRecordService {

	/**
	 * 批量添加还款信息
	 * @param productRepayRecords
	 */
	public void addBatch(List<ProductRepayRecord> productRepayRecords);
	
	public ProductRepayRecord findById(int id);
	
	public ProductRepayRecord update(ProductRepayRecord productRepayRecord);
	
	/**
	 * 根据产品id查询所有的未还记录
	 * @param productId
	 * @return
	 */
	public List<ProductRepayRecord> listAllUnpay(int productId);
	
	/**
	 * 根据产品id查询最近一期已还还款记录
	 * @param productId
	 * @return
	 */
	public ProductRepayRecord findByLastProductRepayRecord(int productId);
	
	
	/**
	 * 根据产品id查询最近一期待还还款记录
	 * @param productId
	 * @return
	 */
	public ProductRepayRecord findByNewProductRepayRecord(int productId);
	
	/**
	 * 查询当日应还
	 * @return
	 */
	public List<Integer> findTodayRepay();
	
	/**
	 * 根据产品id查询所有的逾期
	 * @param productId
	 * @return
	 */
	public List<Integer> listAllDue(int productId);
	
	/**
	 * 根据产品id查询所有的逾期
	 * @param productId
	 * @return
	 */
	public List<ProductRepayRecord> listAllOverDue(int productId);
	
	
	/**
	 * 根据产品id查询所有的还款记录
	 * @param productId
	 * @return
	 */
	public List<ProductRepayRecord> listByProductId(int productId);
	
	/**
	 * 根据产品id查询最后一期已还还款记录
	 * @param productId
	 * @return
	 */
	public ProductRepayRecord findByLastestProductRepayRecord(int productId);

}
