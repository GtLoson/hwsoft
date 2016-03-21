/**
 * 
 */
package com.hwsoft.model.log;

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

import com.hwsoft.common.point.CustomerSubAccountPointType;

/**
 * 渠道子账户资金记录
 * @author tzh
 *
 */
@Entity
@Table(name="customer_sub_account_point_log")
@Audited
public class CustomerSubAccountPointLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	/**
	 * 操作金额
	 */
	@Column(name = "amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
	private double amount;
	
	/**
	 * 可用余额
	 */
	@Column(name = "available_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
	private double availableAmount;
	
	/**
	 * 冻结金额
	 */
	@Column(name = "freezon_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
	private double freezonAmount;
	
	/**
	 * 操作时间
	 */
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	/**
	 * 操作类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "order_type",length=32,nullable = false)
	private CustomerSubAccountPointType type;
	
	/**
	 * 备注
	 */
	@Column(name = "remark",length=128)
	private String remark;
	
	/**
	 * 订单编号（可能是充值订单，可能是投标订单根据操作类型而定）
	 */
	@Column(name = "order_form_id",length=128)
	private String orderFormId;
	
	/**
	 * 操作账户
	 */
	@Column(name = "customer_id",nullable=false)
	private int CustomerId;
	
	/**
	 * 操作子账户
	 */
	@Column(name = "customer_sub_account_id",nullable=false)
	private int customerSubAccountId;
	
	/**
	 * 出账子账户
	 */
	@Column(name = "from_customer_sub_account_id",nullable=false)
	private int fromCustomerSubAccountId;
	
	/**
	 * 真实出账子账户
	 */
	@Column(name = "real_from_customer_sub_account_id")
	private Integer realFromCustomerSubAccountId;
	
	/**
	 * 如账子账户
	 */
	@Column(name = "to_customer_sub_account_id")
	private Integer toCustomerSubAccountId;
	
	/**
	 * 是否是增加
	 */
	@Column(name = "plus", nullable = true)
	private boolean plus;
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Integer productId;
	
	
	@Column(name = "staff_id")
	private Integer staffId;
	
	@Column(name = "staff_name", length=64)
	private String staffName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public CustomerSubAccountPointType getType() {
		return type;
	}

	public void setType(CustomerSubAccountPointType type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderFormId() {
		return orderFormId;
	}

	public void setOrderFormId(String orderFormId) {
		this.orderFormId = orderFormId;
	}

	public int getCustomerSubAccountId() {
		return customerSubAccountId;
	}

	public void setCustomerSubAccountId(int customerSubAccountId) {
		this.customerSubAccountId = customerSubAccountId;
	}

	public int getFromCustomerSubAccountId() {
		return fromCustomerSubAccountId;
	}

	public void setFromCustomerSubAccountId(int fromCustomerSubAccountId) {
		this.fromCustomerSubAccountId = fromCustomerSubAccountId;
	}

	public Integer getToCustomerSubAccountId() {
		return toCustomerSubAccountId;
	}

	public void setToCustomerSubAccountId(Integer toCustomerSubAccountId) {
		this.toCustomerSubAccountId = toCustomerSubAccountId;
	}

	public boolean isPlus() {
		return plus;
	}

	public void setPlus(boolean plus) {
		this.plus = plus;
	}

	public int getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(int customerId) {
		CustomerId = customerId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public double getFreezonAmount() {
		return freezonAmount;
	}

	public void setFreezonAmount(double freezonAmount) {
		this.freezonAmount = freezonAmount;
	}

	public Integer getRealFromCustomerSubAccountId() {
		return realFromCustomerSubAccountId;
	}

	public void setRealFromCustomerSubAccountId(Integer realFromCustomerSubAccountId) {
		this.realFromCustomerSubAccountId = realFromCustomerSubAccountId;
	}

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
}
