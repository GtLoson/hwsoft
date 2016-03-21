/**
 *
 */
package com.hwsoft.model.score;

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

import com.hwsoft.common.score.ScoreOperType;

/**
 * 积分记录
 *
 * @author tzh
 */
@Entity
@Table(name="score_record")
public class ScoreRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5110181060390375312L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "customer_id", nullable = false)
	private Integer customerId;
	
	@Column(name = "score", nullable = false )
	private Integer score;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "score_oper_type", nullable = false, length = 32)
	private ScoreOperType scoreOperType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;
	
	@Column(name="plus",nullable=false)
	private boolean plus;
	
	@Column(name = "operator",nullable = true)
	private String operator;

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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public ScoreOperType getScoreOperType() {
		return scoreOperType;
	}

	public void setScoreOperType(ScoreOperType scoreOperType) {
		this.scoreOperType = scoreOperType;
		if(null != scoreOperType){
			plus = this.scoreOperType.isPlus();
		}
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isPlus() {
		return plus;
	}

	public void setPlus(boolean plus) {
		this.plus = plus;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
