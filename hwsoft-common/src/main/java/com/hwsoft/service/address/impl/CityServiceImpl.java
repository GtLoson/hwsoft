package com.hwsoft.service.address.impl;

import com.hwsoft.dao.address.CityDao;
import com.hwsoft.model.address.City;
import com.hwsoft.service.address.CityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 城市service 实现* 
 */
@Service("cityService")
public class CityServiceImpl implements CityService {

  @Resource(name = "cityDao")
  private CityDao cityDao;
  
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public City addCity(City city){
    return cityDao.addCity(city);
  }
}
