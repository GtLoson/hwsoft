package com.hwsoft.service.banner.impl;

import com.hwsoft.common.version.AppOSType;
import com.hwsoft.dao.banner.BannerDao;
import com.hwsoft.exception.banner.BannerException;
import com.hwsoft.model.banner.Banner;
import com.hwsoft.model.category.Category;
import com.hwsoft.model.file.CustomFile;
import com.hwsoft.service.banner.BannerService;
import com.hwsoft.spring.MessageSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * bannerService IMPL
 */
@Service("bannerService")
public class BannerServiceImpl implements BannerService {

    private static Log logger = LogFactory.getLog(BannerServiceImpl.class);

    @Autowired
    private BannerDao bannerDao;


    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public Banner save(Banner banner) {
        return bannerDao.save(banner);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public Banner update(Banner banner) {
        return bannerDao.update(banner);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public Banner get(Integer id) {
        return bannerDao.get(id);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public List<Banner> getAll() {
        return bannerDao.getAll();
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public List<Banner> getPaginationList(Integer begin, Integer offset) {
        return bannerDao.getPaginationList(begin,offset);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public Integer updateBatch(List<Banner> banners) {
        Session session = bannerDao.getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Iterator<Banner> i$ = banners.iterator();
        int updateCounts=0;
        while ( i$.hasNext() ) {
            session.update(i$.next());
            if ( ++updateCounts % 20 == 0 ) {
                session.flush();
                session.clear();
            }
        }
        tx.commit();
        session.close();
        return updateCounts;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public Integer saveBatch(List<Banner> banners) {
        Session session = bannerDao.getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Iterator<Banner> i$ = banners.iterator();
        int saveCounts=0;
        while ( i$.hasNext() ) {
            session.save(i$.next());
            if ( ++saveCounts % 20 == 0 ) {
                session.flush();
                session.clear();
            }
        }
        tx.commit();
        session.close();
        return saveCounts;
    }
}
