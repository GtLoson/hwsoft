package com.hwsoft.vo.order;

import java.io.Serializable;
import java.util.Date;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.util.enumtype.EnumUtil;
import com.hwsoft.util.string.StringUtils;

public class BindBankCardOrderVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1196169906685854727L;

	private Integer id;
	
	private Integer userId;
	
	private String realName;
	
	private String mobile;
	
	private String memberId;
	
	private String orderFormId;
	
	private String orderStatus;
	
	private String orderStatusValue;
	
	private Integer userBankCardId;
	
	private String bankCardNumber;

	private String bankName;
	
	private String bankPhoneNumber;
	
	private String province;
	
	private String city;
	
	private String bankBranchName;
	
	private Date createTime;
	
	private String backCode;
	
	private String backMsg;
	
	private String response;

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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
		if(!StringUtils.isEmpty(orderStatus)){
			orderStatusValue = EnumUtil.getEnumByName(orderStatus, OrderStatus.class).toString(); 
		}
	}

	public String getOrderStatusValue() {
		return orderStatusValue;
	}

	public void setOrderStatusValue(String orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankPhoneNumber() {
		return bankPhoneNumber;
	}

	public void setBankPhoneNumber(String bankPhoneNumber) {
		this.bankPhoneNumber = bankPhoneNumber;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
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
	
}
