package com.hwsoft.wechat.manager.faceplus;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.hwsoft.util.http.HttpClientUtil;
import com.hwsoft.util.http.RequestType;
import com.hwsoft.util.http.ResponseFormat;
import com.jfinal.weixin.sdk.msg.in.InImageMsg;
import com.jfinal.weixin.sdk.msg.out.OutMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/3/21.
 */
public class FacePlusOutMsg extends OutMsg {

    private static String REQUEST_URL = "http://apicn.faceplusplus.com/v2/detection/detect";

    InImageMsg inImageMsg;

    public FacePlusOutMsg(InImageMsg inImageMsg){
        this.inImageMsg = inImageMsg;
    }

    public OutTextMsg getOutTextMsg() {
        String url = inImageMsg.getPicUrl();
        return new OutTextMsg(inImageMsg);
    }


    public static FacePlusResult request(){

        String responseBody =  HttpClientUtil.submitWithBodyContext(REQUEST_URL, RequestType.HTTP, ResponseFormat.STRING, new HashMap<String, String>(), null);
        FacePlusResult result =  JSON.parseObject(responseBody, FacePlusResult.class);
        return  result;
    }
}
