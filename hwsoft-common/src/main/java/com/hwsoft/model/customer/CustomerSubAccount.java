/**
 * 
 */
package com.hwsoft.model.customer;

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
import javax.persistence.Transient;

import com.hwsoft.common.customer.AccountStatus;
import org.hibernate.envers.Audited;

/**
 * 渠道账户
 * @author tzh
 *
 */
@Entity
@Table(name="customer_sub_account")
@Audited
public class CustomerSubAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5873314723141340017L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 所属用户
	 */
	@Column(name = "customer_id", nullable = false)
	private int customerId;
	
	/**
	 * 客户号
	 */
	@Column(name = "memner_id",length=64, nullable = false)
	private String memberId;
	
	/**
	 * 开户时间
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "reg_time")
	private Date regTime;
	
	/**
	 * 账户状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "status",length=32,nullable=false)
	private AccountStatus status;
	
	/**
	 * 所属产品渠道
	 */
	@Column(name = "product_channel_id", nullable = false)
	private int productChannelId;
	
	/**
	 * 所属用户
	 */
	@Transient
	private Customer customer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getProductChannelId() {
		return productChannelId;
	}

	public void setProductChannelId(int productChannelId) {
		this.productChannelId = productChannelId;
	}

	@Override
	public String toString() {
		return "CustomerSubAccount{" +
				"id=" + id +
				", customerId=" + customerId +
				", memberId='" + memberId + '\'' +
				", regTime=" + regTime +
				", status=" + status +
				", productChannelId=" + productChannelId +
				", customer=" + customer +
				'}';
	}
}