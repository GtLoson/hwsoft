/**
 * 
 */
package com.hwsoft.model.product;

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
import javax.persistence.Transient;

import com.hwsoft.common.product.ProductChannelType;


/**
 * 产品渠道
 * @author tzh
 *
 */
@Entity
@Table(name="product_channel")
public class ProductChannel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3085059761930297268L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 产品渠道：共鸣理财计划、共鸣固定期限产品、钱妈妈理财计划、钱妈妈散标、好买货币基金之类
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "product_channel_type",nullable=false,unique=true)
	private ProductChannelType productChannelType;
	/**
	 * 产品渠道名称
	 */
	@Column(name = "product_channel_name",length=128,nullable = false)
	private String productChannelName;
	
	/**
	 * 产品渠道描述
	 */
	@Column(name = "product_channel_desc",columnDefinition="TEXT",nullable = false)
	private String productChannelDesc;

	/**
	 * 添加时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = true)
	private Date createTime;
	
	@Transient
	private String typeValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductChannelName() {
		return productChannelName;
	}

	public void setProductChannelName(String productChannelName) {
		this.productChannelName = productChannelName;
	}

	public String getProductChannelDesc() {
		return productChannelDesc;
	}

	public void setProductChannelDesc(String productChannelDesc) {
		this.productChannelDesc = productChannelDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public ProductChannelType getProductChannelType() {
		return productChannelType;
	}

	public void setProductChannelType(ProductChannelType productChannelType) {
		this.productChannelType = productChannelType;
		this.typeValue = this.productChannelType.toString();
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	//TODO 其他字段待考虑
	
	
}
