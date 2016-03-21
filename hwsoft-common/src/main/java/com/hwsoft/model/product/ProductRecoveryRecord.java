package com.hwsoft.model.product;

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
import javax.persistence.Version;

import com.hwsoft.common.product.ProductRecoveryRecordType;


/**
 * 投资人回收记录
 * @author tzh
 *
 */
@Entity
@Table(name="product_recovery_record")
public class ProductRecoveryRecord {
	
	
	/**
     * id
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
    /**
     * 产品id
     */
    @Column(name = "product_id", nullable = false, columnDefinition = "int DEFAULT 0")
    private Integer productId;
    
    /**
     * 所属产品子账户
     */
    @Column(name = "product_sub_account_id", nullable = false, columnDefinition = "int DEFAULT 0")
    private Integer productSubAccountId;
    
    /**
     * 还款记录id
     */
    @Column(name = "product_repay_record_id", nullable = false, columnDefinition = "int DEFAULT 0")
    private Integer productRepayRecordId;
    
    /**
     * 期
     */
    @Column(name = "phase_number", nullable = false, columnDefinition = "int DEFAULT 0")
    private Integer phaseNumber;
    
    /**
     * 还款日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "realy_repay_date")
    private Date realyRepayDate;
    
    /**
     * 本金
     */
    @Column(name = "repay_principal", nullable = false, scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double repayPrincipal;
    
    /**
     * 利息
     */
    @Column(name = "repay_interest", nullable = false, scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double repayInterest;
    
    /**
     * 罚息
     */
    @Column(name = "repay_default_interest", nullable = false, scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double repayDefaultInterest;
    
    
    /**
     * 还款类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 32)
    private ProductRecoveryRecordType type;
    
    /**
     * 版本号
     */
    @Version
    @Column(name = "version", nullable = false)
    private int version;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProductSubAccountId() {
		return productSubAccountId;
	}

	public void setProductSubAccountId(Integer productSubAccountId) {
		this.productSubAccountId = productSubAccountId;
	}

	public Integer getProductRepayRecordId() {
		return productRepayRecordId;
	}

	public void setProductRepayRecordId(Integer productRepayRecordId) {
		this.productRepayRecordId = productRepayRecordId;
	}

	public Integer getPhaseNumber() {
		return phaseNumber;
	}

	public void setPhaseNumber(Integer phaseNumber) {
		this.phaseNumber = phaseNumber;
	}

	public Date getRealyRepayDate() {
		return realyRepayDate;
	}

	public void setRealyRepayDate(Date realyRepayDate) {
		this.realyRepayDate = realyRepayDate;
	}

	public Double getRepayPrincipal() {
		return repayPrincipal;
	}

	public void setRepayPrincipal(Double repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
	}

	public Double getRepayInterest() {
		return repayInterest;
	}

	public void setRepayInterest(Double repayInterest) {
		this.repayInterest = repayInterest;
	}

	public Double getRepayDefaultInterest() {
		return repayDefaultInterest;
	}

	public void setRepayDefaultInterest(Double repayDefaultInterest) {
		this.repayDefaultInterest = repayDefaultInterest;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public ProductRecoveryRecordType getType() {
		return type;
	}

	public void setType(ProductRecoveryRecordType type) {
		this.type = type;
	}

}
