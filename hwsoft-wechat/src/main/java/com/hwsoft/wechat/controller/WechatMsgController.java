package com.hwsoft.wechat.controller;

import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.MenuApi;
import com.jfinal.weixin.sdk.jfinal.MsgController;
import com.jfinal.weixin.sdk.msg.in.*;
import com.jfinal.weixin.sdk.msg.in.event.*;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.*;
import org.apache.log4j.Logger;

/**
 *
 * 微信Message消息处理控制类
 *
 * @author loson
 *
 */

public class WechatMsgController extends MsgController {

  static Logger logger = Logger.getLogger(WechatMsgController.class);

  private static final String helpTitle = "/::)感谢您关注恒温软件/:8-)\n\r";
  private static final String helpStr = "小贴士:" +
                                      "\n\r发送'帮助'可获得帮助" +
                                      "\n\r发送'视频'可获取视频教程" +
                                      "\n\r发送'美女'可看美女" +
                                      "\n\r发送'音乐'可听音乐" +
                                      "\n\r发送新闻可看新闻" +
                                      "\n\r公众号功能持续完善中...";


  @Override
  public ApiConfig getApiConfig(String accountIndex) {
    ApiConfig conf = new ApiConfig();
    conf.setToken(PropKit.get("token"));
    conf.setAppId(PropKit.get("appId"));
    conf.setAppSecret(PropKit.get("appSecret"));
    /**
     *  是否对消息进行加密，对应于微信平台的消息加解密方式：
     *  1：true进行加密且必须配置 encodingAesKey
     *  2：false采用明文模式，同时也支持混合模式
     */
    conf.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
    conf.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
    return conf;
  }

  @Override
  protected void processInTextMsg(InTextMsg inTextMsg) {
    String msgContent = inTextMsg.getContent().trim();
    logger.info("接收到消息：" + msgContent);

    if ("help".equalsIgnoreCase(msgContent) || "帮助".equals(msgContent)) {
      OutTextMsg outMsg = new OutTextMsg(inTextMsg);
      outMsg.setContent(helpStr);
      render(outMsg);
    }
    // 图文消息测试
    else if ("news".equalsIgnoreCase(msgContent) || "新闻".equalsIgnoreCase(msgContent)) {
      OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
      outMsg.addNews("我的微信公众号Hw_Office", "好看、好玩、好笑","https://mmbiz.qlogo.cn/mmbiz/ibHRiaZ9MRcUosol56OtHjVibWTK9opiaxsYTQHXuRwoib8YobOfqCbykp3ZSaEk8czAqdkAARU0OdKDtv34F5evFIQ/0?wx_fmt=jpeg", "http://mp.weixin.qq.com/s?__biz=MzA4MDA2OTA0Mg==&mid=208184833&idx=1&sn=d9e615e45902c3c72db6c24b65c4af3e#rd");
      outMsg.addNews("我的博客《智慧云端日记》", "好看、好玩、好笑", "https://mmbiz.qlogo.cn/mmbiz/ibHRiaZ9MRcUosol56OtHjVibWTK9opiaxsY9tPDricojmV5xxuLJyibZJXMAdNOx1qbZFcic9SvsPF2fTUnSc9oQB1IQ/0?wx_fmt=jpeg","http://mp.weixin.qq.com/s?__biz=MzA4MDA2OTA0Mg==&mid=208413033&idx=1&sn=06e816e1b2905c46c9a81df0ac0b3bad#rd");
      outMsg.addNews("我的博客《智慧云端日记》", "好看、好玩、好笑", "https://mmbiz.qlogo.cn/mmbiz/ibHRiaZ9MRcUosol56OtHjVibWTK9opiaxsY9tPDricojmV5xxuLJyibZJXMAdNOx1qbZFcic9SvsPF2fTUnSc9oQB1IQ/0?wx_fmt=jpeg","http://mp.weixin.qq.com/s?__biz=MzA4MDA2OTA0Mg==&mid=208413033&idx=1&sn=06e816e1b2905c46c9a81df0ac0b3bad#rd");
      render(outMsg);
    }
    // 音乐消息测试
    else if ("music".equalsIgnoreCase(msgContent) || "音乐".equals(msgContent)) {
      OutMusicMsg outMsg = new OutMusicMsg(inTextMsg);
      outMsg.setTitle("When The Stars Go Blue-Venke Knutson");
      outMsg.setDescription("建议在 WIFI 环境下流畅欣赏此音乐");
      outMsg.setMusicUrl("http://www.jfinal.com/When_The_Stars_Go_Blue-Venke_Knutson.mp3");
      outMsg.setHqMusicUrl("http://www.jfinal.com/When_The_Stars_Go_Blue-Venke_Knutson.mp3");
      outMsg.setFuncFlag(true);
      render(outMsg);
    }
    else if ("美女".equalsIgnoreCase(msgContent)) {
      OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
      outMsg.addNews(
              "宝贝更新喽",
              "宝贝更新喽，我们只看美女 ^_^",
              "https://mmbiz.qlogo.cn/mmbiz/ibHRiaZ9MRcUosol56OtHjVibWTK9opiaxsYTQHXuRwoib8YobOfqCbykp3ZSaEk8czAqdkAARU0OdKDtv34F5evFIQ/0?wx_fmt=jpeg",
              "http://mp.weixin.qq.com/s?__biz=MzA4MDA2OTA0Mg==&mid=207820985&idx=1&sn=4ef9361e68495fc3ba1d2f7f2bca0511#rd");
      render(outMsg);
    }
    // 其它文本消息直接返回原值 + 帮助提示
    else {
      renderOutTextMsg("\t您发送的内容已接收，内容为： " + inTextMsg.getContent() + "\n\n" + helpStr);
    }
    //createMenu();
  }

  @Override
  protected void processInImageMsg(InImageMsg inImageMsg) {
    //人脸识别
/*
    FacePlusOutMsg facePlus = new FacePlusOutMsg(inImageMsg);

    render(facePlus.getOutTextMsg());*/
  }

  @Override
  protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
      TransferMsg outMsg = new TransferMsg(inVoiceMsg);
      render(outMsg);
  }

  @Override
  protected void processInVideoMsg(InVideoMsg inVideoMsg) {
      OutTextMsg outMsg = new OutTextMsg(inVideoMsg);
      outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inVideoMsg.getMediaId());
      render(outMsg);
  }

  @Override
  protected void processInLocationMsg(InLocationMsg inLocationMsg) {
      OutTextMsg outMsg = new OutTextMsg(inLocationMsg);
      outMsg.setContent("已收到地理位置消息:" +
              "\nlocation_X = " + inLocationMsg.getLocation_X() +
              "\nlocation_Y = " + inLocationMsg.getLocation_Y() +
              "\nscale = " + inLocationMsg.getScale() +
              "\nlabel = " + inLocationMsg.getLabel());
      render(outMsg);
  }

  @Override
  protected void processInLinkMsg(InLinkMsg inLinkMsg) {
      OutNewsMsg outMsg = new OutNewsMsg(inLinkMsg);
      outMsg.addNews("链接消息已成功接收", "链接使用图文消息的方式发回给你，还可以使用文本方式发回。点击图文消息可跳转到链接地址页面，是不是很好玩 :)" , "http://mmbiz.qpic.cn/mmbiz/zz3Q6WSrzq1ibBkhSA1BibMuMxLuHIvUfiaGsK7CC4kIzeh178IYSHbYQ5eg9tVxgEcbegAu22Qhwgl5IhZFWWXUw/0", inLinkMsg.getUrl());
      render(outMsg);
  }



  @Override
  protected void processInFollowEvent(InFollowEvent inFollowEvent) {
      OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
      outMsg.setContent(helpTitle + helpStr);
      // 如果为取消关注事件，将无法接收到传回的信息
      render(outMsg);
  }

  @Override
  protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {

  }

  @Override
  protected void processInLocationEvent(InLocationEvent inLocationEvent) {
      OutTextMsg outMsg = new OutTextMsg(inLocationEvent);
      outMsg.setContent("processInLocationEvent() 方法测试成功");
      render(outMsg);
  }


  @Override
  protected void processInMenuEvent(InMenuEvent inMenuEvent) {
      if("V4001_GOOD".equals(inMenuEvent.getEventKey())){
          renderOutTextMsg("400-021-0951");
      }else if("V5001_GOOD".equals(inMenuEvent.getEventKey())) {
          renderOutTextMsg("客服电话：400-066-8969\n" +
                  "投资客户QQ群：482644889\n" +
                  "公司地址：浙江省杭州市江干区市民中心D座");
      }else{
          renderOutTextMsg("processInMenuEvent() 方法测试成功");
      }

  }

  @Override
  protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
      renderOutTextMsg("语音识别结果： " + inSpeechRecognitionResults.getRecognition());
  }

  String menuJsonStr = "{\n" +
          "    \"button\": [\n" +
          "        {\n" +
          "            \"name\": \"IT技术\",\n" +
          "            \"sub_button\": [\n" +
          "                {\n" +
          "                    \"type\": \"view\",\n" +
          "                    \"name\": \"我要学习\",\n" +
          "                    \"url\": \"http://www.iteye.com/\"\n" +
          "                },\n" +

          "                {\n" +
          "                    \"type\": \"view\",\n" +
          "                    \"name\": \"论坛\",\n" +
          "                    \"url\": \"http://http://www.iteye.com/forums\"\n" +
          "                }\n" +
          "            ]\n" +
          "        },\n" +
          "        {\n" +
          "            \"name\": \"服务中心\",\n" +
          "            \"sub_button\": [\n" +
          "                {\n" +
          "                    \"type\": \"view\",\n" +
          "                    \"name\": \"联系客服\",\n" +
          "                    \"url\": \"http://www.iteye.com/index/contactus\"\n" +
          "                },\n" +


          "                {\n" +
          "                     \"type\": \"click\",\n" +
          "                     \"name\": \"联系我们\",\n" +
          "                     \"key\": \"V5001_GOOD\"\n" +
          "                 }\n" +
          "            ]\n" +
          "        }\n" +
          "    ]\n" +
          "}";
  /**
   * 获取公众号菜单
   */
  public void createMenu() {
    System.out.println(menuJsonStr);
    ApiResult apiResult = MenuApi.createMenu(menuJsonStr);
    if (apiResult.isSucceed())
      renderText(apiResult.getJson());
    else
      renderText(apiResult.getErrorMsg());
  }

}

	