package com.hwsoft.dao.address;

import com.hwsoft.common.acitivity.ActivityStatus;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.address.Province;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 省份dao 
 */
@Repository("provinceDao")
public class ProvinceDao extends BaseDao {
  @Override
  protected Class<?> entityClass() {
    return Province.class;
  }
  
  public List<Province> listAllProvince(){
    return super.loanAll();
  }
  
  public Province addProvince(Province province){
    return super.add(province);
  }
  
  public Province provinceByCode(String code){
    String hql = "from Province where code= :code";

    return (Province)super.getSessionFactory().getCurrentSession().createQuery(hql)
        .setParameter("code", code)
        .uniqueResult();
  }
}
