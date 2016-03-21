package com.hwsoft.vo.customer;

public class SystemCustomerVo {
	
	/**
	 * 
	 */
	private int id;

	/**
	 * 可用余额
	 */
	private double totalAvailable;
	
	/**
	 * 总冻结金额
	 */
	private double totalFreezen;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 来源
	 */
	private String customerSource;
	
	/**
	 * 来源值
	 */
	private String customerSourceValue;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTotalAvailable() {
		return totalAvailable;
	}

	public void setTotalAvailable(double totalAvailable) {
		this.totalAvailable = totalAvailable;
	}

	public double getTotalFreezen() {
		return totalFreezen;
	}

	public void setTotalFreezen(double totalFreezen) {
		this.totalFreezen = totalFreezen;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getCustomerSourceValue() {
		return customerSourceValue;
	}

	public void setCustomerSourceValue(String customerSourceValue) {
		this.customerSourceValue = customerSourceValue;
	}
	
	
}
