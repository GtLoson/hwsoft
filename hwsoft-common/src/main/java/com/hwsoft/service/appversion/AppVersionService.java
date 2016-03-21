package com.hwsoft.service.appversion;

import com.hwsoft.common.version.AppOSType;
import com.hwsoft.model.file.CustomFile;
import com.hwsoft.model.version.AppVersion;

import java.util.List;

/**
 * 应用版本服务类
 */
public interface AppVersionService {

    /**
     * 根据应用版本号获取应用信息
     *
     * @param versionNumber 版本号
     * @return 应用信息
     */
    public AppVersion getAppVersionByVersionNumber(String versionNumber,AppOSType appOSType);

    /**
     * 获取应用版本信息列表
     *
     * @param from     开始位置
     * @param pageSize 页大小
     * @return 版本信息集合
     */
    public List<AppVersion> listAll(int from, int pageSize);

    /**
     * 信息的总得条数
     *
     * @return 条数数量
     */
    public long getTotalCount();

    /**
     * 根据Id获取版本信息
     *
     * @param id id
     * @return id对应的版本信息
     */
    public AppVersion getAppVersionById(int id);

    /**
     * 启用版本
     *
     * @param versionId 启用的版本Id
     * @return 处理的版本信息
     */
    public AppVersion enableAppVersion(int versionId);

    /**
     * 禁用版本
     *
     * @param versionId 禁用某个版本
     * @return 处理的版本信息
     */
    public AppVersion disableAppVersion(int versionId);

    /**
     * 发布手机新版本
     *
     * @param path       版本文件
     * @param appVersionNumber 版本号
     * @param osType           系统类型
     * @param versionName      版本名称
     * @param changeLog        更新日志
     * @param enable           是否可用
     * @return
     */
    public AppVersion publishAppVersion(String path,
                                        String appVersionNumber, // 版本号
                                        String osType,// 版本系统
                                        String versionName, //版本名称
                                        String changeLog, // 版本更新日志
                                        boolean enable);
    
    /**
     * 获取最新可用版本
     *
     * @param versionNumber 版本号
     * @return 应用信息
     */
    public AppVersion getNewestAppVersion(AppOSType appOSType);
}
