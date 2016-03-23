package com.hwsoft.cms.security.tag;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.google.common.base.Strings;
import com.hwsoft.cms.security.service.SecurityStaffRoleCacheService;
import com.hwsoft.cms.security.util.SecurityUtil;
import com.hwsoft.model.staff.Staff;
import com.hwsoft.model.staff.StaffRole;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;



/**
 * 控制标签体是否显示,主要是对做权限控制
 * @author tangzhi
 * @date 2013-04-24
 */
public class FunctionTag extends BodyTagSupport {
	
	/**
	 * Copyright(c) 2010-2013 by XiangShang Inc.
	 * All Rights Reserved
	 */
	private static final long serialVersionUID = -2333534462769758658L;

	private static Log logger = LogFactory.getLog(FunctionTag.class);
	/**
	 * url="back/user!list.action"<br/>
	 * 目前页面三个：标签需要用到<br/>
	 * 1.页面\<td\>所包含的超连接<br/>
	 * 2.如果\<td\>所有的超链接都为空，那么对于的\<th\>(操作)列也不要<br/>
	 */
	private String url = "";
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 判断是否有权限
	 * 
	 */
	private boolean show() {

		if(!Strings.isNullOrEmpty(url)){
			try {
				Staff staff = SecurityUtil.currentLogin();
				if(null == staff){
					return false;
				}
				Set<StaffRole> roles = staff.getRoles();
				if(null == roles || roles.size() == 0 ){
					return false;
				}
				
				Collection<ConfigAttribute> set = SecurityStaffRoleCacheService.getAgencyRoleSetByURI(url);
				if(null == set ){
					return true;
				}
				if( set.size() == 0){
					return false;
				}
				for(ConfigAttribute configAttribute : set){
					for(StaffRole role : roles){
						if(role.getRoleEnName().equals(configAttribute.getAttribute())){
							return true;
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("权限验证失败");
			}
		}
		return false;
	}
	
	/**
	 * 进行权限验证，如果有权限则在页面显示权限标签中的内容，如果没有则不显示
	 */
	public int doAfterBody() throws JspException {
		BodyContent bc = getBodyContent();//权限标签中的内容
		JspWriter out = getPreviousOut();
		try {
			if(show()) {	// 显示标签体内容
				out.write(bc.getString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("权限认证结果返回失败");
		}
		return SKIP_BODY;
	}
}
