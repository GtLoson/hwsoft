package com.hwsoft.vo.order;

import java.util.Date;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.util.enumtype.EnumUtil;
import com.hwsoft.util.string.StringUtils;

/**
 * 流标订单vo对象
 * @author tzh
 *
 */
public class FailedProductOrderVo {

	private Integer id;
	
	private String orderFormId;
	
	private Integer productId;
	
	private String productName;
	
	private Date createTime;
	
	private String orderStatus;
	
	private String orderStatusValue;
	
	private String orderType ;
	
	private String orderTypeValue ;
	
	private int staffId;
	
	private String staffName;
	
	private String notes;

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
