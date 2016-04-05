package com.hwsoft.wap.controller.index;

import com.hwsoft.wap.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by arvin on 16/4/4.
 */
@Controller
@Scope(BaseController.REQUEST_SCOPE)
public class IndexController extends BaseController {

    @RequestMapping("index")
    public String index(){
        return "index/index";
    }
}
