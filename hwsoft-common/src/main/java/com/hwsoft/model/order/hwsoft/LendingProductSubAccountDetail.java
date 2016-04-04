package com.hwsoft.model.order.hwsoft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.hwsoft.common.product.ProductBidType;

/**
 * 放款详情
 * @author tzh
 *
 */
@Entity
@Table(name="order_lending_product_sub_account_detail")
@Audited
public class LendingProductSubAccountDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "product_id",nullable=false)
	private int productId;
	
	@Column(name = "product_sub_account_id",nullable=false)
	private int productSubAccountId;
	
	@Column(name = "bid_order_form_id",length=32)
	private String bidOrderFormId;
	
	@Column(name = "amount", scale = 2, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
	private double amount;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "product_bid_type",length=32,nullable = false)
	private ProductBidType productBidType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductSubAccountId() {
		return productSubAccountId;
	}

	public void setProductSubAccountId(int productSubAccountId) {
		this.productSubAccountId = productSubAccountId;
	}

	public String getBidOrderFormId() {
		return bidOrderFormId;
	}

	public void setBidOrderFormId(String bidOrderFormId) {
		this.bidOrderFormId = bidOrderFormId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public ProductBidType getProductBidType() {
		return productBidType;
	}

	public void setProductBidType(ProductBidType productBidType) {
		this.productBidType = productBidType;
	}
	
}
