package com.hwsoft.service.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.product.ProductOfThatStatus;
import com.hwsoft.dao.product.ProductOfThatRegisterDao;
import com.hwsoft.model.product.ProductOfthatRegister;
import com.hwsoft.service.product.ProductOfThatRegisterService;

@Service("productOfThatRegisterService")
public class ProductOfThatRegisterServiceImpl implements
		ProductOfThatRegisterService {

	@Autowired
	private ProductOfThatRegisterDao productOfThatRegisterDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public ProductOfthatRegister addProductOfthatRegister(
			ProductOfthatRegister productOfthatRegister) {
		return productOfThatRegisterDao.save(productOfthatRegister);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public ProductOfthatRegister updateProductOfthatRegisterForFailedProduct(
			int productId) {
		List<ProductOfthatRegister> productOfthatRegisterList = findByProductId(productId);
		if(null == productOfthatRegisterList || productOfthatRegisterList.size() == 0){
			return null;
		}
		
		for(ProductOfthatRegister productOfthatRegister : productOfthatRegisterList){
			productOfthatRegister.setStatus(ProductOfThatStatus.FAILED);
			productOfThatRegisterDao.update(productOfthatRegister);
		}
		
		
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
	public List<ProductOfthatRegister> findByProductId(int productId) {
		return productOfThatRegisterDao.findByProductId(productId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public List<ProductOfthatRegister> updateLeding(int productId) {
		List<ProductOfthatRegister> productOfthatRegisterList = findByProductId(productId);
		if(null == productOfthatRegisterList || productOfthatRegisterList.size() == 0){
			return null;
		}
		for(ProductOfthatRegister productOfthatRegister : productOfthatRegisterList){
			productOfthatRegister.setStatus(ProductOfThatStatus.PROFITING);
			productOfThatRegisterDao.update(productOfthatRegister);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public List<ProductOfthatRegister> updateClosed(int productId) {
		List<ProductOfthatRegister> productOfthatRegisterList = findByProductId(productId);
		if(null == productOfthatRegisterList || productOfthatRegisterList.size() == 0){
			return null;
		}
		for(ProductOfthatRegister productOfthatRegister : productOfthatRegisterList){
			productOfthatRegister.setStatus(ProductOfThatStatus.EXITED);
			productOfThatRegisterDao.update(productOfthatRegister);
		}
		return null;
	}


}
