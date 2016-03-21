/**
 * 
 */
package com.hwsoft.model.score;

import org.hibernate.envers.Audited;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 积分账户
 * @author tzh
 *
 */
@Entity
@Table(name="customer_score")
@Audited
public class CustomerScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1699714080247738196L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	
	/**
	 * 用户id
	 */
	@Column(name = "customer_id", nullable = false,unique=true)
	private Integer customerId;
	
	
	/**
	 * 累计获取积分
	 */
	@Column(name = "total_gain_score", nullable = false , columnDefinition="INT default 0")
	private Integer totalGainScore;
	
	/**
	 * 可用积分
	 */
	@Column(name = "available_score", nullable = false , columnDefinition="INT default 0")
	private Integer availableScore;
	
	/**
	 * 已使用积分
	 */
	@Column(name = "total_used_score", nullable = false , columnDefinition="INT default 0")
	private Integer totalUsedSocre;
	
	@Version
	@Column(name="version",nullable=false)
	private int version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getTotalGainScore() {
		return totalGainScore;
	}

	public void setTotalGainScore(Integer totalGainScore) {
		this.totalGainScore = totalGainScore;
	}

	public Integer getAvailableScore() {
		return availableScore;
	}

	public void setAvailableScore(Integer availableScore) {
		this.availableScore = availableScore;
	}

	public Integer getTotalUsedSocre() {
		return totalUsedSocre;
	}

	public void setTotalUsedSocre(Integer totalUsedSocre) {
		this.totalUsedSocre = totalUsedSocre;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
