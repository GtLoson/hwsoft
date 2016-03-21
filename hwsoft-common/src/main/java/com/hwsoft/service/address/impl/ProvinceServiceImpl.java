package com.hwsoft.service.address.impl;

import com.hwsoft.dao.address.CityDao;
import com.hwsoft.dao.address.ProvinceDao;
import com.hwsoft.model.address.City;
import com.hwsoft.model.address.Province;
import com.hwsoft.service.address.ProvinceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *  省份service 实现
 */
@Service("provinceService")
public class ProvinceServiceImpl implements ProvinceService {
  
  @Resource(name = "provinceDao")
  private ProvinceDao provinceDao;
  
  @Resource(name = "cityDao")
  private CityDao cityDao;
  
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
  public Province addProvince(Province province) {
    return provinceDao.addProvince(province);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<Province> listAllProvinces() {
    List<Province> provinceList =provinceDao.listAllProvince();
    return null == provinceList?new ArrayList<Province>():provinceList;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public List<City> listCityByProvinceCode(String provinceCode){
    Province province = provinceDao.provinceByCode(provinceCode);
    if(null != province){
      List<City> cityList = cityDao.listByProvinceId(province.getId());
      
      return null == cityList?new ArrayList<City>():cityList;
    }
    return new ArrayList<City>();
  }
}
