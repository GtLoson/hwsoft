package com.hwsoft.crawler.common.pipeline;

import com.hwsoft.crawler.common.processor.ClothesProductListPageProcesser;
import com.hwsoft.crawler.controller.ProductController;
import com.hwsoft.model.banner.Banner;
import com.hwsoft.model.category.Category;
import com.hwsoft.model.category.CategoryLevel;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;
import java.util.List;

/**
 * Created by arvin on 16/4/10.
 */
public class ClothProductMenuPipeline implements Pipeline {

        public ClothProductMenuPipeline() {
        }

        public void process(ResultItems resultItems, Task task) {
            List<Category> menus = resultItems.get("menus");
            ProductController.getClothesProductService().saveBatch(menus);
            List<String> bannerImageUrlList = resultItems.get("nivoSliderList");


        }
    }