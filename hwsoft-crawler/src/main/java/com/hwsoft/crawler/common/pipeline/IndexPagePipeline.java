package com.hwsoft.crawler.common.pipeline;

import com.alibaba.fastjson.JSON;
import com.hwsoft.crawler.controller.ProductController;
import com.hwsoft.crawler.controller.Test;
import com.hwsoft.model.banner.Banner;
import com.hwsoft.model.category.Category;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by arvin on 16/4/10.
 */
public class IndexPagePipeline implements Pipeline {

        public IndexPagePipeline() {
        }

        public void process(ResultItems resultItems, Task task) {
            List<Category> categories = resultItems.get("categories");
            List<Banner> banners = resultItems.get("banners");
            System.out.println("categories="+ JSON.toJSONString(categories));
            System.out.println("banners="+ JSON.toJSONString(banners));

            Test.getCategoryService().saveBatch(categories);

            Test.getBannerService().saveBatch(banners);

        }
    }