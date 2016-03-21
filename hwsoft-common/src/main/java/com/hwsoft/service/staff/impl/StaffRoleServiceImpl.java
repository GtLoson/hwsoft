/**
 *
 */
package com.hwsoft.service.staff.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.staff.StaffRoleType;
import com.hwsoft.dao.staff.StaffRoleDao;
import com.hwsoft.exception.staff.StaffException;
import com.hwsoft.model.staff.FunctionInfo;
import com.hwsoft.model.staff.StaffRole;
import com.hwsoft.service.staff.FunctionInfoService;
import com.hwsoft.service.staff.StaffRoleService;
import com.hwsoft.spring.MessageSource;
import com.hwsoft.util.string.StringUtils;


/**
 * @author tzh
 */
@Service("staffRoleService")
public class StaffRoleServiceImpl implements StaffRoleService {

	@Autowired
	private StaffRoleDao staffRoleDao;
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private FunctionInfoService functionInfoService;
	
	/* (non-Javadoc)
	 * @see com.hwsoft.service.staff.StaffRoleService#addStaffRole(com.hwsoft.model.staff.StaffRole)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public StaffRole addStaffRole(boolean enable,String name,String enName){
		
		if(StringUtils.isEmpty(name)){
			throw new StaffException(messageSource.getMessage("staff.role.name.is.not.null"));
		}
		if(StringUtils.isEmpty(enName)){
			throw new StaffException(messageSource.getMessage("staff.role.enname.is.not.null"));
		}
		StaffRole staffRole = getByName(name);
		if(null != staffRole){
			throw new StaffException(messageSource.getMessage("staff.role.name.is.exsit"));
		}
		staffRole = getByEnName(enName);
		if(null != staffRole){
			throw new StaffException(messageSource.getMessage("staff.role.enname.is.exsit"));
		}
		staffRole = new StaffRole();
		staffRole.setCreateTime(new Date());
		staffRole.setEnable(enable);
		staffRole.setFunctionInfos(null);
//		staffRole.setStaffRoleType(staffRoleType);
		staffRole.setRoleEnName(enName);
		staffRole.setRoleName(name);
		
		return staffRoleDao.addStaffRole(staffRole);
	}

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffRoleService#disableStaffRole(int)
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public StaffRole disableStaffRole(int id) {
    	StaffRole staffRole = getStaffRoleById(id);
    	if(null == staffRole){
			throw new StaffException(messageSource.getMessage("staff.role.type.is.not.exsit"));
		}
    	int count = staffRoleDao.getRoleIsUsingStaffCount(id);
    	if(count > 0){
    		throw new StaffException(messageSource.getMessage("staff.role.is.usin"));
    	}
    	staffRole.setEnable(false);
        return staffRoleDao.updateStaffRole(staffRole);
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffRoleService#enableStaffRole(int)
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
    public StaffRole enableStaffRole(int id) {
    	StaffRole staffRole = getStaffRoleById(id);
    	if(null == staffRole){
			throw new StaffException(messageSource.getMessage("staff.role.type.is.not.exsit"));
		}
    	staffRole.setEnable(true);
        return staffRoleDao.updateStaffRole(staffRole);
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffRoleService#findStaffRolesByFunctionId(int)
     */
    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public List<StaffRole> findStaffRolesByFunctionId(int functionId) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffRoleService#getCount()
     */
    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public int getCount() {
        return staffRoleDao.getCount();
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffRoleService#getStaffRoleById(int)
     */
    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public StaffRole getStaffRoleById(int id) {
        return staffRoleDao.getById(id);
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffRoleService#listAll()
     */
    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public List<StaffRole> listAll() {
        return staffRoleDao.listAll();
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffRoleService#listAll(int, int)
     */
    @Override
    @Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
    public List<StaffRole> listAll(int from, int pageSize) {
        return staffRoleDao.ListAll(from, pageSize);
    }


	/* (non-Javadoc)
	 * @see com.hwsoft.service.staff.StaffRoleService#getByType(com.hwsoft.common.staff.StaffRoleType)
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public StaffRole getByName(String name) {
		return staffRoleDao.getByname(name);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public StaffRole getByEnName(String enName) {
		return staffRoleDao.getByEnName(enName);
	}

	/* (non-Javadoc)
	 * @see com.hwsoft.service.staff.StaffRoleService#listAllEnable()
	 */
	@Override
	@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
	public List<StaffRole> listAllEnable() {
		return staffRoleDao.listAll();
	}
	
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=RuntimeException.class)
	public StaffRole authorityUpdateStaff(Integer staffRoleId,String ids){
		StaffRole staffRole = getStaffRoleById(staffRoleId);
		if(null == staffRole){
			throw new StaffException(messageSource.getMessage("staff.role.type.is.not.exsit"));
		}
		if(null == ids || "".equals(ids)){
			staffRole.setFunctionInfos(null);
		} else {
		
			String[] idArr = ids.split(",");
			Set<FunctionInfo> infos = new HashSet<FunctionInfo>();
			for(String idStr : idArr){
				FunctionInfo info = functionInfoService.getFunctionInfoById(Integer.parseInt(idStr));
				infos.add(info);
			}
			staffRole.setFunctionInfos(infos);
		}
		return staffRoleDao.updateStaffRole(staffRole);
	}
}
