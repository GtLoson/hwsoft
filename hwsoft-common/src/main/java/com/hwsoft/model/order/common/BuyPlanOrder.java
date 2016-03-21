/**
 * 
 */
package com.hwsoft.model.order.common;

import java.io.Serializable;
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

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import org.hibernate.envers.Audited;

/**
 * @author tzh
 *
 */
@Entity
@Table(name="order_buy_plan")
@Audited
public class BuyPlanOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3622879008744883567L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "user_id",nullable=false)
	private Integer userId;
	
	@Column(name = "member_id",length=32,nullable=false)
	private String memberId;
	
	@Column(name = "order_form_id",length=32)
	private String orderFormId;
	
	@Column(name = "product_id",nullable=false)
	private Integer productId;
	
	@Column(name = "third_product_id",length=32)
	private String thirdProductId;
	
	@Column(name = "user_bank_card_id")
	private Integer userBankCardId;
	
	@Column(name = "user_bank_card_number",length=32)
	private String userBankCardNumber;
	
	@Column(name = "buy_amount", scale = 2, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
	private double buyAmount;
	
	@Column(name = "buy_success_amount", scale = 2, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
	private double buySuccessAmount;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	@Column(name = "back_code",length=128)
	private String backCode;
	
	@Column(name = "back_msg",length=128)
	private String backMsg;
	
	@Column(name = "response",length=1024)
	private String response;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_status",length=32,nullable = false)
	private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_type",length=32,nullable = false)
	private OrderType orderType;
	
	@Column(name = "product_buyer_record_id")
	private Integer productBuyerRecordId;
	
	@Column(name = "buy_share_num")
	private Integer buyShareNum;
	
	@Column(name = "buy_success_share_num")
	private Integer buySuccessShareNum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getOrderFormId() {
		return orderFormId;
	}

	public void setOrderFormId(String orderFormId) {
		this.orderFormId = orderFormId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getThirdProductId() {
		return thirdProductId;
	}

	public void setThirdProductId(String thirdProductId) {
		this.thirdProductId = thirdProductId;
	}

	public Integer getUserBankCardId() {
		return userBankCardId;
	}

	public void setUserBankCardId(Integer userBankCardId) {
		this.userBankCardId = userBankCardId;
	}

	public String getUserBankCardNumber() {
		return userBankCardNumber;
	}

	public void setUserBankCardNumber(String userBankCardNumber) {
		this.userBankCardNumber = userBankCardNumber;
	}

	public double getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(double buyAmount) {
		this.buyAmount = buyAmount;
	}

	public double getBuySuccessAmount() {
		return buySuccessAmount;
	}

	public void setBuySuccessAmount(double buySuccessAmount) {
		this.buySuccessAmount = buySuccessAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public Integer getProductBuyerRecordId() {
		return productBuyerRecordId;
	}

	public void setProductBuyerRecordId(Integer productBuyerRecordId) {
		this.productBuyerRecordId = productBuyerRecordId;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Integer getBuyShareNum() {
		return buyShareNum;
	}

	public void setBuyShareNum(Integer buyShareNum) {
		this.buyShareNum = buyShareNum;
	}

	public Integer getBuySuccessShareNum() {
		return buySuccessShareNum;
	}

	public void setBuySuccessShareNum(Integer buySuccessShareNum) {
		this.buySuccessShareNum = buySuccessShareNum;
	}
	
}
