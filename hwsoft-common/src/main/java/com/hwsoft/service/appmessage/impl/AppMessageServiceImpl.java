package com.hwsoft.service.appmessage.impl;

import com.hwsoft.dao.appmessage.AppMessageDao;
import com.hwsoft.exception.appmessage.AppMessageException;
import com.hwsoft.model.appmessage.AppMessage;
import com.hwsoft.service.appmessage.AppMessageService;
import com.hwsoft.spring.MessageSource;
import com.hwsoft.util.string.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 应用消息服务实现
 */
@Service("appMessageService")
public class AppMessageServiceImpl implements AppMessageService {

    @Autowired
    private AppMessageDao appMessageDao;

    @Autowired
    private MessageSource messageSource;

    /* (non-Javadoc)
     * @see com.hwsoft.service.appmessage.AppMessageService#getTotalCountEnable()
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long getTotalCountEnable() {
        return appMessageDao.getTotalCountEnable();
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.appmessage.AppMessageService#listAllEnable(int, int)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<AppMessage> listAllEnable(int from, int pageSize) {
        return appMessageDao.listAllEnable(from, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public AppMessage enableAppMessage(int appMessageId) {
        return appMessageDao.enable(appMessageId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public AppMessage disableAppMessage(int appMessageId) {
        return appMessageDao.disable(appMessageId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<AppMessage> listAll(int from, int pageSize) {
        return appMessageDao.list(from, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long getTotalCount() {
        return appMessageDao.getTotalCount();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AppMessage findById(Integer appMessageId) {
        return appMessageDao.getById(appMessageId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public AppMessage updateMessage(String messageTitle, String messageContent, boolean enable, Integer messageId) {
        checkAppMessageParameter(messageTitle, "app.message.title.can.not.be.null");
        checkAppMessageParameter(messageContent, "app.message.content.can.not.be.null");
        AppMessage currentMessage = appMessageDao.getById(messageId);
        if (null == currentMessage) {
            throw new AppMessageException("Id：" + messageId + "的消息为空");
        }
        currentMessage.setEnable(enable);
        currentMessage.setUpdateTime(new Date());
        currentMessage.setMessageTitle(messageTitle);
        currentMessage.setMessageContent(messageContent);
        currentMessage = appMessageDao.update(currentMessage);
        return currentMessage;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public AppMessage addAppMessage(String messageTitle, String messageContent, boolean enable) {
        checkAppMessageParameter(messageTitle, "app.message.title.can.not.be.null");
        checkAppMessageParameter(messageContent, "app.message.content.can.not.be.null");
        AppMessage message = new AppMessage();
        Date now = new Date();
        message.setCreateTime(now);
        message.setEnable(enable);
        message.setMessageTitle(messageTitle);
        message.setMessageContent(messageContent);
        message.setUpdateTime(now);
        message = appMessageDao.add(message);
        return message;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public AppMessage getLastAppMessage(){
        return appMessageDao.getLastAppMessage();
    }

    private void checkAppMessageParameter(String parameter, String errorKey) {
        if (StringUtils.isEmpty(parameter)) {
            String value = messageSource.getMessage(errorKey);
            throw new AppMessageException(value);
        }
    }
}
