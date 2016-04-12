package com.hwsoft.crawler.common.pipeline;

import com.hwsoft.crawler.controller.Test;
import com.hwsoft.model.product.ClothesProduct;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by arvin on 16/4/10.
 */
public class ClothesProductListPipeline implements Pipeline {

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
        Test.getClothesProductService().saveBatch(clothesProductList);
    }
}