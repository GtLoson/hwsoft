/**
 * 
 */
package com.hwsoft.model.order.gongming;

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

import com.hwsoft.common.customer.Gender;
import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import org.hibernate.envers.Audited;

/**
 * @author tzh
 *
 */
@Entity
@Table(name="order_user_account")
@Audited
public class UserAccountOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "user_id",nullable=false)
	private Integer userId;
	
	@Column(name = "member_id",length=32)
	private String memberId;
	
	@Column(name = "order_form_id",length=32)
	private String orderFormId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_status",length=32,nullable = false)
	private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_type",length=32,nullable = false)
	private OrderType orderType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	@Column(name = "back_code",length=128)
	private String backCode;
	
	@Column(name = "back_msg",length=128)
	private String backMsg;
	
	@Column(name = "response",length=1024)
	private String response;
	@Column(name = "mobile",length=11)
	private String mobile;
	@Column(name = "id_card",length=18)
	private String idCard;
	@Column(name = "real_name",length=18)
	private String realName;
	@Column(name = "email",length=64)
	private String email;
	@Column(name = "nick_name",length=64)
	private String nickName;
	@Enumerated(EnumType.STRING)
	@Column(name = "gender",length=32)
	private Gender gender;
	@Column(name = "address",length=256)
	private String address;
	@Column(name = "education_level",length=64)
	private String educationLevel;
	@Column(name = "product_channel_id")
	private Integer productChannelId;
	@Column(name = "customer_sub_account_id")
	private Integer customerSubAccountId;
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
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}
	public Integer getProductChannelId() {
		return productChannelId;
	}
	public void setProductChannelId(Integer productChannelId) {
		this.productChannelId = productChannelId;
	}
	public Integer getCustomerSubAccountId() {
		return customerSubAccountId;
	}
	public void setCustomerSubAccountId(Integer customerSubAccountId) {
		this.customerSubAccountId = customerSubAccountId;
	}
	
}
