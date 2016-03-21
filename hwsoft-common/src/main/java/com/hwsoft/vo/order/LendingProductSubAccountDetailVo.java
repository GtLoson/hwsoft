package com.hwsoft.vo.order;

import com.hwsoft.common.product.ProductBidType;
import com.hwsoft.util.enumtype.EnumUtil;
import com.hwsoft.util.string.StringUtils;


public class LendingProductSubAccountDetailVo {

	private int id;
	
	private int productId;
	
	private int productSubAccountId;
	
	private int customerId;
	
	private String mobile;
	
	private String realName;
	
	private String bidOrderFormId;
	
	private double amount;
	
	private String productBidType;
	
	private String productBidTypeValue;

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

	public String getProductBidType() {
		return productBidType;
	}

	public void setProductBidType(String productBidType) {
		this.productBidType = productBidType;
		if(!StringUtils.isEmpty(productBidType)){
			this.productBidTypeValue = EnumUtil.getEnumByName(productBidType, ProductBidType.class).toString();
		}
	}

	public String getProductBidTypeValue() {
		return productBidTypeValue;
	}

	public void setProductBidTypeValue(String productBidTypeValue) {
		this.productBidTypeValue = productBidTypeValue;
	}
	
}
