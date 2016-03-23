package com.hwsoft.cms.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

//        System.out.println("登录验证，CustomerUsernamePasswordAuthenticationFilter ~~~~~~~~~");
        //TODO 处理其他登录验证的问题，不过用户名和密码是调用在这个执行方法执行完成之后再执行的

        return super.attemptAuthentication(request, response);
    }
}
