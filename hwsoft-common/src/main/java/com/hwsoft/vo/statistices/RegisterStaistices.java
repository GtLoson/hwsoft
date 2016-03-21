package com.hwsoft.vo.statistices;

import java.io.Serializable;

/**
 * 注册统计
 * @author tzh
 *
 */
public class RegisterStaistices implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4033310782584855680L;


	private Integer channelId;
	
	
	private String channelName;
	
	private String statisticesDate;
	
	private int count;
	
	private double orderAmount;
	
	private int orderNumber;
	
	private double orderPrice;

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getStatisticesDate() {
		return statisticesDate;
	}

	public void setStatisticesDate(String statisticesDate) {
		this.statisticesDate = statisticesDate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	
}
