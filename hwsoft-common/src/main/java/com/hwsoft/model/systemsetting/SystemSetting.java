package com.hwsoft.model.systemsetting;

import com.hwsoft.common.systemsetting.SystemSettingType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * 系统参数设置
 *
 * @author tzh
 */
@Entity
@Table(name = "system_setting")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SystemSetting implements Serializable, Cloneable {

    /**
     * Copyright(c) 2010-2013 by zimadai Inc. All Rights Reserved
     */
    private static final long serialVersionUID = -194480823395866556L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 参数名字
     */
    @Column(name = "name", nullable = false, length = 32)
    private String name;

    /**
     * 参数值
     */
    @Column(name = "value", nullable = false, length = 128)
    private String value;

    /**
     * 更新日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 系统参数类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "system_setting_type", nullable = false, unique = true)
    private SystemSettingType systemSettingType;
    
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 直接使用这个方法将获得参数的字符串值, 因为参数可能存的是数字也可能是字符串, 请使用getDoubleValue、getIntValue来安全地获取数字值
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    public Double getDoubleValue() {
        if (this.systemSettingType.valueType().equals(Double.class)) {
            return Double.parseDouble(this.value);
        }
        return null;
    }

    public Integer getIntValue() {
        if (this.systemSettingType.valueType().equals(Integer.class)) {
            return Integer.parseInt(this.value);
        }
        return null;
    }

    public Boolean getBooleanValue() {
        if (this.systemSettingType.valueType().equals(Boolean.class)) {
            return Boolean.getBoolean(this.value);
        }
        return null;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the systemSettingType
     */
    public SystemSettingType getSystemSettingType() {
        return systemSettingType;
    }

    /**
     * @param systemSettingType the systemSettingType to set
     */
    public void setSystemSettingType(SystemSettingType systemSettingType) {
        this.systemSettingType = systemSettingType;
    }

	/* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    protected SystemSetting clone() throws CloneNotSupportedException {
        return (SystemSetting) super.clone();
    }

    @Override
    public String toString() {
        return "SystemSetting{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", systemSettingType=" + systemSettingType +
                '}';
    }
}
