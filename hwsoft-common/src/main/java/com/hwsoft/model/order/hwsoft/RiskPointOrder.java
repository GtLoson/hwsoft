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
 * 风险金订单
 * @author tzh
 *
 */
@Entity
@Table(name="risk_point_order")
@Audited
public class RiskPointOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	
	/**
	 * 风险金订单
	 */
	@Column(name = "order_form_id",length=32)
	private String orderFormId;
	
	/**
	 * 还款订单
	 */
	@Column(name = "repayment_order_form_id",length=32)
	private String repaymentOrderFormId;
	
	@Column(name = "product_id")
	private Integer productId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_status",length=32,nullable = false)
	private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_type",length=32,nullable = false)
	private OrderType orderType ;
	
	@Column(name = "amount", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0")
	private double amount;
	
	@Column(name = "product_repay_record_id")
	private Integer productRepayRecordId;

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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Integer getProductRepayRecordId() {
		return productRepayRecordId;
	}

	public void setProductRepayRecordId(Integer productRepayRecordId) {
		this.productRepayRecordId = productRepayRecordId;
	}

	public String getRepaymentOrderFormId() {
		return repaymentOrderFormId;
	}

	public void setRepaymentOrderFormId(String repaymentOrderFormId) {
		this.repaymentOrderFormId = repaymentOrderFormId;
	}
	
}
