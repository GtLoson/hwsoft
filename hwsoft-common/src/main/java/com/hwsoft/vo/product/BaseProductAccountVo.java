/**
 * 
 */
package com.hwsoft.vo.product;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tzh
 *
 */
public class BaseProductAccountVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2169694926409576104L;

	private Integer id;
	
	private String productName;
	
	private BigDecimal totalBuyAmount;
	
	private BigDecimal totalEarnings;
	
	private Integer productId;
	
	private BigDecimal todayTotalEarnings;



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

	public BigDecimal getTotalBuyAmount() {
		return totalBuyAmount;
	}

	public void setTotalBuyAmount(BigDecimal totalBuyAmount) {
		this.totalBuyAmount = totalBuyAmount;
	}

	public BigDecimal getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(BigDecimal totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public BigDecimal getTodayTotalEarnings() {
		return todayTotalEarnings;
	}

	public void setTodayTotalEarnings(BigDecimal todayTotalEarnings) {
		this.todayTotalEarnings = todayTotalEarnings;
	}
	
}
