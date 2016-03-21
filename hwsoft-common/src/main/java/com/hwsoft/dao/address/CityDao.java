package com.hwsoft.dao.address;

import com.hwsoft.common.acitivity.ActivityStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.address.City;
import com.hwsoft.util.date.DateTools;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 城市Dao* 
 */
@Repository("cityDao")
public class CityDao extends BaseDao {
  @Override
  protected Class<?> entityClass() {
    return City.class;
  }
  
  public City addCity(City city){
    return super.add(city);
  }

  @SuppressWarnings("unchecked")
  public List<City> listByProvinceId(Integer provinceId){
    String hql = " from City where provinceId=:provinceId";
    return super.getSessionFactory().getCurrentSession().createQuery(hql)
        .setParameter("provinceId", provinceId)
        .list();
  }
}
