package com.hwsoft.vo.customer;

import java.util.Date;

import com.hwsoft.common.customer.RechargeRecordStatus;
import com.hwsoft.util.enumtype.EnumUtil;

public class RechargeRecordVo {

	private Integer id;
	
	private int customerId;
	
	private int customerSubAccountId;
	
	private String realName;
	
	private String mobile;
	
	private Integer productChannelId;
	
	private Date createTime;
	
	private Date arriveTime;
	
	private String status;
	
	private String statusValue;
	
	private Integer userBankCardId;
	
	private String bankCardNumber;

	private String bankCode;
	
	private double amount;
	
	private double successAmount;
	
	private double fee;
	
	private int realFeeCostAccount;
	
	private String reason;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getCustomerSubAccountId() {
		return customerSubAccountId;
	}

	public void setCustomerSubAccountId(int customerSubAccountId) {
		this.customerSubAccountId = customerSubAccountId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getProductChannelId() {
		return productChannelId;
	}

	public void setProductChannelId(Integer productChannelId) {
		this.productChannelId = productChannelId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		RechargeRecordStatus rechargeRecordStatus = EnumUtil.getEnumByName(status, RechargeRecordStatus.class);
		if(null != rechargeRecordStatus){
			statusValue = rechargeRecordStatus.toString();
		}
			
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public Integer getUserBankCardId() {
		return userBankCardId;
	}

	public void setUserBankCardId(Integer userBankCardId) {
		this.userBankCardId = userBankCardId;
	}

	public String getBankCardNumber() {
		return bankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(double successAmount) {
		this.successAmount = successAmount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public int getRealFeeCostAccount() {
		return realFeeCostAccount;
	}

	public void setRealFeeCostAccount(int realFeeCostAccount) {
		this.realFeeCostAccount = realFeeCostAccount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
