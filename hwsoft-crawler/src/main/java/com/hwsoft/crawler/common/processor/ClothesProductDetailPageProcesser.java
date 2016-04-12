package com.hwsoft.crawler.common.processor;

import com.google.common.collect.Lists;
import com.hwsoft.crawler.common.config.Conf;
import com.hwsoft.model.product.ClothesProduct;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * Created by arvin on 16/4/10.
 */
public class ClothesProductDetailPageProcesser implements PageProcessor {

    public ClothesProductDetailPageProcesser(){}

    private Site site = Site.me().setDomain(Conf.DOMAIN);

    @Override
    public void process(Page page) {


    }

    @Override
    public Site getSite() {
        return site;

    }
}