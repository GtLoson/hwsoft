package com.hwsoft.service.appmessage;

import java.util.List;

import com.hwsoft.model.appmessage.AppMessage;


/**
 * 应用消息服务
 */
public interface AppMessageService {
	
    public List<AppMessage> listAllEnable(int from, int pageSize);

    public List<AppMessage> listAll(int from,int pageSize);

    public long getTotalCountEnable();

    public long getTotalCount();

    public AppMessage findById(Integer appMessageId);

    public AppMessage enableAppMessage(int appMessageId);

    public AppMessage disableAppMessage(int appMessageId);

    public AppMessage addAppMessage(String messageTitle,String messageContent,boolean enable);

    public AppMessage updateMessage(String messageTitle,String messageContent,boolean enable,Integer messageId);

    public AppMessage getLastAppMessage();
}
