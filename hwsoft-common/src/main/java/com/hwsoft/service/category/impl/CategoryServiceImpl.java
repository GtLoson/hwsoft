package com.hwsoft.service.category.impl;

import com.hwsoft.dao.category.CategoryDao;
import com.hwsoft.model.category.Category;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.service.category.CategoryService;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/12.
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public Category save(Category category) {
        return categoryDao.save(category);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public Category update(Category category) {
        return categoryDao.update(category);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public Category get(Integer id) {
        return categoryDao.get(id);
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public List<Category> getPaginationList(Integer begin, Integer offset) {
        return categoryDao.getPaginationList(begin,offset);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public Integer updateBatch(List<Category> categories) {
        Session session = categoryDao.getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Iterator<Category> i$ = categories.iterator();
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
    public Integer saveBatch(List<Category> categories) {
        Session session = categoryDao.getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Iterator<Category> i$ = categories.iterator();
        int saveCounts=0;
        while ( i$.hasNext() ) {
            Category category = i$.next();
            session.save(category);
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
