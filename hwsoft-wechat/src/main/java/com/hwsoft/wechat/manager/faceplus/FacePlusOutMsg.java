package com.hwsoft.wechat.manager.faceplus;

import com.jfinal.weixin.sdk.msg.in.InImageMsg;
import com.jfinal.weixin.sdk.msg.out.OutMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;

/**
 * Created by Administrator on 2016/3/21.
 */
public class FacePlusOutMsg extends OutMsg {

    private OutTextMsg outTextMsg;

    public FacePlusOutMsg(InImageMsg inImageMsg){

    }

    public OutTextMsg getOutTextMsg() {
        return outTextMsg;
    }

    public void setOutTextMsg(OutTextMsg outTextMsg) {
        this.outTextMsg = outTextMsg;
    }
}
