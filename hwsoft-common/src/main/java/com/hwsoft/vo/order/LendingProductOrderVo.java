package com.hwsoft.vo.order;

import java.util.Date;
import java.util.List;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.util.enumtype.EnumUtil;


/**
 * 放款请求vo
 * @author tzh
 *
 */
public class LendingProductOrderVo {
	
	private Integer id;
	
	private String orderFormId;
	
	private Integer productId;
	
	private String productName;
	
	private Date createTime;
	
	private String orderStatus;
	
	private String orderStatusValue;
	
	private String orderType ;
	
	private String orderTypeValue ;

	private List<LendingProductSubAccountDetailVo> detailVos;
	
	private double amount;
	
	private double openAmount;
	
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

	public List<LendingProductSubAccountDetailVo> getDetailVos() {
		return detailVos;
	}

	public void setDetailVos(List<LendingProductSubAccountDetailVo> detailVos) {
		this.detailVos = detailVos;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getOpenAmount() {
		return openAmount;
	}

	public void setOpenAmount(double openAmount) {
		this.openAmount = openAmount;
	}

}
