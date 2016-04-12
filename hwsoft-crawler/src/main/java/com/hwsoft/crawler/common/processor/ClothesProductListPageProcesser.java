package com.hwsoft.crawler.common.processor;

import com.google.common.collect.Lists;
import com.hwsoft.crawler.common.config.Conf;
import com.hwsoft.crawler.controller.ProductController;
import com.hwsoft.model.product.ClothesProduct;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * Created by arvin on 16/4/10.
 */
public class ClothesProductListPageProcesser implements PageProcessor {

    public ClothesProductListPageProcesser(){}

    private Site site = Site.me().setDomain(Conf.DOMAIN);

    @Override
    public void process(Page page) {

        String pageStr = page.getHtml().toString();
        String path = pageStr.substring(pageStr.indexOf("jQuery.get('")+1,pageStr.indexOf("', function(json){"));//填充金额

        //组装所需要的product首页信息
        List<String> galleryList = page.getHtml().$("div.module-gallery-01").all();
        List<ClothesProduct> clothesProductList = Lists.newArrayList();
        for(String gallery:galleryList){
            Html html = new Html(gallery);
            String  detailUrl = html.xpath("//div[@class='gallery-image']/a/@href").toString();//详情链接
            String  showImageUrl = html.xpath("//div[@class='gallery-image']/a/img/@initSrc").toString();//图片链接



            html.xpath("//div[@class='title-area']/a/@href").toString();//详情链接
            html.xpath("//div[@class='title-area']/a/@href").toString();//详情链接
            html.xpath("//div[@class='title-area']/a/@title").toString();//详情链接

        }
        page.putField("clothesProductList", clothesProductList);


        //分页信息
        //http://www.diyihy.com/category.php?id=2&price_min=0&price_max=0&page=22&sort=goods_id&order=DESC


    }

    @Override
    public Site getSite() {
        return site;

    }
}