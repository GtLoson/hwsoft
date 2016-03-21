package com.hwsoft.model.order.hwsoft;

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

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;


/**
 * 充值订单
 * @author tzh
 *
 */
@Entity
@Table(name="order_recharge")
@Audited
public class RechargeOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "order_form_id",length=32)
	private String orderFormId;
	
	@Column(name = "pay_order_form_id",length=32)
	private String payOrderFormId;
	
	@Column(name = "product_channel_id",nullable=false)
	private Integer productChannelId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_status",length=32,nullable = false)
	private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_type",length=32,nullable = false)
	private OrderType orderType = OrderType.RECHARGE;
	
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

	@Column(name = "customer_id",nullable=false)
	private int customerId;
	
	@Column(name = "customer_sub_account_id",nullable=false)
	private int customerSubAccountId;
	
	@Column(name = "recharge_record_id",nullable=false)
	private int rechargeRecordId;
	
	@Column(name = "back_code",length=128)
	private String backCode;
	
	@Column(name = "back_msg",length=128)
	private String backMsg;
	
	@Column(name = "response",length=1024)
	private String response;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderFormId() {
		return orderFormId;
	}

	public void setOrderFormId(String orderFormId) {
		this.orderFormId = orderFormId;
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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
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

	public String getBackCode() {
		return backCode;
	}

	public void setBackCode(String backCode) {
		this.backCode = backCode;
	}

	public String getBackMsg() {
		return backMsg;
	}

	public void setBackMsg(String backMsg) {
		this.backMsg = backMsg;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public int getRealFeeCostAccount() {
		return realFeeCostAccount;
	}

	public void setRealFeeCostAccount(int realFeeCostAccount) {
		this.realFeeCostAccount = realFeeCostAccount;
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

	public int getRechargeRecordId() {
		return rechargeRecordId;
	}

	public void setRechargeRecordId(int rechargeRecordId) {
		this.rechargeRecordId = rechargeRecordId;
	}

	public String getPayOrderFormId() {
		return payOrderFormId;
	}

	public void setPayOrderFormId(String payOrderFormId) {
		this.payOrderFormId = payOrderFormId;
	}

}
