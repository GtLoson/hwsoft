package com.hwsoft.model.order.hwsoft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.hwsoft.common.order.LedgerType;

/**
 * 放款分账详情
 * @author tzh
 *
 */
@Entity
@Table(name="order_lending_product_ledger_detail")
@Audited
public class LendingProductLedgerDetail {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "product_id",nullable=false)
	private int productId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "order_type",length=32,nullable = false)
	private LedgerType ledgerType;

	@Column(name = "amount", scale = 2, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0")
	private double amount;
	
	/**
	 * 分账入账账户
	 */
	@Column(name = "into_customer_sub_accountId",nullable=false)
	private int intoCustomerSubAccountId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public LedgerType getLedgerType() {
		return ledgerType;
	}

	public void setLedgerType(LedgerType ledgerType) {
		this.ledgerType = ledgerType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getIntoCustomerSubAccountId() {
		return intoCustomerSubAccountId;
	}

	public void setIntoCustomerSubAccountId(int intoCustomerSubAccountId) {
		this.intoCustomerSubAccountId = intoCustomerSubAccountId;
	}

}
