package com.hwsoft.vo.order;

import java.io.Serializable;
import java.util.Date;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.util.enumtype.EnumUtil;
import com.hwsoft.util.string.StringUtils;

public class BuyOrderVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1612890666465755337L;

	/**
	 * id
	 */
	private int id;
	
	/**
	 * 订单编号
	 */
	private String orderFormId;
	
	/**
	 * 产品id
	 */
	private int productId;
	
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * 用户id
	 */
	private int userId;
	
	/**
	 * 用户姓名
	 */
	private String realName;
	
	/**
	 * 用户电话
	 */
	private String mobile;
	
	/**
	 * 够买卡号
	 */
	private String bankCardNumber;
	
	/**
	 * 够买银行
	 */
	private String bankName;
	
	/**
	 * 银行id
	 */
	private Integer bankId;
	
	/**
	 * 申购金额
	 */
	private double buyAmount;

	/**
	 * 成功够买金额
	 */
	private double buySuccessAmount;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 订单状态
	 */
	private String orderStatus;
	
	/**
	 * 订单状态value
	 */
	private String orderStatusValue;
	
	/**
	 * 响应描述
	 */
	private String backMsg;
	
	/**
	 * 响应代码
	 */
	private String backCode;
	
	/**
	 * 响应内容
	 */
	private String response;
	
	private String orderType;

	
	private String orderTypeValue;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderFormId() {
		return orderFormId;
	}

	public void setOrderFormId(String orderFormId) {
		this.orderFormId = orderFormId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
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

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
		OrderStatus orderStatus2 = EnumUtil.getEnumByName(orderStatus, OrderStatus.class);
		if(null != orderStatus2){
			this.orderStatusValue = orderStatus2.toString();
		}
	}

	public String getOrderStatusValue() {
		return orderStatusValue;
	}

	public void setOrderStatusValue(String orderStatusValue) {
		this.orderStatusValue = orderStatusValue;
	}

	public String getBackMsg() {
		return backMsg;
	}

	public void setBackMsg(String backMsg) {
		this.backMsg = backMsg;
	}

	public String getBackCode() {
		return backCode;
	}

	public void setBackCode(String backCode) {
		this.backCode = backCode;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
		
		OrderType orderType2 = EnumUtil.getEnumByName(orderType, OrderType.class);
		if(null != orderType2){
			this.orderTypeValue = orderType2.toString();
		}
	}

	public String getOrderTypeValue() {
		return orderTypeValue;
	}

	public void setOrderTypeValue(String orderTypeValue) {
		this.orderTypeValue = orderTypeValue;
	}
	
	
}
