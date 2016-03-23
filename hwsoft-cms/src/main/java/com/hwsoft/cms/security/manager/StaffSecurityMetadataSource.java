package com.hwsoft.cms.security.manager;


import com.hwsoft.cms.security.service.SecurityStaffRoleCacheService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;
import java.util.Iterator;

public class StaffSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	
  //  private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    public StaffSecurityMetadataSource() {
        loadResourceDefine();
    }

	private void loadResourceDefine() {  // 初始化权限数据
    	//resourceMap = SecurityAgencyRoleCacheService.loadResourceDefine();
    }

    /**
     * 获取某个权限的角色（即url对应的角色）
     */
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();
        Iterator<String> ite = SecurityStaffRoleCacheService.loadResourceDefine().keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
           
            if (url.contains(resURL)) {
                Collection<ConfigAttribute> c = SecurityStaffRoleCacheService.loadResourceDefine().get(resURL);
                return c;
            }
        }
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    
    
}
