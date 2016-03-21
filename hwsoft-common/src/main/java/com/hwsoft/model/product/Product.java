/**
 *
 */
package com.hwsoft.model.product;

import com.hwsoft.common.product.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 产品
 *
 * @author tzh
 */
@Entity
@Table(name = "product")
@Audited
public class Product implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 2362677425660386984L;

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
   * 第三方产品id
   */
  @Column(name = "third_product_id", length = 32)
  private String thirdProductId;

  /**
   * 产品名称
   */
  @Column(name = "product_name", length = 128, nullable = false)
  private String productName;

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
   * 基础锁定期
   */
  @Column(name = "lock_duration")
  private Integer lockDuration;

  /**
   * 基础锁定期单位
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "lock_unit", length = 32)
  private ProductDateUnit lockUnit;

  /**
   * 预期最低利息
   */
  @Column(name = "min_interest", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
  private Double minInterest;

  /**
   * 预期最高利息
   */
  @Column(name = "max_interest", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
  private Double maxInterest;

  /**
   * 最低投资金额（起购份数）
   */
  @Column(name = "min_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0", nullable = false)
  private double minAmount;
  /**
   * 最高投资金额（最高份数）
   */
  @Column(name = "max_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
  private Double maxAmount;

  /**
   * 投资递进基数（每份金额）
   */
  @Column(name = "base_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
  private double baseAmount;
  
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

  /**
   * 产品剩余可售金额（份额）
   */
  @Column(name = "remaining_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
  private double remainingAmount;

  /**
   * 已经支付金额(份数)
   */
  @Column(name = "paid_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
  private double paidAmount;

  /**
   * 产品投资品种类型
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "product_invest_type", length = 32, nullable = false)
  private ProductInvestType productInvestType;

  /**
   * 产品销售类型
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "product_sale_type", length = 32, nullable = false)
  private ProductSaleType productSaleType;

  /**
   * 产品收益方式
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "product_income_type", length = 32, nullable = false)
  private ProductIncomeType productIncomeType;

  /**
   * 产品赎回方式
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "product_redeem_type", length = 32, nullable = false)
  private ProductRedeemType productRedeemType;

  /********************************************/

  /**
   * 产品类型：共鸣理财计划、共鸣固定期限产品、钱妈妈理财计划、钱妈妈散标、好买货币基金之类
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "product_type", length = 32, nullable = false)
  private ProductType productType;

  //TODO 保障方式、垫付机制暂时不管
  //TODO 手续费、网站分成等暂时不考虑
  //TODO 协议模板，这个拆分到另外一张表中
  //TODO 渠道暂未定义
  /**
   * 是否推荐
   */
  @Column(name = "recommend", nullable = false)
  private boolean recommend;

  /**
   * 放款金额（如果是债权，那么总金额中可能有部分是手续费）
   */
  @Column(name = "open_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0", nullable = false)
  private double openAmount;

  /**
   * 平台实际销售金额（份数）
   */
  @Column(name = "sale_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0", nullable = false)
  private double saleAmount;

  /**
   * 等待支付金额
   */
  @Column(name = "waitting_pay_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0", nullable = false)
  private double waittingPayAmount;

  /**
   * 产品完成时间（就类似于债权的结束时间）
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "closed_time", nullable = true)
  private Date closedTime;

  /**
   * 产品状态
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "product_status", length = 32, nullable = false)
  private ProductStatus productStatus;

  /**
   * 产品期限类型，固定期限、非固定期限（这个需要再考虑一下）
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "product_date_type", length = 32, nullable = false)
  private ProductDateType productDateType;

  /**
   * 渠道id
   */
  @Column(name = "product_channel_id", nullable = false)
  private int productChannelId;

  /**
   * 收益处理方式
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "product_profit_progress_type", length = 32, nullable = false)
  private ProductProfitProgressType productProfitProgressType;

  /**
   * 创建时间
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "create_time")
  private Date createTime;

  /**
   * 收益率(针对于固定收益产品)
   */
  @Column(name = "interest", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
  private Double interest;

  /**
   * 收益率类型
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "product_interest_type", length = 32, nullable = false)
  private ProductInterestType productInterestType;

  /**
   * 平台销售时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "plat_sale_time", nullable = true)
  private Date platSaleTime;

  /**
   * 是否虚增金额
   */
  @Column(name = "enable_dummy_bought_amount")
  private boolean enableDummyBoughtAmount = false;

  /**
   * 虚增金额
   */
  @Column(name = "dummy_bought_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
  private Double dummyBoughtAmount;

  /**
   * 是否虚增人数
   */
  @Column(name = "enable_dummy_bought_count")
  private boolean enableDummyBoughtCount = false;

  /**
   * 虚增人数
   */
  @Column(name = "dummy_bought_count")
  private Integer dummyBoughtCount;

  /**
   * 计息开始时间
   */
  @Temporal(TemporalType.DATE)
  @Column(name = "start_interest_bearing_date")
  private Date startInterestBearingDate;

  /**
   * 计息结束时间
   */
  @Temporal(TemporalType.DATE)
  @Column(name = "end_interest_bearing_date")
  private Date endInterestBearingDate;

  /**
   * 是否在手机端显示
   */
  @Column(name = "is_show", columnDefinition="bit(1) default 0 ")
  private boolean show;
  
  /**
   * 借款人id(居间人，现在是固定的)
   */
  @Column(name = "customer_sub_accountId", nullable = true)
  private Integer customerSubAccountId;
  
  /**
   * 放款时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "pass_time")
  private Date passTime;

	/**
	 * 满标时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ready_time")
	private Date readyTime;

	/**
	 * 流标时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "failed_time")
	private Date failedTime;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getThirdProductId() {
    return thirdProductId;
  }

  public void setThirdProductId(String thirdProductId) {
    this.thirdProductId = thirdProductId;
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

  public Integer getLockDuration() {
    return lockDuration;
  }

  public void setLockDuration(Integer lockDuration) {
    this.lockDuration = lockDuration;
  }

  public ProductDateUnit getLockUnit() {
    return lockUnit;
  }

  public void setLockUnit(ProductDateUnit lockUnit) {
    this.lockUnit = lockUnit;
  }

  public Double getMinInterest() {
    return minInterest;
  }

  public void setMinInterest(Double minInterest) {
    this.minInterest = minInterest;
  }

  public Double getMaxInterest() {
    return maxInterest;
  }

  public void setMaxInterest(Double maxInterest) {
    this.maxInterest = maxInterest;
  }

  public Double getMinAmount() {
    return minAmount;
  }

  public void setMinAmount(Double minAmount) {
    this.minAmount = minAmount;
  }

  public Double getMaxAmount() {
    return maxAmount;
  }

  public void setMaxAmount(Double maxAmount) {
    this.maxAmount = maxAmount;
  }

  public Double getBaseAmount() {
    return baseAmount;
  }

  public void setBaseAmount(Double baseAmount) {
    this.baseAmount = baseAmount;
  }

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Double getRemainingAmount() {
    return remainingAmount;
  }

  public void setRemainingAmount(Double remainingAmount) {
    this.remainingAmount = remainingAmount;
  }

  public Double getPaidAmount() {
    return paidAmount;
  }

  public void setPaidAmount(Double paidAmount) {
    this.paidAmount = paidAmount;
  }

  public ProductInvestType getProductInvestType() {
    return productInvestType;
  }

  public void setProductInvestType(ProductInvestType productInvestType) {
    this.productInvestType = productInvestType;
  }

  public ProductSaleType getProductSaleType() {
    return productSaleType;
  }

  public void setProductSaleType(ProductSaleType productSaleType) {
    this.productSaleType = productSaleType;
  }

  public ProductIncomeType getProductIncomeType() {
    return productIncomeType;
  }

  public void setProductIncomeType(ProductIncomeType productIncomeType) {
    this.productIncomeType = productIncomeType;
  }

  public ProductRedeemType getProductRedeemType() {
    return productRedeemType;
  }

  public void setProductRedeemType(ProductRedeemType productRedeemType) {
    this.productRedeemType = productRedeemType;
  }

  public ProductType getProductType() {
    return productType;
  }

  public void setProductType(ProductType productType) {
    this.productType = productType;
  }

  public boolean isRecommend() {
    return recommend;
  }

  public void setRecommend(boolean recommend) {
    this.recommend = recommend;
  }

  public double getOpenAmount() {
    return openAmount;
  }

  public void setOpenAmount(double openAmount) {
    this.openAmount = openAmount;
  }

  public double getSaleAmount() {
    return saleAmount;
  }

  public void setSaleAmount(double saleAmount) {
    this.saleAmount = saleAmount;
  }

  public double getWaittingPayAmount() {
    return waittingPayAmount;
  }

  public void setWaittingPayAmount(double waittingPayAmount) {
    this.waittingPayAmount = waittingPayAmount;
  }

  public Date getClosedTime() {
    return closedTime;
  }

  public void setClosedTime(Date closedTime) {
    this.closedTime = closedTime;
  }

  public ProductStatus getProductStatus() {
    return productStatus;
  }

  public void setProductStatus(ProductStatus productStatus) {
    this.productStatus = productStatus;
  }

  public ProductDateType getProductDatecustomerSubAccountIdType() {
    return productDateType;
  }

  public void setProductDateType(ProductDateType productDateType) {
    this.productDateType = productDateType;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public ProductProfitProgressType getProductProfitProgressType() {
    return productProfitProgressType;
  }

  public void setProductProfitProgressType(
      ProductProfitProgressType productProfitProgressType) {
    this.productProfitProgressType = productProfitProgressType;
  }

  public Double getInterest() {
    return interest;
  }

  public void setInterest(Double interest) {
    this.interest = interest;
  }

  public ProductInterestType getProductInterestType() {
    return productInterestType;
  }

  public void setProductInterestType(ProductInterestType productInterestType) {
    this.productInterestType = productInterestType;
  }

  public int getProductChannelId() {
    return productChannelId;
  }

  public void setProductChannelId(int productChannelId) {
    this.productChannelId = productChannelId;
  }

  public Integer getSortParameter() {
    return sortParameter;
  }

  public void setSortParameter(Integer sortParameter) {
    this.sortParameter = sortParameter;
  }

  public Date getPlatSaleTime() {
    return platSaleTime;
  }

  public void setPlatSaleTime(Date platSaleTime) {
    this.platSaleTime = platSaleTime;
  }

  public boolean isEnableDummyBoughtAmount() {
    return enableDummyBoughtAmount;
  }

  public void setEnableDummyBoughtAmount(boolean enableDummyBoughtAmount) {
    this.enableDummyBoughtAmount = enableDummyBoughtAmount;
  }

  public Double getDummyBoughtAmount() {
    return dummyBoughtAmount;
  }

  public void setDummyBoughtAmount(Double dummyBoughtAmount) {
    this.dummyBoughtAmount = dummyBoughtAmount;
  }

  public boolean isEnableDummyBoughtCount() {
    return enableDummyBoughtCount;
  }

  public void setEnableDummyBoughtCount(boolean enableDummyBoughtCount) {
    this.enableDummyBoughtCount = enableDummyBoughtCount;
  }

  public Integer getDummyBoughtCount() {
    return dummyBoughtCount;
  }

  public void setDummyBoughtCount(Integer dummyBoughtCount) {
    this.dummyBoughtCount = dummyBoughtCount;
  }

  public Date getEndInterestBearingDate() {
    return endInterestBearingDate;
  }

  public void setEndInterestBearingDate(Date endInterestBearingDate) {
    this.endInterestBearingDate = endInterestBearingDate;
  }

  public Date getStartInterestBearingDate() {
    return startInterestBearingDate;
  }

  public void setStartInterestBearingDate(Date startInterestBearingDate) {
    this.startInterestBearingDate = startInterestBearingDate;
  }

  public boolean isShow() {
    return show;
  }

  public void setShow(boolean show) {
    this.show = show;
  }

	public Integer getTotalCopies() {
		return totalCopies;
	}
	
	public void setTotalCopies(Integer totalCopies) {
		this.totalCopies = totalCopies;
	}

	public Integer getCustomerSubAccountId() {
		return customerSubAccountId;
	}

	public void setCustomerSubAccountId(Integer customerSubAccountId) {
		this.customerSubAccountId = customerSubAccountId;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	public Date getReadyTime() {
		return readyTime;
	}

	public void setReadyTime(Date readyTime) {
		this.readyTime = readyTime;
	}

	public Date getFailedTime() {
		return failedTime;
	}

	public void setFailedTime(Date failedTime) {
		this.failedTime = failedTime;
	}

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", sortParameter=" + sortParameter +
        ", thirdProductId='" + thirdProductId + '\'' +
        ", productName='" + productName + '\'' +
        ", productDesc='" + productDesc + '\'' +
        ", saleStartTime=" + saleStartTime +
        ", saleEndTime=" + saleEndTime +
        ", maturityDuration=" + maturityDuration +
        ", maturityUnit=" + maturityUnit +
        ", lockDuration=" + lockDuration +
        ", lockUnit=" + lockUnit +
        ", minInterest=" + minInterest +
        ", maxInterest=" + maxInterest +
        ", minAmount=" + minAmount +
        ", maxAmount=" + maxAmount +
        ", baseAmount=" + baseAmount +
        ", totalCopies=" + totalCopies +
        ", totalAmount=" + totalAmount +
        ", remainingAmount=" + remainingAmount +
        ", paidAmount=" + paidAmount +
        ", productInvestType=" + productInvestType +
        ", productSaleType=" + productSaleType +
        ", productIncomeType=" + productIncomeType +
        ", productRedeemType=" + productRedeemType +
        ", productType=" + productType +
        ", recommend=" + recommend +
        ", openAmount=" + openAmount +
        ", saleAmount=" + saleAmount +
        ", waittingPayAmount=" + waittingPayAmount +
        ", closedTime=" + closedTime +
        ", productStatus=" + productStatus +
        ", productDateType=" + productDateType +
        ", productChannelId=" + productChannelId +
        ", productProfitProgressType=" + productProfitProgressType +
        ", createTime=" + createTime +
        ", interest=" + interest +
        ", productInterestType=" + productInterestType +
        ", platSaleTime=" + platSaleTime +
        ", enableDummyBoughtAmount=" + enableDummyBoughtAmount +
        ", dummyBoughtAmount=" + dummyBoughtAmount +
        ", enableDummyBoughtCount=" + enableDummyBoughtCount +
        ", dummyBoughtCount=" + dummyBoughtCount +
        ", startInterestBearingDate=" + startInterestBearingDate +
        ", endInterestBearingDate=" + endInterestBearingDate +
        ", show=" + show +
        ", customerSubAccountId=" + customerSubAccountId +
        ", passTime=" + passTime +
        ", readyTime=" + readyTime +
        ", failedTime=" + failedTime +
        '}';
  }
  
  public ProductCornerType getCornerType() {
    return cornerType;
  }

  public void setCornerType(ProductCornerType cornerType) {
    this.cornerType = cornerType;
  }
}
