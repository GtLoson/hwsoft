/**
 * 
 */
package com.hwsoft.model.agreement;

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

import com.hwsoft.common.agreement.AgreementType;

/**
 * 合同模板
 * @author tzh
 *
 */
@Entity
@Table(name = "agreement_template")
public class AgreementTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1219538150470569603L;

	/***
	 * Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 合同名称
	 */
	@Column(name = "name", nullable = false, length = 64)
	private String name;
	/**
	 * 合同内容
	 */
	@Column(name = "agreement_content", nullable = false, columnDefinition="TEXT")
	private String agreementContent;
	/**
	 * 合同类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "agreement_type", nullable = false, length = 32)
	private AgreementType agreementType;
	/**
 	* 创建时间
 	*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	/**
 	* 修改时间
 	*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = false)
	private Date updateTime;
	
//	/**
//	 * 创建人
//	 */
//	@Column(name = "creater_name", nullable = false, length = 32)
//	private String createrName;
//	/**
//	 * 修改人
//	 */
//	@Column(name = "updater_name", nullable = false, length = 32)
//	private String updaterName;
	
	/**
	 * 是否可用
	 */
	@Column(name = "enable", nullable = true)
	private boolean enable;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAgreementContent() {
		return agreementContent;
	}
	public void setAgreementContent(String agreementContent) {
		this.agreementContent = agreementContent;
	}
	public AgreementType getAgreementType() {
		return agreementType;
	}
	public void setAgreementType(AgreementType agreementType) {
		this.agreementType = agreementType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
}
