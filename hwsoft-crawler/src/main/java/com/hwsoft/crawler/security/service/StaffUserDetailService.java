package com.hwsoft.crawler.security.service;

import com.hwsoft.crawler.security.model.SystemUser;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class StaffUserDetailService implements UserDetailsService{
	
	@Autowired
	private CustomerService customerService;
	
	@Override  
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {  
        if(null == mobile || "".equals(mobile)){  
            throw new UsernameNotFoundException("手机号码不能为空！");  
        }  
        Customer customer = customerService.findByMobile(mobile);
        if (customer == null) {
            throw new UsernameNotFoundException("手机号码或密码错误！");
        }
        SystemUser systeUser = new SystemUser(customer);
        return systeUser;
    }
}
