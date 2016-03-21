package com.hwsoft.model.product;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.envers.Audited;

import com.hwsoft.common.product.ProductRepayRecordStatus;

/**
 * 债权还款计划表
 * @author tzh
 *
 */
@Entity
@Table(name="product_repay_record")
@Audited
public class ProductRepayRecord {
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
    @Column(name = "product_id", nullable = false, columnDefinition = "int DEFAULT 0")
    private Integer productId;
    
    /**
     * 借入者id（渠道子账户）
     */
    @Column(name = "customer_sub_account_id", nullable = false, columnDefinition = "int DEFAULT 0")
    private Integer customerSubAccountId;
    
    /**
     * 期
     */
    @Column(name = "phase_number", nullable = false, columnDefinition = "int DEFAULT 0")
    private Integer phaseNumber;
    
    /**
     * 应还款日期（固定还款日/非固定还款日）
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "repay_date", nullable = false)
    private Date repayDate;
    
    /**
     * 实际还款日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "realy_repay_date")
    private Date realyRepayDate;
    
    /**
     * 垫付还款日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "guaranted_date")
    private Date guarantedDate;
    
    /**
     * 应还本金
     */
    @Column(name = "repay_principal", nullable = false, scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double repayPrincipal;
    
    /**
     * 应还利息
     */
    @Column(name = "repay_interest", nullable = false, scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double repayInterest;
    
    /**
     * 该期是否被垫付
     */
    @Column(name = "repaid_by_guarantor", nullable = false)
    private Boolean repaidByGuarantor;
    
    /**
     * 如果垫付，则记录该期的垫付人
     */
    @Column(name = "guarantor_id")
    private Integer guarantorId;
    
    /**
     * 还款记录状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private ProductRepayRecordStatus status;
	
	
		@Transient
		private String statusValue;
    
    /**
     * 应月还本息（本金+利息）
     */
    @Column(name = "repay_principal_interest", nullable = false, scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double repayPrincipalAndInterest;
    
    /**
     * 期初待还本金 = 包含本期的所有剩余未还本金
     */
    @Column(name = "initial_principal", nullable = false, scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double initialPrincipal; 
    
    /**
     * 期末待还本金 = 还完本期后剩余未还本金
     */
    @Column(name = "final_principal", nullable = false, scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double finalPrincipal;
    
    /**
     * 实际支付本金
     */
    @Column(name = "already_repaid_principal",  scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double alreadyRepaidPrincipal;
    
    /**
     * 实际支付利息
     */
    @Column(name = "already_repaid_interest",  scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double alreadyRepaidInterest;
    
    /**
     * 实际支付罚息
     */
    @Column(name = "already_repaid_default_interest", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double alreadyRepaidDefaultInterest;
    
    /**
     * 用户实际支付费用（实际支付费用+风险金实际垫付费用=本息）
     */
    @Column(name = "already_repaid_amount", scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double alreadyRepaidAmount;
    
    /**
     * 风险金实际垫付费用（实际支付费用+风险金实际垫付费用=本息）
     */
    @Column(name = "already_guaranted_amount",  scale = 4, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
    private Double alreadyGuarantedAmount;
    
    /**
     * 版本号
     */
    @Version
    @Column(name = "version", nullable = false)
    private int version;

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

	public Integer getCustomerSubAccountId() {
		return customerSubAccountId;
	}

	public void setCustomerSubAccountId(Integer customerSubAccountId) {
		this.customerSubAccountId = customerSubAccountId;
	}

	public Integer getPhaseNumber() {
		return phaseNumber;
	}

	public void setPhaseNumber(Integer phaseNumber) {
		this.phaseNumber = phaseNumber;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public Date getRealyRepayDate() {
		return realyRepayDate;
	}

	public void setRealyRepayDate(Date realyRepayDate) {
		this.realyRepayDate = realyRepayDate;
	}

	public Date getGuarantedDate() {
		return guarantedDate;
	}

	public void setGuarantedDate(Date guarantedDate) {
		this.guarantedDate = guarantedDate;
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

	public Boolean getRepaidByGuarantor() {
		return repaidByGuarantor;
	}

	public void setRepaidByGuarantor(Boolean repaidByGuarantor) {
		this.repaidByGuarantor = repaidByGuarantor;
	}

	public Integer getGuarantorId() {
		return guarantorId;
	}

	public void setGuarantorId(Integer guarantorId) {
		this.guarantorId = guarantorId;
	}

	public ProductRepayRecordStatus getStatus() {
		return status;
	}

	public void setStatus(ProductRepayRecordStatus status) {
		this.status = status;
	}

	public Double getRepayPrincipalAndInterest() {
		return repayPrincipalAndInterest;
	}

	public void setRepayPrincipalAndInterest(Double repayPrincipalAndInterest) {
		this.repayPrincipalAndInterest = repayPrincipalAndInterest;
	}

	public Double getInitialPrincipal() {
		return initialPrincipal;
	}

	public void setInitialPrincipal(Double initialPrincipal) {
		this.initialPrincipal = initialPrincipal;
	}

	public Double getFinalPrincipal() {
		return finalPrincipal;
	}

	public void setFinalPrincipal(Double finalPrincipal) {
		this.finalPrincipal = finalPrincipal;
	}

	public Double getAlreadyRepaidAmount() {
		return alreadyRepaidAmount;
	}

	public void setAlreadyRepaidAmount(Double alreadyRepaidAmount) {
		this.alreadyRepaidAmount = alreadyRepaidAmount;
	}

	public Double getAlreadyGuarantedAmount() {
		return alreadyGuarantedAmount;
	}

	public void setAlreadyGuarantedAmount(Double alreadyGuarantedAmount) {
		this.alreadyGuarantedAmount = alreadyGuarantedAmount;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Double getAlreadyRepaidPrincipal() {
		return alreadyRepaidPrincipal;
	}

	public void setAlreadyRepaidPrincipal(Double alreadyRepaidPrincipal) {
		this.alreadyRepaidPrincipal = alreadyRepaidPrincipal;
	}

	public Double getAlreadyRepaidInterest() {
		return alreadyRepaidInterest;
	}

	public void setAlreadyRepaidInterest(Double alreadyRepaidInterest) {
		this.alreadyRepaidInterest = alreadyRepaidInterest;
	}

	public Double getAlreadyRepaidDefaultInterest() {
		return alreadyRepaidDefaultInterest;
	}

	public void setAlreadyRepaidDefaultInterest(Double alreadyRepaidDefaultInterest) {
		this.alreadyRepaidDefaultInterest = alreadyRepaidDefaultInterest;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
}
