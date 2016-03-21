package com.hwsoft.model.customer;

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

import org.hibernate.envers.Audited;

import com.hwsoft.common.customer.WithdrawalStatus;
import com.hwsoft.common.customer.WithdrawalType;


/**
 * 提现记录
 * @author tzh
 *
 */
@Entity
@Table(name="withdrawal_record")
@Audited
public class WithdrawalRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0", nullable = false)
	private double amount;
	
	@Column(name = "customer_id", nullable = false)
	private int customerId;
	
	@Column(name = "customer_sub_account_id", nullable = false)
	private int CustomerSubAccountId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "apply_time",nullable=false)
	private Date applyTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "audit_time")
	private Date auditTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "withdrawal_time")
	private Date WithdrawalTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 32,nullable=false)
	private WithdrawalStatus status;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 32,nullable=false)
	private WithdrawalType type;
	
	@Column(name = "user_bank_card_id",nullable = false)
	private Integer userBankCardId;
	
	@Column(name = "bank_card_number",length=32,nullable = false)
	private String bankCardNumber;
	
	@Column(name = "fee", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0")
	private double fee;
	/**
	 * 手续费出账账户
	 */
	@Column(name = "real_fee_cost_account")
	private int realFeeCostAccount;
	
	/**
	 * 备注
	 */
	@Column(name = "notes",length=1024)
	private String notes;
	
	/**
	 * 复核备注
	 */
	@Column(name = "withdrawal_notes",length=1024)
	private String withdrawalNotes;
	
	@Column(name = "audit_staff_id")
	private int auditStaffId;
	
	@Column(name = "audit_staff_name",length=32)
	private String auditStaffName;
	
	@Column(name = "withdrawal_staff_id")
	private int withdrawalStaffId;
	
	@Column(name = "withdrawal_staff_name",length=32)
	private String withdrawalStaffName;
	
	/**
	 * 是否再次处理
	 */
	@Column(name = "again", nullable = false)
	private boolean again = false;
	
	/**
	 * 上次提现记录id
	 */
	@Column(name = "last_withdrawal_record_id")
	private Integer lastWithdrawalRecordId;

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

	public WithdrawalStatus getStatus() {
		return status;
	}

	public void setStatus(WithdrawalStatus status) {
		this.status = status;
	}

	public WithdrawalType getType() {
		return type;
	}

	public void setType(WithdrawalType type) {
		this.type = type;
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

	public boolean isAgain() {
		return again;
	}

	public void setAgain(boolean again) {
		this.again = again;
	}

	public Integer getLastWithdrawalRecordId() {
		return lastWithdrawalRecordId;
	}

	public void setLastWithdrawalRecordId(Integer lastWithdrawalRecordId) {
		this.lastWithdrawalRecordId = lastWithdrawalRecordId;
	}
	
}
