package com.hwsoft.wap.controller.index;

import com.google.common.collect.Maps;
import com.hwsoft.model.banner.Banner;
import com.hwsoft.service.banner.BannerService;
import com.hwsoft.wap.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arvin on 16/4/4.
 */
@Controller
@Scope(BaseController.REQUEST_SCOPE)
@RequestMapping("index")
public class IndexController extends BaseController {

    @Autowired
    BannerService bannerService;

    @RequestMapping("/")
    public String index(Model model){
        //首页banner
        List<Banner> banners = bannerService.getAll();
        model.addAttribute("banners",banners);
        return "index/index";
    }

    @RequestMapping("/banners")
    @ResponseBody
    public Map<String,Object> banners(Model model){
        //首页banner
        List<Banner> banners = bannerService.getAll();
        model.addAttribute("banners",banners);
        return model.asMap();
    }

    @RequestMapping("/banner")
    @ResponseBody
    public Map<String,Object> banner(){
        Map<String,Object> result = new HashMap<String, Object>();
        //首页banner
        List<Banner> banners = bannerService.getAll();
        result.put("banners", banners);
        return result;
    }
}
