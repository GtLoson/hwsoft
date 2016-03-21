/**
 * 
 */
package com.hwsoft.model.bank;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * @author tzh
 *
 */
@Entity
@Table(name="bank")
public class Bank implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7500659559410443606L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "bank_name", nullable = false)
	private String bankName;// 银行名

	@Column(name = "bank_pic_path", nullable = true)
	private String bankPicPath;// 银行图片路径

	@Column(name = "bank_pic_HD_path", nullable = true)
	private String bankHDPicPath;
	

	@Column(name = "bank_time_limit", nullable = true)
	private Double bankTimeLimit;// 银行单次扣款限额

	@Column(name = "bank_day_limit", nullable = true)
	private Double bankDayLimit;// 银行单日扣款限额

	@Column(name = "enable", nullable = true)
	private Boolean enable;// 是否可用

	@Column(name = "bank_code", nullable = false)
	private String bankCode;// 银行代码 购买的时候传的数据
	
	/**
	 * 渠道id
	 */
	@Column(name = "product_channel_id", nullable = false)
	private int productChannelId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankPicPath() {
		return bankPicPath;
	}

	public void setBankPicPath(String bankPicPath) {
		this.bankPicPath = bankPicPath;
	}

	public Double getBankTimeLimit() {
		return bankTimeLimit;
	}

	public void setBankTimeLimit(Double bankTimeLimit) {
		this.bankTimeLimit = bankTimeLimit;
	}

	public Double getBankDayLimit() {
		return bankDayLimit;
	}

	public void setBankDayLimit(Double bankDayLimit) {
		this.bankDayLimit = bankDayLimit;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public int getProductChannelId() {
		return productChannelId;
	}

	public void setProductChannelId(int productChannelId) {
		this.productChannelId = productChannelId;
	}

	public String getBankHDPicPath() {
		return bankHDPicPath;
	}

	public void setBankHDPicPath(String bankHDPicPath) {
		this.bankHDPicPath = bankHDPicPath;
	}
}
