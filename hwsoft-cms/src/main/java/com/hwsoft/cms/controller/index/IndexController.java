package com.hwsoft.cms.controller.index;


import com.google.common.collect.Lists;
import com.hwsoft.cms.common.vo.staff.TreeUtil;
import com.hwsoft.cms.common.vo.staff.TreeVo;
import com.hwsoft.cms.controller.BaseController;
import com.hwsoft.cms.security.util.SecurityUtil;
import com.hwsoft.model.staff.FunctionInfo;
import com.hwsoft.model.staff.Staff;
import com.hwsoft.model.staff.StaffRole;
import com.hwsoft.service.staff.FunctionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Scope("prototype")
public class IndexController extends BaseController {

	@Autowired
	FunctionInfoService functionInfoService;

	@RequestMapping("index")
	public ModelAndView index(){
		System.out.println("跳转主页...");
		ModelAndView modelAndView = new ModelAndView("welcome");
		Staff staff = SecurityUtil.currentLogin();
		//初始化菜单列表
		List<FunctionInfo> funcList = functionInfoService.findFunctionInfosByParenId(1);//获得顶级菜单
		List<TreeVo> tree = TreeUtil.functionInfoToTreeVo(funcList,(List<StaffRole>)Lists.newArrayList(staff.getRoles()));
		//初始化首页资源
		modelAndView.addObject("tree",tree);
		modelAndView.addObject("staff",staff);
		return modelAndView;
	}
}
