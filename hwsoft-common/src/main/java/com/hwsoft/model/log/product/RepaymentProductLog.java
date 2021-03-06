package com.hwsoft.model.log.product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 还款日志
 * @author tzh
 *
 */
@Entity
@Table(name="log_repayment_product")
public class RepaymentProductLog {
	/**
	 *id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	/**
	 * 订单编号
	 */
	@Column(name = "order_form_id",length=32,nullable=false)
	private String orderFormId;
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id",nullable=false)
	private int productId;
	
	
	@Column(name = "staff_id",nullable=false)
	private int staffId;
	
	@Column(name = "staff_name",length=32,nullable=false)
	private String staffName;
	
	@Column(name = "notes",length=128,nullable=false)
	private String notes;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
