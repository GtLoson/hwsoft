/**
 * 
 */
package com.hwsoft.model.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品可用银行卡
 * @author tzh
 *
 */
@Entity
@Table(name="product_bank")
public class ProductBank implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2199737518793158591L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	/**
	 * 银行卡id
	 */
	@Column(name = "bank_id", nullable = false)
	private int bankId;
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id", nullable = false)
	private int productI;
	
	/**
	 * 是否可用
	 */
	@Column(name = "enable", nullable = true)
	private boolean enable;
	
	/**
	 * 银行是否可用
	 */
	@Column(name = "bank_enable", nullable = true)
	private boolean bankEnable;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public int getProductI() {
		return productI;
	}

	public void setProductI(int productI) {
		this.productI = productI;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isBankEnable() {
		return bankEnable;
	}

	public void setBankEnable(boolean bankEnable) {
		this.bankEnable = bankEnable;
	}
	
}
