package com.hwsoft.dao.apphelp;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.help.AppHelp;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 应用帮助dao
 */
@Repository("appHelpDao")
public class AppHelpDao extends BaseDao {

    @Override
    protected Class<?> entityClass() {
        return AppHelp.class;
    }

    public AppHelp get(int helpId){
        return super.get(helpId);
    }

    public AppHelp add(AppHelp appHelp) {
        return super.add(appHelp);
    }

    public AppHelp update(AppHelp appHelp) {
        return super.update(appHelp);
    }

    public List<AppHelp> list(int from, int pageSize) {
        String hql = "from AppHelp";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setFirstResult(from).setMaxResults(from + pageSize);
        List<AppHelp> AppHelps = query.list();
        return AppHelps;
    }

    @Override
    public Long getTotalCount() {
        return super.getTotalCount();
    }
    
	/* (non-Javadoc)
	 * @see com.hwsoft.service.apphelp.AppHelpService#getTotalCountEnable()
	 */
	public long getTotalCountEnable() {
		String hql = "select count(*) from AppHelp where enable=:enable";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setParameter("enable", true);
        return Long.parseLong(query.uniqueResult().toString());
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.apphelp.AppHelpService#listAllEnable(int, int)
	 */
	public List<AppHelp> listAllEnable(int from, int pageSize) {
	 	String hql = "from AppHelp where enable=:enable";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setFirstResult(from).setParameter("enable", true).setMaxResults( pageSize);
        List<AppHelp> AppHelps = query.list();
        return AppHelps;
	}
}
