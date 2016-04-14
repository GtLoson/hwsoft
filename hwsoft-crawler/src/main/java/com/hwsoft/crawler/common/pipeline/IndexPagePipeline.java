package com.hwsoft.crawler.common.pipeline;

import com.alibaba.fastjson.JSON;
import com.hwsoft.model.banner.Banner;
import com.hwsoft.model.category.Category;
import com.hwsoft.service.banner.BannerService;
import com.hwsoft.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by arvin on 16/4/10.
 */
public class IndexPagePipeline implements Pipeline {

    @Autowired
    CategoryService categoryService;

    @Autowired
    BannerService bannerService;

    public IndexPagePipeline() {
    }

    public void process(ResultItems resultItems, Task task) {

        List<Category> categories = resultItems.get("categories");
        List<Banner> banners = resultItems.get("banners");
        System.out.println("categories="+ JSON.toJSONString(categories));
        System.out.println("banners="+ JSON.toJSONString(banners));

        categoryService.saveBatch(categories);

        bannerService.saveBatch(banners);

    }
}