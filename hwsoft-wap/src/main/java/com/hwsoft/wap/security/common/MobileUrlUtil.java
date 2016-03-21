/**
 * 
 */
package com.hwsoft.wap.security.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tzh
 *
 */
public class MobileUrlUtil {

	private static List<String> NO_LOGIN_URL_LIST;
	
	static{
		NO_LOGIN_URL_LIST = new ArrayList<String>();
		NO_LOGIN_URL_LIST.add("login");
		NO_LOGIN_URL_LIST.add("download");
		NO_LOGIN_URL_LIST.add("autoDownload");
		NO_LOGIN_URL_LIST.add("loginSuccess");
		NO_LOGIN_URL_LIST.add("loginFailed");
		NO_LOGIN_URL_LIST.add("loginTimeout");
		NO_LOGIN_URL_LIST.add("loginOut");
		NO_LOGIN_URL_LIST.add("logoutSuccess");
		NO_LOGIN_URL_LIST.add("resetLoginPasswordCode");
		NO_LOGIN_URL_LIST.add("account/resetLoginPassword");//忘记密码
		NO_LOGIN_URL_LIST.add("registerCode");//注册验证码
		NO_LOGIN_URL_LIST.add("register");//注册
		NO_LOGIN_URL_LIST.add("mobileRegistered");//查询手机是否已经注册
		NO_LOGIN_URL_LIST.add("agreement/registerAgreement");//注册协议
		NO_LOGIN_URL_LIST.add("agreement/privacyAgreement");//隐私条款
		NO_LOGIN_URL_LIST.add("agreement/sequestrateAgreement");//联动划款协议
		NO_LOGIN_URL_LIST.add("template/securityTemplate");//安全保障页面模板
		NO_LOGIN_URL_LIST.add("more/dot");// 更多信息--用于处理更多页面想要显示的小红点
		
		NO_LOGIN_URL_LIST.add("list");//TODO 测试使用
		NO_LOGIN_URL_LIST.add("score");//TODO 测试使用
		NO_LOGIN_URL_LIST.add("exchange");//TODO 测试使用
		NO_LOGIN_URL_LIST.add("check");//TODO 测试使用
		NO_LOGIN_URL_LIST.add("recommend");//TODO 测试使用
		NO_LOGIN_URL_LIST.add("testCache");//TODO 测试使用
		NO_LOGIN_URL_LIST.add("detailProduct");//TODO 测试使用defaultChannel
		NO_LOGIN_URL_LIST.add("productAgreementTemplate");//TODO 测试使用
		NO_LOGIN_URL_LIST.add("defaultChannel");
		NO_LOGIN_URL_LIST.add("product/productInfo");
		NO_LOGIN_URL_LIST.add("product/productDesc");
		NO_LOGIN_URL_LIST.add("product/productRisk");
	}

	public static List<String> getNO_LOGIN_URL_LIST() {
		return NO_LOGIN_URL_LIST;
	}
	
}
