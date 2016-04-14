package com.hwsoft.crawler.controller;

import com.hwsoft.crawler.common.config.Conf;
import com.hwsoft.crawler.common.pipeline.ClothesProductListPipeline;
import com.hwsoft.crawler.common.pipeline.IndexPagePipeline;
import com.hwsoft.crawler.common.processor.ClothesProductListPageProcesser;
import com.hwsoft.crawler.common.processor.IndexPageProcesser;
import com.hwsoft.crawler.common.scheduler.ClothesProductScheduler;
import com.hwsoft.model.category.Category;
import com.hwsoft.service.category.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"classpath:spring/spring-wap.xml"}) //加载配置文件
public class CrawlerTest {

    @Autowired
    CategoryService categoryService;

    @Test
    public void getIndexData() throws Exception{
        Spider.create(new IndexPageProcesser())
                .addUrl(Conf.INDEX_PAGE_URL)
                .addPipeline(new ConsolePipeline())
                .addPipeline(new IndexPagePipeline())
                .run();
    }

    @Test
    public void getProductData() throws Exception{
        Assert.assertNotNull(categoryService);
        ClothesProductScheduler scheduler =   new ClothesProductScheduler();
        Spider spider = Spider.create(new ClothesProductListPageProcesser())
                              .addPipeline(new ClothesProductListPipeline())
                              .scheduler(scheduler);
        List<Category> categories = categoryService.getAll();
        int len = 2;
        for (int i = 0; i < categories.size(); i++) {
            for(int k=1;k<len;k++) {
                String requestUrl = categories.get(i).getUrl() + "/" + k + "/0/date/desc";
                Request request = new Request(requestUrl);
                scheduler.push(request,spider);
            }
        }
        spider.run();
    }

    @Test
    public void getProductDetailData() throws Exception{
        String [] stringUrls = null;
        Spider.create(new IndexPageProcesser())
                .addUrl(stringUrls)
                .addPipeline(new ConsolePipeline())
                .addPipeline(new IndexPagePipeline())
                .run();
    }
}
