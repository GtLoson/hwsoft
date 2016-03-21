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
 * 产品协议模板
 * @author tzh
 *
 */
@Entity
@Table(name="product_agreement_template")
public class ProductAgreementTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 357921636424089138L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 所属产品
	 */
	@Column(name = "product_id", nullable = false)
	private Integer productId;
	
	/**
	 * 合同内容
	 */
	@Column(name = "agreement_content", nullable = false,columnDefinition="TEXT")
	private String agreementContent;

	/**
	 * 合同id
	 */
	@Column(name = "agreement_id", nullable = false)
	private Integer agreemenId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getAgreementContent() {
		return agreementContent;
	}

	public void setAgreementContent(String agreementContent) {
		this.agreementContent = agreementContent;
	}

	public Integer getAgreemenId() {
		return agreemenId;
	}

	public void setAgreemenId(Integer agreemenId) {
		this.agreemenId = agreemenId;
	}
	
}
