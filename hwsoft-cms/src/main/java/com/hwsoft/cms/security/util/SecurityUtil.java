package com.hwsoft.cms.security.util;

import com.hwsoft.cms.security.model.SystemUser;
import com.hwsoft.model.staff.Staff;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * 当前登陆用户
 *
 * @author jinkai
 */
public class SecurityUtil {

    /**
     * 获取登陆用户
     */
    public static Staff currentLogin() {
        try {
            SystemUser userDetails = (SystemUser) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            return userDetails.getStaff();
        } catch (Exception e) {
            return null;
        }
    }

    public static void setSecurityUser(Staff user) {
        try {
            SystemUser userDetails = (SystemUser) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            userDetails.setStaff(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
