/**
 * 
 */
package com.hwsoft.service.product.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.dao.product.ProductChannelDao;
import com.hwsoft.exception.product.ProductException;
import com.hwsoft.model.product.ProductChannel;
import com.hwsoft.service.product.ProductChannelService;

/**
 * @author tzh
 *
 */
@Service("productChannelService")
public class ProductChannelServiceImpl implements ProductChannelService {
	
	@Autowired
	private ProductChannelDao productChannelDao;

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductChannelService#findByType(com.hwsoft.common.product.ProductChannelType)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public ProductChannel findByType(ProductChannelType productChannelType) {
		return productChannelDao.findByType(productChannelType);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductChannelService#findById(int)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public ProductChannel findById(int productChannelId) {
		return productChannelDao.findById(productChannelId);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductChannelService#getTotalCount()
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Long getTotalCount() {
		return productChannelDao.getTotalCount();
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductChannelService#listAll(int, int)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<ProductChannel> listAll(int from, int pageSize) {
		return productChannelDao.list(from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<ProductChannel> listAll() {
		return productChannelDao.list();
	}

	/* (non-Javadoc)
         * @see com.hwsoft.service.product.ProductChannelService#add(com.hwsoft.common.product.ProductChannelType, java.lang.String, java.lang.String)
         */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductChannel add(ProductChannelType productChannelType,
			String name, String desc) {
		ProductChannel productChannel = new ProductChannel();
		productChannel.setProductChannelType(productChannelType);
		productChannel.setProductChannelDesc(desc);
		productChannel.setProductChannelName(name);
		return productChannelDao.save(productChannel);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.product.ProductChannelService#update(java.lang.Integer, java.lang.String)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public ProductChannel update(Integer productChannelId, String desc) {
		
		ProductChannel productChannel = findById(productChannelId);
		if(null == productChannel){
			throw new ProductException("产品渠道不存在");
		}
		productChannel.setProductChannelDesc(desc);
		return productChannelDao.update(productChannel);
	}

}
