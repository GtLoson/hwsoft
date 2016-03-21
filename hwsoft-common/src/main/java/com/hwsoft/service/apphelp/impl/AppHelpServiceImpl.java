package com.hwsoft.service.apphelp.impl;

import com.hwsoft.dao.apphelp.AppHelpDao;
import com.hwsoft.exception.apphelp.AppHelpException;
import com.hwsoft.model.help.AppHelp;
import com.hwsoft.service.apphelp.AppHelpService;
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
 * 应用帮助服务
 */
@Service("appHelpService")
public class AppHelpServiceImpl implements AppHelpService {

    private static Log logger = LogFactory.getLog(AppHelpServiceImpl.class);

    @Autowired
    private AppHelpDao appHelpDao;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<AppHelp> listAll(int from, int pageSize) {
        return appHelpDao.list(from, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long getTotalCount() {
        return appHelpDao.getTotalCount();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AppHelp getAppHelpById(int id) {
        return appHelpDao.get(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public AppHelp enableAppHelp(int helpId) {
        AppHelp help = getAppHelpById(helpId);
        if (null == help) {
            logger.error("操作的应用帮助不存在，helpId=" + helpId);
            throw new AppHelpException(messageSource.getMessage("app.help.not.existing"));
        }
        help.setEnable(true);
        appHelpDao.update(help);
        return help;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public AppHelp disableAppHelp(int helpId) {
        AppHelp help = getAppHelpById(helpId);
        if (null == help) {
            logger.error("操作的应用帮助不存在，helpId=" + helpId);
            throw new AppHelpException(messageSource.getMessage("app.help.not.existing"));
        }
        help.setEnable(false);
        appHelpDao.update(help);
        return help;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public AppHelp addHelp(String title,
                           String content,
                           boolean enable,
                           Integer sortParameter) {

        checkAppHelpParameter(title, "app.help.title.can.not.be.null");
        checkAppHelpParameter(content, "app.help.content.can.not.be.null");

        AppHelp help = new AppHelp();
        help.setHelpTitle(title);
        help.setHelpContent(content);
        help.setEnable(enable);
        help.setSortParameter(sortParameter);
        Date now = new Date();
        help.setCreateTime(now);
        help.setUpdateTime(now);

        help = appHelpDao.add(help);
        return help;
    }

    /**
     * update app help
     *
     * @param appHelpId     帮助Id
     * @param title         标题
     * @param content       内容
     * @param enable        是否可用
     * @param sortParameter 排序参数
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public AppHelp updateHelp(Integer appHelpId,
                              String title,
                              String content,
                              boolean enable,
                              Integer sortParameter) {
        AppHelp help = getAppHelpById(appHelpId);
        if (null == help) {
            logger.error("操作的应用帮助不存在，helpId=" + appHelpId);
            throw new AppHelpException(messageSource.getMessage("app.help.not.existing"));
        }

        checkAppHelpParameter(title, "app.help.title.can.not.be.null");
        checkAppHelpParameter(content, "app.help.content.can.not.be.null");

        help.setUpdateTime(new Date());
        help.setHelpTitle(title);
        help.setHelpContent(content);
        help.setEnable(enable);
        help.setSortParameter(sortParameter);

        appHelpDao.update(help);

        return help;
    }

    /*
     * 校验帮助参数
     *
     * @param parameter 参数
     * @param key       key
     */
    private void checkAppHelpParameter(String parameter, String key) {
        logger.info("parameter:" + parameter);
        if (StringUtils.isEmpty(parameter)) {
            logger.error("请求参数不正确：" + messageSource.getMessage(key) + "传入值：【" + parameter + "】");
            throw new AppHelpException(messageSource.getMessage(key));
        }
    }

	/* (non-Javadoc)
	 * @see com.hwsoft.service.apphelp.AppHelpService#getTotalCountEnable()
	 */
	@Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getTotalCountEnable() {
		return appHelpDao.getTotalCountEnable();
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.apphelp.AppHelpService#listAllEnable(int, int)
	 */
	@Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AppHelp> listAllEnable(int from, int pageSize) {
		return appHelpDao.listAllEnable(from, pageSize);
	}
}
