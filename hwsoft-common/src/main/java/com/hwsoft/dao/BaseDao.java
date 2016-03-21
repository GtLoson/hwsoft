/**
 *
 */
package com.hwsoft.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

/**
 * @author tzh
 */
public abstract class BaseDao extends HibernateDaoSupport {

    @Autowired
    protected void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    protected abstract Class<?> entityClass();

    @SuppressWarnings("unchecked")
    protected <T> T get(Serializable id) {
        return (T) getHibernateTemplate().get(entityClass(), id);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getByCollumn(String collumn,String value) {
    	String hql = " from "+entityClass().getName() + " where "+collumn+"='"+value+"'";
        return (T) getSessionFactory().getCurrentSession().createQuery(hql).uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    protected <T> List<T> loanAll() {
        return (List<T>) getHibernateTemplate().loadAll(entityClass());
    }

    protected Long getTotalCount() {
        String hql = "select count(*) from " + entityClass().getName();
        return (Long) getSessionFactory().getCurrentSession().createQuery(hql).uniqueResult();
    }

	@SuppressWarnings("unchecked")
	protected  <T> List<T>  list(int from,int pageSize) {
        String hql = "from " + entityClass().getName();
        return ((List<T>)getSessionFactory().getCurrentSession().createQuery(hql).setFirstResult(from).setMaxResults(pageSize).list());
    }
    
    protected <T> T add(T entity) {
        getHibernateTemplate().persist(entity);
        return entity;
    }

    protected <T> void remove(T entity) {
        getHibernateTemplate().delete(entity);
    }

    protected <T> T update(T entity) {
        getHibernateTemplate().merge(entity);
        return entity;
    }

    protected <T> T refresh(T entity) {
        getHibernateTemplate().refresh(entity);
        return entity;
    }

    protected List<?> find(final String hql) {
        return getHibernateTemplate().find(hql);
    }

    protected List<?> find(final String hql, Object... objects) {
        return getHibernateTemplate().find(hql, objects);
    }
}