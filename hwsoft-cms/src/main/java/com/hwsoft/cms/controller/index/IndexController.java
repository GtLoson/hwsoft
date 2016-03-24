package com.hwsoft.cms.controller.index;


import com.hwsoft.cms.controller.BaseController;
import com.hwsoft.cms.security.util.SecurityUtil;
import com.hwsoft.model.staff.Staff;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
public class IndexController extends BaseController {

	@RequestMapping("index")
	public ModelAndView index(){
		System.out.println("跳转主页...");
		ModelAndView modelAndView = new ModelAndView("welcome");
		//初始化菜单列表

		//初始化首页资源
		Staff staff = SecurityUtil.currentLogin();
		modelAndView.addObject("staff",staff);
		return modelAndView;
	}
}
