package com.hwsoft.model.address;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 城市* 
 */
@Entity
@Table(name = "city")
public class City implements Serializable{
  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  /**
   * 城市名字
   */
  @Column(name = "name", nullable = false, length = 32)
  private String name;

  /**
   * 城市代码
   */
  @Column(name = "code", nullable = false, length = 32)
  private String code;

  /**
   * 所属省份
   */
  @Column(name = "province_id", nullable = false)
  private Integer provinceId;
  
  public Integer getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(Integer provinceId) {
    this.provinceId = provinceId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
