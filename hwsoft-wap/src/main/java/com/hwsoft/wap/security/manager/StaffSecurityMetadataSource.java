package com.hwsoft.wap.security.manager;


import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;

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
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    
}
