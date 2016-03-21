package com.hwsoft.dao.appmessage;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.appmessage.AppMessage;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 应用消息DAO
 */
@Repository("appMessageDao")
public class AppMessageDao extends BaseDao{

    @Override
    protected Class<?> entityClass() {
        return AppMessage.class;
    }

    public AppMessage add(AppMessage appMessage) {
        return super.add(appMessage);
    }

    public AppMessage update(AppMessage appMessage) {
        return super.update(appMessage);
    }

    public List<AppMessage> list(int from, int pageSize) {
        String hql = "from AppMessage";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setFirstResult(from).setMaxResults(pageSize);
        List<AppMessage> AppMessages = query.list();
        return AppMessages;
    }

    @Override
    public Long getTotalCount() {
        return super.getTotalCount();
    }
    
	/* (non-Javadoc)
	 * @see com.hwsoft.service.appmessage.AppMessageService#getTotalCountEnable()
	 */
	public long getTotalCountEnable() {
		String hql = "select count(id) from AppMessage where enable=:enable order by id desc";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setParameter("enable", true);
		return Long.parseLong(query.uniqueResult().toString());
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.appmessage.AppMessageService#listAllEnable(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<AppMessage> listAllEnable(int from, int pageSize) {
		String hql = "from AppMessage where enable=:enable order by id desc";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setParameter("enable", true).setFirstResult(from).setMaxResults(pageSize);
        List<AppMessage> AppMessages = query.list();
        return AppMessages;
	}

    /**
     * 启用
     *
     * @param appMessageId
     * @return
     */
    public AppMessage enable(int appMessageId) {
        AppMessage currentMessage = get(appMessageId);
        currentMessage.setEnable(true);
        return super.update(currentMessage);
    }

    /**
     * 禁用
     *
     * @param appMessageId
     * @return
     */
    public AppMessage disable(int appMessageId) {
        AppMessage currentMessage = get(appMessageId);
        currentMessage.setEnable(false);
        return super.update(currentMessage);
    }

    public AppMessage getById(Integer appMessageId) {
        return super.get(appMessageId);
    }
    
    public AppMessage getLastAppMessage(){
        String hql = "from AppMessage where enable=:enable order by createTime desc";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setParameter("enable", true);
        List<AppMessage> appMessages = query.list();
        if(null != appMessages && appMessages.size() >0){
            return appMessages.get(0);
        }else{
            return null;
        }
    }
}
