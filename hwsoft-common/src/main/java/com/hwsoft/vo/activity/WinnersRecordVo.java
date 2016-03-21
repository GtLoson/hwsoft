package com.hwsoft.vo.activity;

import java.util.Date;

import com.hwsoft.common.acitivity.WinnersRecordStatus;
import com.hwsoft.util.enumtype.EnumUtil;

public class WinnersRecordVo {
	
	private int id;

	/**
	 * 用户id
	 */
	private int customerId;
	
	private String realName;
	
	private String mobile;
	
	/**
	 * 活动id
	 */
	private int acitivityId;
	
	private String activityName;
	
	/**
	 * 奖品id
	 */
	private int prizeId;
	
	/**
	 * 奖品名称
	 */
	private String prizeName;
	
	/**
	 * 中奖时间
	 */
	private Date prizeTime;
	
	/**
	 * 奖品兑换状态
	 */
	private String status;
	
	/**
	 * 奖品兑换状态
	 */
	private String statusValue;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 兑换花费积分
	 */
	private Integer score;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getAcitivityId() {
		return acitivityId;
	}

	public void setAcitivityId(int acitivityId) {
		this.acitivityId = acitivityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		WinnersRecordStatus winnersRecordStatus = EnumUtil.getEnumByName(status, WinnersRecordStatus.class);
		if(null != winnersRecordStatus){
			statusValue = winnersRecordStatus.toString();
		}
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
