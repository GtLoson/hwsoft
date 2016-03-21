/**
 * 
 */
package com.hwsoft.wap.controller.login;

import com.hwsoft.wap.controller.BaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwsoft.wap.common.response.Response;
import com.hwsoft.wap.common.response.ResponseCode;
import com.hwsoft.wap.security.util.SecurityUtil;
import com.hwsoft.model.customer.Customer;
import com.hwsoft.spring.MessageSource;

/**
 * 登录
 * 
 * @author tzh
 * 
 */
@Controller
@Scope(BaseController.REQUEST_SCOPE)
public class LoginController {
	
	private final Log logger = LogFactory.getLog(LoginController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "/loginSuccess")
	@ResponseBody
	public Response loginSuccess() {
		String message = messageSource.getMessage("login.success");
		logger.info(message);
		return Response.addSuccessResponse(message, null);
	}
	
	@RequestMapping(value = "/loginFailed")
	@ResponseBody
	public Response loginFailed() {
		return Response.addFailedResponse(ResponseCode.LOGIN_FAILED);
	}
	
	@RequestMapping(value = "/loginTimeout")
	@ResponseBody
	public Response loginTimeout() {
		return Response.addFailedResponse(ResponseCode.LOGIN_TIMEOUT);
	}
	
	@RequestMapping(value = "/logoutSuccess")
	@ResponseBody
	public Response logoutSuccess() {
		Customer customer = SecurityUtil.currentLogin();
		if(null == customer){
			System.out.println("退出成功");
		} else {
			System.out.println("退出失败");
		}
		return Response.addSuccessResponse("退出成功", null);//(ResponseCode.LOGOUT_SUCCESS);
	}
	

}
