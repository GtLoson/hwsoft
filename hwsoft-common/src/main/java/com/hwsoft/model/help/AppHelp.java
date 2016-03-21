package com.hwsoft.model.help;

import javax.persistence.*;
import java.util.Date;

/**
 * 应用帮助
 */
@Entity
@Table(name = "app_help")
public class AppHelp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 标题
     */
    @Column(name = "title", nullable = true)
    private String helpTitle;

    /**
     * 内容
     */
    @Column(name = "content", nullable = true)
    private String helpContent;

    /**
     * 是否可用
     */
    @Column(name = "enable", nullable = true)
    private Boolean enable;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = true)
    private Date createTime;

    /**
     * 更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time", nullable = true)
    private Date updateTime;

    /**
     * 排序参数
     */
    @Column(name = "sort_parameter", nullable = true)
    private Integer sortParameter;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHelpTitle() {
        return helpTitle;
    }

    public void setHelpTitle(String helpTitle) {
        this.helpTitle = helpTitle;
    }

    public String getHelpContent() {
        return helpContent;
    }

    public void setHelpContent(String helpContent) {
        this.helpContent = helpContent;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
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

    public Integer getSortParameter() {
        return sortParameter;
    }

    public void setSortParameter(Integer sortParameter) {
        this.sortParameter = sortParameter;
    }

    @Override
    public String toString() {
        return "AppHelp{" +
                "id=" + id +
                ", helpTitle='" + helpTitle + '\'' +
                ", helpContent='" + helpContent + '\'' +
                ", enable=" + enable +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", sortParameter=" + sortParameter +
                '}';
    }
}
