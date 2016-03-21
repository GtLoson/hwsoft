package com.hwsoft.service.apphelp;

import com.hwsoft.model.help.AppHelp;

import java.util.List;

/**
 * 应用帮助服务接口
 */
public interface AppHelpService {

    public List<AppHelp> listAll(int from, int pageSize);

    public long getTotalCount();

    /**
     * 根据Id获取版本信息
     *
     * @param id id
     * @return id对应的版本信息
     */
    public AppHelp getAppHelpById(int id);

    /**
     * 启用版本
     *
     * @param versionId 启用的版本Id
     * @return 处理的版本信息
     */
    public AppHelp enableAppHelp(int versionId);

    /**
     * 禁用版本
     *
     * @param versionId 禁用某个版本
     * @return 处理的版本信息
     */
    public AppHelp disableAppHelp(int versionId);

    /**
     * 添加帮助
     *
     * @param title         标题
     * @param content       内容
     * @param enable        是否启用
     * @param sortParameter 排序参数
     * @return
     */
    public AppHelp addHelp(String title, String content, boolean enable, Integer sortParameter);

    /**
     * 更新帮助
     *
     * @param appHelpId     帮助Id
     * @param title         标题
     * @param content       内容
     * @param enable        是否可用
     * @param sortParameter 排序参数
     * @return
     */
    public AppHelp updateHelp(Integer appHelpId, String title, String content, boolean enable, Integer sortParameter);
    
    
    public List<AppHelp> listAllEnable(int from, int pageSize);

    public long getTotalCountEnable();
}
