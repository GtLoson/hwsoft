/**
 * 
 */
package com.hwsoft.model.product;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.hwsoft.common.product.ProductBuyerRecordStatus;
import com.hwsoft.common.product.ProductBidType;

import org.hibernate.envers.Audited;

/**
 * 产品购买记录(即产品订单)
 * @author tzh
 *
 */
@Entity
@Table(name="product_sub_account")
@Audited
public class ProductSubAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8701243835207442320L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id", nullable = false)
	private Integer productId;
	
	/**
	 * 产品账户id
	 */
	@Column(name = "product_buyer_id", nullable = false)
	private Integer productBuyerId;
	
	/**
	 * 渠道子账户id
	 */
	@Column(name = "customer_sub_account_id", nullable = false)
	private Integer customerSubAccountId;
	
	/**
	 * 本次申购金额
	 */
	@Column(name = "amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0",nullable=false)
	private double amount;
	/**
	 * 本次成功购买金额
	 */
	@Column(name = "success_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
	private double successAmount;
	
	/**
	 * 购买时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "buy_time", nullable = false)
	private Date buyTime;
	
	/**
	 * 所属用户
	 */
	@Column(name = "customer_id", nullable = false)
	private Integer customerId;
	
	
	/**
	 * 购买记录状态（产品订单状态）
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "status",length=32,nullable=false)
	private ProductBuyerRecordStatus status;
	
	/**
	 * 订单编号
	 */
	@Column(name = "order_form_id",length=32)
	private String orderFormId;
	
	/**
	 * 昨日收益
	 */
	@Column(name = "daily_interest", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0",nullable=false)
	private double dailyInterest;
	/**
	 * 手续费
	 */
	@Column(name = "fee", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0",nullable=false)
	private double fee;
	
	/**
	 * 收益最后更新时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "last_interest_update_date")
	private Date lastInterestUpdateDate;
	
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
	 * 待收本金
	 */
	@Column(name = "waitting_principal", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0",nullable=false)
	private double waittingPrincipal;
	
	/**
	 * 计息日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "effective_date")
	private Date effectiveDate;
	
	/**
	 * 结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date")
	private Date endDate;
	
	/**
	 * 已提现金额
	 */
	@Column(name = "withdrawal_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0",nullable=false)
	private double withdrawalAmount;
	
	/**
	 * 购买银行卡
	 */
	@Column(name = "bank_card_number",length=20)
	private String bankCardNumber;
	/**
	 * 购买银行卡
	 */
	@Column(name = "user_bank_card_id")
	private Integer userBankCardId;
	@Version
	@Column(name="version")
	private Integer version;
	
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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProductBuyerId() {
		return productBuyerId;
	}

	public void setProductBuyerId(Integer productBuyerId) {
		this.productBuyerId = productBuyerId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public double getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(double successAmount) {
		this.successAmount = successAmount;
	}

	public ProductBuyerRecordStatus getStatus() {
		return status;
	}

	public void setStatus(ProductBuyerRecordStatus status) {
		this.status = status;
	}

	public String getOrderFormId() {
		return orderFormId;
	}

	public void setOrderFormId(String orderFormId) {
		this.orderFormId = orderFormId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public double getDailyInterest() {
		return dailyInterest;
	}

	public void setDailyInterest(double dailyInterest) {
		this.dailyInterest = dailyInterest;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public Date getLastInterestUpdateDate() {
		return lastInterestUpdateDate;
	}

	public void setLastInterestUpdateDate(Date lastInterestUpdateDate) {
		this.lastInterestUpdateDate = lastInterestUpdateDate;
	}

	public double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public double getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public void setWithdrawalAmount(double withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getBankCardNumber() {
		return bankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	public Integer getUserBankCardId() {
		return userBankCardId;
	}

	public void setUserBankCardId(Integer userBankCardId) {
		this.userBankCardId = userBankCardId;
	}

	public Integer getCustomerSubAccountId() {
		return customerSubAccountId;
	}

	public void setCustomerSubAccountId(Integer customerSubAccountId) {
		this.customerSubAccountId = customerSubAccountId;
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

	public double getWaittingPrincipal() {
		return waittingPrincipal;
	}

	public void setWaittingPrincipal(double waittingPrincipal) {
		this.waittingPrincipal = waittingPrincipal;
	}
	
}
