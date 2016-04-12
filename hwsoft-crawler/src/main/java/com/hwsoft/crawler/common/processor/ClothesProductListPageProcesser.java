package com.hwsoft.crawler.common.processor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hwsoft.crawler.common.config.Conf;
import com.hwsoft.crawler.controller.ProductController;
import com.hwsoft.model.product.ClothesProduct;
import com.hwsoft.util.http.HttpClientUtil;
import com.hwsoft.util.http.RequestMethod;
import com.hwsoft.util.http.RequestType;
import com.hwsoft.util.http.ResponseFormat;
import com.hwsoft.util.math.CalculateUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

/**
 * Created by arvin on 16/4/10.
 */
public class ClothesProductListPageProcesser implements PageProcessor {


    public ClothesProductListPageProcesser(){}

    private Site site = Site.me().setDomain(Conf.DOMAIN);

    @Override
    public void process(Page page) {
        //得到价格列表
       JSONObject responseJson =  getResponseJsonForPrice(page);

        //组装所需要的product首页信息
        List<String> galleryList = page.getHtml().$("div.module-gallery-01").all();
        List<ClothesProduct> clothesProductList = Lists.newArrayList();

        for(String gallery:galleryList){

            Html html = new Html(gallery);

            String productImageUrl = html.xpath("//div[@class='gallery-image']/a/img/@initSrc").toString();//图片链接

            String productDetailUrl = html.xpath("//p[@class='title-area']/a/@href").toString();//产品详情连接

            String productTitle = html.xpath("//p[@class='title-area']/a/@title").toString();//产品标题

            //String doller = html.xpath("//p[@class='price-area']/span/text()").all().get(0);//美元销售价格

            //String rmb = html.xpath("//p[@class='price-area']/span/text()").all().get(1);//人民币销售价格

            String identity = html.xpath("//p[@class='dct-area']/@id").toString();//产品标识

            String id  = identity.substring(identity.lastIndexOf("_") + 1);

            JSONObject price = (JSONObject)responseJson.get(id);
            if(price!=null) {
                JSONObject prd = (JSONObject) price.get("prc");

                JSONObject prdc = (JSONObject) price.get("prc");

                JSONObject usd = (JSONObject) prdc.get("usd");

                JSONObject cny = (JSONObject) prdc.get("cny");
                //TODO 暂时处理 不处理出现空指针异常
                ClothesProduct clothesProduct = new ClothesProduct();
                clothesProduct.setProductDetailUrl(productDetailUrl);
                clothesProduct.setProductName(productTitle);
                clothesProduct.setProductPrice(CalculateUtil.formatNubmer(Double.parseDouble(cny == null ? "0" : cny.toString()), 2));
                clothesProduct.setProductPromotePrice(CalculateUtil.formatNubmer(Double.parseDouble(cny == null ? "0" : cny.toString()), 2));
                clothesProductList.add(clothesProduct);
            }



        }
        page.putField("clothesProductList", clothesProductList);

    }

    @Override
    public Site getSite() {
        return site;

    }

    public static JSONObject getResponseJsonForPrice(Page page){

        String pageStr = page.getHtml().toString();
        String path = pageStr.substring(pageStr.lastIndexOf("jQuery.get('/"), pageStr.indexOf("', function(json){")).replace("jQuery.get('/","");//填充金额
        String request = Conf.INDEX_PAGE_URL+path;

        String response = HttpClientUtil.submitWithFormOrUriParam(request, RequestType.HTTP, RequestMethod.GET, ResponseFormat.STRING,new HashMap<String, String>());

        JSONObject object = (JSONObject)JSONObject.parse(response);
        JSONObject responseJson =  (JSONObject)object.get("response");

        System.out.println(responseJson);

        return responseJson;

    }

}