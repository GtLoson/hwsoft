/**
 *
 */
package com.hwsoft.model.version;

import com.hwsoft.common.version.AppOSType;

import javax.persistence.*;
import java.util.Date;

/**
 * app 版本管理
 *
 * @author tzh
 */
@Entity
@Table(name = "app_version")
public class AppVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id; //版本编号

    @Column(name = "version_name", nullable = true)
    private String versionName;// 版本名称

    @Column(name = "app_os_type", nullable = true)
    @Enumerated(EnumType.STRING)
    private AppOSType appOSType;// 客户端版本对应操作系统类型

    @Column(name = "app_download_path", nullable = true)
    private String appDownloadPath;// 客户端版本下载路径

    @Column(name = "version_number", nullable = true)
    private String versionNumber; //  客户端版本号

    @Column(name = "enable", nullable = true)
    private boolean enable = true;// 客户端版本是否可用

    @Column(name = "create_time", nullable = true)
    private Date createTime; //  客户端版本发布时间

    @Column(name = "file_size", nullable = true)
    private Long fileSize;// 版本文件大小

    @Column(name = "update_time", nullable = true)
    private Date updateTime;//操作时间

    @Column(name = "change_log", nullable = true)
    private String changeLog;// 更新日志


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppDownloadPath() {
        return appDownloadPath;
    }

    public void setAppDownloadPath(String appDownloadPath) {
        this.appDownloadPath = appDownloadPath;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
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

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public AppOSType getAppOSType() {
        return appOSType;
    }

    public void setAppOSType(AppOSType appOSType) {
        this.appOSType = appOSType;
    }
}
