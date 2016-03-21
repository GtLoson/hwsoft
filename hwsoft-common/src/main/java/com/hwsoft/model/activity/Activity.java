/**
 *
 */
package com.hwsoft.model.activity;

import com.hwsoft.common.acitivity.ActivityStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动
 *
 * @author tzh
 */
@Entity
@Table(name = "activity")
public class Activity implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 2906567261927232546L;

  /**
   * id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  /**
   * 活动名称
   */
  @Column(name = "name", nullable = false, length = 128)
  private String name;


  /**
   * 活动链接
   */
  @Column(name = "url", nullable = false, length = 255)
  private String url;

  /**
   * 活动图片链接
   */
  @Column(name = "pic_path", nullable = false, length = 255)
  private String picPath;

  /**
   * 活动开始时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "start_date", nullable = false)
  private Date startDate;

  /**
   * 活动结束时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "end_date", nullable = false)
  private Date endDate;

  /**
   * 创建时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_time", nullable = false)
  private Date createTime;

  /**
   * 最近更新时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "update_time", nullable = false)
  private Date updateTime;

  /**
   * 活动状态
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 32)
  private ActivityStatus status;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public ActivityStatus getStatus() {
    return status;
  }

  public void setStatus(ActivityStatus status) {
    this.status = status;
  }

  public String getPicPath() {
    return picPath;
  }

  public void setPicPath(String picPath) {
    this.picPath = picPath;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}
