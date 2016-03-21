/**
 * 
 */
package com.hwsoft.model.order.gongming;

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

import com.hwsoft.common.product.ProductBuyerRecordStatus;
import org.hibernate.envers.Audited;

/**
 * 共鸣产品订单通知订单
 * @author tzh
 *
 */
@Entity
@Table(name="order_buy_plan_notice")
@Audited
public class BuyPlanNoticeOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "user_id",nullable=false)
	private Integer userId;
	
	@Column(name = "member_id",length=32,nullable=false)
	private String memberId;
	
	@Column(name = "buy_plan_order_form_id",length=32)
	private String buyPlanOrderFormId;
	
	@Column(name = "product_id",nullable=false)
	private Integer productId;
	
	@Column(name = "third_product_id",length=32,nullable=false)
	private String thirdProductId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status",length=32,nullable=false)
	private ProductBuyerRecordStatus status;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "before_status",length=32,nullable=false)
	private ProductBuyerRecordStatus beforeStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "status_update_time", nullable = false)
	private Date statusUpdateTime;
	
	/**
	 * 
	 */
	@Column(name = "response",length=4000,nullable=false)
	private String response;

	/**
	 * 
	 */
	@Column(name = "product_buyerRecordId",length=32,nullable=false)
	private Integer productBuyerRecordId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getBuyPlanOrderFormId() {
		return buyPlanOrderFormId;
	}

	public void setBuyPlanOrderFormId(String buyPlanOrderFormId) {
		this.buyPlanOrderFormId = buyPlanOrderFormId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getThirdProductId() {
		return thirdProductId;
	}

	public void setThirdProductId(String thirdProductId) {
		this.thirdProductId = thirdProductId;
	}

	public ProductBuyerRecordStatus getStatus() {
		return status;
	}

	public void setStatus(ProductBuyerRecordStatus status) {
		this.status = status;
	}

	public ProductBuyerRecordStatus getBeforeStatus() {
		return beforeStatus;
	}

	public void setBeforeStatus(ProductBuyerRecordStatus beforeStatus) {
		this.beforeStatus = beforeStatus;
	}

	public Date getStatusUpdateTime() {
		return statusUpdateTime;
	}

	public void setStatusUpdateTime(Date statusUpdateTime) {
		this.statusUpdateTime = statusUpdateTime;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Integer getProductBuyerRecordId() {
		return productBuyerRecordId;
	}

	public void setProductBuyerRecordId(Integer productBuyerRecordId) {
		this.productBuyerRecordId = productBuyerRecordId;
	}
	
}
