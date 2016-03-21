package com.hwsoft.service.channel;

import java.util.List;

import com.hwsoft.model.channel.Channel;

public interface ChannelService {

	public Channel add(String name,String desc,int channelId);
	
	public Channel update(Channel channel);
	
	public Channel findById(int id);
	
	
	public List<Channel> list(int from, int pageSize);

	public long getTotalCount();
	
	public Channel updateDesc(int id,String desc);

	
	public List<Channel> listAll();
	
	
	public Channel findByChannelId(int channelId);
	
}
