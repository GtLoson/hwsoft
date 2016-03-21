/**
 *
 */
package com.hwsoft.model.product;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.persistence.*;
import java.util.List;

/**
 * 产品信息（将product部分信息拆分到这个表中来）
 *
 * @author tzh
 */
@Entity
@Table(name = "product_info")
public class ProductInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;
  @Column(name = "product_id", nullable = false, unique = true)
  private Integer productId;

  /**
   * 项目描述
   */
  @Column(name = "desciption", columnDefinition = "TEXT", nullable = false)
  private String desciption;

  /**
   * 项目描述标题
   */
  @Column(name = "desciption_title", length = 32, nullable = false)
  private String desciptionTitle;

  /**
   * 产品的活动信息描述--add by sdy,之前已经把项目描述标题放在这里了，索性把活动信息也放在这里了，不规范，暂时先放在这里吧
   */
  @Column(name = "activity_desc", columnDefinition = "TEXT")
  private String activityDesc;
  
  /**
   * 抵押物信息
   */
  @Column(name = "mortgage_desc", columnDefinition = "TEXT")
  private String mortgageDesc;

  /**
   * 图片路径集合
   */
  @Column(name = "pic_paths", columnDefinition = "TEXT")
  private String picPaths;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public String getDesciption() {
    return desciption;
  }

  public void setDesciption(String desciption) {
    this.desciption = desciption;
  }

  public String getDesciptionTitle() {
    return desciptionTitle;
  }

  public void setDesciptionTitle(String desciptionTitle) {
    this.desciptionTitle = desciptionTitle;
  }

  public String getMortgageDesc() {
    return mortgageDesc;
  }

  public void setMortgageDesc(String mortgageDesc) {
    this.mortgageDesc = mortgageDesc;
  }

  public List<String> getPicPaths() {
    return new Gson().fromJson(picPaths, new TypeToken<List<String>>() {
    }.getType());
  }

  public void setPicPaths(List<String> picPaths) {
    this.picPaths = new Gson().toJson(picPaths);
  }

  public String getActivityDesc() {
    return activityDesc;
  }

  public void setActivityDesc(String activityDesc) {
    this.activityDesc = activityDesc;
  }
}
