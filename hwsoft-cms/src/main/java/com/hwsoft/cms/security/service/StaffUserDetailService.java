package com.hwsoft.cms.security.service;

import com.hwsoft.cms.security.model.SystemUser;
import com.hwsoft.model.staff.Staff;
import com.hwsoft.service.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class StaffUserDetailService implements UserDetailsService{
	
	@Autowired
    StaffService staffService;
	
	@Override  
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {  
        if(null == userName || "".equals(userName)){  
            throw new UsernameNotFoundException("用户名不能为空！");  
        }  
        Staff staff = staffService.getByName(userName);

        if (staff == null) {
            throw new UsernameNotFoundException("用户名或密码错误！");
        }
        SystemUser systeUser = new SystemUser(staff);

        //TODO 初始化用户菜单

        return systeUser;
    }
}
