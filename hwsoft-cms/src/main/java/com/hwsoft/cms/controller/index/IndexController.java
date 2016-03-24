/**
 *
 */
package com.hwsoft.cms.controller.index;


import com.hwsoft.cms.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("prototype")
public class IndexController extends BaseController {

	@RequestMapping("")
	public String index(){

		return "index";
	}
}
