/**
 * 
 */
package com.hwsoft.model.activity;

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

import com.hwsoft.common.acitivity.WinnersRecordStatus;

/**
 * 中奖纪录
 * @author tzh
 *
 */
@Entity
@Table(name = "winners_record")
public class WinnersRecord {
	
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	/**
	 * 用户id
	 */
	@Column(name = "customer_id", nullable = false)
	private int customerId;
	
	/**
	 * 活动id
	 */
	@Column(name = "acitivity_id", nullable = false)
	private int acitivityId;
	
	/**
	 * 奖品id
	 */
	@Column(name = "prize_id", nullable = false)
	private int prizeId;
	
	/**
	 * 奖品名称
	 */
	@Column(name = "prize_name", nullable = false, length = 64)
	private String prizeName;
	
	/**
	 * 中奖时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "prize_time", nullable = false)
	private Date prizeTime;
	
	/**
	 * 奖品兑换状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 32)
	private WinnersRecordStatus status;
	
	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = false)
	private Date updateTime;
	
	/**
	 * 兑换花费积分
	 */
	@Column(name = "score")
	private Integer score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getAcitivityId() {
		return acitivityId;
	}

	public void setAcitivityId(int acitivityId) {
		this.acitivityId = acitivityId;
	}

	public int getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(int prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Date getPrizeTime() {
		return prizeTime;
	}

	public void setPrizeTime(Date prizeTime) {
		this.prizeTime = prizeTime;
	}

	public WinnersRecordStatus getStatus() {
		return status;
	}

	public void setStatus(WinnersRecordStatus status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
