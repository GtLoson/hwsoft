/**
 * 
 */
package com.hwsoft.vo.product;


import java.math.BigDecimal;
import java.util.Date;

import com.hwsoft.common.product.ProductStatus;

/**
 * 主要为列表等只需要简单产品信息的接口准备
 * 主要展示给用户时使用
 * @author tzh
 *
 */
public class BaseProductVo {
	
	/**
	 * 产品id
	 */
	private Integer id;
	
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * 计划期限
	 */
	private String maturityDuration;
	
	/**
	 * 预期最低利息
	 */
	private Double minInterest;
	
	/**
	 * 预期最高利息
	 */
	private Double maxInterest;
	
	/**
	 * 起投金额
	 */
	private BigDecimal minAmount;
	
	/**
	 * 最高投资金额
	 */
	private BigDecimal maxAmount;
	
	/**
	 * 投资递进基数
	 */
	private BigDecimal baseAmount;
	
	/**
	 * 产品总金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 产品剩余可售金额
	 */
	private BigDecimal remainingAmount;
	
	/**
	 * 已售金额
	 */
	private BigDecimal saleAmount;
	
	/**
	 * 总份数
	 */
	private int totalCopies;
	
	/**
	 * 是否推荐
	 */
	private boolean recommend;
	
	/**
	 * 产品状态
	 */
	private ProductStatus productStatus;
	
	/**
	 * 产品状态
	 */
	private String productStatusValue;
	
	/**
	 * 渠道id
	 */
	private int productChannelId;
	
	/**
	 * 收益率(针对于固定收益产品)
	 */
	private Double interest;
	
	/**
	 * 收益率类型
	 */
	private String productInterestType;
	
	/**
	 * 累计购买人数（钱妈妈销售人数+虚增人数）
	 */
	private Integer totalBuyNumber;
	
	/**
	 * 融资完成率（产品总金额-剩余销售金额+虚增金额/产品总金额+虚增金额）
	 */
	private Double finishRatio;
	
	/**
	 * 开始计息时间
	 */
	private String startInterestDate;
	
	/**
	 * 结束计息时间
	 */
	private String endInterestDate;
	
	/**
	 * 可购开始时间
	 */
	private String startBuyTime;

	
	/**
	 * 可购结束时间
	 */
	private String endBuyTime;
	
	/**
	 * 销售开始时间
	 */
	private Date saleStartTime;

	/**
	 * 产品角标*
	 */
	private String cornerType;

	/**
	 * 资产描述标题，来自productInfo 
	 */
	private String DescriptionTitle;

	/**
	 * 活动描述
	 */
	private String activityDescription;
	
	/**
	 * 销售结束时间
	 */
	private Date saleEndTime;
	
	
	private Date passTime;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}


	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getBaseAmount() {
		return baseAmount;
	}

	public void setBaseAmount(BigDecimal baseAmount) {
		this.baseAmount = baseAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(BigDecimal remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
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
		
		if(null != productStatus){
			productStatusValue = productStatus.toString();
		}
	}

	public String getProductStatusValue() {
		return productStatusValue;
	}

	public void setProductStatusValue(String productStatusValue) {
		this.productStatusValue = productStatusValue;
		
		
	}

	public int getProductChannelId() {
		return productChannelId;
	}

	public void setProductChannelId(int productChannelId) {
		this.productChannelId = productChannelId;
	}


	public String getProductInterestType() {
		return productInterestType;
	}

	public void setProductInterestType(String productInterestType) {
		this.productInterestType = productInterestType;
		
	}

	public Integer getTotalBuyNumber() {
		return totalBuyNumber;
	}

	public void setTotalBuyNumber(Integer totalBuyNumber) {
		this.totalBuyNumber = totalBuyNumber;
	}

	public String getMaturityDuration() {
		return maturityDuration;
	}

	public void setMaturityDuration(String maturityDuration) {
		this.maturityDuration = maturityDuration;
	}

	public String getStartBuyTime() {
		return startBuyTime;
	}

	public void setStartBuyTime(String startBuyTime) {
		this.startBuyTime = startBuyTime;
	}

	public String getEndBuyTime() {
		return endBuyTime;
	}

	public String getStartInterestDate() {
		return startInterestDate;
	}

	public void setStartInterestDate(String startInterestDate) {
		this.startInterestDate = startInterestDate;
	}

	public String getEndInterestDate() {
		return endInterestDate;
	}

	public void setEndInterestDate(String endInterestDate) {
		this.endInterestDate = endInterestDate;
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

	public void setEndBuyTime(String endBuyTime) {
		this.endBuyTime = endBuyTime;
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

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getFinishRatio() {
		return finishRatio;
	}

	public void setFinishRatio(Double finishRatio) {
		this.finishRatio = finishRatio;
	}

	public String getCornerType() {
		return cornerType;
	}

	public void setCornerType(String cornerType) {
		this.cornerType = cornerType;
	}

	public String getDescriptionTitle() {
		return DescriptionTitle;
	}

	public void setDescriptionTitle(String descriptionTitle) {
		DescriptionTitle = descriptionTitle;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public int getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}

	public Date getPassTime() {
		return passTime;
	}

	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}
	
}
