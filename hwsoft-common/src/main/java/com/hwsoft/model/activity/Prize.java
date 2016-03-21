/**
 * 
 */
package com.hwsoft.model.activity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hwsoft.common.acitivity.PrizeStatus;

/**
 * 奖品
 * @author tzh
 *
 */
@Entity
@Table(name = "prize")
public class Prize implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2753940895911857093L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 奖品名称
	 */
	@Column(name = "prize_name", nullable = false, length=64)
	private String prizeName;
	
	/**
	 * 奖品价格
	 */
	@Column(name = "prize_amount", scale = 2, precision = 16, columnDefinition = "decimal(16,2) DEFAULT 0", nullable = false)
	private double prizeAmount;
	
	/**
	 * 奖品描述
	 */
	@Column(name = "prize_desc", nullable = false, length=1024)
	private String prizeDesc;
	
	/**
	 * 奖品等级
	 */
	@Column(name = "level")
	private Integer level;
	
	/**
	 * 奖品是否是固定数量
	 */
	@Column(name = "fixed_number",nullable=false)
	private boolean fixedNumber ;
	
	/**
	 * 奖品总数量
	 */
	@Column(name = "total")
	private Integer total;
	
	/**
	 * 奖品剩余数量
	 */
	@Column(name = "leave_num")
	private Integer leaveNum;
	
	/**
	 * 累计中奖数量
	 */
	@Column(name = "total_winner_num",nullable=false,columnDefinition="INT default 0")
	private Integer totalWinnerNum;
	
	/**
	 * 活动id
	 */
	@Column(name = "activity_id",nullable=false)
	private Integer activityId;
	
	/**
	 * 是否是使用积分兑换
	 */
	@Column(name = "exchange",nullable=false)
	private boolean exchange ;
	
	/**
	 * 兑换所需积分
	 */
	@Column(name = "need_score",nullable=false,columnDefinition="INT default 0")
	private Integer needScore;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 32)
	private PrizeStatus status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public double getPrizeAmount() {
		return prizeAmount;
	}

	public void setPrizeAmount(double prizeAmount) {
		this.prizeAmount = prizeAmount;
	}

	public String getPrizeDesc() {
		return prizeDesc;
	}

	public void setPrizeDesc(String prizeDesc) {
		this.prizeDesc = prizeDesc;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public boolean isFixedNumber() {
		return fixedNumber;
	}

	public void setFixedNumber(boolean fixedNumber) {
		this.fixedNumber = fixedNumber;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getLeaveNum() {
		return leaveNum;
	}

	public void setLeaveNum(Integer leaveNum) {
		this.leaveNum = leaveNum;
	}

	public Integer getTotalWinnerNum() {
		return totalWinnerNum;
	}

	public void setTotalWinnerNum(Integer totalWinnerNum) {
		this.totalWinnerNum = totalWinnerNum;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public boolean isExchange() {
		return exchange;
	}

	public void setExchange(boolean exchange) {
		this.exchange = exchange;
	}

	public Integer getNeedScore() {
		return needScore;
	}

	public void setNeedScore(Integer needScore) {
		this.needScore = needScore;
	}

	public PrizeStatus getStatus() {
		return status;
	}

	public void setStatus(PrizeStatus status) {
		this.status = status;
	}

}
