package com.hwsoft.wap.controller;


import us.codecraft.webmagic.*;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Json;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 *
 *   方法	                          说明	                        示例
     xpath(String xpath)	          使用XPath选择	                html.xpath("//div[@class='title']")
     $(String selector)	              使用Css选择器选择	            html.$("div.title")
     $(String selector,String attr)	  使用Css选择器选择	            html.$("div.title","text")
     css(String selector)	          功能同$()，使用Css选择器选择	    html.css("div.title")
     links()	                      选择所有链接	                html.links()
     regex(String regex)	          使用正则表达式抽取	            html.regex("\(.*?)\")
     regex(String regex,int group)	  使用正则表达式抽取，并指定捕获组	html.regex("\(.*?)\",1)
     replace(String regex, String replacement)	替换内容	            html.replace("\","")
 *
 *
 *
 */
public class MerchandiseController {

    public static void main(String[] args) {
        Spider.create(new OschinaBlogPageProcesser())
                .addUrl("http://my.oschina.net/flashsword/blog?fromerr=8WB1uaU8")
                //.addPipeline(new ConsolePipeline())
                .addPipeline(new OschinaBlogPipeline())
                .run();
    }

}

class OschinaBlogPageProcesser implements PageProcessor {
    //模拟登陆
    private Site site = Site.me()
            .setDomain("oschina.net")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.71 Safari/537.36")
            .addHeader("cookic", "oscid=xxx");

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        List<String> links = html.links().regex("http://my\\.oschina\\.net/flashsword/blog?catalog=\\d+").all();
        page.addTargetRequests(links);
        page.putField("title", page.getHtml().xpath("//div[@class='title']/h2/a/text()").all());
        page.putField("content", page.getHtml().xpath("//div[@class='BlogContent']/text()").all());
        page.putField("tags",page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
class OschinaBlogPipeline implements Pipeline {
    public OschinaBlogPipeline() {
    }

    public void process(ResultItems resultItems, Task task) {
        List<String> titleResult = resultItems.get("title");
        List<String> contentResult = resultItems.get("content");

        for(int i = 0; i<titleResult.size();i++){
            System.out.println(titleResult.get(i));
            System.out.println(contentResult.get(i)+"\r\n---------------------");
        }

    }
}


