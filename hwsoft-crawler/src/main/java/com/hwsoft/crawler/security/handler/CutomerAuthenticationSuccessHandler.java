package com.hwsoft.crawler.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登陆成功handler
 *
 * @author
 */
public class CutomerAuthenticationSuccessHandler extends
        SimpleUrlAuthenticationSuccessHandler {

//    private static Log logger = LogFactory.getLog(CutomerAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //TODO 登录成功处理

//        logger.error("CutomerAuthenticationSuccessHandler test ~~~~~~~~~~~~~~~");

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
