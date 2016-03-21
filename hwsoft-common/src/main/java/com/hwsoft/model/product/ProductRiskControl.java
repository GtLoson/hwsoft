package com.hwsoft.model.product;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 风控信息
 */
@Entity
@Table(name="product_risk_control")
public class ProductRiskControl {

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
    private Integer id;


	/**
	 * 产品id
	 */
	@Column(name = "product_id", nullable = false,unique=true)
    private Integer productId;

	/**
	 * 风控信息
	 */
    @Column(name = "risk_control_description", columnDefinition="TEXT")
    private String riskControlDescription;
    
    /**
     * 图片路径集合
     */
	@Column(name = "pic_paths", columnDefinition="TEXT")
    private String picPaths;

	/**
	 * 抵押权利人
	 */
	@Column(name = "mortgager",length = 64)
	private String mortgager;

	/**
	 * 担保公司
	 */
	@Column(name = "guarantee_company",length = 64)
	private String guaranteeCompany;

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

	public String getRiskControlDescription() {
		return riskControlDescription;
	}

	public void setRiskControlDescription(String riskControlDescription) {
		this.riskControlDescription = riskControlDescription;
	}

	public List<String> getPicPaths() {
		return new Gson().fromJson(picPaths, new TypeToken<List<String>>(){}.getType());
	}

	public void setPicPaths(List<String> picPaths) {
		this.picPaths = new Gson().toJson(picPaths);
	}

	public String getMortgager() {
		return mortgager;
	}

	public void setMortgager(String mortgager) {
		this.mortgager = mortgager;
	}

	public String getGuaranteeCompany() {
		return guaranteeCompany;
	}

	public void setGuaranteeCompany(String guaranteeCompany) {
		this.guaranteeCompany = guaranteeCompany;
	}
}
