/**
 * 
 */
package com.hwsoft.model.product;

import java.io.Serializable;
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
import javax.persistence.Version;

import com.hwsoft.common.product.ProductSubAccountPointLogType;

/**
 * @author tzh
 *
 */
@Entity
@Table(name="product_sub_account_point_log")
public class ProductSubAccountPointLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5492420798089544781L;
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	/**
	 * 操作用户
	 */
	@Column(name = "customer_id", nullable = false)
	private Integer customerId;
	
	/**
	 * 渠道账户id
	 */
	@Column(name = "customer_sub_account_id")
	private Integer customerSubAccountId;
	
	/**
	 * 产品子账户id
	 */
	@Column(name = "product_sub_account_id")
	private Integer productSubAccountId;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;

	/**
	 * 操作金额
	 */
	@Column(name = "amount", scale = 2, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0", nullable = false)
	private Double amount;

	/**
	 * 备注
	 */
	@Column(name = "notes", length = 128, nullable=false)
	private String notes;

	/**
	 * 所属产品id
	 */
	@Column(name = "product_id")
	private Integer productId;


	/**
	 * 资金转出方
	 */
	@Column(name = "from_customer_id")
	private Integer fromCustomer;

	/**
	 * 资金转入方
	 */
	@Column(name = "to_customer_id")
	private Integer toCustomer;
	
	/**
	 * 可用金额（暂时无用）
	 *//*
	private double availableAmount;
	
	*//**
	 * 冻结金额（暂时无用）
	 *//*
	private double freezeAmount;*/
	
	/**
	 * 资金记录类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 64)
	private ProductSubAccountPointLogType type;
	
	/**
	 * 备注的资金记录转出方（如果为平衡金转出，则fromUser=平衡金 realFromUser=真实转出方）
	 */
	@Column(name = "realy_from_customer_id")
	private Integer realyFromCustomerId;

	
	/**
	 * hibernate 控制版本号
	 */
	@Version
	@Column(name = "version", nullable = false)
	private int version;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	public Integer getCustomerSubAccountId() {
		return customerSubAccountId;
	}


	public void setCustomerSubAccountId(Integer customerSubAccountId) {
		this.customerSubAccountId = customerSubAccountId;
	}


	public Integer getProductSubAccountId() {
		return productSubAccountId;
	}


	public void setProductSubAccountId(Integer productSubAccountId) {
		this.productSubAccountId = productSubAccountId;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}


	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	public Integer getFromCustomer() {
		return fromCustomer;
	}


	public void setFromCustomer(Integer fromCustomer) {
		this.fromCustomer = fromCustomer;
	}


	public Integer getToCustomer() {
		return toCustomer;
	}


	public void setToCustomer(Integer toCustomer) {
		this.toCustomer = toCustomer;
	}


	public ProductSubAccountPointLogType getType() {
		return type;
	}


	public void setType(ProductSubAccountPointLogType type) {
		this.type = type;
	}


	public Integer getRealyFromCustomerId() {
		return realyFromCustomerId;
	}


	public void setRealyFromCustomerId(Integer realyFromCustomerId) {
		this.realyFromCustomerId = realyFromCustomerId;
	}


	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}
	
}
