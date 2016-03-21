package com.hwsoft.vo.customer;

import java.util.Date;

import com.hwsoft.common.customer.WithdrawalStatus;
import com.hwsoft.common.customer.WithdrawalType;
import com.hwsoft.util.enumtype.EnumUtil;


public class WithdrawalRecordVo {
	
	private int id;
	
	private double amount;
	
	private int customerId;
	
	private String mobile;
	
	private String realName;
	
	private int CustomerSubAccountId;
	
	private Date applyTime;

	private Date auditTime;
	
	private Date WithdrawalTime;
	
	private String status;
	
	private String statusValue;
	
	private String type;
	
	private String typeValue;
	
	/**
	 * 备注
	 */
	private String notes;
	
	private String withdrawalNotes;
	
	private int auditStaffId;
	
	private String auditStaffName;
	
	private int withdrawalStaffId;
	
	private String withdrawalStaffName;
	
	private boolean again;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public int getCustomerSubAccountId() {
		return CustomerSubAccountId;
	}

	public void setCustomerSubAccountId(int customerSubAccountId) {
		CustomerSubAccountId = customerSubAccountId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Date getWithdrawalTime() {
		return WithdrawalTime;
	}

	public void setWithdrawalTime(Date withdrawalTime) {
		WithdrawalTime = withdrawalTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		WithdrawalStatus withdrawalStatus = EnumUtil.getEnumByName(status, WithdrawalStatus.class);
		if(null != withdrawalStatus){
			statusValue = withdrawalStatus.toString();
		}
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		WithdrawalType withdrawalType = EnumUtil.getEnumByName(type, WithdrawalType.class);
		
		if(null != withdrawalType){
			typeValue = withdrawalType.toString();
		}
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getAuditStaffId() {
		return auditStaffId;
	}

	public void setAuditStaffId(int auditStaffId) {
		this.auditStaffId = auditStaffId;
	}

	public String getAuditStaffName() {
		return auditStaffName;
	}

	public void setAuditStaffName(String auditStaffName) {
		this.auditStaffName = auditStaffName;
	}

	public int getWithdrawalStaffId() {
		return withdrawalStaffId;
	}

	public void setWithdrawalStaffId(int withdrawalStaffId) {
		this.withdrawalStaffId = withdrawalStaffId;
	}

	public String getWithdrawalStaffName() {
		return withdrawalStaffName;
	}

	public void setWithdrawalStaffName(String withdrawalStaffName) {
		this.withdrawalStaffName = withdrawalStaffName;
	}

	public String getWithdrawalNotes() {
		return withdrawalNotes;
	}

	public void setWithdrawalNotes(String withdrawalNotes) {
		this.withdrawalNotes = withdrawalNotes;
	}

	public boolean isAgain() {
		return again;
	}

	public void setAgain(boolean again) {
		this.again = again;
	}
	
}
