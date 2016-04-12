package com.hwsoft.crawler.controller;

import com.hwsoft.crawler.common.config.Conf;
import com.hwsoft.crawler.common.pipeline.ClothesProductListPipeline;
import com.hwsoft.crawler.common.pipeline.IndexPagePipeline;
import com.hwsoft.crawler.common.processor.ClothesProductListPageProcesser;
import com.hwsoft.crawler.common.processor.IndexPageProcesser;
import com.hwsoft.model.category.Category;
import com.hwsoft.service.banner.BannerService;
import com.hwsoft.service.category.CategoryService;
import com.hwsoft.service.clothes.ClothesProductService;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.util.List;


/**
 * Created by Administrator on 2016/4/12.
 */
public class Test  {

    static  ApplicationContext ctx;

    public static ClothesProductService getClothesProductService() {
        ClothesProductService clothesProductService = (ClothesProductService)ctx.getBean("clothesProductService");
        return clothesProductService;
    }

    public static BannerService getBannerService() {
        BannerService bannerService = (BannerService)ctx.getBean("bannerService");
        return bannerService;
    }

    public static CategoryService getCategoryService() {
        CategoryService categoryService = (CategoryService)ctx.getBean("categoryService");
        return categoryService;
    }



    @Before
    public void init() {
        ctx = new FileSystemXmlApplicationContext( "classpath:spring/spring-wap.xml");
    }

    @org.junit.Test
    public void getIndexData() throws Exception{
        Spider.create(new IndexPageProcesser())
                .addUrl(Conf.INDEX_PAGE_URL)
                .addPipeline(new ConsolePipeline())
                .addPipeline(new IndexPagePipeline())
                .run();
    }

    @org.junit.Test
    public void getProductData() throws Exception{
        List<Category> categories = getCategoryService().getAll();
        String []  stringUrls = new String [categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            stringUrls[i] = categories.get(i).getUrl();
        }

        Spider.create(new ClothesProductListPageProcesser())
                .addUrl(stringUrls)
                .addPipeline(new ConsolePipeline())
                .addPipeline(new ClothesProductListPipeline())
                .run();
    }

    @org.junit.Test
    public void getProductDetailData() throws Exception{
        String [] stringUrls = null;
        Spider.create(new IndexPageProcesser())
                .addUrl(stringUrls)
                .addPipeline(new ConsolePipeline())
                .addPipeline(new IndexPagePipeline())
                .run();
    }
}
