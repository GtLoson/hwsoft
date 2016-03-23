package com.hwsoft.cms.security.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.hwsoft.cms.security.common.AutorityConf;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;


public class AuthorityUtil {

	public static Map<String, Collection<ConfigAttribute>> init(){
		Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();  
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();  
        ConfigAttribute ca = new SecurityConfig(AutorityConf.ROLE_ADMIN);  
        atts.add(ca);  
		resourceMap.put("staff/list.do", atts);
		Collection<ConfigAttribute> atts1 = new ArrayList<ConfigAttribute>();  
		ConfigAttribute ca1 = new SecurityConfig(AutorityConf.ROLE_USER);
		atts1.add(ca);  
		atts1.add(ca1);  
		resourceMap.put("staff/add.do", atts1);
		
		return resourceMap;
	}
	  
}
