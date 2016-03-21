/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.sdk.msg.out;

import com.jfinal.weixin.sdk.msg.in.InMsg;

/**
 回复文本消息
 <xml>
 <ToUserName><![CDATA[toUser]]></ToUserName>
 <FromUserName><![CDATA[fromUser]]></FromUserName>
 <CreateTime>12345678</CreateTime>
 <MsgType><![CDATA[text]]></MsgType>
 <Content><![CDATA[你好]]></Content>
 </xml>
 */
public class TransferMsg extends OutMsg {
	public static final String TEMPLATE =
			"<xml>\n" +
					"<ToUserName><![CDATA[${__msg.toUserName}]]></ToUserName>\n" +
					"<FromUserName><![CDATA[${__msg.fromUserName}]]></FromUserName>\n" +
					"<CreateTime>${__msg.createTime}</CreateTime>\n" +
					"<MsgType><![CDATA[${__msg.msgType}]]></MsgType>\n" +
					"</xml>";

	private String content;

	public TransferMsg() {
		this.msgType = "transfer_customer_service";
	}

	public TransferMsg(InMsg inMsg) {
		super(inMsg);
		this.msgType = "transfer_customer_service";
	}

	public String getContent() {
		return content;
	}

	public TransferMsg setContent(String content) {
		this.content = content;
		return this;
	}
}


