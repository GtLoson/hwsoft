/**
 * 
 */
package com.hwsoft.model.bank;

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

import com.hwsoft.common.bank.UserBankCardBindStatus;

/**
 * 用户银行卡
 * @author tzh
 *
 */
@Entity
@Table(name="user_bank_card")
public class UserBankCard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6732047049588201030L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "bank_id", nullable = false)
	private Integer bankId;//银行id 关联银行信息维表

	@Column(name = "bank_code",length=16, nullable = true)
	private String bankCode;// 银行代码 购买的时候传的数据
	
	@Column(name = "bank_name",length=32, nullable = true)
	private String bankName;// 银行名称 购买的时候传的数据
	
	/**
	 * 银行卡号码
	 */
	@Column(name = "bank_card_number",length=24, nullable = true)
	private String bankCardNumber;// 银行卡号
	
	/**
	 * 开户所在省份
	 */
	@Column(name = "bank_card_province",length=16, nullable = true)
	private String bankCardProvince;// 银行卡对应的开户行所在省份

	@Column(name = "bank_card_province_code",length=16, nullable = true)
	private String bankCardProvinceCode;
	
	/**
	 * 开户所在城市
	 */
	@Column(name = "bank_card_city", length=32,nullable = true)
	private String bankCardCity;// 银行卡对应的开户行所在城市

	@Column(name = "bank_card_city_code", length=32,nullable = true)
	private String bankCardCityCode;
	
	/**
	 * 支行名称
	 */
	@Column(name = "bank_branch_name", length=128,nullable = true)
	private String bankBranchName;//  支行名称

	/**
	 * 大额行号信息
	 */
	@Column(name = "prcptcd", length = 32,nullable = true)
	private String prcptcd;
	
	/**
	 * 预留手机号码
	 */
	@Column(name = "reserve_phone_number", length=11,nullable = true)
	private String reservePhoneNumber;// // 预留手机号

	@Column(name = "enable", nullable = false)
	private boolean enable;// 是否可用

	@Column(name = "customer_id", nullable = false)
	private Integer customerId;//客户id 关联客户信息表
	
	/**
	 * 渠道资金账户id
	 */
	@Column(name = "customer_sub_account_id", nullable = false)
	private Integer customerSubAccountId;
	
	/**
	 * 第三方id
	 */
	@Column(name = "member_id")
	private String memberId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;// // 操作时间
	
	/**
	 * 银行卡绑定状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "status",length=32,nullable=false)
	private UserBankCardBindStatus status;
	
	/**
	 * 连连支付银行卡签约协议编号
	 */
	@Column(name = "agreement_number",length=32)
	private String agreementNumber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardNumber() {
		return bankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	public String getBankCardProvince() {
		return bankCardProvince;
	}

	public void setBankCardProvince(String bankCardProvince) {
		this.bankCardProvince = bankCardProvince;
	}

	public String getBankCardCity() {
		return bankCardCity;
	}

	public void setBankCardCity(String bankCardCity) {
		this.bankCardCity = bankCardCity;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getReservePhoneNumber() {
		return reservePhoneNumber;
	}

	public void setReservePhoneNumber(String reservePhoneNumber) {
		this.reservePhoneNumber = reservePhoneNumber;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public UserBankCardBindStatus getStatus() {
		return status;
	}

	public void setStatus(UserBankCardBindStatus status) {
		this.status = status;
	}

	public String getPrcptcd() {
		return prcptcd;
	}

	public void setPrcptcd(String prcptcd) {
		this.prcptcd = prcptcd;
	}

	//TODO 是否默认，这个默认每个渠道最多有一个


	public String getBankCardProvinceCode() {
		return bankCardProvinceCode;
	}

	public void setBankCardProvinceCode(String bankCardProvinceCode) {
		this.bankCardProvinceCode = bankCardProvinceCode;
	}

	public String getBankCardCityCode() {
		return bankCardCityCode;
	}

	public void setBankCardCityCode(String bankCardCityCode) {
		this.bankCardCityCode = bankCardCityCode;
	}

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	@Override
	public String toString() {
		return "UserBankCard{" +
				"id=" + id +
				", bankId=" + bankId +
				", bankCode='" + bankCode + '\'' +
				", bankName='" + bankName + '\'' +
				", bankCardNumber='" + bankCardNumber + '\'' +
				", bankCardProvince='" + bankCardProvince + '\'' +
				", bankCardProvinceCode='" + bankCardProvinceCode + '\'' +
				", bankCardCity='" + bankCardCity + '\'' +
				", bankCardCityCode='" + bankCardCityCode + '\'' +
				", bankBranchName='" + bankBranchName + '\'' +
				", prcptcd='" + prcptcd + '\'' +
				", reservePhoneNumber='" + reservePhoneNumber + '\'' +
				", enable=" + enable +
				", customerId=" + customerId +
				", customerSubAccountId=" + customerSubAccountId +
				", memberId='" + memberId + '\'' +
				", createTime=" + createTime +
				", status=" + status +
				'}';
	}
}
