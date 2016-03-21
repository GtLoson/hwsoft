package com.hwsoft.dao.bank;

import com.hwsoft.common.product.ProductChannelType;
import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.bank.Bank;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 渠道银行管理
 */
@Repository("bankDao")
public class BankDao extends BaseDao {

    @Override
    protected Class<?> entityClass() {
        return Bank.class;
    }

    public Bank saveBank(Bank bank) {
        return super.add(bank);
    }

    public Bank updateBank(Bank bank) {
        return super.update(bank);
    }

    public Bank findById(Integer bankId) {
        return super.get(bankId);
    }

    public List<Bank> list(int from, int pageSize, ProductChannelType productChannelType) {
        String hql = "from Bank where productChannelType = :productChannelType";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setParameter("productChannelType", productChannelType).setFirstResult(from).setMaxResults(pageSize);
        List<Bank> Banks = query.list();
        return Banks;
    }

    public List<Bank> list(int from, int pageSize) {
        String hql = "from Bank";
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery(hql).setFirstResult(from).setMaxResults(from + pageSize);
        List<Bank> Banks = query.list();
        return Banks;
    }

    @Override
    public Long getTotalCount() {
        return super.getTotalCount();
    }

    public List<Bank> listWithProductChannelType(int from, int pageSize, ProductChannelType channelType) {
        return null;
    }
    
    public List<Bank> getListByChannelId(final Integer channelId){
    	String hql = " from Bank where productChannelId=:channelId and enable=:enable";
    	return (List<Bank>) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("channelId", channelId).setParameter("enable", true).list();
    }
    
    public List<Bank> getListByProductId(int productId) {
    	
    	String sql = "select b.* from bank b left join product_bank pb on b.id=pb.bank_id where b.enable=true and pb.enable=true and pb.product_id="+productId;  
    	SQLQuery sqluery = super.getSessionFactory().getCurrentSession().createSQLQuery(sql).addEntity(Bank.class);  
		return sqluery.list();
	}
    
}
