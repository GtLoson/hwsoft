package com.hwsoft.model.order.hwsoft;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;
import com.hwsoft.util.math.CalculateUtil;


/**
 * 还款订单
 * @author tzh
 *
 */
@Entity
@Table(name="order_repayment_product")
public class RepaymentProductOrder {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "order_form_id",length=32)
	private String orderFormId;
	
	@Column(name = "product_id",nullable=false)
	private Integer productId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_status",length=32,nullable = false)
	private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_type",length=64,nullable = false)
	private OrderType orderType ;

	/**
	 * 利息
	 */
	@Column(name = "interest", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0")
	private double interest;
	
	/**
	 * 本金
	 */
	@Column(name = "principal", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0")
	private double principal;
	
	/**
	 * 罚息
	 */
	@Column(name = "default_interest", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0")
	private double defaultInterest;
	
	@Column(name = "staff_id")
	private int staffId;
	
	@Column(name = "staff_name",length=32)
	private String staffName;
	
	@OneToMany(targetEntity = RepaymentProductDetail.class, cascade = { CascadeType.REFRESH,CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "repayment_product_order_id")
	private List<RepaymentProductDetail> details;
	
	@Column(name = "product_repay_record_id")
	private int productRepayRecordId;
	
	/**
	 * 应还总金额
	 */
	@Transient
	private double totalRepayAmount;

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

	public List<RepaymentProductDetail> getDetails() {
		return details;
	}

	public void setDetails(List<RepaymentProductDetail> details) {
		this.details = details;
	}

	public double getTotalRepayAmount() {
		return CalculateUtil.doubleAdd(principal,CalculateUtil.doubleAdd(interest, defaultInterest));
	}

	public void setTotalRepayAmount(double totalRepayAmount) {
		this.totalRepayAmount = totalRepayAmount;
	}

	public int getProductRepayRecordId() {
		return productRepayRecordId;
	}

	public void setProductRepayRecordId(int productRepayRecordId) {
		this.productRepayRecordId = productRepayRecordId;
	}

}
