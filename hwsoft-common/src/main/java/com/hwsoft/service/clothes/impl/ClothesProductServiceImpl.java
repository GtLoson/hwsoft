package com.hwsoft.service.clothes.impl;

import com.hwsoft.dao.clothes.ClothesProductDao;
import com.hwsoft.model.category.Category;
import com.hwsoft.model.product.ClothesProduct;
import com.hwsoft.service.clothes.ClothesProductService;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * Created by arvin on 16/4/3.
 */
@Service("clothesProductService")
public class ClothesProductServiceImpl implements ClothesProductService {

    Logger  logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    ClothesProductDao clothesProductDao;

    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public ClothesProduct save(ClothesProduct clothes){
        return clothesProductDao.save(clothes);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public ClothesProduct update(ClothesProduct clothes) {
        return clothesProductDao.update(clothes);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public ClothesProduct get(Integer id) {
        return null;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public List<ClothesProduct> getAll() {
        return null;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public List<ClothesProduct> getPaginationList(Integer begin, Integer offset) {
        return null;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public Integer updateBatch(List<ClothesProduct> clothes) {
        Session session = clothesProductDao.getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Iterator<ClothesProduct> i$ = clothes.iterator();
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
    public Integer saveBatch(List<ClothesProduct> clothes) {
        Session session = clothesProductDao.getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Iterator<ClothesProduct> i$ = clothes.iterator();
        int saveCounts=0;
        while ( i$.hasNext() ) {
            ClothesProduct cloth = i$.next();
            session.save(cloth);
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
