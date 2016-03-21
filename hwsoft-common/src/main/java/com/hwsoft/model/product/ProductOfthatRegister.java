package com.hwsoft.model.product;

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

import org.hibernate.envers.Audited;

import com.hwsoft.common.product.ProductBidType;
import com.hwsoft.common.product.ProductOfThatStatus;

/**
 * 产品份额登记表
 * @author tzh
 *
 */
@Entity
@Table(name="product_of_that_register")
@Audited
public class ProductOfthatRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 份额编号（可以考虑用订单编号）
	 */
	@Column(name = "of_that_identifier",nullable=false)
	private String ofThatIdentifier;
	
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
	 * 渠道子账户
	 */
	@Column(name = "customer_sub_account_id",nullable=false)
	private Integer cutomerSubAccountId;
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id",nullable=false)
	private Integer productId;
	
	/**
	 * 产品子账户id
	 */
	@Column(name = "product_sub_account_id",nullable=false)
	private Integer productSubAccountId;//这个相当于投标记录
	
	/**
	 * 当期持有持有份额
	 */
	@Column(name = "of_that_number",nullable=false)
	private Integer ofThatNumber;
	
	/**
	 * 总购买份额
	 */
	@Column(name = "total_of_that_number",nullable=false)
	private Integer totalOfThatNumber;
	
	/**
	 * 已转让份额
	 */
	@Column(name = "has_transfer_of_that_number",nullable=false)
	private Integer hasTransferOfThatNumber;
	
	/**
	 * 已退出份额
	 */
	@Column(name = "has_exit_of_that_number",nullable=false)
	private Integer hasExitOfThatNumber;
	
	/**
	 * 购买时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	/**
	 * 原始债权人，即原份额持有人（可能接收人的份额来自于两份债权记录）
	 */
	@Column(name = "orginal_creditor_id")
	private Integer orginalCreditorId;
	
	/**
	 * 原始份额编号
	 */
	@Column(name = "orginal_of_that_id")
	private Integer orginalOfThatId;
	
	/**
	 * 每份金额
	 */
	 @Column(name = "of_that_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0", nullable = false)
	private double ofThatAmount;
	
	/**
	 * 份额类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "type",length=32,nullable = false)
	private ProductBidType type;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status",length=32,nullable = false)
	private ProductOfThatStatus status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOfThatIdentifier() {
		return ofThatIdentifier;
	}

	public void setOfThatIdentifier(String ofThatIdentifier) {
		this.ofThatIdentifier = ofThatIdentifier;
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

	public Integer getCutomerSubAccountId() {
		return cutomerSubAccountId;
	}

	public void setCutomerSubAccountId(Integer cutomerSubAccountId) {
		this.cutomerSubAccountId = cutomerSubAccountId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProductSubAccountId() {
		return productSubAccountId;
	}

	public void setProductSubAccountId(Integer productSubAccountId) {
		this.productSubAccountId = productSubAccountId;
	}

	public Integer getOfThatNumber() {
		return ofThatNumber;
	}

	public void setOfThatNumber(Integer ofThatNumber) {
		this.ofThatNumber = ofThatNumber;
	}

	public Integer getTotalOfThatNumber() {
		return totalOfThatNumber;
	}

	public void setTotalOfThatNumber(Integer totalOfThatNumber) {
		this.totalOfThatNumber = totalOfThatNumber;
	}

	public Integer getHasTransferOfThatNumber() {
		return hasTransferOfThatNumber;
	}

	public void setHasTransferOfThatNumber(Integer hasTransferOfThatNumber) {
		this.hasTransferOfThatNumber = hasTransferOfThatNumber;
	}

	public Integer getHasExitOfThatNumber() {
		return hasExitOfThatNumber;
	}

	public void setHasExitOfThatNumber(Integer hasExitOfThatNumber) {
		this.hasExitOfThatNumber = hasExitOfThatNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOrginalCreditorId() {
		return orginalCreditorId;
	}

	public void setOrginalCreditorId(Integer orginalCreditorId) {
		this.orginalCreditorId = orginalCreditorId;
	}

	public Integer getOrginalOfThatId() {
		return orginalOfThatId;
	}

	public void setOrginalOfThatId(Integer orginalOfThatId) {
		this.orginalOfThatId = orginalOfThatId;
	}

	public double getOfThatAmount() {
		return ofThatAmount;
	}

	public void setOfThatAmount(double ofThatAmount) {
		this.ofThatAmount = ofThatAmount;
	}

	public ProductBidType getType() {
		return type;
	}

	public void setType(ProductBidType type) {
		this.type = type;
	}

	public ProductOfThatStatus getStatus() {
		return status;
	}

	public void setStatus(ProductOfThatStatus status) {
		this.status = status;
	}
}
