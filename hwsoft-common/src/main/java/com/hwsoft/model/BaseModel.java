package com.hwsoft.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Inheritance(strategy=InheritanceType.JOINED)
public class BaseModel implements Serializable {

	@Column(name = "create_user_id", nullable = true, length=11)
	private Integer createUserId;

	@Column(name = "update_user_id", nullable = true, length=11)
	private Integer updateUserId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = true)
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = true)
	private Date updateTime;

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

