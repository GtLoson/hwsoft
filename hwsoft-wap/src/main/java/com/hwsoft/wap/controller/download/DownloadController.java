package com.hwsoft.wap.controller.download;

import com.hwsoft.wap.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 */
@Controller
@Scope(BaseController.REQUEST_SCOPE)
public class DownloadController extends BaseController {
  
  @RequestMapping(value = "/download" ,method = RequestMethod.GET)
  public ModelAndView downloadView(){
    ModelAndView modelAndView = new ModelAndView("download");
    return modelAndView;
  }

  @RequestMapping(value = "/autoDownload" ,method = RequestMethod.GET)
  public ModelAndView phoneAutodownloadView(){
    ModelAndView modelAndView = new ModelAndView("phoneAutoDownload");
    return modelAndView;
  }
}
