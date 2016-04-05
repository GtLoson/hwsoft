/**
 *
 */
package com.hwsoft.model.product;

import com.hwsoft.common.product.*;
import com.hwsoft.model.BaseModel;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 产品
 *
 * @author tzh
 */
@Entity
@Table(name = "clothes_product")
@Audited
public class ClothesProduct extends BaseModel implements Serializable {

  /**
   * id
   */
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "sort_parameter", nullable = true)
  private Integer sortParameter;


  /**
   * 产品名称
   */
  @Column(name = "product_name", length = 128, nullable = false)
  private String productName;

  /**
   * 产品售价
   */
  @Column(name = "product_price", length = 128, nullable = false)
  private Double productPrice;

  /**
   * 促销价格
   */
  @Column(name = "product_promote_price", length = 128, nullable = false)
  private Double productPromotePrice;

  /**
   * 计划描述
   */
  @Column(name = "product_desc", columnDefinition = "TEXT", nullable = false)
  private String productDesc;

  /**
   * 销售开始时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "sale_start_time", nullable = true)
  private Date saleStartTime;

  /**
   * 销售结束时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "sale_end_time", nullable = true)
  private Date saleEndTime;

  /**
   * 计划到期时间
   */
  @Column(name = "maturity_duration", nullable = true)
  private Integer maturityDuration;

  /**
   * 计划到期时间单位
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "maturity_unit", length = 32)
  private ProductDateUnit maturityUnit;

  /**
   * 产品角标
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "corner_icon", length = 32 ,nullable = true)
  private ProductCornerType cornerType;

  /**
   * 总计份数
   */
  @Column(name = "total_copies")
  private Integer totalCopies;

  /**
   * 产品总金额
   */
  @Column(name = "total_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
  private double totalAmount;

  @Column(name = "recommend", nullable = false)
  private boolean recommend;

  @ElementCollection
  @CollectionTable(name="product_image_url", joinColumns=@JoinColumn(name="product_id"))
  @Column(name="image_url")
  private List<String> imageUrl;

  /**
   * 渠道id
   */
  @Column(name = "product_channel_id", nullable = false)
  private int productChannelId;

  /**
   * 产品状态
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "product_status", length = 32, nullable = false)
  private ProductStatus productStatus;

  @Column(name = "product_detail_url", nullable = false)
  private String productDetailUrl;

  public int getProductChannelId() {
    return productChannelId;
  }

  public void setProductChannelId(int productChannelId) {
    this.productChannelId = productChannelId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getSortParameter() {
    return sortParameter;
  }

  public void setSortParameter(Integer sortParameter) {
    this.sortParameter = sortParameter;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductDesc() {
    return productDesc;
  }

  public void setProductDesc(String productDesc) {
    this.productDesc = productDesc;
  }

  public Date getSaleStartTime() {
    return saleStartTime;
  }

  public void setSaleStartTime(Date saleStartTime) {
    this.saleStartTime = saleStartTime;
  }

  public Date getSaleEndTime() {
    return saleEndTime;
  }

  public void setSaleEndTime(Date saleEndTime) {
    this.saleEndTime = saleEndTime;
  }

  public Integer getMaturityDuration() {
    return maturityDuration;
  }

  public void setMaturityDuration(Integer maturityDuration) {
    this.maturityDuration = maturityDuration;
  }

  public ProductDateUnit getMaturityUnit() {
    return maturityUnit;
  }

  public void setMaturityUnit(ProductDateUnit maturityUnit) {
    this.maturityUnit = maturityUnit;
  }

  public ProductCornerType getCornerType() {
    return cornerType;
  }

  public void setCornerType(ProductCornerType cornerType) {
    this.cornerType = cornerType;
  }

  public Integer getTotalCopies() {
    return totalCopies;
  }

  public void setTotalCopies(Integer totalCopies) {
    this.totalCopies = totalCopies;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public boolean isRecommend() {
    return recommend;
  }

  public void setRecommend(boolean recommend) {
    this.recommend = recommend;
  }

  public ProductStatus getProductStatus() {
    return productStatus;
  }

  public void setProductStatus(ProductStatus productStatus) {
    this.productStatus = productStatus;
  }


  public Double getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(Double productPrice) {
    this.productPrice = productPrice;
  }

  public Double getProductPromotePrice() {
    return productPromotePrice;
  }

  public void setProductPromotePrice(Double productPromotePrice) {
    this.productPromotePrice = productPromotePrice;
  }

  public List<String> getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(List<String> imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getProductDetailUrl() {
    return productDetailUrl;
  }

  public void setProductDetailUrl(String productDetailUrl) {
    this.productDetailUrl = productDetailUrl;
  }
}
