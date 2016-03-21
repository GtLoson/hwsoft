package com.hwsoft.model.order.hwsoft;

import com.hwsoft.common.order.OrderStatus;
import com.hwsoft.common.order.OrderType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 放款订单
 * @author tzh
 *
 */
@Entity
@Table(name="order_lending_product")
public class LendingProductOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "order_form_id",length=32)
	private String orderFormId;
	
	@Column(name = "product_id",nullable=false)
	private Integer productId;
	
	/**
	 * 
	 */
	@Column(name = "borrow_customer_id",nullable=false)
	private int borrowCustomerId;
	
	@Column(name = "borrow_customer_sub_account_id",nullable=false)
	private int borrowCustomerSubAccountId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_status",length=32,nullable = false)
	private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_type",length=32,nullable = false)
	private OrderType orderType = OrderType.LENDING_hwsoft_LOAN;
	
	/**
	 * 手续费分账信息
	 */
	@OneToMany(targetEntity = LendingProductLedgerDetail.class, cascade = { CascadeType.REFRESH,CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "lending_product_order_id")
	private List<LendingProductLedgerDetail> ledgerDetails;
	
	/**
	 * 放款信息
	 */
	@OneToMany(targetEntity = LendingProductSubAccountDetail.class, cascade = { CascadeType.REFRESH,CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "lending_product_order_id")
	private List<LendingProductSubAccountDetail>  subAccountDetails;
	
	@Column(name = "amount", scale = 2, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
	private double amount;

	@Column(name = "open_amount", scale = 2, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
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

	public List<LendingProductLedgerDetail> getLedgerDetails() {
		return ledgerDetails;
	}

	public void setLedgerDetails(List<LendingProductLedgerDetail> ledgerDetails) {
		this.ledgerDetails = ledgerDetails;
	}

	public List<LendingProductSubAccountDetail> getSubAccountDetails() {
		return subAccountDetails;
	}

	public void setSubAccountDetails(
			List<LendingProductSubAccountDetail> subAccountDetails) {
		this.subAccountDetails = subAccountDetails;
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

	public int getBorrowCustomerId() {
		return borrowCustomerId;
	}

	public void setBorrowCustomerId(int borrowCustomerId) {
		this.borrowCustomerId = borrowCustomerId;
	}

	public int getBorrowCustomerSubAccountId() {
		return borrowCustomerSubAccountId;
	}

	public void setBorrowCustomerSubAccountId(int borrowCustomerSubAccountId) {
		this.borrowCustomerSubAccountId = borrowCustomerSubAccountId;
	}
	
}
