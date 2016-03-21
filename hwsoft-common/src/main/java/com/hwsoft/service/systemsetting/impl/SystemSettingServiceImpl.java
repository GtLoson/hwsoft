package com.hwsoft.service.systemsetting.impl;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.systemsetting.SystemSettingType;
import com.hwsoft.dao.systemsetting.SystemSettingDao;
import com.hwsoft.model.systemsetting.SystemSetting;
import com.hwsoft.service.systemsetting.SystemSettingService;
import com.hwsoft.spring.MessageSource;



/**
 * 系统参数service实现类
 * @author tangzhi
 *
 */
@Service(value="systemSettingService")
public class SystemSettingServiceImpl implements SystemSettingService {

	@Autowired
	private SystemSettingDao systemSettingDao;
	
	@Autowired
    private MessageSource messageSource;
	
	
	/**
	 * 分页查询系统参数
	 * @return
	 */
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<SystemSetting> list(int from , int pageSize){
		return systemSettingDao.list(from, pageSize);
	}
	
	/**
	 * 查询系统参数总记录数
	 * @return
	 */
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public long listCount(){
		return systemSettingDao.listCount();
	}
	
	
	/**
	 * 根据id查询系统参数
	 * @param id	系统参数id
	 * @return
	 */
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public SystemSetting findSystemSettingById(int id){
		return systemSettingDao.findSystemSettingById(id);
	}
	
	
	/**
	 * 更新系统参数
	 * @param setting	系统参数信息
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.RuntimeException.class)
	public SystemSetting updateSystemSetting(Integer systemSettingId,String name , String value){
		SystemSetting setting2 = findByName(name);
		if(setting2 != null && setting2.getId() == systemSettingId){
			throw new RuntimeException(messageSource.getMessage("systemSetting.name.exists"));
		}
		
		SystemSetting systemSetting = findSystemSettingById(systemSettingId);
		systemSetting.setName(name);
		systemSetting.setUpdateTime(new Date());
		systemSetting.setValue(value);
		
		return systemSettingDao.updateSystemSetting(systemSetting);
	}

	private SystemSetting findByName(String name){
		
		return systemSettingDao.findSystemSettingByName(name);
	}

	/**
	 * 获取所有没有定义的系统参数类别
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public Map<String, String> findNotExsitsType() {
		
		Map<String, String> typeMap = new LinkedHashMap<String, String>();
		
		List<SystemSettingType> systemSettings = findAllType();
		
		for(SystemSettingType type : SystemSettingType.values()){
			
			if(systemSettings == null || !systemSettings.contains(type)){
				typeMap.put(type.name(), type.toString());
			}
		}
		
		return typeMap;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	private List<SystemSettingType> findAllType(){
		return systemSettingDao.findAllType();
	}

	/**
	 * 添加系统参数
	 * @param systemSetting
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.RuntimeException.class)
	public SystemSetting add(SystemSettingType systemSettingType,String name , String value) {
		SystemSetting setting2 = findByName(name);
		if(setting2 != null ){
			throw new RuntimeException(messageSource.getMessage("systemSetting.name.exists"));
		}
		SystemSetting setting = new SystemSetting();
		Date date = new Date();
		setting.setCreateTime(date);
		setting.setUpdateTime(date);
		setting.setSystemSettingType(systemSettingType);
		setting.setName(name);
		setting.setValue(value);
		return systemSettingDao.add(setting);
	}

	/* (non-Javadoc)
	 * @see com.xiangshang.service.systemsetting.SystemSettingServices#getByType(com.xiangshang.common.SystemSettingType)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public SystemSetting getByType(SystemSettingType systemSettingType) {
		return systemSettingDao.getByType(systemSettingType);
	}
	
}
