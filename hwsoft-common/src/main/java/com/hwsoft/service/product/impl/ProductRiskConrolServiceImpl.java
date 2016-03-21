/**
 * 
 */
package com.hwsoft.service.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.product.ProductRiskControlDao;
import com.hwsoft.model.product.ProductRiskControl;
import com.hwsoft.service.product.ProductRiskControlService;

/**
 * @author tzh
 *
 */
@Service("productRiskControlService")
public class ProductRiskConrolServiceImpl implements ProductRiskControlService {
	
	@Autowired
	private ProductRiskControlDao productRiskControlDao;

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductRiskControlService#add(int, java.lang.String, java.util.List)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductRiskControl add(int productId, String desc,
			List<String> picPaths,String mortgager,String guaranteeCompany) {
		ProductRiskControl productRiskControl = new ProductRiskControl();
		productRiskControl.setProductId(productId);
		productRiskControl.setRiskControlDescription(desc);
		productRiskControl.setMortgager(mortgager);
		productRiskControl.setGuaranteeCompany(guaranteeCompany);
		productRiskControl.setPicPaths(picPaths);
		return productRiskControlDao.save(productRiskControl);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductRiskControlService#findByProductId(int)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public ProductRiskControl findByProductId(int productId) {
		return productRiskControlDao.findByProductId(productId);
	}

}
