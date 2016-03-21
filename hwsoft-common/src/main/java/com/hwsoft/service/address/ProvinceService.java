package com.hwsoft.service.address;

import com.hwsoft.model.address.City;
import com.hwsoft.model.address.Province;

import java.util.List;

/**
 * 省份service
 */
public interface ProvinceService {
  
  public Province addProvince(Province province);
  
  public List<Province> listAllProvinces();
  
  public List<City> listCityByProvinceCode(String provinceCode);
}
