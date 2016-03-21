package com.hwsoft.model.order.hwsoft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="order_repayment_product_detail")
public class RepaymentProductDetail {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "product_id",nullable=false)
	private int productId;
	
	@Column(name = "product_sub_account_id",nullable=false)
	private int productSubAccountId;
	
	/**
	 * 利息
	 */
	@Column(name = "interest", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0")
	private double interest;
	
	/**
	 * 本金
	 */
	@Column(name = "principal", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0")
	private double principal;
	
	/**
	 * 罚息
	 */
	@Column(name = "default_interest", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0")
	private double defaultInterest;

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

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getDefaultInterest() {
		return defaultInterest;
	}

	public void setDefaultInterest(double defaultInterest) {
		this.defaultInterest = defaultInterest;
	}
	
}
