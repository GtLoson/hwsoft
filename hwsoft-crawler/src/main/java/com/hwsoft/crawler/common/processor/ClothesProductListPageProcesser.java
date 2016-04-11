package com.hwsoft.crawler.common.processor;

import com.google.common.collect.Lists;
import com.hwsoft.crawler.controller.ProductController;
import com.hwsoft.model.product.ClothesProduct;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * Created by arvin on 16/4/10.
 */
public class ClothesProductListPageProcesser implements PageProcessor {




    private String level_name;
    public ClothesProductListPageProcesser(){}
    public ClothesProductListPageProcesser(String level_name){this.level_name = level_name;}

    private Site site = Site.me().setDomain("www.diyihy.com");

    @Override
    public void process(Page page) {
        //组装所需要的product首页信息
        List<String> productList = page.getHtml().$("div.gallery").all();

        for(String product:productList){
            Html html = new Html(product);
            List<String> productNameList = html.xpath("//div[@class='product_name']/a/text()").all();
            List<String> productPriceList = html.xpath("//div[@class='product_money']/span[@class='markmoney']/del/text()").all();
            List<String> productPromotePriceList = html.xpath("//div[@class='product_money']/span[@class='thmoney']/text()").all();
            List<String> imageUrlList = html.xpath("//div[@class='product']/ul/li/a/img/@src").all();
            List<String> productDetailList = html.xpath("//div[@class='product']/ul/li[@class='pro_imgli']/a/@href").all();
            for(int i=0;i< productNameList.size();i++){
                ClothesProduct clothesProduct = new ClothesProduct();
                clothesProduct.setProductName(productNameList.get(i));
                clothesProduct.setProductPrice(Double.parseDouble(productPriceList.get(i)
                        .replace("￥", "")
                        .replace("元", "")
                        .trim()));
                clothesProduct.setProductPromotePrice(Double.parseDouble(productPromotePriceList.get(i)
                        .replace("￥", "")
                        .replace("元", "")
                        .replace("促销价：", "")
                        .trim()));
                clothesProduct.setImageUrl(imageUrlList);
                clothesProduct.setProductDetailUrl(productDetailList.get(i));
                ProductController.clothesProductList.add(clothesProduct);
            }
        }
        page.putField("clothesProductList", ProductController.clothesProductList);


        //分页信息
        //http://www.diyihy.com/category.php?id=2&price_min=0&price_max=0&page=22&sort=goods_id&order=DESC


    }

    @Override
    public Site getSite() {
        return site;

    }
}