/**
 *
 */
package com.hwsoft.service.staff.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hwsoft.common.staff.StaffRoleType;
import com.hwsoft.dao.staff.StaffDao;
import com.hwsoft.exception.staff.StaffException;
import com.hwsoft.model.staff.Staff;
import com.hwsoft.model.staff.StaffRole;
import com.hwsoft.service.staff.StaffRoleService;
import com.hwsoft.service.staff.StaffService;
import com.hwsoft.spring.MessageSource;
import com.hwsoft.util.password.MD5Util;

/**
 * @author tzh
 */
@Service("staffService")
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffDao staffDao;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private StaffRoleService staffRoleService;

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffService#addStaff(com.hwsoft.model.staff.Staff)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Staff addStaff(String name, String realname, String password, boolean eable, String enName) {
        if (StringUtils.isEmpty(name)) {
            throw new StaffException(messageSource.getMessage("user.name.is.not.null"));
        }
        if (StringUtils.isEmpty(realname)) {
            throw new StaffException(messageSource.getMessage("real.name.is.not.null"));
        }
        if (StringUtils.isEmpty(password)) {
            throw new StaffException(messageSource.getMessage("password.is.not.null"));
        }
        if (StringUtils.isEmpty(enName)) {
            throw new StaffException(messageSource.getMessage("role.is.not.null"));
        }
        // 需要进行数据校验

        Staff staff1 = getByName(name);
        if (null != staff1) {
            throw new StaffException(messageSource.getMessage("user.name.is.exsit"));
        }

        Date date = new Date();
        Staff staff = new Staff();
        staff.setCreateTime(date);
        staff.setEnable(eable);
        staff.setLoginIp(null);
        staff.setLoginTime(null);
        staff.setName(name);
        staff.setPassword(MD5Util.digest(password));
        staff.setRealname(realname);

        Set<StaffRole> roles = new HashSet<StaffRole>();
        StaffRole staffRole = staffRoleService.getByEnName(enName);
        roles.add(staffRole);
        // 需要去查询角色
        staff.setRoles(roles);

        return staffDao.save(staff);
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffService#disableStaff(int)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Staff disableStaff(int id) {
        Staff staff = getStaffById(id);
        if (null == staff) {
            throw new StaffException(messageSource.getMessage("role.is.not.fund"));
        }
        staff.setEnable(false);
        return staffDao.update(staff);
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffService#enableStaff(int)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Staff enableStaff(int id) {
        Staff staff = getStaffById(id);
        if (null == staff) {
            throw new StaffException(messageSource.getMessage("role.is.not.fund"));
        }
        staff.setEnable(true);
        return staffDao.update(staff);
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffService#getByName(java.lang.String)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Staff getByName(String name) {
        return staffDao.getByName(name);
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffService#getStaffById(int)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Staff getStaffById(int id) {
        return staffDao.getStaffById(id);
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffService#getTotalCount()
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long getTotalCount() {
        return staffDao.totalCount();
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffService#listAll(int, int)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Staff> listAll(int from, int pageSize) {
        return staffDao.list(from, pageSize);
    }


    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffService#updateStaffLoginInfo(int, java.lang.String)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Staff updateStaffLoginInfo(int id, String loginIp) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.hwsoft.service.staff.StaffService#updateStaff(int, java.lang.String, boolean, com.hwsoft.common.staff.StaffRoleType)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Staff updateStaff(int staffId, String realName, boolean enable, String enName) {
        if (StringUtils.isEmpty(realName)) {
            throw new StaffException(messageSource.getMessage("real.name.is.not.null"));
        }
        if (com.hwsoft.util.string.StringUtils.isEmpty(enName)) {
            throw new StaffException(messageSource.getMessage("role.is.not.null"));
        }
        Staff staff = getStaffById(staffId);
        if (null == staff) {
            throw new StaffException(messageSource.getMessage("role.is.not.fund"));
        }
        staff.setRealname(realName);
        staff.setEnable(enable);
        Set<StaffRole> roles = new HashSet<StaffRole>();
        StaffRole staffRole = staffRoleService.getByEnName(enName);
        roles.add(staffRole);
        staff.setRoles(roles);

        return staffDao.update(staff);
    }


}
