package com.hwsoft.vo.order;

import java.util.Date;
import java.util.List;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.util.enumtype.EnumUtil;


public class RepaymentProductOrderVo {

	private Integer id;
	
	private String orderFormId;
	
	private Integer productId;
	
	private String productName;
	
	private Date createTime;
	
	private String orderStatus;
	
	private String orderStatusValue;
	
	private String orderType ;
	
	private String orderTypeValue ;

	/**
	 * 利息
	 */
	private double interest;
	
	/**
	 * 本金
	 */
	private double principal;
	
	/**
	 * 罚息
	 */
	private double defaultInterest;
	
	private int staffId;
	
	private String staffName;
	
	private int productRepayRecordId;
	
	/**
	 * 期数
	 */
	private int phaseNumber;
	
	/**
	 * 应还总金额
	 */
	private double totalRepayAmount;
	
	private List<RepaymentProductDetailVo> detailVos;

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

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getDefaultInterest() {
		return defaultInterest;
	}

	public void setDefaultInterest(double defaultInterest) {
		this.defaultInterest = defaultInterest;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public int getProductRepayRecordId() {
		return productRepayRecordId;
	}

	public void setProductRepayRecordId(int productRepayRecordId) {
		this.productRepayRecordId = productRepayRecordId;
	}

	public int getPhaseNumber() {
		return phaseNumber;
	}

	public void setPhaseNumber(int phaseNumber) {
		this.phaseNumber = phaseNumber;
	}

	public double getTotalRepayAmount() {
		return totalRepayAmount;
	}

	public void setTotalRepayAmount(double totalRepayAmount) {
		this.totalRepayAmount = totalRepayAmount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<RepaymentProductDetailVo> getDetailVos() {
		return detailVos;
	}

	public void setDetailVos(List<RepaymentProductDetailVo> detailVos) {
		this.detailVos = detailVos;
	}
	
}
