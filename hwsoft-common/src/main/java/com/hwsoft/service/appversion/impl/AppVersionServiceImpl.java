package com.hwsoft.service.appversion.impl;

import com.hwsoft.common.version.AppOSType;
import com.hwsoft.dao.appversion.AppVersionDao;
import com.hwsoft.exception.appversion.AppVersionException;
import com.hwsoft.model.file.CustomFile;
import com.hwsoft.model.version.AppVersion;
import com.hwsoft.service.appversion.AppVersionService;
import com.hwsoft.spring.MessageSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 版本服务IMPL
 */
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

    private static Log logger = LogFactory.getLog(AppVersionServiceImpl.class);

    @Autowired
    private AppVersionDao appVersionDao;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AppVersion getAppVersionByVersionNumber(String versionNumber,AppOSType appOSType) {
        return appVersionDao.getByVersionNumber(versionNumber,appOSType);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<AppVersion> listAll(int from, int pageSize) {
        return appVersionDao.list(from, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long getTotalCount() {
        return appVersionDao.getTotalCount();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AppVersion getAppVersionById(int id) {
        return appVersionDao.getById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public AppVersion enableAppVersion(int versionId) {
        AppVersion appVersion = getAppVersionById(versionId);
        if (null == appVersion) {
            throw new AppVersionException(messageSource.getMessage("appVersion.not.exiting"));
        }
        appVersion.setEnable(true);
        appVersion.setUpdateTime(new Date());
        appVersionDao.update(appVersion);
        return appVersion;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public AppVersion disableAppVersion(int versionId) {
        AppVersion appVersion = getAppVersionById(versionId);
        if (null == appVersion) {
            throw new AppVersionException(messageSource.getMessage("app.version.not.existing"));
        }
        appVersion.setEnable(false);
        appVersion.setUpdateTime(new Date());
        appVersionDao.update(appVersion);
        return appVersion;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public AppVersion publishAppVersion(String path,
                                        String appVersionNumber,
                                        String osType,
                                        String versionName,
                                        String changeLog,
                                        boolean enable) {

        checkAppVersionParameter(appVersionNumber, "app.version.number.can.not.be.null");
        checkAppVersionParameter(osType, "app.version.osType.can.not.be.null");
        checkAppVersionParameter(versionName, "app.version.name.can.not.be.null");
        checkAppVersionParameter(changeLog, "app.version.changeLong.can.not.be.null");

        String changeLogContent = changeLog.replace("\r\n", "</br>");
        AppVersion appVersion = new AppVersion();
        appVersion.setVersionName(versionName);
        appVersion.setVersionNumber(appVersionNumber);
        appVersion.setChangeLog(changeLogContent);
        appVersion.setEnable(enable);
        appVersion.setFileSize(1L);// TODO 获取文件大小
        appVersion.setCreateTime(new Date());
        appVersion.setUpdateTime(new Date());
        appVersion.setAppOSType(AppOSType.valueOf(osType));
        appVersion.setAppDownloadPath(path);
        appVersionDao.save(appVersion);
        return appVersion;
    }

    /*
     * 校验版本发布异常
     *
     * @param parameter 校验的参数
     * @param key       说明的key，用于国际化
     */
    private void checkAppVersionParameter(String parameter, String key) {
        logger.info("parameter:" + parameter);
        if (StringUtils.isEmpty(parameter)) {
            logger.error("请求参数不正确：" + messageSource.getMessage(key) + "传入值：【" + parameter + "】");
            throw new AppVersionException(messageSource.getMessage(key));
        }
    }

	/* (non-Javadoc)
	 * @see com.hwsoft.service.appversion.AppVersionService#getNewestAppVersion()
	 */
	@Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AppVersion getNewestAppVersion(AppOSType appOSType) {
		return appVersionDao.getNewestAppVersion(appOSType);
	}

}
