/**
 * 
 */
package com.hwsoft.model.validate;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hwsoft.common.validate.ValidateType;

/**
 * @author tzh
 *
 */
@Entity
@Table(name = "mobile_validate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)  
public class MobileValidate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 787813009365307315L;
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 手机号码
	 */
	@Column(name = "mobile", nullable = false, length=11,unique=true)
	private String mobile;
	
	/**
	 * 手机验证码
	 */
	@Column(name = "code", nullable = false, length=6)
	private String code;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "send_time", nullable = false)
	private Date sendTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "validate_type", nullable = false, length = 32)
	private ValidateType validateType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public ValidateType getValidateType() {
		return validateType;
	}

	public void setValidateType(ValidateType validateType) {
		this.validateType = validateType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
