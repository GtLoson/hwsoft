/**
 * 
 */
package com.hwsoft.model.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hwsoft.common.product.ProductBidType;
import com.hwsoft.common.product.ProductBuyerStatus;
import org.hibernate.envers.Audited;

/**
 * 产品购买信息（产品账户）
 * @author tzh
 *
 */
@Entity
@Table(name="product_account")
@Audited
public class ProductAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4265463513570688971L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	
	/**
	 * 总利息
	 */
	@Column(name = "total_interest", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0",nullable=false)
	private double totalInterest;
	
	/**
	 * 总利息(已收)
	 */
	@Column(name = "total_repaied_interest", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0",nullable=false)
	private double totalRepaiedInterest;

	/**
	 * 总罚息(已收)
	 */
	@Column(name = "total_defaule_interest", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0",nullable=false)
	private double totalDefaulInterest;
	
	/**
	 * 总收益(已收)
	 */
	@Column(name = "total_defaule_and_interest", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0",nullable=false)
	private double totalDefaulAndInterest;
	
	/**
	 * 可用余额
	 */
	@Column(name = "available_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0",nullable=false)
	private double availableAmount;
	
	/**
	 * 冻结金额
	 */
	@Column(name = "freeze_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0",nullable=false)
	private double freezeAmount;
	
	/**
	 * 当前总资产,这个值需要计算
	 */
//	@Column(name = "current_amount", scale = 2, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0",nullable=false)
//	private double currentAmount;
	
	/**
	 * 产品账户状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "product_buyer_status",nullable=false)
	private ProductBuyerStatus productBuyerStatus;
	
	/**
	 * 所属用户
	 */
	@Column(name = "customer_id",nullable=false)
	private Integer customerId;
	
	/**
	 * 所属产品
	 */
	@Column(name = "product_id",nullable=false)
	private Integer productId;
	
	/**
	 * 累计购买金额
	 */
	@Column(name = "buy_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0",nullable=false)
	private double buyAmount;
	
	/**
	 * 申购中的金额
	 */
	@Column(name = "purchase_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0",nullable=false)
	private double purchaseAmount;
	
	/**
	 * 已提现金额
	 */
	@Column(name = "withdrawal_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0",nullable=false)
	private double withdrawalAmount;
	
	/**
	 * 待收本金
	 */
	@Column(name = "waitting_principal", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0",nullable=false)
	private double waittingPrincipal;
	//TODO 所属渠道账户
	

	/**
	 * 投标类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "product_bid_type",nullable=false)
	private ProductBidType productBidType;
	
	/**
	 * 计划或者产品投标时，计划或者产品id
	 */
	@Column(name = "bid_product_id")
	private Integer bidProductId;
	
	/**
	 * 计划或者产品投标时，计划或者产品子账户id
	 */
	@Column(name = "bid_product_sub_account_id")
	private Integer bidProductSubAccountId;
	
	
	/**
	 * 购买份额
	 */
	@Column(name = "share_num")
	private Integer shareNum;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public double getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(double freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public ProductBuyerStatus getProductBuyerStatus() {
		return productBuyerStatus;
	}

	public void setProductBuyerStatus(ProductBuyerStatus productBuyerStatus) {
		this.productBuyerStatus = productBuyerStatus;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public double getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(double buyAmount) {
		this.buyAmount = buyAmount;
	}

	public double getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public void setWithdrawalAmount(double withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}

	public double getWaittingPrincipal() {
		return waittingPrincipal;
	}

	public void setWaittingPrincipal(double waittingPrincipal) {
		this.waittingPrincipal = waittingPrincipal;
	}

	public double getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(double purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public ProductBidType getProductBidType() {
		return productBidType;
	}

	public void setProductBidType(ProductBidType productBidType) {
		this.productBidType = productBidType;
	}

	public Integer getBidProductId() {
		return bidProductId;
	}

	public void setBidProductId(Integer bidProductId) {
		this.bidProductId = bidProductId;
	}

	public Integer getBidProductSubAccountId() {
		return bidProductSubAccountId;
	}

	public void setBidProductSubAccountId(Integer bidProductSubAccountId) {
		this.bidProductSubAccountId = bidProductSubAccountId;
	}

	public Integer getShareNum() {
		return shareNum;
	}

	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}

	public double getTotalDefaulInterest() {
		return totalDefaulInterest;
	}

	public void setTotalDefaulInterest(double totalDefaulInterest) {
		this.totalDefaulInterest = totalDefaulInterest;
	}

	public double getTotalDefaulAndInterest() {
		return totalDefaulAndInterest;
	}

	public void setTotalDefaulAndInterest(double totalDefaulAndInterest) {
		this.totalDefaulAndInterest = totalDefaulAndInterest;
	}

	public double getTotalRepaiedInterest() {
		return totalRepaiedInterest;
	}

	public void setTotalRepaiedInterest(double totalRepaiedInterest) {
		this.totalRepaiedInterest = totalRepaiedInterest;
	}
	
}
