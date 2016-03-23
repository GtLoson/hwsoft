package com.hwsoft.wechat.manager.faceplus;

/**
 * Created by arvin on 16/3/23.
 */
public  class ConfigFacePlus{

    private String api_key;
    private String api_secret;
    private String url;
    private String img;
    private String model;
    private String attribute;//gender, age, race, smiling, glass, pose
    private String tag;
    private Boolean async;

    ConfigFacePlus(){}
    ConfigFacePlus(String api_key,String api_secret,String url){
        setApi_key(api_key);
        setApi_secret(api_secret);
        setUrl(url);
    }
    ConfigFacePlus(String api_key,String api_secret,String img,String url,String model){
        setApi_key(api_key);
        setApi_secret(api_secret);
        setModel(model);
        setImg(img);
    }
    ConfigFacePlus(String api_key,String api_secret,String url,String model,String attribute,String tag,Boolean async){
        setApi_key(api_key);
        setApi_secret(api_secret);
        setAttribute(attribute);
        setUrl(url);
        setModel(model);
        setTag(tag);
        setAsync(async);
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public void setApi_secret(String api_secret) {
        this.api_secret = api_secret;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }
}
