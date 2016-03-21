package com.hwsoft.service.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.product.ProductRepayRecordDao;
import com.hwsoft.model.product.ProductRepayRecord;
import com.hwsoft.service.product.ProductRepayRecordService;


/**
 * 
 * @author tzh
 *
 */
@Service("poductRepayRecordService")
public class ProductRepayRecordServiceImpl implements ProductRepayRecordService {

	@Autowired
	private ProductRepayRecordDao productRepayRecordDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public void addBatch(List<ProductRepayRecord> productRepayRecords) {
		if(null == productRepayRecords || productRepayRecords.size() == 0){
			return;
		}
		
		for(ProductRepayRecord productRepayRecord : productRepayRecords){
			productRepayRecordDao.save(productRepayRecord);
		}
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public ProductRepayRecord findById(int id) {
		return productRepayRecordDao.findById(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductRepayRecord update(ProductRepayRecord productRepayRecord) {
		return productRepayRecordDao.update(productRepayRecord);
		
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<ProductRepayRecord> listAllUnpay(int productId) {
		return productRepayRecordDao.listAllUnpay(productId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public ProductRepayRecord findByLastProductRepayRecord(int productId) {
		return productRepayRecordDao.findByLastProductRepayRecord(productId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public ProductRepayRecord findByNewProductRepayRecord(int productId) {
		return productRepayRecordDao.findByNewProductRepayRecord(productId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Integer> findTodayRepay() {
		return productRepayRecordDao.findTodayRepay();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Integer> listAllDue(int productId) {
		return productRepayRecordDao.listAllDue(productId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<ProductRepayRecord> listByProductId(int productId) {
		return productRepayRecordDao.listByProductId(productId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<ProductRepayRecord> listAllOverDue(int productId) {
		return productRepayRecordDao.listAllOverDue(productId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public ProductRepayRecord findByLastestProductRepayRecord(int productId) {
		return productRepayRecordDao.findByLastestProductRepayRecord(productId);
	}

}
