package com.hwsoft.service.channel.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.dao.channel.ChannelDao;
import com.hwsoft.exception.channel.ChannelException;
import com.hwsoft.model.channel.Channel;
import com.hwsoft.service.channel.ChannelService;
import com.hwsoft.util.string.StringUtils;

@Service("channelService")
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelDao channelDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public Channel add(String name,String desc,int channelId) {
		
		
		if(StringUtils.isEmpty(name)){
			throw new ChannelException("推广渠道名称不能为空");
		}
		
		if(StringUtils.isEmpty(desc)){
			throw new ChannelException("推广渠道描述不能为空");
		}
		
		
		Channel channel2 = findByName(name);
		if(null != channel2){
			throw new ChannelException("推广渠道名称已经存在");
		}
		channel2 = findByChannelId(channelId);
		if(null != channel2){
			throw new ChannelException("推广渠道编号已经存在");
		}
		Channel channel = new Channel();
		channel.setChannelId(channelId);
		channel.setBuyCount(0);
		channel.setCardCount(0);
		channel.setCreateTime(new Date());
		channel.setDesc(desc);
		channel.setName(name);
		channel.setRegisterCount(0);
		return channelDao.save(channel);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public Channel update(Channel channel) {
		return channelDao.update(channel);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Channel findById(int id) {
		return channelDao.findById(id);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Channel> list(int from, int pageSize) {
		return channelDao.list(from, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public long getTotalCount() {
		return channelDao.getTotalCount();
	}

	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	private Channel findByName(String name){
		return channelDao.findByName(name);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public Channel updateDesc(int id, String desc) {
		if(StringUtils.isEmpty(desc)){
			throw new ChannelException("推广渠道描述不能为空");
		}
		
		Channel channel = findById(id);
		if(null == channel){
			throw new ChannelException("推广渠道未找到");
		}
		channel.setDesc(desc);
		return channelDao.update(channel);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<Channel> listAll() {
		return channelDao.listAll();
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Channel findByChannelId(int channelId) {
		return channelDao.findByChannelId(channelId);
	}
	
}
