package com.hwsoft.crawler.common.processor;

import com.google.common.collect.Lists;
import com.hwsoft.crawler.controller.ProductController;
import com.hwsoft.model.banner.Banner;
import com.hwsoft.model.category.Category;
import com.hwsoft.model.category.CategoryLevel;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Date;
import java.util.List;

/**
 * Created by arvin on 16/4/10.
 */
public
class IndexPageProcesser implements PageProcessor {

    private Site site = Site.me().setDomain("http://www.thejamy.com/");

    @Override
    public void process(Page page) {
        //获取分类
        List<String> divCategoryList = page.getHtml().$("div.m-pop dis").all();
        List<Category> menus = Lists.newArrayList();
        for(String div:divCategoryList){
            Html html = new Html(div);
            String defaultTitle = html.xpath("//dl/dt/a/@title").toString();
            String defaultHref = html.xpath("//dl/dt/a/@href").toString();
            List<String> titles = html.xpath("//dl/dd/a/@title").all();
            List<String> hrefs = html.xpath("//dl/dd/a/@href").all();

            Category defaultCategory = new Category();
            defaultCategory.setName(defaultTitle);
            defaultCategory.setUrl(defaultHref);
            defaultCategory.setCategoryLevel(CategoryLevel.LEVEL1);
            menus.add(defaultCategory);

            for(int i=0;i<titles.size();i++){
                String title = titles.get(i);
                String href = hrefs.get(i);
                Category category = new Category();
                category.setName(title);
                category.setUrl(href);
                category.setCategoryLevel(CategoryLevel.LEVEL2);
                menus.add(category);
            }
        }


        page.putField("menus", menus);

        //获得banner

        List<String> bannerLis = page.getHtml().$("div.slide-1 slide").all();
        for(String li : bannerLis){
            Html html = new Html(li);
            List<String> bannerTitle = html.xpath("//ul/dd/a/@title").all();
            List<String> bannerHref = html.xpath("//ul/dd/a/@href").all();
            List<String> bannerImage = html.xpath("//ul/li/img/@src").all();
            for(int i=0;i<bannerTitle.size();i++){
                Banner banner = new Banner();
                banner.setHtmlTitle(bannerTitle.get(i));
                banner.setHtmlURL(bannerHref.get(i));
                banner.setCreateTime(new Date());
                banner.setEnable(true);
                banner.setImageURI(bannerImage.get(i));
                ProductController.getBannerService().addBanner(banner.getImageURI(), banner.getHtmlTitle(), banner.getHtmlTitle(), banner.getHtmlURL(), "", true, 1);
            }
        }




        page.putField("menus", menus);

    }

    @Override
    public Site getSite() {
        return site;

    }
}