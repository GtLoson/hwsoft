/**
 * 
 */
package com.hwsoft.cms.controller.staff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.hwsoft.cms.common.vo.staff.TreeUtil;
import com.hwsoft.cms.common.vo.staff.TreeVo;
import com.hwsoft.cms.controller.BaseController;
import com.hwsoft.cms.security.service.SecurityStaffRoleCacheService;
import com.hwsoft.exception.staff.StaffException;
import com.hwsoft.model.staff.FunctionInfo;
import com.hwsoft.service.staff.FunctionInfoService;
import com.hwsoft.spring.MessageSource;
import com.hwsoft.vo.staff.AuthorityVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Scope("prototype")
public class FunctionInfoController extends BaseController {
	
	private static Log logger = LogFactory.getLog(StaffRoleController.class);
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private FunctionInfoService functionInfoService;
	
	@RequestMapping(value = "functionInfo/index")
    public ModelAndView index(String status){
		ModelAndView mv = new ModelAndView("staff/function_index");
    	return mv;
	}
	
	@RequestMapping(value = "functionInfo/tree")
	@ResponseBody
	public Map<String, Object> tree(Integer parentId){
		List<FunctionInfo> functionInfos = functionInfoService.findFunctionInfosByParenId(parentId);
		FunctionInfo info = new FunctionInfo();
		info.setId(null);
		info.setName("功能树");
		info.setChildren(functionInfos);
		List<FunctionInfo> infos = new ArrayList<FunctionInfo>();
		infos.add(info);
		List<TreeVo> treeVos = TreeUtil.functionInfoToTreeVo(infos, parentId);
		Map<String, Object> result = new HashMap<String, Object>();
        result.put("tree", treeVos);
        return result;
	}
	
	
	 /***
	 * 列表
	 * @return
	 */
	@RequestMapping(value = "functionInfo/list")
	@ResponseBody
	public Map<String, Object> list(Integer limit,Integer offset,Integer parentId){
		total = functionInfoService.getCountByParenId(parentId);
		list = functionInfoService.findPageFunctionInfosByParenId(parentId,offset,limit);
        return getResultMap();
	}
	
	@RequestMapping(value = "functionInfo/addInput")
    public ModelAndView addInput(Integer parentId){
		ModelAndView mv = new ModelAndView("staff/functioninfo_add");
		mv.addObject("parentId", parentId);
    	return mv;
	}
	
	@RequestMapping(value = "functionInfo/add")
	@ResponseBody
    public Map<String, Object> add(String name,String uri,boolean enable,Integer parentId){
		try {
			FunctionInfo info = functionInfoService.addFunctionInfo(name, uri, enable, parentId);
			result = true;
			message = messageSource.getMessage("add.success");
			List<AuthorityVo> vos = functionInfoService.loadResourceDefine(info.getId());
			SecurityStaffRoleCacheService.updateAuthorityByFunctionInfo(info, vos);
		} catch (StaffException e) {
			result = false;
			message = e.getMessage();
			logger.error(e.getMessage());
		} catch (Exception e) {
			result = false;
			logger.error(e.getMessage());
			message = messageSource.getMessage("add.failed");
		}
		return getResultMap();
	}
	
	
	@RequestMapping(value = "functionInfo/updateInput")
    public ModelAndView updateInput(Integer functionInfoId){
		ModelAndView mv = new ModelAndView("staff/functioninfo_update");
		FunctionInfo functionInfo = functionInfoService.getFunctionInfoById(functionInfoId);
		mv.addObject("functionInfo", functionInfo);
    	return mv;
	}
	
	@RequestMapping(value = "functionInfo/update")
	@ResponseBody
    public Map<String, Object> update(Integer functionInfoId,String uri,String name){
		try {
			FunctionInfo info = functionInfoService.updateFunctionInfo(functionInfoId, name, uri);
			result = true;
			message = messageSource.getMessage("update.success");
			List<AuthorityVo> vos = functionInfoService.loadResourceDefine(info.getId());
			SecurityStaffRoleCacheService.updateAuthorityByFunctionInfo(info, vos);
		} catch (StaffException e) {
			result = false;
			message = e.getMessage();
		} catch (Exception e) {
			result = false;
			message = messageSource.getMessage("update.failed");
		}
		return getResultMap();
	}
	
	@RequestMapping(value = "functionInfo/enable")
	@ResponseBody
    public Map<String, Object> enable(Integer functionInfoId){
		try {
			functionInfoService.enable(functionInfoId);
			result = true;
			message = messageSource.getMessage("enable.success");
		} catch (StaffException e) {
			result = false;
			message = e.getMessage();
		} catch (Exception e) {
			result = false;
			message = messageSource.getMessage("enable.failed");
		}
		return getResultMap();
	}
	
	@RequestMapping(value = "functionInfo/disable")
	@ResponseBody
    public Map<String, Object> disable(Integer functionInfoId){
		try {
			functionInfoService.disable(functionInfoId);
			result = true;
			message = messageSource.getMessage("disable.success");
		} catch (StaffException e) {
			result = false;
			message = e.getMessage();
		} catch (Exception e) {
			result = false;
			message = messageSource.getMessage("disable.failed");
		}
		return getResultMap();
	}
}
