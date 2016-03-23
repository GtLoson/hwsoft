/**
 *
 */
package com.hwsoft.cms.controller.staff;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hwsoft.cms.controller.BaseController;
import com.hwsoft.cms.security.service.SecurityStaffRoleCacheService;
import com.hwsoft.exception.staff.StaffException;
import com.hwsoft.model.staff.StaffRole;
import com.hwsoft.service.staff.FunctionInfoService;
import com.hwsoft.service.staff.StaffRoleService;
import com.hwsoft.spring.MessageSource;
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
public class StaffRoleController extends BaseController {
	
	private static Log logger = LogFactory.getLog(StaffRoleController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private StaffRoleService staffRoleService;
	
	@Autowired
	private FunctionInfoService functionInfoService;
	
	@RequestMapping(value = "staffRole/index")
    public ModelAndView index(){
		ModelAndView mv = new ModelAndView("staff/role_index");
    	return mv;
	}
	
	 /***
	 * 列表
	 * @return
	 */
	@RequestMapping(value = "staffRole/list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,HttpServletResponse response,Integer page,Integer rows){
		 total = (int) staffRoleService.getCount();
		 if(total > 0 ){
			 list = staffRoleService.listAll(getFrom(page, rows), getPageSize(rows));
		 }
		 return getResultMap();
	}
	
	@RequestMapping(value = "staffRole/addInput")
    public ModelAndView addInput(){
		ModelAndView mv = new ModelAndView("staff/role_add");
//		Map<String,String> types = EnumUtil.getEnumMap(StaffRoleType.class);
		List<StaffRole> staffRoles = staffRoleService.listAll();
//		if(null != staffRoles && staffRoles.size() != 0){
//			for(StaffRole staffRole : staffRoles){
//				types.remove(staffRole.getStaffRoleType().name());
//			}
//		}
		mv.addObject("roles", staffRoles);
    	return mv;
	}
	
	@RequestMapping(value = "staffRole/add")
	@ResponseBody
    public Map<String, Object> add(HttpServletRequest request, HttpServletResponse response, boolean enable,String name,String enName){
		
		try {
			staffRoleService.addStaffRole(enable, name, enName);
			result = true;
			message = messageSource.getMessage("add.success");
		} catch (StaffException e) {
			result = false;
			message = e.getMessage();
		} catch (Exception e) {
			result = false;
			message = messageSource.getMessage("add.failed");
		}
	    return getResultMap();
	}
	
	
	@RequestMapping(value = "staffRole/enable")
	@ResponseBody
    public Map<String, Object> enable(HttpServletRequest request, HttpServletResponse response,Integer staffRoleId){
		try {
			staffRoleService.enableStaffRole(staffRoleId);
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
	
	@RequestMapping(value = "staffRole/disable")
	@ResponseBody
    public Map<String, Object> disable(HttpServletRequest request, HttpServletResponse response,Integer staffRoleId){
		try {
			staffRoleService.disableStaffRole(staffRoleId);
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
	
	@RequestMapping(value = "staffRole/authority")
    public ModelAndView authority(HttpServletRequest request, HttpServletResponse response,Integer staffRoleId){
		ModelAndView mv = new ModelAndView("staff/authority_update");
		StaffRole staffRole = staffRoleService.getStaffRoleById(staffRoleId);
		mv.addObject("staffRole", staffRole);
    	return mv;
	}
	
	@RequestMapping(value = "staffRole/tree")
	@ResponseBody
    public Map<String, Object> tree(HttpServletRequest request, HttpServletResponse response, Integer staffRoleId){
		
//		List<FunctionInfo> functionInfos = functionInfoService.findFunctionInfosByParenId(null);
//		StaffRole staffRole = staffRoleService.getStaffRoleById(staffRoleId);
//		List<TreeVo> treeVos = TreeUtil.functionInfoToTreeVo(functionInfos,staffRole);
//		addResultMap("treeVos", treeVos);
		return getResultMap();
	}
	
	@RequestMapping(value = "staffRole/authorityUpdate")
	@ResponseBody
    public Map<String, Object> authorityUpdate(HttpServletRequest request, HttpServletResponse response, Integer staffRoleId,String ids){
		
		try {
			StaffRole role = staffRoleService.authorityUpdateStaff(staffRoleId, ids);
			result = true;
			message = messageSource.getMessage("authority.success");
			// 更新权限缓存信息
			//TODO 这里异常信息未处理
			SecurityStaffRoleCacheService.updateAuthorityByStaffRole(role);
		} catch (StaffException e) {
			result = false;
			message = e.getMessage();
		} catch (Exception e) {
			result = false;
			message = messageSource.getMessage("authority.failed");
		}
	    return getResultMap();
	}
	

}
