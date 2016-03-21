package com.hwsoft.service.statistics.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.statistcs.ProductStatistcsDao;
import com.hwsoft.service.statistics.ProductStatisticsService;


@Service("productStatisticsService")
public class ProductStatisticsServiceImpl implements ProductStatisticsService {

	@Autowired
	private ProductStatistcsDao productStatistcsDao;
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public double querySalesAmount(int productId) {
		return productStatistcsDao.querySalesAmount(productId);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public double querySalesNum(int productId) {
		return productStatistcsDao.querySalesNum(productId);
	}

}
