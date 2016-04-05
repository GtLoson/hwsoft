package com.hwsoft.crawler.controller;

import com.google.common.collect.Lists;
import com.hwsoft.model.product.ClothesProduct;
import com.hwsoft.service.clothes.ClothesProductService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;


public class ProductController extends BaseController{

    public static void main(String[] args) {
        Spider.create(new IndexPageProcesser())
                .addUrl("http://www.diyihy.com/index.php")
                .addPipeline(new ConsolePipeline())
                .addPipeline(new ProductPipeline())
                .run();
    }

}
class IndexPageProcesser implements PageProcessor {

    private Site site = Site.me().setDomain("www.diyihy.com");

    @Override
    public void process(Page page) {
        List<String> menuCategoryList = page.getHtml().$("ul.menu_category").all();
        List<String> nivoSliderList = page.getHtml().xpath("//ul[@class='nivoSlider']/a/img/@src").all();
        List<String> menuHrefList = Lists.newArrayList();
        List<String> menuNameList = Lists.newArrayList();
        for(String category:menuCategoryList){
            Html html = new Html(category);
            menuNameList.addAll(html.xpath("//ul[@class='menu_category']/h2/a/@title").all());
            menuHrefList.addAll(html.xpath("//ul[@class='menu_category']/h2/a/@href").all());
            menuNameList.addAll(html.xpath("//ul[@class='menu_category']/li/a/@title").all());
            menuHrefList.addAll(html.xpath("//ul[@class='menu_category']/li/a/@href").all());
        }
        page.putField("menuNameList", menuNameList);
        page.putField("menuHrefList", menuHrefList);
        page.putField("nivoSliderList", nivoSliderList);
        //page.addTargetRequests(menuHrefList);
    }

    @Override
    public Site getSite() {
        return site;

    }
}

class ProductPipeline implements Pipeline {
    public ProductPipeline() {
    }

    public void process(ResultItems resultItems, Task task) {
        List<String> menuNameList = resultItems.get("menuNameList");
        List<String> menuHrefList = resultItems.get("menuHrefList");
        int len = 0;
        if(menuNameList!=null&&menuHrefList!=null){
            len = menuNameList.size()>menuHrefList.size()?menuHrefList.size():menuHrefList.size();
        }else{
            return;
        }
        for(int i = 0;i < len;i++){
            System.out.println(menuNameList.get(i));
            System.out.println(menuHrefList.get(i));
            //爬取菜单连接中的product信息
            Spider.create(new ProductListPageProcesser(menuNameList.get(i)))
                    .addUrl(menuHrefList.get(i))
                    .addPipeline(new ConsolePipeline())
                    .addPipeline(new ProductListPipeline())
                    .run();
        }
    }
}


class ProductListPageProcesser implements PageProcessor {

    private String level_name;
    public ProductListPageProcesser(){}
    public ProductListPageProcesser(String level_name){this.level_name = level_name;}

    private Site site = Site.me().setDomain("www.diyihy.com");

    @Override
    public void process(Page page) {
        //组装所需要的product首页信息
        List<String> productList = page.getHtml().$("div.gallery").all();
        List<ClothesProduct> clothesProductList = Lists.newArrayList();
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
                clothesProductList.add(clothesProduct);
            }
        }
        page.putField("clothesProductList",clothesProductList);
        //分页信息
        //http://www.diyihy.com/category.php?id=2&price_min=0&price_max=0&page=22&sort=goods_id&order=DESC


    }

    @Override
    public Site getSite() {
        return site;

    }
}

class ProductListPipeline implements Pipeline {
    public ProductListPipeline() {
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
    }
}






