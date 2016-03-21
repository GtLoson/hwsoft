package com.hwsoft.dao.channel;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hwsoft.dao.BaseDao;
import com.hwsoft.model.channel.Channel;

@Repository("channelDao")
public class ChannelDao extends BaseDao {

	@Override
	protected Class<?> entityClass() {
		return Channel.class;
	}
	
	
	public Channel save(Channel channel){
		return add(channel);
	}
	
	public Channel update(Channel channel){
		return super.update(channel);
	}
	
	public Channel findById(int id){
		return get(id);
	}
	
	  /**
     * 分页查询不区分系统的banner
     *
     * @param form
     * @param pageSize
     * @return
     */
    public List<Channel> list(int from, int pageSize) {
        return super.list(from, pageSize);
    }
    
    public Long getTotalCount(){
    	return super.getTotalCount();
    }
    public Channel findByName(String name){
    	
    	String hql = "from Channel where name=:name";
		return (Channel) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("name", name).uniqueResult();
	}
	
    public List<Channel> listAll() {
    	return super.loanAll();
    }
    
public Channel findByChannelId(int channelId){
    	
    	String hql = "from Channel where channelId=:channelId";
		return (Channel) super.getSessionFactory().getCurrentSession().createQuery(hql).setParameter("channelId", channelId).uniqueResult();
	}

}
