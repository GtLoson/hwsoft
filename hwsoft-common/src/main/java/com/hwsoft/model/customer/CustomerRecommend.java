/**
 *
 */
package com.hwsoft.model.customer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 用户推荐
 *
 * @author tzh
 */
@Entity
@Table(name="customer_recommend")
public class CustomerRecommend {
	
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 邀请人id
	 */
	@Column(name = "recommend_customer_id", nullable = false)
	private Integer recommendCustomerId;
	
	/**
	 * 被邀请人id
	 */
	@Column(name = "recommended_customer_id", nullable = false)
	private Integer recommendedCustomerId;
	
	/**
	 * 被邀请人注册时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "recommendTime", nullable = true)
	private Date recommendTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRecommendCustomerId() {
		return recommendCustomerId;
	}

	public void setRecommendCustomerId(Integer recommendCustomerId) {
		this.recommendCustomerId = recommendCustomerId;
	}

	public Integer getRecommendedCustomerId() {
		return recommendedCustomerId;
	}

	public void setRecommendedCustomerId(Integer recommendedCustomerId) {
		this.recommendedCustomerId = recommendedCustomerId;
	}

	public Date getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}
	
}
