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

import com.hwsoft.common.customer.RechargeRecordStatus;


/**
 * 充值记录
 * @author tzh
 *
 */
@Entity
@Table(name="recharge_record")
@Audited
public class RechargeRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Column(name = "customer_id",nullable=false)
	private int customerId;
	
	@Column(name = "customer_sub_account_id",nullable=false)
	private int customerSubAccountId;
	
	@Column(name = "product_channel_id",nullable=false)
	private Integer productChannelId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "arrive_time")
	private Date arriveTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status",length=32,nullable = false)
	private RechargeRecordStatus status;
	
	@Column(name = "user_bank_card_id")
	private Integer userBankCardId;
	
	@Column(name = "bank_card_number",length=32)
	private String bankCardNumber;

	@Column(name = "bank_code",length=16)
	private String bankCode;
	
	@Column(name = "amount", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0")
	private double amount;
	
	@Column(name = "success_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0")
	private double successAmount;
	
	@Column(name = "fee", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0")
	private double fee;
	/**
	 * 手续费出账账户
	 */
	@Column(name = "real_fee_cost_account",nullable = false)
	private int realFeeCostAccount;
	
	@Column(name = "reason",length=128)
	private String reason;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public RechargeRecordStatus getStatus() {
		return status;
	}

	public void setStatus(RechargeRecordStatus status) {
		this.status = status;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public int getRealFeeCostAccount() {
		return realFeeCostAccount;
	}

	public void setRealFeeCostAccount(int realFeeCostAccount) {
		this.realFeeCostAccount = realFeeCostAccount;
	}
	
}
