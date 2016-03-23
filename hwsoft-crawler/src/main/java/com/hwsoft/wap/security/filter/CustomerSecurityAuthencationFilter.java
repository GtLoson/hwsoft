package com.hwsoft.wap.security.filter;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.hwsoft.wap.security.common.SecurityConf;
import com.hwsoft.wap.security.util.SecurityUtil;
import com.hwsoft.model.customer.Customer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomerSecurityAuthencationFilter extends
        AbstractSecurityInterceptor implements Filter {

    private FilterInvocationSecurityMetadataSource securityMetadataSource;


    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
    	HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    	Customer customer = SecurityUtil.currentLogin();
    	if(null == customer){
    		String currentUri = httpServletRequest.getRequestURI();
    		boolean flag = false;
    		/*for(String url : MobileUrlUtil.getNO_LOGIN_URL_LIST()){
    			if(currentUri.contains(url)){
    				flag = true;
    				break;
    			}
    		}*/
    		if(!flag){
    			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() +httpServletResponse.encodeRedirectURL(SecurityConf.LOGIN_TIMEOUT_URL));   
        		return ;
    		} 
    	} else {
    		if(SecurityUtil.loginTimeout()){//超时
    			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() +httpServletResponse.encodeRedirectURL(SecurityConf.LOGIN_TIMEOUT_URL));   
        		return ;
    		}
    	}
    	
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        
        invoke(fi);
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    public void invoke(FilterInvocation fi) throws IOException,
            ServletException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void setSecurityMetadataSource(
            FilterInvocationSecurityMetadataSource newSource) {
        this.securityMetadataSource = newSource;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
