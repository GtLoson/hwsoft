/**
 * 
 */
package com.hwsoft.model.customer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * @author tzh
 *
 */
public class CustomerBank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8792831145303876336L;

	
	/***
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	/**
	 * 用户id
	 */
	@Column(name = "customer_id", nullable = false)
	private Integer customerId;
	
	/**
	 * 渠道账户id
	 */
	@Column(name = "customer_sub_account_id", nullable = false)
	private Integer customerSubAccountId;
	
	/***
	 * 开户名
	 */
	@Column(name = "account_name", nullable = false, length = 32)
	private String accountName;
	
	/**
	 * 开户支行
	 */
	@Column(name = "subbranch", length = 64)
	private String subbranch; 
	
	/**
	 * 卡号
	 */
	@Column(name = "card_number", nullable = false, unique = true, length = 32)
	private String cardNumber;
	
	/**
	 * 银行类型关联
	 */
	@Column(name = "bank_id",nullable = false)
	private Integer bankId;
	
	/**
	 * 开户城市
	 */
	@Column(name = "city", length = 32)
	private String city;
	
	/**
	 * 开户省份
	 */
	@Column(name = "province", length = 32)
	private String province;
	
	/**
	 * 开户行代码
	 */
	@Column(name = "open_bankid", length = 16)
	private String openBankId;
	
	/**
	 * 开户银行地区
	 */
	@Column(name = "open_area_id", length = 32)
	private String openAreaId;
	
	/***
	 * 是否可用
	 */
	@Column(name = "enable", nullable = false)
	private Boolean enable;
	
	/**
	 * 是否绑定共鸣
	 */
	@Column(name="product_channelId", nullable = false)
	private Integer productChannelId;
	
	/**
	 * 是否默认
	 */
	@Column(name="is_default", nullable = false)
	private Boolean isDefault;
	
	@Version
	@Column(name = "version", nullable = false)
	private int version;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getSubbranch() {
		return subbranch;
	}

	public void setSubbranch(String subbranch) {
		this.subbranch = subbranch;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getOpenBankId() {
		return openBankId;
	}

	public void setOpenBankId(String openBankId) {
		this.openBankId = openBankId;
	}

	public String getOpenAreaId() {
		return openAreaId;
	}

	public void setOpenAreaId(String openAreaId) {
		this.openAreaId = openAreaId;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Integer getProductChannelId() {
		return productChannelId;
	}

	public void setProductChannelId(Integer productChannelId) {
		this.productChannelId = productChannelId;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Integer getCustomerSubAccountId() {
		return customerSubAccountId;
	}

	public void setCustomerSubAccountId(Integer customerSubAccountId) {
		this.customerSubAccountId = customerSubAccountId;
	}
	
}
