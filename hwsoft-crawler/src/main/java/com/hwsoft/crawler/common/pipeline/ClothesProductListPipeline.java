package com.hwsoft.crawler.common.pipeline;


import com.hwsoft.model.product.ClothesProduct;
import com.hwsoft.service.clothes.ClothesProductService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * Created by arvin on 16/4/10.
 */
public class ClothesProductListPipeline implements Pipeline {

    @Autowired
    ClothesProductService clothesProductService;

    public static ClothesProductService getClothesProductService(){
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        return (ClothesProductService)wac.getBean("clothesProductService");
    }

    public ClothesProductListPipeline() {
    }
    public void process(ResultItems resultItems, Task task) {
        List<ClothesProduct> clothesProductList =  resultItems.get("clothesProductList");

        for (ClothesProduct product:clothesProductList){
            System.out.println( task.getUUID());
            System.out.println(product.getProductName());
            System.out.println(product.getProductPrice());
            System.out.println(product.getProductPromotePrice());
            System.out.println(product.getImageUrl());
            System.out.println(product.getProductDetailUrl());

        }
        Assert.assertNotNull(getClothesProductService());
        getClothesProductService().saveBatch(clothesProductList);
    }
}