package com.hwsoft.cms.controller.staff;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hwsoft.cms.controller.BaseController;
import com.hwsoft.exception.staff.StaffException;
import com.hwsoft.model.staff.Staff;
import com.hwsoft.model.staff.StaffRole;
import com.hwsoft.service.staff.StaffRoleService;
import com.hwsoft.service.staff.StaffService;
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
public class StaffController extends BaseController {
	
	private static Log logger = LogFactory.getLog(StaffController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private StaffRoleService staffRoleService;
	
	@RequestMapping(value = "staff/index")
    public ModelAndView index(String status){
		ModelAndView mv = new ModelAndView("staff/index");
    	return mv;
	}
	
	 /***
	 * 列表
	 * @return
	 */
	@RequestMapping(value = "staff/list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,HttpServletResponse response,Integer page,Integer rows){
		 total = (int) staffService.getTotalCount();
		 if(total > 0 ){
			 list = staffService.listAll(getFrom(page, rows), getPageSize(rows));
		 }
		 return getResultMap();
	}
	
	@RequestMapping(value = "staff/addInput")
    public ModelAndView addInput(){
		
		ModelAndView mv = new ModelAndView("staff/add");
		
		List<StaffRole> roles = staffRoleService.listAllEnable();
		mv.addObject("roles", roles);
		
    	return mv;
	}
	
	@RequestMapping(value = "staff/add")
	@ResponseBody
    public Map<String, Object> add(HttpServletRequest request, HttpServletResponse response,String name,
    		String realname,String password,String passwordAgain,boolean eable,String enName){
		
		if(!password.equals(passwordAgain)){
			result = false;
			message = messageSource.getMessage("password.not.equal");
			return getResultMap();
		}
		
		try {
			staffService.addStaff(name, realname, passwordAgain, eable, enName);
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
	
	
	@RequestMapping(value = "staff/updateInput")
    public ModelAndView updateInput(HttpServletRequest request, HttpServletResponse response,Integer staffId){
		
		Staff staff = staffService.getStaffById(staffId);
		ModelAndView mv = new ModelAndView("staff/update");
		List<StaffRole> roles = staffRoleService.listAllEnable();
		mv.addObject("roles", roles);
		mv.addObject("staff", staff);
    	return mv;
	}
	
	@RequestMapping(value = "staff/update")
	@ResponseBody
    public Map<String, Object> update(HttpServletRequest request, HttpServletResponse response,Integer staffId,
    		String realname,boolean enable,String enName){
		try {
			
			staffService.updateStaff(staffId, realname, enable, enName);
			result = true;
			message = messageSource.getMessage("update.success");
		} catch (StaffException e) {
			result = false;
			message = e.getMessage();
		} catch (Exception e) {
			result = false;
			message = messageSource.getMessage("update.failed");
		}
		
	    return getResultMap();
	}
	
	@RequestMapping(value = "staff/enable")
	@ResponseBody
    public Map<String, Object> enable(HttpServletRequest request, HttpServletResponse response,Integer staffId){
		try {
			staffService.enableStaff(staffId);
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
	
	@RequestMapping(value = "staff/disable")
	@ResponseBody
    public Map<String, Object> disable(HttpServletRequest request, HttpServletResponse response,Integer staffId){
		try {
			staffService.disableStaff(staffId);
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
