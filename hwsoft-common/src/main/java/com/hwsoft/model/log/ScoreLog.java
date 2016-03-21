/**
 *
 */
package com.hwsoft.model.log;

import java.io.Serializable;
import java.util.Date;

import com.hwsoft.common.score.ScoreOperType;

/**
 * 积分记录
 *
 * @author tzh
 */
public class ScoreLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2366289556743008011L;

	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 用户id
	 */
	private Integer customerId;
	
	/**
	 * 操作类型
	 */
	private ScoreOperType operType;
	
	/**
	 * 操作时间
	 */
	private Date operTime;
	
	/**
	 * 本次操作分数
	 */
	private int scoreNum;
	
	/**
	 * 剩余可用积分
	 */
	private int availableScore;

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

	public ScoreOperType getOperType() {
		return operType;
	}

	public void setOperType(ScoreOperType operType) {
		this.operType = operType;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public int getScoreNum() {
		return scoreNum;
	}

	public void setScoreNum(int scoreNum) {
		this.scoreNum = scoreNum;
	}

	public int getAvailableScore() {
		return availableScore;
	}

	public void setAvailableScore(int availableScore) {
		this.availableScore = availableScore;
	}
	
}
