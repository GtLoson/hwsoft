package com.hwsoft.dao.banner;

import com.hwsoft.common.version.AppOSType;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.banner.Banner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * banner DAO
 */
@Repository("bannerDao")
public class BannerDao extends BaseDao {


    @Override
    protected Class<?> entityClass() {
        return Banner.class;
    }

    public Banner getById(Integer id) {
        return super.get(id);
    }

    /**
     * 添加banner
     *
     * @param banner banner
     * @return
     */
    public Banner addBanner(Banner banner) {
        return super.add(banner);
    }

    /**
     * 分页查询对应版本的banner
     *
     * @param appOSTypes 系统类型集合
     * @param from
     * @param pageSize
     * @return
     */
    public List<Banner> listByOSType(int from, int pageSize, AppOSType... appOSTypes) {
        String hql = "from Banner where osType in (:osTypes)";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setFirstResult(from).setMaxResults(from + pageSize);
        if (null != appOSTypes) {
            query.setParameterList("osTypes", appOSTypes);
        }
        List<Banner> banners = query.list();
        return banners;
    }

    /**
     * 分页查询不区分系统的banner
     *
     * @param form
     * @param pageSize
     * @return
     */
    public List<Banner> listAll(int form, int pageSize) {
        return listByOSType(form, pageSize);
    }

    /**
     * 更新banner
     *
     * @param banner 更新的banner
     * @return banner
     */
    public Banner updateBanner(Banner banner) {
        return super.update(banner);
    }

    /**
     * 获取某类型的banner 数量
     *
     * @param appOSTypes os
     * @return count
     */
    public Long getTotalCount(AppOSType... appOSTypes) {
        Session session = getSessionFactory().getCurrentSession();
        String hql = "select count(*) from Banner where osType in (:osTypes)";
        Query query = session.createQuery(hql).setParameterList("osTypes", appOSTypes);
        Object count = query.uniqueResult();
        return (Long) count;
    }
    
    public List<Banner> getBannersEnable() {
        String hql = "from Banner where enable=:enable";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setParameter("enable", true);
        List<Banner> banners = query.list();
        return banners;
	}
}
