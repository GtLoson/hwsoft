package com.hwsoft.vo.order;

import java.io.Serializable;
import java.util.Date;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.util.enumtype.EnumUtil;
import com.hwsoft.util.string.StringUtils;

public class AccountOrderVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6158006357041671678L;

	private Integer id;
	
	private Integer userId;
	
	private String memberId;
	
	private String orderFormId;
	
	private String orderStatus;
	
	private String orderStatusValue;
	
	private Date createTime;
	
	private String backCode;
	
	private String backMsg;
	
	private String response;
	
	private String mobile;
	
	private String idCard;
	
	private String realName;
	
	private Integer productChannelId;
	
	private String productChannelName;

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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
		if(!StringUtils.isEmpty(orderStatus)){
			this.orderStatusValue = EnumUtil.getEnumByName(orderStatus, OrderStatus.class).toString();
		}
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

	public Integer getProductChannelId() {
		return productChannelId;
	}

	public void setProductChannelId(Integer productChannelId) {
		this.productChannelId = productChannelId;
	}

	public String getProductChannelName() {
		return productChannelName;
	}

	public void setProductChannelName(String productChannelName) {
		this.productChannelName = productChannelName;
	}

	public String getOrderStatusValue() {
		return orderStatusValue;
	}

	public void setOrderStatusValue(String orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
	}
	
}
