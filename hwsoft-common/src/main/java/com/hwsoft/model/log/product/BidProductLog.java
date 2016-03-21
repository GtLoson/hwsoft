package com.hwsoft.model.log.product;

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


import com.hwsoft.common.product.ProductBidType;

/**
 * 投标日志
 * @author tzh
 *
 */
@Entity
@Table(name="log_bid_product")
public class BidProductLog {

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	/**
	 * 
	 */
	@Column(name = "order_form_id",length=32,nullable=false)
	private String orderFormId;
	
	/**
	 * 投标类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "product_bid_type",length=32,nullable = false)
	private ProductBidType productBidType;
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id",nullable=false)
	private int productId;
	
	/**
	 * 产品子账户id
	 */
	@Column(name = "product_sub_account_id",nullable=false)
	private int productSubAccountId;
	
	/**
	 * 备注
	 */
	@Column(name = "notes",length=128,nullable=false)
	private String notes;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
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
	@Column(name = "share_num",nullable=false)
	private Integer shareNum;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderFormId() {
		return orderFormId;
	}

	public void setOrderFormId(String orderFormId) {
		this.orderFormId = orderFormId;
	}

	public ProductBidType getProductBidType() {
		return productBidType;
	}

	public void setProductBidType(ProductBidType productBidType) {
		this.productBidType = productBidType;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public int getProductSubAccountId() {
		return productSubAccountId;
	}

	public void setProductSubAccountId(int productSubAccountId) {
		this.productSubAccountId = productSubAccountId;
	}
	
}
