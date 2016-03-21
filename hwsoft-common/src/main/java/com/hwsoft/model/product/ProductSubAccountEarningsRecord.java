/**
 * 
 */
package com.hwsoft.model.product;

import org.hibernate.envers.Audited;

import java.io.Serializable;
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
 * 产品子账户收益记录（用户购买的产品的应收记录）
 * 不代表实收
 * @author tzh
 *
 */
@Entity
@Table(name="product_sub_account_earnings_record")
public class ProductSubAccountEarningsRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6974604140238915567L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 收益金额
	 */
	@Column(name = "amount", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0",nullable=false)
	private double amount;
	
	/**
	 * 罚息
	 */
	@Column(name = "default_interest", scale = 4, precision = 16, columnDefinition = "decimal(16,4) DEFAULT 0",nullable=false)
	private double defaultInterest = 0;
	
	/**
	 * 收益更新时间(平台更新时间)
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "earning_time", nullable = false)
	private Date earningTime;
	
	/**
	 * 产生收益的时间段
	 */
	@Column(name = "yield_period",length=64)
	private String yieldPeriod;
	
	/**
	 * 所属产品子账户
	 */
	@Column(name = "product_sub_account_id")
	private Integer productSubAccountId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getEarningTime() {
		return earningTime;
	}

	public void setEarningTime(Date earningTime) {
		this.earningTime = earningTime;
	}

	public String getYieldPeriod() {
		return yieldPeriod;
	}

	public void setYieldPeriod(String yieldPeriod) {
		this.yieldPeriod = yieldPeriod;
	}

	public Integer getProductSubAccountId() {
		return productSubAccountId;
	}

	public void setProductSubAccountId(Integer productSubAccountId) {
		this.productSubAccountId = productSubAccountId;
	}

	public double getDefaultInterest() {
		return defaultInterest;
	}

	public void setDefaultInterest(double defaultInterest) {
		this.defaultInterest = defaultInterest;
	}

	
}
