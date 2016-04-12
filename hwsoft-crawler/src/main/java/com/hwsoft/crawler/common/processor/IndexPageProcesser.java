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
        List<Category> categories = getCategories(page);
        page.putField("categories", categories);
        //获取banner
        List<Banner>   banners = getBanners(page);
        page.putField("banners", banners);

    }

    public List<Category> getCategories(Page page){
        List<String> divCategoryList = page.getHtml().$("div.m-pop").all();
        List<Category> categories = Lists.newArrayList();
        for(String div:divCategoryList){

            Html html = new Html(div);
            String mainCategoryTitle = html.xpath("//dl/dt/a/@title").toString();
            String mainCategoryHref = html.xpath("//dl/dt/a/@href").toString();
            List<String> titles = html.xpath("//dl/dd/a/@title").all();
            List<String> hrefs = html.xpath("//dl/dd/a/@href").all();

            Category mainCategory = new Category();
            mainCategory.setName(mainCategoryTitle);
            mainCategory.setUrl(mainCategoryHref);
            mainCategory.setCreateTime(new Date());
            mainCategory.setCreateUserId(0);
            mainCategory.setDesc(mainCategoryTitle);

            List<Category> children = Lists.newArrayList();
            for(int i=0;i<titles.size();i++){
                String title = titles.get(i);
                String href = hrefs.get(i);
                Category childrenCategory = new Category();
                childrenCategory.setName(title);
                childrenCategory.setUrl(href);
                childrenCategory.setCreateTime(new Date());
                childrenCategory.setCreateUserId(0);
                childrenCategory.setDesc(title);
                children.add(childrenCategory);
            }
            mainCategory.setChildren(children);

            categories.add(mainCategory);
        }
        return  categories;
    }


    public List<Banner> getBanners(Page page){
        //获得banner
        List<Banner> banners = Lists.newArrayList();
        List<String> bannerLis = page.getHtml().$("div.module-slider-03").all();
        for(String li : bannerLis){
            Html html = new Html(li);
            List<String> bannerTitle = html.xpath("//a/@title").all();
            List<String> bannerHref = html.xpath("//a/@href").all();
            List<String> bannerImage = html.xpath("//img/@src").all();
            for(int i=0;i<bannerTitle.size();i++){
                Banner banner = new Banner();
                banner.setHtmlTitle(bannerTitle.get(i));
                banner.setHtmlURL(bannerHref.get(i));
                banner.setEnable(true);
                banner.setImageURI(bannerImage.get(i));
                banner.setCreateTime(new Date());
                banners.add(banner);
            }
        }
        return banners;
    }

    @Override
    public Site getSite() {
        return site;

    }
}