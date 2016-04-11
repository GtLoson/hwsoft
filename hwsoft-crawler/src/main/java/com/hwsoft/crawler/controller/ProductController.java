package com.hwsoft.crawler.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hwsoft.crawler.common.pipeline.ClothProductMenuPipeline;
import com.hwsoft.crawler.common.processor.IndexPageProcesser;
import com.hwsoft.model.banner.Banner;
import com.hwsoft.model.product.ClothesProduct;
import com.hwsoft.service.banner.BannerService;
import com.hwsoft.service.clothes.ClothesProductService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.util.List;


@Controller
@Scope(BaseController.REQUEST_SCOPE)
public class ProductController extends BaseController{

    static ApplicationContext context = null;
    static {
        context = new ClassPathXmlApplicationContext("classpath:spring/spring-wap.xml");
    }

    public static ApplicationContext getInstance(){
        if(context==null){
            context = new ClassPathXmlApplicationContext("classpath:spring/spring-wap.xml");
        }
        return  context;
    }

    public static ClothesProductService getClothesProductService() {
        ClothesProductService clothesProductService = (ClothesProductService)context.getBean("clothesProductService");
        return clothesProductService;
    }

    public static BannerService getBannerService() {
        BannerService bannerService = (BannerService)context.getBean("bannerService");
        return bannerService;
    }

    public static List<ClothesProduct> clothesProductList = Lists.newArrayList();

    public static List<Banner> bannerImageListList = Lists.newArrayList();

    public static void main(String[] args) throws Exception{
        Spider.create(new IndexPageProcesser())
                .addUrl("http://www.thejamy.com/")
                .addPipeline(new ConsolePipeline())
                .addPipeline(new ClothProductMenuPipeline())
                .run();
        //Thread.sleep(1000 * 60);

//        System.out.println(JSON.toJSON(clothesProductList));
//        for(ClothesProduct product:clothesProductList){
//            getClothesProductService().save(product);
//        }
//
//
//        Banner banner = bannerImageListList.get(0);
//        getBannerService().addBanner(banner.getImageURI(), "你好", "哈哈", "http://hwsoft.link",  "", true, 1);
    }

}







