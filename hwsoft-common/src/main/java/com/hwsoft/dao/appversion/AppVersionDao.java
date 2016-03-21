package com.hwsoft.dao.appversion;

import com.hwsoft.common.version.AppOSType;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.version.AppVersion;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 应用版本管理DAO
 */
@Repository
public class AppVersionDao extends BaseDao {
    @Override
    protected Class<?> entityClass() {
        return AppVersion.class;
    }

    /**
     * save the app version info
     *
     * @param appVersion
     * @return
     */
    public AppVersion save(AppVersion appVersion) {
        return super.add(appVersion);
    }

    /**
     * update
     *
     * @param appVersion
     * @return
     */
    public AppVersion update(AppVersion appVersion) {
        return super.update(appVersion);
    }

    /**
     * fetch by id
     *
     * @param id
     * @return
     */
    public AppVersion getById(final int id) {
        return super.get(id);
    }

    /**
     * 根据应用版本号获取版本信息
     *
     * @param versionNumber 版本号
     * @return
     */
    public AppVersion getByVersionNumber(String versionNumber,AppOSType appOSType) {
        Session session = getSessionFactory().getCurrentSession();
        String hql = "from AppVersion where versionNumber = :versionNumber and appOSType=:appOSType";
        Object object = session.createQuery(hql).setParameter("versionNumber", versionNumber).setParameter("appOSType", appOSType).uniqueResult();
        return object == null ? null : (AppVersion) object;
    }

    /**
     * 分页查询应用版本信息
     *
     * @param from     开始
     * @param pageSize 页大小
     * @return 响应的分页数据
     */
    @SuppressWarnings("unchecked")
    public List<AppVersion> list(int from, int pageSize) {
        String hql = "from AppVersion";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setFirstResult(from).setMaxResults(from + pageSize);
        List<AppVersion> AppVersions = query.list();
        return AppVersions;
    }

    @Override
    public Long getTotalCount() {
        return super.getTotalCount();
    }
    
    /**
     * 查询最新版
     * @return
     */
    public AppVersion getNewestAppVersion(AppOSType appOSType) {
    	
    	String hql = " from AppVersion where enable=:enable and appOSType=:appOSType order by versionNumber desc";
    	List<AppVersion> list = super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("enable", true).setParameter("appOSType", appOSType).list();

    	if(null != list && list.size() != 0 ){
    		return list.get(0);
    	}
    	
    	return null;
	}
}
