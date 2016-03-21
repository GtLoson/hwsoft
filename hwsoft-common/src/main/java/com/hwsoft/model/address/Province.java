package com.hwsoft.model.address;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 省份 
 */
@Entity
@Table(name = "province")
public class Province implements Serializable{
  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  /**
   * 省份名称 
   */
  @Column(name = "name", nullable = false, length = 32)
  private String name;
  /**
   * 省份编码
   */
  @Column(name = "code", nullable = false, length = 32)
  private String code;

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
