/*
package com.hwsoft.wechat.manager.faceplus;

import com.facepp.http.PostParameters;
import com.facepp.result.FaceppResult;
import com.jfinal.weixin.sdk.msg.in.InImageMsg;
import com.jfinal.weixin.sdk.msg.out.OutMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;


*/
/**
 * Created by Administrator on 2016/3/21.
 *//*

public class FacePlusOutMsg extends OutMsg {


    InImageMsg inImageMsg;

    public FacePlusOutMsg(InImageMsg inImageMsg){
        this.inImageMsg = inImageMsg;
    }

    public OutTextMsg getOutTextMsg() {
        PostParameters params = new PostParameters();
        params.setUrl(inImageMsg.getPicUrl());
        FaceppResult result =  FacePlusRequestManager.requestDetectionDetect(params);
        //取得face＋＋返回结果分析
        OutTextMsg outTextMsg = new OutTextMsg();
        outTextMsg.setContent(result.toString());
        return outTextMsg;
    }

}
*/
