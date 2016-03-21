package com.hwsoft.wechat.common;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * ApiController 为 ApiController 绑定 ApiConfig 对象到当前线程，
 * 以便在后续的操作中可以使用 ApiConfigKit.getApiConfig() 获取到该对象
 */
public class GlobInterceptor implements Interceptor {
	private ArrayList<String> privateActionList = new ArrayList<String>();

	public GlobInterceptor(){
		privateActionList.add("/pc/stock/list");
		privateActionList.add("/pc/stock/g_edit");
		privateActionList.add("/pc/stock/d_edit");
		privateActionList.add("/pc/stock/delete");
	}

	public void intercept(ActionInvocation ai) {
		String actionKey = ai.getActionKey();
		if (privateActionList.contains(actionKey)){
			HttpSession session = ai.getController().getSession();
			Controller controller = ai.getController();
			String loginStats = (session.getAttribute("loginStats")+"");
			if(!"pass".equals(loginStats)){
				SimpleDateFormat smf1=new SimpleDateFormat("yy-MM-dd HH");
				controller.getPara(0);
				String keyStr = (controller.getPara("keyStr")+"").toLowerCase();
				String str = smf1.format(new Date());
				String dynamicPs =   MD5Utils.MD5("go" + str + "to").toLowerCase();
				if(!dynamicPs.equals(keyStr)){
					ai.getController().render("pz_indexp.jsp"); //无权验证
					return;
				}else{
					session.setAttribute("loginStats", "pass");
				}
			}
		}
		ai.invoke();
	}
}

