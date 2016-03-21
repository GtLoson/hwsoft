/**
 * 
 */
package com.hwsoft.model.point;

import org.hibernate.envers.Audited;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


/**
 * @author tzh
 *
 */
@Entity
@Table(name="customer_sub_account_point")
@Audited
public class CustomerSubAccountPoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2944239088332264966L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	/**
	 * 渠道子账户id
	 */
	@Column(name = "customer_sub_account_id", nullable = false,unique = true)
	private Integer customerSubAccountId;

	/**
	 * 可用余额
	 */
	@Column(name = "available_points", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0", nullable = false)
	private Double availablePoints;

	/**
	 * 冻结金额
	 */
	@Column(name = "frozen_points", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0", nullable = false)
	private Double frozenPoints;
	
	/**
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	
	/**
	 * 最后更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

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

	public Integer getCustomerSubAccountId() {
		return customerSubAccountId;
	}

	public void setCustomerSubAccountId(Integer customerSubAccountId) {
		this.customerSubAccountId = customerSubAccountId;
	}

	public Double getAvailablePoints() {
		return availablePoints;
	}

	public void setAvailablePoints(Double availablePoints) {
		this.availablePoints = availablePoints;
	}

	public Double getFrozenPoints() {
		return frozenPoints;
	}

	public void setFrozenPoints(Double frozenPoints) {
		this.frozenPoints = frozenPoints;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
