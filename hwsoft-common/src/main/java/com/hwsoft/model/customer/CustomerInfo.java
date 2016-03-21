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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hwsoft.common.customer.Gender;

/**
 * 用户基本信息
 * @author tzh
 *
 */
@Entity
@Table(name="customer_info")
public class CustomerInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8917151272570120716L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 
	 */
	@Column(name = "customer_id",nullable=false)
	private Integer customerId;
	
	/**
	 * 真实姓名
	 */
	@Column(name = "real_name",length=255)
	private String realName;
	
	/**
	 * 生日
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date", nullable = true)
	private Date birthDay;
	
	/**
	 * 性别
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "gender",length=32)
	private Gender gender;

	/**
	 * 头像
	 */
	@Column(name = "vartar", length = 128)
	private String vartar;

	/**
	 * 地址
	 */
	@Column(name = "address", length = 255)
	private String address;
	
	/**
	 * 身份证所在省
	 */
	@Column(name = "province", length = 32)
	private String province;
	
	/**
	 * 身份证所在市
	 */
	@Column(name = "city", length = 32)
	private String city;
	
	/**
	 * 身份证认证时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "id_card_validate_time", nullable = true)
	private Date idCardValidateTime;
	
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getVartar() {
		return vartar;
	}

	public void setVartar(String vartar) {
		this.vartar = vartar;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getIdCardValidateTime() {
		return idCardValidateTime;
	}

	public void setIdCardValidateTime(Date idCardValidateTime) {
		this.idCardValidateTime = idCardValidateTime;
	}
	
}
