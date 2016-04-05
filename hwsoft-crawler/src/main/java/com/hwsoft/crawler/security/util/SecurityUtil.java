package com.hwsoft.crawler.security.util;

import com.hwsoft.crawler.security.common.SecurityConf;
import com.hwsoft.crawler.security.model.SystemUser;
import com.hwsoft.model.customer.Customer;

import org.springframework.security.core.context.SecurityContextHolder;


/**
 * 当前登陆用户
 *
 * @author tzh
 */
public class SecurityUtil {
	
    
	/**
     * 获取登陆用户
     */
    public static Customer currentLogin() {
        try {
            return getSystemUser().getCustomer();
        } catch (Exception e) {
            return null;
        }
    }

    public static void setSecurityUser(Customer user) {
        try {
        	long currentTime = System.currentTimeMillis();
        	SystemUser systemUser = getSystemUser();
        	systemUser.setCustomer(user);
        	systemUser.setLastUpdateTime(currentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static SystemUser getSystemUser() {
        try {
            SystemUser userDetails = (SystemUser) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            return userDetails;
        } catch (Exception e) {
            return null;
        }
    }
    
    
    /**
     * 登录是否超时
     * @return
     */
    public static boolean loginTimeout(){
    	Customer customer = currentLogin();
    	if(null == customer){
    		setSecurityUser(null);
    		return true;
    	} else { // 进行时间判断
    		long currentTime = System.currentTimeMillis();
    		SystemUser userDetails = getSystemUser();
    		long lastTime = userDetails.getLastUpdateTime();
    		if(currentTime > lastTime+SecurityConf.LOGIN_TIMEOUT_SECOND){
    			setSecurityUser(null);
    			return true;
    		} else {
    			return false;
    		}
    	}
    }
    
    public static void setLoginTime(){
    	 try {
         	long currentTime = System.currentTimeMillis();
         	SystemUser systemUser = getSystemUser();
         	systemUser.setLastUpdateTime(currentTime);
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

}
