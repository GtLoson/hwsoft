package com.hwsoft.vo.order;

public class RepaymentProductDetailVo {
	
	private int id;
	
	private int productId;
	
	private int productSubAccountId;
	
	private int customerId;
	
	private String mobile;
	
	private String realName;
	
	/**
	 * 利息
	 */
	private double interest;
	
	/**
	 * 本金
	 */
	private double principal;
	
	/**
	 * 罚息
	 */
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

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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
