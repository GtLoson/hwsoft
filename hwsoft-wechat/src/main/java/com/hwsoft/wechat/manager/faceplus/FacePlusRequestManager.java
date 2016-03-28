package com.hwsoft.wechat.manager.faceplus;

import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.facepp.result.FaceppResult;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;

/**
 * Created by arvin on 16/3/29.
 */
public class FacePlusRequestManager {

    private static final boolean DEBUG = true;

    public static FaceppResult requestDetectionDetect(PostParameters params){
        FaceppResult result = null;
        try {
            HttpRequests httpRequests = new HttpRequests(FaceConfig.API_KEY, FaceConfig.API_SECRET, DEBUG);
            httpRequests.detectionDetect(params);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    public static void main(String[] args){
        try {
            HttpRequests httpRequests = new HttpRequests(FaceConfig.API_KEY, FaceConfig.API_SECRET, true);
            FaceppResult result = httpRequests
                    .detectionDetect(new PostParameters()
                            .setUrl("http://www.faceplusplus.com.cn/wp-content/themes/faceplusplus/assets/img/demo/9.jpg"));
            System.out.println(result.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
