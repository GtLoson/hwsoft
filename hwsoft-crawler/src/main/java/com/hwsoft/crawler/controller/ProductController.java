package com.hwsoft.crawler.controller;

import com.hwsoft.crawler.common.pipeline.IndexPagePipeline;
import com.hwsoft.crawler.common.processor.IndexPageProcesser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.ConsolePipeline;


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



    public static void main(String[] args) throws Exception{
        Spider.create(new IndexPageProcesser())
                .addUrl("http://www.thejamy.com/")
                .addPipeline(new ConsolePipeline())
                .addPipeline(new IndexPagePipeline())
                .run();
    }

}







